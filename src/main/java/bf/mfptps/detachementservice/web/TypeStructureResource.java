/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.web;

import bf.mfptps.detachementservice.domain.TypeStructure;
import bf.mfptps.detachementservice.exception.UpdateElementException;
import bf.mfptps.detachementservice.service.TypeStructureService;
import bf.mfptps.detachementservice.service.dto.TypeStructureDTO;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@CrossOrigin("*")
@RestController
@RequestMapping(path = "api/detachements/type-structures")
public class TypeStructureResource {

    private final TypeStructureService typeStructureService;

    public TypeStructureResource(TypeStructureService typeStructureService) {
        this.typeStructureService = typeStructureService;
    }

    @PostMapping()
    public ResponseEntity<TypeStructure> create(@Valid @RequestBody TypeStructureDTO typeStructureDTO) throws URISyntaxException {
        TypeStructure result = typeStructureService.create(typeStructureDTO);
        return ResponseEntity.created(new URI("/api/type-structures/" + result.getId()))
                .body(result);
    }

    @PutMapping()
    public ResponseEntity<TypeStructure> update(@Valid @RequestBody TypeStructure type) throws URISyntaxException {
        if (type.getId() == null) {
            throw new UpdateElementException();
        }
        TypeStructure result = typeStructureService.update(type);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<TypeStructure> get(@PathVariable(name = "id") Long id) {
        Optional<TypeStructure> result = typeStructureService.get(id);
        return ResponseEntity.ok().body(result.orElseThrow());
    }

    @GetMapping(path = "/list-page")
    public ResponseEntity<List<TypeStructure>> findAll(Pageable pageable) {
        Page<TypeStructure> result = typeStructureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), result);
        return new ResponseEntity<>(result.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<TypeStructure>> findAllList() {
        List<TypeStructure> result = typeStructureService.findAllList();
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        typeStructureService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
