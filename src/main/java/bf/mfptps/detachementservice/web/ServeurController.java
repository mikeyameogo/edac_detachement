package bf.mfptps.detachementservice.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import bf.mfptps.detachementservice.exception.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bf.mfptps.detachementservice.domain.Serveur;
import bf.mfptps.detachementservice.exception.CustomException;
import bf.mfptps.detachementservice.service.ServeurService;
import bf.mfptps.detachementservice.util.HeaderUtil;
import bf.mfptps.detachementservice.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Gestion des types de serveur de stockage de fichiers joints et des apk
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@CrossOrigin("*")
@RestController
@RequestMapping("api/detachements/servers")
public class ServeurController {

    private static final String ENTITY_NAME = "Serveur";

    private final ServeurService serveurService;

    public ServeurController(ServeurService serveurService) {
        this.serveurService = serveurService;
    }

    @Operation(summary = "Ajoute un serveur", description = "Ajoute un serveur")
    @ApiResponse(responseCode = "201", description = "")
    @ApiResponse(responseCode = "401")
    @ApiResponse(responseCode = "409", description = "Conflict, une donnée similaire existe déjà")
    @ApiResponse(responseCode = "500", description = "Le serveur n'est pas en mesure de traiter la situation qu'il rencontre")
    @PostMapping(path = "serveurs")
    public ResponseEntity<Serveur> create(@Valid @RequestBody Serveur serveur) throws URISyntaxException {
        Serveur result = serveurService.create(serveur);
        return ResponseEntity.created(new URI("/api/affiliation-immatriculation/serveurs/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("SIGEPA", false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @Operation(summary = "Modifie un Serveur", description = "Modifie un Serveur")
    @PutMapping(path = "serveurs")
    public ResponseEntity<Serveur> update(@Valid @RequestBody Serveur serveur) throws URISyntaxException {

        if (serveur.getId() == null) {
            throw new BadRequestAlertException("Aucun Id dans " + ENTITY_NAME, ENTITY_NAME, ".");
        }

        Serveur result = serveurService.update(serveur);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("SIGEPA", false, ENTITY_NAME, serveur.getId().toString()))
                .body(result);
    }

    @Operation(summary = "Recherche un Serveur", description = "Recherche un Serveur active")
    @GetMapping(path = "serveurs/active")
    public ResponseEntity<Serveur> getActive() {
        Optional<Serveur> result = serveurService.getActive();
        return ResponseUtil.wrapOrNotFound(result);
    }

    @Operation(summary = "Liste tous les Serveurs", description = "Liste tous les Serveurs")
    @GetMapping(path = "serveurs")
    public ResponseEntity<List<Serveur>> findAll() {
        List<Serveur> result = serveurService.findAll();
        return ResponseEntity.ok().body(result);
    }

}
