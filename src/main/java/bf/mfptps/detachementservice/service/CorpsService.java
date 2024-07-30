/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service;

import bf.mfptps.detachementservice.domain.Corps;
import bf.mfptps.detachementservice.service.dto.CorpsDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface CorpsService {

    CorpsDTO create(CorpsDTO corpsDTO);

    CorpsDTO update(CorpsDTO corpsDTO);

    Optional<CorpsDTO> get(String code);

    Optional<CorpsDTO> get(Long id);

    Page<CorpsDTO> findAll(Pageable pageable);

    List<CorpsDTO> findAllList();

    void delete(Long id);
}
