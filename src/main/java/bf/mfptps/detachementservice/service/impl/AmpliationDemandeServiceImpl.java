/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service.impl;

import bf.mfptps.detachementservice.domain.AmpliationDemande;
import bf.mfptps.detachementservice.repository.AmpliationDemandeRepository;
import bf.mfptps.detachementservice.repository.TypeDemandeRepository;
import bf.mfptps.detachementservice.service.AmpliationDemandeService;
import bf.mfptps.detachementservice.service.dto.AmpliationDemandeDTO;
import bf.mfptps.detachementservice.service.dto.VisaDemandeDTO;
import bf.mfptps.detachementservice.service.mapper.AmpliationDemandeMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
public class AmpliationDemandeServiceImpl implements AmpliationDemandeService {

    private final AmpliationDemandeRepository ampliationDemandeRepository;

    private final TypeDemandeRepository typeDemandeRepository;

    private final AmpliationDemandeMapper ampliationDemandeMapper;

    public AmpliationDemandeServiceImpl(AmpliationDemandeRepository ampliationDemandeRepository, TypeDemandeRepository typeDemandeRepository, AmpliationDemandeMapper ampliationDemandeMapper) {
        this.ampliationDemandeRepository = ampliationDemandeRepository;
        this.typeDemandeRepository = typeDemandeRepository;
        this.ampliationDemandeMapper = ampliationDemandeMapper;
    }

    @Transactional
    @Override
    public AmpliationDemandeDTO create(AmpliationDemandeDTO ampliationDemandeDTO) {
        log.info("creation d'une ampliationDemande : {}", ampliationDemandeDTO);
        AmpliationDemande ampliationDemande = ampliationDemandeMapper.toEntity(ampliationDemandeDTO);
        return ampliationDemandeMapper.toDto(ampliationDemandeRepository.save(ampliationDemande));
    }

    @Override
    public AmpliationDemandeDTO update(AmpliationDemandeDTO ampliationDemandeDTO) {
        log.info("update d'une ampliationDemande : {}", ampliationDemandeDTO);
        AmpliationDemande ampliationDemande = ampliationDemandeMapper.toEntity(ampliationDemandeDTO);
        return ampliationDemandeMapper.toDto(ampliationDemandeRepository.save(ampliationDemande));
    }

    @Override
    public Optional<AmpliationDemandeDTO> get(Long id) {
        log.info("consultation d'une ampliationDemande : {}", id);
        return Optional.ofNullable(ampliationDemandeMapper.toDto(ampliationDemandeRepository.findById(id).orElseThrow()));
    }

    @Override
    public Page<AmpliationDemandeDTO> findAll(Pageable pageable) {
        log.info("liste paginée des ampliationDemandes");
        return ampliationDemandeRepository.findAll(pageable).map(ampliationDemandeMapper::toDto);
    }

    @Override
    public List<AmpliationDemandeDTO> findAllList() {
        log.info("liste des ampliationDemandes");
        return ampliationDemandeRepository.findAll().stream().map(ampliationDemandeMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<AmpliationDemandeDTO> findAllByDemandeId(Long demandeId) {
        log.info("liste des ampliationDemandes de la demande : {}", demandeId);
        return ampliationDemandeRepository.findAllByDemandeId(demandeId).stream().map(ampliationDemandeMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        log.info("suppression d'une ampliationDemande : {}", id);
        ampliationDemandeRepository.deleteById(id);
    }
}
