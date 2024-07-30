package bf.mfptps.detachementservice.web;

import bf.mfptps.detachementservice.domain.Duree;
import bf.mfptps.detachementservice.exception.CreateNewElementException;
import bf.mfptps.detachementservice.exception.UpdateElementException;
import bf.mfptps.detachementservice.service.DureeService;
import bf.mfptps.detachementservice.service.dto.DureeDTO;
import bf.mfptps.detachementservice.util.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
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
 * Created by Zak TEGUERA on 09/10/2023.
 * <teguera.zakaria@gmail.com>
 */
@CrossOrigin("*")
@RestController
@RequestMapping("api/detachements/duree")
public class DureeResource {

    private final DureeService dureeService;

    public DureeResource(DureeService dureeService) {
        this.dureeService = dureeService;
    }

    @Operation(summary = "Ajoute une duree", description = "Ajoute une duree")
    @ApiResponse(responseCode = "201", description = "Duree créée avec succès")
    @ApiResponse(responseCode = "401")
    @ApiResponse(responseCode = "409", description = "Conflict, une donnée similaire existe déjà")
    @ApiResponse(responseCode = "500", description = "Le serveur n'est pas en mesure de traiter la situation qu'il rencontre")
    @PostMapping("/new")
    public ResponseEntity<Duree> create(@Valid @RequestBody DureeDTO dureeDTO) throws URISyntaxException {
        if (dureeDTO.getId() != null) {
            throw new CreateNewElementException();
        }
        Duree result = dureeService.create(dureeDTO);
        return ResponseEntity.created(new URI("/api/duree/" + result.getId()))
                .body(result);
    }

    @Operation(summary = "Modifie une duree", description = "Modifie une duree")
    @PutMapping("/update")
    public ResponseEntity<Duree> update(@Valid @RequestBody DureeDTO dureeDTO) throws URISyntaxException {
        if (dureeDTO.getId() == null) {
            throw new UpdateElementException();
        }
        Duree result = dureeService.update(dureeDTO);
        return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "Recherche une duree via un ID", description = "Recherche une duree via un ID")
    @GetMapping(path = "/{id}")
    public ResponseEntity<Duree> get(@PathVariable(name = "id") Long id) {
        Optional<Duree> result = dureeService.get(id);
        return ResponseEntity.ok().body(result.orElseThrow());
    }

    @Operation(summary = "Liste toutes les durees", description = "Liste toutes les durees")
    @GetMapping("/list-page")
    public ResponseEntity<List<Duree>> findAll(Pageable pageable) {
        Page<Duree> result = dureeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), result);
        return new ResponseEntity<>(result.getContent(), headers, HttpStatus.OK);
    }

    @Operation(summary = "Liste toutes les durees", description = "Liste toutes les durees")
    @GetMapping("/list")
    public ResponseEntity<List<Duree>> findAllList() {
        List<Duree> result = dureeService.findAllList();
        return ResponseEntity.ok().body(result);
    }

    @Operation(summary = "Supprime une duree via un ID", description = "Supprime une duree via un ID")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        dureeService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
    
}
