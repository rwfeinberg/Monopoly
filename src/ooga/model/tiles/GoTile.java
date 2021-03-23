package ooga.model.tiles;

import java.util.List;
import ooga.configuration.CSVLoader;
import ooga.model.Player;

/**
 * @author Muazzam Khan Noorpuri
 * @author Sylvie Mason
 * Purpose - The GoTile.java class represents the start tile that a player can land upon in the
 * backend.
 * Assumptions - No general Tile will be created. Must use an extension
 * Dependencies - Tile.java
 */
public class GoTile extends Tile {

  private static final int GO_COLLECT = 0;
  private final int PASS_GO = 200;
  private static final int UNAVAILABLE = 3;
  private final List<String> messages;

  /**
   * Constructor creates an extension of Tile and sets the pop message for when landed upon
   * @param tileName
   * @param inputColor
   */
  public GoTile(String tileName, String inputColor) {
    super(tileName, inputColor, UNAVAILABLE);
    String language = (new CSVLoader("languageChoice", "")).getValues().get(0);
    this.messages = (new CSVLoader("goFreeParkingClassMessages", language)).getValues();
  }

  /**
   * @param p
   * @return no further action needed
   */
  @Override
  public boolean landedUpon(Player p) {
    return false;
  }

  /**
   * Set the tile's current pop up messages that will be displayed to the user
   * @param p
   */
  @Override
  public void setPopUpStrings(Player p) {
    this.getPopUpStrings().add(messages.get(GO_COLLECT) + PASS_GO);
    this.getPopUpStrings().add("OK");
  }

  /**
   * @return 0 since the GoTile cannot be bought
   */
  @Override
  public int getPrice() {
    return 0;
  }
}
