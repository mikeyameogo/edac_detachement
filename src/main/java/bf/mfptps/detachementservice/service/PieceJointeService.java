/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service;

import bf.mfptps.detachementservice.domain.PieceJointe;
import bf.mfptps.detachementservice.service.dto.DemandeDTO;
import bf.mfptps.detachementservice.service.dto.PieceJointeDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface PieceJointeService {

    PieceJointeDTO create(PieceJointeDTO pieceJointeDTO);

    PieceJointeDTO update(PieceJointeDTO pieceJointeDTO);

    Optional<PieceJointeDTO> get(Long id);

    Page<PieceJointeDTO> findAll(Pageable pageable);

    List<PieceJointeDTO> findAllList();

    List<PieceJointe> findAllByDemande(Long idDemande);

    void delete(Long id);
}
