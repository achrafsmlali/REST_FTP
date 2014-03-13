package lille1.car.asseman_durieux.paserelleFTP;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import lille1.car.asseman_durieux.exception.AuthenticationException;

/**
 * Access the FTP server through REST request
 *
 * @author Thomas Durieux
 */
@Path("/")
public class PaserelleFTPImpl implements PaserelleFTP {

  @Context
  HttpHeaders requestHeaders;
  @Context
  UriInfo uriInfo;

  /**
   * @see PaserelleFTP
   */
  @Override
  @GET
  @Path("/file{path: .*}")
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  public Response getFile(@PathParam("path") String path) {
   ClientSession session;
    if(path.equals("")) path = "/";
    try {
      session = AuthenticationManager.INSTANCE.getSession(requestHeaders, uriInfo);
    } catch (AuthenticationException e) {
      return Response.ok("401 not allowed").status(401).header("WWW-Authenticate", "Basic").build();
    }
    return Response.ok(FTPCommand.INSTANCE.getFile(session, path)).build();
  }

  /**
   * @see PaserelleFTP
   */
  @Override
  @GET
  @Path("/dir{path: .*}/{format: (json|xml|html)}")
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  public Response getDir(@PathParam("path") String path, @PathParam("format") String format) {
    ClientSession session;
    if(path.equals("")) path = "/";
    try {
      session = AuthenticationManager.INSTANCE.getSession(requestHeaders, uriInfo);
    } catch (AuthenticationException e) {
      return Response.ok("401 not allowed").status(401).header("WWW-Authenticate", "Basic").build();
    }
    if (format.equals("json")) {
      return Response.ok(FTPCommand.INSTANCE.getDirectory(session, path).toJson()).header("Content-Type", "application/json").build();
    } else if (format.equals("xml")) {
      return Response.ok(FTPCommand.INSTANCE.getDirectory(session, path).toXML()).header("Content-Type", "application/xml").build();
    } else if (format.equals("html")) {
      return Response.ok(FTPCommand.INSTANCE.getDirectory(session, path).toHTML()).header("Content-Type", "text/html").build();
    }
    return Response.ok(FTPCommand.INSTANCE.getDirectory(session, path).toJson()).build();
  }

  /**
   * @see PaserelleFTP
   */
  @Override
  @DELETE
  @Path("/dir{path: .*}/{format: (json|xml|html)}")
  public Response removeFile(@PathParam("path") String path) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  /**
   * @see PaserelleFTP
   */

  @PUT
  @Path("/file{path: .*}")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public Response storeFile(@PathParam("path") String path,InputStream file) {
    ClientSession session;
    if(path.equals("")) path = "/";
    try {
      session = AuthenticationManager.INSTANCE.getSession(requestHeaders, uriInfo);
    } catch (AuthenticationException e) {
      return Response.ok("401 not allowed").status(401).header("WWW-Authenticate", "Basic").build();
    }
    FTPCommand.INSTANCE.upload(session, path,file);
    return Response.ok().build();
  }

  /**
   * @see PaserelleFTP
   */
  @Override
  @POST
  @Path("/mkDir{path: .*}")
  public Response mkDir(@PathParam("path") String path) {
      ClientSession session;
      if (path.equals("")) path = "/";
      try {
          session = AuthenticationManager.INSTANCE.getSession(requestHeaders, uriInfo);
      } catch (AuthenticationException e) {
          return Response.ok("401 not allowed").status(401).header("WWW-Authenticate", "Basic").build();
      }
      try {
          session.getFTPClient().makeDirectory(path);
      } catch (IOException ex) {
        return Response.ok("401 not allowed").status(401).header("WWW-Authenticate", "Basic").build();
      }
      return Response.ok().build();
  }

  /**
   * @see PaserelleFTP
   */
  @Override
  @POST
  @Path("/rmDir{path: .*}")
  public Response rmDir(@PathParam("path") String path) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  /**
   * @see PaserelleFTP
   */
  @Override
  @POST
  @Path("/rename{fromPath: .*}/{toPath: .*}")
  public Response rename(@PathParam("fromPath") String fromPath, @PathParam("toPath") String toPath) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
