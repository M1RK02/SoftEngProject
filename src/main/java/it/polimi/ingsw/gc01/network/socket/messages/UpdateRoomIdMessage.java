package it.polimi.ingsw.gc01.network.socket.messages;

public class UpdateRoomIdMessage implements Message {
    private final String roomId;

    public UpdateRoomIdMessage(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }
}