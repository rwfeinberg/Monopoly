package ooga.visualization;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import ooga.Controller;
import ooga.Main;
import ooga.SelectionScreen;
import ooga.model.Board;
import ooga.model.Player;
import ooga.model.tiles.Tile;
import ooga.visualization.PlayerPanelComponents.PanelButton;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BackgroundTest extends DukeApplicationTest {

  Group root = new Group();
  Scene myScene;

  @Override
  public void start(Stage primaryStage) {
    myScene = new Scene(root, Main.WIDTH, Main.HEIGHT);
    primaryStage.setScene(myScene);
    primaryStage.show();

    List<String> names = List.of("A", "B");
    List<String> pieces = List.of("Shoe", "Dog");
    Controller c = new Controller(myScene, pieces, names,"Standard", "English");
  }

  @Test
  public void testBackgroundSwitch(){
    ImageView background = lookup("#Background").query();
    Image initial = background.getImage();
    select((ChoiceBox<String>) lookup("#BackgroundDropdown").query(), "Duke");
    assertNotEquals(initial, background.getImage());
  }
}
