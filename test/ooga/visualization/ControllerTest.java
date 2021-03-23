package ooga.visualization;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ooga.Controller;
import ooga.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import static org.junit.jupiter.api.Assertions.*;


public class ControllerTest extends DukeApplicationTest {
  private Controller gameController;

  @BeforeEach
  public void setUp() {
    Group root = new Group();
    Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT);
    List<String> names = List.of("Catherine", "Emma");
    List<String> pieces = List.of("Dog", "Thimble");
    gameController = new Controller(scene, pieces, names, "Standard", "English");
  }
  @Test
  public void testController() {
    assertEquals(gameController.getTileFromModel(12).getName().replace(" ", ""), gameController.getTileFromVisual(12).getId());
  }


}
