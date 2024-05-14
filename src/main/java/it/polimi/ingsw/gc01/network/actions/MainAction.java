package it.polimi.ingsw.gc01.network.actions;

import it.polimi.ingsw.gc01.controller.MainController;
import it.polimi.ingsw.gc01.network.VirtualView;

public abstract class MainAction extends Action {
    private MainController mainController;
    private VirtualView client;

    /**
     * Create a new action for the mainController
     *
     * @param playerName the name of the player who is creating the action
     * @param mainController the controller in which the action will be performed
     * @param client the client of the player who is creating the action
     */
    public MainAction(String playerName, MainController mainController, VirtualView client){
        super(playerName);
        this.mainController = mainController;
        this.client = client;
    }


    /**
     *
     * @return the MainController in the server
     */
    public MainController getMain() {
        return mainController;
    }

    /**
     *
     * @return the client
     */
    public VirtualView getClient() {
        return client;
    }
}
