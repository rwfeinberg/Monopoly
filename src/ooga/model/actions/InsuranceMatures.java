package ooga.model.actions;

import java.util.List;
import ooga.model.Player;

/**
 * The purpose of this class is to pay out a certain amount to a player and provide
 * a relevant String for a user-facing popup.
 * @author wyattfocht
 */
public class InsuranceMatures extends Action {

  /**
   * @param firstInt      an int value that might be used to perform specific calculations depending
   *                      on the action type
   * @param secondInt     another int value that might be used to perform specific calculations
   *                      depending on the action type
   * @param players       the current Player on which the action is occurring
   * @param currentPlayer List<Player> representing all other players in the game
   * @param resourcesPath
   */
  public InsuranceMatures(int firstInt, int secondInt, List<Player> players,
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
    editPlayerWealth(getFirstInt(), 1);
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
    return (getStringForPopup(this.getClass().getSimpleName()) + getFirstInt());
  }

}
