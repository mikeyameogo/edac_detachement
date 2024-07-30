package bf.mfptps.detachementservice.domain;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import bf.mfptps.detachementservice.enums.ETypeServeur;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "serveurstorage")
@SQLDelete(sql = "UPDATE serveurstorage SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@FilterDef(name = "deletedFilter", parameters = @ParamDef(name = "isDeleted", type = Boolean.class))
@Filter(name = "deletedFilter", condition = "deleted = :isDeleted")
public class Serveur extends AbstractAuditingEntity {

    private static final long serialVersionUID = -2304965993020156316L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "adresse", nullable = false)
    private String adresse;

    @NotNull
    @Column(name = "port", nullable = false)
    private Integer port;

    @NotNull
    @Column(name = "type_serveur", nullable = false)
    @Enumerated(EnumType.STRING)
    private ETypeServeur eTypeServeur;

    @Column(name = "active", nullable = false)
    private Boolean active = false;

    @NotNull
    @Column(name = "mot_passe", nullable = false)
    private String motPasse;

    @NotNull
    @Column(name = "nom_utilisateur", nullable = false)
    private String nomUtilisateur;

}
