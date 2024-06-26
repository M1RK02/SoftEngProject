package it.polimi.ingsw.gc01.view.gui;

import it.polimi.ingsw.gc01.model.player.PlayerColor;
import javafx.collections.*;
import javafx.scene.text.TextFlow;

import java.util.*;

/**
 * Represents the client-side model of the game application.
 */
public class ClientModel {

    private String currentPlayer;
    private Map<String, Integer> points;
    private Map<PlayerColor, String> colors;
    private List<Integer> handIDs;
    private Map<String, ClientFieldGUI> fields;
    private int numOfPlayers = 0;
    private int commonObjective1;
    private int commonObjective2;
    private int secretObjective;
    private ObservableList<TextFlow> messages;
    private Map<Integer, Integer> drawableCardsIds;
    private List<String> otherPlayers;
    /**
     * Constructs a new ClientModel instance with an empty list of messages and other players.
     */
    public ClientModel() {
        messages = FXCollections.observableArrayList();
        otherPlayers = new ArrayList<>();
    }

    /**
     * Retrieves the map of drawable card IDs.
     *
     * @return the map of drawable card IDs
     */
    public Map<Integer, Integer> getDrawableCardsIds() {
        return drawableCardsIds;
    }
    /**
     * Sets the map of drawable card IDs.
     *
     * @param drawableCardsIds the map of drawable card IDs to set
     */
    public void setDrawableCardsIds(Map<Integer, Integer> drawableCardsIds) {
        this.drawableCardsIds = drawableCardsIds;
    }
    /**
     * Retrieves the name of the current player.
     *
     * @return the name of the current player
     */
    public String getCurrentPlayer() {
        return currentPlayer;
    }
    /**
     * Sets the name of the current player.
     *
     * @param currentPlayer the name of the current player to set
     */
    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    /**
     * Retrieves the map of points scored by players.
     *
     * @return the map of points scored by players
     */
    public Map<String, Integer> getPoints() {
        return points;
    }
    /**
     * Sets the map of points scored by players.
     *
     * @param points the map of points scored by players to set
     */
    public void setPoints(Map<String, Integer> points) {
        this.points = points;
    }

    /**
     * Returns the mapping of player colors to corresponding strings.
     *
     * @return A map where keys are PlayerColor enum values and values are strings representing color names.
     */
    public Map<PlayerColor, String> getColors() {
        return colors;
    }
    /**
     * Sets the mapping of player colors to corresponding strings.
     *
     * @param colors A map where keys are PlayerColor enum values and values are strings representing color names.
     */
    public void setColors(Map<PlayerColor, String> colors) {
        this.colors = colors;
    }
    /**
     * Sets the common objectives.
     *
     * @param commonObjective1 the value of the first common objective
     * @param commonObjective2 the value of the second common objective
     */
    public void setCommonObjectives(int commonObjective1, int commonObjective2) {
        this.commonObjective1 = commonObjective1;
        this.commonObjective2 = commonObjective2;
    }
    /**
     * Retrieves the value of the first common objective.
     *
     * @return the value of the first common objective
     */
    public int getCommonObjective1() {
        return commonObjective1;
    }
    /**
     * Retrieves the value of the second common objective.
     *
     * @return the value of the second common objective
     */
    public int getCommonObjective2() {
        return commonObjective2;
    }
    /**
     * Retrieves the value of the secret objective.
     *
     * @return the value of the secret objective
     */
    public int getSecretObjective() {
        return secretObjective;
    }
    /**
     * Sets the value of the secret objective.
     *
     * @param secretObjective the value of the secret objective to set
     */
    public void setSecretObjective(int secretObjective) {
        this.secretObjective = secretObjective;
    }
    /**
     * Retrieves the number of players in the game.
     *
     * @return the number of players in the game
     */
    public int getNumOfPlayers() {
        return numOfPlayers;
    }
    /**
     * Sets the number of players in the game.
     *
     * @param numOfPlayers the number of players in the game to set
     */
    public void setNumOfPlayers(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }
    /**
     * Retrieves the list of hand card IDs.
     *
     * @return the list of hand card IDs
     */
    public List<Integer> getHandIDs() {
        return handIDs;
    }
    /**
     * Sets the list of hand card IDs.
     *
     * @param handIDs the list of hand card IDs to set
     */
    public void setHandIDs(List<Integer> handIDs) {
        this.handIDs = handIDs;
    }
    /**
     * Retrieves the map of game fields.
     *
     * @return the map of game fields
     */
    public Map<String, ClientFieldGUI> getFields() {
        return fields;
    }
    /**
     * Sets the map of game fields.
     *
     * @param fields the map of game fields to set
     */
    public void setFields(Map<String, ClientFieldGUI> fields) {
        this.fields = fields;
    }
    /**
     * Retrieves the observable list of messages.
     *
     * @return the observable list of messages
     */
    public ObservableList<TextFlow> getMessages() {
        return messages;
    }
    /**
     * Retrieves the list of other players' names.
     *
     * @return the list of other players' names
     */
    public List<String> getOtherPlayers() {
        return otherPlayers;
    }
    /**
     * Adds a player's name to the list of other players.
     *
     * @param playerName the name of the player to add
     */
    public void addOtherPlayers(String playerName) {
        this.otherPlayers.add(playerName);
    }
}
