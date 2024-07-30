package bf.mfptps.detachementservice.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import bf.mfptps.detachementservice.domain.Circuit;
import bf.mfptps.detachementservice.service.dto.CircuitDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy=ReportingPolicy.IGNORE)
public interface CircuitMapper {
   CircuitDTO toDto(Circuit circuit);
	
	Circuit toEntity(CircuitDTO circuitDTO);
  
}
