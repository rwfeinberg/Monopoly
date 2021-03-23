package ooga.visualization;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class BoardCenterTile extends Rectangle {

  public BoardCenterTile(int size, int columnNumber, int rowNumber, int numOfTiles) {
    super(size, size);
    if (isLogoBounds(columnNumber, rowNumber, numOfTiles)) {
      this.setFill(new ImagePattern(
          ImageReader.findTileImage("boardCenterR" + rowNumber + "C" + columnNumber)));
    } else {
      this.setFill(Color.PALEGREEN);
    }

  }

  public boolean isLogoBounds(int col, int row, int numOfTiles) {
    if (numOfTiles == 40) {
      return col >= 4 && col <= 7 && row == 5;
    } else {
      return false;
    }
  }
}
