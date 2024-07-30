package bf.mfptps.detachementservice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.StringPath;

import bf.mfptps.detachementservice.domain.Demande;

/**
 * Created by Zak TEGUERA on 29/09/2023.
 * <teguera.zakaria@gmail.com>
 */
@Repository
public interface DemandeRepository extends JpaRepository<Demande, Long> {

    List<Demande> findByTypeDemandeId(Long idTypeDemande);

    List<Demande> findByAgentId(Long idAgent);

    @Query("SELECT COUNT(d.id) FROM Demande d")
    long countDemande();

    @Query("SELECT COUNT(d.id) FROM Demande d WHERE d.typeDemande.id=?1")
    long countDemandeByTypeDemande(Long typeDemandeId);

    @Query("SELECT COUNT(d.id) FROM Demande d") 
    long countDemandeByStrucDestination(String structure);

   
    @Query("SELECT COUNT(d.id) FROM Demande d WHERE d.destination.id=?1")
    long countDemandeByDestinationId(long id);

    @Query("select d from Demande d where d.agent.matricule=:matricule")
    Page<Demande> findMyDmds(Pageable pageable, @Param("matricule") String matricule);

    @Query("select d from Demande d where d.agent.superieurHierarchique.matricule=:matricule")
    Page<Demande> findMyAgentDmds(Pageable pageable, @Param("matricule") String matricule);

    @Query("select d from Demande d where d.agent.structure.ministere.code=:code")
    Page<Demande> findMinistereDmds(Pageable pageable, @Param("code") String code);
}
