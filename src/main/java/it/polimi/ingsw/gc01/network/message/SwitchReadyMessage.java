package it.polimi.ingsw.gc01.network.message;

public class SwitchReadyMessage extends ClientToServerMessage{
    public SwitchReadyMessage(String playerName) {
        super(playerName);
    }
}