package ooga.visualization;

import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import ooga.model.tiles.GoTile;
import ooga.model.tiles.Property;
import ooga.model.tiles.Tile;

/**
 * @author Ryan Feinberg
 * @author Catherine Livingston
 * <p>
 * Purpose - The VisualTile.java class is used to represent the visual display for each tile in the VisualBoard.
 * <p>
 * Assumptions - This class extends BorderPane. The NON_STANDARD_PROPERTIES list should contain the names of any board space you would like a custom 
 * image to appear on. In order for an error not to be thrown, a .jpeg must exist in the images directory of resources corresponding to the name in 
 * this list.
 * <p>
 * Dependencies - This class depends on GoTile, Tile, and Property.java.
 */

public class VisualTile extends BorderPane {

  private final List<String> NON_STANDARD_PROPERTIES = List
      .of("Chest", "Chance", "Go", "Jail", "Parking", "Railroad", "Short Line", "Electric", "Water",
          "Income", "Luxury", "Matriculation", "C1", "C3", "CSW", "PR1", "Tuition", "Probation",
          "Steam", "Busted", "Financial Aid", "Legacy");

  private boolean isNormalProperty = true;
  private String tileName;

   /**
  * Constructor, initializes tile.
  *
  * @param tileSize - Size of tile desired in pixels
  * @param tile - Tile.java instance that the created VisualTile will correspond to.
  */
  public VisualTile(int tileSize, Tile tile) {
    String name = tile.getName();
    Paint color = tile.getColor();
    int rent = tile.getPrice();
    this.tileName = tile.getName();

    tile.setVisualTile(this);

//    if (!(tile instanceof Property) && !(tile instanceof GoTile)) {
    if (!(tile instanceof Property)) {
      this.isNormalProperty = false;
    }

    this.setMinSize(tileSize, tileSize);
    this.setMaxSize(tileSize, tileSize);
    this.setBackground(
        new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, new Insets(0))));

    Rectangle colorBanner = makeColorBar(tileSize, color);
    StackPane center = makeCenter(tileSize, name);
    StackPane bottom = makeBottom(tileSize, rent);

    if (this.isNormalProperty) {
      this.setTop(colorBanner);
      this.setCenter(center);
      this.setBottom(bottom);
    }

    if (!this.isNormalProperty) {
      this.setImageBackground("tile" + name.replace(" ", "").replace("\n", ""));
    }

    String nameID = name.replace(" ", "").replace("\n", "");
    this.setId(nameID);
  }

  private void setImageBackground(String name) {
    BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false,
        false, true, false);
    this.setBackground(new Background(
        new BackgroundImage(ImageReader.findTileImage(name), BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bSize)));
  }

  private StackPane makeBottom(int tileSize, Integer rent) {
    StackPane bottom = new StackPane();
    Rectangle bottomBackground = new Rectangle();
    bottomBackground.setTranslateY(-1);
    bottomBackground.setFill(Color.PALEGREEN);
    bottomBackground.setWidth(tileSize - 2);
    bottomBackground.setHeight(15);
    Label propertyPrice = new Label("$" + rent);
    if (!this.isNormalProperty) {
      propertyPrice.setText("");
    }
    bottom.getChildren().addAll(bottomBackground, propertyPrice);
    return bottom;
  }

  private StackPane makeCenter(int tileSize, String name) {
    StackPane center = new StackPane();
    Rectangle centerBackground = new Rectangle();
    centerBackground.setTranslateY(1);
    centerBackground.setFill(Color.PALEGREEN);
    centerBackground.setWidth(tileSize - 2);
    centerBackground.setHeight(44);
    String newName = name.replace(" ", "\n");
    Label propertyName = new Label(newName);
    propertyName.setTextAlignment(TextAlignment.CENTER);
    center.getChildren().addAll(centerBackground, propertyName);
    return center;
  }

  private Rectangle makeColorBar(int tileSize, Paint color) {
    Rectangle colorBanner = new Rectangle();
    colorBanner.setTranslateX(1);
    colorBanner.setTranslateY(1);
    colorBanner.setFill(color);
    colorBanner.setWidth(tileSize - 2);
    colorBanner.setHeight(18);
    return colorBanner;
  }

  /**
   * From StackOverFlow
   *
   * @param inputStr - string to compare against list
   * @param items    - list of names
   * @return - true if string is found in list or false otherwise
   */
  private boolean stringContainsItemFromList(String inputStr, List<String> items) {
    return items.stream().anyMatch(inputStr::contains);
  }

  public String getName() {
    return tileName;
  }
}
