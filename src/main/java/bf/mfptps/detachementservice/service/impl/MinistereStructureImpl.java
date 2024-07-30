package bf.mfptps.detachementservice.service.impl;

import bf.mfptps.detachementservice.domain.MinistereStructure;
import bf.mfptps.detachementservice.repository.MinistereStructureRepository;
import bf.mfptps.detachementservice.service.MinistereStructureService;
import bf.mfptps.detachementservice.service.dto.MinistereStructureDTO;
import bf.mfptps.detachementservice.service.dto.StructureDTO;
import bf.mfptps.detachementservice.service.mapper.MinistereStructureMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@Service
@Transactional
public class MinistereStructureImpl implements MinistereStructureService {

    private final MinistereStructureRepository ministereStructureRepository;
    private final MinistereStructureMapper ministereStructureMapper;

    public MinistereStructureImpl(MinistereStructureRepository ministereSRepository,
            MinistereStructureMapper ministereStructureMapper) {
        this.ministereStructureRepository = ministereSRepository;
        this.ministereStructureMapper = ministereStructureMapper;
    }

    @Override
    public MinistereStructure create(MinistereStructureDTO ministereSDTO) {
        log.info("creation d'un MinistereStructure : {}", ministereSDTO);
        return ministereStructureRepository.save(ministereStructureMapper.toEntity(ministereSDTO));
    }

    @Override
    public MinistereStructure update(MinistereStructure ministereS) {
        log.info("update d'un MinistereStructure : {}", ministereS);
        return ministereStructureRepository.save(ministereS);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MinistereStructure> get(Long id) {
        log.info("consultation d'un MinistereStructure : {}", id);
        return ministereStructureRepository.findById(id);
    }

    @Override
    public Page<MinistereStructure> findAll(Pageable pageable) {
        log.info("liste paginée des MinistereStructures");
        return ministereStructureRepository.findAllByStatutIsTrue(pageable);
    }

    @Override
    public Page<StructureDTO> findAllStructureByMinistere(long ministereId, Pageable pageable) {
        log.info("liste paginée des structures d'un Ministere donnée : {}", ministereId);
        Page<StructureDTO> responseMapped = ministereStructureRepository
                .findByMinistereIdAndStatutIsTrue(ministereId, pageable).map(ministereStructureMapper::toStructureDTO);
        return responseMapped;
    }

    @Override
    public List<StructureDTO> findListStructureByMinistere(long ministereId) {
        log.info("liste des structures actives d'un Ministere donnée : {}", ministereId);
        List<StructureDTO> responseMapped = ministereStructureRepository
                .findByMinistereIdAndStatutIsTrue(ministereId)
                .stream()
                .map(ministereStructureMapper::toStructureDTO)
                .collect(Collectors.toList());

        return responseMapped;
    }

    @Override
    public void delete(Long code) {
        log.info("suppression d'un MinistereStructure : {}", code);
        ministereStructureRepository.deleteById(code);
    }
}
