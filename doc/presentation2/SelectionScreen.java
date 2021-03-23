package ooga;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ooga.visualization.ImageReader;

public class SelectionScreen extends HBox {

  private Map<String, Button> buttonMap = Map.of("Monopoly", new Button("Play!"), "Dukeopoly", new Button("Play!"));
  private int numberOfPlayers;
  private List<String> playerNames;

  /**
   * Created button and adds to root
   */
  public SelectionScreen() {
    this.setHeight(220);
    for (Entry<String, String> type : Main.GAME_TYPES.entrySet()) {
      VBox result = makeChoice(type);
      this.getChildren().add(result);
    }
    this.setSpacing(100);
    this.setLayoutX(20);
    this.setLayoutY(40);
  }

  private VBox makeChoice(Entry<String, String> prop) {
    VBox result = new VBox();
    createGameButton(prop.getKey());

    Label description = new Label(prop.getValue());
    description.setTranslateX(this.buttonMap.get(prop.getKey()).getTranslateX());
    description.setTranslateY(this.buttonMap.get(prop.getKey()).getTranslateY());
    description.setId(prop.getKey()+"ButtonDescription");

    result.setSpacing(8);
    result.setAlignment(Pos.CENTER);
    result.getChildren().addAll(this.buttonMap.get(prop.getKey()), description);
    return result;
  }

  private void createGameButton(String prop) {
    ImageView view = new ImageView(ImageReader.findTileImage(prop+"Button"));
    view.setFitHeight(200);
    view.setPreserveRatio(true);

    this.buttonMap.get(prop).setGraphic(view);
    this.buttonMap.get(prop).setId(prop+"Button");
  }

  public int playerPopUp(){
    List<Integer> playerChoices = new ArrayList<>();
    for (int i=1;i<Main.MAX_PLAYERS+1;i++) playerChoices.add(i);

    ChoiceDialog<Integer> choiceDialog = new ChoiceDialog<>(1, playerChoices);
    choiceDialog.setHeaderText("Choose Number of Players\n(Will default to 1 if closed)");
    choiceDialog.setTitle("Start");
    choiceDialog.showAndWait();
    numberOfPlayers = choiceDialog.getSelectedItem();
    enterNamesPopUp();
    return choiceDialog.getSelectedItem();
  }

  private void enterNamesPopUp() {
    playerNames = new LinkedList<>();
    for (int i = 0; i < numberOfPlayers; i++) {
      TextInputDialog enterName = new TextInputDialog();
      enterName.setHeaderText("Enter name " + (i+1));
      enterName.showAndWait();
      playerNames.add(enterName.getResult());
    }
  }

  /**
   * @param scene - scene of application
   */
  public void addActions(Scene scene, String prop) {
    this.buttonMap.get(prop).setOnAction(e -> new Controller(scene, numberOfPlayers, playerNames));
  }

}
