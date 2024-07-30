package bf.mfptps.detachementservice.repository;

import bf.mfptps.detachementservice.domain.MinistereStructure;
import bf.mfptps.detachementservice.domain.Structure;
import bf.mfptps.detachementservice.domain.TypeStructure;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Repository
public interface MinistereStructureRepository extends JpaRepository<MinistereStructure, Long> {

    Optional<MinistereStructure> findByStructureIdAndStatutIsTrue(@NotNull long structureId);

    Page<MinistereStructure> findAllByStatutIsTrue(Pageable pageable);

    @Query("SELECT ms FROM MinistereStructure ms "
            + "WHERE ms.statut = TRUE "
            + "AND ms.ministere.code != :codeMinistere")
    Page<MinistereStructure> findAllByStatutIsTrue(String codeMinistere, Pageable pageable);

    Page<MinistereStructure> findByMinistereIdAndStatutIsTrue(long ministereId, Pageable pageable);

    List<MinistereStructure> findByMinistereIdAndStatutIsTrue(long ministereId);

    @Query("SELECT ms.structure FROM MinistereStructure ms "
            + "WHERE ms.ministere.id = :ministereId "
            + "AND ms.statut = TRUE")
    List<Structure> findAllStructuresByMinistere(long ministereId);

    @Query("SELECT ms.structure FROM MinistereStructure ms WHERE ms.statut = TRUE")
    List<Structure> findAllStructuresActives();

    @Query("SELECT COUNT(*) FROM MinistereStructure ms "
            + "WHERE ms.ministere.id = :idMinistere "
            + "AND ms.statut = TRUE")
    long countStructureByMinistere(long idMinistere);

    @Query("SELECT s FROM MinistereStructure ms, Structure s "
            + "WHERE ms.ministere.id =:ministereId "
            + "AND ms.structure.id=s.id "
            + "AND ms.statut = TRUE AND s.type<>:type")
    List<Structure> allNonInternalStructureByMinistere(long ministereId, TypeStructure type);

//    @Query("SELECT ms.structure FROM MinistereStructure ms, Agent a, AgentStructure ags "
//            + "WHERE a.deleted = false AND a.matricule = :matricule "
//            + "AND a.id = ags.agent.id AND ags.actif = true "
//            + "AND ags.structure.id = ms.structure.id AND ms.statut = true")
//    Optional<Structure> findByAgentMatricule(String matricule);
}
