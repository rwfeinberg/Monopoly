package ooga.configuration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class ButtonGameTypeFactoryTest {

  private static final List<String> STANDARD_MAPPING = List
      .of("BuyHouse","SellHouse","Trade","Mortgage","Unmortgage");
  private static final List<String> DUKE_MAPPING = List
      .of("Trade","Mortgage","Unmortgage");
  private static final List<String> BEACH_MAPPING = List
      .of("BuyHouse","SellHouse","Trade","Mortgage","Unmortgage");
  private static final List<String> EXTREME_MAPPING = List
      .of("Mortgage","Unmortgage","SwapPlayers");
  private static final String STANDARD = "Standard";
  private static final String DUKE = "Duke";
  private static final String BEACH = "Beach";
  private static final String EXTREME = "Extreme";
  private ButtonGameTypeFactory factory = new ButtonGameTypeFactory();

  @Test
  void testProperReadForStandard(){
    assertTrue(factory.getGameTypes().contains(STANDARD));
    assertTrue(checkListsSame(STANDARD_MAPPING,factory.getGameTypesToButtons().get(STANDARD)));
  }
  @Test
  void testProperReadForDuke(){
    assertTrue(factory.getGameTypes().contains(DUKE));
    assertTrue(checkListsSame(DUKE_MAPPING,factory.getGameTypesToButtons().get(DUKE)));
  }
  @Test
  void testProperReadForBeach(){
    assertTrue(factory.getGameTypes().contains(BEACH));
    assertTrue(checkListsSame(BEACH_MAPPING,factory.getGameTypesToButtons().get(BEACH)));
  }
  @Test
  void testProperReadForExtreme(){
    assertTrue(factory.getGameTypes().contains(EXTREME));
    assertTrue(checkListsSame(EXTREME_MAPPING,factory.getGameTypesToButtons().get(EXTREME)));
  }

  private boolean checkListsSame(List<String> expected, List<String> actual){
    boolean same = true;
    for (String ex: expected){
      if (!actual.contains(ex)) same = false;
    }
    for (String ac: expected){
      if (!expected.contains(ac)) same = false;
    }
    return same;
  }

}