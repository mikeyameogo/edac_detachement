package bf.mfptps.detachementservice.service;

import bf.mfptps.detachementservice.domain.Historique;
import bf.mfptps.detachementservice.service.dto.HistoriqueDTO;
import java.util.List;
import java.util.Optional;

import bf.mfptps.detachementservice.service.dto.PieceJointeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface HistoriqueService {

    HistoriqueDTO create(HistoriqueDTO historiqueDTO);

    HistoriqueDTO update(HistoriqueDTO historiqueDTO);

    Optional<HistoriqueDTO> get(Long id);

    Page<HistoriqueDTO> findAll(Pageable pageable);

    List<HistoriqueDTO> findAllList();

    List<HistoriqueDTO> findAllByDemande(Long idDemande);

    void delete(Long id);

}
