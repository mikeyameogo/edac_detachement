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
@SQLDelete(sql = "UPDATE visa_demande SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class VisaDemande extends AbstractAuditingEntity {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String libelle;
    private String code;

    @ManyToOne(optional = false)
    private Demande demande;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "demande_visa", joinColumns = {
//            @JoinColumn(name = "visa_demande_id") }, inverseJoinColumns = {
//            @JoinColumn(name = "demande_id") })
//    private List<Demande> demandes;
}