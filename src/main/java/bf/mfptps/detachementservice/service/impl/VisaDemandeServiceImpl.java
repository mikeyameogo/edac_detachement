/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service.impl;

import bf.mfptps.detachementservice.domain.Demande;
import bf.mfptps.detachementservice.domain.TypeDemande;
import bf.mfptps.detachementservice.domain.VisaDemande;
import bf.mfptps.detachementservice.repository.DemandeRepository;
import bf.mfptps.detachementservice.repository.TypeDemandeRepository;
import bf.mfptps.detachementservice.repository.VisaDemandeRepository;
import bf.mfptps.detachementservice.service.VisaDemandeService;
import bf.mfptps.detachementservice.service.dto.VisaDemandeDTO;
import bf.mfptps.detachementservice.service.mapper.VisaDemandeMapper;
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
public class VisaDemandeServiceImpl implements VisaDemandeService {

    private final VisaDemandeRepository visaDemandeRepository;
    private final TypeDemandeRepository typeDemandeRepository;
    private final DemandeRepository demandeRepository;

    private final VisaDemandeMapper visaDemandeMapper;

    public VisaDemandeServiceImpl(VisaDemandeRepository visaDemandeRepository,
                                  VisaDemandeMapper visaDemandeMapper,
                                  DemandeRepository demandeRepository,
                                  TypeDemandeRepository typeDemandeRepository) {
        this.visaDemandeRepository = visaDemandeRepository;
        this.demandeRepository = demandeRepository;
        this.typeDemandeRepository = typeDemandeRepository;
        this.visaDemandeMapper = visaDemandeMapper;
    }

    @Transactional
    @Override
    public VisaDemandeDTO create(VisaDemandeDTO visaDemandeDTO) {
        log.info("creation d'un visaDemande : {}", visaDemandeDTO);
//        TypeDemande typeDemande = typeDemandeRepository
//                .findById(visaDemandeDTO.getTypeDemandeDTO().getId())
//                .orElseThrow(() -> new CustomException("Le type de demande " + visaDemandeDTO.getTypeDemandeDTO().getId() + " est inexistant."));
        //????????????? verifier que la table intermediaire est updated apres le save ci-dessous
        VisaDemande visaDemande = visaDemandeMapper.toEntity(visaDemandeDTO);
        return visaDemandeMapper.toDto(visaDemandeRepository.save(visaDemande));
    }

    @Override
    public VisaDemandeDTO update(VisaDemandeDTO visaDemandeDTO) {
        log.info("update d'un visaDemande : {}", visaDemandeDTO);
        VisaDemande visaDemande = visaDemandeMapper.toEntity(visaDemandeDTO);
        return visaDemandeMapper.toDto(visaDemandeRepository.save(visaDemande));
    }

    @Override
    public Optional<VisaDemandeDTO> get(Long id) {
        log.info("consultation d'un visaDemande : {}", id);
        return Optional.ofNullable(visaDemandeMapper.toDto(visaDemandeRepository.findById(id).orElseThrow()));
    }

    @Override
    public Page<VisaDemandeDTO> findAll(Pageable pageable) {
        log.info("liste paginée des visaDemandes");
        return visaDemandeRepository.findAll(pageable).map(visaDemandeMapper::toDto);
    }

    @Override
    public List<VisaDemandeDTO> findAllList() {
        log.info("liste des visaDemandes");
        return visaDemandeRepository.findAll().stream().map(visaDemandeMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<VisaDemandeDTO> findAllByDemandeId(Long demandeId) {
        log.info("liste des visaDemandes de la demande : {}", demandeId);
        return visaDemandeRepository.findAllByDemandeId(demandeId).stream().map(visaDemandeMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        log.info("suppression d'un visaDemande : {}", id);
        visaDemandeRepository.deleteById(id);
    }

}
