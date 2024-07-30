//package bf.mfptps.detachementservice.repository;
//
//import bf.mfptps.detachementservice.domain.AgentStructure;
//import java.util.Optional;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
///**
// *
// * @author Canisius <canisiushien@gmail.com>
// */
//public interface AgentStructureRepository extends JpaRepository<AgentStructure, Long> {
//
//    Optional<AgentStructure> findByAgentIdAndActifTrue(Long idAgent);
//
//    /**
//     *
//     * @param matricule : matricule or login of user
//     * @param email :
//     * @return
//     */
//    @Query("SELECT ast FROM AgentStructure ast, Agent a "
//            + "WHERE ast.agent.id = a.id AND ast.actif = true "
//            + "AND a.matricule = :matricule OR a.email = :email")
//    AgentStructure findOneByAgentMatriculeOrAgentEmail(String matricule, String email);
//
//}
