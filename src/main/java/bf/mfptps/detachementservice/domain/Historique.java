package bf.mfptps.detachementservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.Instant;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@SQLDelete(sql = "UPDATE historique SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Historique extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String commentaire;
    private String avis;
    @Column(nullable = true)
    private Instant dateAvis;
    @ManyToOne
    private Demande demande;
    @ManyToOne
    private Circuit circuit;
}
