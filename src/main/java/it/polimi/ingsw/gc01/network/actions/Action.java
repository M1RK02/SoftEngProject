package it.polimi.ingsw.gc01.network.actions;

import it.polimi.ingsw.gc01.controller.MainController;
import it.polimi.ingsw.gc01.network.VirtualView;

public abstract class Action {
    private String playerName;
    private VirtualView client;
    private MainController mainController;

    /**
     *
     * @return the name of the player who is trying to make an action
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     *
     * @return the mainController
     */
    public MainController getMainController() {
        return mainController;
    }

    /**
     *
     * @return the client associated to the player who is trying to make an action
     */
    public VirtualView getClient() {
        return client;
    }

    /**
     * execute the action
     */
    public void execute(){

    }

}
