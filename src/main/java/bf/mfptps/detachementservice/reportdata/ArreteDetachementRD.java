/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.reportdata;

import java.io.InputStream;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * POJO Report Data for jasperFile
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ArreteDetachementRD {

    private InputStream logo;

    private String ministereLibelle;

    private String titre;

    private List<VisaRD> visaRDs;

    private String identificationBeneficiaire;

    private String article1;

    private List<ArticleRD> articleSuivantRDs;

    private String dateActe;

    private String fonctionSignataire;

    private String identiteSignataire;

}
