package it.polimi.ingsw.gc01.network.rmi.actions;

/**
 * General base of an action executed by the RMI Server
 */
public abstract class Action {
    /**
     * Name of the player
     */
    private final String playerName;

    /**
     * Create a new action
     *
     * @param playerName the name of the player who is creating the action
     */
    public Action(String playerName) {
        this.playerName = playerName;
    }

    /**
     * @return the name of the player who is trying to make an action
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Execute the action
     */
    public abstract void execute();
}