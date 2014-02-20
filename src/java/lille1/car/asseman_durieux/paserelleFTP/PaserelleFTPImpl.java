package lille1.car.asseman_durieux.paserelleFTP;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Access the FTP server through REST request
 *
 * @author Thomas Durieux
 */
@Path("/")
public class PaserelleFTPImpl {

  @Context
  HttpHeaders requestHeaders;
  @Context
  UriInfo uriInfo;

  @GET
  @Path("/file{path: .*}")
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  public Response getFile(@PathParam("path") String path) {
    ClientSession session = AuthenticationManager.INSTANCE.getSession(requestHeaders, uriInfo);
    return Response.ok("").build();
  }
  
}
