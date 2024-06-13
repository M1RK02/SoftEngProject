package it.polimi.ingsw.gc01.network.rmi.actions;

import it.polimi.ingsw.gc01.controller.RoomController;

/**
 * Action to leave the room
 */
public class LeaveAction extends RoomAction {
    /**
     * Create a new LeaveAction
     *
     * @param playerName of the player who is creating the action
     * @param room       where the action will be executed
     */
    public LeaveAction(String playerName, RoomController room) {
        super(playerName, room);
    }

    /**
     * Execute the action
     */
    @Override
    public void execute() {
        RoomController controller = getRoomController();
        controller.leave(getPlayerName());
    }
}