package bf.mfptps.detachementservice.web;

import bf.mfptps.detachementservice.exception.CreateNewElementException;
import bf.mfptps.detachementservice.exception.UpdateElementException;
import bf.mfptps.detachementservice.service.AmpliationDemandeService;
import bf.mfptps.detachementservice.service.dto.AmpliationDemandeDTO;
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
 * Created by Zak TEGUERA on 19/12/2023.
 * <teguera.zakaria@gmail.com>
 */
@CrossOrigin("*")
@RestController
@RequestMapping("api/detachements/ampliation-demandes")
public class AmpliationDemandeResource {

    private final AmpliationDemandeService ampliationDemandeService;

    public AmpliationDemandeResource(AmpliationDemandeService ampliationDemandeService) {
        this.ampliationDemandeService = ampliationDemandeService;
    }

    @Operation(summary = "Ajoute une ampliationDemande", description = "Ajoute une ampliationDemande")
    @ApiResponse(responseCode = "201", description = "AmpliationDemande créée avec succès")
    @ApiResponse(responseCode = "401")
    @ApiResponse(responseCode = "409", description = "Conflict, une donnée similaire existe déjà")
    @ApiResponse(responseCode = "500", description = "Le serveur n'est pas en mesure de traiter la situation qu'il rencontre")
    @PostMapping("/new")
    public ResponseEntity<AmpliationDemandeDTO> create(@RequestBody AmpliationDemandeDTO request) throws URISyntaxException {
        if (request.getId() != null) {
            throw new CreateNewElementException();
        }
        AmpliationDemandeDTO response = ampliationDemandeService.create(request);
        return ResponseEntity.created(new URI("/api/ampliationDemandes")).body(response);
    }

    @Operation(summary = "Modifie une ampliationDemande", description = "Modifie une ampliationDemande")
    @PutMapping("/update")
    public ResponseEntity<AmpliationDemandeDTO> update(@RequestBody AmpliationDemandeDTO request) throws URISyntaxException {
        if (request.getId() == null) {
            throw new UpdateElementException();
        }
        AmpliationDemandeDTO response = ampliationDemandeService.update(request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Recherche une ampliationDemande via un ID", description = "Recherche une ampliationDemande via un ID")
    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<AmpliationDemandeDTO>> get(@PathVariable(name = "id", required = true) Long id) {
        Optional<AmpliationDemandeDTO> ampliationDemande = ampliationDemandeService.get(id);
        return ResponseEntity.ok().body(ampliationDemande);
    }

    @Operation(summary = "Liste toutes les ampliationDemandes", description = "Liste toutes les ampliationDemandes")
    @GetMapping("/list-page")
    public ResponseEntity<List<AmpliationDemandeDTO>> findAll(Pageable pageable) {
        Page<AmpliationDemandeDTO> ampliationDemandes = ampliationDemandeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), ampliationDemandes);
        return new ResponseEntity<>(ampliationDemandes.getContent(), headers, HttpStatus.OK);
    }

    @Operation(summary = "Liste toutes les ampliationDemandes", description = "Liste toutes les ampliationDemandes")
    @GetMapping("/list")
    public ResponseEntity<List<AmpliationDemandeDTO>> findAll() {
        List<AmpliationDemandeDTO> ampliationDemandes = ampliationDemandeService.findAllList();
        return ResponseEntity.ok().body(ampliationDemandes);
    }

    @Operation(summary = "Liste toutes les ampliations spécifiques à une demande", description = "Liste toutes les ampliations spécifiques à une demande")
    @GetMapping("/list/{idDemande}")
    public ResponseEntity<List<AmpliationDemandeDTO>> findAllByIdDemande(@PathVariable(name = "idDemande", required = true) Long idDemande) {
        List<AmpliationDemandeDTO> ampliationDemandes = ampliationDemandeService.findAllByDemandeId(idDemande);
        return ResponseEntity.ok().body(ampliationDemandes);
    }

    @Operation(summary = "Supprime une ampliationDemande via un ID", description = "Supprime une ampliationDemande via un ID")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ampliationDemandeService.delete(id);
        return ResponseEntity
                .noContent()
                .build();

    }
}
