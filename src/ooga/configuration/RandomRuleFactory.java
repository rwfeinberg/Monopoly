package ooga.configuration;

/**
 * The purpose of this class is to randomly generate values (within certain restrictions) that are
 * to be used for the rules of the game
 * @author wyattfocht 
 */
public class RandomRuleFactory implements RuleReader {

  private int startingBalance;
  private int losingBalance;
  private int moneyFromGo;
  private int jailSentence;
  private double houseIncrement;
  private double hotelIncrement;
  private int incomeTax;
  private double initialRent;
  private double houseHotelCost;

  private static final int MAX_STARTING = 10000;
  private static final int MAX_GO = 1000;
  private static final int MAX_JAIL = 10;
  private static final int MAX_HOTEL = 1000;
  private static final int MAX_TAX = 1000;
  private static final int MAX_INITIAL_RENT = 1;
  private static final int MAX_HOUSE_HOTEL_MULTIPLIER = 1;

  public RandomRuleFactory(String unusedPath) {
    startingBalance = (int) makeRule(MAX_STARTING);
    losingBalance = (int) makeRule(startingBalance - 1);
    moneyFromGo = (int) makeRule(MAX_GO);
    jailSentence = (int) makeRule(MAX_JAIL);
    hotelIncrement = makeRule(MAX_HOTEL);
    houseIncrement = makeRule((int) (hotelIncrement - 1));
    incomeTax = (int) makeRule(MAX_TAX);
    initialRent = makeRule(MAX_INITIAL_RENT);
    houseHotelCost = makeRule(MAX_HOUSE_HOTEL_MULTIPLIER);
  }

  /**
   * @see RuleReader#getStartingBalance()
   */
  @Override
  public int getStartingBalance() {
    return startingBalance;
  }
  /**
   * @see RuleReader#getLosingBalance()
   */
  @Override
  public int getLosingBalance() {
    return losingBalance;
  }

  /**
   * @see RuleReader#getMoneyGainedPassingGo()
   */
  @Override
  public int getMoneyGainedPassingGo() {
    return moneyFromGo;
  }

  /**
   * @see RuleReader#getJailSentence()
   */
  @Override
  public int getJailSentence() {
    return jailSentence;
  }

/**
   * @see RuleReader#getHouseIncrement()
   */
  @Override
  public double getHouseIncrement() {
    return houseIncrement;
  }

/**
   * @see RuleReader#getHotelIncrement()
   */
  @Override
  public double getHotelIncrement() {
    return hotelIncrement;
  }

/**
   * @see RuleReader#getIncomeTax()
   */
  @Override
  public int getIncomeTax() {
    return incomeTax;
  }

  /**
   * @see RuleReader#getInitialRentMultiplier()
   */
  @Override
  public double getInitialRentMultiplier() {
    return initialRent;
  }

  /**
   * @see RuleReader#getHouseHotelCost()
   */
  @Override
  public double getHouseHotelCost() {
    return houseHotelCost;
  }

  private double makeRule(int upperBound) {
    double instanceVar = ((Math.random() * (upperBound)) + 0);
    return instanceVar;
  }
}
