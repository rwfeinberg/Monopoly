package ooga.visualization.PlayerPanelComponents;

import java.util.List;
import javafx.scene.control.Button;
import ooga.configuration.CSVLoader;
import ooga.model.Player;

/**
 * @author Ryan Feinberg
 * <p>
 * Purpose - The PanelButton.java class is the superclass to be extended when creating a new
 * panel button.
 * <p>
 * Assumptions - This class should be extended when creating new game button subclasses to be
 * added to the player panel, and all of its abstract methods should be overwritten (if any).
 * <p>
 * Dependencies - This class depends on Player.java as parameters in its
 * updateStatus method, as well as CSVLoader in order to read from csvs.
 */
public abstract class PanelButton extends Button {

  public PanelButton(String prop) {
    this.setId(prop);
    this.setText(prop);
    this.setDisable(true);
  }

/**
   * Default constructor, sets id, label, and disable
   *
   * @param prop      - button id string
   * @param language - game language string
   */
  public PanelButton(String prop, String language) {
    this.setId(prop);
    List<String> buttonText = (new CSVLoader(prop.toLowerCase() + "Button", language)).getValues();
    this.setText(buttonText.get(0));
    this.setDisable(true);
  }

/**
   * This method is responsible for enabling/disabling a given button when appropriate.
   * 
   * @param p - The player whose turn it is currently.
   */
  public abstract void updateStatus(Player p);
}
