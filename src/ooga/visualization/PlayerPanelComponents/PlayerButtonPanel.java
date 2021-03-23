package ooga.visualization.PlayerPanelComponents;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import ooga.model.Player;
import ooga.visualization.PlayerPanel;

/**
 * @author Ryan Feinberg
 * <p>
 * Purpose - The PlayerButtonPanel.java class is the class that represents the entire group of PanelButtons in the PlayerPanel (excluding 
 * the roll dice button)
 * <p>
 * Assumptions - This class extends FlowPane, and the PACKAGE constant points to the list of subclasses of PanelButton.java
 * <p>
 * Dependencies - This class depends on Player.java and PlayerPanel.java.
 */
public class PlayerButtonPanel extends FlowPane {

  private final String PACKAGE = "ooga.visualization.PlayerPanelComponents.buttons.";
  private List<Button> buttonList = new ArrayList<>();

  /**
   * Constructor, fills FlowPane with buttons through reflection
   *
   * @param buttons - List of strings containing all the desired buttonIDs (gotten from a csv)
   * @param player - pointer to the currentplayer
   * @param allPlayers - list of all the players in the game
   * @param language - game language
   */
  public PlayerButtonPanel(List<String> buttons, Player player, List<Player> allPlayers,
      String language) {
    this.setHgap(PlayerPanel.DEFAULT_PADDING);
    this.setVgap(PlayerPanel.DEFAULT_PADDING);
    this.setId("ButtonPanel");

    for (String button : buttons) {
      Button b;
      try {
        b = (PanelButton) Class.forName(PACKAGE + button + "Button").getDeclaredConstructors()[0]
            .newInstance(button, player, allPlayers, language);
        buttonList.add(b);
      } catch (InstantiationException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
        b = new Button("INVALID");
      }
      this.getChildren().add(b);
    }
  }

  /**
  * This method calls the enabling/disabling method for each panelButton
  * 
  * @param player - pointer to the current player
  */
  public void refreshButtons(Player player) {
    for (Button b : buttonList) {
      PanelButton nb = (PanelButton) b;
      nb.updateStatus(player);
    }
  }
}
