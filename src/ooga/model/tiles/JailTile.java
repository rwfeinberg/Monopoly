package ooga.model.tiles;

import java.util.List;
import ooga.configuration.CSVLoader;
import ooga.model.Player;

/**
 * @author Muazzam Khan Noorpuri
 * @author Sylvie Mason
 * Purpose - The JailTile.java class represents the jail tile that a player can land upon in the
 * backend.
 * Assumptions - No general Tile will be created. Must use an extension
 * Dependencies - Tile.java
 */
public class JailTile extends Tile {

  private static final int VISITING_JAIL = 0;
  private static final int UNAVAILABLE = 3;
  private final List<String> messages;

  /**
   * Constructor creates an extension of Tile and sets the pop message for when landed upon
   * @param tileName
   * @param inputColor
   */
  public JailTile(String tileName, String inputColor) {
    super(tileName, inputColor, UNAVAILABLE);
    String language = (new CSVLoader("languageChoice", "")).getValues().get(0);
    this.messages = (new CSVLoader("jailClassMessages", language)).getValues();
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
    this.getPopUpStrings().add(messages.get(VISITING_JAIL));
    this.getPopUpStrings().add("OK");
  }

  /**
   * @return 0 since the JailTile cannot be bought
   */
  @Override
  public int getPrice() {
    return 0;
  }
}
