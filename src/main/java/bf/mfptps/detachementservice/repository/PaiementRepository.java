package bf.mfptps.detachementservice.repository;

import bf.mfptps.detachementservice.domain.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    Paiement findPaiementByToken(String token);
}
