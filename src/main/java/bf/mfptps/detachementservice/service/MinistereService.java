package bf.mfptps.detachementservice.service;

import bf.mfptps.detachementservice.domain.Ministere;
import bf.mfptps.detachementservice.service.dto.MinistereDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface MinistereService {

    Ministere create(MinistereDTO ministere);

    Ministere update(Ministere ministere);

    Optional<Ministere> get(String code);

    Optional<Ministere> get(Long id);

    Page<Ministere> findAll(Pageable pageable);

    List<Ministere> findAllList();

    void delete(Long code);

}
