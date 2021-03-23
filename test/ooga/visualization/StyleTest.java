package ooga.visualization;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StyleTest extends DukeApplicationTest {

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
  public void testStyleChange() {
    ComboBox<String> dropdown = lookup("#StyleDropdown").query();
    Button nextTurn = lookup("#NextTurn").query();

    Paint initial = nextTurn.getBackground().getFills().get(0).getFill();

    select(dropdown, "dark.css");

    assertNotEquals(initial, nextTurn.getBackground().getFills().get(0).getFill());
  }
}
