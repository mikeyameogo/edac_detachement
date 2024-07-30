/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bf.mfptps.detachementservice.enums;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public enum ECategorie {
    DETACHEMENT("DETACHEMENT", "DETACHEMENT"),
    DISPONIBILITE("DISPONIBILITE", "DISPONIBILITE");

    protected String value;
    protected String label;

    private ECategorie(String value, String label) {
        this.value = value;
        this.label = label;
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
