package ooga.model;

import ooga.configuration.CSVLoader;
import ooga.configuration.CardFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The purpose of this class it to create a deck of cards and it is used when a player lands on the chance or community
 * chest tiles
 * @author Muazzam Khan
 */
public class DeckOfCards {

  private final int CHANCE = 0;

  private int stackType;
  private Map<String, int[]> availableCards;
  private Map<String, int[]> usedCards = new HashMap<>();
  private Map<String, int[]> topCard = new HashMap<>();
  CardFactory factory;

  /**
   * constructor creates a deck of cards of a certain type
   * @param stackType type of stack (either community chest or chance)
   * @param gameType the type of game being played
   */
  public DeckOfCards(int stackType, String gameType) {
    String language = (new CSVLoader("languageChoice", "")).getValues().get(0);
    factory = new CardFactory(gameType, language);
    if (stackType == CHANCE) {
      availableCards = factory.getChanceCardMethodsAndRelevantNumbers();
    } else {
      availableCards = factory.getCommunityCardMethodsAndRelevantNumbers();
    }
    this.stackType = stackType;
    updateTopCard();
  }

  private void updateTopCard() {
    if (availableCards.size() != 0) {
      int freshStackSize = availableCards.size();
      int cardChoice = (int) (Math.random() * freshStackSize);
      Set<String> availableMethods = availableCards.keySet();
      String chosenMethod = availableMethods.iterator().next(); //default
      int index = 0;
      for (String method : availableMethods) {
        if (cardChoice == index) {
          chosenMethod = method; //random card
          break;
        }
        index++;
      }
      int[] methodNumbers = availableCards.get(chosenMethod);
      availableCards.remove(chosenMethod);
      usedCards.putIfAbsent(chosenMethod, methodNumbers);
      topCard.clear();
      topCard.putIfAbsent(chosenMethod, methodNumbers);

      if (freshStackSize == 1) {
        refreshAvailableStack();

      }
    }
  }

  private void refreshAvailableStack() {
    availableCards = usedCards;
    usedCards.clear();
  }

  public Map<String, int[]> getTopCard() {
    Map<String, int[]> card = new HashMap<>();
    String method = topCard.keySet().iterator().next();
    card.put(method, topCard.get(method));
    updateTopCard();
    return card;
  }

  public int getStackType() {
    return stackType;
  }

  public CardFactory getCardFactory() {
    return factory;
  }
}
