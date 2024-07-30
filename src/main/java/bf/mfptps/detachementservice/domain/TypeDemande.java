package bf.mfptps.detachementservice.domain;

import jakarta.persistence.*;

import java.util.List;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@SQLDelete(sql = "UPDATE type_demande SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class TypeDemande extends AbstractAuditingEntity {

    private static final long serialVersionUID = -7495590583557033795L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Motif> motifs;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "type_demande_article", joinColumns = {
            @JoinColumn(name = "type_demande_id") }, inverseJoinColumns = {
                    @JoinColumn(name = "article_id") })
    private List<Article> articles;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "type_demande_visa", joinColumns = {
            @JoinColumn(name = "type_demande_id") }, inverseJoinColumns = {
                    @JoinColumn(name = "visa_id") })

    private List<Visa> visas;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "type_demande_ampliation", joinColumns = {
            @JoinColumn(name = "type_demande_id") }, inverseJoinColumns = {
                    @JoinColumn(name = "ampliation_id") })
    private List<Ampliation> ampliations;

}
