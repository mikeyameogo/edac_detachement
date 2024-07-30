package bf.mfptps.detachementservice.service.mapper;

import bf.mfptps.detachementservice.domain.Duree;
import bf.mfptps.detachementservice.service.dto.DureeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Created by Zak TEGUERA on 09/10/2023.
 * <teguera.zakaria@gmail.com>
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy= ReportingPolicy.IGNORE)
public interface DureeMapper {

    Duree toDto(Duree Duree);

    Duree toEntity(DureeDTO dureeDTO);
}
