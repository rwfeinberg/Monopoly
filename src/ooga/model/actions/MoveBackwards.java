package ooga.model.actions;

import java.util.List;
import ooga.model.Player;
import ooga.model.tiles.Tile;

/**
 * The purpose of this class is to move a player backwards 3 tiles and to provide a relevant 
 * String for a user-facing popup.
 * @author wyattfocht
 */
public class MoveBackwards extends Action {


  /**
   * @param firstInt      an int value that might be used to perform specific calculations depending
   *                      on the action type
   * @param secondInt     another int value that might be used to perform specific calculations
   *                      depending on the action type
   * @param players       the current Player on which the action is occurring
   * @param currentPlayer List<Player> representing all other players in the game
   * @param resourcesPath
   */
  public MoveBackwards(int firstInt, int secondInt, List<Player> players,
      Player currentPlayer, String resourcesPath) {
    super(firstInt, secondInt, players, currentPlayer, resourcesPath);
  }

  /**
   * This method calls the appropriate methods to invoke the logic necessary for this particular
   * subclass of Action to perform the action on the chance/community card
   *
   * @return boolean representing whether a further action (such as a purchase) is needed in the
   * turn after this action is completed
   */
  @Override
  public boolean performAction() {
    int wealth = getCurrentPlayer().getWealth();
    Tile current = getCurrentPlayer().getLocation();
    Tile back3 = getCurrentPlayer().getLocation();
    Tile back2 = getCurrentPlayer().getLocation();
    Tile back1 = getCurrentPlayer().getLocation();
    Tile temp = getCurrentPlayer().getLocation().next();
    while (!temp.equals(current)) {
      back3 = back2;
      back2 = back1;
      back1 = temp;
      temp = temp.next();
    }
    getCurrentPlayer().setLocation(back3);
    if (wealth < getCurrentPlayer().getWealth()) {
      getCurrentPlayer().editWealth(-1 * (getCurrentPlayer().getWealth() - wealth));
    }
    return futureActionNeeded();
  }

  /**
   * This method generates a string that is to be displayed on the frontend of the project following
   * the invocation of performAction
   *
   * @return String representing the message to be displayed on the corresponding card's popup
   */
  @Override
  public String toString() {
    return (getStringForPopup(this.getClass().getSimpleName() + "Front") + getFirstInt() + " "
        + getStringForPopup(this.getClass().getSimpleName() + "Back"));
  }

}
