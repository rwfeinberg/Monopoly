package ooga.model.tiles;

import java.util.ArrayList;
import java.util.List;
import ooga.configuration.CSVLoader;
import ooga.model.Player;
import ooga.model.actions.Action;
import ooga.model.actions.GoToJail;

/**
 * @author Muazzam Khan Noorpuri
 * @author Sylvie Mason
 * Purpose - The GoToJailTile.java class represents the go to jail tile that a player can land upon in the
 * backend.
 * Assumptions - No general Tile will be created. Must use an extension
 * Dependencies - Tile.java
 */
public class GoToJailTile extends Tile {

  private static final int UNAVAILABLE = 3;
  private static final int GO_TO_JAIL = 1;
  private final int SUSPENDED_TURNS = 3;
  private final List<String> messages;

  /**
   * Constructor creates an extension of Tile and sets the pop message for when landed upon
   * @param tileName
   * @param inputColor
   */
  public GoToJailTile(String tileName, String inputColor) {
    super(tileName, inputColor, UNAVAILABLE);
    String language = (new CSVLoader("languageChoice", "")).getValues().get(0);
    this.messages = (new CSVLoader("jailClassMessages", language)).getValues();
  }

  /**
   * Sends player to jail and suspends them for set number of turns
   * @param p
   * @return whether the action of sending player to jail requires further action
   */
  @Override
  public boolean landedUpon(Player p) {
    p.setSuspended(SUSPENDED_TURNS);
    Action action = new GoToJail(-1, -1, new ArrayList<>(), p, "");
    return action.performAction();
  }

  /**
   * Set the tile's current pop up messages that will be displayed to the user
   * @param p
   */
  @Override
  public void setPopUpStrings(Player p) {
    this.getPopUpStrings().add(messages.get(GO_TO_JAIL));
    this.getPopUpStrings().add("OK");
  }

  /**
   * @return 0 since the GoToJailTile cannot be bought
   */
  @Override
  public int getPrice() {
    return 0;
  }

}
