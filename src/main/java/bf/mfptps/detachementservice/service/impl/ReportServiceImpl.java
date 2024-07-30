/**
 * 
 */
package bf.mfptps.detachementservice.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;

import bf.mfptps.detachementservice.domain.Demande;
import bf.mfptps.detachementservice.domain.Ministere;
//import bf.mfptps.detachementservice.domain.QDemande;
import bf.mfptps.detachementservice.domain.Structure;
import bf.mfptps.detachementservice.domain.TypeDemande;
import bf.mfptps.detachementservice.exception.CustomException;
import bf.mfptps.detachementservice.reportdata.DemandeVo;
import bf.mfptps.detachementservice.reportdtos.GlobalReportVo;
import bf.mfptps.detachementservice.reportdtos.StatsExportFormatEnumVo;
import bf.mfptps.detachementservice.reportdtos.TotalVo;
import bf.mfptps.detachementservice.repository.DemandeRepository;
import bf.mfptps.detachementservice.repository.MinistereRepository;
import bf.mfptps.detachementservice.repository.StructureRepository;
import bf.mfptps.detachementservice.repository.TypeDemandeRepository;
import bf.mfptps.detachementservice.service.ReportService;
import bf.mfptps.detachementservice.service.dto.AgentDTO;
import bf.mfptps.detachementservice.service.dto.DemandeDTO;
import bf.mfptps.detachementservice.service.mapper.DemandeMapper;
import bf.mfptps.detachementservice.util.AppTools;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

/**
 * @author aboubacary
 *
 */
@Service
public class ReportServiceImpl implements ReportService {

	private final DemandeRepository demandeRepository;

	private final TypeDemandeRepository typeDemandeRepository;

	private final StructureRepository structureRepository;

	private final MinistereRepository ministereRepository;

	private final DemandeMapper mapper;

	@PersistenceContext
	private EntityManager entityManager;

	public ReportServiceImpl(DemandeRepository demandeRepository, TypeDemandeRepository typeDemandeRepository,
			StructureRepository structureRepository, MinistereRepository ministereRepository, DemandeMapper mapper) {

		this.demandeRepository = demandeRepository;
		this.typeDemandeRepository = typeDemandeRepository;
		this.structureRepository = structureRepository;
		this.ministereRepository = ministereRepository;
		this.mapper = mapper;
	}

	@Override
	public List<TotalVo> checkTotalGlobal() {

		List<TotalVo> data = new ArrayList<>();

		// == chargement des types de demandes
		List<TypeDemande> tpDems = typeDemandeRepository.findAll();

		for (TypeDemande tpDem : tpDems) {

			TotalVo totalVo = new TotalVo();
			//
			long total = demandeRepository.countDemandeByTypeDemande(tpDem.getId());

			totalVo.setLibelle(tpDem.getLibelle());
			totalVo.setTotal(total);

			data.add(totalVo);

		}

		return data;
	}

	@Override
	public List<TotalVo> checkTotalByStructure() {
		// initialisation des données résultat
		List<TotalVo> results = new ArrayList<>();

		// Charger la liste des structures
		List<Structure> structures = structureRepository.findAll();

		for (Structure structure : structures) {
			TotalVo totalVo = new TotalVo(); 
			totalVo.setStucture(structure.getLibelle());

			long total = demandeRepository.countDemandeByDestinationId(structure.getId().longValue());
			totalVo.setTotal(total); 
			totalVo.setStucture(structure.getLibelle()); 
			results.add(totalVo);
		}

		return results;
	}

	@Override
	public List<DemandeDTO> checkDemandes(GlobalReportVo reportVo, Predicate predicate) throws CustomException {
		return null;
	}

	/**
	 * 
	 */
	/*
	@Override
	public List<DemandeDTO> checkDemandes(GlobalReportVo reportVo, Predicate predicate) throws CustomException {

		List<DemandeDTO> results = new ArrayList<>();

		BooleanBuilder builder = new BooleanBuilder(predicate);

		QDemande qData = QDemande.demande; 

		Date start = reportVo.getDebut() != null ? AppTools.correctToLowDate(reportVo.getDebut()) : null;
		Date end = reportVo.getFin() != null ? AppTools.correctToUpDate(reportVo.getFin()) : null;

		if (start != null) {
			builder.and(qData.dateDemande.goe(convertToLocalDate(start)));
		}

		if (end != null) {
			builder.and(qData.dateDemande.loe(convertToLocalDate(end)));
		}

		if (reportVo.getAgent() != null) {
			builder.and(qData.agent.id.eq(reportVo.getAgent()));
		} 

		if (reportVo.getTypeDemande() != null) {
			builder.and(qData.typeDemande.id.eq(reportVo.getTypeDemande()));
		} 

		if (reportVo.getStatut() != null) {
			builder.and(qData.statut.eq(reportVo.getStatut()));
		} 

		builder.and(qData.deleted.eq(false));

		List<Demande> list = ImmutableList.copyOf(demandeRepository.findAll(builder.getValue()));

		results = list.stream().map(mapper::toDto).collect(Collectors.toList());

		return results;
	}
*/
	@Override
	public void exportDemandes(OutputStream stream, GlobalReportVo reportVo, StatsExportFormatEnumVo format,
			Predicate predicate) throws CustomException {
		String exportFormat = format.equals(StatsExportFormatEnumVo.PDF) ? "pdf"
				: (format.equals(StatsExportFormatEnumVo.WORD) ? "word" : "excel");
		try {
			List<DemandeDTO> data = checkDemandes(reportVo, predicate);
			exportDemande(data, stream, exportFormat);
		} catch (JRException e) {
			throw new CustomException("Une erreur s'est produite lors de la requête " + e.getMessage());
		}
	}

	private void exportDemande(List<DemandeDTO> data, OutputStream outStream, String format) throws JRException {

		if (data.isEmpty()) {
			throw new JRException("Aucune donnée disponible pour export");
		}

		List<Ministere> ministeres = ministereRepository.findAll();

		Ministere ministere = ministeres.isEmpty() ? null : ministeres.get(0);

		String institutionString = constructInstitutionData(ministere);

		InputStream logo = this.getClass().getResourceAsStream("/armoirie.jpeg");
		InputStream qrCode = null;

		String dateJour = "Imprimé ce jour, " + AppTools.convertDateToShortString(new Date());
		String titre = "LISTE DES DEMANDES IMPRIMEES";

		try {
			String qrData = dateJour + " ";
			BufferedImage qrcode = generateQRCode(qrData);
			ByteArrayOutputStream bis = new ByteArrayOutputStream();
			ImageIO.write(qrcode, "jpeg", bis);
			qrCode = new ByteArrayInputStream(bis.toByteArray());
		} catch (Exception exc) {
			qrCode = this.getClass().getResourceAsStream("/armoirie.jpeg");
		}

		List<DemandeVo> dataValues = consctructDemandeVo(data, institutionString, titre, dateJour, logo, qrCode);

		InputStream dataJasper = this.getClass().getResourceAsStream("/sigepa_demandes.jasper");
		Map<String, Object> params = new HashMap<>();
		JRDataSource source = new JRBeanCollectionDataSource(dataValues);
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(dataJasper);

		if (format.toLowerCase().contains("pdf")) {
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, source);
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
		} else {
			if (format.toLowerCase().contains("excel")) {
				// export to xlsx
				params.put(JRParameter.REPORT_LOCALE, Locale.FRENCH);
				params.put(JRParameter.IS_IGNORE_PAGINATION, true);
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, source);
				JRXlsxExporter exporter = new JRXlsxExporter(); // initialize exporter
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint)); // set compiled report as input
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outStream)); // set output file via path
																								// with filename
				SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
				configuration.setOnePagePerSheet(true); // setup configuration
				configuration.setDetectCellType(true);
				exporter.setConfiguration(configuration); // set configuration
				exporter.exportReport();
			} else {
				// export to word
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, source);
				JRDocxExporter exporter = new JRDocxExporter();
				exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outStream));
				exporter.exportReport();
			}
		}
	}

	protected LocalDate convertToLocalDate(Date dateToConvert) {
		return Instant.ofEpochMilli(dateToConvert.getTime())
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
	}

	/**
	 * 
	 * @param qrtDataValue
	 * @return
	 * @throws WriterException
	 */
	private BufferedImage generateQRCode(String qrtDataValue) throws WriterException {
		QRCodeWriter qrcode = new QRCodeWriter();
		BitMatrix bitMatrix = qrcode.encode(qrtDataValue, BarcodeFormat.QR_CODE, 190, 190);
		return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}

	/**
	 * 
	 * @param data
	 * @param titre
	 * @param dateJour
	 * @param logo
	 * @param qrCode
	 * @return
	 */
	protected List<DemandeVo> consctructDemandeVo(List<DemandeDTO> data, String institutionString, String titre,
			String dateJour, InputStream logo, InputStream qrCode) {

		List<DemandeVo> result = new ArrayList<>();
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		int nbOrdre = 1;

		for (DemandeDTO value : data) {

			DemandeVo vo = new DemandeVo();
			vo.setDateJour(dateJour);
			vo.setDateValidation(value.getDateEffet().format(pattern));
			vo.setLogo(logo);
			vo.setNOrdre(String.valueOf(nbOrdre));
			vo.setTitre(titre);
			vo.setInstitution(institutionString);
			vo.setMatricule(value.getAgent().getMatricule());
			vo.setTypeDemande(value.getTypeDemande().getLibelle());
			vo.setMotif(value.getMotif().getLibelle());

			vo.setNom(conctructAgentData(value.getAgent()));

			result.add(vo);

			nbOrdre++;
		}

		return result;
	}

	protected String conctructAgentData(AgentDTO agent) {
		String result = "";

		result = (agent.getNom() != null ? agent.getNom().toUpperCase() : "")
				.concat((agent.getPrenom() != null ? agent.getPrenom() : ""));

		return result;
	}

	protected String constructInstitutionData(Ministere ministere) {
		String result = "";

		if (ministere != null) {
			result = "<html> <b>" + ministere.getLibelle().toUpperCase() +
					"</b> <br /> " + "*********************  <br />" +
					"<b> SECRETARIAT GENERAL </b> " + "*********************  <br />" +
					" <b> DIRECTION DES RESSOURCES HUMAINES </b> </html>";
		}

		return result;
	}

}
