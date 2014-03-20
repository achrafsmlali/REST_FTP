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
 * Class used to autheticate users based on basic authetification
 *
 * @author Thomas Durieux
 */
public class AuthenticationManagerImpl implements AuthenticationManager {

    Map<String, ClientSession> sessions;

    public AuthenticationManagerImpl() {
        sessions = new HashMap<String, ClientSession>();
    }

    /**
     * @see AuthenticationManager
     */
    public ClientSession getSession(HttpHeaders httpHeaders, UriInfo uriInfo) throws AuthenticationException{
        ClientSession session =  getHTTPSession(httpHeaders);
        
        // login the user
        try {
            session.login();
        } catch (ClientSessionException e) {
            sessions.remove(session.getUsername() + ":" + session.getPassword());
            throw new AuthenticationException("Unable to connect the user bad login and/or password", e);
        }
        return session;
    }
    /**
     * Parse the basic authentication header
     * @param header authentication header 
     * @return First index of the array is the username, the second index is the password
     */
    private String[] parseAuthenticationHeader(String header) {
        return new String(Base64.decodeBase64(header.split("\\s")[1])).split(":");
    }
    
    /**
     * Get the session of user based of the header of the request
     * @param requestHeaders the header of the request
     * @return the client session
     */
    private ClientSession getHTTPSession(HttpHeaders requestHeaders) {
        // Get Headers
        List<String> authHeaders = requestHeaders.getRequestHeader(HttpHeaders.AUTHORIZATION);
        // check if the header is persent
        if (authHeaders == null || authHeaders.isEmpty()) {
            throw new AuthenticationException("Authentification header not present");
        }
        
        String[] login = parseAuthenticationHeader(authHeaders.get(0));
        // Get username/password
        String username = login.length > 0 ? "" : login[0];
        String password = login.length > 1 ? "" : login[1];

        // verify if the session already exist
        ClientSession session = sessions.get(username + ":" + password);
        if (session == null) {
            // create a new session
            session = new ClientSessionImpl(username, password);
            sessions.put(username + ":" + password, session);
        }
        return session;
    }

    /**
     * @see AuthenticationManager
     */
    public void removeClientSession(ClientSession session) {
        sessions.remove(session.getUsername() + ":" + session.getPassword());
    }
}
