package it.polimi.ingsw.gc01.network.actions;

import it.polimi.ingsw.gc01.controller.RoomController;

/**
 * Base for actions executed on the room controller
 */
public abstract class RoomAction extends Action {
    /**
     * Reference to the room controller
     */
    private final RoomController room;

    /**
     * Create a new RoomAction
     *
     * @param playerName the name of the player who is creating the action
     * @param room       the room in which the player is creating the action
     */
    public RoomAction(String playerName, RoomController room) {
        super(playerName);
        this.room = room;
    }

    /**
     * @return the RoomController of the room
     */
    public RoomController getRoomController() {
        return room;
    }
}