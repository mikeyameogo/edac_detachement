package bf.mfptps.detachementservice.service;

import bf.mfptps.detachementservice.domain.MinistereStructure;
import bf.mfptps.detachementservice.service.dto.MinistereStructureDTO;
import bf.mfptps.detachementservice.service.dto.StructureDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MinistereStructureService {

    MinistereStructure create(MinistereStructureDTO ministereSDTO);

    MinistereStructure update(MinistereStructure ministereS);

    Optional<MinistereStructure> get(Long id);

    Page<MinistereStructure> findAll(Pageable pageable);

    void delete(Long code);

    Page<StructureDTO> findAllStructureByMinistere(long ministereId, Pageable pageable);

    List<StructureDTO> findListStructureByMinistere(long ministereId);
}
