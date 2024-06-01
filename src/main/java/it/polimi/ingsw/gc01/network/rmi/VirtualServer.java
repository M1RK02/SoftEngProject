package it.polimi.ingsw.gc01.network.rmi;

import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.model.player.Position;
import it.polimi.ingsw.gc01.network.VirtualView;

import java.rmi.*;

public interface VirtualServer extends Remote {
    void createGame(String playerName, VirtualView client) throws RemoteException;

    void joinGame(String playerName, VirtualView client, String roomId) throws RemoteException;

    void joinFirstGame(String playerName, VirtualView client) throws RemoteException;

    void chooseColor(String playerName, String roomId, PlayerColor color) throws RemoteException;

    void switchReady(String playerName, String roomId) throws RemoteException;

    void chooseSecretObjective(String playerName, String roomId, int cardId) throws RemoteException;

    void flipCard(String playerName, String roomId, int cardId) throws RemoteException;

    void playCard(String playerName, String roomId, int cardId, Position position) throws RemoteException;

    void drawCard(String playerName, String roomId, int choice) throws RemoteException;

    void leave(String playerName, String roomId) throws RemoteException;
}