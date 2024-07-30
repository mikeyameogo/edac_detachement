/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service;

import bf.mfptps.detachementservice.service.dto.CircuitDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface CircuitService {

    CircuitDTO create(CircuitDTO circuitDTO);

    CircuitDTO update(CircuitDTO circuitDTO);

    Optional<CircuitDTO> getById(Long id);

    Page<CircuitDTO> findAll(Pageable pageable);

    List<CircuitDTO> findAllList();

    void delete(Long id);
}
