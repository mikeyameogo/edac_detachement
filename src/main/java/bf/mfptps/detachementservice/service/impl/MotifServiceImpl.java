/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service.impl;

import bf.mfptps.detachementservice.domain.Duree;
import bf.mfptps.detachementservice.domain.Motif;
import bf.mfptps.detachementservice.repository.DureeRepository;
import bf.mfptps.detachementservice.repository.MotifRepository;
import bf.mfptps.detachementservice.repository.TypeDemandeRepository;
import bf.mfptps.detachementservice.service.MotifService;
import bf.mfptps.detachementservice.service.dto.MotifDTO;
import bf.mfptps.detachementservice.service.mapper.DureeMapper;
import bf.mfptps.detachementservice.service.mapper.MotifMapper;
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
public class MotifServiceImpl implements MotifService {

    private final MotifRepository motifRepository;

    private final TypeDemandeRepository typeDemandeRepository;

    private final DureeRepository dureeRepository;

    private final DureeMapper dureeMapper;
    private final MotifMapper motifMapper;

    public MotifServiceImpl(MotifRepository motifRepository, TypeDemandeRepository typeDemandeRepository, DureeRepository dureeRepository, DureeMapper dureeMapper, MotifMapper motifMapper) {
        this.motifRepository = motifRepository;
        this.typeDemandeRepository = typeDemandeRepository;
        this.dureeRepository = dureeRepository;
        this.dureeMapper = dureeMapper;
        this.motifMapper = motifMapper;
    }

    @Override
    public MotifDTO create(MotifDTO motifDTO) {
        log.info("creation d'un motif : {}", motifDTO);
//        TypeDemande typeDemande = typeDemandeRepository
//                .findById(motifDTO.getTypeDemandeDTO().getId())
//                .orElseThrow(() -> new CustomException("Le type de demande " + motifDTO.getTypeDemandeDTO().getId() + " est inexistant."));
        //????????????? verifier que la table intermediaire est updated apres le save ci-dessous
        //Motif motif = motifMapper.toEntity(motifDTO);
        Duree duree = dureeRepository.save(motifDTO.getDureeMax());
        motifDTO.setDureeMax(duree);
        Motif motifsaved = motifMapper.toEntity(motifDTO);
        return motifMapper.toDto(motifRepository.save(motifsaved));
    }

    @Override
    public MotifDTO update(MotifDTO motifDTO) {
        log.info("update d'un motif : {}", motifDTO);
        Motif motif = motifMapper.toEntity(motifDTO);
        return motifMapper.toDto(motifRepository.save(motif));
    }

    @Override
    public Optional<MotifDTO> get(Long id) {
        log.info("consultation d'un motif : {}", id);
        return Optional.ofNullable(motifMapper.toDto(motifRepository.findById(id).orElseThrow()));
    }

    @Override
    public Page<MotifDTO> findAll(Pageable pageable) {
        log.info("liste paginée des motifs");
        return motifRepository.findAll(pageable).map(motifMapper::toDto);
    }

    @Override
    public List<MotifDTO> findAllList() {
        log.info("liste des motifs");
        return motifRepository.findAll().stream().map(motifMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        log.info("suppression d'un motif : {}", id);
        motifRepository.deleteById(id);
    }
}
