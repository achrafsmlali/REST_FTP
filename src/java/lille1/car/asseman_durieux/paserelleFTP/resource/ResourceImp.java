package lille1.car.asseman_durieux.paserelleFTP.resource;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class contains the basic data of a FTP resource
 *
 * @author Thomas DUrieux
 */
public abstract class ResourceImp implements Resource {

  protected String type;
  private String name;
  private Date creationDate;
  private long size;
  private String group;
  private String owner;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }

  public String getGroup() {
    return group;
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  /**
   * Call a specfic method name on a object
   *
   * @param method    the name of the method to call
   * @param object    the object contained to method
   * @param separator the type sepator
   *
   * @return A serialisation of the object
   *
   * @throws IllegalArgumentException
   * @throws IllegalAccessException
   */
  private String callSerializeMethodOnObject(String method, Object object, String separator) throws IllegalArgumentException, IllegalAccessException {
    String text = "";
    String valueSeparator = method.equals("toJson") ? "\"" : "";
    if (object == null) {
      return valueSeparator + "null" + valueSeparator;
    }

    if (Iterable.class.isAssignableFrom(object.getClass())) {
      text = method.equals("toJson") ? "[" : "";

      for (Object object1 : (Iterable) object) {
        text += callSerializeMethodOnObject(method, object1, separator);
        text += separator;
      }
      if (text.length() > 2) {
        text = text.substring(0, text.length() - separator.length());
      }
      return text + (method.equals("toJson") ? "]" : "");
    }

    Method childMethod;
    try {
      childMethod = object.getClass().getDeclaredMethod(method, new Class[0]);
    } catch (NoSuchMethodException ex) {
      try {
        childMethod = object.getClass().getSuperclass().getDeclaredMethod(method, new Class[0]);
      } catch (NoSuchMethodException ex1) {
        childMethod = null;
      } catch (SecurityException ex1) {
        childMethod = null;
      }
    } catch (SecurityException ex) {
      childMethod = null;
    }
    if (childMethod != null) {
      try {
        return (String) childMethod.invoke(object, new Object[0]);
      } catch (InvocationTargetException ex) {
        return object.toString();
      }
    }
    return valueSeparator + object.toString() + valueSeparator;
  }

  /**
   * Get all fields of the current class (with parent fields)
   *
   * @return fields of the class
   */
  private List<Field> getAllFields() {
    List<Field> fields = new ArrayList<Field>();
    Field[] f = this.getClass().getDeclaredFields();
    for (Field field : f) {
      fields.add(field);
    }
    Class class1 = this.getClass().getSuperclass();
    if (class1 != null) {
      f = class1.getDeclaredFields();
      for (Field field : f) {
        fields.add(field);
      }
    }
    return fields;
  }

  /**
   * @see Resource
   */
  public String toJson() {
    String json = "{";
    List<Field> fields = getAllFields();

    for (int i = 0, length = fields.size(); i < length; i++) {
      Field field = fields.get(i);
      try {
        Object object = field.get(this);
        String value = callSerializeMethodOnObject("toJson", object, ",");
        json += "\"" + field.getName() + "\": " + value + "";
        if (i < length - 1) {
          json += ",";
        }
      } catch (Throwable ex) {
        // ignore the field if we connot display it
        continue;
      }
    }
    return json + "}";
  }

  /**
   * @see Resource
   */
  public String toHTML() {
    String html = "<ul>";
    List<Field> fields = getAllFields();

    for (int i = 0, length = fields.size(); i < length; i++) {
      Field field = fields.get(i);
      try {
        Object object = field.get(this);
        String value = callSerializeMethodOnObject("toHTML", object, "");
        html += "<li><span class='labbel'>" + field.getName() + "</span> <span>" + value + "</span>";
      } catch (Throwable ex) {
        // ignore the field if we connot display it
        continue;
      }
    }
    return html + "</ul>";
  }

  /**
   * @see Resource
   */
  public String toXML() {
    String xml = "<" + this.type + ">";
    List<Field> fields = getAllFields();

    for (int i = 0, length = fields.size(); i < length; i++) {
      Field field = fields.get(i);
      try {
        Object object = field.get(this);
        String value = callSerializeMethodOnObject("toXML", object, "");
        xml += "<" + field.getName() + ">" + value + "</" + field.getName() + ">";
      } catch (Throwable ex) {
        // ignore the field if we connot display it
        continue;
      }
    }
    return xml + "</" + this.type + ">";
  }
}
