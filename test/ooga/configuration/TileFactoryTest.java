package ooga.configuration;

import javafx.scene.paint.Paint;
import ooga.model.tiles.GoTile;
import ooga.model.tiles.Property;
import ooga.model.tiles.Railroad;
import ooga.model.tiles.Tile;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TileFactoryTest {

  private static final List<String> DEFAULT_NAMES = List
      .of("Go", "Mediterranean Avenue", "Community Chest1", "Baltic Avenue", "Income Tax",
          "Reading Railroad",
          "Oriental Avenue", "Chance1", "Vermont Avenue", "Connecticut Avenue",
          "Jail", "St. Charles Avenue", "Electric Company", "States Avenue", "Virginia Avenue",
          "Pennsylvania Railroad", "St. James Avenue", "Community Chest2", "Tennessee Avenue",
          "New York Avenue",
          "Free Parking", "Kentucky Avenue", "Chance2", "Indiana Avenue", "Illinois Avenue",
          "B & O Railroad",
          "Atlantic Avenue", "Ventnor Avenue", "Water Works", "Marvin Gardens",
          "Go To Jail", "Pacific Avenue", "North Carolina Avenue", "Community Chest3",
          "Pennsylvania Avenue",
          "Short Line", "Chance3", "Park Place", "Luxury Tax", "Boardwalk");

  private TileFactory defaultTiles = new TileFactory("Standard");
  private TileFactory testTiles = new TileFactory("Test");
  private TileFactory zippedTiles = new TileFactory("Zipped");


  @Test
  void testZipReadGamePropertiesSize() {
    assertEquals(40, zippedTiles.getTiles().size());
  }
  @Test
  void testZipIndividualPropertyProperlyRead(){
    Tile individualProperty = zippedTiles.getTiles().get(39);
    assertEquals(individualProperty.getName(),"Boardwalk");
    assertEquals(individualProperty.getColor(), Paint.valueOf("MEDIUMBLUE"));
    assertEquals(individualProperty.getPrice(),400);
  }

  @Test
  void testZipGetPropertyNames(){
    for (int i = 0; i < zippedTiles.getTileNames().size();i++){
      assertEquals(DEFAULT_NAMES.get(i), zippedTiles.getTileNames().get(i));
    }
  }

  @Test
  void testZipFileLineWrongLength(){
    Tile fromWrongLengthLine = testTiles.getTiles().get(0);
    assertEquals(fromWrongLengthLine.getName(),"Property");
    assertEquals(fromWrongLengthLine.getColor(), Paint.valueOf("WHITE"));
    assertEquals(fromWrongLengthLine.getPrice(),-1);
  }

  @Test
  void testZipReflectionFirstTileIsGo(){
    assertTrue( zippedTiles.getTiles().get(0) instanceof GoTile);
  }

  @Test
  void testZipReflectionLastTileProperty(){
    assertTrue(zippedTiles.getTiles().get(39) instanceof Property);
  }

  @Test
  void testZipReflectionRailRoads(){
    assertTrue(zippedTiles.getTiles().get(5) instanceof Railroad);
    assertTrue(zippedTiles.getTiles().get(15) instanceof Railroad);
    assertTrue(zippedTiles.getTiles().get(25) instanceof Railroad);
    assertTrue(zippedTiles.getTiles().get(35) instanceof Railroad);
  }

  @Test
  void testZipReflectionBadLineErrorHandling(){
    assertTrue(testTiles.getTiles().get(0) instanceof Property);
  }

  @Test
  void testReadGamePropertiesSize() {
    assertEquals(40, defaultTiles.getTiles().size());
  }
  @Test
  void testIndividualPropertyProperlyRead(){
    Tile individualProperty = defaultTiles.getTiles().get(39);
    assertEquals(individualProperty.getName(),"Boardwalk");
    assertEquals(individualProperty.getColor(), Paint.valueOf("MEDIUMBLUE"));
    assertEquals(individualProperty.getPrice(),400);
  }

  @Test
  void testGetPropertyNames(){
    for (int i = 0; i < defaultTiles.getTileNames().size();i++){
      assertEquals(DEFAULT_NAMES.get(i), defaultTiles.getTileNames().get(i));
    }
  }

  @Test
  void testFileLineWrongLength(){
    Tile fromWrongLengthLine = testTiles.getTiles().get(0);
    assertEquals(fromWrongLengthLine.getName(),"Property");
    assertEquals(fromWrongLengthLine.getColor(), Paint.valueOf("WHITE"));
    assertEquals(fromWrongLengthLine.getPrice(),-1);
  }

  @Test
  void testReflectionFirstTileIsGo(){
    assertTrue( defaultTiles.getTiles().get(0) instanceof GoTile);
  }

  @Test
  void testReflectionLastTileProperty(){
    assertTrue(defaultTiles.getTiles().get(39) instanceof Property);
  }

  @Test
  void testReflectionRailRoads(){
    assertTrue(defaultTiles.getTiles().get(5) instanceof Railroad);
    assertTrue(defaultTiles.getTiles().get(15) instanceof Railroad);
    assertTrue(defaultTiles.getTiles().get(25) instanceof Railroad);
    assertTrue(defaultTiles.getTiles().get(35) instanceof Railroad);
  }

  @Test
  void testReflectionBadLineErrorHandling(){
    assertTrue(testTiles.getTiles().get(0) instanceof Property);
  }



}