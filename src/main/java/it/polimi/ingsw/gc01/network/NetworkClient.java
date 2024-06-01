package it.polimi.ingsw.gc01.network;

import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.model.room.TablePosition;

import java.rmi.RemoteException;

public interface NetworkClient {
    String getRoomId();
    void createGame();

    void joinGame(String roomId);

    void joinFirstGame();

    void chooseColor(PlayerColor color);

    void switchReady();

    void chooseSecretObjective(int cardId);

    void flipCard(int cardId);

    void playCard(int cardId, int x, int y);

    void drawCard(int choice);

    void leave();
}