package it.polimi.ingsw.gc01.model;

import it.polimi.ingsw.gc01.model.player.*;
import it.polimi.ingsw.gc01.network.VirtualView;

import java.rmi.RemoteException;
import java.util.*;

public class VirtualViewStub implements VirtualView {

    public String roomId;
    public String error;

    @Override
    public void updateRoomId(String roomId) throws RemoteException {
        this.roomId = roomId;
    }

    @Override
    public void updateCurrentPlayer(String playerName) throws RemoteException {

    }

    @Override
    public void showStarter(int cardId) throws RemoteException {

    }

    @Override
    public void showAvailableColors(List<PlayerColor> availableColors) throws RemoteException {

    }

    @Override
    public void updateReady(String playerName, boolean ready) throws RemoteException {

    }

    @Override
    public void showCommonObjectives(List<Integer> objectivesIds) throws RemoteException {

    }

    @Override
    public void showTable(Map<Integer, Integer> drawableCardsIds) throws RemoteException {

    }

    @Override
    public void showHand(List<Integer> cardIds) throws RemoteException {

    }

    @Override
    public void showField(String playerName) throws RemoteException {

    }

    @Override
    public void showSecretObjectives(List<Integer> possibleObjectivesIds) throws RemoteException {

    }

    @Override
    public void showError(String error) throws RemoteException {
        this.error = error;
    }

    @Override
    public void serviceMessage(String message) throws RemoteException {

    }

    @Override
    public void updateField(int id, boolean front, Position position, List<Position> availablePositions) throws RemoteException {

    }

    @Override
    public void isAlive() throws RemoteException {

    }

    @Override
    public void startGame() throws RemoteException {

    }
}
