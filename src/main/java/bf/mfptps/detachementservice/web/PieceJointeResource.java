package bf.mfptps.detachementservice.web;

import bf.mfptps.detachementservice.domain.PieceJointe;
import bf.mfptps.detachementservice.exception.CreateNewElementException;
import bf.mfptps.detachementservice.exception.UpdateElementException;
import bf.mfptps.detachementservice.service.PieceJointeService;
import bf.mfptps.detachementservice.service.dto.PieceJointeDTO;
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
@RequestMapping("api/detachements/piece-jointes")
public class PieceJointeResource {

    private final PieceJointeService pieceJointeService;

    public PieceJointeResource(PieceJointeService pieceJointeService) {
        this.pieceJointeService = pieceJointeService;
    }

    @Operation(summary = "Ajoute une pieceJointe", description = "Ajoute une pieceJointe")
    @ApiResponse(responseCode = "201", description = "PieceJointe créée avec succès")
    @ApiResponse(responseCode = "401")
    @ApiResponse(responseCode = "409", description = "Conflict, une donnée similaire existe déjà")
    @ApiResponse(responseCode = "500", description = "Le serveur n'est pas en mesure de traiter la situation qu'il rencontre")
    @PostMapping("/new")
    public ResponseEntity<PieceJointeDTO> create(@RequestBody PieceJointeDTO request) throws URISyntaxException {
        if (request.getId() != null) {
            throw new CreateNewElementException();
        }
        PieceJointeDTO response = pieceJointeService.create(request);
        return ResponseEntity.created(new URI("/api/pieceJointes")).body(response);
    }

    @Operation(summary = "Modifie une pieceJointe", description = "Modifie une pieceJointe")
    @PutMapping("/update")
    public ResponseEntity<PieceJointeDTO> update(@RequestBody PieceJointeDTO request) throws URISyntaxException {
        if (request.getId() == null) {
            throw new UpdateElementException();
        }
        PieceJointeDTO response = pieceJointeService.update(request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Recherche une pieceJointe via un ID", description = "Recherche une pieceJointe via un ID")
    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<PieceJointeDTO>> get(@PathVariable(name = "id", required = true) Long id) {
        Optional<PieceJointeDTO> pieceJointe = pieceJointeService.get(id);
        return ResponseEntity.ok().body(pieceJointe);
    }

    @Operation(summary = "Liste toutes les pieceJointes", description = "Liste toutes les pieceJointes")
    @GetMapping("/list-page")
    public ResponseEntity<List<PieceJointeDTO>> findAll(Pageable pageable) {
        Page<PieceJointeDTO> pieceJointes = pieceJointeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), pieceJointes);
        return new ResponseEntity<>(pieceJointes.getContent(), headers, HttpStatus.OK);
    }

    @Operation(summary = "Liste toutes les pieceJointes", description = "Liste toutes les pieceJointes")
    @GetMapping("/list")
    public ResponseEntity<List<PieceJointeDTO>> findAll() {
        List<PieceJointeDTO> pieceJointes = pieceJointeService.findAllList();
        return ResponseEntity.ok().body(pieceJointes);
    }

    @Operation(summary = "Liste des pieces jointes d'une demande", description = "Liste des pieces jointes d'une demande")
    @GetMapping("/list/{idDemande}")
    public ResponseEntity<List<PieceJointe>> findAllByDemande(@PathVariable(name = "idDemande", required = true) Long idDemande) {
        List<PieceJointe> pieceJointes = pieceJointeService.findAllByDemande(idDemande);
        return ResponseEntity.ok().body(pieceJointes);
    }

    @Operation(summary = "Supprime une pieceJointe via un ID", description = "Supprime une pieceJointe via un ID")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pieceJointeService.delete(id);
        return ResponseEntity
                .noContent()
                .build();

    }
}
