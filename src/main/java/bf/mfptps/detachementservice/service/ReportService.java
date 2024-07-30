/**
 * 
 */
package bf.mfptps.detachementservice.service;

import java.io.OutputStream;
import java.util.List;

import bf.mfptps.detachementservice.exception.CustomException;
import bf.mfptps.detachementservice.reportdtos.GlobalReportVo;
import bf.mfptps.detachementservice.reportdtos.StatsExportFormatEnumVo;
import bf.mfptps.detachementservice.reportdtos.TotalVo;
import bf.mfptps.detachementservice.service.dto.DemandeDTO;
import com.querydsl.core.types.Predicate;

/**
 * @author aboubacary
 *
 */
public interface ReportService {

	/**
	 * 
	 * @return
	 */
	List<TotalVo> checkTotalGlobal();

	/**
	 * renvoie la liste des totaux de
	 * 
	 * @return
	 */
	List<TotalVo> checkTotalByStructure();

	/**
	 * Exporte la liste des disponibilités en fonction des critères définies
	 * 
	 * @param reportVo
	 * @param predicate
	 * @return
	 * @throws CustomException
	 */

	List<DemandeDTO> checkDemandes(GlobalReportVo reportVo, Predicate predicate) throws CustomException;

	/**
	 * exporte la liste des données dans le format précisé
	 * 
	 * @param reportVo
	 * @param format 
	 * @param stream
	 * @param predicate
	 * @throws CustomException
	 */
	void exportDemandes(OutputStream stream, GlobalReportVo reportVo, StatsExportFormatEnumVo format, Predicate predicate) throws CustomException;

}
