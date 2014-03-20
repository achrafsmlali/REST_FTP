package lille1.car.asseman_durieux.paserelleFTP;

import java.io.InputStream;
import lille1.car.asseman_durieux.paserelleFTP.resource.Directory;

/**
 * Interface between FTP serveur and REST interface
 *
 * @author Thomas Durieux
 */
interface FTPCommand {
    
    /**
     * Singloton
     */
    FTPCommand INSTANCE = new FTPCommandImpl();

    /**
     * Connect the client to the server
     * @param clientSession the client 
     */
    void connectClient(ClientSession clientSession);
    
    /**
     * Login the user to the server
     * @param clientSession 
     */
    void loginClient(ClientSession clientSession);
    
    /**
     * Disconnect the client 
     * @param clientSession 
     */
    void disconnectClient(ClientSession clientSession);

    /**
     * Dowload a file
     * @param clientSession the client witch download a file
     * @param path the path to the document
     * @return the input stream of the file
     */
    InputStream getFile(ClientSession clientSession, String path);
    /**
     * Upload a new file on the ftp server
     * @param clientSession  the client witch upload a file
     * @param path the path of the new file
     * @param inputStream the inputStream of the file
     */
    void upload(ClientSession clientSession, String path, InputStream inputStream);
   
    /**
     * Get the list of resource of a directory
     * @param clientSession the client witch demand the list
     * @param path the path of the directory
     * @return the directory
     */
    Directory getDirectory(ClientSession clientSession, String path);
    
    /**
     * Create a folder on the ftp server
     * @param clientSession the client witch create the new folder
     * @param path the path of the folder
     */
    public void mkdir(ClientSession clientSession, String path);
}
