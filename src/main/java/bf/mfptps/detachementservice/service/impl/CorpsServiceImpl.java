/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service.impl;

import bf.mfptps.detachementservice.domain.Agent;
import bf.mfptps.detachementservice.domain.Corps;
import bf.mfptps.detachementservice.exception.CustomException;
import bf.mfptps.detachementservice.repository.AgentRepository;
import bf.mfptps.detachementservice.repository.CorpsRepository;
import bf.mfptps.detachementservice.service.CorpsService;
import bf.mfptps.detachementservice.service.dto.CorpsDTO;
import bf.mfptps.detachementservice.service.mapper.CorpsMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
public class CorpsServiceImpl implements CorpsService {

    private final CorpsRepository corpsRepository;

    private final AgentRepository agentRepository;

    private final CorpsMapper corpsMapper;

    public CorpsServiceImpl(CorpsRepository corpsRepository, AgentRepository agentRepository, CorpsMapper corpsMapper) {
        this.corpsRepository = corpsRepository;
        this.agentRepository = agentRepository;
        this.corpsMapper = corpsMapper;
    }

    @Override
    public CorpsDTO create(CorpsDTO corpsDTO) {
        log.info("creation d'un corps : {}", corpsDTO);
        Corps corps = corpsMapper.toEntity(corpsDTO);
        return corpsMapper.toDto(corpsRepository.save(corps));
    }

    @Override
    public CorpsDTO update(CorpsDTO corpsDTO) {
        log.info("update d'un corps : {}", corpsDTO);
        Corps corps = corpsMapper.toEntity(corpsDTO);
        return corpsMapper.toDto(corpsRepository.save(corps));
    }

    @Override
    public Optional<CorpsDTO> get(String code) {
        log.info("consultation d'un corps de code : {}", code);
        return Optional.ofNullable(corpsMapper.toDto(corpsRepository.findByCode(code).orElse(null)));
    }

    @Override
    public Optional<CorpsDTO> get(Long id) {
        log.info("consultation d'un corps : {}", id);
        return Optional.ofNullable(corpsMapper.toDto(corpsRepository.findById(id).orElse(null)));
    }

    @Override
    public Page<CorpsDTO> findAll(Pageable pageable) {
        log.info("liste paginée des corps");
        return corpsRepository.findAll(pageable).map(corpsMapper::toDto);
    }

    @Override
    public List<CorpsDTO> findAllList() {
        log.info("liste des corps");
        return corpsRepository.findAll().stream().map(corpsMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        log.info("suppression d'un corps  de code : {}", id);
        List<Agent> agents = agentRepository.findByCorpsId(id);
        if (agents == null || CollectionUtils.isEmpty(agents)) {
            corpsRepository.deleteById(id);
        } else {
            throw new CustomException("Veuillez supprimer les agents de cet corps avant de poursuivre.");
        }
    }

}
