package ooga.configuration;

import ooga.model.DeckOfCards;
import ooga.model.tiles.Property;
import ooga.model.tiles.Railroad;
import ooga.model.tiles.Tile;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

  /**
  * The purpose of this class is to provide a concrete implementation of TileReader that reads in
  * values from a data file and associates those values with Tile types to compile a list of all
  * possible tiles for a given game type
  * @author wyattfocht 
  */
public class TileFactory implements TileReader {

  private final String DATA_DIRECTORY = "./data/";
  private String TILES = "TILES";
  private final String PACKAGE = "ooga.model.tiles.";
  private final int ITEMS_PER_LINE = 4;
  private final int PROPERTY = 1;
  private final int RAILROAD = 2;
  private final int UTILITY = 3;
  private final int CHANCE = 7;
  private final int COMMUNITY = 8;
  private final String[] DEFAULT_VALUES = {"Property", "WHITE", "-1", "1"};
  private final String[] TILE_TYPES = {"GoTile", "Property", "Railroad", "UtilityTile", "TaxTile",
      "JailTile", "GoToJailTile", "CardTile", "CardTile", "FreeParkingTile"};
  private HashMap<String, List<Property>> propertyMap = new HashMap<>();
  private String pathToTiles;
  private List<Tile> allTiles;
  private List<String> propertyNames;
  private CSVFactory csvFactory;
  private String gameType;

  public TileFactory(String gameType) {
    this.gameType = gameType;
    this.pathToTiles = DATA_DIRECTORY + gameType.toLowerCase() + "/" + gameType + TILES;
    csvFactory = new CSVFactory(this.pathToTiles);
    propertyNames = new ArrayList<>();
    readGameProperties();
    initialize();
    setMonopolyVals();
  }

  private void readGameProperties() {
    allTiles = new ArrayList<>();
    List<List<String>> allLinesOfCSV = csvFactory.getLinesOfCSV();

    for (List<String> line : allLinesOfCSV) {
      String[] arrayLine;
      if (line.size() != ITEMS_PER_LINE) {
        arrayLine = DEFAULT_VALUES;
      } else {
        arrayLine = line.toArray(new String[0]);
      }
      allTiles.add(handleLine(arrayLine));
    }
  }

  private Tile handleLine(String[] nextLine) {
    String name = nextLine[0];
    propertyNames.add(name);
    String color = nextLine[1];
    int startingRent = Integer.parseInt(nextLine[2]);
    int type = Integer.parseInt(nextLine[3]);
    return createProperty(name, color, startingRent, type);
  }

  private Tile createProperty(String name, String color, int initialRent, int type) {
    Tile t;
    DeckOfCards chanceDeck = new DeckOfCards(0, gameType);
    DeckOfCards communityDeck = new DeckOfCards(1, gameType);

    try {
      if (type == RAILROAD || type == UTILITY || type == PROPERTY) {
        t = (Tile) Class.forName(PACKAGE + TILE_TYPES[type]).getDeclaredConstructors()[0]
            .newInstance(name, color, initialRent);
        if (type == PROPERTY) {
          propertyMap.putIfAbsent(color, new ArrayList<>());
          propertyMap.get(color).add((Property) t);
        }
      } else if (type == CHANCE) {
        t = (Tile) Class.forName(PACKAGE + TILE_TYPES[type]).getDeclaredConstructors()[0]
            .newInstance(name, color, chanceDeck);
      } else if (type == COMMUNITY) {
        t = (Tile) Class.forName(PACKAGE + TILE_TYPES[type]).getDeclaredConstructors()[0]
            .newInstance(name, color, communityDeck);
      } else {
        t = (Tile) Class.forName(PACKAGE + TILE_TYPES[type]).getDeclaredConstructors()[0]
            .newInstance(name, color);
      }
    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
      t = new Property(name, color, initialRent);
    }
    t.setGameType(gameType);
    return t;
  }

  /**
   * This method is a getter method for a list of Strings that represent the names of all the
   * tiles within the game
   * @return List that contains Strings that correspond to just the names of all the possible
   * tiles for a given game type
   */
  public List<String> getTileNames() {
    if (propertyNames != null) {
      return propertyNames;
    } else {
      return Collections.emptyList();
    }
  }

  /**
   * @see TileReader#getTiles()
   */
  @Override
  public List<Tile> getTiles() {
    if (allTiles != null) {
      return allTiles;
    } else {
      return Collections.emptyList();
    }
  }


  private void setMonopolyVals () {
    for (List<Property> propertyList : propertyMap.values()) {
      for (Property p : propertyList) {
        p.setForMonopoly(propertyList.size());
        p.setGameType(gameType);
        p.initialize();
      }
    }
  }

  private void initialize(){
    for(Tile t : allTiles){
      t.setGameType(gameType);
      if(t instanceof Railroad) {
        ((Railroad)t).initialize();
      }
    }
  }

}
