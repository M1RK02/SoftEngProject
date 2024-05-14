package it.polimi.ingsw.gc01.network.message;

public class ClientToServerMessage implements Message{
    private String playerName;

    public ClientToServerMessage(String playerName){
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }
}