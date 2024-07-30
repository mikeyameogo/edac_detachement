package bf.mfptps.detachementservice.repository;

import bf.mfptps.detachementservice.domain.Demande;
import bf.mfptps.detachementservice.domain.PieceJointe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Zak TEGUERA on 29/09/2023.
 * <teguera.zakaria@gmail.com>
 */
@Repository
public interface PieceJointeRepository  extends JpaRepository<PieceJointe, Long> {
    List<PieceJointe> findByDemandeId(Long idDemande);
}
