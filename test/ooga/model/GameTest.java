package ooga.model;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
  private ArrayList<String> names = new ArrayList<>(Arrays.asList("Sylvie", "Muazzam"));
  private Game game = new Game( "Standard", names);


  @Test
  void testGamePlayers(){
    ArrayList<Player> players = game.getPlayers();
    assertEquals("Sylvie", players.get(0).getMyName());
    assertEquals("Muazzam", players.get(1).getMyName());
  }

}
