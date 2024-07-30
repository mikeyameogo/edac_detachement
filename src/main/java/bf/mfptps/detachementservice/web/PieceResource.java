package bf.mfptps.detachementservice.web;

import bf.mfptps.detachementservice.exception.CreateNewElementException;
import bf.mfptps.detachementservice.exception.UpdateElementException;
import bf.mfptps.detachementservice.service.PieceService;
import bf.mfptps.detachementservice.service.dto.PieceDTO;
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
@RequestMapping("api/detachements/pieces")
public class PieceResource {

    private final PieceService pieceService;

    public PieceResource(PieceService pieceService) {
        this.pieceService = pieceService;
    }

    @Operation(summary = "Ajoute une piece", description = "Ajoute une piece")
    @ApiResponse(responseCode = "201", description = "Piece créée avec succès")
    @ApiResponse(responseCode = "401")
    @ApiResponse(responseCode = "409", description = "Conflict, une donnée similaire existe déjà")
    @ApiResponse(responseCode = "500", description = "Le serveur n'est pas en mesure de traiter la situation qu'il rencontre")
    @PostMapping("/new")
    public ResponseEntity<PieceDTO> create(@RequestBody PieceDTO request) throws URISyntaxException {
        if (request.getId() != null) {
            throw new CreateNewElementException();
        }
        PieceDTO response = pieceService.create(request);
        return ResponseEntity.created(new URI("/api/pieces")).body(response);
    }

    @Operation(summary = "Modifie une piece", description = "Modifie une piece")
    @PutMapping("/update")
    public ResponseEntity<PieceDTO> update(@RequestBody PieceDTO request) throws URISyntaxException {
        if (request.getId() == null) {
            throw new UpdateElementException();
        }
        PieceDTO response = pieceService.update(request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Recherche une piece via un ID", description = "Recherche une piece via un ID")
    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<PieceDTO>> get(@PathVariable(name = "id", required = true) Long id) {
        Optional<PieceDTO> piece = pieceService.get(id);
        return ResponseEntity.ok().body(piece);
    }

    @Operation(summary = "Liste toutes les pieces", description = "Liste toutes les pieces")
    @GetMapping("/list-page")
    public ResponseEntity<List<PieceDTO>> findAll(Pageable pageable) {
        Page<PieceDTO> pieces = pieceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), pieces);
        return new ResponseEntity<>(pieces.getContent(), headers, HttpStatus.OK);
    }

    @Operation(summary = "Liste toutes les pieces", description = "Liste toutes les pieces")
    @GetMapping("/list")
    public ResponseEntity<List<PieceDTO>> findAll() {
        List<PieceDTO> pieces = pieceService.findAllList();
        return ResponseEntity.ok().body(pieces);
    }

    @Operation(summary = "Supprime une piece via un ID", description = "Supprime une piece via un ID")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        pieceService.delete(id);
        return ResponseEntity
                .noContent()
                .build();

    }
}
