package ooga.model;

import javafx.scene.paint.Paint;
import ooga.configuration.RuleFactory;
import ooga.model.tiles.Property;
import ooga.model.tiles.Railroad;
import ooga.model.tiles.Tile;
import ooga.model.tiles.UtilityTile;
import ooga.visualization.PlayerPanel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Player class represents each individual player and stores all their information throughout the game
 * @author Sylvie Mason
 * @author Muazzam Khan
 */
public class Player {

  private Tile location;
  String myName;
  private int wealth;
  private int netWorth;
  private List<Tile> ownedTiles = new ArrayList<>();
  private Map<Paint, List<Tile>> propertyMap = new HashMap<>();
  private int utilitiesCount = 0;
  private int railroadCount = 0;
  private int suspended = 0;
  private int monopolyCount = 0;
  private Map monopolies = new HashMap<>();
  private int houseCount = 0;
  private int hotelCount = 0;
  private boolean getOutOfJail;
  private PlayerPanel panel;
  private boolean trade = false;
  private boolean alive = true;

  private int FOR_SALE = 0;
  private final int OWNED = 1;
  public static final int MORTGAGED = 2;

  /**
   * Constructor creates a player and sets their starting position and name
   * @param startingPosition the starting tile on the board
   * @param name the name of the player
   * @param tilePath the type of game being played, used to create an instance of RuleFactory
   */
  public Player(Tile startingPosition, String name, String tilePath) {
    myName = name;
    location = startingPosition;
    getOutOfJail = false;
    RuleFactory ruleFactory = new RuleFactory(tilePath);
    wealth = ruleFactory.getStartingBalance();
    netWorth = wealth;
  }

  /**
   * moves the Player on the board by the input value
   *
   * @param diceRoll
   */
  public void movePlayer(int diceRoll) {
    Tile tempLocation = location;
    for (int i = 0; i < diceRoll; i++) {
      tempLocation = tempLocation.next();
      if (tempLocation.getName().equals("Go")) {
        wealth += 200;
        updateNetWorth();
      }
    }
    location = tempLocation;
  }

  /**
   * sets the player's location on the board to a new tile
   * @param target tile the player is moved to
   */
  public void setLocation(Tile target) {
    while (!location.equals(target)) {
      movePlayer(1);
    }
  }

  /**
   * this method executes the buying of a property when a player chooses to do so
   */
  public void buyProperty() {
    ownedTiles.add(location);
    if (location instanceof Property) {
      wealth -= location.getPrice(); //to value
      ((Property) location).setOwner(this);
      propertyMap.putIfAbsent(location.getColor(), new ArrayList<Tile>());
      propertyMap.get(location.getColor()).add(location);
      checkForMonopoly();
    } else if (location instanceof Railroad) {
      wealth -= ((Railroad) location).getPrice();
      ((Railroad) location).setOwner(this);
      railroadCount += 1;
    } else if (location instanceof UtilityTile) {
      wealth -= ((UtilityTile) location).getPrice();
      ((UtilityTile) location).setOwner(this);
      utilitiesCount += 1;
    }
    location.setStatus(OWNED);
    updateNetWorth();
  }

  /**
   * this method iterates through the players' list of properties to see if they have a monopoly
   */
  public void checkForMonopoly() {
    for (Paint color : propertyMap.keySet()) {
      List<Tile> tileList = propertyMap.get(color);
      if (!tileList.isEmpty() && tileList.size() == ((Property) tileList.get(0)).getForMonopoly()) {
        monopolies.put(color, tileList);
        monopolyCount += 1;
      }
    }
  }

  /**
   * this method executes the buying of a house on a given property
   * @param name name of the player who is buying the house
   */
  public void buyHouse(String name) {
    for (Tile t : ownedTiles) {
      if (name.contains(t.getName())) {
        houseCount += 1;
        ((Property) t).editHouseCount(1);
        wealth -= ((Property) t).getHouseHotelCost();
      }
    }
    updateNetWorth();
  }

  /**
   * this method executes the buying of a hotel on a given property
   * @param name name of the player who is buying the hotel
   */
  public void buyHotel(String name) {
    for (Tile t : ownedTiles) {
      if (t.getName().equals(name)) {
        hotelCount += 1;
        houseCount -= 4;
        ((Property) t).editHotelCount(1);
        ((Property) t).editHouseCount(-4);
        wealth -= ((Property) t).getHouseHotelCost();
      }
    }
    updateNetWorth();
  }

  /**
   * executes the mortgaging of a property when the player chooses to do so
   * @param name name of the property the player is mortgaging
   */
  public void mortgage(String name) {
    for (Tile t : ownedTiles) {
      if (t.getName().equals(name)) {
        wealth += ((Property) t).getMortgage();
        t.setStatus(MORTGAGED);
        //remove from monopoly
        monopolies.remove(t.getColor(), monopolies.get(t.getColor()));
      }
    }
    updateNetWorth();
  }

  /**
   * executes the unmortgaging of a property when the player chooses to do so
   * @param name name of the property the player is unmortgaging
   */
  public void unmortgage(String name) {
    for (Tile t : ownedTiles) {
      if (t.getName().equals(name)) {
        wealth -= ((Property) t).getMortgage();
        t.setStatus(OWNED);
        checkForMonopoly();
      }
    }
    updateNetWorth();
  }

  /**
   * sells a house or a hotel when the user chooses to do so based on what they have on the given property
   * @param name name of the property they want to sell real estate from
   */
  public void sellRealEstate(String name) {
    for (Tile t : ownedTiles) {
      if (t.getName().equals(name)) {
        if (((Property) t).getHotelCount() == 1) {
          ((Property) t).editHotelCount(-1);
        } else {
          ((Property) t).editHouseCount(-1);
        }
        wealth += ((Property) t).getHouseHotelCost();
      }
    }
    updateNetWorth();
  }

  /**
   * executes a trade between two players
   * @param tile the tile to be traded
   * @param addedWealth the money the player gains from the trade
   * @param reducedWealth the money the player loses from the trade
   */
  public void executeTrade(Tile tile, int addedWealth, int reducedWealth) {
    if (tile instanceof Property) {
      Player formerOwner = ((Property) tile).getOwner();
      formerOwner.getOwnedTiles().remove(tile);
      formerOwner.propertyMap.get(tile.getColor()).remove(tile);
      formerOwner.getMonopolies().remove(tile.getColor());
      ((Property) tile).setOwner(this);
      propertyMap.putIfAbsent(tile.getColor(), new ArrayList<Tile>());
      propertyMap.get(tile.getColor()).add(tile);
      ownedTiles.add(tile);
      checkForMonopoly();
    } else if (tile instanceof Railroad) {
      Player formerOwner = ((Railroad) tile).getOwner();
      formerOwner.getOwnedTiles().remove(tile);
      ((Railroad) tile).setOwner(this);
      ownedTiles.add(tile);
      railroadCount++;
      formerOwner.decreaseRailroadCount();
    } else if (tile instanceof UtilityTile) {
      Player formerOwner = ((UtilityTile) tile).getOwner();
      formerOwner.getOwnedTiles().remove(tile);
      ((UtilityTile) tile).setOwner(this);
      ownedTiles.add(tile);
      utilitiesCount++;
      formerOwner.decreaseUtilitiesCount();
    }
    editWealth(addedWealth);
    editWealth(-reducedWealth);
    updateNetWorth();
  }

  /**
   * this method updates the net worth of the player
   */
  public void updateNetWorth() {
    netWorth = wealth;
    for (Tile tile : ownedTiles) {
      if (tile.getStatus() != 1) {
        continue;
      }
      if (tile instanceof Property) {
        netWorth += ((Property) tile).getMortgage();
      } else if (tile instanceof Railroad) {
        netWorth += ((Railroad) tile).getMortgage();
      } else if (tile instanceof UtilityTile) {
        netWorth += ((UtilityTile) tile).getMortgage();
      }
    }
  }

  /**
   * this method pays the player's remaining value to the player they owe
   * @param owner player the current player owes money to
   */
  public void payRemaining(Player owner) {
    owner.editWealth(this.netWorth);
  }

  /**
   * clears the player's owned tiles and properties and resets all their tiles to for sale when they lose
   */
  public void endPlayer() {
    for (Tile t : ownedTiles) {
      t.setStatus(FOR_SALE);
    }
    ownedTiles.clear();
    propertyMap.clear();
  }

  public Tile getLocation() {
    return location;
  }

  public String getMyName() {
    return myName;
  }

  public void editWealth(int change) {
    wealth += change;
  }

  public List<Tile> getOwnedTiles() {
    return ownedTiles;
  }

  public int getWealth() {
    return wealth;
  }

  public int getNetWorth() {
    return netWorth;
  }

  public int getUtilitiesCount() {
    return utilitiesCount;
  }

  public void decreaseUtilitiesCount() {
    utilitiesCount--;
  }

  public void setSuspended(int value) {
    suspended = value;
  }

  public int getSuspended() {
    return suspended;
  }

  public int getRailroadCount() {
    return railroadCount;
  }

  public void decreaseRailroadCount() {
    railroadCount--;
  }

  public void setGetOutOfJail(boolean status) {
    getOutOfJail = status;
  }

  public boolean canGetOutOfJail() {
    return getOutOfJail;
  }

  public int getHotelCount() {
    return hotelCount;
  }

  public int getHouseCount() {
    return houseCount;
  }

  public void setPanel(PlayerPanel p) {
    this.panel = p;
  }

  public PlayerPanel getPanel() {
    return this.panel;
  }

  public Map<Paint, List<Tile>> getPropertyMap() {
    return propertyMap;
  }

  public Map<Paint, List<Tile>> getMonopolies() {
    return monopolies;
  }

  public int getMonopolyCount() {
    return monopolyCount;
  }

  public boolean isAlive() {
    return alive;
  }

  public void setAlive(boolean status) {
    alive = false;
  }

  public void setHotelCountForTesting(int count) {
    hotelCount = count;
  }

  public void setHouseCountForTesting(int count) {
    houseCount = count;
  }

  public void setRailroadCountForTesting(int count) {
    railroadCount = count;
  }
}

