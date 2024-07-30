package bf.mfptps.detachementservice.service;

import bf.mfptps.detachementservice.domain.Duree;
import bf.mfptps.detachementservice.service.dto.DureeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Created by Zak TEGUERA on 09/10/2023.
 * <teguera.zakaria@gmail.com>
 */
public interface DureeService {

    Duree create(DureeDTO dureeDTO);

    Duree update(DureeDTO dureeDTO);

    Optional<Duree> get(Long id);

    Page<Duree> findAll(Pageable pageable);

    List<Duree> findAllList();

    void delete(Long id);
    
}
