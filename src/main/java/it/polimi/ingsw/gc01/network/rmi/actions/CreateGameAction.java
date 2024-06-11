package it.polimi.ingsw.gc01.network.rmi.actions;

import it.polimi.ingsw.gc01.controller.MainController;
import it.polimi.ingsw.gc01.network.VirtualView;

public class CreateGameAction extends MainAction {
    public CreateGameAction(String playerName, VirtualView client) {
        super(playerName, client);
    }

    @Override
    public void execute() {
        MainController controller = MainController.getInstance();
        controller.createGame(getPlayerName(), getClient());
    }
}