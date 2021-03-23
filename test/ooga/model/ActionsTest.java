package ooga.model;

import ooga.model.actions.*;
import ooga.model.tiles.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ActionsTest {

  private static final int STARTING_WEALTH = 1500;
  private static final String GAME_TYPE = "Standard";
  private static final String EMPTY = "";
  private static final String CHANCE_RESOURCES_PATH =
      GAME_TYPE.toLowerCase() + "/" + GAME_TYPE + "Chance" + "English";
  private static final String COMMUNITY_RESOURCES_PATH =
      GAME_TYPE.toLowerCase() + "/" + GAME_TYPE + "Community" + "English";
  private static final int FIRST_INT = 100;
  private static final int SECOND_INT = 200;
  private static final Board BOARD = new Board( GAME_TYPE);
  private List<Player> currentPlayers = List
      .of(new Player(BOARD.getBoard().get(0), "1", ""), new Player(BOARD.getBoard().get(0), "2", ""),
          new Player(BOARD.getBoard().get(0), "3", ""));


  private Player currentPlayer = currentPlayers.get(0);
  private int otherPlayersSize = currentPlayers.size() - 1;

  @Test
  void testAdvanceToRailroad() {
    Action action = new AdvanceToRailroad(FIRST_INT, SECOND_INT, currentPlayers, currentPlayer,
        CHANCE_RESOURCES_PATH);
    assertTrue(action.performAction());
    assertTrue(currentPlayer.getLocation() instanceof Railroad);
    assertNotEquals(EMPTY, action.toString());
  }

  @Test
  void testAdvanceToGo() {
    Action action = new AdvanceToGo(FIRST_INT, SECOND_INT, currentPlayers, currentPlayer,
        CHANCE_RESOURCES_PATH);
    currentPlayer.movePlayer(1);
    assertFalse(currentPlayer.getLocation() instanceof GoTile);
    assertFalse(action.performAction());
    assertTrue(currentPlayer.getLocation() instanceof GoTile);
    assertNotEquals(EMPTY, action.toString());
  }

  @Test
  void testAdvanceToProperty() {
    Action action = new AdvanceToProperty(FIRST_INT, SECOND_INT, currentPlayers, currentPlayer,
        CHANCE_RESOURCES_PATH);
    assertTrue(action.performAction());
    assertTrue(currentPlayer.getLocation() instanceof Property);
    assertNotEquals(EMPTY, action.toString());
  }

  @Test
  void testAdvanceToUtility() {
    Action action = new AdvanceToUtility(FIRST_INT, SECOND_INT, currentPlayers, currentPlayer,
        CHANCE_RESOURCES_PATH);
    assertTrue(action.performAction());
    assertTrue(currentPlayer.getLocation() instanceof UtilityTile);
    assertNotEquals(EMPTY, action.toString());
  }

  @Test
  void testGoToJail() {
    Action action = new GoToJail(FIRST_INT, SECOND_INT, currentPlayers, currentPlayer,
        COMMUNITY_RESOURCES_PATH);
    assertFalse(action.performAction());
    assertTrue(currentPlayer.getLocation() instanceof JailTile);
    assertNotEquals(EMPTY, action.toString());
  }

  @Test
  void testGetOutOfJail() {
    Action action = new GetOutOfJail(FIRST_INT, SECOND_INT, currentPlayers, currentPlayer,
        COMMUNITY_RESOURCES_PATH);
    assertFalse(action.performAction());
    //TODO: Add more checks
    assertNotEquals(EMPTY, action.toString());
  }

  @Test
  void testMoveBackwards() {
    Action action = new MoveBackwards(FIRST_INT, SECOND_INT, currentPlayers, currentPlayer,
        CHANCE_RESOURCES_PATH);
    assertTrue(action.performAction());
    assertEquals("Park Place",currentPlayer.getLocation().getName());
    assertNotEquals(EMPTY, action.toString());

  }

  @Test
  void testStreetRepair() {
    currentPlayer.setHotelCountForTesting(2);
    currentPlayer.setHouseCountForTesting(1);
    Action action = new StreetRepair(FIRST_INT, SECOND_INT, currentPlayers, currentPlayer,
        COMMUNITY_RESOURCES_PATH);
    assertFalse(action.performAction());
    assertEquals(
        (STARTING_WEALTH - FIRST_INT * currentPlayer.getHouseCount() - SECOND_INT * currentPlayer
            .getHotelCount()), currentPlayer.getWealth());
    assertNotEquals(EMPTY, action.toString());
  }

  @Test
  void testRepairsOnProperties() {
    currentPlayer.setHotelCountForTesting(2);
    currentPlayer.setHouseCountForTesting(1);
    Action action = new StreetRepair(FIRST_INT, SECOND_INT, currentPlayers, currentPlayer,
        CHANCE_RESOURCES_PATH);
    assertFalse(action.performAction());
    assertEquals(
        (STARTING_WEALTH - FIRST_INT * currentPlayer.getHouseCount() - SECOND_INT * currentPlayer
            .getHotelCount()), currentPlayer.getWealth());
    assertNotEquals(EMPTY, action.toString());
  }


  @Test
  void testBankDividend() {
    Action action = new BankDividend(FIRST_INT, SECOND_INT, currentPlayers, currentPlayer,
        CHANCE_RESOURCES_PATH);
    assertFalse(action.performAction());
    assertEquals(STARTING_WEALTH + FIRST_INT, currentPlayer.getWealth());
    assertNotEquals(EMPTY, action.toString());
  }

  @Test
  void testBeautyContest() {
    Action action = new BeautyContest(FIRST_INT, SECOND_INT, currentPlayers, currentPlayer,
        COMMUNITY_RESOURCES_PATH);
    assertFalse(action.performAction());
    assertEquals(STARTING_WEALTH + FIRST_INT, currentPlayer.getWealth());
    assertNotEquals(EMPTY, action.toString());
  }

  @Test
  void testBirthdayPresent() {
    Action action = new BirthdayPresent(FIRST_INT, SECOND_INT, currentPlayers, currentPlayer,
        COMMUNITY_RESOURCES_PATH);
    assertFalse(action.performAction());
    assertEquals(STARTING_WEALTH + FIRST_INT, currentPlayer.getWealth());
    assertNotEquals(EMPTY, action.toString());
  }

  @Test
  void testConsultancyFee() {
    Action action = new ConsultancyFee(FIRST_INT, SECOND_INT, currentPlayers, currentPlayer,
        COMMUNITY_RESOURCES_PATH);
    assertFalse(action.performAction());
    assertEquals(STARTING_WEALTH + FIRST_INT, currentPlayer.getWealth());
    assertNotEquals(EMPTY, action.toString());
  }

  @Test
  void testCrossWordCompetition() {
    Action action = new CrossWordCompetition(FIRST_INT, SECOND_INT, currentPlayers, currentPlayer,
        CHANCE_RESOURCES_PATH);
    assertFalse(action.performAction());
    assertEquals(STARTING_WEALTH + FIRST_INT, currentPlayer.getWealth());
    assertNotEquals(EMPTY, action.toString());
  }

  @Test
  void testFundMatures() {
    Action action = new FundMatures(FIRST_INT, SECOND_INT, currentPlayers, currentPlayer,
        COMMUNITY_RESOURCES_PATH);
    assertFalse(action.performAction());
    assertEquals(STARTING_WEALTH + FIRST_INT, currentPlayer.getWealth());
    assertNotEquals(EMPTY, action.toString());
  }

  @Test
  void testInheritMoney() {
    Action action = new InheritMoney(FIRST_INT, SECOND_INT, currentPlayers, currentPlayer,
        COMMUNITY_RESOURCES_PATH);
    assertFalse(action.performAction());
    assertEquals(STARTING_WEALTH + FIRST_INT, currentPlayer.getWealth());
    assertNotEquals(EMPTY, action.toString());
  }

  @Test
  void testInsuranceMatures() {
    Action action = new InsuranceMatures(FIRST_INT, SECOND_INT, currentPlayers, currentPlayer,
        COMMUNITY_RESOURCES_PATH);
    assertFalse(action.performAction());
    assertEquals(STARTING_WEALTH + FIRST_INT, currentPlayer.getWealth());
    assertNotEquals(EMPTY, action.toString());
  }

  @Test
  void testLoanMatures() {
    Action action = new LoanMatures(FIRST_INT, SECOND_INT, currentPlayers, currentPlayer,
        CHANCE_RESOURCES_PATH);
    assertFalse(action.performAction());
    assertEquals(STARTING_WEALTH + FIRST_INT, currentPlayer.getWealth());
    assertNotEquals(EMPTY, action.toString());
  }

  @Test
  void testSellStock() {
    Action action = new SellStock(FIRST_INT, SECOND_INT, currentPlayers, currentPlayer,
        COMMUNITY_RESOURCES_PATH);
    assertFalse(action.performAction());
    assertEquals(STARTING_WEALTH + FIRST_INT, currentPlayer.getWealth());
    assertNotEquals(EMPTY, action.toString());
  }

  @Test
  void testBankError() {
    Action action = new BankError(FIRST_INT, SECOND_INT, currentPlayers, currentPlayer,
        COMMUNITY_RESOURCES_PATH);
    assertFalse(action.performAction());
    assertEquals(STARTING_WEALTH + FIRST_INT, currentPlayer.getWealth());
    assertNotEquals(EMPTY, action.toString());
  }

  @Test
  void testTaxRefund() {
    Action action = new TaxRefund(FIRST_INT, SECOND_INT, currentPlayers, currentPlayer,
        COMMUNITY_RESOURCES_PATH);
    assertFalse(action.performAction());
    assertEquals(STARTING_WEALTH + FIRST_INT, currentPlayer.getWealth());
    assertNotEquals(EMPTY, action.toString());
  }

  @Test
  void testChairManOfBoard() {
    Action action = new ChairManOfBoard(FIRST_INT, SECOND_INT, currentPlayers, currentPlayer,
        CHANCE_RESOURCES_PATH);
    assertFalse(action.performAction());
    assertEquals(STARTING_WEALTH - FIRST_INT * otherPlayersSize, currentPlayer.getWealth());
    for (Player other : currentPlayers) {
      if (other != currentPlayer) {
        assertEquals(STARTING_WEALTH + FIRST_INT, other.getWealth());
      }
    }
    assertNotEquals(EMPTY, action.toString());
  }

  @Test
  void testDoctorsFee() {
    Action action = new DoctorFee(FIRST_INT, SECOND_INT, currentPlayers, currentPlayer,
        COMMUNITY_RESOURCES_PATH);
    assertFalse(action.performAction());
    assertEquals(STARTING_WEALTH - FIRST_INT, currentPlayer.getWealth());
    assertNotEquals(EMPTY, action.toString());
  }

  @Test
  void testHospitalFee() {
    Action action = new HospitalFee(FIRST_INT, SECOND_INT, currentPlayers, currentPlayer,
        COMMUNITY_RESOURCES_PATH);
    assertFalse(action.performAction());
    assertEquals(STARTING_WEALTH - FIRST_INT, currentPlayer.getWealth());
    assertNotEquals(EMPTY, action.toString());
  }

  @Test
  void testPoorTax() {
    Action action = new PoorTax(FIRST_INT, SECOND_INT, currentPlayers, currentPlayer,
        COMMUNITY_RESOURCES_PATH);
    assertFalse(action.performAction());
    assertEquals(STARTING_WEALTH - FIRST_INT, currentPlayer.getWealth());
    assertNotEquals(EMPTY, action.toString());
  }

  @Test
  void testSchoolFee() {
    Action action = new SchoolFee(FIRST_INT, SECOND_INT, currentPlayers, currentPlayer,
        COMMUNITY_RESOURCES_PATH);
    assertFalse(action.performAction());
    assertEquals(STARTING_WEALTH - FIRST_INT, currentPlayer.getWealth());
    assertNotEquals(EMPTY, action.toString());
  }


  @Test
  void testGrandOperaNight() {
    Action action = new GrandOperaNight(FIRST_INT, SECOND_INT, currentPlayers, currentPlayer,
        COMMUNITY_RESOURCES_PATH);
    assertFalse(action.performAction());
    assertEquals(STARTING_WEALTH + FIRST_INT * otherPlayersSize, currentPlayer.getWealth());
    for (Player other : currentPlayers) {
      if (other != currentPlayer) {
        assertEquals(STARTING_WEALTH - FIRST_INT, other.getWealth());
      }
    }
    assertNotEquals(EMPTY, action.toString());
  }

  @Test
  void testIncreaseWealthBySetConstant() {
    int constant = 10;
    Action action = new IncreaseWealthBySetConstant(FIRST_INT, SECOND_INT, currentPlayers, currentPlayer,
        COMMUNITY_RESOURCES_PATH);
    assertFalse(action.performAction());
    assertEquals(STARTING_WEALTH + constant, currentPlayer.getWealth());
    assertEquals(EMPTY, action.toString());
  }

  @Test
  void testDecreaseWealthBySetConstant() {
    int constant = 10;
    Action action = new DecreaseWealthBySetConstant(FIRST_INT, SECOND_INT, currentPlayers, currentPlayer,
        COMMUNITY_RESOURCES_PATH);
    assertFalse(action.performAction());
    assertEquals(STARTING_WEALTH - constant, currentPlayer.getWealth());
    assertEquals(EMPTY, action.toString());
  }

  @Test
  void testMoveForwardSingleTile() {
    Action action = new MoveForwardSingleTile(FIRST_INT, SECOND_INT, currentPlayers, currentPlayer,
        COMMUNITY_RESOURCES_PATH);
    Tile previousTile = currentPlayer.getLocation();
    assertFalse(action.performAction());
    assertNotEquals(currentPlayer.getLocation(), previousTile);
    assertEquals(EMPTY, action.toString());
  }


}
