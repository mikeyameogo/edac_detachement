package bf.mfptps.detachementservice.service.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import bf.mfptps.detachementservice.domain.Historique;
import bf.mfptps.detachementservice.service.dto.ArticleDTO;
import bf.mfptps.detachementservice.service.dto.HistoriqueDTO;
@Mapper(componentModel = "spring", unmappedTargetPolicy=ReportingPolicy.IGNORE)
public interface HistoriqueMapper {
	HistoriqueDTO toDto(Historique historique);
	Historique toEntity(HistoriqueDTO historique);

}
