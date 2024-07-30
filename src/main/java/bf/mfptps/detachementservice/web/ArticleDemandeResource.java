package bf.mfptps.detachementservice.web;

import bf.mfptps.detachementservice.exception.CreateNewElementException;
import bf.mfptps.detachementservice.exception.UpdateElementException;
import bf.mfptps.detachementservice.service.ArticleDemandeService;
import bf.mfptps.detachementservice.service.dto.AmpliationDemandeDTO;
import bf.mfptps.detachementservice.service.dto.ArticleDemandeDTO;
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
 * Created by Zak TEGUERA on 19/12/2023.
 * <teguera.zakaria@gmail.com>
 */
@CrossOrigin("*")
@RestController
@RequestMapping("api/detachements/article-demandes")
public class ArticleDemandeResource {

    private final ArticleDemandeService articleDemandeService;

    public ArticleDemandeResource(ArticleDemandeService articleDemandeService) {
        this.articleDemandeService = articleDemandeService;
    }

    @Operation(summary = "Ajoute un articleDemande", description = "Ajoute un articleDemande")
    @ApiResponse(responseCode = "201", description = "ArticleDemande créée avec succès")
    @ApiResponse(responseCode = "401")
    @ApiResponse(responseCode = "409", description = "Conflict, une donnée similaire existe déjà")
    @ApiResponse(responseCode = "500", description = "Le serveur n'est pas en mesure de traiter la situation qu'il rencontre")
    @PostMapping("/new")
    public ResponseEntity<ArticleDemandeDTO> create(@RequestBody ArticleDemandeDTO request) throws URISyntaxException {
        if (request.getId() != null) {
            throw new CreateNewElementException();
        }
        ArticleDemandeDTO response = articleDemandeService.create(request);
        return ResponseEntity.created(new URI("/api/articleDemandes")).body(response);
    }

    @Operation(summary = "Modifie une articleDemande", description = "Modifie une articleDemande")
    @PutMapping("/update")
    public ResponseEntity<ArticleDemandeDTO> update(@RequestBody ArticleDemandeDTO request) throws URISyntaxException {
        if (request.getId() == null) {
            throw new UpdateElementException();
        }
        ArticleDemandeDTO response = articleDemandeService.update(request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Recherche une articleDemande via un ID", description = "Recherche une articleDemande via un ID")
    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<ArticleDemandeDTO>> get(@PathVariable(name = "id", required = true) Long id) {
        Optional<ArticleDemandeDTO> articleDemande = articleDemandeService.get(id);
        return ResponseEntity.ok().body(articleDemande);
    }

    @Operation(summary = "Liste toutes les articleDemandes", description = "Liste toutes les articleDemandes")
    @GetMapping("/list-page")
    public ResponseEntity<List<ArticleDemandeDTO>> findAll(Pageable pageable) {
        Page<ArticleDemandeDTO> articleDemandes = articleDemandeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), articleDemandes);
        return new ResponseEntity<>(articleDemandes.getContent(), headers, HttpStatus.OK);
    }

    @Operation(summary = "Liste toutes les articleDemandes", description = "Liste toutes les articleDemandes")
    @GetMapping("/list")
    public ResponseEntity<List<ArticleDemandeDTO>> findAll() {
        List<ArticleDemandeDTO> articleDemandes = articleDemandeService.findAllList();
        return ResponseEntity.ok().body(articleDemandes);
    }

    @Operation(summary = "Liste tous les articles spécifiques à une demande", description = "Liste tous les articles spécifiques à une demande")
    @GetMapping("/list/{idDemande}")
    public ResponseEntity<List<ArticleDemandeDTO>> findAllByIdDemande(@PathVariable(name = "idDemande", required = true) Long idDemande) {
        List<ArticleDemandeDTO> articleDemandes = articleDemandeService.findAllByDemandeId(idDemande);
        return ResponseEntity.ok().body(articleDemandes);
    }

    @Operation(summary = "Supprime une articleDemande via un ID", description = "Supprime une articleDemande via un ID")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        articleDemandeService.delete(id);
        return ResponseEntity
                .noContent()
                .build();

    }
}
