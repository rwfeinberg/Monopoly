package ooga.visualization;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import ooga.Main;
import ooga.configuration.CSVLoader;
import ooga.model.Player;
import ooga.model.tiles.Property;
import ooga.model.tiles.Tile;

import java.util.List;

/**
 * @author Ryan Feinberg
 * @author Catherine Livingston
 * <p>
 * Purpose - The ViewComponents.java class consists purely of static methods, and is used when a visual element needs to be created (a popup, 
 * dropdown, rectangle, animation, etc.)
 * <p>
 * Assumptions - The constants below should not change, except for FRAME_LIMIT, which when increases makes the dice animation longer.
 * <p>
 * Dependencies - This class depends on Main.java for constants, CSVLoader, ButtonGameTypeFactory, Die, Player, Turn, Action, Tile, PlayerButtonPanel, 
 * PlayerMoneyPanel, and PlayerNamePanel.java
 */
public class ViewComponents {

  public static final int BUY_HOUSE = 2;
  public static final int FRAME_LIMIT = 10;
  private static final int SELL_HOUSE = 3;
  private static final int MORTGAGE = 4;
  private static final int UNMORTGAGE = 5;
  private static final int SWAP_PLAYERS = 6;
  private static int frame = 0;

  /**
  * This method creates a new text label.
  *
  * @param id - id to find element
  * @param text - Display text
  * @param xPos - Desired x position
  * @param yPos - Desired y position
  */
  public static Label createLabel(String id, String text, int xPos, int yPos) {
    Label createdLabel = new Label();
    createdLabel.setId(id);
    createdLabel.setText(text);
    createdLabel.setLayoutX(xPos);
    createdLabel.setLayoutY(yPos);
    return createdLabel;
  }

  /**
  * This method creates a new button with a text label.
  *
  * @param id - id to find element
  * @param text - Display text
  * @param xPos - Desired x position
  * @param yPos - Desired y position
  */
  public static Button createButton(String id, String text, int xPos, int yPos) {
    Button createdButton = new Button();
    createdButton.setId(id);
    createdButton.setText(text);
    createdButton.setLayoutX(xPos);
    createdButton.setLayoutY(yPos);
    return createdButton;
  }

  /**
  * This method creates a new button with a text label that should change language.
  *
  * @param id - id to find element
  * @param text - Display text
  * @param xPos - Desired x position
  * @param yPos - Desired y position
  * @param language - game language
  */
  public static Button createButton(String id, String text, int xPos, int yPos, String language) {
    Button createdButton = new Button();
    if (!language.equals("English")) {
      createdButton.setText(PropertiesLoader.getTranslation(text));
    } else {
      createdButton.setText(text);
    }
    createdButton.setId(id);
    createdButton.setLayoutX(xPos);
    createdButton.setLayoutY(yPos);
    return createdButton;
  }

  /**
  * This method creates a new property dropdown for the PlayerPanel
  *
  * @param id - id to find element
  * @param pList - list of tiles to add to the dropdown
  * @param language - game language
  */
  public static ComboBox createPropertiesDropdown(String id, List<Tile> pList, String language) {
    ComboBox<Tile> menu = new ComboBox<>();
    for (Tile t : pList) {
      menu.getItems().add(t);
    }
    menu.setId(id);
    List<String> messages = (new CSVLoader("viewComponentMessages", language).getValues());
    menu.setPromptText(messages.get(0));
    return menu;
  }

  /**
  * This method creates a new CSS dropdown, to select new styles in game.
  *
  * @param cssList - list of css files to add to the dropdown, by filename
  * @param root - Scene's root
  * @param backgroundHandler - pointer to the current backgroundHandler in the root.
  * @param language - game language
  */
  public static VBox createCSSDropdown(List<String> cssList, Group root,
      BackgroundHandler backgroundHandler, String language) {
    VBox result = new VBox();
    result.setSpacing(5);
    List<String> messages = (new CSVLoader("viewComponentMessages", language).getValues());
    Label label = new Label(messages.get(1));
    label.setId("StyleLabel");

    ComboBox<String> dropdown = new ComboBox<>();
    dropdown.setValue(cssList.get(0));
    dropdown.getItems().addAll(cssList);
    dropdown.getSelectionModel()
        .selectedItemProperty()
        .addListener((o, old, nw) -> {
          changeStyle(root, nw, backgroundHandler);
        });
    dropdown.setId("StyleDropdown");

    result.getChildren().addAll(label, dropdown);
    result.setLayoutX(Main.WIDTH - 350);
    result.setLayoutY(15);
    return result;
  }

  private static void changeStyle(Group root, String sheet, BackgroundHandler backgroundHandler) {
    Scene s = root.getScene();
    s.getStylesheets().clear();
    s.getStylesheets().add(sheet);

    backgroundHandler.setBackground(sheet.replace(".css", ""));
  }

  /**
  * This method creates a new rectangle with desired position, size, and color.
  *
  * @param width - desired width
  * @param height - desired height
  * @param xPos - Desired x position
  * @param yPos - Desired y position
  * @param fill - desired color
  * @param stroke - desired stroke
  */
  public static Rectangle createRectangle(int width, int height, int xPos, int yPos, Paint fill,
      Paint stroke) {
    Rectangle createdRectangle = new Rectangle();
    createdRectangle.setFill(fill);
    createdRectangle.setStroke(stroke);
    createdRectangle.setLayoutX(xPos);
    createdRectangle.setLayoutY(yPos);
    createdRectangle.setWidth(width);
    createdRectangle.setHeight(height);
    createdRectangle.setOpacity(0.6);
    return createdRectangle;
  }

  /**
  * This method creates the popUp that appears when the BuyHouse button is clicked on.
  *
  * @param ownedTiles - list of tiles owned by the currentPlayer
  * @param language - game language
  */
  public static ChoiceDialog<String> createBuyHousePopUp(List<Tile> ownedTiles, String language) {
    ChoiceDialog<String> result = new ChoiceDialog<>();
    result.setHeaderText(null);
    List<String> messages = (new CSVLoader("viewComponentMessages", language).getValues());
    result.setContentText(messages.get(BUY_HOUSE));
    for (Tile t : ownedTiles) {
      if (t instanceof Property) {
        result.getItems().add(t.getName());
      }
    }
    return result;
  }

  /**
  * This method creates the popUp that appears when the SellHouse button is clicked on.
  *
  * @param ownedTiles - list of tiles owned by the currentPlayer that have houses on them
  * @param language - game language
  */
  public static ChoiceDialog<String> createSellHousePopUp(List<Property> houseTiles,
      String language) {
    ChoiceDialog<String> result = new ChoiceDialog<>();
    result.setHeaderText(null);
    List<String> messages = (new CSVLoader("viewComponentMessages", language).getValues());
    result.setContentText(messages.get(SELL_HOUSE));
    for (Property p : houseTiles) {
      result.getItems().add(p.getName());
    }
    return result;
  }

  /**
  * This method creates the popUp that appears when the Mortgage button is clicked on.
  *
  * @param ownedTiles - list of tiles owned by the currentPlayer that can be mortgaged
  * @param language - game language
  */
  public static ChoiceDialog<String> createMortgagePopUp(List<Tile> tiles, String language) {
    ChoiceDialog<String> result = new ChoiceDialog<>();
    result.setHeaderText(null);
    List<String> messages = (new CSVLoader("viewComponentMessages", language).getValues());
    result.setContentText(messages.get(MORTGAGE));
    for (Tile t : tiles) {
      result.getItems().add(t.getName());
    }
    return result;
  }

  /**
  * This method creates the popUp that appears when the Unmortgage button is clicked on.
  *
  * @param ownedTiles - list of tiles owned by the currentPlayer that are already mortgaged
  * @param language - game language
  */
  public static ChoiceDialog<String> createUnmortgagePopUp(List<Tile> tiles, String language) {
    ChoiceDialog<String> result = new ChoiceDialog<>();
    result.setHeaderText(null);
    List<String> messages = (new CSVLoader("viewComponentMessages", language).getValues());
    result.setContentText(messages.get(UNMORTGAGE));
    for (Tile t : tiles) {
      result.getItems().add(t.getName());
    }
    return result;
  }

  /**
  * This method creates the popUp that appears when a notification needs to be displayed.
  *
  * @param props - List of strings: 1 is message, the rest are the buttons required to appear. 
  */
  public static boolean createConfirmPopUp(List<String> props) {
    Alert alert = new Alert(AlertType.CONFIRMATION, props.get(0), ButtonType.OK);
    alert.showAndWait();
    return true;
  }

  /**
  * This method creates the popUp that appears when a choice needs to be displayed for the player to choose.
  *
  * @param props - List of strings: 1 is message, the rest are the buttons required to appear. 
  */
  public static boolean createChoicePopUp(List<String> props) {
    Alert alert = new Alert(AlertType.CONFIRMATION, props.get(0), ButtonType.YES, ButtonType.NO);
    alert.showAndWait();
    return alert.getResult() == ButtonType.YES;
  }

  /**
  * This method creates the dice animation that occurs when the dice roll button is clicked.
  *
  * @param diceBox - HBox where the dice images are contained in
  * @param roll1 - 1st roll value
  * @param roll2 - 2nd roll value
  */
  public static void createDiceAnimation(HBox diceBox, int roll1, int roll2) {
    new AnimationTimer() {
      @Override
      public void handle(long l) {
        ImageView d1 = new ImageView(ImageReader.getDiceImage((int) (Math.random() * 6) + 1));
        ImageView d2 = new ImageView(ImageReader.getDiceImage((int) (Math.random() * 6) + 1));
        diceBox.getChildren().clear();
        diceBox.getChildren().addAll(d1, d2);
        if (frame > FRAME_LIMIT) {
          frame = 0;
          diceBox.getChildren().clear();
          ImageView final1 = new ImageView(ImageReader.getDiceImage(roll1));
          ImageView final2 = new ImageView(ImageReader.getDiceImage(roll2));
          diceBox.getChildren().addAll(final1, final2);
          this.stop();
        }
        frame++;
      }
    }.start();
  }

  /**
  * This method creates the popUp that appears when the SwapPlayers button is clicked on.
  *
  * @param listToDisplay - list of players besides currentPlayer
  * @param language - game language
  */
  public static ChoiceDialog<String> createSwapPopUp(List<Player> listToDisplay, String language) {
    ChoiceDialog<String> result = new ChoiceDialog<>();
    result.setHeaderText(null);
    List<String> messages = (new CSVLoader("viewComponentMessages", language).getValues());
    result.setContentText(messages.get(SWAP_PLAYERS));
    for (Player p : listToDisplay) {
      result.getItems().add(p.getMyName());
    }
    result.setSelectedItem(listToDisplay.get(0).getMyName());
    return result;
  }

  /**
  * This method creates updates the necessary piece location and PlayerPanel children that need to change if a purchase is made, a sell is made, a 
  * swap happens, or something similar.
  *
  * @param currentPlayer - pointer to the current Player
  */
  public static void updatePanel(Player currentPlayer) {
    PlayerPanel playerPanel = currentPlayer.getPanel();
    playerPanel.updatePieceLocation();
    playerPanel.getNamePanel().refreshPropertyDropdown(currentPlayer);
    playerPanel.getMoneyPanel().updateBalance(currentPlayer);
    playerPanel.getButtonPanel().refreshButtons(currentPlayer);
  }
}
