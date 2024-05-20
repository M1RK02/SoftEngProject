package it.polimi.ingsw.gc01.network;

import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.model.room.TablePosition;

import java.util.*;

public interface VirtualView {
    /**
     *
     * @param roomId
     */
    public void updateRoomId(String roomId);

    /**
     *
     * @param availableColors
     */
    public void showAvailableColors(List<PlayerColor> availableColors);

    /**
     *
     * @param ready
     */
    public void updateReady(boolean ready);

    /**
     *
     * @param objectivesIds
     */
    public void showCommonObjectives(List<Integer> objectivesIds);

    /**
     *
     * @param drawableCardsIds
     */
    public void showTable(Map<TablePosition, Integer> drawableCardsIds);

    /**
     *
     * @param cardIds
     */
    public void showHand(List<Integer> cardIds);

    /**
     *
     * @param playerName
     * @param cardId
     * @param x
     * @param y
     */
    public void showField(String playerName, int cardId, int x, int y);

    /**
     *
     * @param possibleObjectivesIds
     */
    public void showSecretObjectives(List<Integer> possibleObjectivesIds);

    /**
     *
     * @param error
     */
    public void showError(String error);

    /**
     *
     * @param message
     */
    public void serviceMessage(String message);
}
