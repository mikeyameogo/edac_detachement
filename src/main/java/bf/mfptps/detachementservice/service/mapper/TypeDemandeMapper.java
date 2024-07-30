package bf.mfptps.detachementservice.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import bf.mfptps.detachementservice.domain.TypeDemande;
import bf.mfptps.detachementservice.service.dto.TypeDemandeDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy=ReportingPolicy.IGNORE)
public interface TypeDemandeMapper {
	TypeDemandeDTO toDto(TypeDemande typeDemande);
	
	TypeDemande toEntity(TypeDemandeDTO typeDemandeDTO);

	
}

