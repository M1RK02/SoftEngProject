package it.polimi.ingsw.gc01.network.socket.messages;

public class LeaveMessage extends ClientToServerMessage {
    public LeaveMessage(String playerName) {
        super(playerName);
    }
}
