package ooga.configuration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RuleFactoryTest {
  private RuleFactory defaultRules = new RuleFactory("Standard");
  private RuleFactory testingRules = new RuleFactory("Test");
  @Test
  void testDefaultStartingBalance() {
    assertEquals(1500,defaultRules.getStartingBalance());
  }
  @Test
  void testTestingStartingBalance() {
    assertEquals(-1,testingRules.getStartingBalance());
  }

  @Test
  void testDefaultLosingBalance() {
    assertEquals(0, defaultRules.getLosingBalance());
  }
  @Test
  void testTestingLosingBalance() {
    assertEquals(500, testingRules.getLosingBalance());
  }
  @Test
  void testDefaultMoneyGainedPassingGo() {
    assertEquals(200, defaultRules.getMoneyGainedPassingGo());
  }
  @Test
  void testErrorInTestingMoneyGainedPassingGo() {
    assertEquals(0, testingRules.getMoneyGainedPassingGo());
  }

  @Test
  void testDefaultJailSentence() {
    assertEquals(3, defaultRules.getJailSentence());
  }
  @Test
  void testTestingJailSentence() {
    assertEquals(1000, testingRules.getJailSentence());
  }

  @Test
  void testDefaultHouseIncrement() {
    //subject to change
    assertEquals(.5, defaultRules.getHouseIncrement());
  }
  @Test
  void testTestingHouseIncrement() {
    //subject to change
    assertEquals(1, testingRules.getHouseIncrement());
  }

  @Test
  void testDefaultHotelIncrement() {
    //subject to change
    assertEquals(.6, defaultRules.getHotelIncrement());
  }
  @Test
  void testTestingHotelIncrement() {
    //subject to change
    assertEquals(2, testingRules.getHotelIncrement());
  }

  @Test
  void testBadGameType(){
    RuleFactory factory = new RuleFactory("This is a bad game type");
    assertEquals(.6,factory.getHotelIncrement());
    assertEquals(200, factory.getIncomeTax());
    assertEquals(.3, factory.getInitialRentMultiplier());
    //should use default rules instead
  }

  @Test
  void testDefaultIncomeTax() {
    assertEquals(200, defaultRules.getIncomeTax());
  }
  @Test
  void testTestingIncomeTax() {
    assertEquals(6, testingRules.getIncomeTax());
  }

  @Test
  void testDefaultHouseHotelCost(){
    assertEquals(.3, defaultRules.getHouseHotelCost());
  }

  @Test
  void testDefaultInitialCost(){
    assertEquals(.3, defaultRules.getInitialRentMultiplier());
  }
}