package it.polimi.ingsw.gc01.view.gui;

import java.util.List;
import java.util.Map;

public class ClientModel {

    private String currentPlayer;
    private Map<String, Integer> points;
    private List<Integer> handIDs;

    private Map<String, ClientFieldGUI> fields;


    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public Map<String, Integer> getPoints() {
        return points;
    }

    public void setPoints(Map<String, Integer> points) {
        this.points = points;
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

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
