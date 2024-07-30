package bf.mfptps.detachementservice.web;

import bf.mfptps.detachementservice.exception.CreateNewElementException;
import bf.mfptps.detachementservice.exception.UpdateElementException;
import bf.mfptps.detachementservice.service.ArticleService;
import bf.mfptps.detachementservice.service.dto.ArticleDTO;
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
@RequestMapping("api/detachements/articles")
public class ArticleResource {

    private final ArticleService articleService;

    public ArticleResource(ArticleService articleService) {
        this.articleService = articleService;
    }

    @Operation(summary = "Ajoute une article", description = "Ajoute une article")
    @ApiResponse(responseCode = "201", description = "Article créée avec succès")
    @ApiResponse(responseCode = "401")
    @ApiResponse(responseCode = "409", description = "Conflict, une donnée similaire existe déjà")
    @ApiResponse(responseCode = "500", description = "Le serveur n'est pas en mesure de traiter la situation qu'il rencontre")
    @PostMapping("/new")
    public ResponseEntity<ArticleDTO> create(@RequestBody ArticleDTO request) throws URISyntaxException {
        if (request.getId() != null) {
            throw new CreateNewElementException();
        }
        ArticleDTO response = articleService.create(request);
        return ResponseEntity.created(new URI("/api/articles")).body(response);
    }

    @Operation(summary = "Modifie une article", description = "Modifie une article")
    @PutMapping("/update")
    public ResponseEntity<ArticleDTO> update(@RequestBody ArticleDTO request) throws URISyntaxException {
        if (request.getId() == null) {
            throw new UpdateElementException();
        }
        ArticleDTO response = articleService.update(request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Recherche une article via un ID", description = "Recherche une article via un ID")
    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<ArticleDTO>> get(@PathVariable(name = "id", required = true) Long id) {
        Optional<ArticleDTO> article = articleService.get(id);
        return ResponseEntity.ok().body(article);
    }

    @Operation(summary = "Liste toutes les articles", description = "Liste toutes les articles")
    @GetMapping("/list-page")
    public ResponseEntity<List<ArticleDTO>> findAll(Pageable pageable) {
        Page<ArticleDTO> articles = articleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), articles);
        return new ResponseEntity<>(articles.getContent(), headers, HttpStatus.OK);
    }

    @Operation(summary = "Liste toutes les articles", description = "Liste toutes les articles")
    @GetMapping("/list")
    public ResponseEntity<List<ArticleDTO>> findAll() {
        List<ArticleDTO> articles = articleService.findAllList();
        return ResponseEntity.ok().body(articles);
    }

    @Operation(summary = "Supprime une article via un ID", description = "Supprime une article via un ID")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        articleService.delete(id);
        return ResponseEntity
                .noContent()
                .build();

    }
}
