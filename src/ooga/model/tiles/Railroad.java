package ooga.model.tiles;

import ooga.configuration.CSVLoader;
import ooga.configuration.RuleFactory;
import ooga.model.Player;

import java.util.List;

/**
 * This class creates a Railroad extension of Tile
 * @author Sylvie Mason
 */
public class Railroad extends Tile {

  private int rent;
  private int price;
  private Player owner;
  private int mortgage;
  private RuleFactory ruleFactory;
  private String gameType;
  private final List<String> messages;
  private static final int FOR_SALE = 0;
  private static final int OWNED = 1;
  private static final int MORTGAGED = 2;
  private static final int TWO_RAILROADS = 2;
  private static final int THREE_RAILROADS = 4;
  private static final int FOUR_RAILROADS = 8;

  /**
   * Constructor creates a railroad and sets the name, color, and price
   * @param tileName the name of the railroad
   * @param inputColor the color of the tile
   * @param initialPrice the starting price of the railroad
   */
  public Railroad(String tileName, String inputColor, int initialPrice) {
    super(tileName, inputColor, FOR_SALE);
    String language = (new CSVLoader("languageChoice", "")).getValues().get(0);
    this.messages = (new CSVLoader("railroadClassMessages", language)).getValues();
    price = initialPrice;
    mortgage = initialPrice / 2;
  }

  /**
   * initializes the rent based on the value retrieved from ruleFactory
   */
  public void initialize(){
    ruleFactory = new RuleFactory(gameType);
    rent = (int) (ruleFactory.getInitialRentMultiplier()*price);
    //rent = initialRent;
  }

  public void setGameType(String type){
    gameType = type;
    initialize();
  }

  /**
   * holds the actions required when a player lands on the railroad tile
   * @param p the player who lands on the tile
   * @return boolean whether further action is required
   */
  @Override
  public boolean landedUpon(Player p) {
    if (this.getStatus() == FOR_SALE) {
      return true;
    } else if (this.getStatus() == OWNED && !p.equals(owner)) {
      int owe = rent * getMultiplier(owner.getRailroadCount());
      if (owe > p.getNetWorth()) {
        p.setAlive(false);
        p.payRemaining(owner);
      } else {
        p.editWealth(rent * getMultiplier(owner.getRailroadCount()));
        owner.editWealth(rent * getMultiplier(owner.getRailroadCount()));
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
            .add(messages.get(0) + this.getOwner().getMyName() + " $" + this.getRent());
        this.getPopUpStrings().add("OK.");
      } else {
        this.getPopUpStrings().clear();
        this.getPopUpStrings().add(messages.get(1) + this.getName() + messages.get(2));
        this.getPopUpStrings().add("OK");
      }
    } else if (this.getStatus() == FOR_SALE) {
      this.getPopUpStrings()
          .add(messages.get(3) + this.getName() + messages.get(4) + this.getPrice() + "?");
      this.getPopUpStrings().add("Yes");
      this.getPopUpStrings().add("No");
    } else {
      this.getPopUpStrings().clear();

      this.getPopUpStrings().add(this.getName() + messages.get(5));
      this.getPopUpStrings().add("OK");

    }
  }

  private int getMultiplier(int railroadCount) {
    if (railroadCount == 1) {
      return 1;
    } else if (railroadCount == 2) {
      return TWO_RAILROADS;
    } else if (railroadCount == 3) {
      return THREE_RAILROADS;
    } else {
      return FOUR_RAILROADS;
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

  public int getRent() {
    return rent;
  }

  public int getMortgage() {
    return mortgage;
  }

  public void manuallySetRentForTesting(int num) {
    rent = num;
  }
}
