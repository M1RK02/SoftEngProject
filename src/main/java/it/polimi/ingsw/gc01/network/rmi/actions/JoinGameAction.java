package it.polimi.ingsw.gc01.network.rmi.actions;

import it.polimi.ingsw.gc01.controller.MainController;
import it.polimi.ingsw.gc01.network.VirtualView;

public class JoinGameAction extends MainAction {
    private final String roomId;

    public JoinGameAction(String playerName, VirtualView client, String roomId) {
        super(playerName, client);
        this.roomId = roomId;
    }

    @Override
    public void execute() {
        MainController controller = MainController.getInstance();
        controller.joinGame(getPlayerName(), getClient(), roomId);
    }
}