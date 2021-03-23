package ooga.model;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DieTest {
  private Die testDie = new Die();

  @Test
  void testRollDie(){
    int randomNumber = testDie.rollDie();
    assertTrue(randomNumber>=1 && randomNumber<=6);
  }
}
