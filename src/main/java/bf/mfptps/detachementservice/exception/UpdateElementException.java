package bf.mfptps.detachementservice.exception;

/**
 * Created by Zak TEGUERA on 29/09/2023.
 * <teguera.zakaria@gmail.com>
 */
public class UpdateElementException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UpdateElementException() {
        super("L'element a mettre a jour doit d'abord exister");
    }

}
