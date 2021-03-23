package ooga.configuration;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import ooga.exceptions.FilePathException;


public class RuleFactory implements RuleReader {

  private final int DEFAULT_RULE_VALUE = 0;
  private final String DATA_DIRECTORY = "./data/";

  private ResourceBundle ruleResources;

  public RuleFactory(String pathToRules) {
    this.ruleResources = ResourceBundle.getBundle(pathToRules);
  }

  @Override
  public int getStartingBalance() {
    return getRule("StartingBalance");
  }

  @Override
  public int getLosingBalance() {
    return getRule("LosingBalance");
  }

  @Override
  public int getMoneyGainedPassingGo() {
    return getRule("MoneyFromGo");
  }

  @Override
  public int getJailSentence() {
    return getRule("JailSentence");
  }

  @Override
  public int getHouseIncrement() {
    return getRule("HouseIncrement");
  }

  @Override
  public int getHotelIncrement() {
    return getRule("HotelIncrement");
  }

  @Override
  public int getIncomeTax() {
    return getRule("IncomeTax");
  }

  private int getRule(String rule){
    try {
      return Integer.parseInt((ruleResources.getString(rule)));
    }
    catch(Exception e){ //make custom exception later
      return DEFAULT_RULE_VALUE;
    }

  }
}
