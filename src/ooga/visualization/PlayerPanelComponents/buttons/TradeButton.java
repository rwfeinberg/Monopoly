package ooga.visualization.PlayerPanelComponents.buttons;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import ooga.configuration.CSVLoader;
import ooga.model.Player;
import ooga.model.tiles.Tile;
import ooga.visualization.PlayerPanelComponents.PanelButton;
import ooga.visualization.Trade;
import ooga.visualization.ViewComponents;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Catherine Livingston
 * <p>
 * Purpose - The TradeButton.java class is the class that represents the Trade button in PlayerPanel.
 * <p>
 * Assumptions - This class extends PanelButton.java
 * <p>
 * Dependencies - This class depends on Player, Tile, Trade, CSVLoader, PanelButton, and ViewComponents.java.
 */
public class TradeButton extends PanelButton {

  private static final int SELECT_PROPERTY = 2;
  private static final int SELECT_PLAYER = 3;
  private static final int ENTER_MONEY = 4;
  private static final int YOUR_TRADE = 5;
  private Player currentPlayer;
  private TextField moneyAmount1;
  private TextField moneyAmount2;
  private ChoiceBox<String> playerSelection;
  private ChoiceBox<String> propertySelection1;
  private ChoiceBox<String> propertySelection2;
  private String language;
  private List<String> messages;

  /**
   * Constructor, adds function
   *
   * @param prop - Button id
   * @param player - pointer to the currentplayer
   * @param allPlayers - list of all the players in the game
   * @param language - game language
   */
  public TradeButton(String prop, Player player, List<Player> allPlayers, String language) {
    super(prop, language);
    this.messages = (new CSVLoader("tradeButton", language)).getValues();
    this.setOnAction(e -> openPopUp(allPlayers));
  }

  @Override
  public void updateStatus(Player player) {
    this.currentPlayer = player;
    if (player.getOwnedTiles().size() > 0) {
      this.setDisable(false);
    }
  }

  public void openPopUp(List<Player> allPlayers) {
    Dialog<Trade> popUp = new Dialog<>();
    popUp.setHeaderText(messages.get(1));
    DialogPane dialogPane = popUp.getDialogPane();
    dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    VBox currentUserPane = createCurrentUserPane();
    currentUserPane.setSpacing(10);
    VBox otherUserPane = createOtherUserPane(allPlayers);
    otherUserPane.setSpacing(7);
    FlowPane bothSides = new FlowPane(currentUserPane, otherUserPane);
    bothSides.setHgap(20);
    dialogPane.setContent(bothSides);
    convertResult(popUp, allPlayers);
    Optional<Trade> results = popUp.showAndWait();
    ViewComponents.updatePanel(currentPlayer);
  }

  private void convertResult(Dialog<Trade> popUp, List<Player> allPlayers) {
    popUp.setResultConverter((ButtonType button) -> {
      if (button == ButtonType.OK) {
        Player otherPlayer = findPlayer(playerSelection.getValue(), allPlayers);
        return new Trade(currentPlayer, moneyAmount1.getText(), propertySelection1.getValue(),
            otherPlayer, moneyAmount2.getText(), propertySelection2.getValue());
      }
      return null;
    });
  }

  private VBox createOtherUserPane(List<Player> otherPlayers) {
    //Property Selection
    propertySelection2 = new ChoiceBox<>();
    propertySelection2.getItems().add(messages.get(SELECT_PROPERTY));
    propertySelection2.setValue(messages.get(SELECT_PROPERTY));
    //Player Selection
    playerSelection = new ChoiceBox<>();
    List<Player> selection = new ArrayList<>();
    selection.addAll(otherPlayers);
    selection.remove(currentPlayer);
    playerSelection.getItems().addAll(makeStringPlayerList(selection));
    playerSelection.getSelectionModel().selectedItemProperty().addListener(
        (ov, value, new_value) -> changeProperties(propertySelection2,
            findPlayer(new_value, otherPlayers)));
    //Money TextField
    moneyAmount2 = new TextField("0");
    noLetters(moneyAmount2);
    Label playerLabel = ViewComponents
        .createLabel("SelectPlayer", messages.get(SELECT_PLAYER), 0, 0);
    Label moneyLabel = ViewComponents
        .createLabel("EnterMoneyAmount", messages.get(ENTER_MONEY), 0, 0);
    Label propertyLabel = ViewComponents
        .createLabel("SelectProperty", messages.get(SELECT_PROPERTY) + ":", 0, 0);
    return new VBox(playerLabel, playerSelection, moneyLabel, moneyAmount2, propertyLabel,
        propertySelection2);
  }

  private void changeProperties(ChoiceBox<String> properties, Player player) {
    properties.getItems().clear();
    properties.getItems().addAll(makeStringTileList(player.getOwnedTiles()));
  }

  private List<String> makeStringPlayerList(List<Player> players) {
    List<String> playerNames = new ArrayList<>();
    for (Player otherPlayer : players) {
      playerNames.add(otherPlayer.getMyName());
    }
    return playerNames;
  }

  private List<String> makeStringTileList(List<Tile> tiles) {
    List<String> tileNames = new ArrayList<>();
    for (Tile tile : tiles) {
      tileNames.add(tile.getName());
    }
    return tileNames;
  }

  private Player findPlayer(String new_value, List<Player> otherPlayers) {
    for (Player guess : otherPlayers) {
      if (guess.getMyName().equals(new_value)) {
        return guess;
      }
    }
    return null;
  }

  private VBox createCurrentUserPane() {
    Label name = new Label(currentPlayer.getMyName());
    //moneyAmount1 = new TextField("Amount");
    moneyAmount1 = new TextField("0");
    noLetters(moneyAmount1);
    propertySelection1 = new ChoiceBox<>();
    propertySelection1.getItems().addAll(makeStringTileList(currentPlayer.getOwnedTiles()));
    propertySelection1.getItems().add(messages.get(SELECT_PROPERTY));
    propertySelection1.setValue(messages.get(SELECT_PROPERTY));
    Label playerLabel = ViewComponents.createLabel("SelectPlayer", messages.get(YOUR_TRADE), 0, 0);
    Label moneyLabel = ViewComponents.createLabel("EnterMoneyAmount",
        messages.get(ENTER_MONEY), 0, 0);
    Label propertyLabel = ViewComponents.createLabel("SelectProperty",
        messages.get(SELECT_PROPERTY) + ":", 0, 0);
    return new VBox(playerLabel, name, moneyLabel, moneyAmount1, propertyLabel, propertySelection1);
  }

  private void noLetters(TextField input) {
    input.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        if (!newValue.matches("\\d*")) {
          input.setText(newValue.replaceAll("[^\\d]", ""));
        }
      }
    });
  }
}
