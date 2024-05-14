package it.polimi.ingsw.gc01.model;

import it.polimi.ingsw.gc01.model.cards.*;
import it.polimi.ingsw.gc01.model.player.*;
import it.polimi.ingsw.gc01.model.room.TablePosition;
import it.polimi.ingsw.gc01.network.VirtualView;

import java.util.*;

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
        //TODO
    }

    public void showAvailableColor (String playerName, List<PlayerColor> colors){
        //TODO
    }
    public void updateReady (String playerName, boolean ready){
        //TODO
    }

    public void showCommonObjective (List<ObjectiveCard> commonObjectives){
        //TODO
    }

    public void showTable (Player currentPlayer, Map<TablePosition, ResourceCard> drawableCards){
        //TODO
    }
    public void showHand (String playerName, List<PlayableCard> hand){
        //TODO
    }
    public void showField (String playerName, Position position, PlayableCard card){
        //TODO
    }
    public void showSecretObjectives (String playerName, List<ObjectiveCard> possibleObjectives){
        //TODO
    }
    public void showError(String playerName, String error){
        //TODO
    }
    public void serviceMessage(String message){
        //TODO
    }
}