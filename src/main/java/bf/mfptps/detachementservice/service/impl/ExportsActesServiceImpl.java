/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.service.impl;

import bf.mfptps.detachementservice.domain.*;
import bf.mfptps.detachementservice.enums.EStatutDemande;
import bf.mfptps.detachementservice.exception.CustomException;
import bf.mfptps.detachementservice.reportdata.ArreteDetachementRD;
import bf.mfptps.detachementservice.reportdata.ArticleRD;
import bf.mfptps.detachementservice.reportdata.DemandeTraiteeRD;
import bf.mfptps.detachementservice.reportdata.FormulaireRD;
import bf.mfptps.detachementservice.reportdata.RecepisseRD;
import bf.mfptps.detachementservice.reportdata.VisaRD;
import bf.mfptps.detachementservice.repository.ArticleRepository;
import bf.mfptps.detachementservice.repository.DemandeRepository;
import bf.mfptps.detachementservice.repository.HistoriqueRepository;
import bf.mfptps.detachementservice.repository.MinistereStructureRepository;
import bf.mfptps.detachementservice.repository.PieceJointeRepository;
import bf.mfptps.detachementservice.repository.VisaRepository;
import bf.mfptps.detachementservice.service.ExportsActesService;
import bf.mfptps.detachementservice.util.AppUtil;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@Service
public class ExportsActesServiceImpl implements ExportsActesService {

    private final DemandeRepository demandeRepository;

    private final MinistereStructureRepository ministereStructureRepository;

    private final VisaRepository visaRepository;

    private final HistoriqueRepository historiqueRepository;

    private final ArticleRepository articleRepository;

    private final PieceJointeRepository pieceJointeRepository;

    private final ResourceLoader resourceLoader;

    public ExportsActesServiceImpl(DemandeRepository demandeRepository,
            MinistereStructureRepository ministereStructureRepository,
            VisaRepository visaRepository,
            HistoriqueRepository historiqueRepository,
            ArticleRepository articleRepository,
            PieceJointeRepository pieceJointeRepository,
            ResourceLoader resourceLoader) {
        this.demandeRepository = demandeRepository;
        this.ministereStructureRepository = ministereStructureRepository;
        this.visaRepository = visaRepository;
        this.historiqueRepository = historiqueRepository;
        this.articleRepository = articleRepository;
        this.pieceJointeRepository = pieceJointeRepository;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void printDemandeTraitee(Long demandeId, OutputStream outputStream) {
        log.info("Export de la demande traitée : {}", demandeId);

        try {
            //recherche de la demande
            Demande d = demandeRepository.findById(demandeId).orElseThrow(() -> new CustomException("Demande d'ID " + demandeId + " inexistante en base de données."));
            List<PieceJointe> pieces = pieceJointeRepository.findByDemandeId(d.getId());
            if (pieces.isEmpty() || pieces == null) {
                throw new CustomException("Aucune pièce fournie lors de la soumission de la demande.");
            }
            //recherche du ministere et de la structure de l'agent demandeur
            Structure s = new Structure();
            Ministere m = new Ministere();
            String cijoint = "";
            List<Historique> avisSH = null;
            List<Historique> avisDRH = null;
            List<Historique> avisSG = null;
            s = d.getAgent().getStructure();
            m = s.getMinistere();
            avisSH = historiqueRepository.findAvisSHSortByDateAvis(d.getId());
            avisDRH = historiqueRepository.findAvisDRHSortByDateAvis(d.getId());
            avisSG = historiqueRepository.findAvisSGSortByDateAvis(d.getId());
            String titre = m.getCivilite().getLabel() + " le " + m.getTitre();
            String formuleAppelPolitesse = m.getCivilite().getLabel() + " le "
                    + (m.getTitre().toLowerCase().contains("ministre") && m.isMinistreEtat() ? "Ministre d'Etat"
                    : (m.getTitre().toLowerCase().contains("ministre") && !m.isMinistreEtat() ? "Ministre" : m.getTitre()));
            String objet = null;
            String contenu = "J’ai l’honneur de solliciter de votre très haute bienveillance ";
            int totalPiece = pieces.size();
            int i = totalPiece;
            for (PieceJointe p : pieces) {
                i--;
                cijoint += " - " + p.getLibelle() + (i > 0 ? " ;<br/>" : ".");
            }
            if (d.getTypeDemande().getLibelle().toLowerCase().contains("nouvelle")) {
                objet = "Détachement";
                contenu += "un détachement auprès du/de la "
                        + (d.getDestination() != null ? d.getDestination().getLibelle() : ".............")
                        + " pour compter du " + (d.getDateEffet() != null ? AppUtil.convertDatWithFullMonthToString(Date.from(d.getDateEffet().atStartOfDay(ZoneId.systemDefault()).toInstant())) : ".............")
                        + ".";
            }
            if (d.getTypeDemande().getLibelle().toLowerCase().contains("renouvellement")) {
                objet = "Renouvellement de détachement";
                contenu += "un renouvellement de mon détachement auprès du/de la "
                        + (d.getDestination() != null ? d.getDestination().getLibelle() : ".............")
                        + " pour compter du " + (d.getDateEffet() != null ? AppUtil.convertDatWithFullMonthToString(Date.from(d.getDateEffet().atStartOfDay(ZoneId.systemDefault()).toInstant())) : ".............")
                        + ".";
            }
            if (d.getTypeDemande().getLibelle().toLowerCase().contains("fin")) {
                objet = "Fin de détachement";
                contenu += "une fin de mon détachement auprès du/de la "
                        + (d.getDestination() != null ? d.getDestination().getLibelle() : ".............")
                        + " pour compter du " + (d.getDateEffet() != null ? AppUtil.convertDatWithFullMonthToString(Date.from(d.getDateEffet().atStartOfDay(ZoneId.systemDefault()).toInstant())) : ".............")
                        + ".";
            }
            if (d.getTypeDemande().getLibelle().toLowerCase().contains("annulation")) {
                objet = "Annulation de détachement";
                contenu += "une annulation de mon détachement auprès du/de la "
                        + (d.getDestination() != null ? d.getDestination().getLibelle() : ".............")
                        + " pour compter du " + (d.getDateEffet() != null ? AppUtil.convertDatWithFullMonthToString(Date.from(d.getDateEffet().atStartOfDay(ZoneId.systemDefault()).toInstant())) : ".............")
                        + ".";
            }
            if (d.getTypeDemande().getLibelle().toLowerCase().contains("rectificatif")) {
                objet = "Rectificatif de détachement";
                contenu += "un rectificatif de mon détachement auprès du/de la "
                        + (d.getDestination() != null ? d.getDestination().getLibelle() : ".............")
                        + " pour compter du " + (d.getDateEffet() != null ? AppUtil.convertDatWithFullMonthToString(Date.from(d.getDateEffet().atStartOfDay(ZoneId.systemDefault()).toInstant())) : ".............")
                        + ".";
            }
            Image qrCodeData = AppUtil.constructQRCode("DEMANDE N° " + d.getNumero());
            // conteneur de données de base à imprimer
            DemandeTraiteeRD dataExport = new DemandeTraiteeRD(
                    (d.getAgent() != null ? d.getAgent().getNom() + " " + d.getAgent().getPrenom() : null),
                    (d.getAgent() != null ? d.getAgent().getMatricule() : null),
                    "....................",/*ici emploi du demandeur*/
                    (s != null ? s.getLibelle() : null),
                    (d.getAgent() != null ? d.getAgent().getTelephone() : null),
                    (d.getAgent() != null ? d.getAgent().getEmail() : null),
                    (d.getDateDemande() != null ? "Ouagadougou, le " + AppUtil.convertDatWithFullMonthToString(Date.from(d.getDateDemande().atStartOfDay(ZoneId.systemDefault()).toInstant())) : null),
                    (titre != null ? titre : null),
                    d.getNumero(),
                    objet,
                    formuleAppelPolitesse,
                    contenu,
                    (cijoint == null ? null : cijoint),
                    (avisSH == null || avisSH.isEmpty() ? null : "Vu le " + AppUtil.convertDateToShort(Date.from(avisSH.get(0).getDateAvis())) + " ; " + avisSH.get(0).getAvis()),
                    (avisDRH == null || avisDRH.isEmpty() ? null : "Vu le " + AppUtil.convertDateToShort(Date.from(avisDRH.get(0).getDateAvis())) + " ; " + avisDRH.get(0).getAvis()),
                    (avisSG == null || avisSG.isEmpty() ? null : "Vu le " + AppUtil.convertDateToShort(Date.from(avisSG.get(0).getDateAvis())) + " ; " + avisSG.get(0).getAvis()),
                    (avisSG == null || avisSG.isEmpty() ? null : avisSG.get(0).getAvis()),
                    (m != null ? m.getLibelle() : null),
                    qrCodeData);

            InputStream inputStream = this.getClass().getResourceAsStream("/demande_traitee_edac.jasper");

            JRDataSource jRDataSource = new JRBeanCollectionDataSource(Arrays.asList(dataExport));

            Map<String, Object> map = new HashMap<>();

            JasperReport japerReport = (JasperReport) JRLoader.loadObject(inputStream);

            JasperPrint jaspertPrint = JasperFillManager.fillReport(japerReport, map, jRDataSource);

            JasperExportManager.exportReportToPdfStream(jaspertPrint, outputStream);

        } catch (JRException e) {
            log.error("Erreur survenue lors de la génération de la demande traitée : {}", e);
            throw new CustomException("Document indisponible, pour cause d'erreur inconnue ! Veuillez réessayer SVP.");
        }
    }

    @Override
    public void printRecepisseDemande(Long demandeId, OutputStream outputStream) {

        log.info("Export du récépissé de la demande : {}", demandeId);
        try {
            //recherche de la demande
            Demande d = demandeRepository.findById(demandeId).orElseThrow(() -> new CustomException("Demande d'ID " + demandeId + " inexistante en base de données."));
            //recherche du ministere et de la structure de l'agent demandeur
            Structure s = d.getAgent().getStructure();
            Ministere m = s.getMinistere();
            // chargement de l'armoirie du Faso
            InputStream logoStream = resourceLoader.getResource("classpath:armoirie.jpeg").getInputStream();

            // le titre de l'acte
            String titre = "Demande d’acte de " + d.getTypeDemande().getLibelle();
            Image qrCodeData = AppUtil.constructQRCode("DEMANDE ENREGISTRE SOUS LE N° " + d.getNumero());
            // conteneur de données de base à imprimer
            RecepisseRD dataExport = new RecepisseRD(
                    m.getLibelle().toUpperCase(),
                    logoStream,
                    d.getNumero(),
                    titre.toUpperCase(),
                    (d.getAgent() != null ? d.getAgent().getNom() + " " + d.getAgent().getPrenom() : null),
                    (d.getAgent() != null ? d.getAgent().getMatricule() : null),
                    (s != null ? s.getLibelle() : null),
                    "emploi",
                    "fonction",
                    (d.getAgent() != null ? d.getAgent().getTelephone() : null),
                    (d.getAgent() != null ? d.getAgent().getEmail() : null),
                    (d.getDateDemande() != null ? AppUtil.convertDatWithFullMonthToString(Date.from(d.getDateDemande().atStartOfDay(ZoneId.systemDefault()).toInstant())) : null),/*conversion local pour java<=8*/
                    qrCodeData);

            InputStream inputStream = this.getClass().getResourceAsStream("/recepisse_demande_edac.jasper");

            JRDataSource jRDataSource = new JRBeanCollectionDataSource(Arrays.asList(dataExport));

            Map<String, Object> map = new HashMap<>();

            JasperReport japerReport = (JasperReport) JRLoader.loadObject(inputStream);

            JasperPrint jaspertPrint = JasperFillManager.fillReport(japerReport, map, jRDataSource);

            JasperExportManager.exportReportToPdfStream(jaspertPrint, outputStream);

        } catch (JRException | IOException e) {
            log.error("Erreur survenue lors de la génération du récépissé : {}", e);
            throw new CustomException("Document indisponible, pour cause d'erreur inconnue ! Veuillez réessayer SVP.");
        }
    }

    @Override
    public void printFormulaireDemande(Long demandeId, OutputStream outputStream) {
        log.info("Export du formulaire de la demande : {}", demandeId);
        try {
            //recherche de la demande
            Demande d = demandeRepository.findById(demandeId).orElseThrow(() -> new CustomException("Demande d'ID " + demandeId + " inexistante en base de données."));
            //recherche du ministere et de la structure de l'agent demandeur
            Structure s = d.getAgent().getStructure();
            Ministere m = s.getMinistere();
            List<Historique> avisSH = null;
            List<Historique> avisDRH = null;
            List<Historique> avisSG = null;
            avisSH = historiqueRepository.findAvisSHSortByDateAvis(d.getId());
            avisDRH = historiqueRepository.findAvisDRHSortByDateAvis(d.getId());
            avisSG = historiqueRepository.findAvisSGSortByDateAvis(d.getId());
//            AgentStructure as = agentStructureRepository.findByAgentIdAndActifTrue(d.getAgent().getId()).orElseThrow(() -> new CustomException("La structure active de l'agent " + d.getAgent().getId() + " est introuvable."));
//            MinistereStructure ms = ministereStructureRepository.findByStructureIdAndStatutIsTrue(as.getStructure().getId()).orElseThrow(() -> new CustomException("Ministère et structure d'origine demandeur introuvable."));
            // chargement de l'armoirie du Faso
            InputStream logoStream = resourceLoader.getResource("classpath:armoirie.jpeg").getInputStream();

            // le titre de l'acte
            String titre = "FORMULAIRE INTRODUIT ET ENREGISTRE SOUS LE N° " + d.getNumero();
            Image qrCodeData = AppUtil.constructQRCode("FORMULAIRE ENREGISTRE SOUS LE N° " + d.getNumero());
            String neant = " - - - - - -";
            // conteneur de données de base à imprimer
            FormulaireRD dataExport = new FormulaireRD(logoStream, titre,
                    (d.getMotif() != null ? d.getMotif().getTypeDemandeur().name() : neant),
                    (d.getDateDemande() != null ? AppUtil.convertDatWithFullMonthToString(Date.from(d.getDateDemande().atStartOfDay(ZoneId.systemDefault()).toInstant())) : neant),/*conversion local pour java<=8*/
                    d.getTypeDemande().getLibelle().toUpperCase(),
                    (d.getMotif() != null ? d.getMotif().getLibelle() : neant),
                    (d.getDestination() != null ? d.getDestination().getLibelle() : neant),
                    (d.getAgent() != null ? d.getAgent().getNom() : neant),
                    (d.getAgent() != null ? d.getAgent().getPrenom() : neant),
                    (d.getAgent() != null ? d.getAgent().getMatricule() : neant),
                    "emploi",
                    (s != null ? s.getLibelle() : neant),
                    (m != null ? m.getLibelle() : neant),
                    (d.getAgent() != null ? d.getAgent().getTelephone() : neant),
                    (d.getAgent() != null ? d.getAgent().getEmail() : neant),
                    (d.getDateEffet() != null ? AppUtil.convertDatWithFullMonthToString(Date.from(d.getDateEffet().atStartOfDay(ZoneId.systemDefault()).toInstant())) + " | durée : " + (d.getMotif() != null ? d.getMotif().getPlafondAnnee() + " ans" : neant) : neant),/*conversion local pour java<=8*/
                    (avisSH == null || avisSH.isEmpty() ? null : avisSH.get(0).getAvis() + ". Le " + avisSH.get(0).getDateAvis()),
                    (avisDRH == null || avisDRH.isEmpty() ? null : avisDRH.get(0).getAvis() + ". Le " + avisDRH.get(0).getDateAvis()),
                    (avisSG == null || avisSG.isEmpty() ? null : avisSG.get(0).getAvis() + ". Le " + avisSG.get(0).getDateAvis()),
                    "decision",
                    qrCodeData);

            InputStream inputStream = this.getClass().getResourceAsStream("/formulaire_demande.jasper");

            JRDataSource jRDataSource = new JRBeanCollectionDataSource(Arrays.asList(dataExport));

            Map<String, Object> map = new HashMap<>();

            JasperReport japerReport = (JasperReport) JRLoader.loadObject(inputStream);

            JasperPrint jaspertPrint = JasperFillManager.fillReport(japerReport, map, jRDataSource);

            JasperExportManager.exportReportToPdfStream(jaspertPrint, outputStream);

        } catch (JRException | IOException e) {
            log.error("Erreur survenue lors de la génération du formulaire : {}", e);
            throw new CustomException("Document indisponible, pour cause d'erreur inconnue ! Veuillez réessayer SVP.");
        }
    }

    @Override
    public void printArreteDetachement(Long demandeId, boolean isRegularisation, OutputStream outputStream) {
        log.info("Export du projet d'arrêté de détachement suivant demande : {}", demandeId);
        try {
            //recherche de la demande
            Demande d = demandeRepository.findById(demandeId).orElseThrow(() -> new CustomException("Demande d'ID " + demandeId + " inexistante en base de données."));
            //si demande est non validé, ne pas permettre la generation du document
            if (!d.getStatut().equals(EStatutDemande.PROJET_SIGNE)) {
                throw new CustomException("Impossible de générer le projet d'arrêté. Veuillez finaliser le traitement de la demande SVP.");
            }
            //recherche du ministere et de la structure de l'agent demandeur
            Structure s = d.getAgent().getStructure();
            Ministere m = s.getMinistere();
//            AgentStructure as = agentStructureRepository.findByAgentIdAndActifTrue(d.getAgent().getId()).orElseThrow(() -> new CustomException("La structure active de l'agent " + d.getAgent().getId() + " est introuvable."));
//            MinistereStructure ms = ministereStructureRepository.findByStructureIdAndStatutIsTrue(as.getStructure().getId()).orElseThrow(() -> new CustomException("Ministère et structure d'origine demandeur introuvable."));

            //on fait appel à la liste des visas parametrés pour reconstituer les visaRD
            List<Visa> visas = visaRepository.findAll();
            List<VisaRD> visaRDs = new ArrayList<>();
            for (Visa v : visas) {
                visaRDs.add(new VisaRD(v.getLibelle() + " ;"));
            }
            //on fait appel à la liste des articles parametrés pour reconstituer les articleRD
            List<Article> articles = articleRepository.findAll();
            List<ArticleRD> articleRDs = new ArrayList<>();
            for (Article a : articles) {
                articleRDs.add(new ArticleRD(a.getCode() + " : " + a.getLibelle()));
            }
            //on construit l'identité du beneficiaire
//            String identificationBeneficiaire = " <b>" + d.getAgent().getNom() + " " + d.getAgent().getPrenom() + "</b>          Matricule : <b>" + d.getAgent().getMatricule() + "</b>"
//                    + "<br/> Emploi : <b>" + d.getAgent().getCorps().getLibelle() + "</b>"
            // d.getAgent().getCorps().getLibelle()
            String identificationBeneficiaire = " <b>" + d.getAgent().getNom() + " " + d.getAgent().getPrenom() + "</b>\t Matricule : <b>" + d.getAgent().getMatricule() + "</b>"
                    + "<br/> Emploi : <b>" + "corps agent" + "</b>"
                    + "<br/> Catégorie/Echelle : <b>" + d.getAgent().getCategorie() + d.getAgent().getEchelle() + "</b> Classe : <b>" + d.getAgent().getClasse() + "</b> Echelon : <b>" + d.getAgent().getEchelon() + "</b>"
                    + "<br/> Ministère : <b>" + m.getLibelle() + "</b>";
//                    + "<br/> Ministère : <b>" + ms.getMinistere().getLibelle() + "</b>";
            //on construit l'article selon qu'il s'agisse d'un nouveau, renouvellement ou fin
            String article1 = "";
            if (d.getTypeDemande().getLibelle().toLowerCase().contains("nouveau nouvelle")) {//il s'agit d'un nouveau detachement
                article1 = "est placé(e) en position de détachement sur demande auprès de " + d.getDestination().getLibelle() + " à compter du " + d.getDateEffet() + " pour une durée de " + d.getDuree().getAnnee() + " an(s)";
            } else if (d.getTypeDemande().getLibelle().toLowerCase().contains("renouvellement")) {//il s'agit d'un renouvellement
                article1 = "en détachement auprès de " + d.getDestination().getLibelle() + ", bénéficie d'une renouvellement de son détachement, à compter du " + d.getDateEffet() + " pour une durée de " + d.getDuree().getAnnee() + " an(s)";
            } else {
                article1 = "Il est mis fin... au détachement accordé ...";
            }
            // chargement de l'armoirie du Faso
            InputStream logoStream = resourceLoader.getResource("classpath:armoirie.jpeg").getInputStream();

            // le titre de l'acte
            String titre = "ARRETE N° ???? <br />PORTANT " + d.getTypeDemande().getLibelle().toUpperCase();
//            Image qrCodeData = AppUtil.constructQRCode("ARRETE N° ???? SUIVANT DEMANDE N°" + d.getNumero());
            String neant = " - - - - - -";
            // conteneur de données de base à imprimer
            ArreteDetachementRD dataExport = new ArreteDetachementRD(
                    logoStream,
                    (m != null ? m.getLibelle().toUpperCase() : neant),
                    titre,
                    visaRDs,
                    identificationBeneficiaire,
                    article1,
                    articleRDs,
                    "...................................................",/*dateActe*/
                    "P. le Ministre d'Etat, P/D. le Secrétaire général",
                    "<u>Hamidou SAWADOGO</u>");

            InputStream inputStream = this.getClass().getResourceAsStream("/static/jasperfiles/arrete_detachement.jasper");

            JRDataSource jRDataSource = new JRBeanCollectionDataSource(Arrays.asList(dataExport));

            Map<String, Object> map = new HashMap<>();

            JasperReport japerReport = (JasperReport) JRLoader.loadObject(inputStream);

            JasperPrint jaspertPrint = JasperFillManager.fillReport(japerReport, map, jRDataSource);

            JasperExportManager.exportReportToPdfStream(jaspertPrint, outputStream);

        } catch (JRException | IOException e) {
            log.error("Erreur survenue lors de la génération du document : {}", e);
            throw new CustomException("Document indisponible, pour cause d'erreur inconnue ! Veuillez réessayer SVP.");
        }
    }

}
