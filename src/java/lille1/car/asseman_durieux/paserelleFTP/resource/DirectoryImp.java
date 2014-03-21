package lille1.car.asseman_durieux.paserelleFTP.resource;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author thomas
 */
public class DirectoryImp extends ResourceImp implements Directory {

    List<Resource> listFile;

    public DirectoryImp() {
        listFile = new ArrayList<Resource>();
        super.type = "directory";
    }

    public void addResource(Resource resource) {
        listFile.add(resource);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
