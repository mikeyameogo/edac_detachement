package bf.mfptps.detachementservice.service.dto;

import bf.mfptps.detachementservice.domain.Demande;
import lombok.Data;

import java.util.List;

@Data
public class VisaDemandeDTO {

    private Long id;
    private String libelle;
    private String code;
    private Demande demande;

}