package ooga.model.tiles;

import java.util.List;
import ooga.configuration.CSVLoader;
import ooga.model.Player;

/**
 * @author Muazzam Khan Noorpuri
 * Purpose - The TaxTile.java class represents the tax tile that a player can land upon in the
 * backend.
 * Assumptions - No general Tile will be created. Must use an extension
 * Dependencies - Tile.java
 */
public class TaxTile extends Tile {

  private static final int UNAVAILABLE = 3;
  private int price;
  private final List<String> messages;

  /**
   * Constructor creates an extension of Tile and sets the pop message for when landed upon
   * @param tileName
   * @param color
   */
  public TaxTile(String tileName, String color) {
    super(tileName, color, UNAVAILABLE);
    String language = (new CSVLoader("languageChoice", "")).getValues().get(0);
    this.messages = (new CSVLoader("taxTileClassMessages", language)).getValues();
  }

  /**
   * tax the player the relevant amount based on the type of tax tile
   * @param p
   * @return no further action needed
   */
  @Override
  public boolean landedUpon(Player p) {
    if (this.getName().equals("Income Tax")) {
      price = (int) Math.min(200, p.getWealth() * 0.1);
    } else { //name is "Luxury Tax"
      price = 75;
    }
    executeTax(p);
    return false;
  }

  private void executeTax(Player p) {
    if (price > p.getNetWorth()) {
      p.setAlive(false);
      //TODO: create popup saying the player is bankrupted and has lost
    }
    if (price <= p.getWealth()) {
      p.editWealth(price * -1);
    }
    //TODO: create popup if wealth is negative prompting user to mortgage
  }

  /**
   * Set the tile's current pop up messages that will be displayed to the user
   * @param p
   */
  @Override
  public void setPopUpStrings(Player p) {
    this.getPopUpStrings().clear();
    this.getPopUpStrings()
        .add(messages.get(0) + this.getName() + messages.get(1) + this.getPrice() + ".");
    this.getPopUpStrings().add("OK");
  }

  /**
   * @return tax value of the tile
   */
  @Override
  public int getPrice() {
    return price;
  }
}
