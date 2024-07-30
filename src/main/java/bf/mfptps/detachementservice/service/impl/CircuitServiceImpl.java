/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service.impl;

import bf.mfptps.detachementservice.domain.Agent;
import bf.mfptps.detachementservice.domain.Circuit;
import bf.mfptps.detachementservice.exception.CustomException;
import bf.mfptps.detachementservice.repository.AgentRepository;
import bf.mfptps.detachementservice.repository.CircuitRepository;
import bf.mfptps.detachementservice.service.CircuitService;
import bf.mfptps.detachementservice.service.dto.CircuitDTO;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import bf.mfptps.detachementservice.service.dto.CircuitDTO;
import bf.mfptps.detachementservice.service.mapper.CircuitMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@Service
public class CircuitServiceImpl implements CircuitService {

    private final CircuitRepository circuitRepository;

    private final AgentRepository agentRepository;

    private final CircuitMapper circuitMapper;

    public CircuitServiceImpl(CircuitRepository circuitRepository, AgentRepository agentRepository, CircuitMapper circuitMapper) {
        this.circuitRepository = circuitRepository;
        this.agentRepository = agentRepository;
        this.circuitMapper = circuitMapper;
    }

    @Override
    public CircuitDTO create(CircuitDTO circuitDTO) {
        log.info("creation d'un circuit : {}", circuitDTO);
        Circuit circuit = circuitMapper.toEntity(circuitDTO);
        return circuitMapper.toDto(circuitRepository.save(circuit));
    }

    @Override
    public CircuitDTO update(CircuitDTO circuitDTO) {
        log.info("update d'un circuit : {}", circuitDTO);
        Circuit circuit = circuitMapper.toEntity(circuitDTO);
        return circuitMapper.toDto(circuitRepository.save(circuit));
    }

    @Override
    public Optional<CircuitDTO> getById(Long id) {
        log.info("consultation d'un circuit : {}", id);
        return Optional.ofNullable(circuitMapper.toDto(circuitRepository.findById(id).orElse(null)));
    }

    @Override
    public Page<CircuitDTO> findAll(Pageable pageable) {
        log.info("liste paginée des circuit");
        return circuitRepository.findAll(pageable).map(circuitMapper::toDto);
    }

    @Override
    public List<CircuitDTO> findAllList() {
        log.info("liste des circuit");
        return circuitRepository.findAll().stream().map(circuitMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        log.info("suppression d'un circuit  de code : {}", id);
//        List<Agent> agents = agentRepository.findByCircuitId(id);
//        if (agents == null || CollectionUtils.isEmpty(agents)) {
//            circuitRepository.deleteById(id);
//        } else {
//            throw new CustomException("Veuillez supprimer les agents de cet circuit avant de poursuivre.");
//        }

        circuitRepository.deleteById(id);
    }

}
