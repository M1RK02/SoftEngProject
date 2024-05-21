package it.polimi.ingsw.gc01.network.actions;

import it.polimi.ingsw.gc01.controller.MainController;
import it.polimi.ingsw.gc01.network.VirtualView;

public class JoinFirstGameAction extends MainAction {
    public JoinFirstGameAction(String playerName, VirtualView client) {
        super(playerName, client);
    }

    @Override
    public void execute() {
        MainController controller = MainController.getInstance();
        controller.joinFirstGame(getPlayerName(), getClient());
    }
}