package bf.mfptps.detachementservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "custom_structure")
@SQLDelete(sql = "UPDATE custom_structure SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Structure extends AbstractAuditingEntity {

    private static final long serialVersionUID = -3830527150152208419L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false, unique = true)
    private String libelle;

    @Column(nullable = false)
    private String sigle;

    private String responsable;

    @ManyToOne
    @JoinColumn(nullable = false)
    private TypeStructure type;

    @ManyToOne(fetch = FetchType.EAGER)
    private Structure parent;

    @ManyToOne(fetch = FetchType.EAGER)
    private Ministere ministere; // Remis le 06122023 //09112023 commenté car deja pris en compte dans la table intermediaire MinistereStrucuture
}
