package ooga.model.tiles;

import ooga.configuration.CSVLoader;
import ooga.configuration.RuleFactory;
import ooga.model.Player;

import java.util.List;


/**
 * @author Muazzam Khan Noorpuri
 * @author Sylvie Mason
 * Purpose - The PropertyTile.java class represents the property tiles that a player can land upon in the
 * backend.
 * Assumptions - No general Tile will be created. Must use an extension
 * Dependencies - Tile.java
 */
public class Property extends Tile {

  private int rent;
  private int initialRent;
  private int price;
  private Player owner;
  private int forMonopoly;
  private int houseCount;
  private int hotelCount;
  private boolean monopoly = false;
  private int mortgage;
  private int houseHotelCost;
  private String gameType;
  private RuleFactory ruleFactory;
  private static final int FOR_SALE = 0;
  private static final int OWNED = 1;
  private static final int MORTGAGED = 2;
  private static final int UNAVAILABLE = 3;
  private final List<String> messages;

  /**
   * Constructor creates an extension of Tile and sets the pop message for when landed upon, the
   * price and the mortgage value.
   * @param tileName
   * @param color
   * @param initialPrice
   */
  public Property(String tileName, String color, int initialPrice) {
    super(tileName, color, FOR_SALE);
    String language = (new CSVLoader("languageChoice", "")).getValues().get(0);
    this.messages = (new CSVLoader("propertyClassMessages", language)).getValues();
    price = initialPrice;
    mortgage = initialPrice / 2;
    //setForMonopoly();
    //status = color.toUpperCase().equals("WHITE") ? 1 : 0;
  }

  /**
   * set the initial rent and cost for a house/hotel
   */
  public void initialize() {
    ruleFactory = new RuleFactory(gameType);
    initialRent = (int) (ruleFactory.getInitialRentMultiplier() * price);
    rent = initialRent;
    houseHotelCost = (int) (ruleFactory.getHouseHotelCost() * price);
  }

  /**
   * @param p
   * @return true if the property is for sale. Else make player pay rent and return false
   */
  @Override
  public boolean landedUpon(Player p) {
    if (this.getStatus() == FOR_SALE) {
      return true;
    } else if (this.getStatus() == OWNED && !p.equals(owner)) {
      if (rent > p.getNetWorth()) {
        p.setAlive(false);
        p.payRemaining(owner);
      } else {
        p.editWealth(rent * -1);
        owner.editWealth(rent);
      }
    }
    return false;
  }

  /**
   * Set the tile's current pop up messages that will be displayed to the user
   * @param p
   */
  @Override
  public void setPopUpStrings(Player p) {
    if (this.getStatus() == OWNED) {
      if (!p.equals(owner)) {
        this.getPopUpStrings().clear();
        this.getPopUpStrings()
            .add(messages.get(0) + " " + this.getOwner().getMyName() + " $" + this.getRent());
        this.getPopUpStrings().add("OK");
      } else {
        this.getPopUpStrings().clear();
        this.getPopUpStrings().add(messages.get(1) + " " + this.getName() + messages.get(2));
        this.getPopUpStrings().add("OK");
      }
    } else if (this.getStatus() == FOR_SALE) {
      this.getPopUpStrings()
          .add(messages.get(3) + " " + this.getName() + messages.get(4) + this.getPrice() + "?");
      this.getPopUpStrings().add("Yes");
      this.getPopUpStrings().add("No");
    } else {
      this.getPopUpStrings().clear();
      this.getPopUpStrings().add(this.getName() + messages.get(5));
      this.getPopUpStrings().add("OK");
    }

  }

  /**
   * set whether or not this player has a monopoly
   * @param updated
   */
  public void setMonopoly(boolean updated) {
    monopoly = updated;
  }

  /**
   * update the property's rent based on the real estate on it
   */
  public void updateRent() {
    if (monopoly) {
      if (houseCount > 0) {
        rent = (int) (initialRent * houseCount * ruleFactory.getHouseIncrement());
      }
      if (hotelCount > 0) {
        rent = (int) (initialRent * hotelCount * ruleFactory.getHotelIncrement());
      } else {
        rent = initialRent;
      }
    }
  }

  /**
   * @return the price of the property
   */
  @Override
  public int getPrice() {
    return price;
  }

  public void setOwner(Player newOwner) {
    owner = newOwner;
  }

  public Player getOwner() {
    return owner;
  }

  public int getRent() {
    return rent;
  }

  public void setForMonopoly(int num) {
    //if(this.getColor().equals(Paint.valueOf("BROWN")) || this.getColor().equals(Paint.valueOf("MEDIUMBLUE"))){ forMonopoly = 2; }
    //else{ forMonopoly = 3;}
    forMonopoly = num;
  }


  public int getForMonopoly() {
    return forMonopoly;
  }

  public int getHouseCount() {
    return houseCount;
  }

  public void editHouseCount(int change) {
    houseCount += change;
  }

  public int getHotelCount() {
    return hotelCount;
  }

  public void editHotelCount(int change) {
    hotelCount += change;
  }

  public int getMortgage() {
    return mortgage;
  }

  public int getHouseHotelCost() {
    return houseHotelCost;
  }

  public void setGameType(String type) {
    gameType = type;
    initialize();
  }
}
