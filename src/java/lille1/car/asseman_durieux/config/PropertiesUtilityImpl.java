package lille1.car.asseman_durieux.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Configuration class is a utility class used to load ini file
 *
 * @author Durieux Thomas
 * @author Toulet Cyrille
 */
public class PropertiesUtilityImpl implements PropertiesUtility {

  /* Instance */
  private final Properties properties;

  /**
   * Constructor
   *
   * @param fileName The property file name
   */
  public PropertiesUtilityImpl(final String fileName) {
    /* Set instance */
    properties = new Properties();

    /* Set file */
    final File f = new File(fileName);

    /* Check */
    if (!f.exists()) {
      try {
        properties.store(new FileOutputStream(f), null);
      } catch (final IOException ex) {
        Logger.getLogger(PropertiesUtilityImpl.class.getName()).log(
                Level.SEVERE, null, ex);
      }
    } else {
      try {
        // Load the properties file
        properties.load(new FileInputStream(f));
      } catch (final IOException ex) {
        throw new RuntimeException(ex);
      }
    }
  }

  /**
   * Constructor
   *
   * @param openStream The property file stream
   */
  public PropertiesUtilityImpl(final InputStream openStream) {
    /* Set instance */
    properties = new Properties();

    /* Use open stream */
    try {
      properties.load(openStream);
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * @see PropertiesUtility
   */
  @Override
  public String getProperty(final String key) {
    return properties.getProperty(key);
  }

  /**
   * @see PropertiesUtility
   */
  @Override
  public boolean getBooleanProperty(final String key) {
    return Boolean.parseBoolean(properties.getProperty(key));
  }

  /**
   * @see PropertiesUtility
   */
  @Override
  public int getIntProperty(final String key) {
    return Integer.parseInt(properties.getProperty(key));
  }
}
