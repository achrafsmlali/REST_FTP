package lille1.car.asseman_durieux.paserelleFTP;

import java.io.InputStream;
import lille1.car.asseman_durieux.paserelleFTP.resource.Directory;

/**
 * Interface between FTP serveur and REST interface
 *
 * @author Thomas Durieux
 */
interface FTPCommand {

  FTPCommand INSTANCE = new FTPCommandImpl();

  void connectClient(ClientSession clientSession);

  void loginClient(ClientSession clientSession);

  void disconnectClient(ClientSession clientSession);

  InputStream getFile(ClientSession clientSession, String path);

  void upload(ClientSession clientSession, String path, InputStream inputStream);

  Directory getDirectory(ClientSession clientSession, String path);

  public void upload(ClientSession session, String path, String first);

  public void mkdir(ClientSession session, String path);

}
