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
 * Purpose - The SellHouseButton.java class is the class that represents the SellHouse button in PlayerPanel.
 * <p>
 * Assumptions - This class extends PanelButton.java
 * <p>
 * Dependencies - This class depends on Player, Property, Tile, PanelButton, and ViewComponents.java.
 */
public class SellHouseButton extends PanelButton {

  /**
   * Constructor, adds function
   *
   * @param prop - Button id
   * @param player - pointer to the currentplayer
   * @param allPlayers - list of all the players in the game
   * @param language - game language
   */
  public SellHouseButton(String prop, Player player, List<Player> allPlayers, String language) {
    super(prop, language);
    this.setOnAction(e -> removeHouse(player, language));
  }

  private void removeHouse(Player player, String language) {
    List<Property> pList = new ArrayList<>();
    for (Tile t : player.getOwnedTiles()) {
      if (t instanceof Property) {
        Property p = (Property) t;
        if (p.getHouseCount() > 0) {
          pList.add(p);
        }
      }
    }

    ChoiceDialog<String> popup = ViewComponents.createSellHousePopUp(pList, language);
    popup.showAndWait();
    if (popup.getSelectedItem() == null) {
      return;
    }
    //Backend method here, popup.getSelectedItem() is name, ex. "Baltic Avenue"
    player.sellRealEstate(popup.getSelectedItem());
    player.getPanel().getNamePanel().decreaseHouseCount(popup.getSelectedItem(), player);
    player.getPanel().getMoneyPanel().updateBalance(player);
  }

  @Override
  public void updateStatus(Player player) {
    if (player.getMonopolyCount() > 0) {
      for (List<Tile> tileList : player.getMonopolies().values()) {
        for (Tile t : tileList) {
          Property p = (Property) t;
          if (p.getHouseCount() > 0) {
            this.setDisable(false);
            break;
          }
        }
      }
    }
  }
}
