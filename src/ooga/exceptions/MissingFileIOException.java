package ooga.exceptions;

import java.io.IOException;

/**
 * The purpose of this class is to provide a unique IOException in situations in which the
 * exception is caused by a missing file
 * @author wyattfocht
 */
public class MissingFileIOException extends IOException {

  public MissingFileIOException(String message) {
    super(message);
  }

  public MissingFileIOException(String message, Throwable cause) {
    super(message, cause);
  }

}
