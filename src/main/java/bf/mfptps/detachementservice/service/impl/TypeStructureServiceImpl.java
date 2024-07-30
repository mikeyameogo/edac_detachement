/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service.impl;

import bf.mfptps.detachementservice.domain.TypeStructure;
import bf.mfptps.detachementservice.repository.TypeStructureRepository;
import bf.mfptps.detachementservice.service.TypeStructureService;
import bf.mfptps.detachementservice.service.dto.TypeStructureDTO;
import bf.mfptps.detachementservice.service.mapper.TypeStructureMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@Service
public class TypeStructureServiceImpl implements TypeStructureService {

    private final TypeStructureRepository typeStructureRepository;

    private final TypeStructureMapper typeStructureMapper;

    public TypeStructureServiceImpl(TypeStructureRepository typeStructureRepository, TypeStructureMapper typeStructureMapper) {
        this.typeStructureRepository = typeStructureRepository;
        this.typeStructureMapper = typeStructureMapper;
    }

    @Override
    public TypeStructure create(TypeStructureDTO typeStructureDTO) {
        log.info("creation d'un type de structure : {}", typeStructureDTO);
        TypeStructure result = typeStructureMapper.toEntity(typeStructureDTO);
        return typeStructureRepository.save(result);
    }

    @Override
    public TypeStructure update(TypeStructure typeStructure) {
        log.info("update d'un type de structure : {}", typeStructure);
        return typeStructureRepository.save(typeStructure);
    }

    @Override
    public Optional<TypeStructure> get(Long id) {
        log.info("consultation d'un type de structure : {}", id);
        return typeStructureRepository.findById(id);
    }

    @Override
    public Page<TypeStructure> findAll(Pageable pageable) {
        log.info("liste paginée des types de structure");
        return typeStructureRepository.findAll(pageable);
    }

    @Override
    public List<TypeStructure> findAllList() {
        log.info("liste des types de structure");
        return typeStructureRepository.findAll().stream().collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        log.info("suppression d'un type de structure : {}", id);
        typeStructureRepository.deleteById(id);
    }

}
