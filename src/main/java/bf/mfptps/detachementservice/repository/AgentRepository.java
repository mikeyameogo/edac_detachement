package bf.mfptps.detachementservice.repository;

import bf.mfptps.detachementservice.domain.Agent;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by Zak TEGUERA on 29/09/2023.
 * <teguera.zakaria@gmail.com>
 */
@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {

    Optional<Agent> findOneByMatricule(String matricule);

    Optional<Agent> findOneByMatriculeOrEmail(String matricule, String email);

//    @Query("SELECT a FROM Agent a, AgentStructure ast "
//            + "WHERE ast.structure.id = :structureId AND a.id = ast.agent.id AND ast.actif = true AND a.deleted = false")
//    Page<Agent> findAllByStructure(@Param("structureId") long structureId, Pageable pageable);

    List<Agent> findByCorpsId(Long id);

//    @Query(value = "SELECT new bf.mfptps.detachementservice.service.dto.AgentStructureDTO(a.id, a.matricule,a.nom,a.prenom,a.telephone,a.email, s.libelle) "
//            + "FROM Agent a, AgentStructure ags, Structure s "
//            + "WHERE ags.structure.id = s.id "
//            + "AND a.id = :id "
//            + "AND ags.agent.id = a.id ")
//    AgentStructureDTO findAgentById(@Param("id") long id);
}
