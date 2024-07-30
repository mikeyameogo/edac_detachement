package bf.mfptps.detachementservice.domain;

import bf.mfptps.detachementservice.enums.EStatutDemande;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "demande")
@SQLDelete(sql = "UPDATE demande SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Demande extends AbstractAuditingEntity {

    private static final long serialVersionUID = -3185382652273115483L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateEffet;

    private String numero;

    private LocalDate dateDemande;

    private String destinataire;

    private String imputerA;//agent traitant (charge d'analyse) aqui un superieur impute une demande apres reception

    @Enumerated(EnumType.STRING)
    private EStatutDemande statut;

    private String urlActe;

    @ManyToOne
    @JoinColumn(nullable = false)
    private TypeDemande typeDemande;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Agent agent;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Motif motif;

    @ManyToOne(optional = false)
    private Structure destination;

    @ManyToOne(optional = false)
    private Duree duree; // duree de la mise en detachement demandee

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "demande_article", joinColumns = {
        @JoinColumn(name = "demande_id")}, inverseJoinColumns = {
        @JoinColumn(name = "article_demande_id")})
    private List<ArticleDemande> articles;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "demande_visa", joinColumns = {
        @JoinColumn(name = "demande_id")}, inverseJoinColumns = {
        @JoinColumn(name = "visa_demande_id")})
    private List<VisaDemande> visas;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "demande_ampliation", joinColumns = {
        @JoinColumn(name = "demande_id")}, inverseJoinColumns = {
        @JoinColumn(name = "ampliation_demande_id")})
    private List<AmpliationDemande> ampliations;

}
