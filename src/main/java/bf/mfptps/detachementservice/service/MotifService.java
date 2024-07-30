/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service;

import bf.mfptps.detachementservice.domain.Motif;
import bf.mfptps.detachementservice.service.dto.MotifDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface MotifService {

    MotifDTO create(MotifDTO motifDTO);

    MotifDTO update(MotifDTO motifDTO);

    Optional<MotifDTO> get(Long id);

    Page<MotifDTO> findAll(Pageable pageable);

    List<MotifDTO> findAllList();

    void delete(Long id);
}
