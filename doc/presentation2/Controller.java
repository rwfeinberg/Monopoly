package ooga;

import javafx.beans.value.ChangeListener;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import ooga.configuration.PropertyFactory;
import ooga.model.Player;
import ooga.model.Property;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Controller {

  private static final int PADDING = 15;
//  private final int HEIGHT = 880;
//  private final int WIDTH = 1600;
  private PlayerPanel gamePanel;
  private List<Property> tiles;
  private List<Piece> gamePieces;
  private ImageView background;

  public Controller(Scene scene, int numberOfPlayers, List<String> playerNames) {
    Group root = new Group();
    background = new ImageView();
    root.getChildren().addAll(setBackground("WoodTable"), backgroundSelection());
    root.getChildren().add(ViewComponents.createLabel("SelectBackground", "Select Background: ", 1200, PADDING-5));
    PropertyFactory properties = new PropertyFactory("StandardTiles");
    tiles = properties.getProperties();
    setUpNextProperties(tiles);
    List<String> propertyNames = properties.getPropertyNames();
    List<Paint> colors = new ArrayList<>();
    List<Integer> prices = new ArrayList<>();
    for (Property currProperty : tiles) {
      colors.add(currProperty.getColor());
      prices.add(currProperty.getRent());
    }

    MonopolyBoard board = new MonopolyBoard(root, propertyNames, colors, prices);

    List<Player> gamePlayers = new LinkedList<>();
    gamePieces = new LinkedList<>();
    for (int i = 0; i < numberOfPlayers; i++) {
      Player newPlayer = new Player(tiles.get(0), playerNames.get(i));
      gamePlayers.add(newPlayer);
      Piece gamePiece = new Piece(i+1);
      gamePieces.add(gamePiece);
      root.getChildren().add(gamePiece);
    }
    Button nextTurn = ViewComponents.createButton("NextTurn", "Next Turn", Main.WIDTH-(10*PADDING), PADDING);
    nextTurn.setOnAction(event -> goToNextPlayer());
    root.getChildren().add(nextTurn);
    gamePanel = new PlayerPanel(root, gamePlayers, gamePieces);
    scene.setFill(Color.FUCHSIA);
    scene.setRoot(root);
  }

  private ImageView setBackground(String fileName) {
    background.setImage(ImageReader.findTileImage(fileName.replace(" ", "") + "Background"));
    background.setFitHeight(Main.HEIGHT);
    background.setFitWidth(Main.WIDTH);
    background.setLayoutX(0);
    background.setLayoutY(0);
    return background;
  }

  private void setUpNextProperties(List<Property> gameProperties) {
    for (int i = 0; i < gameProperties.size()-1; i++) {
      Property currentProperty = gameProperties.get(i);
      Property nextProperty = gameProperties.get(i+1);
      currentProperty.setNextProperty(nextProperty);
    }
    Property lastProperty = gameProperties.get(gameProperties.size()-1);
    Property firstProperty = gameProperties.get(0);
    lastProperty.setNextProperty(firstProperty);
  }

  public ChoiceBox<String> backgroundSelection() {
    ChoiceBox<String> backgroundSelection = new ChoiceBox<>();
    CSVLoader backgroundOptions = new CSVLoader("backgroundOptions");
    backgroundSelection.getItems().addAll(backgroundOptions.getValues());
    backgroundSelection.getSelectionModel().selectedItemProperty().addListener(
        (ChangeListener<String>) (ov, value, new_value) -> setBackground(new_value));
    backgroundSelection.setLayoutX(1200);
    backgroundSelection.setLayoutY(2*PADDING);
    return backgroundSelection;
  }


  private void goToNextPlayer() {
    gamePanel.nextPlayer();
  }

}
