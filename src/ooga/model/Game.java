package ooga.model;

import ooga.model.tiles.CardTile;
import ooga.model.tiles.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * The purpose of this class is to create a game that stores the board and list of players
 * @author Sylvie Mason
 */
public class Game {

  private Board myBoard;
  private ArrayList<Player> players = new ArrayList<>();
  private Tile Go;
  private boolean gameOver = false;

  /**
   * constructor creates a game of a certain type and given a list of names inputted by the user
   * @param gameType type of game being played
   * @param names names of the players that need to be created
   */
  public Game(String gameType, List<String> names) {
    myBoard = new Board(gameType);
    Go = myBoard.getBoard().get(0);
    createPlayers(names, gameType);
    for (Tile tile : myBoard.getBoard()) {
      if (tile instanceof CardTile) {
        ((CardTile) tile).setAllPlayers(players);
      }
    }
  }

  /**
   * generates a list of players based on the user's names that were inputted
   * @param names names of the players
   * @param tilePath type of game being played
   */
  public void createPlayers(List<String> names, String tilePath) {
    for (String name : names) {
      players.add(new Player(Go, name, tilePath));
    }
  }

  public ArrayList<Player> getPlayers() {
    return players;
  }

  public Board getMyBoard() {
    return myBoard;
  }
}
