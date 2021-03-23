package ooga.model;

import ooga.configuration.TileFactory;
import ooga.model.tiles.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * Board class stores properties to create a board from a list of Tiles
 * @author Sylvie Mason
 */
public class Board {

  private TileFactory propertyFactory;
  private ArrayList<Tile> board = new ArrayList<>();

  /**
   * constructs board from a list of tiles obtained by RuleFactory
   * @param type of game being played
   */
  public Board(String gameType) {
    propertyFactory = new TileFactory(gameType);
    createBoard();
  }

  private void createBoard() {
    List<Tile> tiles = propertyFactory.getTiles();
    Tile first = tiles.get(0);
    board.add(first);
    Tile temp = first;
    for (int i = 1; i < tiles.size(); i++) {
      Tile tile = tiles.get(i);
      temp.setNextTile(tile);
      board.add(tile);
      temp = tile;
    }
    temp.setNextTile(first);
    board.add(temp);
  }

  public ArrayList<Tile> getBoard() {
    return board;
  }
}
