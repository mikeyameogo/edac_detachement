package bf.mfptps.detachementservice.service.mapper;

import org.mapstruct.Mapper;

import bf.mfptps.detachementservice.domain.Acte;
import bf.mfptps.detachementservice.service.dto.ActeDTO;

@Mapper(componentModel = "spring")
public interface ActeMapper {
    ActeDTO toDto(Acte acte);

    Acte toEntity(ActeDTO acteDTO);
}
