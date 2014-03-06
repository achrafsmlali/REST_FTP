package lille1.car.asseman_durieux.paserelleFTP;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import lille1.car.asseman_durieux.exception.AuthenticationException;
import lille1.car.asseman_durieux.exception.ClientSessionException;
import org.apache.commons.net.util.Base64;

/**
 *
 * @author Thomas Durieux
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
      throw new AuthenticationException("Header not present");
    }
    // Get username/password
    String[] login = new String(Base64.decodeBase64(authHeaders.get(0).split("\\s")[1])).split(":");
    String username = login.length < 2 ? "" : login[0];
    String password = login.length < 2 ? "" : login[1];

    ClientSession session = sessions.get(username + ":" + password);
    if (session == null) {

      session = new ClientSessionImpl(username, password);
      sessions.put(username + ":" + password, session);
    }
    try {
      session.login();
      return session;
    } catch (ClientSessionException e) {
      sessions.remove(username + ":" + password);
      throw new AuthenticationException("Unable to connect the user", e);
    }
  }

  public void removeClientSession(ClientSession session) {
    sessions.remove(session.getUsername() + ":" + session.getPassword());
  }
}
