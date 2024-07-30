package bf.mfptps.detachementservice.domain;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@SQLDelete(sql = "UPDATE visa SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Visa extends AbstractAuditingEntity {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String libelle;
	private String code;
}