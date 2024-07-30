package bf.mfptps.detachementservice.web;

import bf.mfptps.detachementservice.domain.Historique;
import bf.mfptps.detachementservice.enums.EStatutDemande;
import bf.mfptps.detachementservice.exception.CreateNewElementException;
import bf.mfptps.detachementservice.exception.UpdateElementException;
import bf.mfptps.detachementservice.repository.CircuitRepository;
import bf.mfptps.detachementservice.repository.HistoriqueRepository;
import bf.mfptps.detachementservice.service.DemandeService;
import bf.mfptps.detachementservice.service.FileService;
import bf.mfptps.detachementservice.service.HistoriqueService;
import bf.mfptps.detachementservice.service.MailService;
import bf.mfptps.detachementservice.service.dto.DemandeDTO;
import bf.mfptps.detachementservice.service.dto.HistoriqueDTO;
import bf.mfptps.detachementservice.service.mapper.DemandeMapper;
import bf.mfptps.detachementservice.util.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Created by Zak TEGUERA on 29/09/2023.
 * <teguera.zakaria@gmail.com>
 */
@CrossOrigin("*")
@RestController
@RequestMapping("api/detachements/demandes")
public class DemandeResource {
//git st
    @Value("${storage.folder}")
    private String storageFolder;

    private final DemandeService demandeService;

    private final DemandeMapper demandeMapper;

    private final HistoriqueRepository historiqueRepository;

    private final CircuitRepository circuitRepository;

    private final FileService fileService;

    private final MailService mailService;

    public DemandeResource(DemandeService demandeService, DemandeMapper demandeMapper, HistoriqueService historiqueService, HistoriqueRepository historiqueRepository, CircuitRepository circuitRepository, FileService fileService, MailService mailService) {
        this.demandeService = demandeService;
        this.demandeMapper = demandeMapper;
        this.historiqueRepository = historiqueRepository;
        this.circuitRepository = circuitRepository;
        this.fileService = fileService;
        this.mailService = mailService;
    }

    @Operation(summary = "Ajoute une demande avec PJ", description = "Ajoute une demande avec PJ")
    @ApiResponse(responseCode = "201", description = "Demande créée avec succès")
    @ApiResponse(responseCode = "401")
    @ApiResponse(responseCode = "409", description = "Conflict, une donnée similaire existe déjà")
    @ApiResponse(responseCode = "500", description = "Le serveur n'est pas en mesure de traiter la situation qu'il rencontre")
    @PostMapping(path = "/new", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<DemandeDTO> create(@RequestBody DemandeDTO request) throws URISyntaxException {
        if (request.getId() != null) {
            throw new CreateNewElementException();
        }
        DemandeDTO response = demandeService.create(request);

        //le bloc if est un test d'envoi de mail
        if (response.getAgent().getEmail() != null) {
            mailService.sendEmail(response.getAgent().getEmail(), "ENVOIE DE VOTRE DEMANDE", mailService.constructReceptionBody(response));
        }

        if (response.getAgent().getSuperieurHierarchique().getEmail() != null) {
            mailService.sendEmail(response.getAgent().getSuperieurHierarchique().getEmail(), "DEMANDE DE CHANGEMENT DE POSITION D'UN DE VOS AGENTS", mailService.constructSHBody(response));
        }

        return ResponseEntity.created(new URI("/api/demandes")).body(response);
    }

    @Operation(summary = "Modifie une demande", description = "Modifie une demande")
    @PutMapping("/update")
    public ResponseEntity<DemandeDTO> update(@RequestBody DemandeDTO request) throws URISyntaxException {
        if (request.getId() == null) {
            throw new UpdateElementException();
        }
        DemandeDTO response = demandeService.update(request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Recherche une demande via un ID", description = "Recherche une demande via un ID")
    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<DemandeDTO>> get(@PathVariable(name = "id", required = true) Long id) {
        Optional<DemandeDTO> demande = demandeService.get(id);
        return ResponseEntity.ok().body(demande);
    }

    @Operation(summary = "Liste toutes les demandes", description = "Liste toutes les demandes")
    @GetMapping("/list-page")
    public ResponseEntity<List<DemandeDTO>> findAll(Pageable pageable) {
        Page<DemandeDTO> demandes = demandeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), demandes);
        return new ResponseEntity<>(demandes.getContent(), headers, HttpStatus.OK);
    }

    @Operation(summary = "Liste toutes les demandes", description = "Liste toutes les demandes")
    @GetMapping("/list")
    public ResponseEntity<List<DemandeDTO>> findAll() {
        List<DemandeDTO> demandes = demandeService.findAllList();
        return ResponseEntity.ok().body(demandes);
    }

    @Operation(summary = "Receptionner une demande", description = "Receptionner une demande")
    @PostMapping("/receptionner/{id}")
    public ResponseEntity<DemandeDTO> receptionner(@PathVariable Long id) {
        DemandeDTO demande = demandeService.receptionnerDemande(id);
        return ResponseEntity.ok().body(demande);
    }

    //imprimer ensuite le formulaire de demande via le endpoit dedie
    @Operation(summary = "Receptionner une demande validee par SG", description = "Receptionner une demande validee par SG")
    @PostMapping("/receptionner/valided-sg/{id}")
    public ResponseEntity<DemandeDTO> receptionnerValidedBySG(@PathVariable Long id) {
        DemandeDTO demande = demandeService.receptionnerDemandeValidee(id);
        return ResponseEntity.ok().body(demande);
    }

    @Operation(summary = "Receptionner une demande validee par SG", description = "Receptionner une demande validee par SG")
    @PostMapping("/imputer/{id}/{matriculeImputation}")
    public ResponseEntity<DemandeDTO> imputerDemande(@PathVariable Long id, @PathVariable String matriculeImputation) {
        DemandeDTO demande = demandeService.imputerDemande(id, matriculeImputation);
        return ResponseEntity.ok().body(demande);
    }

    //permet d’abandonner une demande d’acte dont le projet n’a pas encore été élaboré
    @Operation(summary = "Abandonner une demande d'acte", description = "Abandonner une demande d'acte")
    @PostMapping("/abandonner/{id}")
    public ResponseEntity<DemandeDTO> abandonnerDemande(@PathVariable Long id) {
        DemandeDTO demande = demandeService.abandonnerDemande(id);
        return ResponseEntity.ok().body(demande);
    }

    @Operation(summary = "SH Avise une demande", description = "SH Avise une demande")
    @PostMapping("/avis-sh/{id}")
    public ResponseEntity<DemandeDTO> aviserSH(@PathVariable Long id, @RequestBody HistoriqueDTO historiqueDTO) {
        DemandeDTO demande = demandeService.aviserDemandeSH(id, historiqueDTO);
        return ResponseEntity.ok().body(demande);
    }


    @Operation(summary = "DRH Avise une demande", description = "DRH Avise une demande")
    @PostMapping("/avis-drh/{id}")
    public ResponseEntity<DemandeDTO> aviserDRH(@PathVariable Long id, @RequestBody HistoriqueDTO historiqueDTO) {
        DemandeDTO demande = demandeService.aviserDemandeDRH(id, historiqueDTO);
        return ResponseEntity.ok().body(demande);
    }

    @Operation(summary = "SG valide une demande", description = "SG valide une demande")
    @PostMapping("/valider-demande/{id}")
    public ResponseEntity<DemandeDTO> validerDemande(@PathVariable Long id, @RequestBody HistoriqueDTO historiqueDTO) {
        DemandeDTO demande = demandeService.validerDemande(id, historiqueDTO);
        return ResponseEntity.ok().body(demande);
    }

    @Operation(summary = "Rejeter d'une demande par DRH", description = "Rejet d'une demande par DRH")
    @PostMapping("/rejet-drh/{id}")
    public ResponseEntity<DemandeDTO> rejetDRH(@PathVariable Long id, @RequestBody HistoriqueDTO historiqueDTO) {
        DemandeDTO demande = demandeService.rejetDRH(id, historiqueDTO);
        return ResponseEntity.ok().body(demande);
    }

    @Operation(summary = "Rejeter d'une demande par SG", description = "Rejet d'une demande par SG")
    @PostMapping("/rejet-sg/{id}")
    public ResponseEntity<DemandeDTO> rejetSG(@PathVariable Long id, @RequestBody HistoriqueDTO historiqueDTO) {
        DemandeDTO demande = demandeService.rejetSG(id, historiqueDTO);
        return ResponseEntity.ok().body(demande);
    }

    @Operation(summary = "Supprime une demande via un ID", description = "Supprime une demande via un ID")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        demandeService.delete(id);
        return ResponseEntity
                .noContent()
                .build();

    }

    @Operation(summary = "Liste toutes mes demandes", description = "Liste de toutes mes demandes")
    @GetMapping("/list-page/mes-demandes/{matricule}")
    public ResponseEntity<List<DemandeDTO>> findMyDmds(Pageable pageable, @PathVariable String matricule) {
        Page<DemandeDTO> demandes = demandeService.findMyDmds(pageable, matricule);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), demandes);
        return new ResponseEntity<>(demandes.getContent(), headers, HttpStatus.OK);
    }

    @Operation(summary = "Liste toutes mes demandes agents", description = "Liste toutes les demandes de mes agents")
    @GetMapping("/list-page/demandes-agent/{matricule}")
    public ResponseEntity<List<DemandeDTO>> findMyAgentDmds(Pageable pageable, @PathVariable String matricule) {
        Page<DemandeDTO> demandes = demandeService.findMyAgentDmds(pageable, matricule);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), demandes);
        return new ResponseEntity<>(demandes.getContent(), headers, HttpStatus.OK);
    }

    @Operation(summary = "Liste toutes les demandes du ministere", description = "Liste toutes les demandes du ministere")
    @GetMapping("/list-page/demandes-ministere/{matricule}")
    public ResponseEntity<List<DemandeDTO>> findMinistereDmds(Pageable pageable, @PathVariable String matricule) {
        Page<DemandeDTO> demandes = demandeService.findMinistereDmds(pageable, matricule);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), demandes);
        return new ResponseEntity<>(demandes.getContent(), headers, HttpStatus.OK);
    }

    @Operation(summary = "Analyser une demande", description = "Analyser une demande")
    @PostMapping("/analyser/{id}")
    public ResponseEntity<DemandeDTO> analyser(@PathVariable Long id, @RequestBody HistoriqueDTO historiqueDTO) {
        DemandeDTO demande = demandeService.analyserDemande(id, historiqueDTO);
        return ResponseEntity.ok().body(demande);
    }

    @PostMapping(path = "/upload/{id}")
    @Transactional
    public ResponseEntity<Object> uploadActeDetachement(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        DemandeDTO demande = demandeService.get(id).orElseThrow();

        String fileName = fileService.storeFile(file, storageFolder, fileService.generateFileName(file, demande));
        demande.setUrlActe(storageFolder + fileName);
        demande.setStatut(EStatutDemande.CLOS);
        demandeService.save(demande);

        Historique historique = new Historique();
        historique.setDemande(demandeMapper.toEntity(demande));
        historique.setCircuit(circuitRepository.findByPosition("SAD"));
        historique.setDateAvis(new Date().toInstant());
        historiqueRepository.save(historique);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        // Récupérer la demande associée à l'ID
        DemandeDTO demande = demandeService.get(id).orElseThrow();

        // Récupérer le chemin du fichier à télécharger
        String filePath = demande.getUrlActe();
        System.out.println("Chemin du fichier à télécharger : " + filePath);

        // Charger le fichier à partir de son chemin local
        Resource resource = new FileSystemResource(filePath);

        // Vérifier si la ressource existe
        if (!resource.exists()) {
            throw new RuntimeException("Fichier non trouvé : " + filePath);
        }

        // Préparer la réponse pour le téléchargement
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
