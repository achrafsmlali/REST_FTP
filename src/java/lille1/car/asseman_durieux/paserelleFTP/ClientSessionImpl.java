package lille1.car.asseman_durieux.paserelleFTP;

import lille1.car.asseman_durieux.exception.ClientSessionException;

/**
 *
 * @author thomas
 */
public class ClientSessionImpl implements ClientSession {

  String username;
  String password;

  public ClientSessionImpl(String username, String password) {
    this.username = username;
    this.password = password;
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

  public void connect() throws ClientSessionException {
    
  }
}
