package lille1.car.asseman_durieux.paserelleFTP;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;
import lille1.car.asseman_durieux.exception.AuthenticationException;

/**
 * Singloton used to autheticate users
 * @author Thomas Durieux
 */
public interface AuthenticationManager {

    /**
     * Singloton
     */
    AuthenticationManager INSTANCE = new AuthenticationManagerImpl();

    /**
     * Get the session of a client based on the header of a request (basic
     * authentification)
     *
     * @param httpHeaders the header of a reaquest
     * @param uriInfo
     * @return The session of the client
     * @throws AuthenticationException If the user are not authenticate
     */
    ClientSession getSession(HttpHeaders httpHeaders, UriInfo uriInfo) throws AuthenticationException;

    /**
     * Remove the session of a client
     *
     * @param session the session to remove
     */
    void removeClientSession(ClientSession session);
}
