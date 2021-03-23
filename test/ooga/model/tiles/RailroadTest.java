package ooga.model.tiles;

import ooga.configuration.RuleFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RailroadTest {

    @Test
    void initialize() {
        RuleFactory ruleFactory = new RuleFactory("Standard");
        int rent = (int) (ruleFactory.getInitialRentMultiplier()*200);
        assertEquals(60, rent);
    }
}