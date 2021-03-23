package ooga.configuration;

import java.util.List;
import ooga.model.tiles.Tile;

  /**
  * The purpose of this interface is to provide a framework for the methods that are necessary for
  * each possible type of implementation of a class that provides tiles to other classes
  * @author wyattfocht 
  */
public interface TileReader {

  /**
   * This method is a getter method for a list that contains all the Tile(s) with properly
   * initialized color, name, and starting rent
   *
   * @return List<Tile> that contains every tile to be displayed for the current board
   */
  public List<Tile> getTiles();

}
