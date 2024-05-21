package it.polimi.ingsw.gc01.network.actions;

import it.polimi.ingsw.gc01.network.VirtualView;

public abstract class MainAction extends Action {
    private final VirtualView client;

    /**
     * Create a new action for the MainController
     *
     * @param playerName     the name of the player who is creating the action
     * @param client         the client of the player who is creating the action
     */
    public MainAction(String playerName, VirtualView client) {
        super(playerName);
        this.client = client;
    }

    /**
     * @return the client
     */
    public VirtualView getClient() {
        return client;
    }
}