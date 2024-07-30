package bf.mfptps.detachementservice.service.dto;

import bf.mfptps.detachementservice.domain.Agent;
import bf.mfptps.detachementservice.domain.Corps;
import bf.mfptps.detachementservice.domain.Profil;
import bf.mfptps.detachementservice.domain.Structure;
import bf.mfptps.detachementservice.enums.ETypeAgent;
import java.time.LocalDate;
import lombok.Data;

@Data
public class AgentDTO {

    private Long id;
    private String nom;
    private String prenom;
    private String matricule;
    private String email;
    private String sexe;
    private LocalDate dateNaissance;
    private String lieuNaissance;
    private LocalDate dateRecrutement;
    private String noCNIB;
    private String nip;
    private String qualite;
    private LocalDate dateQualite;
    private String telephone;
    private String echelon;
    private String classe;
    private String echelle;
    private String categorie;
    private String position;
    private ETypeAgent typeAgent;
    private Corps corps;
    private Structure structure;
    private Agent superieurHierarchique;
    private Profil profil;
}
