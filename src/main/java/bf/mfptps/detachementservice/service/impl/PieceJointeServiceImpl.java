/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service.impl;

import bf.mfptps.detachementservice.domain.PieceJointe;
import bf.mfptps.detachementservice.repository.PieceJointeRepository;
import bf.mfptps.detachementservice.service.PieceJointeService;
import bf.mfptps.detachementservice.service.dto.DemandeDTO;
import bf.mfptps.detachementservice.service.dto.PieceJointeDTO;
import bf.mfptps.detachementservice.service.mapper.PieceJointeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@Service
public class PieceJointeServiceImpl implements PieceJointeService {

    private final PieceJointeRepository pieceJointeRepository;

    private final PieceJointeMapper pieceJointeMapper;

    public PieceJointeServiceImpl(PieceJointeRepository pieceJointeRepository, PieceJointeMapper pieceJointeMapper) {
        this.pieceJointeRepository = pieceJointeRepository;
        this.pieceJointeMapper = pieceJointeMapper;
    }

    @Override
    public PieceJointeDTO create(PieceJointeDTO pieceJointeDTO) {
        log.info("creation d'une pieceJointe : {}", pieceJointeDTO);
        PieceJointe pieceJointe = pieceJointeMapper.toEntity(pieceJointeDTO);
        return pieceJointeMapper.toDto(pieceJointeRepository.save(pieceJointe));
    }

    @Override
    public PieceJointeDTO update(PieceJointeDTO pieceJointeDTO) {
        log.info("update d'une pieceJointe : {}", pieceJointeDTO);
        PieceJointe pieceJointe = pieceJointeMapper.toEntity(pieceJointeDTO);
        return pieceJointeMapper.toDto(pieceJointeRepository.save(pieceJointe));
    }

    @Override
    public Optional<PieceJointeDTO> get(Long id) {
        log.info("consultation d'une pieceJointe : {}", id);
        return Optional.ofNullable(pieceJointeMapper.toDto(pieceJointeRepository.findById(id).orElseThrow()));
    }

    @Override
    public Page<PieceJointeDTO> findAll(Pageable pageable) {
        log.info("liste paginée des pieceJointes");
        return pieceJointeRepository.findAll(pageable).map(pieceJointeMapper::toDto);
    }

    @Override
    public List<PieceJointeDTO> findAllList() {
        log.info("liste des pieceJointes");
        return pieceJointeRepository.findAll().stream().map(pieceJointeMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<PieceJointe> findAllByDemande(Long idDemande) {
        log.info("Lise des pieces jointes d'une demande : {}", idDemande);
        return new ArrayList<>(pieceJointeRepository.findByDemandeId(idDemande));
    }

    @Override
    public void delete(Long id) {
        log.info("suppression d'une pieceJointe : {}", id);
        pieceJointeRepository.deleteById(id);
    }

}
