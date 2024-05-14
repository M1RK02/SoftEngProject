package it.polimi.ingsw.gc01.network.message;

import it.polimi.ingsw.gc01.model.room.TablePosition;

public class DrawCardMessage extends ClientToServerMessage{
    private TablePosition position;

    public DrawCardMessage(String playerName, TablePosition position) {
        super(playerName);
        this.position = position;
    }

    public TablePosition getPosition() {
        return position;
    }
}