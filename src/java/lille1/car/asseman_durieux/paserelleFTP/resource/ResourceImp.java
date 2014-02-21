package lille1.car.asseman_durieux.paserelleFTP.resource;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
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
  
  private String callMethodOnObject(String method, Object object, String separator) throws IllegalArgumentException, IllegalAccessException {
    String text = "";
    if (object == null) {
      return "\"null\"";
    }
    if (object instanceof List) {
      text = "[";
      List objectList = (List) object;
      for (Iterator it = objectList.iterator(); it.hasNext();) {
        Object object1 = it.next();
        text += callMethodOnObject(method, object1, separator);
        if (it.hasNext()) {
          text += separator;
        }
      }
      return text + "]";
    }
    if (object.getClass().isArray()) {
      text = "[";
      Object[] objectArray = (Object[]) object;
      for (int i = 0; i < objectArray.length; i++) {
        Object object1 = objectArray[i];
        text += callMethodOnObject(method, object1, separator);
        if (i < objectArray.length - 1) {
          text += separator;
        }
      }
      return text + "]";
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
    return "\"" + object.toString() + "\"";
  }
  
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
  
  public String toJson() {
    String json = "{";
    List<Field> fields = getAllFields();
    
    for (int i = 0, length = fields.size(); i < length; i++) {
      Field field = fields.get(i);
      try {
        Object object = field.get(this);
        String value = callMethodOnObject("toJson", object, ",");
        json += "\"" + field.getName() + "\": " + value + "";
        if (i < length - 1) {
          json += ",";
        }
      } catch (IllegalArgumentException ex) {
        continue;
      } catch (IllegalAccessException ex) {
        continue;
      }
    }
    return json + "}";
  }
  
  public String toHTML() {
    String html = "<ul>";
    List<Field> fields = getAllFields();
    
    for (int i = 0, length = fields.size(); i < length; i++) {
      Field field = fields.get(i);
      try {
        Object object = field.get(this);
        String value = callMethodOnObject("toHTML", object, "");
        html += "<li><span class='labbel'>" + field.getName() + "</span><div>" + value + "</div>";
      } catch (IllegalArgumentException ex) {
        continue;
      } catch (IllegalAccessException ex) {
        continue;
      }
    }
    return html + "</ul>";
  }
  
  public String toXML() {
    String xml = "<" + this.getClass().getSimpleName() + ">";
    List<Field> fields = getAllFields();
    
    for (int i = 0, length = fields.size(); i < length; i++) {
      Field field = fields.get(i);
      try {
        Object object = field.get(this);
        String value = callMethodOnObject("toXML", object, "");
        xml += "<" + field.getName() + ">" + value + "</" + field.getName() + ">";
      } catch (IllegalArgumentException ex) {
        continue;
      } catch (IllegalAccessException ex) {
        continue;
      }
    }
    return xml + "</" + this.getClass().getSimpleName() + ">";
  }
}
