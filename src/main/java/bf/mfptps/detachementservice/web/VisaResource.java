package bf.mfptps.detachementservice.web;

import bf.mfptps.detachementservice.exception.CreateNewElementException;
import bf.mfptps.detachementservice.exception.UpdateElementException;
import bf.mfptps.detachementservice.service.VisaService;
import bf.mfptps.detachementservice.service.dto.VisaDTO;
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
@RequestMapping("api/detachements/visas")
public class VisaResource {

    private final VisaService visaService;

    public VisaResource(VisaService visaService) {
        this.visaService = visaService;
    }

    @Operation(summary = "Ajoute un visa", description = "Ajoute un visa")
    @ApiResponse(responseCode = "201", description = "Visa créé avec succès")
    @ApiResponse(responseCode = "401")
    @ApiResponse(responseCode = "409", description = "Conflict, une donnée similaire existe déjà")
    @ApiResponse(responseCode = "500", description = "Le serveur n'est pas en mesure de traiter la situation qu'il rencontre")
    @PostMapping("/new")
    public ResponseEntity<VisaDTO> create(@RequestBody VisaDTO request) throws URISyntaxException {
        if (request.getId() != null) {
            throw new CreateNewElementException();
        }
        VisaDTO response = visaService.create(request);
        return ResponseEntity.created(new URI("/api/visas")).body(response);
    }

    @Operation(summary = "Modifie un visa", description = "Modifie un visa")
    @PutMapping("/update")
    public ResponseEntity<VisaDTO> update(@RequestBody VisaDTO request) throws URISyntaxException {
        if (request.getId() == null) {
            throw new UpdateElementException();
        }
        VisaDTO response = visaService.update(request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Recherche un visa via un ID", description = "Recherche un visa via un ID")
    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<VisaDTO>> get(@PathVariable(name = "id", required = true) Long id) {
        Optional<VisaDTO> visa = visaService.get(id);
        return ResponseEntity.ok().body(visa);
    }

    @Operation(summary = "Liste tous les visas", description = "Liste tous les visas")
    @GetMapping("/list-page")
    public ResponseEntity<List<VisaDTO>> findAll(Pageable pageable) {
        Page<VisaDTO> visas = visaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), visas);
        return new ResponseEntity<>(visas.getContent(), headers, HttpStatus.OK);
    }

    @Operation(summary = "Liste tous les visas", description = "Liste tous les visas")
    @GetMapping("/list")
    public ResponseEntity<List<VisaDTO>> findAll() {
        List<VisaDTO> visas = visaService.findAllList();
        return ResponseEntity.ok().body(visas);
    }

    @Operation(summary = "Supprime un visa via un ID", description = "Supprime un visa via un ID")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        visaService.delete(id);
        return ResponseEntity
                .noContent()
                .build();

    }
}
