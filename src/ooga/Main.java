package ooga;

import java.util.List;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ooga.configuration.ButtonGameTypeFactory;
import ooga.visualization.BackgroundHandler;

import java.util.Map;

public class Main extends Application {

  public static final int TILE_SIZE = 70; //80
  public static final int WIDTH = 1400; //160
  public static final int HEIGHT = 820; //920

  public static final String STYLESHEET = "default.css";
  //This could be changed to a property file

  Group startRoot = new Group();
  Scene myScene;

  @Override
  public void start(Stage primaryStage) {
    SelectionScreen startScreen = new SelectionScreen();
    BackgroundHandler background = new BackgroundHandler("selection");
    startRoot.getChildren().add(background);

    startRoot.getChildren().add(startScreen);

    myScene = new Scene(startRoot, WIDTH, HEIGHT);
    System.out.println(myScene);
    myScene.getStylesheets().add(STYLESHEET);

    primaryStage.setTitle("Monopoly");
    primaryStage.setResizable(false);
    primaryStage.setScene(myScene);
    primaryStage.show();

    for (String game : new ButtonGameTypeFactory().getGameTypes()) {
      startScreen.addActions(myScene, game);
    }
    startScreen.languagePopUp();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
