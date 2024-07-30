package bf.mfptps.detachementservice.web;

import bf.mfptps.detachementservice.exception.CreateNewElementException;
import bf.mfptps.detachementservice.exception.UpdateElementException;
import bf.mfptps.detachementservice.service.AmpliationService;
import bf.mfptps.detachementservice.service.dto.AmpliationDTO;
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
@RequestMapping("api/detachements/ampliations")
public class AmpliationResource {

    private final AmpliationService ampliationService;

    public AmpliationResource(AmpliationService ampliationService) {
        this.ampliationService = ampliationService;
    }

    @Operation(summary = "Ajoute une ampliation", description = "Ajoute une ampliation")
    @ApiResponse(responseCode = "201", description = "Ampliation créée avec succès")
    @ApiResponse(responseCode = "401")
    @ApiResponse(responseCode = "409", description = "Conflict, une donnée similaire existe déjà")
    @ApiResponse(responseCode = "500", description = "Le serveur n'est pas en mesure de traiter la situation qu'il rencontre")
    @PostMapping("/new")
    public ResponseEntity<AmpliationDTO> create(@RequestBody AmpliationDTO request) throws URISyntaxException {
        if (request.getId() != null) {
            throw new CreateNewElementException();
        }
        AmpliationDTO response = ampliationService.create(request);
        return ResponseEntity.created(new URI("/api/ampliations")).body(response);
    }

    @Operation(summary = "Modifie une ampliation", description = "Modifie une ampliation")
    @PutMapping("/update")
    public ResponseEntity<AmpliationDTO> update(@RequestBody AmpliationDTO request) throws URISyntaxException {
        if (request.getId() == null) {
            throw new UpdateElementException();
        }
        AmpliationDTO response = ampliationService.update(request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Recherche une ampliation via un ID", description = "Recherche une ampliation via un ID")
    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<AmpliationDTO>> get(@PathVariable(name = "id", required = true) Long id) {
        Optional<AmpliationDTO> ampliation = ampliationService.get(id);
        return ResponseEntity.ok().body(ampliation);
    }

    @Operation(summary = "Liste toutes les ampliations", description = "Liste toutes les ampliations")
    @GetMapping("/list-page")
    public ResponseEntity<List<AmpliationDTO>> findAll(Pageable pageable) {
        Page<AmpliationDTO> ampliations = ampliationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), ampliations);
        return new ResponseEntity<>(ampliations.getContent(), headers, HttpStatus.OK);
    }

    @Operation(summary = "Liste toutes les ampliations", description = "Liste toutes les ampliations")
    @GetMapping("/list")
    public ResponseEntity<List<AmpliationDTO>> findAll() {
        List<AmpliationDTO> ampliations = ampliationService.findAllList();
        return ResponseEntity.ok().body(ampliations);
    }

    @Operation(summary = "Supprime une ampliation via un ID", description = "Supprime une ampliation via un ID")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ampliationService.delete(id);
        return ResponseEntity
                .noContent()
                .build();

    }
}
