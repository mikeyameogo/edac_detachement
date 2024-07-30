/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service.impl;

import bf.mfptps.detachementservice.domain.*;
import bf.mfptps.detachementservice.enums.EStatutDemande;
import bf.mfptps.detachementservice.exception.CustomException;
import bf.mfptps.detachementservice.repository.*;
import bf.mfptps.detachementservice.service.DemandeService;
import bf.mfptps.detachementservice.service.HistoriqueService;
import bf.mfptps.detachementservice.service.PieceJointeService;
import bf.mfptps.detachementservice.service.dto.DemandeDTO;
import bf.mfptps.detachementservice.service.dto.HistoriqueDTO;
import bf.mfptps.detachementservice.service.mapper.DemandeMapper;
import bf.mfptps.detachementservice.service.mapper.HistoriqueMapper;
import bf.mfptps.detachementservice.util.AppUtil;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@Service
public class DemandeServiceImpl implements DemandeService {

    private final DemandeRepository demandeRepository;

    private final TypeDemandeRepository typeDemandeRepository;

    private final AgentRepository agentRepository;

    private final MinistereStructureRepository ministereStructureRepository;
    private final PieceJointeService pieceJointeService;
    private final PieceJointeRepository pieceJointeRepository;
    private final CircuitRepository circuitRepository;
    private final DureeRepository dureeRepository;
    private final HistoriqueRepository historiqueRepository;
    private final HistoriqueService historiqueService;

    private final DemandeMapper demandeMapper;
    private final HistoriqueMapper historiqueMapper;

    public DemandeServiceImpl(DemandeRepository demandeRepository,
            TypeDemandeRepository typeDemandeRepository,
            AgentRepository agentRepository,
            DureeRepository dureeRepository,
            MinistereStructureRepository ministereStructureRepository,
            PieceJointeService pieceJointeService, PieceJointeRepository pieceJointeRepository,
            CircuitRepository circuitRepository, HistoriqueRepository historiqueRepository,
            HistoriqueService historiqueService, DemandeMapper demandeMapper, HistoriqueMapper historiqueMapper) {
        this.demandeRepository = demandeRepository;
        this.typeDemandeRepository = typeDemandeRepository;
        this.agentRepository = agentRepository;
        this.ministereStructureRepository = ministereStructureRepository;
        this.pieceJointeService = pieceJointeService;
        this.pieceJointeRepository = pieceJointeRepository;
        this.circuitRepository = circuitRepository;
        this.dureeRepository = dureeRepository;
        this.historiqueRepository = historiqueRepository;
        this.historiqueService = historiqueService;
        this.demandeMapper = demandeMapper;
        this.historiqueMapper = historiqueMapper;
    }

    @Override
    public DemandeDTO save(DemandeDTO demandeDTO) {
        Demande demande = demandeMapper.toEntity(demandeDTO);
        return demandeMapper.toDto(demandeRepository.save(demande));
    }

    @Transactional(rollbackFor = CustomException.class)
    @Override
    public DemandeDTO create(DemandeDTO demandeDTO) {
        log.info("creation d'une demande : {}", demandeDTO);
        // controle de cohérence de données
        if (demandeDTO.getPieceJointes() == null || CollectionUtils.isEmpty(demandeDTO.getPieceJointes())) {
            throw new CustomException("Veuillez fournir les pièces-jointes SVP.");
        }

        TypeDemande typeDemande = typeDemandeRepository
                .findById(demandeDTO.getTypeDemande().getId())
                .orElseThrow(() -> new CustomException(
                        "Le type de demande " + demandeDTO.getTypeDemande().getId() + " est inexistant."));
        Agent agent = agentRepository
                .findById(demandeDTO.getAgent().getId())
                .orElseThrow(() -> new CustomException(
                        "L'agent demandeur " + demandeDTO.getAgent().getId() + " est introuvable en base de données."));

        Duree duree = dureeRepository.save(demandeDTO.getDuree());

        // mappage, initialisation et persistence des données de la demande
        Demande demande = demandeMapper.toEntity(demandeDTO);
        demande.setTypeDemande(typeDemande);
        demande.setAgent(agent);
        demande.setDateDemande(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        demande.setNumero(this.generateDemandeSequence(agent));
        // Ajout du statut a la creation de la demande
        demande.setStatut(EStatutDemande.INITIEE);
        demande.setDuree(duree);
        demande = demandeRepository.save(demande);

        // pieceJointeRepository.saveAll(pieceJointes);
        for (PieceJointe pj : demandeDTO.getPieceJointes()) {
            pj.setDemande(demande);
            pieceJointeRepository.save(pj);
        }

        // Creation de l'historique liee a la demande
        Historique historique = new Historique();
        historique.setDemande(demande);
        historique.setCommentaire("DEMANDE INITIEE");
        historique.setCircuit(circuitRepository.findByPosition("CLIENT"));
        historiqueRepository.save(historique);
       // historiqueService.create(historiqueMapper.toDto(historique));

        return demandeMapper.toDto(demande);
    }

    /**
     * Verif et creation du repertoire local de stockage de fichiers uploaded
     *
     * @param directoryRef
     * @return
     */
    private Path initLocalStoragePath(String directoryRef) {
        Path rootPath = Paths.get(AppUtil.appStoreRootPath + directoryRef + File.separatorChar);
        try {
            Files.createDirectories(rootPath);
            return rootPath;
        } catch (IOException e) {
            throw new CustomException("Erreur survenue lors de l'initialisation de la source de stockage.");
        }
    }

    private final String generateDemandeSequence(Agent agent) {
        String sequence = "";
        Calendar calendarToYear = Calendar.getInstance();
        calendarToYear.setTime(new Date());
        Structure s = agent.getStructure();
        Ministere m = s.getMinistere();
        // AgentStructure as =
        // agentStructureRepository.findByAgentIdAndActifTrue(agent.getId()).orElseThrow(()
        // -> new CustomException("La structure active de l'agent " + agent.getId() + "
        // est introuvable."));
        // MinistereStructure ms =
        // ministereStructureRepository.findByStructureIdAndStatutIsTrue(as.getStructure().getId())
        // .orElseThrow(() -> new CustomException("Le ministère d'origine de ce
        // demandeur est introuvable."));
        long count = demandeRepository.count();
        sequence += m.getSigle().toUpperCase() + "/" + s.getSigle().toUpperCase() + "/"
                + calendarToYear.get(Calendar.YEAR) + "-" + String.format("%04d", (count + 1));
        return sequence;
    }

    @Override
    public DemandeDTO update(DemandeDTO demandeDTO) {
        log.info("update de la demande : {}", demandeDTO);

        if (demandeDTO.getPieceJointes() == null || CollectionUtils.isEmpty(demandeDTO.getPieceJointes())) {
            throw new CustomException("Veuillez fournir les pièces-jointes !");
        }

        if (demandeDTO.getStatut() != EStatutDemande.INITIEE && demandeDTO.getStatut() != EStatutDemande.REJET_CA
                && demandeDTO.getStatut() != EStatutDemande.REJET_DRH
                && demandeDTO.getStatut() != EStatutDemande.REJET_SG) {
            throw new CustomException("Vous ne pouvez plus modifier cette demande !");
        }

        for (PieceJointe anciennesPj : pieceJointeService.findAllByDemande(demandeDTO.getId())) {
            anciennesPj.setDeleted(true);
            pieceJointeRepository.save(anciennesPj);
        }

        Duree duree = dureeRepository.save(demandeDTO.getDuree());
        Demande demande = demandeMapper.toEntity(demandeDTO);
        demande.setDuree(duree);
        demande.setLastModifiedDate(Instant.now());
        Historique historique = new Historique();
        historique.setDateAvis(new Date().toInstant());
        historique.setDemande(demande);

        if (demandeDTO.getStatut() == EStatutDemande.INITIEE) {
            demande.setStatut(EStatutDemande.INITIEE);
            historique.setCircuit(circuitRepository.findByPosition("CLIENT"));
        }
        if (demandeDTO.getStatut() == EStatutDemande.REJET_CA) {
            demande.setStatut(EStatutDemande.IMPUTEE);
            historique.setCommentaire("CORRECTION CLIENT");
            historique.setCircuit(circuitRepository.findByPosition("CLIENT"));
        }
        if (demandeDTO.getStatut() == EStatutDemande.REJET_DRH) {
            demande.setStatut(EStatutDemande.CONFORME);
            historique.setAvis("CONFORME");
            historique.setCircuit(circuitRepository.findByPosition("CA"));
            historique.setCommentaire("CORRECTION CA");
        }
        if (demandeDTO.getStatut() == EStatutDemande.REJET_SG) {
            demande.setStatut(EStatutDemande.AVIS_DRH);
            historique.setAvis("CONFORME");
            historique.setCommentaire("CORRECTION CA");
            historique.setCircuit(circuitRepository.findByPosition("CA"));
        }

        demande = demandeRepository.save(demande);

        for (PieceJointe pj : demandeDTO.getPieceJointes()) {
            pieceJointeRepository.save(pj);
        }

        historiqueRepository.save(historique);

        return demandeMapper.toDto(demande);
    }

    @Override
    public Optional<DemandeDTO> get(Long id) {
        log.info("Consultation de la demande : {}", id);

        return Optional.ofNullable(demandeMapper.toDto(
                demandeRepository.findById(id).orElseThrow(() ->
                        new bf.mfptps.detachementservice.exception.CustomException(
                                "La demande spécifiée n'existe pas !"))));
    }

    @Override
    public Page<DemandeDTO> findAll(Pageable pageable) {
        log.info("liste paginée des demandes");
        return demandeRepository.findAll(pageable).map(demandeMapper::toDto);
    }

    @Override
    public List<DemandeDTO> findAllList() {
        log.info("liste des demandes");
        return demandeRepository.findAll().stream().map(demandeMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<DemandeDTO> findAllByTypedemande(Long idTypeDemande) {
        log.info("Lise des demandes d'un type donné : {}", idTypeDemande);
        return demandeRepository.findByTypeDemandeId(idTypeDemande).stream().map(demandeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DemandeDTO> findAllByAgent(Long idAgent) {
        log.info("Lise des demandes d'un agent donné : {}", idAgent);
        return demandeRepository.findByAgentId(idAgent).stream().map(demandeMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public DemandeDTO receptionnerDemande(Long id) {
        log.info("Receptionner une demande : {}", id);

        Demande demande = demandeRepository.findById(id).orElseThrow(() ->
                new bf.mfptps.detachementservice.exception.CustomException(
                "La demande spécifiée n'existe pas. Veuillez réessayer !"));

        demande.setStatut(EStatutDemande.RECEPTIONEE);
        demande = demandeRepository.save(demande);

        Historique historique = new Historique();
        historique.setDateAvis(new Date().toInstant());
        historique.setCommentaire("Demande réceptionnée !");
        historique.setDemande(demande);
        historique.setCircuit(circuitRepository.findByPosition("CSTDRH"));
        historiqueRepository.save(historique);

        return demandeMapper.toDto(demande);
    }

    @Override
    public DemandeDTO analyserDemande(Long id, HistoriqueDTO historiqueDTO) {
        log.info("Analyser une demande : {} par CA", id);

        Demande demande = demandeRepository.findById(id).orElseThrow(() ->
                new bf.mfptps.detachementservice.exception.CustomException(
                "La demande spécifiée n'existe pas. Veuillez réessayer !"));

        Historique historique = historiqueMapper.toEntity(historiqueDTO);
        if (historique.getAvis().equalsIgnoreCase("CONFORME")) {
            demande.setStatut(EStatutDemande.CONFORME);
            historique.setCircuit(circuitRepository.findByPosition("CA"));
        }
        else {
            demande.setStatut(EStatutDemande.REJET_CA);
            historique.setCircuit(circuitRepository.findByPosition("CA"));
        }

        demande = demandeRepository.save(demande);
        historique.setDemande(demande);
        historique.setDateAvis(new Date().toInstant());
       historiqueRepository.save(historique);

        return demandeMapper.toDto(demande);
    }

    @Override
    public DemandeDTO aviserDemandeSH(Long id, HistoriqueDTO historiqueDTO) {
        log.info("Aviser une demande : {} par SH", id);

        Demande demande = demandeRepository.findById(id).orElseThrow(() ->
                new bf.mfptps.detachementservice.exception.CustomException(
                        "La demande spécifiée n'existe pas. Veuillez réessayer !"));
        demande.setStatut(EStatutDemande.AVIS_SH);
        Historique historique = historiqueMapper.toEntity(historiqueDTO);
        historique.setDemande(demande);
        historique.setDateAvis(new Date().toInstant());
        historique.setCircuit(circuitRepository.findByPosition("SH"));
       historiqueRepository.save(historique);

        return demandeMapper.toDto(demandeRepository.save(demande));
    }

    @Override
    public DemandeDTO aviserDemandeDRH(Long id, HistoriqueDTO historiqueDTO) {
        log.info("Aviser une demande : {} par DRH", id);

        Demande demande = demandeRepository.findById(id).orElseThrow(() ->
                new bf.mfptps.detachementservice.exception.CustomException(
                        "La demande spécifiée n'existe pas. Veuillez réessayer !"));
        demande.setStatut(EStatutDemande.AVIS_DRH);
        Historique historique = historiqueMapper.toEntity(historiqueDTO);
        historique.setDemande(demande);
        historique.setDateAvis(new Date().toInstant());
        historique.setCircuit(circuitRepository.findByPosition("DRH"));
        historiqueRepository.save(historique);

        return demandeMapper.toDto(demandeRepository.save(demande));
    }

    @Override
    public DemandeDTO validerDemande(Long id, HistoriqueDTO historiqueDTO) {
        log.info("Valider une demande : {} par SG", id);

        Demande demande = demandeRepository.findById(id).orElseThrow(() ->
                new bf.mfptps.detachementservice.exception.CustomException(
                        "La demande spécifiée n'existe pas. Veuillez réessayer !"));

        Historique historique = historiqueMapper.toEntity(historiqueDTO);
        if (historique.getAvis().equalsIgnoreCase("FAVORABLE")) {
            demande.setStatut(EStatutDemande.DEMANDE_VALIDEE);
            historique.setCircuit(circuitRepository.findByPosition("SG"));
        } else {
            demande.setStatut(EStatutDemande.REFUSEE);
            historique.setCircuit(circuitRepository.findByPosition("SG"));
        }

        demande = demandeRepository.save(demande);
        historique.setDemande(demande);
        historique.setDateAvis(new Date().toInstant());
        historiqueRepository.save(historique);

        return demandeMapper.toDto(demande);
    }

    @Override
    public DemandeDTO rejetDRH(Long id, HistoriqueDTO historiqueDTO) {
        log.info("Rejet de la demande: {} par DRH", id);
        Demande demande = demandeRepository.findById(id).orElseThrow(() ->
                new bf.mfptps.detachementservice.exception.CustomException(
                        "La demande spécifiée n'existe pas. Veuillez réessayer !"));

        demande.setStatut(EStatutDemande.REJET_DRH);
        demande = demandeRepository.save(demande);

        Historique historique = historiqueMapper.toEntity(historiqueDTO);
        historique.setDemande(demande);
        historique.setDateAvis(new Date().toInstant());
        historique.setCircuit(circuitRepository.findByPosition("DRH"));
        historiqueRepository.save(historique);

        return demandeMapper.toDto(demande);
    }

    @Override
    public DemandeDTO rejetSG(Long id, HistoriqueDTO historiqueDTO) {
        log.info("Rejet de la demande: {} par SG", id);
        Demande demande = demandeRepository.findById(id).orElseThrow(() ->
                new bf.mfptps.detachementservice.exception.CustomException(
                        "La demande spécifiée n'existe pas. Veuillez réessayer !"));

        demande.setStatut(EStatutDemande.REJET_SG);
        demande = demandeRepository.save(demande);

        Historique historique = historiqueMapper.toEntity(historiqueDTO);
        historique.setDemande(demande);
        historique.setDateAvis(new Date().toInstant());
        historique.setCircuit(circuitRepository.findByPosition("SG"));
       historiqueRepository.save(historique);

        return demandeMapper.toDto(demande);
    }

    @Override
    public void delete(Long id) {
        log.info("suppression d'une demande : {}", id);
        demandeRepository.deleteById(id);
    }

    public Page<DemandeDTO> findMyDmds(Pageable pageable, String matricule) {
        log.info("liste paginée des demandes");
        return demandeRepository.findMyDmds(pageable, matricule).map(demandeMapper::toDto);
    }

    public Page<DemandeDTO> findMyAgentDmds(Pageable pageable, String matricule) {
        log.info("liste paginée des demandes");
        return demandeRepository.findMyAgentDmds(pageable, matricule).map(demandeMapper::toDto);
    }

    public Page<DemandeDTO> findMinistereDmds(Pageable pageable, String matricule) {
        log.info("liste paginée des demandes");
        Agent agent = agentRepository.findOneByMatricule(matricule).get();
        String codeMinistere = agent.getStructure().getMinistere().getCode();
        return demandeRepository.findMinistereDmds(pageable, codeMinistere).map(demandeMapper::toDto);
    }

    /**
     * Process: mettre la demande a "en cours...", puis set l'agent d'analyse
     *
     * @param id                  : id de la demande
     * @param matriculeImputation : matricule de l'agent charge d'analyse a qui
     *                            est imputee la demande
     * @return
     */
    @Override
    public DemandeDTO imputerDemande(Long id, String matriculeImputation) {
        Demande response = demandeRepository.findById(id)
                .orElseThrow(() -> new bf.mfptps.detachementservice.exception.CustomException(
                        "La demande n'existe pas. Veuillez réessayer SVP."));
        if (response.getStatut() != EStatutDemande.AVIS_SH) {
            throw new CustomException("La demande en question doit être préalablement receptionnée.");
        }
        // Le système change l’état de la demande à (IMPUTEE) et la positionne chez le
        // chargé d’analyse choisi
        response.setStatut(EStatutDemande.IMPUTEE);
        response.setImputerA(matriculeImputation);
        response = demandeRepository.save(response);

        Historique historique = new Historique();
        historique.setDemande(response);
        historique.setCircuit(circuitRepository.findByPosition("CSTDRH"));
        historique.setDateAvis(new Date().toInstant());
        historique.setAvis("IMPUTEE");
        historique.setCommentaire("A TRAITER");
        historiqueRepository.save(historique);

        return demandeMapper.toDto(response);
    }

    /**
     *
     * @param id : id de la demande a receptionner
     * @return
     */
    @Override
    public DemandeDTO receptionnerDemandeValidee(Long id) {
        Demande response = demandeRepository.findById(id)
                .orElseThrow(() -> new bf.mfptps.detachementservice.exception.CustomException(
                        "La demande n'existe pas. Veuillez réessayer SVP."));
        // containtes : statut = demndeValidee, circuit = chez SG, avis = favorable
        if (response.getStatut() != EStatutDemande.DEMANDE_VALIDEE) {
            throw new CustomException("La demande en question doit être préalablement validée.");
        }
        Historique isFavorableBySG = historiqueRepository.findByIdAndValideSG(id)
                .orElseThrow(() -> new bf.mfptps.detachementservice.exception.CustomException(
                        "La demande doit être préalablement validée par SG"));
        // telecharger demande et piecejointes (voir endpoint exporterFormulaireDemande)
        // mettre demande a ELABORATION
        response.setStatut(EStatutDemande.ELABORATION);
        response = demandeRepository.save(response);

        Historique historique = new Historique();
        historique.setDemande(response);
        historique.setCommentaire("Demande valide réceptionnée");
        historique.setCircuit(circuitRepository.findByPosition("STDRH"));
        historique.setDateAvis(new Date().toInstant());
        historiqueRepository.save(historique);

        return demandeMapper.toDto(response);
    }

    /**
     *
     * @param id : id de la demande a abandonner
     * @return
     */
    @Override
    public DemandeDTO abandonnerDemande(Long id) {
        Demande response = demandeRepository.findById(id)
                .orElseThrow(() -> new bf.mfptps.detachementservice.exception.CustomException(
                        "La demande n'existe pas. Veuillez réessayer SVP."));
        // containtes : statut != ELABORATION, circuit pas encore chez SG, avis !=
        // PROJET D'ARRETE ELABORE
        if (response.getStatut() == EStatutDemande.ELABORATION
                || response.getStatut() == EStatutDemande.DEMANDE_VALIDEE
                || response.getStatut() == EStatutDemande.PROJET_ELABORE
                || response.getStatut() == EStatutDemande.PROJET_VISE
                || response.getStatut() == EStatutDemande.PROJET_SIGNE) {
            throw new CustomException("La demande en question ne peut être abandonnée.");
        }
        response.setStatut(EStatutDemande.ABANDONNEE);
        response.setDeleted(false);// on enleve la demande du circuit
        response = demandeRepository.save(response);

        Historique historique = new Historique();
        historique.setDemande(response);
        historique.setCommentaire("Demande abandonnée");
        historique.setCircuit(circuitRepository.findByPosition("CLIENT"));
        historique.setDateAvis(new Date().toInstant());
        historiqueRepository.save(historique);

        return demandeMapper.toDto(response);
    }

}
