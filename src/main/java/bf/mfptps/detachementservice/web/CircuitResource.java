package bf.mfptps.detachementservice.web;

import bf.mfptps.detachementservice.exception.CreateNewElementException;
import bf.mfptps.detachementservice.exception.UpdateElementException;
import bf.mfptps.detachementservice.service.CircuitService;
import bf.mfptps.detachementservice.service.dto.CircuitDTO;
import bf.mfptps.detachementservice.util.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Created by Zak TEGUERA on 29/09/2023.
 * <teguera.zakaria@gmail.com>
 */
@CrossOrigin("*")
@RestController
@RequestMapping("api/detachements/circuit")
public class CircuitResource {

    private final CircuitService circuitService;

    public CircuitResource(CircuitService circuitService) {
        this.circuitService = circuitService;
    }

    @Operation(summary = "Ajoute un circuit", description = "Ajoute un circuit")
    @ApiResponse(responseCode = "201", description = "Circuit créée avec succès")
    @ApiResponse(responseCode = "401")
    @ApiResponse(responseCode = "409", description = "Conflict, une donnée similaire existe déjà")
    @ApiResponse(responseCode = "500", description = "Le serveur n'est pas en mesure de traiter la situation qu'il rencontre")
    @PostMapping("/new")
    public ResponseEntity<CircuitDTO> create(@RequestBody CircuitDTO request) throws URISyntaxException {
        if (request.getId() != null) {
            throw new CreateNewElementException();
        }
        CircuitDTO response = circuitService.create(request);
        return ResponseEntity.created(new URI("/api/circuits")).body(response);
    }

    @Operation(summary = "Modifie un circuit", description = "Modifie un circuit")
    @PutMapping("/update")
    public ResponseEntity<CircuitDTO> update(@RequestBody CircuitDTO request) throws URISyntaxException {
        if (request.getId() == null) {
            throw new UpdateElementException();
        }
        CircuitDTO response = circuitService.update(request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Recherche un circuit via un ID", description = "Recherche un circuit via un ID")
    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<CircuitDTO>> get(@PathVariable(name = "id", required = true) Long id) {
        Optional<CircuitDTO> circuit = circuitService.getById(id);
        return ResponseEntity.ok().body(circuit);
    }

    @Operation(summary = "Liste tous les circuits", description = "Liste tous les circuits")
    @GetMapping("/list-page")
    public ResponseEntity<List<CircuitDTO>> findAll(Pageable pageable) {
        Page<CircuitDTO> circuits = circuitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), circuits);
        return new ResponseEntity<>(circuits.getContent(), headers, HttpStatus.OK);
    }

    @Operation(summary = "Liste tous les circuits", description = "Liste tous les circuits")
    @GetMapping("/list")
    public ResponseEntity<List<CircuitDTO>> findAll() {
        List<CircuitDTO> circuits = circuitService.findAllList();
        return ResponseEntity.ok().body(circuits);
    }

    @Operation(summary = "Supprime un circuit via un ID", description = "Supprime un circuit via un ID")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        circuitService.delete(id);
        return ResponseEntity
                .noContent()
                .build();

    }
}
