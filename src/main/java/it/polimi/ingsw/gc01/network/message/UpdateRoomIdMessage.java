package it.polimi.ingsw.gc01.network.message;

public class UpdateRoomIdMessage implements Message {
    private String roomId;

    public UpdateRoomIdMessage(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }
}