package bf.mfptps.detachementservice.domain;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@EqualsAndHashCode(callSuper = true)
@Data @AllArgsConstructor @NoArgsConstructor @ToString @Builder
@Entity
@SQLDelete(sql = "UPDATE acte SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Acte extends AbstractAuditingEntity {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String reference;
	private String statut;
	@OneToOne
	private Demande demande;
}
