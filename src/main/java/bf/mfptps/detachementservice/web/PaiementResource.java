package bf.mfptps.detachementservice.web;

import bf.mfptps.detachementservice.domain.Paiement;
import bf.mfptps.detachementservice.service.PaiementService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/detachements/paiements")
public class PaiementResource {

    @Autowired
    private PaiementService paiementService;

    @PostMapping("/save")
    public Paiement createPaiement(@RequestBody Paiement paiement) {
        System.err.println("DEBUT SAVE TRANSACTION");
        return paiementService.save(paiement);
    }

    @PostMapping("/callback")
    public String handleCallback(@RequestBody String payload) {
        System.err.println("DECLENCHEMENT TRANSACTION");
        JSONObject event = new JSONObject(payload);

        String token = event.getString("token");
        String transactionId = event.getString("transaction_id");
        String status = event.getString("status");

        Paiement transaction = paiementService.findPaiementByToken(token); // Ou avec le transaction_id ou tout autre identifiant unique

        if (transaction.getStatus().equals("pending") && status.equals("completed")) {
            // Mettre à jour le statut de la transaction dans la base de données
            transaction.setStatus("completed");
            paiementService.update(transaction);
            // Livrer le produit ou valider la commande
            System.err.println("AAAAAAAAAA");
            System.err.println("transactionId");
            System.err.println("transactionId : "+transactionId);
        }

        return "success";
    }

}
