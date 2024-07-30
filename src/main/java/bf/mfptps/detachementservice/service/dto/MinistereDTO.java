package bf.mfptps.detachementservice.service.dto;

import bf.mfptps.detachementservice.enums.Civilite;
import lombok.Data;

@Data
public class MinistereDTO {

    private Long id;
    private String code;
    private String libelle;
    private String sigle;
    private String titre;
    private Civilite civilite;//civilité du ministre
    private boolean ministreEtat;//est-ce un ministre d'Etat ou simple ministre.
}
