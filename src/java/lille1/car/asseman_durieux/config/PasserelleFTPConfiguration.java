package lille1.car.asseman_durieux.config;

/**
 * FTP configuration
 *
 * @author Durieux Thomas
 */
public interface PasserelleFTPConfiguration extends PropertiesUtility {
    /* Set instance */

    PropertiesUtility INSTANCE = new PropertiesUtilityImpl(
            PasserelleFTPConfiguration.class.getResourceAsStream("ftp_config.ini"));
}
