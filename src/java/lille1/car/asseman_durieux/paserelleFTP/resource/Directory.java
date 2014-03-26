package lille1.car.asseman_durieux.paserelleFTP.resource;

/**
 * This interface represents a directory on the FTP file system
 *
 * @author Thomas Durieux
 */
public interface Directory extends Resource {

  /**
   * Add a resource to the directory
   *
   * @param resource the resource to add
   */
  void addResource(Resource resource);
}
