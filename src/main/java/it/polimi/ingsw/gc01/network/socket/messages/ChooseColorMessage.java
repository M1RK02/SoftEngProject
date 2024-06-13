package it.polimi.ingsw.gc01.network.socket.messages;

import it.polimi.ingsw.gc01.model.player.PlayerColor;

public class ChooseColorMessage extends ClientToServerMessage {
    private final PlayerColor color;

    public ChooseColorMessage(String playerName, PlayerColor color) {
        super(playerName);
        this.color = color;
    }

    public PlayerColor getPlayerColor() {
        return color;
    }
}
