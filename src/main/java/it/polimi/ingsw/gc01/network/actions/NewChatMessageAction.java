package it.polimi.ingsw.gc01.network.actions;

import it.polimi.ingsw.gc01.controller.RoomController;
import it.polimi.ingsw.gc01.model.ChatMessage;

/**
 * Actions to add a new message to the chat
 */
public class NewChatMessageAction extends RoomAction {

    private final ChatMessage newChatMessage;

    /**
     *
     * @param playerName player who sent the message
     * @param room room of the game the player is playing in
     * @param newChatMessage the new message to be added to the chat of the game
     */
    public NewChatMessageAction(String playerName, RoomController room,ChatMessage newChatMessage){
        super(playerName, room);
        this.newChatMessage = newChatMessage;
    }

    /**
     * Execute the action
     */
    public void execute(){
        RoomController controller = getRoomController();
        controller.newChatMessage(newChatMessage);
    }
}
