/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service.impl;

import bf.mfptps.detachementservice.domain.TypeDemande;
import bf.mfptps.detachementservice.domain.Visa;
import bf.mfptps.detachementservice.exception.CustomException;
import bf.mfptps.detachementservice.repository.TypeDemandeRepository;
import bf.mfptps.detachementservice.repository.VisaRepository;
import bf.mfptps.detachementservice.service.VisaService;
import bf.mfptps.detachementservice.service.dto.VisaDTO;
import bf.mfptps.detachementservice.service.mapper.VisaMapper;
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
public class VisaServiceImpl implements VisaService {

    private final VisaRepository visaRepository;

    private final TypeDemandeRepository typeDemandeRepository;

    private final VisaMapper visaMapper;

    public VisaServiceImpl(VisaRepository visaRepository, TypeDemandeRepository typeDemandeRepository, VisaMapper visaMapper) {
        this.visaRepository = visaRepository;
        this.typeDemandeRepository = typeDemandeRepository;
        this.visaMapper = visaMapper;
    }

    @Override
    public VisaDTO create(VisaDTO visaDTO) {
        log.info("creation d'un visa : {}", visaDTO);
//        TypeDemande typeDemande = typeDemandeRepository
//                .findById(visaDTO.getTypeDemandeDTO().getId())
//                .orElseThrow(() -> new CustomException("Le type de demande " + visaDTO.getTypeDemandeDTO().getId() + " est inexistant."));
        //????????????? verifier que la table intermediaire est updated apres le save ci-dessous
        Visa visa = visaMapper.toEntity(visaDTO);
        return visaMapper.toDto(visaRepository.save(visa));
    }

    @Override
    public VisaDTO update(VisaDTO visaDTO) {
        log.info("update d'un visa : {}", visaDTO);
        Visa visa = visaMapper.toEntity(visaDTO);
        return visaMapper.toDto(visaRepository.save(visa));
    }

    @Override
    public Optional<VisaDTO> get(Long id) {
        log.info("consultation d'un visa : {}", id);
        return Optional.ofNullable(visaMapper.toDto(visaRepository.findById(id).orElseThrow()));
    }

    @Override
    public Page<VisaDTO> findAll(Pageable pageable) {
        log.info("liste paginée des visas");
        return visaRepository.findAll(pageable).map(visaMapper::toDto);
    }

    @Override
    public List<VisaDTO> findAllList() {
        log.info("liste des visas");
        return visaRepository.findAll().stream().map(visaMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        log.info("suppression d'un visa : {}", id);
        visaRepository.deleteById(id);
    }

}
