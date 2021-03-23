package ooga.visualization;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;
import ooga.exceptions.FilePathException;

/**
 * @author Catherine Livingston
 * <p>
 * Purpose - The ImageReader.java class is used to get the images that are wanted to be added to the
 * visualization of the game.
 * <p>
 * Assumptions - Files are in resources/images
 * <p>
 * Dependencies - This class depends on Java's classes..
 */
public class ImageReader {

  public static Image findTileImage(String name) {
    try {
      return new Image(new FileInputStream(
          System.getProperty("user.dir") + "/resources/images/" + name + ".jpeg"));
    } catch (FileNotFoundException e) {
      throw new FilePathException("Did not find image at /resources/images/" + name + ".jpeg", e);
    }
  }

  /**
   * method to get the piece image based on its number assigned in the file name
   *
   * @param num - the number of the piece in its file name
   */
  public static Image getPieceImage(String num) {
    try {
      return new Image(new FileInputStream(
          System.getProperty("user.dir") + "/resources/images/piece" + num + ".png"));
    } catch (FileNotFoundException e) {
      throw new FilePathException(
          "Did not find piece image at /resources/images/piece" + num + ".png", e);
    }
  }

  /**
   * method to get the image for a background
   *
   * @param name - name of the background
   */
  public static Image findBackgroundImage(String name) {
    try {
      return new Image(new FileInputStream(
          System.getProperty("user.dir") + "/resources/images/" + name + "Background.jpeg"));
    } catch (FileNotFoundException e) {
      throw new FilePathException(
          "Did not find background image at /resources/images/" + name + "Background.jpeg", e);
    }
  }

  /**
   * method to get the image for a specific dice roll to show the result of the die
   *
   * @param roll - number that the dice rolled to
   */
  public static Image getDiceImage(int roll) {
    try {
      return new Image(new FileInputStream(
          System.getProperty("user.dir") + "/resources/images/dice" + roll + ".png"));
    } catch (FileNotFoundException e) {
      throw new FilePathException(
          "Did not find piece image at /resources/images/dice" + roll + ".png", e);
    }
  }
}
