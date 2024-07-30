/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service;

import bf.mfptps.detachementservice.service.dto.TypeDemandeDTO;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface TypeDemandeService {

    TypeDemandeDTO create(TypeDemandeDTO typeDemandeDTO);

    TypeDemandeDTO update(TypeDemandeDTO typeDemandeDTO);

    Optional<TypeDemandeDTO> get(Long id);

    Page<TypeDemandeDTO> findAll(Pageable pageable);

    List<TypeDemandeDTO> findAllList();


    void delete(Long code);
}
