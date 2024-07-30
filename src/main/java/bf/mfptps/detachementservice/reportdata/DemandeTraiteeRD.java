/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.reportdata;

import java.awt.Image;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * modele de demande traitee
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Getter
@Setter
@ToString
public class DemandeTraiteeRD {

    private String nomPrenom; //nom et prenom de l'agent demandeur
    private String matricule; //matricule de l'agent demandeur
    private String emploiFonction; //emploi ou fonction de l'agent demandeur
    private String enServiceA; //structure d'origine de l'agent demandeur
    private String telephone; //contact de l'agent demandeur
    private String email; //email de l'agent demandeur
    private String lieuDateDemande; //les lieu et date de soumission de la demande
    private String titre; //titre de celui aqui on est adressee la demande
    private String numeroDemande;//le numero d'enregistrement de la demande
    private String objet; //l'objet de la demande
    private String formuleAppelPolitesse; //formule d'appel et de politesse
    private String contenu;//le contenu/corps de la demande
    private String cijoint;//pieces jointes lors de la soumission de la demande
    private String avisSH; //date et avis du superieur hierarchique
    private String avisDRH; //date et avis du/de la directeur-trice des ressources humaines
    private String avisSG; //date et avis du Secretaire général
    private String decision; //date et decision du ministre/president d'institution
    private String ministere; //ministere d'origine de l'agent
    private Image qrCodeData;
    //===========================================

    public DemandeTraiteeRD() {
    }

    public DemandeTraiteeRD(String nomPrenom, String matricule, String emploiFonction, String enServiceA, String telephone, String email, String lieuDateDemande, String titre, String numeroDemande, String objet, String formuleAppelPolitesse, String contenu, String cijoint, String avisSH, String avisDRH, String avisSG, String decision, String ministere, Image qrCodeData) {
        this.nomPrenom = nomPrenom;
        this.matricule = matricule;
        this.emploiFonction = emploiFonction;
        this.enServiceA = enServiceA;
        this.telephone = telephone;
        this.email = email;
        this.lieuDateDemande = lieuDateDemande;
        this.titre = titre;
        this.numeroDemande = numeroDemande;
        this.objet = objet;
        this.formuleAppelPolitesse = formuleAppelPolitesse;
        this.contenu = contenu;
        this.cijoint = cijoint;
        this.avisSH = avisSH;
        this.avisDRH = avisDRH;
        this.avisSG = avisSG;
        this.decision = decision;
        this.ministere = ministere;
        this.qrCodeData = qrCodeData;
    }

}
