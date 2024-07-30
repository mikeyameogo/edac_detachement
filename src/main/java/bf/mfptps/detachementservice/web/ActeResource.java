package bf.mfptps.detachementservice.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import bf.mfptps.detachementservice.exception.CreateNewElementException;
import bf.mfptps.detachementservice.exception.UpdateElementException;
import bf.mfptps.detachementservice.service.ActeService;
import bf.mfptps.detachementservice.service.dto.ActeDTO;
import bf.mfptps.detachementservice.util.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

/**
 * Created by Zak TEGUERA on 29/09/2023.
 * <teguera.zakaria@gmail.com>
 */
@CrossOrigin("*")
@RestController
@RequestMapping("api/detachements/actes")
public class ActeResource {

    private final ActeService acteService;

    public ActeResource(ActeService acteService) {
        this.acteService = acteService;
    }

    @Operation(summary = "Ajoute un acte", description = "Ajoute un acte")
    @ApiResponse(responseCode = "201", description = "Acte créée avec succès")
    @ApiResponse(responseCode = "401")
    @ApiResponse(responseCode = "409", description = "Conflict, une donnée similaire existe déjà")
    @ApiResponse(responseCode = "500", description = "Le serveur n'est pas en mesure de traiter la situation qu'il rencontre")
    @PostMapping("/new")
    public ResponseEntity<ActeDTO> create(@RequestBody ActeDTO request) throws URISyntaxException {
        if (request.getId() != null) {
            throw new CreateNewElementException();
        }
        ActeDTO response = acteService.create(request);
        return ResponseEntity.created(new URI("/api/actes")).body(response);
    }

    @Operation(summary = "Modifie un acte", description = "Modifie un acte")
    @PutMapping("/update")
    public ResponseEntity<ActeDTO> update(@RequestBody ActeDTO request) throws URISyntaxException {
        if (request.getId() == null) {
            throw new UpdateElementException();
        }
        ActeDTO response = acteService.update(request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Recherche un acte via un ID", description = "Recherche un acte via un ID")
    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<ActeDTO>> get(@PathVariable(name = "id", required = true) Long id) {
        Optional<ActeDTO> acte = acteService.get(id);
        return ResponseEntity.ok().body(acte);
    }

    @Operation(summary = "Liste tous les actes", description = "Liste tous les actes")
    @GetMapping("/list-page")
    public ResponseEntity<List<ActeDTO>> findAll(Pageable pageable) {
        Page<ActeDTO> actes = acteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), actes);
        return new ResponseEntity<>(actes.getContent(), headers, HttpStatus.OK);
    }

    @Operation(summary = "Liste tous les actes", description = "Liste tous les actes")
    @GetMapping("/list")
    public ResponseEntity<List<ActeDTO>> findAll() {
        List<ActeDTO> actes = acteService.findAllList();
        return ResponseEntity.ok().body(actes);
    }

    @Operation(summary = "Supprime un acte via un ID", description = "Supprime un acte via un ID")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        acteService.delete(id);
        return ResponseEntity
                .noContent()
                .build();

    }
    
    @Operation(summary = "Télécharge un acte via un ID", description = "Télécharge un acte via un ID")
    @GetMapping(path ="/download/{dataId}/{disponibilite}")
    public ResponseEntity<Resource> download(@PathVariable(name = "dataId", required = true) Long dataId, @PathVariable(name = "disponibilite", required = true) Boolean disponibilite) {
    	ByteArrayResource resource = acteService.downloadActe(dataId, disponibilite);
    	 return ResponseEntity.ok()
                 .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=myfile.txt")
                 .contentType(MediaType.APPLICATION_OCTET_STREAM)
                 .contentLength(resource.getByteArray().length)
                 .body(resource);
    }
    
    
    @Operation(summary = "Upload lo fichier d'un acte via un ID", description = "Upload le fichier d'un acte via un ID")
    @PostMapping(path = "upload/{dataId}/{disponibilite}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> uploadActe(@RequestPart(value = "fileData", required = false) MultipartFile fileData, @PathVariable(name = "dataId", required = true) Long dataId, @PathVariable(name = "disponibilite", required = true) Boolean disponibilite) throws URISyntaxException {
    	String result = acteService.uploadActe(fileData, dataId, disponibilite);
    	return ResponseEntity.ok().body(result);
    }
      
    
    @Operation(summary = "Signature d'un acte via un ID", description = "Opération qui consiste à signer un acte via un ID")
    @GetMapping(path = "signer/{dataId}/{disponibilite}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> signerProjetArrete(@PathVariable(name = "dataId", required = true) Long dataId, @PathVariable(name = "disponibilite", required = true) Boolean disponibilite) throws URISyntaxException {
    	String result = acteService.signerProjetArrete(dataId, disponibilite);
    	return ResponseEntity.ok().body(result);
    }
}
