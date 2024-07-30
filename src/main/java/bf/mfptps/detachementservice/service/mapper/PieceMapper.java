package bf.mfptps.detachementservice.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import bf.mfptps.detachementservice.domain.Corps;
import bf.mfptps.detachementservice.domain.Piece;
import bf.mfptps.detachementservice.service.dto.PieceDTO;
@Mapper(componentModel = "spring", unmappedTargetPolicy=ReportingPolicy.IGNORE)
public interface PieceMapper {

	PieceDTO toDto(Piece piece);
	
	Piece toEntity(PieceDTO pieceDTO);
}

