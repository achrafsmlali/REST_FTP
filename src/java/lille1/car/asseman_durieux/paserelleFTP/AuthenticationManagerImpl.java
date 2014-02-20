/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lille1.car.asseman_durieux.paserelleFTP;

import javax.ws.rs.core.HttpHeaders;
import com.sun.jersey.core.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.UriInfo;
import lille1.car.asseman_durieux.exception.AuthenticationException;
import lille1.car.asseman_durieux.exception.ClientSessionException;

/**
 *
 * @author thomas
 */
public class AuthenticationManagerImpl implements AuthenticationManager {

  Map<String, ClientSession> sessions;

  public AuthenticationManagerImpl() {
    sessions = new HashMap<String, ClientSession>();
  }

  public ClientSession getSession(HttpHeaders httpHeaders, UriInfo uriInfo) {
    return getHTTPSession(httpHeaders);
  }

  private ClientSession getHTTPSession(HttpHeaders requestHeaders) {
    // Get Headers
    List<String> authHeaders = requestHeaders.getRequestHeader(HttpHeaders.AUTHORIZATION);
    // If no header, no session...
    if (authHeaders == null || authHeaders.isEmpty()) {
      throw new AuthenticationException("Header not");
    }
    // Get username/password
    String[] login = new String(Base64.decode(authHeaders.get(0).split("\\s")[1])).split(":");
    String username = login.length < 2 ? "" : login[0];
    String password = login.length < 2 ? "" : login[1];

    ClientSession session = sessions.get(username);
    if (session == null) {
      session = new ClientSessionImpl(username, password);
      sessions.put(username, session);
    }
    try {
      session.connect();
      return session;
    } catch (ClientSessionException e) {
      throw new AuthenticationException("Unable to connect the user", e);
    }
  }
}
