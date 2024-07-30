package bf.mfptps.detachementservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@SQLDelete(sql = "UPDATE piece_jointe SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class PieceJointe extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;
    private String url; //uri y compris filename
    private String nomFichier; //nom du document uploaded
    private String extensionFichier; //extension du document uploaded
    private long tailleFichier; //taille du document uploaded
    @ManyToOne
    @JoinColumn(nullable = false)
    private Demande demande;

    //===================== custom constructor ==========================
    public PieceJointe(String libelle, String url, String nomFichier, String extensionFichier, long tailleFichier, Demande demande) {
        this.libelle = libelle;
        this.url = url;
        this.nomFichier = nomFichier;
        this.extensionFichier = extensionFichier;
        this.tailleFichier = tailleFichier;
        this.demande = demande;
    }

}
