package bf.mfptps.detachementservice.enums;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public enum Civilite {

    Monsieur("Monsieur"),
    Mademoiselle("Mademoiselle"),
    Madame("Madame");

    private final String label;

    Civilite(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
