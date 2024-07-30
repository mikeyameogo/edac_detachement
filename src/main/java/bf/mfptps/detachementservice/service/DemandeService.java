/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service;

import bf.mfptps.detachementservice.service.dto.DemandeDTO;
import bf.mfptps.detachementservice.service.dto.HistoriqueDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface DemandeService {

    DemandeDTO save(DemandeDTO demandeDTO);

    DemandeDTO create(DemandeDTO demandeDTO);

    DemandeDTO update(DemandeDTO demandeDTO);

    Optional<DemandeDTO> get(Long id);

    Page<DemandeDTO> findAll(Pageable pageable);

    List<DemandeDTO> findAllList();

    List<DemandeDTO> findAllByTypedemande(Long idTypeDemande);

    List<DemandeDTO> findAllByAgent(Long idAgent);

    DemandeDTO receptionnerDemande(Long id);

    DemandeDTO analyserDemande(Long id, HistoriqueDTO historiqueDTO);

    DemandeDTO aviserDemandeSH(Long id, HistoriqueDTO historiqueDTO);

    DemandeDTO aviserDemandeDRH(Long id, HistoriqueDTO historiqueDTO);

    DemandeDTO validerDemande(Long id, HistoriqueDTO historiqueDTO);

    DemandeDTO rejetDRH(Long id, HistoriqueDTO historiqueDTO); // niveau DRH

    DemandeDTO rejetSG(Long id, HistoriqueDTO historiqueDTO); // niveau SG

    void delete(Long id);

    Page<DemandeDTO> findMyDmds(Pageable pageable, String matricule);

    Page<DemandeDTO> findMyAgentDmds(Pageable pageable, String matricule);

    Page<DemandeDTO> findMinistereDmds(Pageable pageable, String matricule);

    DemandeDTO imputerDemande(Long id, String matriculeImputation);//permet au CST d’imputer une demande d’acte au rédacteur

    DemandeDTO receptionnerDemandeValidee(Long id);//permet au Secrétaire_DRH de constituer le fond du dossier physique pour l’élaboration de l’acte demandé

    DemandeDTO abandonnerDemande(Long id);//permet d’abandonner une demande d’acte dont le projet n’a pas encore été élaboré
}
