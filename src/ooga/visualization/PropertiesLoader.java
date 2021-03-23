package ooga.visualization;

import java.util.Properties;
import java.util.ResourceBundle;
/**
 * @author Catherine Livingston
 * <p>
 * Purpose - The PropertiesLoader.java class is used to get Strings that are needed in setting up
 * the game.
 * <p>
 * Assumptions - Files are in /resources and correctly set up.
 * <p>
 * Dependencies - This class depends on Java's classes.
 */
public class PropertiesLoader {

  public static final String TRANSLATION_FILE = "translation";

  public PropertiesLoader() {

  }

  /**
   * method to get the associated names needed in setting up the game in controller
   *
   * @param gameType - the game type selected during selection screen
   */
  public static String[] getGameTypeOptions(String gameType) {
    ResourceBundle gameTypeResources = ResourceBundle.getBundle("gameType");
    String options = gameTypeResources.getString(gameType);
    return options.split(", ");
  }

  /**
   * method to get a translation back to english so that the string can be used to identify next steps
   *
   * @param statement - the string of what needs to be translated
   */
  public static String getTranslation(String statement) {
    ResourceBundle translations = ResourceBundle.getBundle(TRANSLATION_FILE);
    return (String) translations.getString(statement);
  }


}
