package it.polimi.ingsw.gc01.model.room;

import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.model.player.Position;
import it.polimi.ingsw.gc01.network.VirtualView;

import java.rmi.RemoteException;
import java.util.*;

public class VirtualViewStub implements VirtualView {

    @Override
    public void updateRoomId(String roomId) throws RemoteException {

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
    public void showTable(Map<TablePosition, Integer> drawableCardsIds) throws RemoteException {

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

    }

    @Override
    public void serviceMessage(String message) throws RemoteException {

    }

    @Override
    public void updateField(int id, boolean front, Position position, Set<Position> availablePositions) throws RemoteException {

    }

    @Override
    public void startGame() throws RemoteException {

    }
}
