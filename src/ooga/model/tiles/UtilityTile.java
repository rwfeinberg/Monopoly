package ooga.model.tiles;

import java.util.List;
import ooga.configuration.CSVLoader;
import ooga.model.Die;
import ooga.model.Player;

/**
 * @author Muazzam Khan Noorpuri
 * @author Sylvie Mason
 * Purpose - The UtilityTile.java class represents the utility tiles that a player can land upon in the
 * backend.
 * Assumptions - No general Tile will be created. Must use an extension
 * Dependencies - Tile.java
 */
public class UtilityTile extends Tile {

  private static final int FOR_SALE = 0;
  private static final int OWNED = 1;
  private static final int MORTGAGED = 2;

  private final List<String> messages;
  private int price;
  private Player owner;
  private int mortgage;
  private int roll;
  private int amountOwed;

  /**
   * Constructor creates an extension of Tile and sets the pop message for when landed upon, initial
   * price and mortgage values
   * @param tileName
   * @param color
   * @param initialPrice
   */
  public UtilityTile(String tileName, String color, int initialPrice) {
    super(tileName, color, FOR_SALE);
    String language = (new CSVLoader("languageChoice", "")).getValues().get(0);
    this.messages = (new CSVLoader("utilityTileClassMessages", language)).getValues();
    price = initialPrice;
    mortgage = initialPrice / 2;
  }

  /**
   * @param p
   * @return true if the utility is for sale. Else make player pay 'rent' and return false
   */
  @Override
  public boolean landedUpon(Player p) {
    if (this.getStatus() == FOR_SALE) {
      return true;
    } else if (this.getStatus() == OWNED && !p.equals(owner)) {
      roll = new Die().rollDie();
      if (p.getUtilitiesCount() == 2) {
        amountOwed = 10 * roll;
      } else {
        amountOwed = 4 * roll;
      }
      if (amountOwed > p.getNetWorth()) {
        p.setAlive(false);
        p.payRemaining(owner);
      } else {
        p.editWealth(-1 * amountOwed);
        owner.editWealth(amountOwed);
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
        this.getPopUpStrings().add(
            messages.get(0) + " " + roll + messages.get(1) + this.getOwner().getMyName() + " $"
                + amountOwed);
        this.getPopUpStrings().add("OK");
      } else {
        this.getPopUpStrings().clear();
        this.getPopUpStrings().add(messages.get(2) + this.getName() + messages.get(3));
        this.getPopUpStrings().add("OK");
      }
    } else if (this.getStatus() == FOR_SALE) {
      this.getPopUpStrings()
          .add(messages.get(4) + this.getName() + messages.get(5) + this.getPrice() + "?");
      this.getPopUpStrings().add("Yes");
      this.getPopUpStrings().add("No");
    } else {
      this.getPopUpStrings().clear();
      this.getPopUpStrings().add(this.getName() + messages.get(6));
      this.getPopUpStrings().add("OK");
    }
  }

  @Override
  public int getPrice() {
    return price;
  }

  public Player getOwner() {
    return owner;
  }

  public void setOwner(Player p) {
    this.owner = p;
  }

  public int getMortgage() {
    return mortgage;
  }
}
