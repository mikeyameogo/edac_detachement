package bf.mfptps.detachementservice.domain;

import bf.mfptps.detachementservice.enums.ETypeAgent;
import jakarta.persistence.*;
import java.time.LocalDate;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "agent")
@SQLDelete(sql = "UPDATE agent SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Agent extends AbstractAuditingEntity {

    private static final long serialVersionUID = 3070127258786679590L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String prenom;

    @Column(nullable = false, unique = true)
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

    @Enumerated(EnumType.STRING)
    private ETypeAgent typeAgent;

    @ManyToOne
    private Corps corps;

    @ManyToOne
    private Structure structure; //Remis le 06122023 //09112023 ceci est remplacé par la table intermediaire AgentStructure pour prendre en compte les mouvement inter structure de l'agent

    @ManyToOne
    private Agent superieurHierarchique;

    @ManyToOne(fetch = FetchType.EAGER)
    private Profil profil;


}
