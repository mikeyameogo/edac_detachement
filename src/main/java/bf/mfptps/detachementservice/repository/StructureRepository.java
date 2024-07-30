package bf.mfptps.detachementservice.repository;

import bf.mfptps.detachementservice.domain.Structure;
import bf.mfptps.detachementservice.domain.TypeStructure;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Repository
public interface StructureRepository extends JpaRepository<Structure, Long> {

    List<Structure> findByParentId(Long id);

//    @Query("SELECT s FROM Structure s, MinistereStructure ms, Ministere m "
//            + "WHERE ms.ministere.id = m.id "
//            + "AND m.id =:ministerId "
//            + "AND ms.structure.id = s.id "
//            + "AND ms.statut = true "
//            + "AND s.type <>:excludeType "
//            + "AND s.deleted = false")
//    List<Structure> findMinistereStructure(Long ministerId, TypeStructure excludeType);
}
