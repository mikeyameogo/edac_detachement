package bf.mfptps.detachementservice.service;

import bf.mfptps.detachementservice.domain.Demande;
import bf.mfptps.detachementservice.domain.Paiement;
import bf.mfptps.detachementservice.enums.EStatutDemande;
import bf.mfptps.detachementservice.repository.DemandeRepository;
import bf.mfptps.detachementservice.repository.PaiementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaiementService {

    private final PaiementRepository paiementRepository;
    private final DemandeRepository demandeRepository;

    public PaiementService(PaiementRepository paiementRepository, DemandeRepository demandeRepository) {
        this.paiementRepository = paiementRepository;
        this.demandeRepository = demandeRepository;
    }

    public List<Paiement> findAll() {
        return paiementRepository.findAll();
    }

    public Paiement save(Paiement paiement) {
     Paiement paiement1 =  paiementRepository.save(paiement);


        Demande demande = demandeRepository.findById(Long.parseLong(paiement1.getExternal_id())).orElseThrow(() ->
                new bf.mfptps.detachementservice.exception.CustomException(
                        "La demande spécifiée n'existe pas. Veuillez réessayer !"));

        demande.setStatut(EStatutDemande.PAYEE);
        demande = demandeRepository.save(demande);
        return paiement1;

    }


    public Paiement update(Paiement paiement) {
        return paiementRepository.save(paiement);
    }

    public Paiement findPaiementByToken(String token) {
        return paiementRepository.findPaiementByToken(token);
    }
}
