package lille1.car.asseman_durieux.paserelleFTP;

import lille1.car.asseman_durieux.exception.ClientSessionException;
import lille1.car.asseman_durieux.exception.FTPCommandException;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author Thomas Durieux
 */
public class ClientSessionImpl implements ClientSession {

  private FTPClient ftpClient;
  String username;
  String password;
  private boolean isLogged = false;

  public ClientSessionImpl() {
    ftpClient = new FTPClient();
  }

  public ClientSessionImpl(String username, String password) {
    this();
    this.username = username;
    this.password = password;
    //this.connect();
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public FTPClient getFTPClient() {
    return ftpClient;
  }

  public void connect() throws ClientSessionException {
    if (ftpClient.isConnected()) {
      return;
    }
    try {
      FTPCommand.INSTANCE.connectClient(this);
    } catch (FTPCommandException e) {
      throw new ClientSessionException(e);
    }
  }

  public void login() throws ClientSessionException {
    if (isLogged) {
      return;
    }
    try {
      FTPCommand.INSTANCE.loginClient(this);
      isLogged = true;
    } catch (FTPCommandException e) {
      throw new ClientSessionException(e);
    }
  }

  public void disconnect() throws ClientSessionException {
    try {
      FTPCommand.INSTANCE.disconnectClient(this);
      AuthenticationManager.INSTANCE.removeClientSession(this);
      isLogged = false;
    } catch (FTPCommandException e) {
      throw new ClientSessionException(e);
    }
  }
}
