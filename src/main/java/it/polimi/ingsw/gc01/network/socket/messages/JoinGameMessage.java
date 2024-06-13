package it.polimi.ingsw.gc01.network.socket.messages;

public class JoinGameMessage extends ClientToServerMessage {
    private final String roomId;

    public JoinGameMessage(String playerName, String roomId) {
        super(playerName);
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }
}