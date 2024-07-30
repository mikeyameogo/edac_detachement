package bf.mfptps.detachementservice.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.JoinColumn;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE paiement SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String date;
    private String response_code;
    private String token;
    private String description;
    private String amount;
    private String montant;
    private String response_text;
    private String status;
    private String operator_name;
    private String operator_id;
    private String customer;
    private String transaction_id;
    private String external_id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "paiement_id")
    private List<CustomDatum> custom_data;

    // Getters and Setters
}

