package ooga.visualization.PlayerPanelComponents.buttons;

import javafx.scene.control.ChoiceDialog;
import ooga.model.Player;
import ooga.model.tiles.Property;
import ooga.model.tiles.Tile;
import ooga.visualization.PlayerPanelComponents.PanelButton;
import ooga.visualization.ViewComponents;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ryan Feinberg
 * <p>
 * Purpose - The BuyHouseButton.java class is the class that represents the BuyHouse button in PlayerPanel.
 * <p>
 * Assumptions - This class extends PanelButton.java
 * <p>
 * Dependencies - This class depends on Player, Property, Tile, PanelButton, and ViewComponents.java.
 */
public class BuyHouseButton extends PanelButton {

  /**
   * Constructor, adds function
   *
   * @param prop - Button id
   * @param player - pointer to the currentplayer
   * @param allPlayers - list of all the players in the game
   * @param language - game language
   */
  public BuyHouseButton(String prop, Player player, List<Player> allPlayers, String language) {
    super(prop, language);
    this.setOnAction(e -> addHouse(player, language));
  }

  private void addHouse(Player player, String language) {
    for (List<Tile> tileList : player.getMonopolies().values()) {
      ChoiceDialog<String> popup = ViewComponents.createBuyHousePopUp(tileList, language);
      popup.showAndWait();
      if (popup.getSelectedItem() == null) {
        return;
      }

      if (player.getWealth() < ((Property) player.getLocation()).getHouseHotelCost()) {
        ViewComponents.createConfirmPopUp(getInvalidPurchaseStrings());
      } else {
        player.buyHouse(popup.getSelectedItem());
        player.getPanel().getButtonPanel().refreshButtons(player);
        player.getPanel().getNamePanel().increaseHouseCount(popup.getSelectedItem(), player);
        player.getPanel().getMoneyPanel().updateBalance(player);
      }
    }
  }

  @Override
  public void updateStatus(Player player) {
    if (player.getMonopolyCount() > 0) {
      this.setDisable(false);
    }
  }

  private List<String> getInvalidPurchaseStrings() {
    ArrayList<String> strings = new ArrayList<>();
    strings.add("You do not have enough money for this purchase.");
    strings.add("OK");
    return strings;
  }

}
