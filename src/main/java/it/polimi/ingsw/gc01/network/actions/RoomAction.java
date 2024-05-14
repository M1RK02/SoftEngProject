package it.polimi.ingsw.gc01.network.actions;

import it.polimi.ingsw.gc01.controller.RoomController;

public abstract class RoomAction extends Action {
    private RoomController room;

    /**
     * Create a new RoomAction
     *
     * @param playerName the name of the player who is creating the action
     * @param room the room in which the player is creating the action
     */
    public RoomAction(String playerName, RoomController room){
        super(playerName);
        this.room = room;
    }

    /**
     *
     * @return the RoomController of the room
     */
    public RoomController getRoom() {
        return room;
    }

}
