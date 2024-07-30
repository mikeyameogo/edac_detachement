package bf.mfptps.detachementservice.repository;

import bf.mfptps.detachementservice.domain.Circuit;
import bf.mfptps.detachementservice.domain.Visa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Zak TEGUERA on 29/09/2023.
 * <teguera.zakaria@gmail.com>
 */
@Repository
public interface CircuitRepository  extends JpaRepository<Circuit, Long> {

    Circuit findByPosition(String position);

}
