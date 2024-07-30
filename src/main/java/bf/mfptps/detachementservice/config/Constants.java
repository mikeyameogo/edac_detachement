package bf.mfptps.detachementservice.config;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
public final class Constants {

    // Regex for acceptable logins or another string
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";
    public static String SYSTEM_ACCOUNT = "system";

    private Constants() {
    }
}
