package bf.mfptps.detachementservice.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import bf.mfptps.detachementservice.domain.Ampliation;
import bf.mfptps.detachementservice.domain.Article;
import bf.mfptps.detachementservice.service.dto.AmpliationDTO;
import bf.mfptps.detachementservice.service.dto.ArticleDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy=ReportingPolicy.IGNORE)
public interface ArticleMapper {

	
	ArticleDTO toDto(Article article);
	
	Article toEntity(ArticleDTO articleDTO);

}
    
