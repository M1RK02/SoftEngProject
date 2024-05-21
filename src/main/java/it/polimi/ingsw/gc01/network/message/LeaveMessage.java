package it.polimi.ingsw.gc01.network.message;

public class LeaveMessage extends ClientToServerMessage{
    public LeaveMessage(String playerName) {
        super(playerName);
    }
}
