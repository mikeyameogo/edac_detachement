package bf.mfptps.detachementservice.service;

import bf.mfptps.detachementservice.domain.Ministere;
import bf.mfptps.detachementservice.domain.Structure;
import bf.mfptps.detachementservice.service.dto.ChangeMinistereDTO;
import bf.mfptps.detachementservice.service.dto.StructureDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StructureService {

    Structure create(StructureDTO structure);

    Structure update(Structure structure);

    Optional<Structure> get(long id);

    Ministere getMinistereByStructureId(long idStructure);

    Page<Structure> findAll(Pageable pageable);

    List<Structure> findAllList();

    void delete(Long code);

    Structure changementMinistere(ChangeMinistereDTO changeMinistereDTO);
}
