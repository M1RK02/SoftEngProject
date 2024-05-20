package it.polimi.ingsw.gc01.network.actions;

import it.polimi.ingsw.gc01.controller.MainController;
import it.polimi.ingsw.gc01.network.VirtualView;

public class JoinGameAction extends MainAction{
    private String roomId;

    public JoinGameAction(String playerName, MainController mainController, VirtualView client, String roomId){
        super(playerName, mainController, client);
        this.roomId = roomId;
    }

    @Override
    public void execute(){
        MainController controller = getMainController();

        try {
            controller.joinGame(getPlayerName(), getClient(), roomId);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
