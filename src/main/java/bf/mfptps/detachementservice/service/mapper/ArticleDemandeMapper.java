package bf.mfptps.detachementservice.service.mapper;

import bf.mfptps.detachementservice.domain.ArticleDemande;
import bf.mfptps.detachementservice.service.dto.ArticleDemandeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy=ReportingPolicy.IGNORE)
public interface ArticleDemandeMapper {

	
	ArticleDemandeDTO toDto(ArticleDemande articleDemande);
	
	ArticleDemande toEntity(ArticleDemandeDTO articleDemandeDTO);

}
    
