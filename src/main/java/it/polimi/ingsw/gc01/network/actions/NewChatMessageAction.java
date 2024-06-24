package it.polimi.ingsw.gc01.network.actions;

import it.polimi.ingsw.gc01.controller.RoomController;
import it.polimi.ingsw.gc01.model.ChatMessage;

public class NewChatMessageAction extends RoomAction {

    private final ChatMessage newChatMessage;

    public NewChatMessageAction(String playerName, RoomController room,ChatMessage newChatMessage){
        super(playerName, room);
        this.newChatMessage = newChatMessage;
    }

    public void execute(){
        RoomController controller = getRoomController();
        controller.newChatMessage(newChatMessage);
    }
}
