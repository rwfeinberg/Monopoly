package ooga.configuration;



/**
 * This purpose of this interface is to facilitate all appropriate logic when it comes to reading
 * information/data values from various files.  This additionally involves cleaning, processing, and
 * reformating data as necessary
 * @author wyattfocht
 */
public interface RuleReader {

  /**
   * This method is a getter method for an int that represents the amount of money that a player
   * should have at the beginning of a game.
   *
   * @return int representing the starting balance of a player
   */
  public int getStartingBalance();

  /**
   * This method is a getter method for an int that represents the amount of money that would
   * warrant a player to lose the game.
   *
   * @return int representing the losing balance of a player
   */
  public int getLosingBalance();

  /**
   * This method is a getter method for an int that represents the amount of money gained from a
   * player passing the Go tile
   *
   * @return int representing the amount of money earned from passing the Go tile
   */
  public int getMoneyGainedPassingGo();

  /**
   * This method is a getter method for an int that represents the number of turns an individual
   * must spend in jail
   *
   * @return int representing the amount of time a player must spend in jail
   */
  public int getJailSentence();

  /**
   * This method is a getter method for an int representing the increase in value of a property with
   * the addition of a new house.
   *
   * @return int representing the amount increase in a property's value with the addition of a new
   * house
   */
  public double getHouseIncrement();

  /**
   * This method is a getter method for an int representing the increase in value of a property with
   * the addition of a new hotel.
   *
   * @return int representing the amount increase in a property's value with the addition of a new
   * hotel
   */
  public double getHotelIncrement();

  /**
   * This method is a getter method for an int representing the income tax an individual must pay
   * upon landing on an income tax tile
   *
   * @return int representing the amount due for income tax hotel
   */
  public int getIncomeTax();

  /**
   * This method is a getter method for a double representing the amount by which a property's price
   * will be multiplied by in order to determine its initial rent
   *
   * @return double representing the aforementioned multiplier
   */
  public double getInitialRentMultiplier();

  /**
   * This method is a getter method for a double representing the amount by which a property's price
   * will be multiplied by in order to determine its price for a hotel or house
   *
   * @return double representing the aforementioned multiplier
   */
  public double getHouseHotelCost();
}
