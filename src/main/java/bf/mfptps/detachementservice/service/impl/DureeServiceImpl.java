package bf.mfptps.detachementservice.service.impl;

import bf.mfptps.detachementservice.domain.Duree;
import bf.mfptps.detachementservice.repository.DureeRepository;
import bf.mfptps.detachementservice.service.DureeService;
import bf.mfptps.detachementservice.service.dto.DureeDTO;
import bf.mfptps.detachementservice.service.mapper.DureeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Zak TEGUERA on 09/10/2023.
 * <teguera.zakaria@gmail.com>
 */
@Slf4j
@Service
public class DureeServiceImpl implements DureeService {
    private final DureeRepository dureeRepository;

    private final DureeMapper dureeMapper;

    public DureeServiceImpl(DureeRepository dureeRepository, DureeMapper dureeMapper) {
        this.dureeRepository = dureeRepository;
        this.dureeMapper = dureeMapper;
    }

    @Override
    public Duree create(DureeDTO dureeDTO) {
        log.info("creation d'un type de structure : {}", dureeDTO);
        Duree result = dureeMapper.toEntity(dureeDTO);
        return dureeRepository.save(result);
    }

    @Override
    public Duree update(DureeDTO dureeDTO) {
        log.info("update d'un type de structure : {}", dureeDTO);
        Duree duree = dureeMapper.toEntity(dureeDTO);
        return dureeRepository.save(duree);
    }

    @Override
    public Optional<Duree> get(Long id) {
        log.info("consultation d'un type de structure : {}", id);
        return dureeRepository.findById(id);
    }

    @Override
    public Page<Duree> findAll(Pageable pageable) {
        log.info("liste paginée des types de structure");
        return dureeRepository.findAll(pageable);
    }

    @Override
    public List<Duree> findAllList() {
        log.info("liste des types de structure");
        return new ArrayList<>(dureeRepository.findAll());
    }

    @Override
    public void delete(Long id) {
        log.info("suppression d'un type de structure : {}", id);
        dureeRepository.deleteById(id);
    }

}
