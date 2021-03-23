package presentation1;

import java.util.List;

/**
 * This purpose of this interface is to facilitate all appropriate logic when it comes to reading information/data
 * values from various files.  This additionally involves cleaning, processing, and reformating data
 * as necessary
 */
public interface DataReader {

  /**
   * The purpose of this method is to read the various rules of a basic Monopoly game (starting
   * balance, losing balance, turns spent in jail, house/hotel multipliers, etc...) from a
   * file and assign them to instance variables
   */
  public void readGameRules();

  /**
   * This method is a getter method for an int that represents the amount of money that a player
   * should have at the beginning of a game.
   * @return int representing the starting balance of a player
   */
  public int getStartingBalance();

  /**
   * This method is a getter method for an int that represents the amount of money that would warrant
   * a player to lose the game.
   * @return int representing the losing balance of a player
   */
  public int getLosingBalance();

  /**
   * This method is a getter method for an int that represents the amount of money gained from a
   * player passing the Go tile
   * @return int representing the amount of money earned from passing the Go tile
   */
  public int getMoneyGainedPassingGo();

  /**
   * This method is a getter method for an int that represents the number of turns an individual
   * must spend in jail
   * @return int representing the amount of time a player must spend in jail
   */
  public int getJailSentence();

  /**
   * This method is a getter method for a list that contains all the names for each location/property
   * @return List<String> that contains every name for each tile in the current board
   */
  public List<String> getTileNames();

  /**
   * This method is a getter method for an int representing the increase in value of a property
   * with the addition of a new house.
   * @return int representing the amount increase in a property's value with the addition of a new
   * house
   */
  public int getHouseMultiplier();

  /**
   * This method is a getter method for an int representing the increase in value of a property
   * with the addition of a new hotel.
   * @return int representing the amount increase in a property's value with the addition of a new
   * hotel
   */
  public int getHotelMultiplier();
}
