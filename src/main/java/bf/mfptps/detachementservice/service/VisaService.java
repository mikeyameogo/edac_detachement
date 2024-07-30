/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service;

import bf.mfptps.detachementservice.domain.Visa;
import bf.mfptps.detachementservice.service.dto.VisaDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface VisaService {

    VisaDTO create(VisaDTO visaDTO);

    VisaDTO update(VisaDTO visaDTO);

    Optional<VisaDTO> get(Long id);

    Page<VisaDTO> findAll(Pageable pageable);

    List<VisaDTO> findAllList();

    void delete(Long id);
}
