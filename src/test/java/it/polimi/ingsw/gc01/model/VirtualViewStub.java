package it.polimi.ingsw.gc01.model;

import it.polimi.ingsw.gc01.model.player.*;
import it.polimi.ingsw.gc01.network.VirtualView;

import java.util.*;

public class VirtualViewStub implements VirtualView {

    public String roomId;
    public String error;

    @Override
    public void updateRoomId(String roomId) {
        this.roomId = roomId;
    }

    @Override
    public void showPlayers(Map<String, Boolean> playersAlreadyIn) {

    }

    @Override
    public void showPlayerJoined(String playerName) {

    }

    @Override
    public void showPlayerLeft(String playerName) {

    }

    @Override
    public void showWaitingFor(String playerName, String scene) {

    }

    @Override
    public void updateCurrentPlayer(String playerName) {

    }

    @Override
    public void showStarter(int cardId) {

    }

    @Override
    public void showAvailableColors(List<PlayerColor> availableColors) {

    }

    @Override
    public void updateReady(String playerName, boolean ready) {

    }

    @Override
    public void showCommonObjectives(List<Integer> objectivesIds) {

    }

    @Override
    public void showTable(Map<Integer, Integer> drawableCardsIds) {

    }

    @Override
    public void updateTable(Map<Integer, Integer> drawableCardsIds) {

    }

    @Override
    public void showHand(List<Integer> cardIds) {

    }

    @Override
    public void updateHand(List<Integer> cardIds) {

    }

    @Override
    public void showField(String playerName) {

    }

    @Override
    public void showPoints(Map<String, Integer> points, Map<PlayerColor, String> colors) {

    }

    @Override
    public void showSecretObjectives(List<Integer> possibleObjectivesIds) {

    }

    @Override
    public void showError(String error) {
        this.error = error;
    }

    @Override
    public void serviceMessage(String message) {

    }

    @Override
    public void showLastCircle() {

    }

    @Override
    public void showWinners(List<String> winners) {

    }

    @Override
    public void updateField(String playerName, int id, boolean front, Position position, List<Position> availablePositions) {

    }

    @Override
    public void updateChat(ChatMessage newChatMessage) {

    }

    @Override
    public void isAlive() {

    }

    @Override
    public void backToMenu() {

    }

    @Override
    public void startGame() {

    }
}