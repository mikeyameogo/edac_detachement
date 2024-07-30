package bf.mfptps.detachementservice.service.mapper;

import bf.mfptps.detachementservice.domain.Demande;
import bf.mfptps.detachementservice.service.dto.DemandeDTO;
import org.mapstruct.Mapper;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Mapper(componentModel = "spring")
public interface DemandeMapper {

    DemandeDTO toDto(Demande demande);

    Demande toEntity(DemandeDTO demandeDTO);
}
