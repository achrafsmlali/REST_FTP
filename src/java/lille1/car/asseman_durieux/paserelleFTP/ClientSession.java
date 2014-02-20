package lille1.car.asseman_durieux.paserelleFTP;

import lille1.car.asseman_durieux.exception.ClientSessionException;

/**
 * 
 * @author Thomas Durieux
 */
public interface ClientSession {
  String getUsername();
  void setUsername(String username);
  
  String getPassword();
  void setPassword(String passwword);
  
  void connect() throws ClientSessionException;
}
