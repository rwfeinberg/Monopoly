package plan.usecases;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import plan.usecases.dummyclasses.Player;

/**
 * This interface represents the right side of the application, which is the current player's control panel.
 * Every Player in the game will hold an instance of their respective PlayerPanel in order to associate the two.
 * This panel will include buttons for rolling the dice, buying/selling houses and hotels, making trades, and ending turns,
 * as well as a dropdown menu to manage their properties/deeds, and a display label at the top indicating their name/piece.
 * There will also be a visual towards the button that displays the current Player's money.
 */
public abstract class PlayerPanel {

    /**
     *This method creates the entire panel described above, it will call the makeDropDow() and createButton() methods to do so.
     *
     * @param player - Associated Player, holds an instance of this panel. Passed in to retrieve name, properties, piece, and money
     */
    public abstract void createPanel(Player player);

    /**
     * This method, used solely in createPanel(), will create a javaFX dropdown either through a ComboBox or something similar.
     * The dropdown will contain a list of all of the Player's owned properties.
     *
     * @param player - Associated Player, passed in to retrieve properties.
     */
    public abstract void makeDropDown(Player player);

    /**
     * This method, used solely in createPanel(), will create a javaFX button for the roll dice, make trade, buy/sell house, and ending turn actions
     *
     * @param title - Some sort of String indicating the label of the button as well as which function will be applied to it.
     * @param handler - The action (in form of a lambda) to be assigned to the button.
     */
    public abstract void createButton(String title, EventHandler<ActionEvent> handler);

    /**
     * Adds property to dropdown menu when Player purchases a new property (or recieves one in trade).
     *
     * @param propertyName - String that represents the property to be added
     */
    public abstract void addProperty(String propertyName);

    /**
     * Removes property to dropdown menu when Player sells a property (or gives one away in trade).
     *
     * @param propertyName - String that represents the property to be removed
     */
    public abstract void removeProperty(String propertyName);

    /**
     * Update money visual on panel. Negative value in order to reduce money total.
     * This method would be called by Player.panel
     *
     * @param value - Amount to be reduced
     */
    public abstract void updateMoney(int value);
}
