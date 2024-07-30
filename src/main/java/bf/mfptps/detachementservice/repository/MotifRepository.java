package bf.mfptps.detachementservice.repository;

import bf.mfptps.detachementservice.domain.Motif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Zak TEGUERA on 29/09/2023.
 * <teguera.zakaria@gmail.com>
 */
@Repository
public interface MotifRepository  extends JpaRepository<Motif, Long> {
}
