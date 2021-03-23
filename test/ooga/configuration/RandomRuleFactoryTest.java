package ooga.configuration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RandomRuleFactoryTest {
  private RandomRuleFactory randomRules = new RandomRuleFactory("");

  @Test
  void testStartingBalanceAlwaysGreaterThanLosing(){
    for (int i = 0; i < 20; i++){
      RandomRuleFactory test = new RandomRuleFactory("");
      assertTrue(test.getLosingBalance() < test.getStartingBalance());
    }
  }

  @Test
  void testHotelAlwaysGreaterThanHouse(){
    for (int i = 0; i < 20; i++){
      RandomRuleFactory test = new RandomRuleFactory("");
      assertTrue(test.getHouseIncrement() < test.getHotelIncrement());
    }
  }

  @Test
  void testRandomStartingBalanceInitialization() {
    assertNotEquals(-1, randomRules.getStartingBalance());
  }

  @Test
  void testRandomLosingBalanceInitialization() {
    assertNotEquals(-1, randomRules.getLosingBalance());
  }

  @Test
  void testRandomMoneyGainedPassingGoInitialization() {
    assertNotEquals(-1, randomRules.getMoneyGainedPassingGo());
  }


  @Test
  void testRandomJailSentenceInitialization() {
    assertNotEquals(-1, randomRules.getJailSentence());
  }

  @Test
  void testRandomHouseIncrementInitialization() {
    assertNotEquals(-1, randomRules.getHouseIncrement());
  }

  @Test
  void testRandomHotelIncrementInitialization() {
    assertNotEquals(-1, randomRules.getHotelIncrement());
  }

  @Test
  void testRandomIncomeTaxInitialization() {
    assertNotEquals(-1, randomRules.getIncomeTax());
  }

}