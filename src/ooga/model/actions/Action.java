package ooga.model.actions;

import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import ooga.model.Player;
import ooga.model.tiles.Property;
import ooga.model.tiles.Tile;
import org.apache.commons.lang3.ObjectUtils.Null;

/**
 * The purpose of this class is to define an abstract framework for many different action 
 * subclasses, each of which possess somewhat unique functionality in terms of performAction and
 * toString
 * @author wyattfocht 
 */
public abstract class Action {

  private static final String DEFAULT_BUNDLE = "standard/StandardChanceEnglish";

  private int firstInt;
  private int secondInt;
  private List<Player> players;
  private Player currentPlayer;
  private ResourceBundle popUpResources;


  /**
   * @param firstInt      an int value that might be used to perform specific calculations depending
   *                      on the action type
   * @param secondInt     another int value that might be used to perform specific calculations
   *                      depending on the action type
   * @param players       the current Player on which the action is occurring
   * @param currentPlayer List<Player> representing all other players in the game
   */
  public Action(int firstInt, int secondInt, List<Player> players, Player currentPlayer,
      String resourcesPath) {
    this.firstInt = firstInt;
    this.secondInt = secondInt;
    this.players = players;
    this.currentPlayer = currentPlayer;
    handleBundle(resourcesPath);
  }

  private void handleBundle(String resourcesPath) {
    try {
      popUpResources = ResourceBundle.getBundle(resourcesPath);
    } catch (MissingResourceException e) {
      popUpResources = ResourceBundle.getBundle(DEFAULT_BUNDLE);
    }
  }

  /**
   * This method calls the appropriate methods to invoke the logic necessary for this particular
   * subclass of Action to perform the action on the chance/community card
   *
   * @return boolean representing whether a further action (such as a purchase) is needed in the
   * turn after this action is completed
   */
  public abstract boolean performAction();

  /**
   * This method generates a string that is to be displayed on the frontend of the project following
   * the invocation of performAction
   *
   * @return String representing the message to be displayed on the corresponding card's popup
   */
  public abstract String toString();

  protected void editPlayerWealth(int delta, int increase) {
    currentPlayer.editWealth(increase * delta);
  }

  protected void editOtherPlayersWealth(int delta, int increase) {
    for (Player other : players) {
      if (other != currentPlayer) {
        if (other.getNetWorth() < delta) {
          other.setAlive(false);
        } else {
          other.editWealth(increase * delta);
        }
      }
    }
  }

  protected void movePlayerGivenSteps(int delta) {
    currentPlayer.movePlayer(delta);
  }

  protected void movePlayerToFirstInstanceOf(String location) {
    int originalWealth = currentPlayer.getWealth();
    Tile originalTile = currentPlayer.getLocation();
    currentPlayer.movePlayer(1);
    while (!(currentPlayer.getLocation().getClass().getSimpleName().equals(location))
        && currentPlayer.getLocation() != originalTile) {
      currentPlayer.movePlayer(1);
    }
    if (currentPlayer.getLocation() == originalTile) {
      currentPlayer.editWealth(-1 * Math.abs(currentPlayer.getWealth() - originalWealth));
    }
  }

  protected int getFirstInt() {
    return firstInt;
  }

  protected int getSecondInt() {
    return secondInt;
  }

  protected String getStringForPopup(String key) {
    try {
      return popUpResources.getString(key);
    } catch (MissingResourceException | ClassCastException | NullPointerException e) { //make custom exception later
      return "";
    }
  }

  protected boolean futureActionNeeded() {
    return true;
  }

  protected boolean futureActionNotNeeded() {
    return false;
  }

  protected Player getCurrentPlayer() {
    return currentPlayer;
  }

  protected int getOtherPlayersSize() {
    return (players.size() - 1);
  }

  protected boolean playerCanPay(int value) {
    if (value > currentPlayer.getNetWorth()) {
      currentPlayer.setAlive(false);
      return false;
    }
    return true;
  }
}
