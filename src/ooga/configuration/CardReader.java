package ooga.configuration;

import java.util.Map;

/**
 * The purpose of this interface is to provide a framework for the bare minimum functionality
 * that is necessary for the concrete implementation of a Card reading class that is meant
 * to deliver chance/community chest cards and their corresponding parameter values to where it is 
 * needed.
 * @author wyattfocht 
 */
public interface CardReader {

  /**
   * This method is a getter method for a map that contains Community Chest cards (in the form of 
   * Strings) mapped to relevant method-call parameter values (in the form of int[])
   *
   * @return Map<String, int[]> that contains every possible community chest card along with its
   * corresponding array of associated parameters for future execution
   */
  public Map<String, int[]> getCommunityCardMethodsAndRelevantNumbers();

  /**
   * This method is a getter method for a map that contains Chance cards (in the form of
   * Strings) mapped to relevant method-call parameter values (in the form of int[])
   *
   * @return Map<String, int[]> that contains every possible chance card along with its
   * corresponding array of associated parameters for future execution
   */
  public Map<String, int[]> getChanceCardMethodsAndRelevantNumbers();
}
