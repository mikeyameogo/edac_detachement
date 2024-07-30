/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.reportdata;

import java.awt.Image;
import java.io.InputStream;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * modele du formulaire à exporter
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Getter
@Setter
@ToString
public class FormulaireRD {

    private InputStream logo;

    private String titre; //titre du document

    private String typeDemandeur; //si le demandeur est un AGENT ou une STRUCTURE

    private String lieuDateDemande; //les lieu et date de soumission de la demande

    private String objet; //le type de la demande

    private String motif; //le motif de la demande

    private String structureBeneficiaire; //structure devant recevoir l'agent

    private String nom; //nom de l'agent demandeur

    private String prenom; //prenom de l'agent demandeur

    private String matricule; //matricule de l'agent demandeur

    private String emploi; //emploi de l'agent demandeur

    private String enServiceA; //structure d'origine de l'agent demandeur

    private String ministere; //ministere d'origine de l'agent

    private String telephone; //contact de l'agent demandeur

    private String email; //email de l'agent demandeur

    private String dateEffet; //date d'effet de l'acte

    private String avisSH; //avis du superieur hierarchique

    private String avisDRH; //avis du/de la directeur-trice des ressources humaines

    private String avisSG; //avis du Secretaire général

    private String decision; //decision du ministre/president d'institution

    private Image qrCodeData;

    public FormulaireRD() {
    }

    public FormulaireRD(InputStream logo, String titre, String typeDemandeur, String lieuDateDemande, String objet, String motif, String structureBeneficiaire, String nom, String prenom, String matricule, String emploi, String enServiceA, String ministere, String telephone, String email, String dateEffet, String avisSH, String avisDRH, String avisSG, String decision, Image qrCodeData) {
        this.logo = logo;
        this.titre = titre;
        this.typeDemandeur = typeDemandeur;
        this.lieuDateDemande = lieuDateDemande;
        this.objet = objet;
        this.motif = motif;
        this.structureBeneficiaire = structureBeneficiaire;
        this.nom = nom;
        this.prenom = prenom;
        this.matricule = matricule;
        this.emploi = emploi;
        this.enServiceA = enServiceA;
        this.ministere = ministere;
        this.telephone = telephone;
        this.email = email;
        this.dateEffet = dateEffet;
        this.avisSH = avisSH;
        this.avisDRH = avisDRH;
        this.avisSG = avisSG;
        this.decision = decision;
        this.qrCodeData = qrCodeData;
    }

}
