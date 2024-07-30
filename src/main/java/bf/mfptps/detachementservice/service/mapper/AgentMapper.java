package bf.mfptps.detachementservice.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import bf.mfptps.detachementservice.domain.Agent;
import bf.mfptps.detachementservice.service.dto.AgentDTO;

@Mapper(componentModel = "spring")
public interface AgentMapper {
AgentDTO toDto(Agent agent);
Agent toEntity(AgentDTO agentDto);

    
}
