package bf.mfptps.detachementservice.enums;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public enum EStatutDemande {
    INITIEE("INITIEE", "INITIEE"),
    PAYEE("PAYEE", "PAYEE"),
    AVIS_SH("AVIS_SH", "AVIS_SH"),
    AVIS_DRH("AVIS_DRH", "AVIS_DRH"),
    AVIS_DGFP("AVIS_DGFP", "AVIS_DGFP"),
    RECEPTIONEE("RECEPTIONEE", "RECEPTIONEE"),
    IMPUTEE("IMPUTEE", "IMPUTEE"),
    CONFORME("CONFORME", "CONFORME"),
    REJET_CA("REJET_CA", "REJET_CA"),
    REJET_DRH("REJET_DRH", "REJET_DRH"),
    VALIDEE("VALIDEE", "VALIDEE"),
    REFUSEE("REFUSEE", "REFUSEE"),
    REJET_SG("REJET_SG", "REJET_SG"),
    ABANDONNEE("ABANDONNEE", "ABANDONNEE"),//permet d'abandonner une demande dont le projet d'acte n'a pas encore ete elabore
    ELABORATION("ELABORATION", "ELABORATION"),
    CLOS("CLOS", "CLOS"),
    DEMANDE_VALIDEE("DEMANDE_VALIDEE", "DEMANDE VALIDEE"),
    DEMANDE_REJETEE("DEMANDE_REJETEE", "DEMANDE REJETEE"),
    PROJET_ELABORE("PROJET_ELABORE", "PROJET ELABORE"),
    PROJET_VALIDE("PROJET_VALIDE", "PROJET VALIDE"),
    PROJET_VERIFIE("PROJET_VERIFIE", "PROJET VERIFIE"),
    PROJET_VISE("PROJET_VISE", "PROJET VISE"),
    PROJET_SIGNE("PROJET_SIGNE", "PROJET SIGNE"),
    PROJET_REJETE("REJETE", "REJETE");

    protected String value;
    protected String label;

    EStatutDemande(String value,
            String pLabel) {
        this.value = value;
        this.label = pLabel;
    }

    public String getLabel() {
        return this.label;
    }

    public String getValue() {
        return this.value;
    }

    public static EStatutDemande getByValue(String value) {
        for (EStatutDemande val : EStatutDemande.values()) {
            if (val.getValue().equals(value)) {
                return val;
            }
        }
        return null; //or throw new IllegalArgumentException("...");
    }

    public static String[] getLabels() {

        String[] labels = new String[EStatutDemande.values().length];
        int index = 0;
        for (EStatutDemande val : EStatutDemande.values()) {
            labels[index] = val.getLabel();
            index++;
        }
        return labels;
    }

    public static String getLabelByValue(String value) {
        return getByValue(value).getLabel();
    }

    public static EStatutDemande getByLabel(String label) {
        for (EStatutDemande val : EStatutDemande.values()) {
            if (val.getLabel().equals(label)) {
                return val;
            }
        }
        return null; // ou throw new IllegalArgumentException("...");
    }

    public static String getValueByLabel(String label) {
        EStatutDemande eTranslationKind = getByLabel(label);
        if (null != eTranslationKind) {
            return getByLabel(label).getValue();
        }
        return null;
    }

}
