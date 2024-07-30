package bf.mfptps.detachementservice.web;

import bf.mfptps.detachementservice.exception.CreateNewElementException;
import bf.mfptps.detachementservice.exception.UpdateElementException;
import bf.mfptps.detachementservice.service.TypeDemandeService;
import bf.mfptps.detachementservice.service.dto.TypeDemandeDTO;
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
@RequestMapping("api/detachements/type-demandes")
public class TypeDemandeResource {

    private final TypeDemandeService typeDemandeService;

    public TypeDemandeResource(TypeDemandeService typeDemandeService) {
        this.typeDemandeService = typeDemandeService;
    }

    @Operation(summary = "Ajoute une typeDemande", description = "Ajoute une typeDemande")
    @ApiResponse(responseCode = "201", description = "TypeDemande créée avec succès")
    @ApiResponse(responseCode = "401")
    @ApiResponse(responseCode = "409", description = "Conflict, une donnée similaire existe déjà")
    @ApiResponse(responseCode = "500", description = "Le serveur n'est pas en mesure de traiter la situation qu'il rencontre")
    @PostMapping("/new")
    public ResponseEntity<TypeDemandeDTO> create(@RequestBody TypeDemandeDTO request) throws URISyntaxException {
        if (request.getId() != null) {
            throw new CreateNewElementException();
        }
        TypeDemandeDTO response = typeDemandeService.create(request);
        return ResponseEntity.created(new URI("/api/typeDemandes")).body(response);
    }

    @Operation(summary = "Modifie une typeDemande", description = "Modifie une typeDemande")
    @PutMapping("/update")
    public ResponseEntity<TypeDemandeDTO> update(@RequestBody TypeDemandeDTO request) throws URISyntaxException {
        if (request.getId() == null) {
            throw new UpdateElementException();
        }
        TypeDemandeDTO response = typeDemandeService.update(request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Recherche une typeDemande via un ID", description = "Recherche une typeDemande via un ID")
    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<TypeDemandeDTO>> get(@PathVariable(name = "id", required = true) Long id) {
        Optional<TypeDemandeDTO> typeDemande = typeDemandeService.get(id);
        return ResponseEntity.ok().body(typeDemande);
    }

    @Operation(summary = "Liste toutes les typeDemandes", description = "Liste toutes les typeDemandes")
    @GetMapping("/list-page")
    public ResponseEntity<List<TypeDemandeDTO>> findAll(Pageable pageable) {
        Page<TypeDemandeDTO> typeDemandes = typeDemandeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), typeDemandes);
        return new ResponseEntity<>(typeDemandes.getContent(), headers, HttpStatus.OK);
    }

    @Operation(summary = "Liste toutes les typeDemandes", description = "Liste toutes les typeDemandes")
    @GetMapping("/list")
    public ResponseEntity<List<TypeDemandeDTO>> findAll() {
        List<TypeDemandeDTO> typeDemandes = typeDemandeService.findAllList();
        return ResponseEntity.ok().body(typeDemandes);
    }
    @Operation(summary = "Supprime une typeDemande via un ID", description = "Supprime une typeDemande via un ID")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        typeDemandeService.delete(id);
        return ResponseEntity
                .noContent()
                .build();

    }
}
