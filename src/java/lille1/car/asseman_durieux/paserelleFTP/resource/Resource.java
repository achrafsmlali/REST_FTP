package lille1.car.asseman_durieux.paserelleFTP.resource;

import java.util.Date;

/**
 * This interface discrables the basic data of a FTP resource
 *
 * @author Thomas Durieux
 */
public interface Resource {

  public String getName();

  public void setName(String name);

  public Date getCreationDate();

  public void setCreationDate(Date creationDate);

  public long getSize();

  public void setSize(long size);

  public String getGroup();

  public void setGroup(String group);

  public String getOwner();

  public void setOwner(String owner);

  /**
   * Serialise the ressource to JSON
   *
   * @return a JSON representation
   */
  String toJson();

  /**
   * Serialize the ressource to JSON
   *
   * @return a HTML representation
   */
  String toHTML();

  /**
   * Serialize the ressource to JSON
   *
   * @return a XML representation
   */
  String toXML();
}
