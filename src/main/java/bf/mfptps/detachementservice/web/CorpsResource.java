package bf.mfptps.detachementservice.web;

import bf.mfptps.detachementservice.exception.CreateNewElementException;
import bf.mfptps.detachementservice.exception.UpdateElementException;
import bf.mfptps.detachementservice.service.CorpsService;
import bf.mfptps.detachementservice.service.dto.CorpsDTO;
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
@RequestMapping("api/detachements/corps")
public class CorpsResource {

    private final CorpsService corpsService;

    public CorpsResource(CorpsService corpsService) {
        this.corpsService = corpsService;
    }

    @Operation(summary = "Ajoute une corps", description = "Ajoute une corps")
    @ApiResponse(responseCode = "201", description = "Corps créée avec succès")
    @ApiResponse(responseCode = "401")
    @ApiResponse(responseCode = "409", description = "Conflict, une donnée similaire existe déjà")
    @ApiResponse(responseCode = "500", description = "Le serveur n'est pas en mesure de traiter la situation qu'il rencontre")
    @PostMapping("/new")
    public ResponseEntity<CorpsDTO> create(@RequestBody CorpsDTO request) throws URISyntaxException {
        if (request.getId() != null) {
            throw new CreateNewElementException();
        }
        CorpsDTO response = corpsService.create(request);
        return ResponseEntity.created(new URI("/api/corpss")).body(response);
    }

    @Operation(summary = "Modifie une corps", description = "Modifie une corps")
    @PutMapping("/update")
    public ResponseEntity<CorpsDTO> update(@RequestBody CorpsDTO request) throws URISyntaxException {
        if (request.getId() == null) {
            throw new UpdateElementException();
        }
        CorpsDTO response = corpsService.update(request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Recherche une corps via un ID", description = "Recherche une corps via un ID")
    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<CorpsDTO>> get(@PathVariable(name = "id", required = true) Long id) {
        Optional<CorpsDTO> corps = corpsService.get(id);
        return ResponseEntity.ok().body(corps);
    }

    @Operation(summary = "Liste toutes les corpss", description = "Liste toutes les corpss")
    @GetMapping("/list-page")
    public ResponseEntity<List<CorpsDTO>> findAll(Pageable pageable) {
        Page<CorpsDTO> corpss = corpsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), corpss);
        return new ResponseEntity<>(corpss.getContent(), headers, HttpStatus.OK);
    }

    @Operation(summary = "Liste toutes les corpss", description = "Liste toutes les corpss")
    @GetMapping("/list")
    public ResponseEntity<List<CorpsDTO>> findAll() {
        List<CorpsDTO> corpss = corpsService.findAllList();
        return ResponseEntity.ok().body(corpss);
    }

    @Operation(summary = "Supprime une corps via un ID", description = "Supprime une corps via un ID")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        corpsService.delete(id);
        return ResponseEntity
                .noContent()
                .build();

    }
}
