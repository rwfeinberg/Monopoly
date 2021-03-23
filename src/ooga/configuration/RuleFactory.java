package ooga.configuration;


import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * The purpose of this class is to provide rules for a given game type that are read in from a 
 * corresponding file
 * @author wyattfocht 
 */
public class RuleFactory implements RuleReader {

  private static final String DEFAULT_BUNDLE = "standard/StandardGameRules";
  private static final int DEFAULT_RULE_VALUE = 0;
  private static final String GAME_RULES = "GameRules";

  private ResourceBundle ruleResources;

  public RuleFactory(String gameType) {
    try {
      this.ruleResources = ResourceBundle
          .getBundle(gameType.toLowerCase() + "/" + gameType + GAME_RULES);
    } catch (MissingResourceException e) {
      this.ruleResources = ResourceBundle.getBundle(DEFAULT_BUNDLE);
    }
  }

  /**
   * @see RuleReader#getStartingBalance()
   */
  @Override
  public int getStartingBalance() {
    return getRule("StartingBalance");
  }

  /**
   * @see RuleReader#getLosingBalance()
   */
  @Override
  public int getLosingBalance() {
    return getRule("LosingBalance");
  }

  /**
   * @see RuleReader#getMoneyGainedPassingGo()
   */
  @Override
  public int getMoneyGainedPassingGo() {
    return getRule("MoneyFromGo");
  }

  /**
   * @see RuleReader#getJailSentence()
   */
  @Override
  public int getJailSentence() {
    return getRule("JailSentence");
  }

  /**
   * @see RuleReader#getHouseIncrement()
   */
  @Override
  public double getHouseIncrement() {
    return getDoubleRule("HouseIncrement");
  }

  /**
   * @see RuleReader#getHotelIncrement()
   */
  @Override
  public double getHotelIncrement() {
    return getDoubleRule("HotelIncrement");
  }

  /**
   * @see RuleReader#getIncomeTax()
   */
  @Override
  public int getIncomeTax() {
    return getRule("IncomeTax");
  }

  /**
   * @see RuleReader#getInitialRentMultiplier()
   */
  @Override
  public double getInitialRentMultiplier() {
    return getDoubleRule("InitialRent");
  }

  /**
   * @see RuleReader#getHouseHotelCost()
   */
  @Override
  public double getHouseHotelCost() {
    return getDoubleRule("HouseHotelCost");
  }

  private int getRule(String rule) {
    try {
      return Integer.parseInt((ruleResources.getString(rule)));
    } catch (Exception e) {
      return DEFAULT_RULE_VALUE;
    }

  }

  private double getDoubleRule(String rule) {
    try {
      return Double.parseDouble((ruleResources.getString(rule)));
    } catch (Exception e) {
      return DEFAULT_RULE_VALUE;
    }

  }
}
