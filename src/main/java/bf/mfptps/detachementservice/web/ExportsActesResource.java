/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.web;

import bf.mfptps.detachementservice.service.ExportsActesService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@RestController
@RequestMapping(path = "api/detachements/exports")
public class ExportsActesResource {

    private final ExportsActesService exportsActesService;

    public ExportsActesResource(ExportsActesService exportsActesService) {
        this.exportsActesService = exportsActesService;
    }

    /**
     *
     * @param response
     * @param idDemande
     * @throws IOException
     * @throws Exception
     */
    @Operation(summary = "Exporte la demande taitée via un ID", description = "Exporte la demande taitée via un ID")
    @GetMapping(value = "/demande-traitee/{id}")
    public void exporterDemandeTraitee(HttpServletResponse response, @PathVariable(name = "id", required = true) Long idDemande) throws IOException, Exception {
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"DEMANDE_EDAC_" + idDemande + ".pdf" + "\""));
        OutputStream outStream = response.getOutputStream();
        exportsActesService.printDemandeTraitee(idDemande, outStream);
    }

    /**
     *
     * @param response
     * @param idDemande
     * @throws IOException
     * @throws Exception
     */
    @Operation(summary = "Exporte le recepisse d'une demande via un ID", description = "Exporte le recepisse d'une demande via un ID")
    @GetMapping(value = "/recepisse-demande/{id}")
    public void exporterRecepisseDemande(HttpServletResponse response, @PathVariable(name = "id", required = true) Long idDemande) throws IOException, Exception {
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"RECEPISSE_EDAC_" + idDemande + ".pdf" + "\""));
        OutputStream outStream = response.getOutputStream();
        exportsActesService.printRecepisseDemande(idDemande, outStream);
    }

    /**
     *
     * @param response
     * @param idDemande
     * @throws IOException
     * @throws Exception
     */
    @Operation(summary = "Exporte le formulaire d'une demande via un ID", description = "Exporte le formulaire d'une demande via un ID")
    @GetMapping(value = "/formulaire-demande/{id}")
    public void exporterFormulaireDemande(HttpServletResponse response, @PathVariable(name = "id", required = true) Long idDemande) throws IOException, Exception {
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"FORMULAIRE_DEMANDE_" + idDemande + ".pdf" + "\""));
        OutputStream outStream = response.getOutputStream();
        exportsActesService.printFormulaireDemande(idDemande, outStream);
    }

    /**
     *
     * @param response
     * @param idDemande
     * @param isRegularisation
     * @throws IOException
     * @throws Exception
     */
    @Operation(summary = "Exporte le projet d'arreté de détachement via un ID", description = "Exporte le projet d'arreté de détachement via un ID")
    @GetMapping(value = "/arrete-detachement/{id}/{isRegularisation}")
    public void exporterArreteDetachement(HttpServletResponse response, @PathVariable(name = "id", required = true) Long idDemande, @PathVariable(name = "isRegularisation", required = true) boolean isRegularisation) throws IOException, Exception {
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"ARRETE_DETACHEMENT_" + idDemande + ".pdf" + "\""));
        OutputStream outStream = response.getOutputStream();
        exportsActesService.printArreteDetachement(idDemande, isRegularisation, outStream);
    }
}
