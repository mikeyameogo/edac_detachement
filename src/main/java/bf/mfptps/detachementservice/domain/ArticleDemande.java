package bf.mfptps.detachementservice.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@SQLDelete(sql = "UPDATE article_demande SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class ArticleDemande extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;
    private String code;
    private Long numeroOrdre;
    @ManyToOne(optional = false)
    private Demande demande;

}
