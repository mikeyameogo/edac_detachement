package bf.mfptps.detachementservice.web;

import bf.mfptps.detachementservice.domain.Historique;
import bf.mfptps.detachementservice.exception.CreateNewElementException;
import bf.mfptps.detachementservice.exception.UpdateElementException;
import bf.mfptps.detachementservice.service.HistoriqueService;
import bf.mfptps.detachementservice.service.dto.HistoriqueDTO;
import bf.mfptps.detachementservice.service.dto.PieceJointeDTO;
import bf.mfptps.detachementservice.util.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
@RequestMapping("api/detachements/positions")
public class HistoriqueResource {

    private final HistoriqueService historiqueService;

    public HistoriqueResource(HistoriqueService historiqueService) {
        this.historiqueService = historiqueService;
    }

    @Operation(summary = "Ajoute une position", description = "Ajoute une position")
    @ApiResponse(responseCode = "201", description = "Position créée avec succès")
    @ApiResponse(responseCode = "401")
    @ApiResponse(responseCode = "409", description = "Conflict, une donnée similaire existe déjà")
    @ApiResponse(responseCode = "500", description = "Le serveur n'est pas en mesure de traiter la situation qu'il rencontre")
    @PostMapping("/new")
    public ResponseEntity<HistoriqueDTO> create(@RequestBody HistoriqueDTO request) throws URISyntaxException {
        if (request.getId() != null) {
            throw new CreateNewElementException();
        }
        HistoriqueDTO response = historiqueService.create(request);
        return ResponseEntity.created(new URI("/api/positions")).body(response);
    }

    @Operation(summary = "Modifie une position", description = "Modifie une position")
    @PutMapping("/update")
    public ResponseEntity<HistoriqueDTO> update(@RequestBody HistoriqueDTO request) throws URISyntaxException {
        if (request.getId() == null) {
            throw new UpdateElementException();
        }
        HistoriqueDTO response = historiqueService.update(request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Recherche une position via un ID", description = "Recherche une position via un ID")
    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<HistoriqueDTO>> get(@PathVariable(name = "id", required = true) Long id) {
        Optional<HistoriqueDTO> position = historiqueService.get(id);
        return ResponseEntity.ok().body(position);
    }

    @Operation(summary = "Liste toutes les positions", description = "Liste toutes les positions")
    @GetMapping("/list-page")
    public ResponseEntity<List<HistoriqueDTO>> findAll(Pageable pageable) {
        Page<HistoriqueDTO> positions = historiqueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), positions);
        return new ResponseEntity<>(positions.getContent(), headers, HttpStatus.OK);
    }

    @Operation(summary = "Liste toutes les positions", description = "Liste toutes les positions")
    @GetMapping("/list")
    public ResponseEntity<List<HistoriqueDTO>> findAll() {
        List<HistoriqueDTO> positions = historiqueService.findAllList();
        return ResponseEntity.ok().body(positions);
    }

    @Operation(summary = "Liste des historiques d'une demande", description = "Liste des historiques d'une demande")
    @GetMapping("/list/{idDemande}")
    public ResponseEntity<List<HistoriqueDTO>> findAllByDemande(@PathVariable(name = "idDemande", required = true) Long idDemande) {
        List<HistoriqueDTO> historiques = historiqueService.findAllByDemande(idDemande);
        return ResponseEntity.ok().body(historiques);
    }

    @Operation(summary = "Supprime une position via un ID", description = "Supprime une position via un ID")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        historiqueService.delete(id);
        return ResponseEntity
                .noContent()
                .build();

    }
}
