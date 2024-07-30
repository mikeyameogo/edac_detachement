package bf.mfptps.detachementservice.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bf.mfptps.detachementservice.domain.Duree;
import bf.mfptps.detachementservice.domain.Historique;
import bf.mfptps.detachementservice.domain.PieceJointe;
import bf.mfptps.detachementservice.domain.TypeDemande;
import bf.mfptps.detachementservice.enums.EStatutDemande;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DemandeDTO implements Serializable {

    private static final long serialVersionUID = 1682534621095542841L;

    private Long id;

    private LocalDate dateEffet;

    private String numero;

    private LocalDate dateDemande;

    private String imputerA;//agent traitant (charge d'analyse) aqui un superieur impute une demande apres reception


    private String destinataire;

    private EStatutDemande statut;

    private TypeDemande typeDemande;

    private String urlActe;

    private MotifDTO motif;

    private StructureDTO destination;

    private AgentDTO agent;

    private ActeDTO acte;

    private Historique historique;

    private Duree duree;

    private List<PieceJointe> pieceJointes = new ArrayList<>();

   // private List<PiecesFourniesDTO> piecesFourniesDTO = new ArrayList<>(); // ajouté pour recevoir les pieces jointes
                                                                           // lors de la soumission d'une demande

}
