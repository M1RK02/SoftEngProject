package it.polimi.ingsw.gc01.network.rmi;

import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.model.room.TablePosition;
import it.polimi.ingsw.gc01.network.VirtualView;

import java.rmi.*;

public interface VirtualServer extends Remote{
    public void createGame(String playerName, VirtualView client) throws RemoteException;

    public void joinGame(String playerName, VirtualView client, String roomId) throws RemoteException;

    public void joinFirstGame(String playerName, VirtualView client) throws RemoteException;

    public void chooseColor(String playerName, String roomId, PlayerColor color) throws RemoteException;

    public void switchReady(String playerName, String roomId) throws RemoteException;

    public void chooseSecretObjective(String playerName, String roomId, int cardId) throws RemoteException;

    public void flipCard(String playerName, String roomId, int cardId) throws RemoteException;

    public void playCard(String playerName, String roomId, int cardId, int x, int y) throws RemoteException;

    public void drawCard(String playerName, String roomId, TablePosition card) throws RemoteException;

    public void leave(String playerName, String roomId) throws RemoteException;
}