package it.polimi.ingsw.gc01.model;

import it.polimi.ingsw.gc01.model.cards.*;
import it.polimi.ingsw.gc01.model.player.*;
import it.polimi.ingsw.gc01.model.room.TablePosition;
import it.polimi.ingsw.gc01.network.VirtualView;
import it.polimi.ingsw.gc01.network.message.*;

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
        client.updateRoomId(new UpdateRoomIdMessage(roomId));
    }

    public void showAvailableColor (String playerName, List<PlayerColor> colors){
        VirtualView client = observers.get(playerName);
        client.showAvailableColors(new ShowAvailableColorMessage(colors));
    }

    public void updateReady (String playerName, boolean ready){
        VirtualView client = observers.get(playerName);
        client.updateReady(new ReadyMessage(ready));
    }

    public void showCommonObjective (List<ObjectiveCard> commonObjectives){
        List<Integer> commonObjectivesIds = commonObjectives.stream().map(ObjectiveCard::getId).collect(Collectors.toList());
        CommonObjectiveMessage message = new CommonObjectiveMessage(commonObjectivesIds);
        for (VirtualView client : observers.values()) {
            client.showCommonObjective(message);
        }
    }

    public void showTable (String playerName, Map<TablePosition, ResourceCard> drawableCards){
        Map<TablePosition, Integer> drawableCardsIds = new HashMap<>();
        for (TablePosition tablePosition : drawableCards.keySet()) {
            drawableCardsIds.put(tablePosition, drawableCards.get(tablePosition).getId());
        }
        VirtualView client = observers.get(playerName);
        client.showTable(new TableMessage(drawableCardsIds));
    }

    public void showHand (String playerName, List<PlayableCard> hand){
        List<Integer> handIds = hand.stream().map(PlayableCard::getId).collect(Collectors.toList());
        VirtualView client = observers.get(playerName);
        client.showHand(new HandMessage(handIds));
    }

    public void showField (String playerName, Position position, PlayableCard card){
        FieldMessage message = new FieldMessage(playerName, card.getId(), position.getX(), position.getY());
        for (VirtualView client : observers.values()) {
            client.showCommonObjective(message);
        }
    }
    public void showSecretObjectives (String playerName, List<ObjectiveCard> possibleObjectives){
        VirtualView client = observers.get(playerName);
        List<Integer> possibleObjectivesIds = possibleObjectives.stream().map(ObjectiveCard::getId).collect(Collectors.toList());
        client.showSecretObjectives(new SecretObjectiveMessage(possibleObjectivesIds));
    }

    public void showError(String playerName, String error){
        VirtualView client = observers.get(playerName);
        client.showError(new ErrorMessage(error));
    }

    public void serviceMessage(String message){
        ServiceMessage serviceMessage = new ServiceMessage(message);
        for (VirtualView client : observers.values()) {
            client.serviceMessage(serviceMessage);
        }
    }
}