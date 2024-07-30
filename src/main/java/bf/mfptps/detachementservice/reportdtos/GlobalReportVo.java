/**
 * 
 */
package bf.mfptps.detachementservice.reportdtos;

import java.util.Date;

import bf.mfptps.detachementservice.enums.EStatutDemande;
import bf.mfptps.detachementservice.enums.ETypeDemandeur;
import lombok.Getter;
import lombok.Setter;

/**
 * @author aboubacary
 *
 */
@Getter
@Setter
public class GlobalReportVo {
	
	/**
	 * date de début de la période
	 */
	private Date debut;
	
	/**
	 * date limite de la période
	 */
	private Date fin; 
 
	/*
	 * 
	 * Sexe de l'agent
	 */
	 private String sexe;
	 
	 /**
	  * Permet de préciser si ce sont les structures centrales ou déconcentrées
	  */
	 private Long typeStructure;
	 
	 /**
	  * Statut de la demande
	  */
    private EStatutDemande statut;
    
    /**
     * Type du demandeur
     */
    private ETypeDemandeur typeDemandeur;
    
    /**
     * Id du type de demande
     */
    private Long typeDemande;
    
    /**
     * Id de l'Agent auteur de la demande
     */
    private Long agent;
    
    /**
     * Id de l'acte associé à la demande
     */
    private Long acte;

}
