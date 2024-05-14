package it.polimi.ingsw.gc01.network.message;

public class CreateGameMessage extends ClientToServerMessage{
    public CreateGameMessage(String playerName) {
        super(playerName);
    }
}
