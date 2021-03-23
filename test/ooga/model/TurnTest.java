package ooga.model;

import ooga.model.tiles.Tile;
import org.junit.jupiter.api.Test;
import util.DukeApplicationTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

class TurnTest extends DukeApplicationTest {

    Board board = new Board("Standard");
    Player player = new Player(board.getBoard().get(0), "Sylvie", "Standard");

    @Test
    void testTurn(){
        Die die = new Die();
        Turn turn = new Turn(player, die.rollDie(), die.rollDie());
        assertNotSame(board.getBoard().get(0), player.getLocation());
    }

    @Test
    void testJailTurn(){
        player.setSuspended(1);
        player.setGetOutOfJail(false);
        Turn turn = new Turn(player, 6, 4);
        List<String> expectedStrings = new ArrayList<>();
        expectedStrings.add("Guess you're staying here!");
        expectedStrings.add("OK");
        assertEquals(expectedStrings, turn.getPopUpStrings());
    }

    @Test
    void testPopUpStrings(){
        List<String> expectedStrings = new ArrayList<>();
        expectedStrings.add("Buy Mediterranean Avenue for $60?");
        expectedStrings.add("Yes");
        expectedStrings.add("No");
        Turn turn = new Turn(player, 0, 1);
        assertEquals(expectedStrings, player.getLocation().getPopUpStrings());
    }

    @Test
    void testHandleCurrentProperty(){
        ArrayList<Tile> empty = new ArrayList<>();
        Die die = new Die();
        Turn turn = new Turn(player, die.rollDie(), die.rollDie());
        assertNotSame(empty, player.getOwnedTiles());
    }


}