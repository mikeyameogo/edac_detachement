/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service;

import bf.mfptps.detachementservice.service.dto.VisaDemandeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface VisaDemandeService {

    VisaDemandeDTO create(VisaDemandeDTO visaDemandeDTO);

    VisaDemandeDTO update(VisaDemandeDTO visaDemandeDTO);

    Optional<VisaDemandeDTO> get(Long id);

    Page<VisaDemandeDTO> findAll(Pageable pageable);

    List<VisaDemandeDTO> findAllList();

    List<VisaDemandeDTO> findAllByDemandeId(Long demandeId);

    void delete(Long id);
}
