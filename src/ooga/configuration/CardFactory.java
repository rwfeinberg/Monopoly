package ooga.configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This class is a factory class that is intended to create a map that maps available
 * chance/community cards (Strings) to an array of relevant ints to be used in a future method call
 * @author wyattfocht
 */
public class CardFactory implements CardReader {

  private final static String STANDARD_LANGUAGE = "English";
  private final static List<String> POSSIBLE_LANGUAGES = List.of("ENGLISH", "SPANISH");
  private final static String CHANCE = "Chance";
  private final static String COMMUNITY = "Community";
  private final int ITEMS_PER_LINE = 3;
  private final String OUTER_DIRECTORY = "./data/";

  private String gameType;
  private String language;
  private Map<String, int[]> chanceMethods;
  private Map<String, int[]> communityMethods;
  private CSVFactory chanceCSV;
  private CSVFactory communityCSV;

  /**
   * This is a constructor for a CardFactory object that uses the game type to create a relevant
   * path to a path of chance/community cards
   *
   * @param gameType String representing the type of game which is being created
   */
  public CardFactory(String gameType) {
    this.gameType = gameType;
    this.language = STANDARD_LANGUAGE;
    initialize();
  }

  /**
   * @param gameType String representing the type of game which is being created
   * @param language String representing the set language for the current game
   */
  public CardFactory(String gameType, String language) {
    this.gameType = gameType;
    this.language =
        POSSIBLE_LANGUAGES.contains(language.toUpperCase()) ? language : STANDARD_LANGUAGE;
    initialize();
  }

  private void initialize() {
    initializeChanceCards();
    initializeCommunityCards();
  }

  private void initializeCommunityCards() {
    communityMethods = new HashMap<>();
    String communityPath = OUTER_DIRECTORY + gameType.toLowerCase() + "/" + gameType + COMMUNITY;
    communityCSV = new CSVFactory(communityPath);
    readMethods(communityMethods);
  }

  private void initializeChanceCards() {
    chanceMethods = new HashMap<>();
    String chancePath = OUTER_DIRECTORY + gameType.toLowerCase() + "/" + gameType + CHANCE;
    chanceCSV = new CSVFactory(chancePath);
    readMethods(chanceMethods);
  }

  private void readMethods(Map chanceOrCommunity) {
    List<List<String>> allLinesOfCSV =
        chanceOrCommunity == chanceMethods ? chanceCSV.getLinesOfCSV()
            : communityCSV.getLinesOfCSV();

    for (List<String> line : allLinesOfCSV) {
      String[] arrayLine;
      if (line.size() != ITEMS_PER_LINE) {
        //bad line, move onto next
        continue;
      } else {
        arrayLine = line.toArray(new String[0]);
      }
      handleLine(arrayLine, chanceOrCommunity);
    }
  }

  private void handleLine(String[] nextLine, Map<String, int[]> methods) {
    methods.put(nextLine[0], new int[]{parseInt(nextLine[1]), parseInt(nextLine[2])});
  }

  private int parseInt(String s) {
    try {
      int ret = Integer.parseInt(s);
      return ret;
    } catch (NumberFormatException e) {
      return -1;
    }
  }


  /**
   * This method is a getter method for the map of possible chance cards to their relevant int
   * parameters
   *
   * @return Map representing a possible chance card (action) class with relevant numbers to its
   * eventual invocation
   */
  @Override
  public Map<String, int[]> getChanceCardMethodsAndRelevantNumbers() {
    if (chanceMethods.size() == 0) {
      return Collections.emptyMap();
    }
    return chanceMethods;
  }


  /**
   * This method is a getter method for the map of possible community cards to their relevant int
   * parameters
   *
   * @return Map representing a possible community card (action) class with relevant numbers to its
   * eventual invocation
   */
  @Override
  public Map<String, int[]> getCommunityCardMethodsAndRelevantNumbers() {
    if (communityMethods.size() == 0) {
      return Collections.emptyMap();
    }
    return communityMethods;
  }

  /**
   * This method is a getter method for the language option of the current game
   *
   * @return String representing the set language for the current game
   */
  public String getLanguage() {
    return language;
  }

  /**
   * THis method is a getter method for the game type of the current game
   *
   * @return String representing the game type of the current game
   */
  public String getGameType() {
    return gameType;
  }
}
