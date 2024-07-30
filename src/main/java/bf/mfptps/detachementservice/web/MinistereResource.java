package bf.mfptps.detachementservice.web;

import bf.mfptps.detachementservice.domain.Ministere;
import bf.mfptps.detachementservice.exception.UpdateElementException;
import bf.mfptps.detachementservice.service.MinistereService;
import bf.mfptps.detachementservice.service.dto.MinistereDTO;
import bf.mfptps.detachementservice.util.PaginationUtil;
import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@CrossOrigin("*")
@RestController
@RequestMapping(path = "api/detachements")
public class MinistereResource {

    private final MinistereService ministereService;

    public MinistereResource(MinistereService ministereService) {
        this.ministereService = ministereService;
    }

    /**
     *      *
     * @param ministere
     * @return
     * @throws URISyntaxException
     */
    @PostMapping(path = "/ministeres")
    public ResponseEntity<Ministere> create(@Valid @RequestBody MinistereDTO ministere) throws URISyntaxException {
        Ministere min = ministereService.create(ministere);
        return ResponseEntity.created(new URI("/api/ministeres/" + min.getId()))
                .body(min);
    }

    /**
     *      *
     * @param ministere
     * @return
     * @throws URISyntaxException
     */
    @PutMapping(path = "/ministeres")
    public ResponseEntity<Ministere> updateMinistere(@Valid @RequestBody Ministere ministere) throws URISyntaxException {
        if (ministere.getId() == null) {
            throw new UpdateElementException();
        }
        Ministere result = ministereService.update(ministere);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(path = "/ministeres/{code}")
    public ResponseEntity<Ministere> getMinistere(@PathVariable(name = "code") String code) {
        Optional<Ministere> ministere = ministereService.get(code);
        return ResponseEntity.ok().body(ministere.get());
    }

    @GetMapping(path = "/ministeres/list-page")
    public ResponseEntity<List<Ministere>> findAllMinisteres(Pageable pageable) {
        Page<Ministere> minsiteres = ministereService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), minsiteres);
        return new ResponseEntity<>(minsiteres.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/ministeres/list")
    public ResponseEntity<List<Ministere>> findAllList() {
        List<Ministere> minsiteres = ministereService.findAllList();
        return ResponseEntity.ok().body(minsiteres);
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping(path = "/ministeres/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ministereService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }

}
