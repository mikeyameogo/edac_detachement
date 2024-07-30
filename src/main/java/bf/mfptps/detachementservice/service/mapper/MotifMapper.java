package bf.mfptps.detachementservice.service.mapper;

import bf.mfptps.detachementservice.domain.Motif;
import bf.mfptps.detachementservice.service.dto.MotifDTO;
import org.mapstruct.Mapper;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Mapper(componentModel = "spring")
public interface MotifMapper {

    MotifDTO toDto(Motif motif);

    Motif toEntity(MotifDTO motifDTO);
}
