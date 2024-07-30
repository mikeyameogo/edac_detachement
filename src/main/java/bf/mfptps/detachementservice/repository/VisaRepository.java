package bf.mfptps.detachementservice.repository;

import bf.mfptps.detachementservice.domain.Historique;
import bf.mfptps.detachementservice.domain.Visa;
import bf.mfptps.detachementservice.domain.VisaDemande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Zak TEGUERA on 29/09/2023.
 * <teguera.zakaria@gmail.com>
 */
@Repository
public interface VisaRepository extends JpaRepository<Visa, Long> {
}
