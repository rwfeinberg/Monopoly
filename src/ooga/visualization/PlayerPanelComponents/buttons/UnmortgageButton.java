package ooga.visualization.PlayerPanelComponents.buttons;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ChoiceDialog;
import ooga.model.Player;
import ooga.model.tiles.Property;
import ooga.model.tiles.Tile;
import ooga.visualization.PlayerPanelComponents.PanelButton;
import ooga.visualization.ViewComponents;

/**
 * @author Ryan Feinberg
 * <p>
 * Purpose - The UnmortgageButton.java class is the class that represents the Unmortgage button in PlayerPanel.
 * <p>
 * Assumptions - This class extends PanelButton.java
 * <p>
 * Dependencies - This class depends on Player, Property, Tile, PanelButton, and ViewComponents.java.
 */
public class UnmortgageButton extends PanelButton {

  /**
   * Constructor, adds function
   *
   * @param prop - Button id
   * @param player - pointer to the currentplayer
   * @param allPlayers - list of all the players in the game
   * @param language - game language
   */
  public UnmortgageButton(String prop, Player player, List<Player> allPlayers, String language) {
    super(prop, language);
    this.setOnAction(e -> unmortgage(player, language));
  }

  private void unmortgage(Player player, String language) {
    List<Tile> tileList = new ArrayList<>();
    for (Tile t : player.getOwnedTiles()) {
      if (t.getStatus() == Player.MORTGAGED) {
        tileList.add(t);
      }
    }

    ChoiceDialog<String> popup = ViewComponents.createUnmortgagePopUp(tileList, language);
    popup.showAndWait();
    if (popup.getSelectedItem() == null) {
      return;
    }

    player.unmortgage(popup.getSelectedItem());
    player.getPanel().getButtonPanel().refreshButtons(player);
    player.getPanel().getNamePanel().unmortgageProperty(popup.getSelectedItem(), player);
    player.getPanel().getMoneyPanel().updateBalance(player);
  }

  @Override
  public void updateStatus(Player player) {
    for (Tile t : player.getOwnedTiles()) {
      if (t.getStatus() == Player.MORTGAGED) {
        this.setDisable(false);
        break;
      }
    }
  }
}
