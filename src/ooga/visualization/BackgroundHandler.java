package ooga.visualization;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import ooga.Main;
import ooga.configuration.CSVLoader;
/**
 * @author Catherine Livingston
 * <p>
 * Purpose - The BackgroundHandler.java class is used to set a background in the scene and create
 * functionality to be able to change the background to an option listed in the dropdown at any time.
 * <p>
 * Assumptions - Files are in correct places.
 * <p>
 * Dependencies - This class depends on PropertiesLoader, ImageReader, and CSVLoader.
 */
public class BackgroundHandler extends ImageView {

  private final int PADDING = 20;
  private final int X_POS = 850;
  private String language;

  public BackgroundHandler(String background) {
    setBackground(background);
  }

  /**
   * method to set or change the background to a new file
   *
   * @param fileName - name of the background
   */
  public void setBackground(String fileName) {
    String imageName = PropertiesLoader.getTranslation(fileName.replace(" ", ""));
    this.setId("Background");
    this.setImage(ImageReader.findBackgroundImage(imageName));
    this.setFitHeight(Main.HEIGHT);
    this.setFitWidth(Main.WIDTH);
    this.setLayoutX(0);
    this.setLayoutY(0);
  }

  /**
   * method to get create the dropdown of options for the background
   *
   * @param language - language to get the options in from CSVLoader
   */
  public ChoiceBox<String> backgroundSelection(String language) {
    this.language = language;
    ChoiceBox<String> backgroundSelection = new ChoiceBox<>();
    backgroundSelection.setId("BackgroundDropdown");
    CSVLoader backgroundOptions = new CSVLoader("backgroundOptions", language);
    backgroundSelection.getItems().addAll(backgroundOptions.getValues());
    backgroundSelection.getSelectionModel().selectedItemProperty().addListener(
        (ov, value, new_value) -> setBackground(new_value));
    backgroundSelection.setLayoutX(X_POS);
    backgroundSelection.setLayoutY(2 * PADDING);
    return backgroundSelection;
  }

  /**
   * method to get create a label above the dropdown options instructing the player
   */
  public Label selectBackgroundLabel() {
    CSVLoader label = new CSVLoader("selectBackground", language);
    return ViewComponents
        .createLabel("SelectBackground", label.getValues().get(0), X_POS, PADDING - 5);
  }

}
