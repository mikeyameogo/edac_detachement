package bf.mfptps.detachementservice.service.impl;

import bf.mfptps.detachementservice.domain.Ministere;
import bf.mfptps.detachementservice.repository.MinistereRepository;
import bf.mfptps.detachementservice.service.MinistereService;
import bf.mfptps.detachementservice.service.dto.MinistereDTO;
import bf.mfptps.detachementservice.service.mapper.MinistereMapper;
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
public class MinistereServiceImpl implements MinistereService {

    private final MinistereRepository ministereRepository;
    private final MinistereMapper ministereMapper;

    public MinistereServiceImpl(MinistereRepository ministereRepository, MinistereMapper ministereMapper) {
        this.ministereRepository = ministereRepository;
        this.ministereMapper = ministereMapper;
    }

    @Override
    public Ministere create(MinistereDTO ministereDTO) {
        log.info("creation d'un ministere : {}", ministereDTO);
        Ministere ministere = ministereMapper.toEntity(ministereDTO);
        return ministereRepository.save(ministere);
    }

    @Override
    public Ministere update(Ministere ministere) {
        log.info("update d'un ministere : {}", ministere);
        return ministereRepository.save(ministere);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Ministere> get(String code) {
        log.info("consultation d'un ministere de code : {}", code);
        return ministereRepository.findByCode(code);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Ministere> findAll(Pageable pageable) {
        log.info("liste paginée des ministeres");
        return ministereRepository.findAll(pageable);
    }

    @Override
    public void delete(Long id) {
        log.info("suppression d'un ministere  de code : {}", id);
        ministereRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Ministere> get(Long id) {
        log.info("consultation d'un ministere : {}", id);
        return ministereRepository.findById(id);
    }

    @Override
    public List<Ministere> findAllList() {
        log.info("liste des ministere");
        return ministereRepository.findAll().stream().collect(Collectors.toList());
    }

}
