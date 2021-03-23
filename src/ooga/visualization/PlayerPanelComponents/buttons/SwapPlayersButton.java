package ooga.visualization.PlayerPanelComponents.buttons;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.ChoiceDialog;
import ooga.model.Player;
import ooga.model.tiles.Tile;
import ooga.visualization.PlayerPanel;
import ooga.visualization.PlayerPanelComponents.PanelButton;
import ooga.visualization.ViewComponents;


/**
 * @author Ryan Feinberg
 * <p>
 * Purpose - The SwapPlayersButton.java class is the class that represents the SwapPlayers button in PlayerPanel.
 * <p>
 * Assumptions - This class extends PanelButton.java
 * <p>
 * Dependencies - This class depends on Player, Tile, PanelButton, and ViewComponents.java.
 */
public class SwapPlayersButton extends PanelButton {

 
  private Player myPlayer;

   /**
   * Constructor, adds function
   *
   * @param prop - Button id
   * @param player - pointer to the currentplayer
   * @param allPlayers - list of all the players in the game
   * @param language - game language
   */
  public SwapPlayersButton(String prop, Player player, List<Player> allPlayers, String language) {
    super(prop, language);
    this.myPlayer = player;
    this.setOnAction(e -> swap(allPlayers, language));
  }

  private void swap(List<Player> listOfPlayers, String language) {
    List<Player> others = new ArrayList<>(listOfPlayers);
    others.remove(myPlayer);
    ChoiceDialog<String> result = ViewComponents.createSwapPopUp(others, language);
    result.showAndWait();
    Player chosenPlayer = null;
    for (Player p : others) {
      if (p.getMyName().equals(result.getSelectedItem())) {
        chosenPlayer = p;
      }
    }
    Tile oldLocation = myPlayer.getLocation();
    myPlayer.setLocation(chosenPlayer.getLocation());
    chosenPlayer.setLocation(oldLocation);

    PlayerPanel currentPanel = myPlayer.getPanel();
    PlayerPanel chosenPanel = chosenPlayer.getPanel();

    //Need better way to update Pieces, since only have access to 1 pieceView per turn
    chosenPanel.updatePieceLocation();
    currentPanel.updatePieceLocation();
  }


  @Override
  public void updateStatus(Player p) {
    if (p.getPanel().getNumOfPlayers() > 1) {
      this.setDisable(false);
    }
    this.myPlayer = p;
  }
}
