package bf.mfptps.detachementservice.service.mapper;

import bf.mfptps.detachementservice.domain.VisaDemande;
import bf.mfptps.detachementservice.service.dto.VisaDemandeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VisaDemandeMapper {

    VisaDemandeDTO toDto(VisaDemande visaDemande);

    VisaDemande toEntity(VisaDemandeDTO visaDemandeDTO);

}
