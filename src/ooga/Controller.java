package ooga;

import java.util.LinkedList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import ooga.configuration.ButtonGameTypeFactory;
import ooga.model.Game;
import ooga.model.tiles.Tile;
import ooga.visualization.BackgroundHandler;
import ooga.configuration.CSVLoader;
import ooga.visualization.Piece;
import ooga.visualization.PieceView;
import ooga.visualization.PlayerPanel;
import ooga.visualization.PropertiesLoader;
import ooga.visualization.ViewComponents;
import ooga.visualization.VisualBoard;
import ooga.visualization.VisualTile;

public class Controller {
  /**
   * @author Catherine Livingston
   * @author Ryan Feinberg
   * <p>
   * Purpose - The Controller.java class is used to start the game after the user has given all of
   * their input. It connects the frontend and the backend to work together while the game is played.
   * <p>
   * Assumptions - The parameters to the constructor are in line with how they are used in the class.
   * <p>
   * Dependencies - This class depends on Player, PlayerPanel, Game, Visualboard, PieceView, Piece,
   * BackgroundHandler, and ViewComponents.
   */
  private static final int PADDING = 15;

  private String language;
  private PlayerPanel gamePanel;
  private Game gameBoard;
  private VisualBoard visualBoard;
  private List<PieceView> gamePieces;
  private List<String> messages;

  /**
   * Constructor, initializes view of the piece with params and adds to game board
   *
   * @param scene - scene to add the root to
   * @param pieces - names of the pieces chosen by each player in order of the players
   * @param playerNames - names of the players playing the game
   * @param gameType - the game variation chosen by the players
   * @param language - language to play the game in
   */
  public Controller(Scene scene, List<String> pieces, List<String> playerNames, String gameType,
      String language) {
    this.language = language;
    this.messages = (new CSVLoader("controllerMessages", language)).getValues();
    Group root = new Group();
    scene.setRoot(root);
    String tileFile = PropertiesLoader.getGameTypeOptions(gameType)[0];
    String backgroundFile = PropertiesLoader.getGameTypeOptions(gameType)[1];

    //Backend
    gameBoard = new Game(tileFile, playerNames);

    //Frontend
    BackgroundHandler background = new BackgroundHandler(backgroundFile);
    root.getChildren().addAll(background, background.backgroundSelection(this.language),
        background.selectBackgroundLabel());

    VBox c = ViewComponents
        .createCSSDropdown(List.of("default.css", "dark.css", "forest.css"), root, background,
            language);
    root.getChildren().add(c);

    visualBoard = new VisualBoard(gameBoard.getMyBoard());
    root.getChildren().add(visualBoard);

    gamePieces = createPieces(pieces, visualBoard);
    root.getChildren().addAll(gamePieces);

    Button nextTurn = ViewComponents.createButton("NextTurn", messages.get(0),
        Main.WIDTH - (12 * PADDING), PADDING);
    nextTurn.setOnAction(event -> goToNextPlayer());
    Button startOver = ViewComponents.createButton("StartOver", messages.get(1),
        Main.WIDTH - (12 * PADDING), Main.HEIGHT - (8 * PADDING));
    startOver.setOnAction((event -> startGameOver(scene)));

    root.getChildren().addAll(nextTurn, startOver);
    gamePanel = new PlayerPanel(gameBoard.getPlayers(), gamePieces, scene, this.language, gameType);
    root.getChildren().add(gamePanel);
  }

  private void startGameOver(Scene scene) {
    Group startRoot = new Group();
    scene.setRoot(startRoot);
    SelectionScreen startScreen = new SelectionScreen();
    BackgroundHandler background = new BackgroundHandler("selection");
    startRoot.getChildren().add(background);
    startRoot.getChildren().add(startScreen);
    for (String game : new ButtonGameTypeFactory().getGameTypes()) {
      startScreen.addActions(scene, game);
    }
    startScreen.languagePopUp();
  }

  private List<PieceView> createPieces(List<String> pieces, VisualBoard board) {
    List<PieceView> pieceList = new LinkedList<>();
    for (String pieceName : pieces) {
      System.out.println(pieceName);
      PieceView nextPiece = Piece.findPiece(pieceName);
      nextPiece.setUpBoard(board);
      pieceList.add(nextPiece);
    }
    return pieceList;
  }


  private void goToNextPlayer() {
    if (gamePanel.getCurrentPlayer().getWealth() < 0) {
      CSVLoader message = new CSVLoader("messageToMortgage", language);
      ViewComponents.createConfirmPopUp(message.getValues());
    } else {
      gamePanel.nextPlayer();
    }
  }


  public Tile getTileFromModel(int i) {
    return gameBoard.getMyBoard().getBoard().get(i);
  }

  public VisualTile getTileFromVisual(int i) {
    return visualBoard.getVisualTiles().get(i);
  }

}
