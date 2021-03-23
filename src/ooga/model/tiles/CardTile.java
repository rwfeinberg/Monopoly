package ooga.model.tiles;

import ooga.configuration.CardFactory;
import ooga.model.DeckOfCards;
import ooga.model.Player;
import ooga.model.actions.Action;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * @author Muazzam Khan Noorpuri
 * Purpose - The CardTile.java class represents the card tiles that a player can land upon in the
 * backend.
 * Assumptions - No general Tile will be created. Must use an extension
 * Dependencies - Tile.java
 */
public class CardTile extends Tile {

  private static final int UNAVAILABLE = 3;
  private static final String CHANCE = "Chance";
  private static final String COMMUNITY = "Community";
  private static final String ACTION_PACKAGE = "ooga.model.actions.";
  private String popupMessage = "No Card Message";

  private List<Player> currentPlayers;
  private CardFactory cardFactory;
  private DeckOfCards deck;
  private Player currentPlayer;
  private String cardResourcesPath;

  /**
   * Constructor creates an extension of Tile and sets the deck of cards associated with it. Also
   * set the path resource path for the relevant game type
   * @param tileName
   * @param color
   * @param inputDeck
   */
  public CardTile(String tileName, String color, DeckOfCards inputDeck) {
    super(tileName, color, UNAVAILABLE);
    deck = inputDeck;
    setResourcesPath();
  }

  /**
   * Picks a card for the player when they land on this tile and calls further actions
   * @param p
   * @return whether further steps are needed (e.g. input from the player)
   */
  @Override
  public boolean landedUpon(Player p) {
    currentPlayer = p;
    Map<String, int[]> topCard = deck.getTopCard();
    String methodName = topCard.keySet().iterator().next();
    int[] parameters = topCard.get(methodName);
    String message = "Blank card!  Sucks for you!";
    this.setFurtherStepNeeded(false);
    try {
      Action action = (Action) Class.forName(ACTION_PACKAGE + methodName)
          .getDeclaredConstructors()[0]
          .newInstance(parameters[0], parameters[1], currentPlayers, currentPlayer,
              cardResourcesPath);
      boolean result = action.performAction();
      popupMessage = action.toString();
      this.setFurtherStepNeeded(result);
      return result;
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
      popupMessage = message;
    }
    return false;
  }

  /**
   * set the current list of players
   * @param all
   */
  public void setAllPlayers(List<Player> all) {
    currentPlayers = all;
  }

  /**
   * @return current player on tile
   */
  public List<Player> getCurrentPlayersForTesting() {
    return currentPlayers;
  }

  /**
   * sets the Factory for this card tile
   * @param factory
   */
  public void setCardFactory(CardFactory factory) {
    cardFactory = factory;
    setResourcesPath();
  }

  private void setResourcesPath() {
    cardFactory = deck.getCardFactory();
    cardResourcesPath = cardFactory.getGameType().toLowerCase() + "/";
    cardResourcesPath =
        cardResourcesPath + (deck.getStackType() == 0 ? cardFactory.getGameType() + CHANCE
            : cardFactory.getGameType() + COMMUNITY);
    cardResourcesPath = cardResourcesPath + cardFactory.getLanguage();
  }

  /**
   * set the tile's current pop up messages that will be displayed to the user
   * @param p
   */
  @Override
  public void setPopUpStrings(Player p) {
    this.getPopUpStrings().clear();
    this.getPopUpStrings().add(popupMessage);
    this.getPopUpStrings().add("OK");
  }

  /**
   * @return 0 since the CardTile cannot be bought
   */
  @Override
  public int getPrice() {
    return 0;
  }
}
