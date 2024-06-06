package it.polimi.ingsw.gc01.view;

import it.polimi.ingsw.gc01.model.player.*;

import java.util.*;

public interface UI {
    void showRoom(String roomId);

    void showError(String error);

    void showServiceMessage(String message);

    void showLastCircle();

    void showWinners(List<String> winners);

    void showStarter(int cardId);

    void showAvailableColors(List<PlayerColor> availableColors);

    void showCurrentPlayer(String playerName);

    void showField(String playerName);

    void showPoints(Map<String, Integer> points);

    void showHand(List<Integer> handIds);

    void showPossibleObjectives(List<Integer> possibleObjectiveIds);

    void updateReady(String playerName, boolean ready);

    void startGame();

    void updateField(String playerName, int id, boolean front, Position position, List<Position> availablePositions);

    void showTable(Map<Integer, Integer> drawableCardsIds);

    void showCommonObjectives(List<Integer> objectiveIds);

    void backToMenu();
}