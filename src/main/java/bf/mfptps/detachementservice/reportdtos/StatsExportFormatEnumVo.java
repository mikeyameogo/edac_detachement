/**
 * 
 */
package bf.mfptps.detachementservice.reportdtos;

/**
 * @author aboubacary
 *
 */
public enum StatsExportFormatEnumVo {
	
	PDF("PDF"),
	WORD("DOCX"),
	EXCEL("XLSX");
	
	String label;
	
	StatsExportFormatEnumVo(String label){
		this.label = label;
	}
	

	public static StatsExportFormatEnumVo convert(String libelle) {
        for (StatsExportFormatEnumVo donnee : StatsExportFormatEnumVo.values()) {
            if (donnee.toString().equals(libelle)) {
                return donnee;
            }
        }
        return null;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
}
