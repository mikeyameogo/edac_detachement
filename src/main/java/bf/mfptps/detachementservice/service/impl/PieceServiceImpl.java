/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service.impl;

import bf.mfptps.detachementservice.domain.Piece;
import bf.mfptps.detachementservice.repository.PieceRepository;
import bf.mfptps.detachementservice.repository.TypeDemandeRepository;
import bf.mfptps.detachementservice.service.PieceService;
import bf.mfptps.detachementservice.service.dto.PieceDTO;
import bf.mfptps.detachementservice.service.mapper.PieceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@Service
public class PieceServiceImpl implements PieceService {

    private final PieceRepository pieceRepository;

    private final PieceMapper pieceMapper;

    public PieceServiceImpl(PieceRepository pieceRepository, TypeDemandeRepository typeDemandeRepository, PieceMapper pieceMapper) {
        this.pieceRepository = pieceRepository;
        this.pieceMapper = pieceMapper;
    }

    @Override
    public PieceDTO create(PieceDTO pieceDTO) {
        log.info("creation d'une piece : {}", pieceDTO);
        Piece piece = pieceMapper.toEntity(pieceDTO);
        return pieceMapper.toDto(pieceRepository.save(piece));
    }

    @Override
    public PieceDTO update(PieceDTO pieceDTO) {
        log.info("update d'une piece : {}", pieceDTO);
        Piece piece = pieceMapper.toEntity(pieceDTO);
        return pieceMapper.toDto(pieceRepository.save(piece));
    }

    @Override
    public Optional<PieceDTO> get(Long id) {
        log.info("consultation d'une piece : {}", id);
        return Optional.ofNullable(pieceMapper.toDto(pieceRepository.findById(id).orElseThrow()));
    }

    @Override
    public Page<PieceDTO> findAll(Pageable pageable) {
        log.info("liste paginée des pieces");
        return pieceRepository.findAll(pageable).map(pieceMapper::toDto);
    }

    @Override
    public List<PieceDTO> findAllList() {
        log.info("liste des pieces");
        return pieceRepository.findAll().stream().map(pieceMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        log.info("suppression d'une piece : {}", id);
        pieceRepository.deleteById(id);
    }

}
