package ooga;

import plan.usecases.dummyclasses.Player;
import plan.usecases.dummyclasses.PlayerPanelBasic;

public class VisualizationExampleAPI {
    private int value = 0;
    /**
     * Use case describing when the balance of a Player increases,
     * This case solely demonstrates the visual counter of the Player's balance updating.
     */
    public void updateMoneyUseCase() {
        /**
         * The player is defined at the beginning of the game.
         */
        Player p = new Player();

        /**
         * Let's say for example, Player has landed on income tax,
         * then after all of the backend processing occurs to update the Player's actual balance,
         * the visual counter updates as follows:
         *
         *  (Happens inside of Player.java's method that updates balance, which has parameter "value")
         */
        PlayerPanelBasic panel = this.getPanel();
        panel.updateMoney(value);
    }

    private PlayerPanelBasic getPanel() {
        return null;
    }
}
