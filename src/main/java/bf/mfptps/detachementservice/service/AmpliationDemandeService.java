/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service;

import bf.mfptps.detachementservice.service.dto.AmpliationDemandeDTO;
import bf.mfptps.detachementservice.service.dto.VisaDemandeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AmpliationDemandeService {

    AmpliationDemandeDTO create(AmpliationDemandeDTO ampliationDemandeDTO);

    AmpliationDemandeDTO update(AmpliationDemandeDTO ampliationDemandeDTO);

    Optional<AmpliationDemandeDTO> get(Long id);

    Page<AmpliationDemandeDTO> findAll(Pageable pageable);

    List<AmpliationDemandeDTO> findAllByDemandeId(Long demandeId);

    List<AmpliationDemandeDTO> findAllList();

    void delete(Long id);
}
