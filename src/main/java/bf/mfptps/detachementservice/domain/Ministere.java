package bf.mfptps.detachementservice.domain;

import bf.mfptps.detachementservice.enums.Civilite;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE ministere SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Ministere extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String libelle;

    @Column(nullable = false)
    private String sigle;

    @Column(nullable = false)
    private String titre;//titre du ministre. ex: Ministre d'Etat de la Fonction ...

    @Column(name = "civilite", nullable = false)
    private Civilite civilite;//civilité du ministre

    @Column(nullable = false)
    private boolean ministreEtat = false;//est-ce un ministre d'Etat ou simple ministre.
}
