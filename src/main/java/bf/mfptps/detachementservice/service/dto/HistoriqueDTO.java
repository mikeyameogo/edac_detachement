package bf.mfptps.detachementservice.service.dto;

import bf.mfptps.detachementservice.domain.Circuit;
import bf.mfptps.detachementservice.domain.Demande;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

@Data

public class HistoriqueDTO {
	private Long id;
	private Instant date;
	private String commentaire;
	private String avis;
	private Demande demande;
	private Circuit circuit;

}
