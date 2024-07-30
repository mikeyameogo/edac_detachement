package bf.mfptps.detachementservice.repository;

import bf.mfptps.detachementservice.domain.AmpliationDemande;
import bf.mfptps.detachementservice.domain.VisaDemande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Zak TEGUERA on 19/12/2023.
 * <teguera.zakaria@gmail.com>
 */
@Repository
public interface AmpliationDemandeRepository extends JpaRepository<AmpliationDemande, Long> {
    List<AmpliationDemande> findAllByDemandeId(Long demandeId);

}
