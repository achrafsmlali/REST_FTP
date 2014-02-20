package lille1.car.asseman_durieux.paserelleFTP;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author thomas
 */
public interface AuthenticationManager {
  AuthenticationManager INSTANCE = new AuthenticationManagerImpl();
  
  ClientSession getSession (HttpHeaders httpHeaders, UriInfo uriInfo);
}
