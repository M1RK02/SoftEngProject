package it.polimi.ingsw.gc01.network;

import it.polimi.ingsw.gc01.controller.MainController;
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
        new Thread(this::heartBeat).start();
    }

    public void addObserver(String playerName, VirtualView client) {
        synchronized (observers) {
            observers.put(playerName, client);
        }
    }

    public void removeObserver(String playerName) {
        synchronized (observers) {
            observers.remove(playerName);
        }
    }

    public VirtualView getObserver(String playerName) {
        synchronized (observers) {
            return observers.get(playerName);
        }
    }

    public void updateRoomId(String playerName, String roomId) {
        synchronized (observers) {
            VirtualView client = observers.get(playerName);
            try {
                client.updateRoomId(roomId);
            } catch (RemoteException ignored) {}
        }
    }

    public void startGame() {
        synchronized (observers) {
            for (VirtualView client : observers.values()) {
                try {
                    client.startGame();
                } catch (RemoteException ignored) {}
            }
        }
    }

    public void updateCurrentPlayer(String playerName) {
        synchronized (observers) {
            for (VirtualView client : observers.values()) {
                try {
                    client.updateCurrentPlayer(playerName);
                } catch (RemoteException ignored) {}
            }
        }
    }

    public void showStarter(String playerName, StarterCard card) {
        synchronized (observers) {
            VirtualView client = observers.get(playerName);
            try {
                client.showStarter(card.getId());
            } catch (RemoteException ignored) {}
        }
    }

    public void showAvailableColor(String playerName, List<PlayerColor> colors) {
        synchronized (observers) {
            VirtualView client = observers.get(playerName);
            try {
                client.showAvailableColors(colors);
            } catch (RemoteException ignored) {}
        }
    }

    public void updateReady(String playerName, boolean ready) {
        synchronized (observers) {
            for (VirtualView client : observers.values()) {
                try {
                    client.updateReady(playerName, ready);
                } catch (RemoteException ignored) {}
            }
        }
    }

    public void updateField(String playerName, int id, boolean front, Position position, List<Position> availablePositions) {
        synchronized (observers) {
            VirtualView client = observers.get(playerName);
            try {
                client.updateField(id, front, position, availablePositions);
            } catch (RemoteException ignored) {}
        }
    }

    public void showTable(String playerName, Map<TablePosition, ResourceCard> drawableCards) {
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
        synchronized (observers) {
            VirtualView client = observers.get(playerName);
            try {
                client.showTable(drawableIds);
            } catch (RemoteException ignored) {}
        }
    }

    public void showCommonObjectives(List<ObjectiveCard> commonObjectives) {
        synchronized (observers) {
            List<Integer> commonObjectivesIds = commonObjectives.stream().map(ObjectiveCard::getId).collect(Collectors.toList());
            for (VirtualView client : observers.values()) {
                try {
                    client.showCommonObjectives(commonObjectivesIds);
                } catch (RemoteException ignored) {}
            }
        }
    }

    public void showHand(String playerName, List<PlayableCard> hand) {
        List<Integer> handIds = hand.stream().map(PlayableCard::getId).collect(Collectors.toList());
        synchronized (observers) {
            VirtualView client = observers.get(playerName);
            try {
                client.showHand(handIds);
            } catch (RemoteException ignored) {}
        }
    }

    public void showField(String playerName) {
        synchronized (observers) {
            VirtualView client = observers.get(playerName);
            try {
                client.showField(playerName);
            } catch (RemoteException ignored) {}
        }
    }

    public void showSecretObjectives(String playerName, List<ObjectiveCard> possibleObjectives) {
        List<Integer> possibleObjectivesIds = possibleObjectives.stream().map(ObjectiveCard::getId).collect(Collectors.toList());
        synchronized (observers) {
            VirtualView client = observers.get(playerName);
            try {
                client.showSecretObjectives(possibleObjectivesIds);
            } catch (RemoteException ignored) {}
        }

    }

    public void showError(String playerName, String error) {
        synchronized (observers) {
            VirtualView client = observers.get(playerName);
            try {
                client.showError(error);
            } catch (RemoteException ignored) {}
        }
    }

    public void showGameError(String error) {
        synchronized (observers) {
            for (VirtualView client : observers.values()) {
                try {
                    client.showError(error);
                } catch (RemoteException ignored) {}
            }
        }
    }

    public void serviceMessage(String message) {
        synchronized (observers) {
            for (VirtualView client : observers.values()) {
                try {
                    client.serviceMessage(message);
                } catch (RemoteException ignored) {}
            }
        }
    }

    public void addressedServiceMessage(String playerName, String message) {
        synchronized (observers) {
            VirtualView client = observers.get(playerName);
            try {
                client.serviceMessage(message);
            } catch (RemoteException ignored) {}
        }
    }

    private void heartBeat() {
        try {
            Thread.sleep(30*1000);
        } catch (InterruptedException ignored) {}
        String dead = "";
        synchronized (observers) {
            for (String playerName : observers.keySet()) {
                try {
                    observers.get(playerName).isAlive();
                } catch (RemoteException e) {
                    observers.remove(playerName);
                    dead = playerName;
                    break;
                }
            }
        }
        if (!dead.isEmpty()) {
            showGameError("GAME " + dead + " has left the game");
        }
    }
}