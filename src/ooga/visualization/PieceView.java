package ooga.visualization;

import javafx.scene.image.ImageView;
import ooga.Main;
import ooga.model.Player;
/**
 * @author Catherine Livingston
 * @author Ryan Feinberg
 * <p>
 * Purpose - The PieceView.java class is used to create the visual of the piece that is hopping
 * around the board.
 * <p>
 * Assumptions - Assumes that the pieces are labeled piece#.jpeg.
 * <p>
 * Dependencies - This class depends on Player, VisualBoard, and VisualTile.
 */
public class PieceView extends ImageView {

  private final int SIZE = 30;
  private int placeOnBoard;
  private VisualBoard board;
  /**
   * Constructor, initializes view of the piece with params and adds to game board
   *
   * @param num - number of the piece chosen
   * @param gameBoard - the visual of the board being used
   */
  public PieceView(int num, VisualBoard gameBoard) {
    this.setFitHeight(SIZE);
    this.setFitWidth(SIZE);
    this.placeOnBoard = 0;
    this.board = gameBoard;
    this.setLayoutX(
        Main.TILE_SIZE * ((gameBoard.getVisualTiles().size() / 4)) + this.getFitWidth());
    this.setLayoutY(
        Main.TILE_SIZE * ((gameBoard.getVisualTiles().size() / 4)) + this.getFitHeight());
    this.setImage(ImageReader.getPieceImage(String.valueOf(num)));
    this.setId("piece" + num);
  }
  /**
   * Constructor, initializes view of the piece with params
   *
   * @param num - number of the piece chosen
   */
  public PieceView(int num) {
    this.setFitHeight(SIZE);
    this.setFitWidth(SIZE);
    this.placeOnBoard = 0;
    this.setImage(ImageReader.getPieceImage(String.valueOf(num)));
    this.setId("piece" + num);
  }

  /**
   * adds piece to the game board when needed
   *
   * @param gameBoard - the visual of the board being used
   */
  public void setUpBoard(VisualBoard gameBoard) {
    this.board = gameBoard;
    this.setLayoutX(
        Main.TILE_SIZE * ((gameBoard.getVisualTiles().size() / 4)) + this.getFitWidth());
    this.setLayoutY(
        Main.TILE_SIZE * ((gameBoard.getVisualTiles().size() / 4)) + this.getFitHeight());
  }

  /**
   * moves the player's piece by getting the visual tile attached to the player
   *
   * @param currentPlayer - the visual of the board being used
   */
  public void setPosition(Player currentPlayer) {
    if (currentPlayer.getLocation().getName().equals("Huzzah!")) {
      System.out.println(currentPlayer.getLocation().getVisualTile().getName());
    }
    VisualTile location = currentPlayer.getLocation().getVisualTile();
    this.setLayoutX(location.getLayoutX());
    this.setLayoutY(location.getLayoutY());
  }

  /**
   * moves the player's piece the name of the tile it needs to move to
   *
   * @param tileName - name of the tile that the player is on
   */
  public void setPosition(String tileName) {
    for (VisualTile currTile : board.getVisualTiles()) {
      if (currTile.getName().equals(tileName)) {
        this.setLayoutX(currTile.getLayoutX());
        this.setLayoutY(currTile.getLayoutY());
        this.placeOnBoard = board.getVisualTiles().indexOf(currTile);
        break;
      }
    }
  }
}
