package ooga.model;

import javafx.util.Pair;
import ooga.model.tiles.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * Turn class contains the methods needed for a single turn of the monopoly game to occur
 * @author Sylvie Mason
 * @author Muazzam Khan
 */
public class Turn {

  private Player currentPlayer;
  private Pair<Integer, Integer> rollAmount;
  private boolean promptUser = false;
  private List<String> popUpStrings = new ArrayList<>();

  /**
   * executes a singular turn for the given player
   *
   * @param player the player who's turn it is
   */
  public Turn(Player player, int roll1, int roll2) {
    currentPlayer = player;
    rollAmount = new Pair<>(roll1, roll2);

    boolean furtherStepNeeded = true;
    if (player.getSuspended() == 0 || player.canGetOutOfJail() || roll1 == roll2) {
      player.setSuspended(0);
      player.setGetOutOfJail(false);

      move(roll1 + roll2);
      while(furtherStepNeeded){

        Tile initialPosition = player.getLocation();
        handleCurrentProperty();
        if (!initialPosition.isFurtherStepNeeded()) {
          furtherStepNeeded = false;
        }
      }
    } else {
      popUpStrings.addAll(stayInJailPopUpStrings());
      currentPlayer.setSuspended(Math.max(currentPlayer.getSuspended() - 1, 0));
    }
    if (!player.isAlive()) {
      player.endPlayer();
    }
    currentPlayer.updateNetWorth();
  }

  private List<String> stayInJailPopUpStrings() {
    List<String> popUp = new ArrayList<>();
    popUp.add("Guess you're staying here!");
    popUp.add("OK");
    return popUp;
  }

  private void move(int roll) {
    currentPlayer.movePlayer(roll);
  }

  private void handleCurrentProperty() {
    Tile currentTile = currentPlayer.getLocation();
    if (currentTile.landedUpon(currentPlayer)) {
      promptUser = true;
    }
    currentTile.setPopUpStrings(currentPlayer);
    popUpStrings.addAll(currentTile.getPopUpStrings());
  }

  public boolean getPromptUser() {
    return promptUser;
  }

  public int getDiceAmount() {
    return rollAmount.getKey() + rollAmount.getValue();
  }

  public Pair<Integer, Integer> getDice() {
    return rollAmount;
  }

  public List<String> getPopUpStrings() {
    return popUpStrings;
  }
}
