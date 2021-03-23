package ooga.visualization;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import ooga.Controller;
import ooga.Main;
import ooga.SelectionScreen;
import ooga.model.Board;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VisualBoardTest extends DukeApplicationTest {

  Group root = new Group();
  Scene myScene;

  @Override
  public void start(Stage primaryStage) {
    myScene = new Scene(root, Main.WIDTH, Main.HEIGHT);
    primaryStage.setScene(myScene);
    primaryStage.show();

    List<String> names = List.of("Test");
    List<String> pieces = List.of("Dog");
    Controller c = new Controller(myScene, pieces, names,"Standard", "English");
  }

  @Test
  public void testSpaceIsPresent(){
    VisualTile testTile = lookup("#Boardwalk").query();
    assertTrue(testTile.isVisible());

    //Should be black, since the outline (rectangle in the tile's background) of every tile is black
    assertEquals(testTile.getBackground().getFills().get(0).getFill(), Color.BLACK);
  }

  @Test
  public void testNotPurchaseSpaceHasImage() {
    VisualTile testTile = lookup("#Go").query();
    assertTrue(testTile.isVisible());

    assertEquals(testTile.getBackground().getImages().size(), 1);
  }
}
