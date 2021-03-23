package api;

import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import plan.usecases.dummyclasses.Player;

/**
 * API interfaces for the frontend visualization.
 */
public class VisualAPI {


    /**
     * This interface represents the visual component of a tile on the monopoly board. It is in charge of updating the houses/hotels
     * placed on it as well as initializing its color, name, and purchase price to be shown on the board.
     */
    public interface VisualTile {

        /**
         * Utilize .properties file to load properties for the given tile to be displayed, including color, name, and price.
         * These properties will be displayed on the board in the left side of the application.
         */
        public void setTileInformation(int tileNumber);

        /**
         * Increment house visual on tile.
         * To be called in addition to a Player.buyHouse() action.
         */
        public void addHouse();

        /**
         * Increment hotel visual on tile.
         * To be called in addition to a Player.buyHotel() action.
         */
        public void addHotel();

        /**
         * Decrement house visual on tile.
         * To be called in addition to a Player.sellHouse() action.
         */
        public void subHouse();

        /**
         * Decrement hotel visual on tile.
         * To be called in addition to a Player.sellHotel() action.
         */
        public void subHotel();

        //TODO: These next two methods MAY need to be defined in the Player interface or somewhere in the backend

        /**
         * Set an owner of the property to know who to pay to.
         * The VisualTile as well as the backend Tile will now become associated with the Player who bought it
         */
        public void setOwner(Player buyer);

        /**
         * Called inside of setTileInformation to find instances within the same color group. (Find the other tiles of its color)
         * The purpose of this is to determine when a player has a monopoly/can build houses.
         */
        public List<VisualTile> getColorGroup();
    }

    /**
     * This interface represents the visual component of a card in the game. It will solely appear when a property is clicked on
     * in a player's card dropdown menu to display all of the cards relevant information (rent prices, mortgage/unmortgage prices,
     * house prices, etc.)
     */
    public interface VisualCard {

        /**
         * Utilizes a .properties file to load properties for given deed to be displayed in the dropdown menu.
         * This properties file will be the same file used in VisualTile.setTileInformation() for each card.
         */
        public void setCardInformation(int cardNumber);

        /**
         * Creates a dialog box/pop-up window containing a visual that displays the card's information.
         * This method should be called by the PlayerPanel class to happen on the onClick action set to the dropdown menu item.
         */
        public void displayCard();
    }

    /**
     * This interface represents the right side of the application, which is the current player's control panel.
     * Every Player in the game will hold an instance of their respective PlayerPanel in order to associate the two.
     * This panel will include buttons for rolling the dice, buying/selling houses and hotels, making trades, and ending turns,
     * as well as a dropdown menu to manage their properties/deeds, and a display label at the top indicating their name/piece.
     * There will also be a visual towards the button that displays the current Player's money.
     */
    public interface PlayerPanel {

        /**
         *This method creates the entire panel described above, it will call the makeDropDow() and createButton() methods to do so.
         *
         * @param player - Associated Player, holds an instance of this panel. Passed in to retrieve name, properties, piece, and money
         */
        public void createPanel(Player player);

        /**
         * This method, used solely in createPanel(), will create a javaFX dropdown either through a ComboBox or something similar.
         * The dropdown will contain a list of all of the Player's owned properties.
         *
         * @param player - Associated Player, passed in to retrieve properties.
         */
        public void makeDropDown(Player player);

        /**
         * This method, used solely in createPanel(), will create a javaFX button for the roll dice, make trade, buy/sell house, and ending turn actions
         *
         * @param title - Some sort of String indicating the label of the button as well as which function will be applied to it.
         * @param handler - The action (in form of a lambda) to be assigned to the button.
         */
        public void createButton(String title, EventHandler<ActionEvent> handler);

        /**
         * Adds property to dropdown menu when Player purchases a new property (or recieves one in trade).
         *
         * @param propertyName - String that represents the property to be added
         */
        public void addProperty(String propertyName);

        /**
         * Removes property to dropdown menu when Player sells a property (or gives one away in trade).
         *
         * @param propertyName - String that represents the property to be removed
         */
        public void removeProperty(String propertyName);

        /**
         * Update money visual on panel. Negative value in order to reduce money total.
         * This method would be called by Player.panel
         *
         * @param value - Amount to be reduced
         */
        public void updateMoney(int value);
    }
}
