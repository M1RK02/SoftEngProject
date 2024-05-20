package it.polimi.ingsw.gc01.network.socket;

import it.polimi.ingsw.gc01.controller.MainController;
import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.model.room.TablePosition;
import it.polimi.ingsw.gc01.network.VirtualView;
import it.polimi.ingsw.gc01.network.message.Message;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

public class ClientHandler implements VirtualView {
    //TODO
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private MainController mainController;

    public ClientHandler(){
        super(); //Da rimuovere
    }

    public void executeMessage(){

    }

    /**
     * @param roomId
     */
    @Override
    public void updateRoomId(String roomId) {

    }

    /**
     * @param availableColors
     */
    @Override
    public void showAvailableColors(List<PlayerColor> availableColors) {

    }

    /**
     * @param ready
     */
    @Override
    public void updateReady(boolean ready) {

    }

    /**
     * @param objectivesIds
     */
    @Override
    public void showCommonObjectives(List<Integer> objectivesIds) {

    }

    /**
     * @param drawableCardsIds
     */
    @Override
    public void showTable(Map<TablePosition, Integer> drawableCardsIds) {

    }

    /**
     * @param cardIds
     */
    @Override
    public void showHand(List<Integer> cardIds) {

    }

    /**
     * @param playerName
     * @param cardId
     * @param x
     * @param y
     */
    @Override
    public void showField(String playerName, int cardId, int x, int y) {

    }

    /**
     * @param possibleObjectivesIds
     */
    @Override
    public void showSecretObjectives(List<Integer> possibleObjectivesIds) {

    }

    /**
     * @param error
     */
    @Override
    public void showError(String error) {

    }

    /**
     * @param message
     */
    @Override
    public void serviceMessage(String message) {

    }
}