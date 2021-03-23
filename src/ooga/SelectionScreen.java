package ooga;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
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
import ooga.configuration.ButtonGameTypeFactory;
import ooga.configuration.CSVLoader;
import ooga.visualization.ImageReader;
import ooga.visualization.PropertiesLoader;
import ooga.visualization.WriteToCSV;

public class SelectionScreen extends HBox {

  public static final int MIN_PLAYERS = 2;
  public static final int MAX_PLAYERS = 4;

  private Map<String, Button> buttonMap;
  private List<String> SelectionStrings;
  private int numberOfPlayers;
  private String languageChoice;
  private List<String> playerNames;
  private List<String> playerPieces;
  private ButtonGameTypeFactory buttonFactory;

  /**
   * Created button and adds to root
   */
  public SelectionScreen() {
    this.setHeight(220);
    this.setWidth(600);
    this.setSpacing(50);
    this.setId("Selection");
    buttonFactory = new ButtonGameTypeFactory();
    buttonMap = new HashMap<>();
    createButtonMap();
  }

  private void createButtonMap() {
    for (String gameType : buttonFactory.getGameTypes()) {
      buttonMap.put(gameType, new Button());
    }
  }

  private void createGameTypeButtons() {
    List<String> gameTypes = (new CSVLoader("gameTypes", languageChoice)).getValues();
    for (String type : gameTypes) {
      String[] setOptions = type.split("--");
      Entry<String, String> options = new SimpleEntry<>(setOptions[0], setOptions[1]);
      VBox result = makeChoice(options);
      this.getChildren().add(result);
    }
  }

  private VBox makeChoice(Entry<String, String> prop) {
    VBox result = new VBox();
    createGameButton(prop.getKey());

    Label description = new Label(prop.getValue());
    description.setTranslateX(this.buttonMap.get(prop.getKey()).getTranslateX());
    description.setTranslateY(this.buttonMap.get(prop.getKey()).getTranslateY());
    description.setId(prop.getKey() + "ButtonDescription");

    result.setSpacing(8);
    result.setAlignment(Pos.CENTER);
    result.getChildren().addAll(this.buttonMap.get(prop.getKey()), description);
    return result;
  }

  private void createGameButton(String prop) {
    ImageView view = new ImageView(ImageReader.findTileImage(prop + "Button"));
    view.setFitHeight(200);
    view.setPreserveRatio(true);

    this.buttonMap.get(prop).setGraphic(view);
    this.buttonMap.get(prop).setId(prop + "Button");
  }

  public void languagePopUp() {
    CSVLoader languageOptions = new CSVLoader("languageOptions", "");
    ChoiceDialog<String> languageDialog = new ChoiceDialog<>("English",
        languageOptions.getValues());
    languageDialog.setHeaderText("Choose a language: ");
    languageDialog.setTitle("Start");
    languageDialog.showAndWait();
    languageChoice = PropertiesLoader.getTranslation(languageDialog.getSelectedItem());
    SelectionStrings = (new CSVLoader("selectionScreen", languageChoice)).getValues();
    writeToCSV();
    createGameTypeButtons();
    playerPopUp();
  }

  private void writeToCSV() {
    List<String[]> data = new ArrayList<>();
    data.add(new String[]{languageChoice});
    new WriteToCSV("languageChoice", data);
  }

  public int playerPopUp() {
    List<Integer> playerChoices = new ArrayList<>();
    for (int i = MIN_PLAYERS; i < MAX_PLAYERS + 1; i++) {
      playerChoices.add(i);
    }

    ChoiceDialog<Integer> choiceDialog = new ChoiceDialog<>(MIN_PLAYERS, playerChoices);
    choiceDialog.setHeaderText(SelectionStrings.get(0) + "\n" + SelectionStrings.get(1));
    choiceDialog.setTitle(SelectionStrings.get(2));
    choiceDialog.showAndWait();
    numberOfPlayers = choiceDialog.getSelectedItem();
    enterNamesPopUp();
    return choiceDialog.getSelectedItem();
  }

  private void enterNamesPopUp() {
    playerNames = new LinkedList<>();
    playerPieces = new LinkedList<>();
    CSVLoader pieceViewOptions = new CSVLoader("pieceOptions", languageChoice);
    for (int i = 0; i < numberOfPlayers; i++) {
      TextInputDialog enterName = new TextInputDialog();
      enterName.setHeaderText(SelectionStrings.get(3) + (i + 1));
      enterName.showAndWait();
      playerNames.add(enterName.getResult());
      pieceViewOptions.getValues().removeAll(playerPieces);
      ChoiceDialog<String> pieceOptions = new ChoiceDialog<>(pieceViewOptions.getValues().get(0));
      pieceOptions.getItems().addAll(pieceViewOptions.getValues());
      pieceOptions.setHeaderText(enterName.getResult() + SelectionStrings.get(4));
      pieceOptions.showAndWait();
      playerPieces.add(PropertiesLoader.getTranslation(pieceOptions.getResult()));
    }
  }

  /**
   * @param scene - scene of application
   */
  public void addActions(Scene scene, String prop) {
    this.buttonMap.get(prop)
        .setOnAction(e -> new Controller(scene, playerPieces, playerNames, prop, languageChoice));
  }

}
