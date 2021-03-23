package ooga.visualization.PlayerPanelComponents;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import ooga.model.Player;
import ooga.configuration.CSVLoader;
import ooga.visualization.ViewComponents;

/**
 * @author Ryan Feinberg
 * <p>
 * Purpose - The PlayerMoneyPanel.java class is the class that represents the visual displays of the current player's money in PlayerPanel.
 * <p>
 * Assumptions - This class extends HBox. 
 * <p>
 * Dependencies - This class depends on Player.java, CSVLoader.java, and ViewComponents.java.
 */

public class PlayerMoneyPanel extends HBox {

  private String message;
  Label balance;

  /**
   * Constructor, fills HBox with name label and balance display
   *
   * @param currentPlayer - pointer to the current player
   * @param language - game language
   */
  public PlayerMoneyPanel(Player currentPlayer, String language) {
    message = new CSVLoader("moneyPanelMessages", language).getValues().get(0);
    balance = ViewComponents
        .createLabel("Balance", message + currentPlayer.getWealth(), 0, 0);
    this.getChildren().add(balance);
  }

  /**
   * This method updates the balance on the PlayerPanel to reflect the currentPlayer's updated balance.
   *
   * @param currentPlayer - pointer to the currentplayer
   */
  public void updateBalance(Player currentPlayer) {
    balance.setText(message + currentPlayer.getWealth());
  }
}
