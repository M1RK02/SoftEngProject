package it.polimi.ingsw.gc01.network.rmi.actions;

import it.polimi.ingsw.gc01.controller.RoomController;

/**
 * Action to switch ready
 */
public class SwitchReadyAction extends RoomAction {

    /**
     * Create a new SwitchReadyAction
     * @param playerName of the player who is creating the action
     * @param room where the action will be executed
     */
    public SwitchReadyAction(String playerName, RoomController room) {
        super(playerName, room);
    }

    /**
     * Execute the action
     */
    @Override
    public void execute() {
        RoomController controller = getRoomController();
        controller.switchReady(getPlayerName());
    }
}