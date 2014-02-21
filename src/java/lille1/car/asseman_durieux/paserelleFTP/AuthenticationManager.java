package lille1.car.asseman_durieux.paserelleFTP;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Thomas Durieux
 */
public interface AuthenticationManager {

  AuthenticationManager INSTANCE = new AuthenticationManagerImpl();

  ClientSession getSession(HttpHeaders httpHeaders, UriInfo uriInfo);

  void removeClientSession(ClientSession session);
}
