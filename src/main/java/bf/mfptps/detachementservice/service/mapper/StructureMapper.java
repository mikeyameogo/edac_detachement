package bf.mfptps.detachementservice.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import bf.mfptps.detachementservice.domain.Structure;
import bf.mfptps.detachementservice.service.dto.StructureDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy=ReportingPolicy.IGNORE)
public interface StructureMapper {

	StructureDTO toDto(Structure structure);
	
	Structure toEntity(StructureDTO structureDTO);

}

