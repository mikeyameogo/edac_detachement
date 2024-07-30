package bf.mfptps.detachementservice.web;

import bf.mfptps.detachementservice.domain.Ministere;
import bf.mfptps.detachementservice.domain.MinistereStructure;
import bf.mfptps.detachementservice.domain.Structure;
import bf.mfptps.detachementservice.exception.UpdateElementException;
import bf.mfptps.detachementservice.service.MinistereStructureService;
import bf.mfptps.detachementservice.service.StructureService;
import bf.mfptps.detachementservice.service.dto.ChangeMinistereDTO;
import bf.mfptps.detachementservice.service.dto.StructureDTO;
import bf.mfptps.detachementservice.util.PaginationUtil;
import jakarta.validation.Valid;
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
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@CrossOrigin("*")
@RestController
@RequestMapping(path = "api/detachements")
public class StructureResource {

    private final StructureService structureService;

    private final MinistereStructureService ministereStructureService;

    public StructureResource(StructureService structureService,
            MinistereStructureService ministereStructureService) {
        this.structureService = structureService;
        this.ministereStructureService = ministereStructureService;
    }

    /**
     *
     * @param structure
     * @return
     * @throws URISyntaxException
     */
    @PostMapping(path = "/structures")
    public ResponseEntity<Structure> create(@Valid @RequestBody StructureDTO structure) throws URISyntaxException {
        Structure structu = structureService.create(structure);
        return ResponseEntity.created(new URI("/api/structures/" + structu.getId())).body(structu);
    }

    @DeleteMapping(path = "/structures/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        structureService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    /**
     *
     *
     * @param structure
     * @return
     * @throws URISyntaxException
     */
    @PutMapping(path = "/structures")
    public ResponseEntity<Structure> updateStructure(@Valid @RequestBody Structure structure) throws URISyntaxException {
        if (structure.getId() == null) {
            throw new UpdateElementException();
        }
        Structure result = structureService.update(structure);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(path = "/ministere-structures/list-page")
    public ResponseEntity<List<MinistereStructure>> findAllMinistereStructure(Pageable pageable) {
        Page<MinistereStructure> structures = ministereStructureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), structures);
        return new ResponseEntity<>(structures.getContent(), headers, HttpStatus.OK);
    }

    /**
     *
     * @param id : id of ministere
     * @return
     */
    @GetMapping(path = "/ministere-structures/{id}")
    public ResponseEntity<List<StructureDTO>> findAllStructuresByMinistere(@PathVariable Long id, Pageable pageable) {
        Page<StructureDTO> structure = ministereStructureService.findAllStructureByMinistere(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), structure);
        return ResponseEntity.ok().headers(headers).body(structure.getContent());
    }

    /**
     *
     * @param id : id of structure
     * @return
     */
    @GetMapping(path = "/structures/ministere/{id}")
    public ResponseEntity<Ministere> getMinistereByStructureId(@PathVariable Long id) {
        Ministere result = structureService.getMinistereByStructureId(id);
        return ResponseEntity.ok().body(result);
    }

    /**
     *
     * @param id : id of ministere
     * @param
     * @return
     */
    @GetMapping(path = "/ministere-structures/list/{id}")
    public ResponseEntity<List<StructureDTO>> findListStructuresByMinistere(@PathVariable Long id) {
        List<StructureDTO> structure = ministereStructureService.findListStructureByMinistere(id);
        return ResponseEntity.ok().body(structure);
    }

    /**
     * Liste de toutes les structures actives (de tous les ministeres)
     *
     * @return
     */
    @GetMapping(path = "/structures/list")
    public ResponseEntity<List<Structure>> findAllStructuresActives() {
        List<Structure> structure = structureService.findAllList();
        return ResponseEntity.ok().body(structure);
    }

    @GetMapping(path = "/structures/{id}")
    public ResponseEntity<Structure> getStructureById(@PathVariable Long id) {
        Optional<Structure> structureFound = structureService.get(id);
        return ResponseEntity.ok().body(structureFound.get());
    }

    /**
     *
     *
     * @param changeMinistereDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping(path = "/structures/change-ministere")
    public ResponseEntity<Structure> changeMinistere(@Valid @RequestBody ChangeMinistereDTO changeMinistereDTO) throws URISyntaxException {
        Structure result = structureService.changementMinistere(changeMinistereDTO);
        return ResponseEntity.ok().body(result);
    }
}
