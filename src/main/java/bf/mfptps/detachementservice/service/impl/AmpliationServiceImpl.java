/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service.impl;

import bf.mfptps.detachementservice.domain.Ampliation;
import bf.mfptps.detachementservice.domain.TypeDemande;
import bf.mfptps.detachementservice.exception.CustomException;
import bf.mfptps.detachementservice.repository.AmpliationRepository;
import bf.mfptps.detachementservice.repository.TypeDemandeRepository;
import bf.mfptps.detachementservice.service.AmpliationService;
import bf.mfptps.detachementservice.service.dto.AmpliationDTO;
import bf.mfptps.detachementservice.service.mapper.AmpliationMapper;
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
public class AmpliationServiceImpl implements AmpliationService {

    private final AmpliationRepository ampliationRepository;

    private final TypeDemandeRepository typeDemandeRepository;

    private final AmpliationMapper ampliationMapper;

    public AmpliationServiceImpl(AmpliationRepository ampliationRepository, TypeDemandeRepository typeDemandeRepository, AmpliationMapper ampliationMapper) {
        this.ampliationRepository = ampliationRepository;
        this.typeDemandeRepository = typeDemandeRepository;
        this.ampliationMapper = ampliationMapper;
    }

    @Override
    public AmpliationDTO create(AmpliationDTO ampliationDTO) {
        log.info("creation d'une ampliation : {}", ampliationDTO);
//        TypeDemande typeDemande = typeDemandeRepository
//                .findById(ampliationDTO.getTypeDemandeDTO().getId())
//                .orElseThrow(() -> new CustomException("Le type de demande " + ampliationDTO.getTypeDemandeDTO().getId() + " est inexistant."));
        //????????????? verifier que la table intermediaire est updated apres le save ci-dessous
        Ampliation ampliation = ampliationMapper.toEntity(ampliationDTO);
        return ampliationMapper.toDto(ampliationRepository.save(ampliation));
    }

    @Override
    public AmpliationDTO update(AmpliationDTO ampliationDTO) {
        log.info("update d'une ampliation : {}", ampliationDTO);
        Ampliation ampliation = ampliationMapper.toEntity(ampliationDTO);
        return ampliationMapper.toDto(ampliationRepository.save(ampliation));
    }

    @Override
    public Optional<AmpliationDTO> get(Long id) {
        log.info("consultation d'une ampliation : {}", id);
        return Optional.ofNullable(ampliationMapper.toDto(ampliationRepository.findById(id).orElseThrow()));
    }

    @Override
    public Page<AmpliationDTO> findAll(Pageable pageable) {
        log.info("liste paginée des ampliations");
        return ampliationRepository.findAll(pageable).map(ampliationMapper::toDto);
    }

    @Override
    public List<AmpliationDTO> findAllList() {
        log.info("liste des ampliations");
        return ampliationRepository.findAll().stream().map(ampliationMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        log.info("suppression d'une ampliation : {}", id);
        ampliationRepository.deleteById(id);
    }
}
