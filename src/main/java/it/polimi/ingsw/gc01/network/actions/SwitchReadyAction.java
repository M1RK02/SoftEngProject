package it.polimi.ingsw.gc01.network.actions;

import it.polimi.ingsw.gc01.controller.RoomController;

public class SwitchReadyAction extends RoomAction {

    public SwitchReadyAction(String playerName, RoomController room) {
        super(playerName, room);
    }

    @Override
    public void execute() {
        RoomController controller = getRoomController();
        controller.switchReady(getPlayerName());
    }
}