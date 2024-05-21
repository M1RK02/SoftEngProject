package it.polimi.ingsw.gc01.network;

import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.model.room.TablePosition;

import java.rmi.RemoteException;

public interface NetworkClient {
    void createGame() throws RemoteException;

    void joinGame() throws RemoteException;

    void joinFirstGame() throws RemoteException;

    void chooseColor(PlayerColor color) throws RemoteException;

    void switchReady() throws RemoteException;

    void chooseSecretObjective(int cardId) throws RemoteException;

    void flipCard(int cardId) throws RemoteException;

    void playCard(int cardId, int x, int y) throws RemoteException;

    void drawCard(TablePosition card) throws RemoteException;

    void leave() throws RemoteException;
}