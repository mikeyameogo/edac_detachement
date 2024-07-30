/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import bf.mfptps.detachementservice.service.dto.ActeDTO;

/**
 * ????????????????? A REVOIR LE CONTENU
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface ActeService {

    ActeDTO create(ActeDTO acteDTO);

    ActeDTO update(ActeDTO acteDTO);

    Optional<ActeDTO> get(Long id);

    Page<ActeDTO> findAll(Pageable pageable);

    List<ActeDTO> findAllList();

    void delete(Long id);
    
  
    /**
     * chargeur du fichier final de l'acte signé et scanné
     * @param acteFile
     * @param dataId
     * @param disponibilite
     * @return
     */    
    String uploadActe(MultipartFile acteFile, Long dataId, boolean disponibilite);
    
   /**
    * 
    * @param dataId
    * @param disponibilite
    * @return
    */
    ByteArrayResource downloadActe(Long dataId, boolean disponibilite);
    
    /**
     * 
     * @param dataId
     * @param disponibilite
     * @return
     */
    String signerProjetArrete(Long dataId, boolean disponibilite);
    
    
}
