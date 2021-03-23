package ooga.visualization;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ooga.Main;
import ooga.SelectionScreen;
import ooga.configuration.ButtonGameTypeFactory;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

public class PlayerPopUpTest extends DukeApplicationTest {
  private static final int MAX_PLAYERS = 4;

  Group root = new Group();
  Group root2 = new Group();
  Scene myScene;
  int numberOfPlayers;
  SelectionScreen startScreen;

  @Override
  public void start(Stage primaryStage){
    startScreen = new SelectionScreen();

    myScene = new Scene(root2, 1600, 920);
    primaryStage.setTitle("Monopoly");
    primaryStage.setResizable(false);
    primaryStage.setScene(myScene);
    primaryStage.show();

    for (String game : new ButtonGameTypeFactory().getGameTypes()) {
      startScreen.addActions(myScene, game);
    }
    numberOfPlayers = startScreen.playerPopUp();
  }


  @Test
  public void testPlayers(){
    List<Integer> possibleChoices = new ArrayList<>();

    for (int i=2;i<MAX_PLAYERS+1;i++) { possibleChoices.add(i); }

    assertTrue(possibleChoices.contains(numberOfPlayers));
  }
}
