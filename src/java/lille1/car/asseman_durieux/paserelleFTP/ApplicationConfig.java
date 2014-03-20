package lille1.car.asseman_durieux.paserelleFTP;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;

/**
 * Application configuration used to define the path of the rest API
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
        return classes;
    }
}
