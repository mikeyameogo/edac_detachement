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
 * modele du recepisse à exporter
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Getter
@Setter
@ToString
public class RecepisseRD {

    private String ministere;
    private InputStream logo;
    private String numeroRecepisse;//c'est le numero de demande generé
    private String titre; //titre du document
    private String nomPrenom;
    private String matricule;
    private String structure; //structure d'appartenance
    private String emploi;
    private String fonction;
    private String telephone;
    private String email;
    private String dateDemande;
    private Image qrCodeData;
    //============================

    public RecepisseRD() {
    }

    public RecepisseRD(String ministere, InputStream logo, String numeroRecepisse, String titre, String nomPrenom, String matricule, String structure, String emploi, String fonction, String telephone, String email, String dateDemande, Image qrCodeData) {
        this.ministere = ministere;
        this.logo = logo;
        this.numeroRecepisse = numeroRecepisse;
        this.titre = titre;
        this.nomPrenom = nomPrenom;
        this.matricule = matricule;
        this.structure = structure;
        this.emploi = emploi;
        this.fonction = fonction;
        this.telephone = telephone;
        this.email = email;
        this.dateDemande = dateDemande;
        this.qrCodeData = qrCodeData;
    }

}
