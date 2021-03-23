package ooga.model;

import ooga.model.tiles.Tile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class BoardTest {
    private Board board = new Board("Standard");

    @Test
    void testBoardSize(){
        assertEquals(41, board.getBoard().size());
    }

    @Test
    void testNextProperty(){
        Tile actual = board.getBoard().get(0).next();
        Tile expected = board.getBoard().get(1);
        assertSame(expected, actual);
    }

}