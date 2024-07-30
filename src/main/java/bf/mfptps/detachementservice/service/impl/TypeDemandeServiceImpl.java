/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service.impl;

import bf.mfptps.detachementservice.domain.TypeDemande;
import bf.mfptps.detachementservice.repository.TypeDemandeRepository;
import bf.mfptps.detachementservice.service.TypeDemandeService;
import bf.mfptps.detachementservice.service.dto.TypeDemandeDTO;
import bf.mfptps.detachementservice.service.mapper.TypeDemandeMapper;
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
public class TypeDemandeServiceImpl implements TypeDemandeService {

    private final TypeDemandeRepository typeDemandeRepository;

    private final TypeDemandeMapper typeDemandeMapper;

    public TypeDemandeServiceImpl(TypeDemandeRepository typeDemandeRepository, TypeDemandeMapper typeDemandeMapper) {
        this.typeDemandeRepository = typeDemandeRepository;
        this.typeDemandeMapper = typeDemandeMapper;
    }

    @Override
    public TypeDemandeDTO create(TypeDemandeDTO typeDemandeDTO) {
        log.info("creation d'un type de demande : {}", typeDemandeDTO);
        TypeDemande result = typeDemandeMapper.toEntity(typeDemandeDTO);
        return typeDemandeMapper.toDto(typeDemandeRepository.save(result));
    }

    @Override
    public TypeDemandeDTO update(TypeDemandeDTO typeDemandeDTO) {
        log.info("update d'un type de demande : {}", typeDemandeDTO);
        TypeDemande typeDemande = typeDemandeMapper.toEntity(typeDemandeDTO);
        return typeDemandeMapper.toDto(typeDemandeRepository.save(typeDemande));
    }

    @Override
    public Optional<TypeDemandeDTO> get(Long id) {
        log.info("consultation d'un type de demande : {}", id);
        return Optional.ofNullable(typeDemandeMapper.toDto(typeDemandeRepository.findById(id).orElseThrow()));
    }

    @Override
    public Page<TypeDemandeDTO> findAll(Pageable pageable) {
        log.info("liste paginée des types de demande");
        return typeDemandeRepository.findAll(pageable).map(typeDemandeMapper::toDto);
    }

    @Override
    public List<TypeDemandeDTO> findAllList() {
        log.info("liste des types de demande");
        return typeDemandeRepository.findAll().stream().map(typeDemandeMapper::toDto).collect(Collectors.toList());
    }


    @Override
    public void delete(Long id) {
        log.info("suppression d'un type de demande : {}", id);
        typeDemandeRepository.deleteById(id);
    }

}
