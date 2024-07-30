/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service;

import bf.mfptps.detachementservice.domain.TypeStructure;
import bf.mfptps.detachementservice.service.dto.TypeStructureDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface TypeStructureService {

    TypeStructure create(TypeStructureDTO typeStructureDTO);

    TypeStructure update(TypeStructure typeStructure);

    Optional<TypeStructure> get(Long id);

    Page<TypeStructure> findAll(Pageable pageable);

    List<TypeStructure> findAllList();

    void delete(Long id);
}
