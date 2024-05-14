package it.polimi.ingsw.gc01.network.actions;

import it.polimi.ingsw.gc01.controller.MainController;
import it.polimi.ingsw.gc01.network.VirtualView;

public abstract class Action {
    private String playerName;

    /**
     * Create a new action
     *
     * @param playerName the name of the player who is creating the action
     */
    public Action(String playerName){
        this.playerName = playerName;
    }

    /**
     *
     * @return the name of the player who is trying to make an action
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * execute the action
     */
    public void execute(){
    }

}
