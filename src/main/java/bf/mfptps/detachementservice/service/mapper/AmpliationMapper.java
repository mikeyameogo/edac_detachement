package bf.mfptps.detachementservice.service.mapper;

import bf.mfptps.detachementservice.domain.Ampliation;
import bf.mfptps.detachementservice.service.dto.AmpliationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy=ReportingPolicy.IGNORE)
public interface AmpliationMapper {
	
	AmpliationDTO toDto(Ampliation ampliation);
	
	Ampliation toEntity(AmpliationDTO ampliationDTO);

}
