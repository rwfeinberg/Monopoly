package ooga.model.actions;

import java.util.List;
import ooga.model.Player;

/**
 * The purpose of this class is to advance a player to a Go tile and provide
 * a relevant String for a user-facing popup.
 * @author wyattfocht 
 */
public class AdvanceToGo extends Action {

  /**
   * @param firstInt      an int value that might be used to perform specific calculations depending
   *                      on the action type
   * @param secondInt     another int value that might be used to perform specific calculations
   *                      depending on the action type
   * @param players       the current Player on which the action is occurring
   * @param currentPlayer List<Player> representing all other players in the game
   */
  public AdvanceToGo(int firstInt, int secondInt, List<Player> players,
      Player currentPlayer, String resourcePath) {
    super(firstInt, secondInt, players, currentPlayer, resourcePath);

  }

  /**
   * This method calls the appropriate methods to invoke the logic necessary for this particular
   * subclass of Action to perform the action on the chance/community card
   *
   * @return boolean representing whether a further action (such as a purchase) is needed in the
   * turn after this action is completed
   */
  public boolean performAction() {
    movePlayerToFirstInstanceOf("GoTile");
    return futureActionNotNeeded();
  }

  /**
   * This method generates a string that is to be displayed on the frontend of the project following
   * the invocation of performAction
   *
   * @return String representing the message to be displayed on the corresponding card's popup
   */
  @Override
  public String toString() {
    return getStringForPopup(this.getClass().getSimpleName());
  }
}
