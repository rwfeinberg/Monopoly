package ooga.visualization.PlayerPanelComponents;

import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import ooga.model.Player;
import ooga.model.tiles.Property;
import ooga.model.tiles.Tile;
import ooga.visualization.PieceView;
import ooga.visualization.ViewComponents;

import java.util.List;

/**
 * @author Ryan Feinberg
 * <p>
 * Purpose - The PlayerNamePanel.java class is the class that holds the name, piece, and property dropdown menu for the current player in PlayerPanel.
 * <p>
 * Assumptions - This class extends HBox.
 * <p>
 * Dependencies - This class depends on Player, Property, Tile, PieceView, and ViewComponents.java.
 */
public class PlayerNamePanel extends HBox {

  private Label nameLabel;
  private ImageView pieceView;
  private ComboBox<String> propertyDropdown;
  private List<Tile> propertyList;

  /**
   * Constructor, fills HBox with name, piece, and dropdown
   *
   * @param currentPlayer - pointer to the current player
   * @param currentPiece - pointer to the current player's piece selection
   * @param language - game language
   */
  public PlayerNamePanel(Player currentPlayer, PieceView currentPiece, String language) {
    propertyList = currentPlayer.getOwnedTiles();

    this.setSpacing(20);
    this.setAlignment(Pos.CENTER_LEFT);
    nameLabel = ViewComponents.createLabel("NameLabel", currentPlayer.getMyName(), 0, 0);

    pieceView = new ImageView();
    setUpImagePiece(currentPiece);

    propertyDropdown = ViewComponents
        .createPropertiesDropdown("PropertyDropdown", propertyList, language);
    propertyDropdown.getSelectionModel()
        .selectedItemProperty()
        .addListener((o, old, nw) -> System.out.println(nw));

    this.getChildren().addAll(nameLabel, pieceView, propertyDropdown);
  }

  /**
  * This method initializes the piece display
  *
  * @param currentPiece - pointer to the player's piece
  */
  public void setUpImagePiece(PieceView currentPiece) {
    pieceView.setImage(currentPiece.getImage());
    int PIECE_SIZE = 60;
    pieceView.setFitWidth(PIECE_SIZE);
    pieceView.setFitHeight(PIECE_SIZE);
  }

  /**
  * This method updates the name label
  *
  * @param currentPlayer - pointer to the current player
  */
  public void changeName(Player currentPlayer) {
    this.nameLabel.setText(currentPlayer.getMyName());
  }

  /**
  * This method adds a property to the property dropdown when one is purchased by the current player.
  *
  * @param t - tile purchased
  */
  public void addProperty(Tile t) {
    this.propertyDropdown.getItems().add(t.getName());
  }

  /**
  * This method updates the name of the selected property in the dropdown to be mortgaged.
  * Adds a "MORTGAGED" to the end of the name
  *
  * @param propertyName - name of the property selected
  * @param player - pointer to the current player
  */
  public void mortgageProperty(String propertyName, Player player) {
    List<String> oldProperties = new ArrayList<>(propertyDropdown.getItems());
    propertyDropdown.getItems().clear();
    for (int i = 0; i < player.getOwnedTiles().size(); i++) {
      Tile property = player.getOwnedTiles().get(i);
      if (property.getName().equals(propertyName)) {
        propertyDropdown.getItems().add(property.getName() + ", MORTGAGED");
      } else {
        for (String oldProperty : oldProperties) {
          if (oldProperty.contains(property.getName())) {
            propertyDropdown.getItems().add(oldProperty);
          }
        }
      }
    }
  }

  /**
  * This method updates the name of the selected property in the dropdown to be unmortgaged.
  * Removes a "MORTGAGED" from the end of the name
  *
  * @param propertyName - name of the property selected
  * @param player - pointer to the current player
  */
  public void unmortgageProperty(String propertyName, Player player) {
    List<String> oldProperties = new ArrayList<>(propertyDropdown.getItems());
    propertyDropdown.getItems().clear();
    for (int i = 0; i < player.getOwnedTiles().size(); i++) {
      Tile property = player.getOwnedTiles().get(i);
      if (property.getName().equals(propertyName)) {
        propertyDropdown.getItems().add(property.getName());
      } else {
        for (String oldProperty : oldProperties) {
          if (oldProperty.contains(property.getName())) {
            propertyDropdown.getItems().add(oldProperty);
          }
        }
      }
    }
  }


  /**
  * This method decreases the house count located next to the name of a property in the dropdown. Removes the entire addition if the housecount = 0
  *
  * @param propertyName - name of the property selected
  * @param player - pointer to the current player
  */
  public void decreaseHouseCount(String propertyName, Player player) {
    List<String> oldProperties = new ArrayList<>(propertyDropdown.getItems());
    propertyDropdown.getItems().clear();
    for (int i = 0; i < player.getOwnedTiles().size(); i++) {
      Tile property = player.getOwnedTiles().get(i);
      String[] nameList = oldProperties.get(i).split(",");
      if (nameList[0].equals(propertyName)) {
        decreaseCount((Property) property, nameList);
      } else {
        for (String oldProperty : oldProperties) {
          if (oldProperty.contains(property.getName())) {
            propertyDropdown.getItems().add(oldProperty);
          }
        }
      }
    }
  }

  private void decreaseCount(Property property, String[] nameList) {
    String newName;
    if (property.getHouseCount() <= 0) {
      newName = nameList[0];
    } else {
      newName = nameList[0] + ", " + ((property.getHouseCount())) + "H";
    }
    propertyDropdown.getItems().add(newName);
  }

  /**
  * This method increases the house count located next to the name of a property in the dropdown. 
  * Changes to: "1 Hotel" if the housecount goes above 4
  *
  * @param propertyName - name of the property selected
  * @param player - pointer to the current player
  */
  public void increaseHouseCount(String propertyName, Player player) {
    List<String> oldProperties = new ArrayList<>(propertyDropdown.getItems());
    propertyDropdown.getItems().clear();
    for (int i = 0; i < player.getOwnedTiles().size(); i++) {
      Tile property = player.getOwnedTiles().get(i);
      String[] nameList = oldProperties.get(i).split(",");
      if (nameList[0].equals(propertyName)) {
        increaseCount((Property) property, nameList);
      } else {
        for (String oldProperty : oldProperties) {
          if (oldProperty.contains(property.getName())) {
            propertyDropdown.getItems().add(oldProperty);
          }
        }
      }
    }
  }

  private void increaseCount(Property property, String[] nameList) {
    String newName;
    if (nameList.length == 1) {
      newName = property.getName() + ", " + 1 + "H";
    } else {
      if (property.getHouseCount() > 4) {
        newName = nameList[0] + ", " + 1 + " Hotel";
      } else {
        newName = nameList[0] + ", " + ((property.getHouseCount())) + "H";
      }
    }
    propertyDropdown.getItems().add(newName);
  }

  /**
  * This method updates the property dropdown to the new player when next turn is clicked.
  *
  * @param player - pointer to the new current player
  */
  public void refreshPropertyDropdown(Player player) {
    propertyDropdown.getItems().clear();
    for (Tile t : player.getOwnedTiles()) {
      if (t.getStatus() == Player.MORTGAGED) {
        propertyDropdown.getItems().add(t.getName() + ", MORTGAGED");
        continue;
      }
      if (t instanceof Property) {
        String newName;
        if (((Property) t).getHotelCount() == 1) {
          newName = t.getName() + ", " + 1 + "Hotel";
        } else if (((Property) t).getHouseCount() != 0) {
          newName = t.getName() + ", " + ((((Property) t).getHouseCount())) + "H";
        } else {
          newName = t.getName();
        }
        propertyDropdown.getItems().add(newName);
      } else {
        propertyDropdown.getItems().add(t.getName());
      }
    }
  }
}
