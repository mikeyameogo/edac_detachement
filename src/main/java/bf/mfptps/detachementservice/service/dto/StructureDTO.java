package bf.mfptps.detachementservice.service.dto;

import bf.mfptps.detachementservice.domain.Ministere;
import bf.mfptps.detachementservice.domain.Structure;
import bf.mfptps.detachementservice.domain.TypeStructure;
import lombok.Data;

@Data
public class StructureDTO {

    private Long id;
    private String code;
    private String libelle;
    private String sigle;
    private String responsable;
    private Structure parent;
    private TypeStructure type;
    private Ministere ministere;
}
