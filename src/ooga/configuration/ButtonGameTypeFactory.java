package ooga.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The purpose of this class is to function as a factory by either returning a list of all possible
 * game types or a map of the aforementioned game types to their corresponding buttons
 * @author wyattfocht
 */
public class ButtonGameTypeFactory {

  //this should never chance
  private static final String PATH_TO_FILE = "./data/gamespecific/allGameTypesButtons";
  private static final List<String> KNOWN_BUTTONS = List
      .of("BuyHouse", "SellHouse", "Trade", "Mortgage", "Unmortgage");

  private CSVFactory csv;
  private List<String> allGameTypes;
  private Map<String, List<String>> gameTypesToButtons;

  /**
   * This is a constructor for a ButtonGameType factory class.  It initializes the relevant instance
   * variables for this class.
   */
  public ButtonGameTypeFactory() {
    csv = new CSVFactory(PATH_TO_FILE);
    allGameTypes = new ArrayList<>();
    gameTypesToButtons = new HashMap<>();
    initialize();
  }

  private void initialize() {
    List<List<String>> csvLines = csv.getLinesOfCSV();

    for (List<String> line : csvLines) {
      handleLine(line);
    }
  }

  private void handleLine(List<String> line) {
    String gameType = line.get(0);
    //ensure line is not missing gameType
    if (!KNOWN_BUTTONS.contains(gameType)) {
      gameTypesToButtons.put(line.get(0), line.subList(1, line.size()));
      allGameTypes.add(gameType);
    }

  }


  /**
   * This method is a getter method for allGameTypes: a list of all possible game types for monopoly
   * in this project
   *
   * @return List of Strings that lists all possible game types for monopoly that currently exist in
   * this game
   */
  public List<String> getGameTypes() {
    return allGameTypes;
  }

  /**
   * This method is a getter method for a map that maps game type to which buttons are to be
   * displayed for the given game type
   *
   * @return Map with keys as game types and values as list of types of buttons to be displayed for
   * the corresponding game type
   */
  public Map<String, List<String>> getGameTypesToButtons() {
    return gameTypesToButtons;
  }


}
