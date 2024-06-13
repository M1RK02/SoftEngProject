package it.polimi.ingsw.gc01.network.rmi.actions;

import it.polimi.ingsw.gc01.controller.MainController;
import it.polimi.ingsw.gc01.network.VirtualView;

/**
 * Action to join a selected game
 */
public class JoinGameAction extends MainAction {
    /**
     * Id of the room to join
     */
    private final String roomId;

    /**
     * Create a new JoinGameAction
     *
     * @param playerName of the player who is creating the action
     * @param client     reference to the client
     * @param roomId     of the room to join
     */
    public JoinGameAction(String playerName, VirtualView client, String roomId) {
        super(playerName, client);
        this.roomId = roomId;
    }

    /**
     * Execute the action
     */
    @Override
    public void execute() {
        MainController controller = MainController.getInstance();
        controller.joinGame(getPlayerName(), getClient(), roomId);
    }
}