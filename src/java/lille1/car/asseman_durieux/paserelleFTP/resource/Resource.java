package lille1.car.asseman_durieux.paserelleFTP.resource;

import java.util.Date;

/**
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

  String toJson();

  String toHTML();

  String toXML();
}
