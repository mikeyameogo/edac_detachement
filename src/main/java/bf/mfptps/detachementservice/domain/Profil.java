package bf.mfptps.detachementservice.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


import java.util.HashSet;
import java.util.Set;

/**
 * Created by Zak TEGUERA on 05/08/2023.
 * <teguera.zakaria@gmail.com>
 */
@Entity
@Table(name = "profil")
@SQLDelete(sql = "UPDATE profil SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@Data
public class Profil extends AbstractAuditingEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "actif")
    private Boolean actif;

    @Column(name = "deleted")
    private Boolean deleted = false;

 /*   @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "profil_roles",
            joinColumns = @JoinColumn(name = "profil_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();*/
}
