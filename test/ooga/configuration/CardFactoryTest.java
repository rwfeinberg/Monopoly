package ooga.configuration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class CardFactoryTest {

  private List<String> defaultChanceMethods = List.of("AdvanceToGo",
      "AdvanceToProperty",
      "AdvanceToUtility",
      "AdvanceToRailroad",
      "BankDividend",
      "GetOutOfJail",
      "MoveBackwards",
      "RepairsOnProperties",
      "PoorTax",
      "ChairManOfBoard",
      "LoanMatures",
      "CrossWordCompetition");
  private List<String> defaultCommunityMethods = List.of("AdvanceToGo",
      "BankError",
      "DoctorFee",
      "SellStock",
      "GetOutOfJail",
      "GoToJail",
      "GrandOperaNight",
      "FundMatures",
      "TaxRefund",
      "BirthdayPresent",
      "InsuranceMatures",
      "HospitalFee",
      "SchoolFee",
      "ConsultancyFee",
      "StreetRepair",
      "BeautyContest",
      "InheritMoney");

  private CardFactory defaultCards = new CardFactory("Standard", "English");
  private CardFactory testCards = new CardFactory("Test", "English");
  @Test
  void testBadLanguage() {
    CardFactory bad = new CardFactory("StandardTiles", "Gibberish");Map<String,int[]> a = testCards.getCommunityCardMethodsAndRelevantNumbers();
    assertEquals("English", bad.getLanguage());
  }

  @Test
  void testStandardChanceCards() {
    Map<String, int[]> chance = defaultCards.getChanceCardMethodsAndRelevantNumbers();
    assertEquals(12, chance.size());

    for (String method: defaultChanceMethods){
      assertTrue(chance.keySet().contains(method));
    }

    assertEquals(3,chance.get("MoveBackwards")[0]);
    assertEquals(-1,chance.get("MoveBackwards")[1]);
  }

  @Test
  void testStandardCommunityCards() {
    Map<String, int[]> community = defaultCards.getCommunityCardMethodsAndRelevantNumbers();
    assertEquals(17, community.size());

    for (String method: defaultCommunityMethods){
      assertTrue(community.keySet().contains(method));
    }

    assertEquals(40,community.get("StreetRepair")[0]);
    assertEquals(115,community.get("StreetRepair")[1]);
  }

  @Test
  void testErrorInParsingInt(){
    Map<String,int[]> chance = testCards.getChanceCardMethodsAndRelevantNumbers();
    assertEquals(-1,chance.get("Method")[1]);
  }

  @Test
  void testMissingCommunityFile(){
    Map<String,int[]> community = testCards.getCommunityCardMethodsAndRelevantNumbers();
    assertEquals(0,community.size());
  }

  @Test
  void testEnglishPreset(){
    CardFactory noLang = new CardFactory("Test");
    assertEquals("English",noLang.getLanguage());
  }

  @Test
  void testZippedProperReader(){
    CardFactory zipped = new CardFactory("Zipped");

    for (String method: defaultChanceMethods){
      assertTrue(zipped.getChanceCardMethodsAndRelevantNumbers().keySet().contains(method));
    }
  }

}