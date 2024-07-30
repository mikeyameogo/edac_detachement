/**
 * 
 */
package bf.mfptps.detachementservice.reportdata;

import java.io.InputStream;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author aboubacary
 *
 */
@Getter
@Setter
public class DemandeVo implements Serializable {

	private static final long serialVersionUID = -3699805447322842348L;

	private String titre;
	
	private String dateJour;

	private String dateValidation;

	private String nOrdre;
 
	private String nom; 
	
	private String matricule;
	
	private String typeDemande; 

	private InputStream logo;

	//private InputStream qrCode;
	
	private String institution;
	
	private String sexe;
	
	private String motif;  
}
