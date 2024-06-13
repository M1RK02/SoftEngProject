package it.polimi.ingsw.gc01.network.socket.messages;

import it.polimi.ingsw.gc01.model.room.TablePosition;

public class DrawCardMessage extends ClientToServerMessage {
    private final TablePosition position;

    public DrawCardMessage(String playerName, TablePosition position) {
        super(playerName);
        this.position = position;
    }

    public TablePosition getPosition() {
        return position;
    }
}