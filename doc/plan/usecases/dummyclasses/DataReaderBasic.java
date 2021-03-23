package plan.usecases.dummyclasses;

import java.util.List;
import plan.api.configuration.DataReader;

/**
 * The purpose of this class is to facilitate as a dummy class that is used as a placeholder
 * in ControllerRuleInitializationExample and ControllerTileInitializationExample
 */
public class DataReaderBasic implements DataReader {

  @Override
  public void readGameRules() {

  }

  @Override
  public int getStartingBalance() {
    return 0;
  }

  @Override
  public int getLosingBalance() {
    return 0;
  }

  @Override
  public int getMoneyGainedPassingGo() {
    return 0;
  }

  @Override
  public int getJailSentence() {
    return 0;
  }

  @Override
  public List<String> getTileNames() {
    return null;
  }

  @Override
  public int getHouseMultiplier() {
    return 0;
  }

  @Override
  public int getHotelMultiplier() {
    return 0;
  }
}
