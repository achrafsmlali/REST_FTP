package lille1.car.asseman_durieux.paserelleFTP;

import java.io.InputStream;
import javax.ws.rs.core.Response;

/**
 * This interface discribes methods used to access to a FTP server.
 *
 * @author Thomas Durieux
 */
public interface PaserelleFTP {

  /**
   * Download a file
   *
   * @param path the path to the file
   *
   * @return Response a HTTP response containing the file
   */
  Response getFile(String path);

  /**
   * Remove a file
   *
   * @param path the path to the file
   *
   * @return Response a HTTP response containing ok
   */
  Response removeFile(String path);

  /**
   * Store a file
   *
   * @param path the path to the file
   *
   * @return Response a HTTP response containing ok
   */
  Response storeFile(String path, InputStream file);

  /**
   * Get a representation of a Directory
   *
   * @param path   The path to the direcory
   * @param format The output format (JSON, XML, HTML)
   *
   * @return a HTTP response containing a resource list
   */
  Response getDir(String path, String format);

  /**
   * Create a directory
   *
   * @param path The path to the direcory
   *
   * @return a HTTP response containing ok
   */
  Response mkDir(String path);

  /**
   * Remove a Directory
   *
   * @param path The path to the direcory
   *
   * @return a HTTP response containing ok
   */
  Response rmDir(String path);

  /**
   * Rename a resource
   *
   * @param fromPath the old path of the resource
   * @param toPath   the new path of the resource
   *
   * @return a HTTP response containing ok
   */
  Response rename(String fromPath, String toPath);
}
