package bf.mfptps.detachementservice.service.mapper;

import bf.mfptps.detachementservice.domain.MinistereStructure;
import bf.mfptps.detachementservice.service.dto.MinistereStructureDTO;
import bf.mfptps.detachementservice.service.dto.StructureDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MinistereStructureMapper {

    @Mappings({
        @Mapping(target = "statut", source = "statut")
    })
    MinistereStructureDTO toDto(MinistereStructure ministereS);

    @Mappings({
        @Mapping(target = "statut", source = "statut")
    })
    MinistereStructure toEntity(MinistereStructureDTO ministereSDTO);

    @Mappings({
        @Mapping(target = "id", source = "structure.id"),
        @Mapping(target = "code", source = "structure.code"),
        @Mapping(target = "libelle", source = "structure.libelle"),
        @Mapping(target = "sigle", source = "structure.sigle"),
        @Mapping(target = "responsable", source = "structure.responsable"),
        @Mapping(target = "type", source = "structure.type"),
//        @Mapping(target = "ministereDTO", source = "ministere"),
        @Mapping(target = "parent", source = "structure.parent")})
    StructureDTO toStructureDTO(MinistereStructure ministereStructure);

}
