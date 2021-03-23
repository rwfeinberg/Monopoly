package plan.usecases;

import plan.usecases.dummyclasses.DataReaderBasic;

public class ControllerRuleInitializationExample {
  /**
   * This use case describes the initialization of all the rules of the game, in the very beginning
   * of a game's creation.  Each of the currently-defined rules is ascertained from the plan.api.configuration.DataReader
   * class and set to the desired parameter accordingly
   */

    private int startingBalance;
    private int losingBalance;
    private int jailSentence;
    private int houseMultiplier;
    private int hotelMultiplier;
    private int passingGoPayout;

  /**
   * This is the constructor of a feux-controller that is being initialized with all of the current
   * rules of the game.  These rules are being read from files handled in a plan.api.configuration.DataReader object.
   */
    public ControllerRuleInitializationExample(){
      DataReaderBasic reader = new DataReaderBasic();
      initializeRules(reader);
    }

  /**
   * This method initializes the instance variables of this feux-controller class with the relevant
   * data points that are read in the plan.api.configuration.DataReader reader.
   * @param reader plan.api.configuration.DataReader from which to get the current rules of the game
   */
    private void initializeRules(DataReaderBasic reader){
      startingBalance = reader.getStartingBalance();
      losingBalance = reader.getLosingBalance();
      passingGoPayout = reader.getMoneyGainedPassingGo();
      jailSentence = reader.getJailSentence();
      houseMultiplier = reader.getHouseMultiplier();
      hotelMultiplier = reader.getHotelMultiplier();
    }

}
