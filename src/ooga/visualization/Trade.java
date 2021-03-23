package ooga.visualization;

import ooga.model.Player;
import ooga.model.tiles.Tile;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Catherine Livingston
 * <p>
 * Purpose - The Trade.java class is used to create mechanism to make players do the trade.
 * <p>
 * Assumptions - Assumes that the property names being passed in are in the players owned tiles.
 * <p>
 * Dependencies - This class depends on Player, Tile.
 */
public class Trade {

  private Player player1;
  private int moneyAmount1 = 0;
  private Tile property1;
  private Player player2;
  private int moneyAmount2 = 0;
  private Tile property2;

  /**
   * Constructor, initializes view of the piece with params and adds to game board
   *
   * @param player1 - the first player in the trade
   * @param moneyAmount1 - the amount of money being given by player1.
   * @param propertyName1 - the name of the property being given by player1
   * @param player2 - the name of the other player in the traded
   * @param moneyAmount2 - the amount of money being given by player2
   * @param propertyName2 - the name of the property being given by player2
   */
  public Trade(Player player1, String moneyAmount1, String propertyName1,
      Player player2, String moneyAmount2, String propertyName2) {
    this.player1 = player1;
    if (!moneyAmount1.equals("")) {
      this.moneyAmount1 = Integer.parseInt(moneyAmount1);
    }
    if (propertyName1 != null) {
      this.property1 = findTile(player1, propertyName1);
    }
    this.player2 = player2;
    if (!moneyAmount2.equals("")) {
      this.moneyAmount2 = Integer.parseInt(moneyAmount2);
    }
    if (propertyName2 != null) {
      this.property2 = findTile(player2, propertyName2);
    }
    if (checkValidMoneyTransfer()) {
      executeTrade();
    }
  }

  /**
   * finds the tile  to do the trade with
   *
   * @param currPlayer - name of the background
   * @param propertyName
   */
  public Tile findTile(Player currPlayer, String propertyName) {
    for (Tile option : currPlayer.getOwnedTiles()) {
      if (option.getName().equals(propertyName)) {
        return option;
      }
    }
    return null;
  }

  /**
   * makes the necessary trade after all values have been assigned
   */
  public void executeTrade() {
    if (player2 != null) {
      player1.executeTrade(property2, moneyAmount2, moneyAmount1);
      player2.executeTrade(property1, moneyAmount1, moneyAmount2);
    }
    player1.updateNetWorth();
    player2.updateNetWorth();
  }

  private boolean checkValidMoneyTransfer() {
    if (moneyAmount2 > player1.getWealth() || moneyAmount1 > player2.getWealth()) {
      List<String> popUpStrings = new ArrayList<>();
      popUpStrings.add("The money transfer is invalid because a player had insufficient funds!");
      popUpStrings.add("OK");
      ViewComponents.createConfirmPopUp(popUpStrings);
      return false;
    }
    return true;
  }

  public Player getCurrentPlayer() {
    return player1;
  }

  public Player getOtherPlayer() {
    return player2;
  }

  public int getCurrentPlayerMoneyAmount() {
    return moneyAmount1;
  }

  public int getOtherPlayerMoneyAmount() {
    return moneyAmount2;
  }

  public Tile getCurrentPlayerProperty() {
    return property1;
  }

  public Tile getOtherPlayerProperty() {
    return property2;
  }
}
