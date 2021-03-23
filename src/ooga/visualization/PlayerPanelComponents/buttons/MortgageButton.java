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
 * Purpose - The MortgageButton.java class is the class that represents the Mortgage button in PlayerPanel.
 * <p>
 * Assumptions - This class extends PanelButton.java
 * <p>
 * Dependencies - This class depends on Player, Property, Tile, PanelButton, and ViewComponents.java.
 */

public class MortgageButton extends PanelButton {

  private Player myPlayer;

   /**
   * Constructor, adds function
   *
   * @param prop - Button id
   * @param player - pointer to the currentplayer
   * @param allPlayers - list of all the players in the game
   * @param language - game language
   */
  public MortgageButton(String prop, Player player, List<Player> allPlayers, String language) {
    super(prop, language);
    this.myPlayer = player;
    this.setOnAction(e -> mortgage(language));
  }

  private void mortgage(String language) {
    List<Tile> tileList = new ArrayList<>();
    for (Tile t : myPlayer.getOwnedTiles()) {
      if (t instanceof Property) {
        Property p = (Property) t;
        if (p.getHouseCount() == 0) {
          tileList.add(t);
        }
      } else {
        tileList.add(t);
      }
    }

    ChoiceDialog<String> popup = ViewComponents.createMortgagePopUp(tileList, language);
    popup.showAndWait();
    if (popup.getSelectedItem() == null) {
      return;
    }

    myPlayer.mortgage(popup.getSelectedItem());
    myPlayer.getPanel().getButtonPanel().refreshButtons(myPlayer);
    myPlayer.getPanel().getNamePanel().mortgageProperty(popup.getSelectedItem(), myPlayer);
    myPlayer.getPanel().getMoneyPanel().updateBalance(myPlayer);
  }

  @Override
  public void updateStatus(Player player) {
    for (Tile t : player.getOwnedTiles()) {
      if (!(t instanceof Property)) {
        this.setDisable(false);
        break;
      } else {
        Property p = (Property) t;
        if (p.getHouseCount() == 0) {
          this.setDisable(false);
          break;
        }
      }
    }
    this.myPlayer = player;
  }
}
