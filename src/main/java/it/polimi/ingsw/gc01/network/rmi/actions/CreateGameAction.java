package it.polimi.ingsw.gc01.network.rmi.actions;

import it.polimi.ingsw.gc01.controller.MainController;
import it.polimi.ingsw.gc01.network.VirtualView;

/**
 * Action to create a new game
 */
public class CreateGameAction extends MainAction {
    /**
     * Create a new CreateGameAction
     *
     * @param playerName of the player who is creating the action
     * @param client     reference to the client
     */
    public CreateGameAction(String playerName, VirtualView client) {
        super(playerName, client);
    }

    /**
     * Execute the action
     */
    @Override
    public void execute() {
        MainController controller = MainController.getInstance();
        controller.createGame(getPlayerName(), getClient());
    }
}