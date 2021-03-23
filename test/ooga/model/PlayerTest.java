package ooga.model;
import ooga.model.tiles.Property;
import ooga.model.tiles.Railroad;
import ooga.model.tiles.Tile;
import ooga.model.tiles.UtilityTile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
  private Board board = new Board("Standard");
  private Player testPlayer = new Player(board.getBoard().get(0), "Muazzam", "");
  private Player testPlayer2 = new Player(board.getBoard().get(0), "Sylvie", "");


  @Test
  void testMovePlayer(){
    Tile expectedPosition = board.getBoard().get(1);
    testPlayer.movePlayer(1);
    assertSame(expectedPosition, testPlayer.getLocation());
  }

  @Test
  void testBuyProperty(){
    testPlayer.movePlayer(1);
    testPlayer.buyProperty();
    assertEquals(1, testPlayer.getOwnedTiles().size());
    assertEquals(testPlayer.getOwnedTiles().get(0), board.getBoard().get(1));
  }

  @Test
  void testMonopoly(){
    testPlayer.movePlayer(1);
    testPlayer.buyProperty();
    testPlayer.movePlayer(2);
    testPlayer.buyProperty();
    assertTrue(testPlayer.getOwnedTiles().size()>0);
    assertTrue(testPlayer.getMonopolies().size()>0);
  }

  @Test
  void testBuyRailRoad(){
    testPlayer.movePlayer(5);
    testPlayer.buyProperty();
    assertEquals(1, testPlayer.getOwnedTiles().size());
    assertEquals(1, testPlayer.getRailroadCount());
  }

  @Test
  void testBuyUtility(){
    testPlayer.movePlayer(12);
    testPlayer.buyProperty();
    assertEquals(1, testPlayer.getOwnedTiles().size());
    assertEquals(1, testPlayer.getUtilitiesCount());
  }

  @Test
  void testBuyHousesAndHotel(){
    testPlayer.movePlayer(1);
    testPlayer.buyProperty();
    testPlayer.movePlayer(2);
    testPlayer.buyProperty();
    testPlayer.buyHouse("Baltic Avenue");
    assertEquals(1, testPlayer.getHouseCount());
    testPlayer.buyHouse("Baltic Avenue");
    testPlayer.buyHouse("Baltic Avenue");
    testPlayer.buyHouse("Baltic Avenue");
    testPlayer.buyHotel("Baltic Avenue");
    assertEquals(0, testPlayer.getHouseCount());
    assertEquals(1, testPlayer.getHotelCount());
  }

  @Test
  void testMortgage(){
    testPlayer.movePlayer(1);
    testPlayer.buyProperty();
    int wealth = testPlayer.getWealth();
    testPlayer.mortgage("Mediterranean Avenue");
    int change = testPlayer.getWealth()-wealth;
    assertEquals(30,change);
  }

  @Test
  void testUnmortgage(){
    testPlayer.movePlayer(1);
    testPlayer.buyProperty();
    int wealth = testPlayer.getWealth();
    testPlayer.mortgage("Mediterranean Avenue");
    testPlayer.unmortgage("Mediterranean Avenue");
    int change = testPlayer.getWealth()-wealth;
    assertEquals(0,change);
  }

  @Test
  void testSellRealEstate(){
    testPlayer.movePlayer(1);
    testPlayer.buyProperty();
    testPlayer.movePlayer(2);
    testPlayer.buyProperty();
    testPlayer.buyHouse("Baltic Avenue");
    assertEquals(1, testPlayer.getHouseCount());
    testPlayer.sellRealEstate("Baltic Avenue");
    Property p = (Property)testPlayer.getOwnedTiles().get(0);
    assertEquals(0, p.getHouseCount());
  }

  @Test
  void testExecutePropertyTrade(){
    testPlayer.movePlayer(1);
    testPlayer.buyProperty();
    testPlayer2.movePlayer(3);
    testPlayer2.buyProperty();
    Property p = (Property)testPlayer2.getOwnedTiles().get(0);
    testPlayer.executeTrade(p, 0, 0);
    assertEquals(2, testPlayer.getOwnedTiles().size());
    assertEquals(1,testPlayer.getMonopolies().size());
  }

  @Test
  void testExecuteRailRoadTrade(){
    testPlayer.movePlayer(5);
    testPlayer.buyProperty();
    Railroad p = (Railroad) testPlayer.getOwnedTiles().get(0);
    testPlayer2.executeTrade(p, 0, 0);
    assertEquals(1, testPlayer2.getOwnedTiles().size());
    assertEquals(1,testPlayer2.getRailroadCount());
  }

  @Test
  void testExecuteUtilityTrade(){
    testPlayer.movePlayer(12);
    testPlayer.buyProperty();
    UtilityTile p = (UtilityTile) testPlayer.getOwnedTiles().get(0);
    testPlayer2.executeTrade(p, 0, 0);
    assertEquals(1, testPlayer2.getOwnedTiles().size());
    assertEquals(1,testPlayer2.getUtilitiesCount());
  }

}
