package it.polimi.ingsw.gc01.network.socket.messages;

public class SwitchReadyMessage extends ClientToServerMessage {
    public SwitchReadyMessage(String playerName) {
        super(playerName);
    }
}