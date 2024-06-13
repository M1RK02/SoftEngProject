package it.polimi.ingsw.gc01.network.socket.messages;

import it.polimi.ingsw.gc01.model.player.PlayerColor;

import java.util.List;

public class ShowAvailableColorMessage implements Message {
    private final List<PlayerColor> colors;

    public ShowAvailableColorMessage(List<PlayerColor> colors) {
        this.colors = colors;
    }

    public List<PlayerColor> getColors() {
        return colors;
    }
}