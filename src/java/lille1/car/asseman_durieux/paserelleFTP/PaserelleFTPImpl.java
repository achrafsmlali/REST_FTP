package lille1.car.asseman_durieux.paserelleFTP;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import lille1.car.asseman_durieux.exception.AuthenticationException;
import lille1.car.asseman_durieux.exception.FTPCommandException;

/**
 * This class provides REST API to allow client to acces a FTP server
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
  @Path("{path: .*}")
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  public Response getFile(@PathParam("path") String path) {
    ClientSession session;
    if (path == null) {
      path = "/";
    }
    if (path.charAt(0) != '/') {
      path = "/" + path;
    }
    try {
      session = AuthenticationManager.INSTANCE.getSession(requestHeaders, uriInfo);
    } catch (AuthenticationException e) {
      Response.ResponseBuilder response = Response.ok("401 Unauthorized").status(401);
      List<String> ajaxHeader = requestHeaders.getRequestHeader("X-Requested-With");
      if (ajaxHeader == null || !requestHeaders.getRequestHeader("X-Requested-With").contains("XMLHttpRequest")) {
        response = response.header("WWW-Authenticate", "Basic");
      }
      return response.build();
    }
    try {
      return Response.ok(FTPCommand.INSTANCE.getFile(session, path)).build();
    } catch (FTPCommandException e) {
      return Response.ok(e.getMessage()).status(404).build();
    } catch (Exception e) {
      e.printStackTrace();
      return Response.ok(e.getMessage()).status(404).build();
    }
  }

  /**
   * @see PaserelleFTP
   */
  @Override
  @GET
  @Path("/dir{path: .*}/{format : (json|xml|html)?}")
  @Consumes({"application/xml", "application/json", "text/html"})
  @Produces(MediaType.APPLICATION_OCTET_STREAM)
  public Response getDir(@PathParam("path") String path, @PathParam("format") String format) {
    ClientSession session;
    if (path == null || path.equals("")) {
      path = "/";
    }
    if (path.charAt(0) != '/') {
      path = "/" + path;
    }
    try {
      session = AuthenticationManager.INSTANCE.getSession(requestHeaders, uriInfo);
    } catch (AuthenticationException e) {
      Response.ResponseBuilder response = Response.ok("401 Unauthorized").status(401);
      List<String> ajaxHeader = requestHeaders.getRequestHeader("X-Requested-With");
      if (ajaxHeader == null || !requestHeaders.getRequestHeader("X-Requested-With").contains("XMLHttpRequest")) {
        response = response.header("WWW-Authenticate", "Basic");
      }
      return response.build();
    }
    String f = format;
    if (f == null) {
      List<MediaType> listType = requestHeaders.getAcceptableMediaTypes();
      for (MediaType mediaType : listType) {
        if (mediaType.getType().equals(MediaType.TEXT_HTML)) {
          f = "html";
          break;
        }
        if (mediaType.getType().equals(MediaType.APPLICATION_JSON)) {
          f = "json";
          break;
        }
        if (mediaType.getType().equals(MediaType.APPLICATION_XML)) {
          f = "xml";
          break;
        }
      }
    }
    if (f.equals("json")) {
      return Response.ok(FTPCommand.INSTANCE.getDirectory(session, path).toJson()).header("Content-Type", "application/json").build();
    } else if (f.equals("xml")) {
      return Response.ok(FTPCommand.INSTANCE.getDirectory(session, path).toXML()).header("Content-Type", "application/xml").build();
    } else if (f.equals("html")) {
      return Response.ok(FTPCommand.INSTANCE.getDirectory(session, path).toHTML()).header("Content-Type", "text/html").build();
    }
    return Response.ok(FTPCommand.INSTANCE.getDirectory(session, path).toJson()).build();
  }

  /**
   * @see PaserelleFTP
   */
  @Override
  @DELETE
  @Path("{path: .*}")
  public Response removeFile(@PathParam("path") String path) {
    ClientSession session;
    if (path == null) {
      path = "/";
    }
    if (path.charAt(0) != '/') {
      path = "/" + path;
    }
    try {
      session = AuthenticationManager.INSTANCE.getSession(requestHeaders, uriInfo);
    } catch (AuthenticationException e) {
      Response.ResponseBuilder response = Response.ok("401 Unauthorized").status(401);
      List<String> ajaxHeader = requestHeaders.getRequestHeader("X-Requested-With");
      if (ajaxHeader == null || !requestHeaders.getRequestHeader("X-Requested-With").contains("XMLHttpRequest")) {
        response = response.header("WWW-Authenticate", "Basic");
      }
      return response.build();
    }
    try {
      session.getFTPClient().deleteFile(path);
    } catch (IOException ex) {
      return Response.ok("404 Ressource not found").status(404).build();
    }
    return Response.ok().build();
  }

  /**
   * @see PaserelleFTP
   */
  @PUT
  @Path("{path: .*}")
  @Consumes({MediaType.MULTIPART_FORM_DATA})
  public Response storeFile(@PathParam("path") String path, InputStream file) {
    ClientSession session;
    if (path == null) {
      path = "/";
    }
    if (path.charAt(0) != '/') {
      path = "/" + path;
    }
    try {
      session = AuthenticationManager.INSTANCE.getSession(requestHeaders, uriInfo);
    } catch (AuthenticationException e) {
      Response.ResponseBuilder response = Response.ok("401 Unauthorized").status(401);
      List<String> ajaxHeader = requestHeaders.getRequestHeader("X-Requested-With");
      if (ajaxHeader == null || !requestHeaders.getRequestHeader("X-Requested-With").contains("XMLHttpRequest")) {
        response = response.header("WWW-Authenticate", "Basic");
      }
      return response.build();
    }
    try {
      FTPCommand.INSTANCE.upload(session, path, file);
    } catch (FTPCommandException e) {
      return Response.ok("500 Unable to upload file").status(500).build();
    }
    return Response.ok().build();
  }

  /**
   * @see PaserelleFTP
   */
  @Override
  @POST
  @Path("/mkdir{path: .*}")
  public Response mkDir(@PathParam("path") String path) {
    ClientSession session;
    if (path == null) {
      path = "/";
    }
    if (path.charAt(0) != '/') {
      path = "/" + path;
    }
    try {
      session = AuthenticationManager.INSTANCE.getSession(requestHeaders, uriInfo);
    } catch (AuthenticationException e) {
      Response.ResponseBuilder response = Response.ok("401 Unauthorized").status(401);
      List<String> ajaxHeader = requestHeaders.getRequestHeader("X-Requested-With");
      if (ajaxHeader == null || !requestHeaders.getRequestHeader("X-Requested-With").contains("XMLHttpRequest")) {
        response = response.header("WWW-Authenticate", "Basic");
      }
      return response.build();
    }
    FTPCommand.INSTANCE.mkdir(session, path);
    return Response.ok("200 Created").build();
  }

  /**
   * @see PaserelleFTP
   */
  @Override
  @DELETE
  @Path("/rmDir{path: .*}")
  public Response rmDir(@PathParam("path") String path) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  /**
   * @see PaserelleFTP
   */
  @Override
  @POST
  @Path("/rename{fromPath: .*}")
  public Response rename(@PathParam("fromPath") String fromPath, @FormParam("toPath") String toPath) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
