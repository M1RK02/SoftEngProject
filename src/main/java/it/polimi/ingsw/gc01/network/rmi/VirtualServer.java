package it.polimi.ingsw.gc01.network.rmi;

import it.polimi.ingsw.gc01.model.player.*;
import it.polimi.ingsw.gc01.network.VirtualView;

import java.rmi.*;

/**
 * Remote interface for the RMI Server
 */
public interface VirtualServer extends Remote {
    /**
     * Create a game and add the player to it
     *
     * @param playerName of the player
     * @param client     reference to the view
     * @throws RemoteException
     */
    void createGame(String playerName, VirtualView client) throws RemoteException;

    /**
     * Join the indicated game
     *
     * @param playerName of the player
     * @param client     reference to the view
     * @param roomId     to join
     * @throws RemoteException
     */
    void joinGame(String playerName, VirtualView client, String roomId) throws RemoteException;

    /**
     * Join first available game
     *
     * @param playerName of the player
     * @param client     reference to the view
     * @throws RemoteException
     */
    void joinFirstGame(String playerName, VirtualView client) throws RemoteException;

    /**
     * Choose color for the player
     *
     * @param playerName of the player
     * @param roomId     where the player is
     * @param color      chosen
     * @throws RemoteException
     */
    void chooseColor(String playerName, String roomId, PlayerColor color) throws RemoteException;

    /**
     * Switch ready
     *
     * @param playerName of the player
     * @param roomId     where the player is
     * @throws RemoteException
     */
    void switchReady(String playerName, String roomId) throws RemoteException;

    /**
     * Choose the secret objective
     *
     * @param playerName of the player
     * @param roomId     where the player is
     * @param cardId     chosen by the player
     * @throws RemoteException
     */
    void chooseSecretObjective(String playerName, String roomId, int cardId) throws RemoteException;

    /**
     * Flip the indicated card
     *
     * @param playerName of the player
     * @param roomId     where the player is
     * @param cardId     of the card to flip
     * @throws RemoteException
     */
    void flipCard(String playerName, String roomId, int cardId) throws RemoteException;

    /**
     * Play the card in the indicated position
     *
     * @param playerName of the player
     * @param roomId     where the player is
     * @param cardId     of the card to play
     * @param position   where to play the card
     * @throws RemoteException
     */
    void playCard(String playerName, String roomId, int cardId, Position position) throws RemoteException;

    /**
     * Draw the chosen card
     *
     * @param playerName of the player
     * @param roomId     where the player is
     * @param choice     position of the chosen card
     * @throws RemoteException
     */
    void drawCard(String playerName, String roomId, int choice) throws RemoteException;

    /**
     * Leave the current game
     *
     * @param playerName of the player
     * @param roomId     where the player is
     * @throws RemoteException
     */
    void leave(String playerName, String roomId) throws RemoteException;
}