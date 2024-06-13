package it.polimi.ingsw.gc01.network.socket.messages;

public class ClientToServerMessage implements Message {
    private final String playerName;

    public ClientToServerMessage(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }
}