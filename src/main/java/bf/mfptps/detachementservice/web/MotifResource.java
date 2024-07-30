package bf.mfptps.detachementservice.web;

import bf.mfptps.detachementservice.exception.CreateNewElementException;
import bf.mfptps.detachementservice.exception.UpdateElementException;
import bf.mfptps.detachementservice.service.MotifService;
import bf.mfptps.detachementservice.service.dto.MotifDTO;
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
@RequestMapping("api/detachements/motifs")
public class MotifResource {

    private final MotifService motifService;

    public MotifResource(MotifService motifService) {
        this.motifService = motifService;
    }

    @Operation(summary = "Ajoute une motif", description = "Ajoute une motif")
    @ApiResponse(responseCode = "201", description = "Motif créée avec succès")
    @ApiResponse(responseCode = "401")
    @ApiResponse(responseCode = "409", description = "Conflict, une donnée similaire existe déjà")
    @ApiResponse(responseCode = "500", description = "Le serveur n'est pas en mesure de traiter la situation qu'il rencontre")
    @PostMapping("/new")
    public ResponseEntity<MotifDTO> create(@RequestBody MotifDTO request) throws URISyntaxException {
        if (request.getId() != null) {
            throw new CreateNewElementException();
        }
        MotifDTO response = motifService.create(request);
        return ResponseEntity.created(new URI("/api/motifs")).body(response);
    }

    @Operation(summary = "Modifie un motif", description = "Modifie un motif")
    @PutMapping("/update")
    public ResponseEntity<MotifDTO> update(@RequestBody MotifDTO request) throws URISyntaxException {
        if (request.getId() == null) {
            throw new UpdateElementException();
        }
        MotifDTO response = motifService.update(request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Recherche une motif via un ID", description = "Recherche une motif via un ID")
    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<MotifDTO>> get(@PathVariable(name = "id", required = true) Long id) {
        Optional<MotifDTO> motif = motifService.get(id);
        return ResponseEntity.ok().body(motif);
    }

    @Operation(summary = "Liste toutes les motifs", description = "Liste toutes les motifs")
    @GetMapping("/list-page")
    public ResponseEntity<List<MotifDTO>> findAll(Pageable pageable) {
        Page<MotifDTO> motifs = motifService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), motifs);
        return ResponseEntity.ok().headers(headers).body(motifs.getContent());
    }

    @Operation(summary = "Liste toutes les motifs", description = "Liste toutes les motifs")
    @GetMapping("/list")
    public ResponseEntity<List<MotifDTO>> findAll() {
        List<MotifDTO> motifs = motifService.findAllList();
        return ResponseEntity.ok().body(motifs);
    }

    @Operation(summary = "Supprime une motif via un ID", description = "Supprime une motif via un ID")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        motifService.delete(id);
        return ResponseEntity
                .noContent()
                .build();

    }
}
