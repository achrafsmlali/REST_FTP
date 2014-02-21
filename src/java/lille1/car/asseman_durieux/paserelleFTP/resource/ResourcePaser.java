package lille1.car.asseman_durieux.paserelleFTP.resource;

/**
 *
 * @author Thomas Durieux
 */
public class ResourcePaser {

  public static Resource parse(String resourceString) {
    return new DirectoryImp();
  }
}
