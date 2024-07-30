package bf.mfptps.detachementservice.repository;

import bf.mfptps.detachementservice.domain.Duree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Zak TEGUERA on 09/10/2023.
 * <teguera.zakaria@gmail.com>
 */
@Repository
public interface DureeRepository extends JpaRepository<Duree, Long> {

}
