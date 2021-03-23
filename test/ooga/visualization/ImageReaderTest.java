package ooga.visualization;
import ooga.exceptions.FilePathException;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;
import static org.junit.jupiter.api.Assertions.*;

public class ImageReaderTest extends DukeApplicationTest {


  @Test
  public void testTileImageIsFound() {
    assertNotEquals(null, ImageReader.findTileImage("tileGo"));
  }

  @Test
  public void testTileImageIsNotFound() {
    Exception exception = assertThrows(FilePathException.class, () -> ImageReader.findTileImage("incorrect"));
    assertEquals(exception.getMessage(), "Did not find image at /resources/images/incorrect.jpeg");
  }

  @Test
  public void testBackgroundImageIsFound() {
    assertNotEquals(null, ImageReader.findBackgroundImage("WoodTable"));
  }

  @Test
  public void testBackgroundImageIsNotFound() {
    Exception exception = assertThrows(FilePathException.class, () -> ImageReader.findBackgroundImage("incorrect"));
    assertEquals(exception.getMessage(), "Did not find background image at /resources/images/incorrectBackground.jpeg");
  }

  @Test
  public void testPieceImageIsFound() {
    assertNotEquals(null, ImageReader.getPieceImage("4"));
  }

  @Test
  public void testPieceImageIsNotFound() {
    Exception exception = assertThrows(FilePathException.class, () -> ImageReader.getPieceImage("6"));
    assertEquals(exception.getMessage(), "Did not find piece image at /resources/images/piece6.png");
  }
}
