package it.polimi.ingsw.gc01.network.message;

public class JoinFirstGameMessage extends ClientToServerMessage {
    public JoinFirstGameMessage(String playerName) {
        super(playerName);
    }
}
