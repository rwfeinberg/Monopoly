package ooga.model;

/**
 * This class models the behavior of a Die and is solely responsible for outputting a random number
 * @author Muazzam Khan
 */
public class Die {

  public Die() {
  }

  /**
   * @return a random number between 1 and 6
   */
  public int rollDie() {
    int roll = (int) (Math.random() * 6) + 1;
    return roll;
  }
}
