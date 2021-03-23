package ooga.model;

import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.List;

public abstract class Tile {
  private String name;
  private Tile nextTile;
  private Paint color;
  private int status;
  private int price;
  private List<String> popUpStrings = new ArrayList<>();
  private ArrayList players;

  private final int FOR_SALE = 0;

  public Tile(String tileName, String inputColor, int initialStatus){
    name = tileName;
    color = Paint.valueOf(inputColor);
    status = initialStatus;
    setPopUpStrings();
  }

  public Tile(String tileName, String inputColor, int initialStatus, ArrayList<String> playerList){
    name = tileName;
    color = Paint.valueOf(inputColor);
    status = initialStatus;
    setPopUpStrings();
    players = playerList;
  }

  public void setNextTile(Tile next){
    nextTile = next;
  }

  public Tile next(){
    return nextTile;
  }

  public abstract boolean landedUpon(Player p);

  public abstract void setPopUpStrings();

  public List<String> getPopUpStrings(){return popUpStrings;}
  /**
   * set the current Tile's status
   * @param newStatus
   */
  public void setStatus(int newStatus){
    status = newStatus;
  }
  public int getStatus(){
    return status;
  }
  public String getName(){
    return name;
  }
  public Paint getColor(){
    return color;
  }
  public int getPrice(){return price; }
}
