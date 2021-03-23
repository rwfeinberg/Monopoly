package ooga.visualization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import ooga.Main;
import ooga.model.Board;
import ooga.model.tiles.Tile;

/**
 * @author Ryan Feinberg
 * <p>
 * Purpose - The VisualBoard.java class is responsible for creating the board display on the left side of the screen when playing a game.
 * <p>
 * Assumptions - This class extends GridPane.
 * <p>
 * Dependencies - This class depends on Main.java for constants, Board, and Tile.java.
 */
public class VisualBoard extends GridPane {

  private final int BOARD_SIZE;
  private final int BOARD_WIDTH;
  private final List<VisualTile> visualTiles = new ArrayList<>();

  /**
  * Constructor, initializes board
  *
  * @param gameBoard - Board.java object created by backend to build the board after
  */
  public VisualBoard(Board gameBoard) {
    List<Tile> tiles = gameBoard.getBoard();
    BOARD_WIDTH = (tiles.size() / 4) + 1;
    BOARD_SIZE = BOARD_WIDTH * Main.TILE_SIZE;

    this.setMinWidth(BOARD_SIZE);
    this.setMaxWidth(BOARD_SIZE);
    this.setMinHeight(BOARD_SIZE);
    this.setMaxHeight(BOARD_SIZE);
    this.setPadding(new Insets(20));

    createBoard(tiles);
  }

  private void createBoard(List<Tile> tiles) {
    int tileSize = BOARD_SIZE / BOARD_WIDTH;

    for (Tile tile : tiles) {
      VisualTile n = new VisualTile(tileSize, tile);
      visualTiles.add(n);
    }
    List<VisualTile> sortedVisualTiles = new ArrayList<>();
    sortList(sortedVisualTiles, visualTiles);

    Collection<Integer> edgeSpaces = new HashSet<>();
    fillEdgeIndices(edgeSpaces);

    fillGrid(tileSize, sortedVisualTiles, edgeSpaces);
  }

  private void fillGrid(int tileSize, List<VisualTile> sortedVisualTile,
      Collection<Integer> edgeSpaces) {
    int gridPaneIndex = 0;
    int edgeIndex = 0;

    for (int row = 0; row < BOARD_WIDTH; row++) {
      for (int col = 0; col < BOARD_WIDTH; col++) {
        Node n;
        if (edgeSpaces.contains(gridPaneIndex)) {
          n = sortedVisualTile.get(edgeIndex);
          edgeIndex++;
        } else {
          n = new BoardCenterTile(tileSize, col, row, sortedVisualTile.size());
        }
        this.add(n, col, row);
        gridPaneIndex++;
      }
    }
  }

  private void fillEdgeIndices(Collection<Integer> boardSpaces) {
    int boardSize = BOARD_WIDTH * BOARD_WIDTH;
    for (int i = 0; i < boardSize; i++) {
      if (i < BOARD_WIDTH || i > boardSize - BOARD_WIDTH - 1) {
        boardSpaces.add(i);
      } else if (i % BOARD_WIDTH == 0) {
        boardSpaces.add(i);
      } else if ((i - (BOARD_WIDTH - 1)) % BOARD_WIDTH == 0) {
        boardSpaces.add(i);
      }
    }
  }

  /**
   * Sort list in order: 20,21,22,23,24,25,26,27,28,29,30,19,31,18,32,17,33,16,34,15,35,14,26,13,37,12,38,11,39,10,9,8,7,6,5,4,3,2,1,0
   * built from original indices, this is needed so that the board is created in the correct order
   *
   * @param l        - new list to be made with order above
   * @param baseList - old list with indices in 0-39 order
   */
  private void sortList(List l, List baseList) {
    int firstCorner = baseList.size() / 4;
    int secondCorner = baseList.size() / 2;
    int thirdCorner = (3 * baseList.size()) / 4;

    for (int i = secondCorner; i < thirdCorner + 1; i++) {
      l.add(baseList.get(i));
    }

    int initialSeparationDistance = BOARD_WIDTH + 1;
    for (int i = secondCorner - 1; i > firstCorner; i--) {
      l.add(baseList.get(i));
      l.add(baseList.get(i + initialSeparationDistance));
      initialSeparationDistance += 2;
    }
    for (int i = firstCorner; i > -1; i--) {
      l.add(baseList.get(i));
    }
  }

  /**
   * Sort list in order: 30,31,32,33,34,35,36,37,38,39,29,28,27,26,25,24,23,22,21,20,19,18,10,11,12,13,14,15,16,17,9,8,7,6,5,4,3,2,1,0
   * built from original indices, this is needed so that the board is created in the correct order
   *
   * @param l        - new list to be made with order above
   * @param baseList - old list with indices in 0-39 order
   */
  private void sortListPath(List l, List baseList) {
    for (int i = 30; i < 40; i++) {
      l.add(baseList.get(i));
    }

    for (int i = 29; i > 17; i--) {
      l.add(baseList.get(i));
    }

    for (int i = 10; i < 18; i++) {
      l.add(baseList.get(i));
    }

    for (int i = 9; i > -1; i--) {
      l.add(baseList.get(i));
    }
  }

  public List<VisualTile> getVisualTiles() {
    return visualTiles;
  }
}
