/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service;

import bf.mfptps.detachementservice.domain.Ampliation;
import bf.mfptps.detachementservice.service.dto.AmpliationDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface AmpliationService {

    AmpliationDTO create(AmpliationDTO ampliationDTO);

    AmpliationDTO update(AmpliationDTO ampliationDTO);

    Optional<AmpliationDTO> get(Long id);

    Page<AmpliationDTO> findAll(Pageable pageable);

    List<AmpliationDTO> findAllList();

    void delete(Long id);
}
