package bf.mfptps.detachementservice.domain;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import bf.mfptps.detachementservice.enums.ETypeDemandeur;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE motif SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Motif extends AbstractAuditingEntity {
	
	private static final long serialVersionUID = -7775704779997212216L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String libelle;

	private int plafondAnnee;

	@Enumerated(EnumType.STRING)
	private ETypeDemandeur typeDemandeur;

	@ManyToOne(optional = false)
	private Duree dureeMax; // represente la duree maximale pour une demande de ce motif
}
