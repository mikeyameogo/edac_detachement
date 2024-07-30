/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service.impl;

import bf.mfptps.detachementservice.domain.Agent;
import bf.mfptps.detachementservice.domain.Corps;
import bf.mfptps.detachementservice.domain.Structure;
import bf.mfptps.detachementservice.exception.CustomException;
import bf.mfptps.detachementservice.repository.AgentRepository;
import bf.mfptps.detachementservice.repository.CorpsRepository;
import bf.mfptps.detachementservice.repository.StructureRepository;
import bf.mfptps.detachementservice.service.AgentService;
import bf.mfptps.detachementservice.service.dto.AgentDTO;
import bf.mfptps.detachementservice.service.mapper.AgentMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@Service
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;

    private final StructureRepository structureRepository;

    private final CorpsRepository corpsRepository;

    private final AgentMapper agentMapper;

    public AgentServiceImpl(AgentRepository agentRepository, StructureRepository structureRepository, CorpsRepository corpsRepository, AgentMapper agentMapper) {
        this.agentRepository = agentRepository;
        this.structureRepository = structureRepository;
        this.corpsRepository = corpsRepository;
        this.agentMapper = agentMapper;
    }

    @Transactional(rollbackFor = CustomException.class)
    @Override
    public AgentDTO create(AgentDTO agentDTO) {
        log.info("creation d'un agent : {}", agentDTO);
        if (agentDTO.getTypeAgent() == null || agentDTO.getTypeAgent().equals("")) {
            throw new CustomException("Veuillez renseigner le type d'agent SVP.");
        }
        Structure structure = structureRepository
                .findById(agentDTO.getStructure().getId())
                .orElseThrow(() -> new CustomException("La structure " + agentDTO.getStructure().getId() + " est inexistante."));
        Corps corps = corpsRepository
                .findById(agentDTO.getCorps().getId())
                .orElseThrow(() -> new CustomException("Le corps " + agentDTO.getCorps().getId() + " est inexistant."));
        Agent agent = agentMapper.toEntity(agentDTO);
        agent.setCorps(corps);
        agent = agentRepository.save(agent);

        //on met a jour la table intermediaire AgentStructure
//        AgentStructure agentStructure = new AgentStructure();
//        agentStructure.setAgent(agent);
//        agentStructure.setStructure(structure);
//        agentStructureRepository.save(agentStructure);

        return agentMapper.toDto(agent);
    }

//    @Override
//    public Agent affectationAgent(String matriculeOrMail, Long structureID) {
//        Structure structure = structureRepository.findById(structureID).orElseThrow(() -> new CustomException("Structure " + structureID + " inexistante."));
//        Agent agent = agentRepository.findOneByMatriculeOrEmail(matriculeOrMail, matriculeOrMail).orElseThrow(() -> new CustomException("Agent " + matriculeOrMail + " inexistant."));
//        AgentStructure entity = new AgentStructure();
//        entity.setAgent(agent);
//        entity.setStructure(structure);
//        agentStructureRepository.findByAgentIdAndActifTrue(agent.getId()).ifPresent(oldAffectation -> oldAffectation.setActif(false));;
//        agentStructureRepository.save(entity);
//        return agent;
//    }

    @Override
    public AgentDTO update(AgentDTO agentDTO) {
        log.info("update d'un agent : {}", agentDTO);
        Agent agent = agentMapper.toEntity(agentDTO);
        return agentMapper.toDto(agentRepository.save(agent));
    }

    @Override
    public Optional<AgentDTO> get(String matricule) {
        log.info("consultation d'un agent de matricule : {}", matricule);
        return agentRepository.findOneByMatricule(matricule).map(agentMapper::toDto);
    }

    @Override
    public Optional<AgentDTO> get(Long id) {
        log.info("consultation d'un agent : {}", id);
        return agentRepository.findById(id).map(agentMapper::toDto);
    }

    @Override
    public Page<AgentDTO> findAll(Pageable pageable) {
        log.info("liste paginée des agents");
        return agentRepository.findAll(pageable).map(agentMapper::toDto);
    }

    @Override
    public List<AgentDTO> findAllList() {
        log.info("liste des agents");
        return agentRepository.findAll().stream().map(agentMapper::toDto).collect(Collectors.toList());
    }

//    @Override
//    @Transactional(readOnly = true)
//    public Page<AgentDTO> getAllAgentsByStructure(long structureId, Pageable pageable) {
//        Page<AgentDTO> result = agentRepository.findAllByStructure(structureId, pageable).map(agentMapper::toDto);
//        return result;
//    }

//    @Transactional(readOnly = true)
//    public AgentStructureDTO getAgentWithStructure(long id) {
//        return agentRepository.findAgentById(id);
//    }

    @Override
    public void delete(Long id) {
        log.info("suppression d'un agent : {}", id);
        agentRepository.deleteById(id);
    }

}
