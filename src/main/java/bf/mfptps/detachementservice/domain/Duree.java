package bf.mfptps.detachementservice.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * Created by Zak TEGUERA on 05/10/2023.
 * <teguera.zakaria@gmail.com>
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE duree SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Duree extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private int annee;
    private int mois;
    private int jours;
}
