package lille1.car.asseman_durieux.exception;

/**
 *
 * @author Thomas Durieux
 */
public class FTPCommandException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 7674458099465801345L;

    /**
     * Constructor
     */
    public FTPCommandException() {
        super();
    }

    /**
     * Constructor
     *
     * @param message Message of exception
     */
    public FTPCommandException(final String message) {
        super(message);
    }

    /**
     * Constructor
     *
     * @param t Throwable exception
     */
    public FTPCommandException(final Throwable t) {
        super(t);
    }

    /**
     * Constructor
     *
     * @param message Message of exception
     * @param t Throwable exception
     */
    public FTPCommandException(final String message, final Throwable t) {
        super(message, t);
    }
}
