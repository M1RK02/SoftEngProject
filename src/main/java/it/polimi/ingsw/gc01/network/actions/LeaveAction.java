package it.polimi.ingsw.gc01.network.actions;

import it.polimi.ingsw.gc01.controller.RoomController;
import it.polimi.ingsw.gc01.model.player.Player;

public class LeaveAction extends RoomAction{

    public LeaveAction(String playerName, RoomController room){
        super(playerName, room);
    }

    @Override
    public void execute(){
        RoomController controller = getRoomController();
        Player playerWhoIsLeaving = controller.getRoom().getPlayerByName(getPlayerName());

        controller.leave(playerWhoIsLeaving);
    }
}
