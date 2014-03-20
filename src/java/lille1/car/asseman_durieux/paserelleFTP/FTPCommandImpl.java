package lille1.car.asseman_durieux.paserelleFTP;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import lille1.car.asseman_durieux.config.PasserelleFTPConfiguration;
import lille1.car.asseman_durieux.exception.ClientSessionException;
import lille1.car.asseman_durieux.exception.FTPCommandException;
import lille1.car.asseman_durieux.paserelleFTP.resource.Directory;
import lille1.car.asseman_durieux.paserelleFTP.resource.DirectoryImp;
import lille1.car.asseman_durieux.paserelleFTP.resource.FileImp;
import lille1.car.asseman_durieux.paserelleFTP.resource.Resource;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPFile;

/**
 *
 * @author Thomas Durieux
 */
public class FTPCommandImpl implements FTPCommand {

    /**
     * @see FTPCommand
     */
    public void connectClient(ClientSession clientSession) {
        try {
            String host = PasserelleFTPConfiguration.INSTANCE.getProperty("ftpHost");
            int port = PasserelleFTPConfiguration.INSTANCE.getIntProperty("ftpPort");
            clientSession.getFTPClient().connect(host, port);
            clientSession.getFTPClient().enterLocalPassiveMode();
        } catch (SocketException ex) {
            throw new ClientSessionException("Unable to connect FTP client", ex);
        } catch (IOException ex) {
            throw new FTPCommandException("Unable to connect FTP client", ex);
        }
    }

    /**
     * @see FTPCommand
     */
    public void loginClient(ClientSession clientSession) {
        try {
            clientSession.connect();
        } catch (FTPCommandException e) {
            clientSession.disconnect();
            clientSession.connect();
        }
        try {
            boolean value = clientSession.getFTPClient().login(clientSession.getUsername(), clientSession.getPassword());
            if (!value) {
                throw new FTPCommandException("Unable to login client: bad password");
            }
        } catch (IOException ex) {
            throw new FTPCommandException("Unable to login client", ex);
        }
    }

    /**
     * @see FTPCommand
     */
    public void disconnectClient(ClientSession clientSession) {
        try {
            if (!clientSession.getFTPClient().logout()) {
                throw new FTPCommandException("Unable to logout client");
            }
        } catch (IOException ex) {
            /*
             * On arrive dans ce cas lorsque le sereur FTP n'est pas accessible.
             */
        }
    }

    /**
     * @see FTPCommand
     */
    public InputStream getFile(final ClientSession clientSession, String path) {
        clientSession.login();
        try {
            InputStream inputStream = clientSession.getFTPClient().retrieveFileStream(path);
            if(clientSession.getFTPClient().getReplyCode() != 150) {
                throw new FTPCommandException("Unable to retreive file");
            }
            new Thread(new Runnable() {
                public void run() {
                    try {
                        boolean tmp = clientSession.getFTPClient().completePendingCommand();
                    } catch (IOException ex) {
                        clientSession.disconnect();
                        throw new FTPCommandException("Unable to retreive file", ex);
                    }
                }
            }).start();
            return inputStream;
        } catch (IOException e) {
            clientSession.disconnect();
            throw new FTPCommandException("Unable to retreive file", e);
        }
    }

    /**
     * @see FTPCommand
     */
    public static void deleteFile(ClientSession clientSession, String path) {
        clientSession.login();
        try {
            int retCode = clientSession.getFTPClient().dele(path);
            switch (retCode) {
                case 250:
                    break;
                case 550:
                    throw new FTPCommandException("Unable to delete file (no such file ?)");
                default:
                    throw new FTPCommandException("Error during DELE command, return code : " + retCode);
            }
        } catch (IOException e) {
            throw new FTPCommandException("IO error during deleting file");
        }
    }

    /**
     * @see FTPCommand
     */
    public Directory getDirectory(ClientSession clientSession, String path) {
        clientSession.login();
        try {
            clientSession.getFTPClient().setFileType(FTP.ASCII_FILE_TYPE);
            clientSession.getFTPClient().cwd(path);
            FTPFile[] ftpFiles = clientSession.getFTPClient().listFiles();
            Directory directory = new DirectoryImp();
            directory.setName(clientSession.getFTPClient().printWorkingDirectory());
            for (FTPFile fTPFile : ftpFiles) {
                Resource resource;
                if (fTPFile.isDirectory()) {
                    resource = new DirectoryImp();
                } else {
                    resource = new FileImp();
                }
                resource.setName(fTPFile.getName());
                resource.setOwner(fTPFile.getUser());
                resource.setCreationDate(fTPFile.getTimestamp().getTime());
                resource.setSize(fTPFile.getSize());
                resource.setGroup(fTPFile.getGroup());
                directory.addResource(resource);
            }
            return directory;
        } catch (IOException e) {
            clientSession.disconnect();
            throw new FTPCommandException("Unable to retreive file", e);
        }
    }

    /**
     * @see FTPCommand
     */
    public void upload(ClientSession clientSession, String path, InputStream inputStream) {
        clientSession.login();
        try {
            clientSession.getFTPClient().setFileType(FTP.BINARY_FILE_TYPE);
            clientSession.getFTPClient().storeFile(path, inputStream);
        } catch (IOException e) {
            clientSession.disconnect();
            throw new FTPCommandException("IO error during uploading file");
        }
    }

    /**
     * @see FTPCommand
     */
    public void mkdir(ClientSession clientSession, String path) {
        clientSession.login();
        try {
            clientSession.getFTPClient().mkd(path);
        } catch (IOException ex) {
            clientSession.disconnect();
            throw new FTPCommandException("Unable to create directory", ex);
        }
    }
}
