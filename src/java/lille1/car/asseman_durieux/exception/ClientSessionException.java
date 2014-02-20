/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lille1.car.asseman_durieux.exception;

/**
 *
 * @author Thomas Durieux
 */
public class ClientSessionException extends RuntimeException {

  /**
   *
   */
  private static final long serialVersionUID = 7674458099465801345L;

  /**
   * Constructor
   */
  public ClientSessionException() {
    super();
  }

  /**
   * Constructor
   *
   * @param message Message of exception
   */
  public ClientSessionException(final String message) {
    super(message);
  }

  /**
   * Constructor
   *
   * @param t Throwable exception
   */
  public ClientSessionException(final Throwable t) {
    super(t);
  }

  /**
   * Constructor
   *
   * @param message Message of exception
   * @param t Throwable exception
   */
  public ClientSessionException(final String message, final Throwable t) {
    super(message, t);
  }
}
