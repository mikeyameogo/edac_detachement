package bf.mfptps.detachementservice.service.dto;
import java.util.List;

import bf.mfptps.detachementservice.domain.Duree;
import bf.mfptps.detachementservice.enums.ECategorie;
import bf.mfptps.detachementservice.enums.ETypeDemandeur;
import lombok.Data;

@Data
public class MotifDTO {
	private Long id;
	private String libelle;
	private ETypeDemandeur typeDemandeur;
	private int plafondAnnee;
	private Duree dureeMax;
}
