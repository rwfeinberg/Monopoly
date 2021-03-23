package plan.usecases;

import java.util.LinkedList;
import plan.usecases.dummyclasses.DataReaderBasic;
import plan.usecases.dummyclasses.TileBasic;

public class ControllerTileInitializationExample {
/**
 * This use case describes the initialization of all the tiles of the game, in the very beginning
 * of a game's creation.  Each of the currently-defined tile is ascertained from the plan.api.configuration.DataReader
 * class and set to the desired parameter accordingly
 */

  private LinkedList<TileBasic> currentTiles;

  /**
   * This is the constructor of a feux-controller that is being initialized with all of the current
   * tiles on the gameboard.  These tilenames are being read from files handled in a plan.api.configuration.DataReader object.
   */
  public ControllerTileInitializationExample(){
    DataReaderBasic reader = new DataReaderBasic();
    initializeTiles(reader);
  }

  /**
   * This method initializes the instance variable of this feux-controller class with the relevant
   * data points that are read in the plan.api.configuration.DataReader reader.
   * @param reader plan.api.configuration.DataReader from which to get the current tile/location names of the gameboard
   */
  private void initializeTiles(DataReaderBasic reader){
    currentTiles = new LinkedList<TileBasic>();
    for (int i = 0; i < reader.getTileNames().size();i++){
      currentTiles.add(new TileBasic(reader.getTileNames().get(i)));
    }
  }
}
