package it.polimi.ingsw.gc01.network.socket.messages;

public class JoinFirstGameMessage extends ClientToServerMessage {
    public JoinFirstGameMessage(String playerName) {
        super(playerName);
    }
}
