package it.polimi.ingsw.gc01.network.socket.messages;

public class CreateGameMessage extends ClientToServerMessage {
    public CreateGameMessage(String playerName) {
        super(playerName);
    }
}
