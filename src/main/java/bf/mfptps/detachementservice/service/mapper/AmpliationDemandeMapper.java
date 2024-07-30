package bf.mfptps.detachementservice.service.mapper;

import bf.mfptps.detachementservice.domain.AmpliationDemande;
import bf.mfptps.detachementservice.service.dto.AmpliationDemandeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy=ReportingPolicy.IGNORE)
public interface AmpliationDemandeMapper {
	
	AmpliationDemandeDTO toDto(AmpliationDemande ampliationDemande);
	AmpliationDemande toEntity(AmpliationDemandeDTO ampliationDemandeDTO);

}
