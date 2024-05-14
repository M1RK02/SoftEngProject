package it.polimi.ingsw.gc01.network.actions;

import it.polimi.ingsw.gc01.controller.RoomController;

public class SetReadyAction extends RoomAction{

    public SetReadyAction(String playerName, RoomController room){
        super(playerName, room);
    }

    @Override
    public void execute(){
        return;
    }
}
