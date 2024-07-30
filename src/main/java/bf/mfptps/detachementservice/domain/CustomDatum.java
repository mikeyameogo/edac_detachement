package bf.mfptps.detachementservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE customDatum SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class CustomDatum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int id_invoice;
    private String keyof_customdata;
    private String valueof_customdata;

    // Getters and Setters
}
