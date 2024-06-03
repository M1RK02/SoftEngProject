package it.polimi.ingsw.gc01.network;

import it.polimi.ingsw.gc01.model.cards.*;
import it.polimi.ingsw.gc01.model.player.*;
import it.polimi.ingsw.gc01.model.room.TablePosition;

import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;

public class ObserverManager {
    private final Map<String, VirtualView> observers;

    public ObserverManager() {
        observers = new HashMap<>();
    }

    public void addObserver(String playerName, VirtualView client) {
        observers.put(playerName, client);
    }

    public void removeObserver(String playerName) {
        observers.remove(playerName);
    }

    public VirtualView getObserver(String playerName) {
        return observers.get(playerName);
    }

    public void updateRoomId(String playerName, String roomId) {
        VirtualView client = observers.get(playerName);
        try {
            client.updateRoomId(roomId);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void startGame() {
        for (VirtualView client : observers.values()) {
            try {
                client.startGame();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updateCurrentPlayer(String playerName) {
        for (VirtualView client : observers.values()) {
            try {
                client.updateCurrentPlayer(playerName);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void showStarter(String playerName, StarterCard card) {
        VirtualView client = observers.get(playerName);
        try {
            client.showStarter(card.getId());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void showAvailableColor(String playerName, List<PlayerColor> colors) {
        VirtualView client = observers.get(playerName);
        try {
            client.showAvailableColors(colors);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateReady(String playerName, boolean ready) {
        for (VirtualView client : observers.values()) {
            try {
                client.updateReady(playerName, ready);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void updateField(String playerName, int id, boolean front, Position position, List<Position> availablePositions) {
        VirtualView client = observers.get(playerName);
        try {
            client.updateField(id, front, position, availablePositions);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void showTable(String playerName, Map<TablePosition, ResourceCard> drawableCards) {
        VirtualView client = observers.get(playerName);
        Map<Integer, Integer> drawableIds = new HashMap<>();
        for (TablePosition position : TablePosition.values()) {
            switch (position) {
                case RESOURCEDECK -> drawableIds.put(1, drawableCards.get(TablePosition.RESOURCEDECK).getId());
                case RESOURCELEFT -> drawableIds.put(2, drawableCards.get(TablePosition.RESOURCELEFT).getId());
                case RESOURCERIGHT -> drawableIds.put(3, drawableCards.get(TablePosition.RESOURCERIGHT).getId());
                case GOLDENDECK -> drawableIds.put(4, drawableCards.get(TablePosition.GOLDENDECK).getId());
                case GOLDENLEFT -> drawableIds.put(5, drawableCards.get(TablePosition.GOLDENLEFT).getId());
                case GOLDENRIGHT -> drawableIds.put(6, drawableCards.get(TablePosition.GOLDENRIGHT).getId());
            }
        }
        try {
            client.showTable(drawableIds);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void showCommonObjectives(List<ObjectiveCard> commonObjectives) {
        List<Integer> commonObjectivesIds = commonObjectives.stream().map(ObjectiveCard::getId).collect(Collectors.toList());
        for (VirtualView client : observers.values()) {
            try {
                client.showCommonObjectives(commonObjectivesIds);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void showHand(String playerName, List<PlayableCard> hand) {
        List<Integer> handIds = hand.stream().map(PlayableCard::getId).collect(Collectors.toList());
        VirtualView client = observers.get(playerName);
        try {
            client.showHand(handIds);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void showField(String playerName) {
        VirtualView client = observers.get(playerName);
        try {
            client.showField(playerName);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void showSecretObjectives(String playerName, List<ObjectiveCard> possibleObjectives) {
        VirtualView client = observers.get(playerName);
        List<Integer> possibleObjectivesIds = possibleObjectives.stream().map(ObjectiveCard::getId).collect(Collectors.toList());
        try {
            client.showSecretObjectives(possibleObjectivesIds);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void showError(String playerName, String error) {
        VirtualView client = observers.get(playerName);
        try {
            client.showError(error);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void serviceMessage(String message) {
        for (VirtualView client : observers.values()) {
            try {
                client.serviceMessage(message);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void addressedServiceMessage(String playerName, String message) {
        VirtualView client = observers.get(playerName);
        try {
            client.serviceMessage(message);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}