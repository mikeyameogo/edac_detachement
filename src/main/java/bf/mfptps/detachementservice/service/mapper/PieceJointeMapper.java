package bf.mfptps.detachementservice.service.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import bf.mfptps.detachementservice.domain.PieceJointe;
import bf.mfptps.detachementservice.service.dto.PieceJointeDTO;
@Mapper(componentModel = "spring", unmappedTargetPolicy=ReportingPolicy.IGNORE)
public interface PieceJointeMapper {
PieceJointeDTO toDto(PieceJointe pieceJointe);
	
PieceJointe toEntity(PieceJointeDTO pieceJointeDTO);

}

