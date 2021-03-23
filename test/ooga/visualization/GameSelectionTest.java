package ooga.visualization;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ooga.Controller;
import ooga.Main;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class GameSelectionTest extends DukeApplicationTest {

  Group root = new Group();
  Scene myScene;

  @Override
  public void start(Stage primaryStage) {
    myScene = new Scene(root, Main.WIDTH, Main.HEIGHT);
    primaryStage.setScene(myScene);
    primaryStage.show();

    List<String> names = List.of("Test");
    List<String> pieces = List.of("Dog");
    Controller c = new Controller(myScene, pieces, names, "Standard", "English");
  }

  @Test
  public void testNotPurchaseSpaceHasImage() {
    VisualTile testTile = lookup("#Go").query();
    assertTrue(testTile.isVisible());

    assertEquals(testTile.getBackground().getImages().size(), 1);
  }
}
