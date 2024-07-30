package bf.mfptps.detachementservice.service.mapper;

import bf.mfptps.detachementservice.domain.Corps;
import bf.mfptps.detachementservice.service.dto.CorpsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CorpsMapper {

    CorpsDTO toDto(Corps corps);

    Corps toEntity(CorpsDTO corpsDTO);

}
