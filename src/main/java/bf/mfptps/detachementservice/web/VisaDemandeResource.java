package bf.mfptps.detachementservice.web;

import bf.mfptps.detachementservice.exception.CreateNewElementException;
import bf.mfptps.detachementservice.exception.UpdateElementException;
import bf.mfptps.detachementservice.service.VisaDemandeService;
import bf.mfptps.detachementservice.service.dto.VisaDemandeDTO;
import bf.mfptps.detachementservice.util.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * Created by Zak TEGUERA on 29/09/2023.
 * <teguera.zakaria@gmail.com>
 */
@CrossOrigin("*")
@RestController
@RequestMapping("api/detachements/visa-demandes")
public class VisaDemandeResource {

    private final VisaDemandeService visaDemandeService;

    public VisaDemandeResource(VisaDemandeService visaDemandeService) {
        this.visaDemandeService = visaDemandeService;
    }

    @Operation(summary = "Ajoute un visaDemande", description = "Ajoute un visaDemande")
    @ApiResponse(responseCode = "201", description = "VisaDemande créé avec succès")
    @ApiResponse(responseCode = "401")
    @ApiResponse(responseCode = "409", description = "Conflict, une donnée similaire existe déjà")
    @ApiResponse(responseCode = "500", description = "Le serveur n'est pas en mesure de traiter la situation qu'il rencontre")
    @PostMapping("/new")
    public ResponseEntity<VisaDemandeDTO> create(@RequestBody VisaDemandeDTO request) throws URISyntaxException {
        if (request.getId() != null) {
            throw new CreateNewElementException();
        }
        VisaDemandeDTO response = visaDemandeService.create(request);
        return ResponseEntity.created(new URI("/api/visaDemandes")).body(response);
    }

    @Operation(summary = "Modifie un visaDemande", description = "Modifie un visaDemande")
    @PutMapping("/update")
    public ResponseEntity<VisaDemandeDTO> update(@RequestBody VisaDemandeDTO request) throws URISyntaxException {
        if (request.getId() == null) {
            throw new UpdateElementException();
        }
        VisaDemandeDTO response = visaDemandeService.update(request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Recherche un visaDemande via un ID", description = "Recherche un visaDemande via un ID")
    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<VisaDemandeDTO>> get(@PathVariable(name = "id", required = true) Long id) {
        Optional<VisaDemandeDTO> visaDemande = visaDemandeService.get(id);
        return ResponseEntity.ok().body(visaDemande);
    }

    @Operation(summary = "Liste tous les visaDemandes", description = "Liste tous les visaDemandes")
    @GetMapping("/list-page")
    public ResponseEntity<List<VisaDemandeDTO>> findAll(Pageable pageable) {
        Page<VisaDemandeDTO> visaDemandes = visaDemandeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), visaDemandes);
        return new ResponseEntity<>(visaDemandes.getContent(), headers, HttpStatus.OK);
    }

    @Operation(summary = "Liste tous les visaDemandes", description = "Liste tous les visaDemandes")
    @GetMapping("/list")
    public ResponseEntity<List<VisaDemandeDTO>> findAll() {
        List<VisaDemandeDTO> visaDemandes = visaDemandeService.findAllList();
        return ResponseEntity.ok().body(visaDemandes);
    }

    @Operation(summary = "Liste tous les visas spécifiques à une demande", description = "Liste tous les visas spécifiques à une demande")
    @GetMapping("/list/{idDemande}")
    public ResponseEntity<List<VisaDemandeDTO>> findAllByDemandeId(@PathVariable(name = "idDemande", required = true) Long idDemande) {
        List<VisaDemandeDTO> visaDemandes = visaDemandeService.findAllByDemandeId(idDemande);
        return ResponseEntity.ok().body(visaDemandes);
    }

    @Operation(summary = "Supprime un visaDemande via un ID", description = "Supprime un visaDemande via un ID")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        visaDemandeService.delete(id);
        return ResponseEntity
                .noContent()
                .build();

    }
}
