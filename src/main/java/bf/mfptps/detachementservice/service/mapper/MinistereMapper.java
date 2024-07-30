package bf.mfptps.detachementservice.service.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import bf.mfptps.detachementservice.domain.Ministere;
import bf.mfptps.detachementservice.service.dto.MinistereDTO;
@Mapper(componentModel = "spring", unmappedTargetPolicy=ReportingPolicy.IGNORE)
public interface MinistereMapper {

	MinistereDTO toDto(Ministere ministere);
	
	Ministere toEntity(MinistereDTO ministereDTO);
}

