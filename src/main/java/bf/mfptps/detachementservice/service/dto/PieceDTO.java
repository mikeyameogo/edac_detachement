package bf.mfptps.detachementservice.service.dto;

import lombok.Data;

import java.util.List;

import bf.mfptps.detachementservice.domain.Motif;

@Data
public class PieceDTO {
	private Long id;
	private String libelle;
	private Motif motif;

}