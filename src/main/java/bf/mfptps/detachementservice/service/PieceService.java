/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service;

import bf.mfptps.detachementservice.service.dto.PieceDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface PieceService {

    PieceDTO create(PieceDTO pieceDTO);

    PieceDTO update(PieceDTO pieceDTO);

    Optional<PieceDTO> get(Long id);

    Page<PieceDTO> findAll(Pageable pageable);

    List<PieceDTO> findAllList();

    void delete(Long id);
}
