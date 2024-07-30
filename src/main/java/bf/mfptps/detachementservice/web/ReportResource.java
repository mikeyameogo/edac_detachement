/**
 * 
 */
package bf.mfptps.detachementservice.web;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.Predicate;

import bf.mfptps.detachementservice.domain.Demande;
import bf.mfptps.detachementservice.exception.CustomException;
import bf.mfptps.detachementservice.reportdtos.GlobalReportVo;
import bf.mfptps.detachementservice.reportdtos.StatsExportFormatEnumVo;
import bf.mfptps.detachementservice.reportdtos.TotalVo;
import bf.mfptps.detachementservice.service.ReportService;
import bf.mfptps.detachementservice.service.dto.DemandeDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author aboubacary
 *
 */
@CrossOrigin("*")
@RestController
@RequestMapping("api/detachements/reports")
public class ReportResource {

	private final ReportService service;

	public ReportResource(ReportService service) {
		this.service = service;
	}
	
	/**
     * 
     * @return
     */
    @Operation(summary = "Statistiques des statistiques globales sur les demandes", description = "compile les statistiques sur les demandes")
    @GetMapping(path = "/check-total-globale")
    public ResponseEntity<List<TotalVo>> checkTotalGlobal() {
        List<TotalVo> data = service.checkTotalGlobal();
        return ResponseEntity.ok().body(data);
    }

    /**
     * 
     * @param nombreAnnee
     * @return
     * @throws ParseException
     */
    @Operation(summary = "Statistiques de demandes traitées par structure", description = "compile les statistiques des demandes traitées par chaque structure")
    @GetMapping(path = "/check-total-by-structure")
    public ResponseEntity<List<TotalVo>> checkTotalByStructure() throws ParseException {
        List<TotalVo> data = service.checkTotalByStructure();
        return ResponseEntity.ok().body(data);
    }
    
    
    @Operation(summary = "Recherche des déclarations", description = "Recherche une liste de déclarations en fonction des critères fournis en paramètre")
    @PostMapping(path = "/check-demandes")
    public ResponseEntity<List<DemandeDTO>> checkDemandes(@Valid @RequestBody GlobalReportVo dataVo, @ApiIgnore @QuerydslPredicate(root = Demande.class) Predicate predicate) {
    	List<DemandeDTO> result = service.checkDemandes(dataVo, predicate);
        return ResponseEntity.ok().body(result);
    }
    
    
    @Operation(summary = "Export les données des demandes", description = "Exporte une liste de demandes en fonction des critères fournis en paramètre")
    @PostMapping(path = "/export-demandes/{format}")
    public void exportDemandes(HttpServletResponse reponse, @Valid @RequestBody GlobalReportVo dataVo,  @PathVariable(name = "format", required = true) String format, @ApiIgnore @QuerydslPredicate(root = Demande.class) Predicate predicate) throws IOException, CustomException, JRException {
    	 
    	 if(format.toLowerCase().contains("pdf")) { 
    		 reponse.setContentType("application/pdf");
             reponse.setHeader("Content-Disposition", "attachment; filename=\"demandes_export_pdf.pdf");
    	 }else {
    		 if(format.toLowerCase().contains("word")) { 
    			 reponse.setContentType("application/octet-stream");
    	         reponse.setHeader("Content-Disposition", "attachment; filename=\"demandes_export_word.docx"); 
    		 }else {
    			 reponse.setContentType("application/x-download");
    			 reponse.setHeader("Content-Disposition", "attachment; filename=\"demandes_export_xlsx.xlsx"); 
    		 }
    	 } 
    	 
    	 OutputStream stream = reponse.getOutputStream();
    	 service.exportDemandes(stream, dataVo, StatsExportFormatEnumVo.convert(format), predicate);
    	 
    } 
}
