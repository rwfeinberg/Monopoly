package ooga.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import ooga.configuration.CardFactory;
import ooga.model.tiles.CardTile;
import ooga.model.tiles.FreeParkingTile;
import ooga.model.tiles.GoTile;
import ooga.model.tiles.GoToJailTile;
import ooga.model.tiles.JailTile;
import ooga.model.tiles.Property;
import ooga.model.tiles.Railroad;
import ooga.model.tiles.TaxTile;
import ooga.model.tiles.Tile;
import ooga.model.tiles.UtilityTile;
import org.junit.jupiter.api.Test;

class TileTest {

  private static final Tile GO = new GoTile("Go", "WHITE");
  private static final Property PROPERTY = new Property("Sloane Square", "BLUE", 1000);
  private static final Tile PARKING = new FreeParkingTile("Free Parking", "WHITE");
  private static final Tile GOTOJAIL = new GoToJailTile("Go To Jail", "WHITE");
  private static final Tile JAIL = new JailTile("Jail", "WHITE");
  private static final Railroad RAILROAD = new Railroad("Railroad", "WHITE", 100);
  private static final Tile TAX = new TaxTile("Income Tax", "WHITE");
  private static final Tile TAXTWO = new TaxTile("Tax", "WHITE");
  private static final UtilityTile UTILITY = new UtilityTile("Utility", "WHITE", 100);
  private static final CardTile CARD = new CardTile("Card", "White", new DeckOfCards(0, "Extreme"));

  private static final List<String> RAILROAD_STRINGS = List
      .of("OK.", "Yes", "No");
  private static final List<String> UTILITY_STRINGS = List
      .of("Yes", "OK", "No",
          "Utility is mortgaged. No payment necessary.");
  private static final List<String> PROPERTY_STRINGS = List
      .of("Yes", "No", "OK", "Pay Wyatt $0", "Buy Sloane Square for $1000?",
          "You already own Sloane Square. No payment necessary!",
          "Sloane Square is mortgaged. No payment necessary.");

  private Board board = new Board("Standard");
  private Player testPlayer = new Player(board.getBoard().get(0), "Muazzam", "");
  private Player testPlayerTwo = new Player(board.getBoard().get(0), "Wyatt", "");

  //go
  @Test
  void testGoLandedUpon() {
    assertFalse(GO.landedUpon(testPlayer));
  }

  @Test
  void testGoSetPopUpString() {
    GO.setPopUpStrings(testPlayer);
    assertTrue(GO.getPopUpStrings().contains("OK"));
    assertTrue(GO.getPopUpStrings().contains("You hit Go! Collect $200"));
  }

  @Test
  void testGoPrice() {
    assertEquals(0, GO.getPrice());
  }

  //property

  private Property property2 = new Property("King's Road", "blue", 100);

  @Test
  void testPropertyLandedUpon() {
    PROPERTY.setStatus(0);
    assertTrue(PROPERTY.landedUpon(testPlayer));

    PROPERTY.setOwner(testPlayer);
    PROPERTY.setStatus(1);
    assertFalse(PROPERTY.landedUpon(testPlayer));

    PROPERTY.setOwner(testPlayerTwo);
    PROPERTY.setStatus(1);
    testPlayer.editWealth((-1 * testPlayer.getWealth()) - 1);
    testPlayer.updateNetWorth();
    PROPERTY.landedUpon(testPlayer);
    assertFalse(testPlayer.isAlive());
  }

  @Test
  void testPropertySetPopUpString() {

    List<String> popups = new ArrayList<>();

    PROPERTY.setOwner(testPlayer);
    PROPERTY.setStatus(0);
    PROPERTY.setPopUpStrings(testPlayer);
    for (String popup : PROPERTY.getPopUpStrings()) {
      popups.add(popup);
    }
    PROPERTY.setStatus(1);
    PROPERTY.setPopUpStrings(testPlayer);
    for (String popup : PROPERTY.getPopUpStrings()) {
      popups.add(popup);
    }

    PROPERTY.setStatus(-1);
    PROPERTY.setPopUpStrings(testPlayer);
    for (String popup : PROPERTY.getPopUpStrings()) {
      popups.add(popup);
    }

    PROPERTY.setOwner(testPlayerTwo);
    PROPERTY.setStatus(0);
    PROPERTY.setPopUpStrings(testPlayer);
    for (String popup : UTILITY.getPopUpStrings()) {
      popups.add(popup);
    }
    PROPERTY.setStatus(1);
    PROPERTY.setPopUpStrings(testPlayer);
    for (String popup : PROPERTY.getPopUpStrings()) {
      popups.add(popup);
    }
    assertTrue(checkListsSame(new ArrayList<>(new HashSet<>(PROPERTY_STRINGS)),
        new ArrayList<>(new HashSet<>(popups))));
  }

  @Test
  void testPropertyPrice() {
    assertEquals(1000, PROPERTY.getPrice());
  }

  @Test
  void testPropertyVariousGettersSetters() {
    int monopoly = 10;
    PROPERTY.setForMonopoly(monopoly);
    assertEquals(monopoly, PROPERTY.getForMonopoly());

    int hotel = 5;
    int house = 6;
    PROPERTY.editHotelCount(hotel);
    PROPERTY.editHouseCount(6);
    assertEquals(hotel, PROPERTY.getHotelCount());
    assertEquals(house, PROPERTY.getHouseCount());

    assertEquals(0, PROPERTY.getHouseHotelCost());
    assertEquals(500, PROPERTY.getMortgage());
  }


  //card
  @Test
  void testSetAllPlayers() {
    List<Player> allplayers = List.of(testPlayerTwo);
    CARD.setAllPlayers(allplayers);
    assertEquals(allplayers, CARD.getCurrentPlayersForTesting());
  }

  @Test
  void testCardLandedUpon() {
    CARD.setAllPlayers(List.of(testPlayerTwo, testPlayer));
    assertFalse(CARD.landedUpon(testPlayer));
    CARD.setPopUpStrings(testPlayer);

  }

  @Test
  void testCardSetPopUpString() {
    CARD.setPopUpStrings(testPlayer);
    assertTrue(CARD.getPopUpStrings().contains("OK"));
  }

  @Test
  void testCardPrice() {
    assertEquals(0, CARD.getPrice());
  }

  //free
  @Test
  void testParkingLandedUpon() {
    assertFalse(PARKING.landedUpon(testPlayer));
  }

  @Test
  void testParkingSetPopUpString() {
    PARKING.setPopUpStrings(testPlayer);
    assertTrue(PARKING.getPopUpStrings().contains("OK"));
    assertTrue(PARKING.getPopUpStrings().contains("You landed on free parking."));
  }

  @Test
  void testParkingPrice() {
    assertEquals(0, PARKING.getPrice());
  }

  //go to jail
  @Test
  void testGoToJailLandedUpon() {
    assertFalse(GOTOJAIL.landedUpon(testPlayer));
    assertEquals(-1, testPlayer.getSuspended());

  }

  @Test
  void testGoToJailSetPopUpString() {
    GOTOJAIL.setPopUpStrings(testPlayer);
    assertTrue(GOTOJAIL.getPopUpStrings().contains("OK"));
    assertTrue(GOTOJAIL.getPopUpStrings().contains("Go to Jail."));
  }

  @Test
  void testGoToJailPrice() {
    assertEquals(0, GOTOJAIL.getPrice());
  }

  //jail
  @Test
  void testJailLandedUpon() {
    assertFalse(JAIL.landedUpon(testPlayer));

  }

  @Test
  void testJailSetPopUpString() {
    JAIL.setPopUpStrings(testPlayer);
    assertTrue(JAIL.getPopUpStrings().contains("OK"));
    assertTrue(JAIL.getPopUpStrings().contains("You are just visiting jail."));
  }

  @Test
  void testJailPrice() {
    assertEquals(0, JAIL.getPrice());
  }

  //railroad
  @Test
  void testRailroadLandedUpon() {
    RAILROAD.setOwner(testPlayer);
    RAILROAD.setStatus(0);
    assertTrue(RAILROAD.landedUpon(testPlayer));

    RAILROAD.setStatus(1);
    assertFalse(RAILROAD.landedUpon(testPlayer));

  }

  @Test
  void testRailroadLandedUponDiffOwner() {
    for (int i = 1; i <= 4; i++) {
      testPlayer.editWealth(-1 * testPlayer.getWealth());
      testPlayer.updateNetWorth();
      RAILROAD.manuallySetRentForTesting(100);
      RAILROAD.setOwner(testPlayerTwo);
      testPlayerTwo.setRailroadCountForTesting(i);
      RAILROAD.setStatus(1);
      assertFalse(RAILROAD.landedUpon(testPlayer));

      assertFalse(testPlayer.isAlive());
    }
  }


  @Test
  void testRailroadSetPopUpString() {
    List<String> popups = new ArrayList<>();
    RAILROAD.manuallySetRentForTesting(1);

    RAILROAD.setOwner(testPlayer);

    RAILROAD.setStatus(0);
    RAILROAD.setPopUpStrings(testPlayer);
    for (String popup : RAILROAD.getPopUpStrings()) {
      popups.add(popup);
    }
    RAILROAD.setStatus(1);
    RAILROAD.setPopUpStrings(testPlayer);
    for (String popup : RAILROAD.getPopUpStrings()) {
      popups.add(popup);
    }

    RAILROAD.setOwner(testPlayerTwo);

    RAILROAD.setStatus(0);
    RAILROAD.setPopUpStrings(testPlayer);
    for (String popup : RAILROAD.getPopUpStrings()) {
      popups.add(popup);
    }
    RAILROAD.setStatus(1);
    RAILROAD.setPopUpStrings(testPlayer);
    for (String popup : RAILROAD.getPopUpStrings()) {
      popups.add(popup);
    }

    assertTrue(checkListsSame(new ArrayList<>(new HashSet<>(RAILROAD_STRINGS)),
        new ArrayList<>(new HashSet<>(popups))));

  }


  @Test
  void testRailroadPrice() {
    assertEquals(100, RAILROAD.getPrice());
  }
  //tax

  @Test
  void testTaxLandedUpon() {
    assertFalse(TAX.landedUpon(testPlayer));
    assertEquals(150, TAX.getPrice());

    assertFalse(TAXTWO.landedUpon(testPlayer));
    assertEquals(75, TAXTWO.getPrice());


  }

  @Test
  void testTaxSetPopUpString() {
    TAX.landedUpon(testPlayer);
    TAX.setPopUpStrings(testPlayer);
    List<String> fuck = TAX.getPopUpStrings();
    assertTrue(TAX.getPopUpStrings().contains("OK"));
    assertTrue(TAX.getPopUpStrings().contains("PayIncome Tax of $150."));
  }

  //utility
  @Test
  void testUtilitySetPopUpString() {
    List<String> popups = new ArrayList<>();

    UTILITY.setOwner(testPlayer);

    UTILITY.setStatus(0);
    UTILITY.setPopUpStrings(testPlayer);
    for (String popup : UTILITY.getPopUpStrings()) {
      popups.add(popup);
    }
    UTILITY.setStatus(1);
    UTILITY.setPopUpStrings(testPlayer);
    for (String popup : UTILITY.getPopUpStrings()) {
      popups.add(popup);
    }

    UTILITY.setStatus(-1);
    UTILITY.setPopUpStrings(testPlayer);
    for (String popup : UTILITY.getPopUpStrings()) {
      popups.add(popup);
    }

    UTILITY.setOwner(testPlayerTwo);

    UTILITY.setStatus(0);
    UTILITY.setPopUpStrings(testPlayer);
    for (String popup : UTILITY.getPopUpStrings()) {
      popups.add(popup);
    }

    assertTrue(checkListsSame(new ArrayList<>(new HashSet<>(UTILITY_STRINGS)),
        new ArrayList<>(new HashSet<>(popups))));
  }


  @Test
  void testUtilityLandedUpon() {
    testPlayer.editWealth((-1 * testPlayer.getWealth()) - 1);
    testPlayer.updateNetWorth();

    UTILITY.setStatus(1);
    UTILITY.setOwner(testPlayerTwo);
    assertFalse(UTILITY.landedUpon(testPlayer));

    assertFalse(testPlayer.isAlive());


  }


  private boolean checkListsSame(List<String> expected, List<String> actual) {
    boolean same = true;
    for (String ex : expected) {
      if (!actual.contains(ex)) {
        same = false;
        System.out.println(ex);
      }
    }
    for (String ac : expected) {
      if (!expected.contains(ac)) {
        same = false;
        System.out.println(ac);
      }
    }
    return same;
  }
}