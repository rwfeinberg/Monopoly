package ooga.exceptions;

/**
 * The purpose of this class is to provide a unique RuntimeException in situations in which the 
 * exception is caused by a bad file path
 * @author wyattfocht
 */
public class FilePathException extends RuntimeException {

  public FilePathException(String message) {
    super(message);
  }

  public FilePathException(String message, Throwable cause) {
    super(message, cause);
  }

}
