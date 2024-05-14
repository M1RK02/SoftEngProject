package it.polimi.ingsw.gc01.network.message;

public class JoinGameMessage extends ClientToServerMessage{
    private String roomId;

    public JoinGameMessage(String playerName, String roomId) {
        super(playerName);
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }
}