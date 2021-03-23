package ooga.model.tiles;

import javafx.scene.paint.Paint;
import ooga.model.Player;
import ooga.visualization.VisualTile;

import java.util.ArrayList;
import java.util.List;

/**
 * The purpose of this class is to create an abstraction of a Tile to represent the spaces on the Monopoly board
 * @author Sylvie Mason
 * @author Muazzam Khan Noorpuri
 */
public abstract class Tile {

  private String name;
  private Tile nextTile;
  private Paint color;
  private int status;
  private int price;
  private List<String> popUpStrings = new ArrayList<>();
  private ArrayList players;
  private boolean furtherStepNeeded = false;
  private VisualTile visualTile;
  private String gameType;

  private final int FOR_SALE = 0;

  /**
   * Constructor creates a tile and sets its name, color, and status
   * @param tileName name representing the name of the tile on the board
   * @param inputColor String corresponding to the paint value for the color of the tile
   * @param initialStatus the status of the tile at the beginning of game (for sale or unavailable)
   */
  public Tile(String tileName, String inputColor, int initialStatus) {
    name = tileName;
    color = Paint.valueOf(inputColor);
    status = initialStatus;
  }

  /**
   * Constructor creates a tile and sets its name, color, and status, price and player list. Used for
   * Tiles which require access to the list of players.
   * @param tileName name representing the name of the tile on the board
   * @param inputColor String corresponding to the paint value for the color of the tile
   * @param initialStatus the status of the tile at the beginning of game (for sale or unavailable)
   * @param initialPrice int the initial price of the tile
   * @param playerList list of players in the current game
   */
  public Tile(String tileName, String inputColor, int initialStatus, int initialPrice,
      ArrayList<String> playerList) {
    name = tileName;
    color = Paint.valueOf(inputColor);
    status = initialStatus;
    price = initialPrice;
    players = playerList;
  }

  /**
   * Used to store what the next tile is on the board. (ie. if the player moves 1 from this Tile,
   * they would move to the nextTile.
   * @param next
   */
  public void setNextTile(Tile next) {
    nextTile = next;
  }

  /**
   * @return the next Tile
   */
  public Tile next() {
    return nextTile;
  }

  /**
   * All extensions must have a method that explains what occurs when a player land on them
   * @param p
   * @return if further action is required (e.g. ask if the player wants to buy a property that is
   * for sale.
   */
  public abstract boolean landedUpon(Player p);

  /**
   * Set the text for the any pop up messages when the player lands on the tile
   * @param currentPlayer
   */
  public abstract void setPopUpStrings(Player currentPlayer);

  /**
   * @return the tile's relevant pop-up message
   */
  public List<String> getPopUpStrings() {
    return popUpStrings;
  }

  public void setStatus(int newStatus) {
    status = newStatus;
  }

  /**
   * @return the tile's status
   */
  public int getStatus() {
    return status;
  }

  /**
   * @return name of tile
   */
  public String getName() {
    return name;
  }

  /**
   * @return color of tile
   */
  public Paint getColor() {
    return color;
  }

  /**
   * @return price of tile
   */
  public abstract int getPrice();

  /**
   * @return whether further steps are needed
   */
  public boolean isFurtherStepNeeded() {
    return furtherStepNeeded;
  }

  /**
   * set the whether further steps are needed for this tile.
   * @param status
   */
  public void setFurtherStepNeeded(boolean status) {
    furtherStepNeeded = status;
  }

  /**
   * @return the visual tile that is linked to this model tile
   */
  public VisualTile getVisualTile() {
    return visualTile;
  }

  /**
   * set the visual tile that is linked to this model tile
   * @param visualTile
   */
  public void setVisualTile(VisualTile visualTile) {
    this.visualTile = visualTile;
  }

  /**
   * set the gametype
   * @param type
   */
  public void setGameType(String type) {
    gameType = type;
  }
}
