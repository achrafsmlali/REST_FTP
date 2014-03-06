/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lille1.car.asseman_durieux.paserelleFTP;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Thomas Durieux
 */
@javax.ws.rs.ApplicationPath("rest")
public class ApplicationConfig extends Application {

  public ApplicationConfig() {
    super();
  }

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.addAll(super.getClasses());
        // register resources and features
        //classes.add(javax.ws.rs.core.MultivaluedMap.class);
        return classes;
    }
  
  
}
