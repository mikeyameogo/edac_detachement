package bf.mfptps.detachementservice.service.dto;
import bf.mfptps.detachementservice.domain.*;
import lombok.Data;

import java.util.List;

@Data
public class TypeDemandeDTO {
private Long id;
private String libelle;
private List<Motif> motifs;
private List<Article> articles;
private List<Visa> visas;
private List<Ampliation> ampliations;
//private List<Piece> pieces;

}