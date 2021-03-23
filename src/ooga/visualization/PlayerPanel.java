package ooga.visualization;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import ooga.Main;

import ooga.configuration.CSVLoader;

import ooga.configuration.ButtonGameTypeFactory;

import ooga.model.Die;
import ooga.model.Player;
import ooga.model.Turn;
import ooga.model.actions.Action;
import ooga.model.tiles.Tile;
import ooga.visualization.PlayerPanelComponents.PlayerButtonPanel;
import ooga.visualization.PlayerPanelComponents.PlayerMoneyPanel;
import ooga.visualization.PlayerPanelComponents.PlayerNamePanel;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Ryan Feinberg
 * @author Catherine Livingston
 * @author Sylvie Mason
 * @author Muazzam Khan
 * <p>
 * Purpose - The PlayerPanel.java class represents the control Panel present on the right side of the screen when playing games. It houses the 
 * player's information as well as their properties and buttons for actions the player can take.
 * <p>
 * Assumptions - The ACTION_PACKAGE should point to all of the possible chance/communtiy card actions.
 * The CHEAT_KEYS should contain keycodes mapped to method names.
 * <p>
 * Dependencies - This class depends on Main.java for constants, CSVLoader, ButtonGameTypeFactory, Die, Player, Turn, Action, Tile, PlayerButtonPanel, 
 * PlayerMoneyPanel, and PlayerNamePanel.java
 */
public class PlayerPanel extends VBox {

  private static final String ACTION_PACKAGE = "ooga.model.actions.";
  private static final Map<KeyCode, String> CHEAT_KEYS = Map.of(KeyCode.R, "AdvanceToRailroad",
      KeyCode.U, "AdvanceToUtility", KeyCode.L, "AdvanceToGo",
      KeyCode.A, "IncreaseWealthBySetConstant", KeyCode.V, "MoveForwardSingleTile", KeyCode.D,
      "DecreaseWealthBySetConstant");

  public static final int DEFAULT_PADDING = 5;

  private final int PANEL_HEIGHT = 600;
  private final int PANEL_WIDTH = 500;
  private final List<Player> gamePlayers;
  private final List<PieceView> gamePieces;
  private final List<String> buttonMessages;
  private String language;
  private Die die = new Die();
  private Player currentPlayer;
  private PieceView currentPiece;
  private Button rollButton;

  private PlayerNamePanel namePanel;
  private PlayerMoneyPanel moneyPanel;
  private PlayerButtonPanel buttonPanel;

  private HBox diceBox;
  private Pair<Integer, Integer> roll;

  private Scene scene;


  /**
  * Constructor, initializes panel with components, is only called ONCE (when the game starts)
  *
  * @param players - List of all of the players initialized.
  * @param pieces - List of the chosen Pieces by each player, in order of player
  * @param scene - Current scene pointer
  * @param language - game language
  * @param gameType - String represented the selected game's type
  */
  public PlayerPanel(List<Player> players, List<PieceView> pieces, Scene scene, String language,
      String gameType) {
    this.scene = scene;
    this.language = language;
    gamePlayers = players;
    gamePieces = pieces;
    currentPlayer = gamePlayers.get(0);
    currentPiece = gamePieces.get(0);

    this.buttonMessages = (new CSVLoader("playerPanelMessages", language)).getValues();

    this.scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));

    this.setLayoutX(Main.TILE_SIZE * 11 + 100);
    this.setLayoutY(75);

    StackPane box = fillPanel(gameType);

    this.getChildren().addAll(
        ViewComponents.createLabel("CurrentPlayerDashboardLabel", buttonMessages.get(0), 0, 0),
        box);
    currentPlayer.setPanel(this);
  }


  private StackPane fillPanel(String gameType) {
    StackPane box = new StackPane();

    //Background rectangle fill
    Rectangle rect = ViewComponents
        .createRectangle(PANEL_WIDTH, PANEL_HEIGHT, 0, 0, Color.ALICEBLUE, Color.BLACK);

    //Panel with labels and buttons
    VBox panel = new VBox();
    panel.setPrefHeight(PANEL_HEIGHT);
    panel.setSpacing(30);
    panel.setTranslateX(DEFAULT_PADDING);
    panel.setTranslateY(DEFAULT_PADDING);

    namePanel = new PlayerNamePanel(currentPlayer, currentPiece, language);
    buttonPanel = new PlayerButtonPanel(
        new ButtonGameTypeFactory().getGameTypesToButtons().get(gameType), currentPlayer,
        gamePlayers, language);
    moneyPanel = new PlayerMoneyPanel(currentPlayer, language);
    VBox diceBox = makeDiceBox();

    panel.getChildren().addAll(namePanel, diceBox, buttonPanel, moneyPanel);
    box.getChildren().addAll(rect, panel);
    return box;
  }

  private VBox makeDiceBox() {
    VBox result = new VBox();
    result.setSpacing(DEFAULT_PADDING);

    rollButton = ViewComponents.createButton("RollDie", buttonMessages.get(1), 0, 0);
    rollButton.setOnAction(event -> rollDice());

    diceBox = new HBox();
    diceBox.setSpacing(DEFAULT_PADDING);
    ImageView dice1 = new ImageView();
    ImageView dice2 = new ImageView();
    dice1.setImage(ImageReader.getDiceImage(1));
    dice2.setImage(ImageReader.getDiceImage(1));
    diceBox.getChildren().addAll(dice1, dice2);

    diceBox.setAlignment(Pos.CENTER);
    result.setAlignment(Pos.CENTER);

    result.getChildren().addAll(rollButton, diceBox);
    return result;
  }

  /**
  * This method rolls the dice when the button "Roll the Die" is clicked, and commences the turn. It also animates the dice and updates anything 
  * necessary on the turn.
  *
  */
  public void rollDice() {
    for (int i = 0; i < 3; i++) {
      int roll1 = die.rollDie();
      int roll2 = die.rollDie();
      ViewComponents.createDiceAnimation(diceBox, roll1, roll2);

      Turn thisTurn = new Turn(currentPlayer, roll1, roll2);
      currentPiece.setPosition(currentPlayer);
      boolean prompt = thisTurn.getPromptUser();
      List<String> popUpStrings = thisTurn.getPopUpStrings();
      dealWithPrompt(prompt, popUpStrings);

      if (!currentPlayer.isAlive()) {
        currentPlayer.endPlayer();
        ViewComponents.createConfirmPopUp(getLossStrings());
      }
      currentPiece.setPosition(currentPlayer);
      if (roll1 != roll2 || !currentPlayer.isAlive()) {
        break;
      }
      if (i < 2) {
        List<String> words = List.of(buttonMessages.get(2));
        ViewComponents.createConfirmPopUp(words);
      }

    }
    currentPiece.setPosition(currentPlayer);
    rollButton.setDisable(true);
  }

  private void dealWithPrompt(boolean prompt, List<String> popUpStrings) {
    if (!prompt || popUpStrings.size() > 3) {
      List<String> newList = new ArrayList<>();
      String first = popUpStrings.get(0);
      newList.add(first);
      String second = popUpStrings.get(1);
      newList.add(second);
      popUpStrings.remove(first);
      popUpStrings.remove(second);

      boolean confirmation = ViewComponents
          .createConfirmPopUp(newList); //THIS IS ALWAYS TRUE, Used for non-choices
      if (confirmation) {
        ViewComponents.updatePanel(currentPlayer);
      }
    }
    if (prompt) {
      boolean wantsToBuy = ViewComponents
          .createChoicePopUp(popUpStrings); //True if user said yes, false if they said no
      if (wantsToBuy) {
        Tile currentTile = currentPlayer.getLocation();
        if (currentPlayer.getWealth() >= currentTile.getPrice()) {
          currentPlayer.buyProperty();
          ViewComponents.updatePanel(currentPlayer);
        } else {
          ViewComponents.createConfirmPopUp(getInvalidPurchaseStrings());
        }
      }
    }
  }

  private List<String> getLossStrings() {
    ArrayList<String> ret = new ArrayList<>();
    ret.add("You are now bankrupt. You lose!");
    ret.add("OK");
    return ret;
  }

  private List<String> getInvalidPurchaseStrings() {
    ArrayList<String> ret = new ArrayList<String>();
    ret.add("You do not have enough money for this purchase.");
    ret.add("OK");
    return ret;
  }

  private void setCurrentPlayerPanel() {
    namePanel.changeName(currentPlayer);
    namePanel.refreshPropertyDropdown(currentPlayer);
    moneyPanel.updateBalance(currentPlayer);
    rollButton.setDisable(false);
    currentPlayer.setPanel(this);
    handleJail();
  }

  /**
  * This method is called when the NextTurn button is clicked, and switches the PlayerPanel to represent the next player, updates the currentPlayer 
  * and currentPiece pointers, and refreshes the buttons.
  */
  public void nextPlayer() {
    int index = gamePlayers.indexOf(currentPlayer);
    removeDeadPlayers();
    int nextIndex = index + 1;
    if (nextIndex > (gamePlayers.size() - 1)) {
      //nextIndex -= gamePlayers.size();
      nextIndex = 0;
    }
    currentPlayer = gamePlayers.get(nextIndex);
    currentPiece = gamePieces.get(nextIndex);
    namePanel.setUpImagePiece(currentPiece);
    setCurrentPlayerPanel();
    buttonPanel.refreshButtons(currentPlayer);
  }

  private void removeDeadPlayers() {
    List<Player> toRemove = new ArrayList<>();
    for (Player p : gamePlayers) {
      if (!p.isAlive()) {
        toRemove.add(p);
        System.out.println("Player" + p.getMyName() + " removed.");
      }
    }
    for (Player p : toRemove) {
      gamePlayers.remove(p);
    }
    if (gamePlayers.size() == 1) {
      endGame();
    }
  }

//
//  private void switchPlayers(String name){}

  private void handleJail() {
    if (currentPlayer.getSuspended() != 0) {
      List<String> popUpStrings = new ArrayList<>();
      popUpStrings.add(buttonMessages.get(3));
      popUpStrings.add(buttonMessages.get(4));
      popUpStrings.add(buttonMessages.get(5));
      boolean wantsToPay = ViewComponents
          .createChoicePopUp(popUpStrings);
      if (wantsToPay && currentPlayer.getWealth() >= 50) {
        currentPlayer.editWealth(-50);
        currentPlayer.setSuspended(0);
        moneyPanel.updateBalance(currentPlayer);
      }
    }
  }

  private void endGame() {
    List<String> popUpStrings = new ArrayList<>();
    popUpStrings.add("Game Over! " + gamePlayers.get(0).getMyName() + " wins!");
    ViewComponents.createConfirmPopUp(popUpStrings);
    //TODO: exit game
  }


  public PlayerNamePanel getNamePanel() {
    return this.namePanel;
  }

  public PlayerMoneyPanel getMoneyPanel() {
    return this.moneyPanel;
  }

  public int getNumOfPlayers() {
    return this.gamePlayers.size();
  }

  public void updatePieceLocation() {
    currentPiece.setPosition(currentPlayer.getLocation().getName());
  }

  public Player getCurrentPlayer() {
    return currentPlayer;
  }

  public PlayerButtonPanel getButtonPanel() {
    return this.buttonPanel;
  }


  private void handleKeyInput(KeyCode code) {
    try {
      Action action = (Action) Class.forName(ACTION_PACKAGE + CHEAT_KEYS.get(code))
          .getDeclaredConstructors()[0]
          .newInstance(-1, -1, gamePlayers, currentPlayer,
              "");
      action.performAction();
      currentPiece.setPosition(currentPlayer);
      setCurrentPlayerPanel();
    } catch (SecurityException | ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
      //cheat key does not exist, do nothing
    }
    currentPlayer.updateNetWorth();
  }

}
