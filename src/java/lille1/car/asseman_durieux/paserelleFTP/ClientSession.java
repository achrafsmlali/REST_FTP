package lille1.car.asseman_durieux.paserelleFTP;

import lille1.car.asseman_durieux.exception.ClientSessionException;
import org.apache.commons.net.ftp.FTPClient;

/**
 * Access to all client session data
 *
 * @author Thomas Durieux
 */
public interface ClientSession {

  /**
   * Connect the client to ftp server
   *
   * @throws ClientSessionException
   */
  void login() throws ClientSessionException;

  /**
   * Connect the client to ftp server
   *
   * @throws ClientSessionException
   */
  void connect() throws ClientSessionException;

  /**
   * Disconnect the client to ftp server
   *
   * @throws ClientSessionException
   */
  void disconnect() throws ClientSessionException;

  String getUsername();

  void setUsername(String username);

  String getPassword();

  void setPassword(String passwword);

  FTPClient getFTPClient();
}
