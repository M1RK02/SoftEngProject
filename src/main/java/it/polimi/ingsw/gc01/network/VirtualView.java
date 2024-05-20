package it.polimi.ingsw.gc01.network;

import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.model.room.TablePosition;

import java.rmi.*;
import java.util.*;

public interface VirtualView extends Remote {
    /**
     *
     * @param roomId
     */
    public void updateRoomId(String roomId) throws RemoteException;

    /**
     *
     * @param availableColors
     */
    public void showAvailableColors(List<PlayerColor> availableColors) throws RemoteException;

    /**
     *
     * @param ready
     */
    public void updateReady(boolean ready) throws RemoteException;

    /**
     *
     * @param objectivesIds
     */
    public void showCommonObjectives(List<Integer> objectivesIds) throws RemoteException;

    /**
     *
     * @param drawableCardsIds
     */
    public void showTable(Map<TablePosition, Integer> drawableCardsIds) throws RemoteException;

    /**
     *
     * @param cardIds
     */
    public void showHand(List<Integer> cardIds) throws RemoteException;

    /**
     *
     * @param playerName
     * @param cardId
     * @param x
     * @param y
     */
    public void showField(String playerName, int cardId, int x, int y) throws RemoteException;

    /**
     *
     * @param possibleObjectivesIds
     */
    public void showSecretObjectives(List<Integer> possibleObjectivesIds) throws RemoteException;

    /**
     *
     * @param error
     */
    public void showError(String error) throws RemoteException;

    /**
     *
     * @param message
     */
    public void serviceMessage(String message) throws RemoteException;
}
