package ooga.model.tiles;

import java.util.List;
import ooga.configuration.CSVLoader;
import ooga.model.Player;

/**
 * @author Muazzam Khan Noorpuri
 * Purpose - The FreeParkingTile.java class represents the parking tile that a player can land upon in the
 * backend.
 * Assumptions - No general Tile will be created. Must use an extension
 * Dependencies - Tile.java
 */
public class FreeParkingTile extends Tile {

  private static final int UNAVAILABLE = 3;
  private static final int FREE_PARKING = 1;
  private final List<String> messages;

  /**
   * Constructor creates an extension of Tile and sets the pop message for when landed upon
   * @param tileName
   * @param color
   */
  public FreeParkingTile(String tileName, String color) {
    super(tileName, color, UNAVAILABLE);
    String language = (new CSVLoader("languageChoice", "")).getValues().get(0);
    this.messages = (new CSVLoader("goFreeParkingClassMessages", language)).getValues();
  }

  /**
   * @param p
   * @return no further action needed
   */
  @Override
  public boolean landedUpon(Player p) {
    //just relax;)
    return false;
  }

  /**
   * set the tile's current pop up messages that will be displayed to the user
   * @param p
   */
  @Override
  public void setPopUpStrings(Player p) {
    this.getPopUpStrings().add(messages.get(FREE_PARKING));
    this.getPopUpStrings().add("OK");
  }

  /**
   * @return 0 since the FreeParkingTile cannot be bought
   */
  @Override
  public int getPrice() {
    return 0;
  }
}
