package it.polimi.ingsw.gc01.network.actions;

import it.polimi.ingsw.gc01.controller.RoomController;
import it.polimi.ingsw.gc01.model.player.Player;

public class SetReadyAction extends RoomAction{

    public SetReadyAction(String playerName, RoomController room){
        super(playerName, room);
    }

    @Override
    public void execute(){
        RoomController controller = getRoomController();
        Player playerToSetReady = controller.getRoom().getPlayerByName(getPlayerName());

        controller.changeReady(playerToSetReady);
    }
}
