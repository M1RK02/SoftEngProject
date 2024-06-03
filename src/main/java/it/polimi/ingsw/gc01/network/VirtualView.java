package it.polimi.ingsw.gc01.network;

import it.polimi.ingsw.gc01.model.player.*;

import java.rmi.*;
import java.util.*;

public interface VirtualView extends Remote {
    void updateRoomId(String roomId) throws RemoteException;

    void startGame() throws RemoteException;

    void updateCurrentPlayer(String playerName) throws RemoteException;

    void showStarter(int cardId) throws RemoteException;

    void showAvailableColors(List<PlayerColor> availableColors) throws RemoteException;

    void updateReady(String playerName, boolean ready) throws RemoteException;

    void showCommonObjectives(List<Integer> objectivesIds) throws RemoteException;

    void showTable(Map<Integer, Integer> drawableCardsIds) throws RemoteException;

    void showHand(List<Integer> cardIds) throws RemoteException;

    void showField(String playerName) throws RemoteException;

    void showSecretObjectives(List<Integer> possibleObjectivesIds) throws RemoteException;

    void showError(String error) throws RemoteException;

    void serviceMessage(String message) throws RemoteException;

    void updateField(int id, boolean front, Position position, List<Position> availablePositions) throws RemoteException;

    void isAlive() throws RemoteException;
}