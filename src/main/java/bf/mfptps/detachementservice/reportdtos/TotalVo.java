/**
 * 
 */
package bf.mfptps.detachementservice.reportdtos;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author aboubacary
 *
 */
@Getter
@Setter
public class TotalVo implements Serializable {

	private static final long serialVersionUID = -313388926120302320L;

	private long total; 
	
	private String stucture;
	
	private String libelle;
	
}
