package it.polimi.ingsw.gc01.model;

import it.polimi.ingsw.gc01.model.cards.*;
import it.polimi.ingsw.gc01.model.player.*;
import it.polimi.ingsw.gc01.model.room.TablePosition;
import it.polimi.ingsw.gc01.network.VirtualView;

import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;

public class ObserverManager {
    private Map<String, VirtualView> observers;

    public ObserverManager() {
        observers = new HashMap<>();
    }

    public void addObserver (String playerName, VirtualView client) {
        observers.put(playerName, client);
    }

    public void removeObserver(String playerName) {
        observers.remove(playerName);
    }

    public VirtualView getObserver(String playerName) {
        return observers.get(playerName);
    }

    public void updateRoomId (String playerName, String roomId){
        VirtualView client = observers.get(playerName);
        try {
            client.updateRoomId(roomId);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCurrentPlayer (String playerName) {
        for (VirtualView client : observers.values()) {
            try {
                client.updateCurrentPlayer(playerName);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void showStarter (String playerName, StarterCard card){
        VirtualView client = observers.get(playerName);
        try {
            client.showStarter(card.getId());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
    public void showAvailableColor (String playerName, List<PlayerColor> colors){
        VirtualView client = observers.get(playerName);
        try {
            client.showAvailableColors(colors);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateReady (String playerName, boolean ready){
        for (VirtualView client : observers.values()) {
            try {
                client.updateReady(playerName, ready);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void showCommonObjectives(List<ObjectiveCard> commonObjectives){
        List<Integer> commonObjectivesIds = commonObjectives.stream().map(ObjectiveCard::getId).collect(Collectors.toList());
        for (VirtualView client : observers.values()) {
            try {
                client.showCommonObjectives(commonObjectivesIds);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void showTable (Map<TablePosition, ResourceCard> drawableCards){
        Map<TablePosition, Integer> drawableCardsIds = new HashMap<>();
        for (TablePosition tablePosition : drawableCards.keySet()) {
            drawableCardsIds.put(tablePosition, drawableCards.get(tablePosition).getId());
        }
        for (VirtualView client : observers.values()) {
            try {
                client.showTable(drawableCardsIds);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void showHand (String playerName, List<PlayableCard> hand){
        List<Integer> handIds = hand.stream().map(PlayableCard::getId).collect(Collectors.toList());
        VirtualView client = observers.get(playerName);
        try {
            client.showHand(handIds);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void showField (String playerName, Position position, PlayableCard card){
        for (VirtualView client : observers.values()) {
            try {
                client.showField(playerName, card.getId(), position.getX(), position.getY());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void showSecretObjectives (String playerName, List<ObjectiveCard> possibleObjectives){
        VirtualView client = observers.get(playerName);
        List<Integer> possibleObjectivesIds = possibleObjectives.stream().map(ObjectiveCard::getId).collect(Collectors.toList());
        try {
            client.showSecretObjectives(possibleObjectivesIds);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void showError(String playerName, String error){
        VirtualView client = observers.get(playerName);
        try {
            client.showError(error);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void serviceMessage(String message){
        for (VirtualView client : observers.values()) {
            try {
                client.serviceMessage(message);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void addressedServiceMessage(String playerName, String message){
        VirtualView client = observers.get(playerName);
        try {
            client.serviceMessage(message);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}