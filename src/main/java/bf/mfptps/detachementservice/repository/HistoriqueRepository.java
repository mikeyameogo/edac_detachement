package bf.mfptps.detachementservice.repository;

import bf.mfptps.detachementservice.domain.Historique;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by Zak TEGUERA on 29/09/2023.
 * <teguera.zakaria@gmail.com>
 */
@Repository
public interface HistoriqueRepository extends JpaRepository<Historique, Long> {

    Optional<Historique> findById(Long id);

    List<Historique> findByDemandeId(Long idDemande);

    //demande validee par SG
    @Query("SELECT h FROM Historique h, Demande d, Circuit c "
            + "WHERE d.id = :id AND d.id = h.demande.id "
            + "AND c.position = 'SG' AND h.circuit.id = c.id "
            + "AND (h.avis = 'CONFORME' OR h.avis = 'FAVORABLE')")
    Optional<Historique> findByIdAndValideSG(Long id);

    //les avis de SH
    @Query("SELECT h FROM Historique h, Demande d, Circuit c "
            + "WHERE d.id = :id AND d.id = h.demande.id "
            + "AND c.position = 'SH' AND h.circuit.id = c.id "
            + "ORDER BY h.dateAvis DESC")
    List<Historique> findAvisSHSortByDateAvis(Long id);

    //les avis de DRH
    @Query("SELECT h FROM Historique h, Demande d, Circuit c "
            + "WHERE d.id = :id AND d.id = h.demande.id "
            + "AND c.position = 'DRH' AND h.circuit.id = c.id "
            + "ORDER BY h.dateAvis DESC")
    List<Historique> findAvisDRHSortByDateAvis(Long id);

    //les avis de SG
    @Query("SELECT h FROM Historique h, Demande d, Circuit c "
            + "WHERE d.id = :id AND d.id = h.demande.id "
            + "AND c.position = 'SG' AND h.circuit.id = c.id "
            + "ORDER BY h.dateAvis DESC")
    List<Historique> findAvisSGSortByDateAvis(Long id);
}
