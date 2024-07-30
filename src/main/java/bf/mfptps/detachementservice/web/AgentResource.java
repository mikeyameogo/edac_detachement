package bf.mfptps.detachementservice.web;

import bf.mfptps.detachementservice.config.Constants;
import bf.mfptps.detachementservice.domain.Agent;
import bf.mfptps.detachementservice.exception.CreateNewElementException;
import bf.mfptps.detachementservice.exception.CustomException;
import bf.mfptps.detachementservice.exception.UpdateElementException;
import bf.mfptps.detachementservice.service.AgentService;
import bf.mfptps.detachementservice.service.dto.AffectationDTO;
import bf.mfptps.detachementservice.service.dto.AgentDTO;
import bf.mfptps.detachementservice.util.PaginationUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
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
@RestController
@RequestMapping("api/detachements/agents")
public class AgentResource {

    private final AgentService agentService;

    public AgentResource(AgentService agentService) {
        this.agentService = agentService;
    }

    @Operation(summary = "Ajoute une agent", description = "Ajoute une agent")
    @ApiResponse(responseCode = "201", description = "Agent créée avec succès")
    @ApiResponse(responseCode = "401")
    @ApiResponse(responseCode = "409", description = "Conflict, une donnée similaire existe déjà")
    @ApiResponse(responseCode = "500", description = "Le serveur n'est pas en mesure de traiter la situation qu'il rencontre")
    @PostMapping("/new")
    public ResponseEntity<AgentDTO> create(@RequestBody AgentDTO request) throws URISyntaxException {
        if (request.getId() != null) {
            throw new CreateNewElementException();
        }
        AgentDTO response = agentService.create(request);
        return ResponseEntity.created(new URI("/api/agents")).body(response);
    }

    @Operation(summary = "Modifie une agent", description = "Modifie une agent")
    @PutMapping("/update")
    public ResponseEntity<AgentDTO> update(@RequestBody AgentDTO request) throws URISyntaxException {
        if (request.getId() == null) {
            throw new UpdateElementException();
        }
        AgentDTO response = agentService.update(request);
        return ResponseEntity.ok().body(response);
    }

//    @Operation(summary = "Affecte un agent dans une autre structure", description = "Affecte un agent dans une autre structure")
//    @PostMapping("/affectation-agent")
//    public ResponseEntity<Agent> affecterAgent(@Valid @RequestBody AffectationDTO affectationDTO) throws URISyntaxException {
//        Agent newAgent = agentService.affectationAgent(affectationDTO.getUsername(), affectationDTO.getStructureId());
//        return ResponseEntity.created(new URI("/api/agents/" + newAgent.getMatricule()))
//                .body(newAgent);
//    }

    @Operation(summary = "Recherche une agent via un ID", description = "Recherche une agent via un ID")
    @GetMapping(path = "/{id}")
    public ResponseEntity<Optional<AgentDTO>> get(@PathVariable(name = "id", required = true) Long id) {
        Optional<AgentDTO> agent = agentService.get(id);
        return ResponseEntity.ok().body(agent);
    }

    @Operation(summary = "Recherche une agent via un matricule", description = "Recherche une agent via un matricule")
    @GetMapping("/matricule/{matricule:" + Constants.LOGIN_REGEX + "}")
    public ResponseEntity<Optional<AgentDTO>> getAgent(@PathVariable String matricule) {
        return ResponseEntity.ok().body(agentService.get(matricule));
    }

    @Operation(summary = "Liste toutes les agents", description = "Liste toutes les agents")
    @GetMapping("/list-page")
    public ResponseEntity<List<AgentDTO>> findAll(Pageable pageable) {
        Page<AgentDTO> agents = agentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), agents);
        return new ResponseEntity<>(agents.getContent(), headers, HttpStatus.OK);
    }

    @Operation(summary = "Liste toutes les agents", description = "Liste toutes les agents")
    @GetMapping("/list")
    public ResponseEntity<List<AgentDTO>> findAll() {
        List<AgentDTO> agents = agentService.findAllList();
        return ResponseEntity.ok().body(agents);
    }

    @Operation(summary = "Supprime une agent via un ID", description = "Supprime une agent via un ID")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        agentService.delete(id);
        return ResponseEntity
                .noContent()
                .build();

    }

    /**
     * liste les agents d'une structure donnee
     *
     * @param structureId
     * @param pageable
     * @return
     */
//    @GetMapping("/by-structure/{ids}")
//    public ResponseEntity<List<AgentDTO>> getAllAgentsByStructure(@PathVariable(name = "ids", required = true) Long structureId, Pageable pageable) {
//        if (structureId == null) {
//            throw new CustomException("Veuillez renseigner le paramètre");
//        }
//        final Page<AgentDTO> page = agentService.getAllAgentsByStructure(structureId, pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
//    }

    /**
     * consulte l'agent avec sa structure
     *
     * @param idAgent : idAgent de l'agent
     * @return
     */
//    @GetMapping("/struct/{id}")
//    public ResponseEntity<AgentStructureDTO> getAgentWithStructure(@PathVariable(name = "id", required = true) Long idAgent) {
//        if (idAgent == null) {
//            throw new CustomException("Veuillez renseigner le paramètre");
//        }
//        AgentStructureDTO page = agentService.getAgentWithStructure(idAgent);
//        return new ResponseEntity<>(page, HttpStatus.OK);
//    }
}
