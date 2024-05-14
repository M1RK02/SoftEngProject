package it.polimi.ingsw.gc01.network.rmi;

import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.model.room.TablePosition;
import it.polimi.ingsw.gc01.network.VirtualView;

import java.rmi.Remote;

public interface VirtualServer extends Remote {
    public void createGame(String playerName, VirtualView client);

    public void joinGame(String playerName, VirtualView client, String roomId);

    public void joinFirstGame(String playerName, VirtualView client);

    public void chooseColor(String playerName, String roomId, PlayerColor color);

    public void switchReady(String playerName, String roomId);

    public void chooseSecretObjective(String playerName, String roomId, int cardId);

    public void flipCard(String playerName, String roomId, int cardId);

    public void playCard(String playerName, String roomId, int cardId, int x, int y);

    public void drawCard(String playerName, String roomId, TablePosition card);

    public void leave(String playerName, String roomId);
}
