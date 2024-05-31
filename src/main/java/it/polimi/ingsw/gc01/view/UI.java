package it.polimi.ingsw.gc01.view;

import it.polimi.ingsw.gc01.model.player.PlayerColor;

import java.util.List;

public interface UI {
    void showRoom(String roomId);
    void showError(String error);
    void showServiceMessage(String message);
    void showStarter(int cardId);
    void showAvailableColors(List<PlayerColor> availableColors);
    void showCurrentPlayer(String playerName);
    void showField();
    void showHand(List<Integer> handIds);
    void showPossibleObjectives(List<Integer> possibleObjectiveIds);
    void updateReady(String playerName, boolean ready);
    void startGame();
    void updateField(int id, int x, int y, boolean front);
}