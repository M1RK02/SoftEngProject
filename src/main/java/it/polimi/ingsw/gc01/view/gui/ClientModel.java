package it.polimi.ingsw.gc01.view.gui;

import it.polimi.ingsw.gc01.model.ChatMessage;
import it.polimi.ingsw.gc01.model.player.PlayerColor;

import java.util.*;

public class ClientModel {

    private String currentPlayer;
    private Map<String, Integer> points;
    private Map<PlayerColor, Integer> pawnPoints;
    private List<Integer> handIDs;
    private Map<String, ClientFieldGUI> fields;
    private int numOfPlayers = 0;
    private int commonObjective1;
    private int commonObjective2;
    private int secretObjective;
    private List<ChatMessage> messages;

    public ClientModel() {
        messages = new ArrayList<>();
    }

    private Map<Integer, Integer> drawableCardsIds;

    public Map<Integer, Integer> getDrawableCardsIds() {
        return drawableCardsIds;
    }

    public void setDrawableCardsIds(Map<Integer, Integer> drawableCardsIds) {
        this.drawableCardsIds = drawableCardsIds;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Map<String, Integer> getPoints() {
        return points;
    }

    public void setPoints(Map<String, Integer> points) {
        this.points = points;
    }

    public Map<PlayerColor, Integer> getPawnPoints() {
        return pawnPoints;
    }

    public void setPawnPoints(Map<PlayerColor, Integer> pawnPoints) {
        this.pawnPoints = pawnPoints;
    }

    public void setCommonObjectives(int commonObjective1, int commonObjective2) {
        this.commonObjective1 = commonObjective1;
        this.commonObjective2 = commonObjective2;
    }

    public int getCommonObjective1() {
        return commonObjective1;
    }

    public int getCommonObjective2() {
        return commonObjective2;
    }

    public int getSecretObjective() {
        return secretObjective;
    }

    public void setSecretObjective(int secretObjective) {
        this.secretObjective = secretObjective;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public void setNumOfPlayers(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    public List<Integer> getHandIDs() {
        return handIDs;
    }

    public void setHandIDs(List<Integer> handIDs) {
        this.handIDs = handIDs;
    }

    public Map<String, ClientFieldGUI> getFields() {
        return fields;
    }

    public void setFields(Map<String, ClientFieldGUI> fields) {
        this.fields = fields;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }
}
