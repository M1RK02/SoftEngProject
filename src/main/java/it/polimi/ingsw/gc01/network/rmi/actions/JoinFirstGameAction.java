package it.polimi.ingsw.gc01.network.rmi.actions;

import it.polimi.ingsw.gc01.controller.MainController;
import it.polimi.ingsw.gc01.network.VirtualView;

/**
 * Action to join the first available game
 */
public class JoinFirstGameAction extends MainAction {
    /**
     * Create a new JoinFirstGameAction
     * @param playerName of the player who is creating the action
     * @param client reference to the client
     */
    public JoinFirstGameAction(String playerName, VirtualView client) {
        super(playerName, client);
    }

    /**
     * Execute the action
     */
    @Override
    public void execute() {
        MainController controller = MainController.getInstance();
        controller.joinFirstGame(getPlayerName(), getClient());
    }
}