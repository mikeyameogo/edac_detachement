/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service;

import bf.mfptps.detachementservice.domain.Agent;
import bf.mfptps.detachementservice.service.dto.AgentDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public interface AgentService {

    AgentDTO create(AgentDTO agentDTO);

    AgentDTO update(AgentDTO agentDTO);

//    Agent affectationAgent(String matriculeOrMail, Long structureID);

    Optional<AgentDTO> get(String matricule);

    Optional<AgentDTO> get(Long id);

    Page<AgentDTO> findAll(Pageable pageable);

    List<AgentDTO> findAllList();

//    Page<AgentDTO> getAllAgentsByStructure(long structureId, Pageable pageable);

//    AgentStructureDTO getAgentWithStructure(long idAgent);

    void delete(Long code);

}
