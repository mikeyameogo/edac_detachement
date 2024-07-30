package bf.mfptps.detachementservice.service.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import bf.mfptps.detachementservice.domain.TypeStructure;
import bf.mfptps.detachementservice.service.dto.TypeStructureDTO;


@Mapper(componentModel = "spring", unmappedTargetPolicy=ReportingPolicy.IGNORE)
public interface TypeStructureMapper {
	TypeStructure toDto(TypeStructure TypeStructure);
	
	TypeStructure toEntity(TypeStructureDTO typeStructureDTO);

}

