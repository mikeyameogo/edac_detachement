package bf.mfptps.detachementservice.service.mapper;

import bf.mfptps.detachementservice.domain.Visa;
import bf.mfptps.detachementservice.service.dto.VisaDTO;
import org.mapstruct.Mapper;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Mapper(componentModel = "spring")
public interface VisaMapper {

    VisaDTO toDto(Visa visa);

    Visa toEntity(VisaDTO visaDTO);

}
