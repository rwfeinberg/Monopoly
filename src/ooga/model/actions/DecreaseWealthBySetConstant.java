package ooga.model.actions;

import java.util.List;
import ooga.model.Player;

/**
 * The purpose of this class is to decrease the wealth of a player by a hard-coded amount (for cheat
 * key)
 * @author wyattfocht
 */
public class DecreaseWealthBySetConstant extends Action {

  private static final int CONSTANT = 10;

  /**
   * @param firstInt      an int value that might be used to perform specific calculations depending
   *                      on the action type
   * @param secondInt     another int value that might be used to perform specific calculations
   *                      depending on the action type
   * @param players       the current Player on which the action is occurring
   * @param currentPlayer List<Player> representing all other players in the game
   * @param resourcesPath
   */
  public DecreaseWealthBySetConstant(int firstInt, int secondInt,
      List<Player> players,
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
    editPlayerWealth(CONSTANT, -1);
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
    return ("");
  }

}
