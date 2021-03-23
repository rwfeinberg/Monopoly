package ooga.model;
import ooga.model.tiles.Tile;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


class GoTileTest {

    private Board board = new Board( "Standard");
    private Player testPlayer = new Player(board.getBoard().get(0), "Muazzam", "");

    @Test
    void testLandedUpon(){
        testPlayer.movePlayer(40);
        Tile currentTile = testPlayer.getLocation();
        assertEquals("Go", currentTile.getName());
        boolean actual = currentTile.landedUpon(testPlayer);
        assertEquals(false, actual);
    }

    @Test
    void testSetPopUpStrings(){
        testPlayer.movePlayer(40);
        testPlayer.getLocation().setPopUpStrings(testPlayer);
        ArrayList<String> expected = new ArrayList<>();
        expected.add("You hit Go! Collect $200");
        expected.add("OK");
        assertEquals(expected, testPlayer.getLocation().getPopUpStrings());
    }

    @Test
    void testGoTile(){

    }

}