package it.polimi.ingsw.gc01.network.rmi.actions;

import it.polimi.ingsw.gc01.controller.RoomController;

public class LeaveAction extends RoomAction {

    public LeaveAction(String playerName, RoomController room) {
        super(playerName, room);
    }

    @Override
    public void execute() {
        RoomController controller = getRoomController();
        controller.leave(getPlayerName());
    }
}