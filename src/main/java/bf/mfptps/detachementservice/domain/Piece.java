package bf.mfptps.detachementservice.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@SQLDelete(sql = "UPDATE piece SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Piece extends AbstractAuditingEntity {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String libelle;
	@ManyToOne
	private Motif motif;

}