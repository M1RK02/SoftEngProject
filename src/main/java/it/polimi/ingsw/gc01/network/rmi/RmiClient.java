package it.polimi.ingsw.gc01.network.rmi;

import it.polimi.ingsw.gc01.view.UI;
import it.polimi.ingsw.gc01.model.DefaultValue;
import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.model.room.TablePosition;
import it.polimi.ingsw.gc01.network.*;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class RmiClient extends UnicastRemoteObject implements VirtualView, NetworkClient {
    private final String playerName;
    private VirtualServer server;
    private String roomId;
    private final UI ui;

    public RmiClient(String playerName, UI userInterface) throws RemoteException {
        this.playerName = playerName;
        this.ui = userInterface;
        connect();
    }

    /**
     * the method connects the client to the server creating his own stub
     */
    private void connect() {
        try {
            Registry registry = LocateRegistry.getRegistry(DefaultValue.ServerIp, DefaultValue.RMIPort);
            this.server = (VirtualServer) registry.lookup(DefaultValue.RMIServerName);
            System.out.println("Client RMI ready");
        } catch (RemoteException | NotBoundException e) {
            //TODO
            System.err.println("Server RMI not working!");
        }
    }

    public String getRoomId(){
        return roomId;
    }

    /**
     * asks stub to create game
     */
    @Override
    public void createGame() {
        try {
            server.createGame(this.playerName, this);
        } catch (RemoteException e) {
            //TODO
            System.err.println("Server RMI not working!");
        }
    }

    /**
     * asks stub to join game
     */
    @Override
    public void joinGame(String roomId) {
        try {
            server.joinGame(this.playerName, this, roomId);
        } catch (RemoteException e) {
            //TODO
            System.err.println("Server RMI not working!");
        }
    }

    /**
     * asks stub to join the first available game
     */
    @Override
    public void joinFirstGame() {
        try {
            server.joinFirstGame(this.playerName, this);
        } catch (RemoteException e) {
            //TODO
            System.err.println("Server RMI not working!");
        }
    }

    /**
     * asks stub to choose the color of the player
     *
     * @param color the color wich will be given to the player
     */
    @Override
    public void chooseColor(PlayerColor color) {
        try {
            server.chooseColor(this.playerName, this.roomId, color);
        } catch (RemoteException e) {
            //TODO
            System.err.println("Server RMI not working!");
        }
    }

    /**
     * asks stub to set the player ready
     */
    @Override
    public void switchReady() {
        try {
            server.switchReady(this.playerName, this.roomId);
        } catch (RemoteException e) {
            //TODO
            System.err.println("Server RMI not working!");
        }
    }

    /**
     * asks stub to choose the secret objective
     *
     * @param cardId the id of the chosen objective card
     */
    @Override
    public void chooseSecretObjective(int cardId) {
        try {
            server.chooseSecretObjective(this.playerName, this.roomId, cardId);
        } catch (RemoteException e) {
            //TODO
            System.err.println("Server RMI not working!");
        }
    }

    /**
     * asks stub to flip the selected card
     *
     * @param cardId the id of the card to flip
     */
    @Override
    public void flipCard(int cardId) {
        try {
            server.flipCard(this.playerName, this.roomId, cardId);
        } catch (RemoteException e) {
            //TODO
            System.err.println("Server RMI not working!");
        }
    }

    /**
     * asks stub to play the card in the position with coordinates x ,y
     *
     * @param cardId the id of the card to play
     * @param x      the x coordinate in the matrix of the player field
     * @param y      the y coordinate in the matrix of the player field
     */
    @Override
    public void playCard(int cardId, int x, int y) {
        try {
            server.playCard(this.playerName, this.roomId, cardId, x, y);
        } catch (RemoteException e) {
            //TODO
            System.err.println("Server RMI not working!");
        }
    }

    /**
     * asks stub to draw card from a certain Table position
     *
     * @param card the position of the card to draw in the drawableCard positions
     */
    @Override
    public void drawCard(TablePosition card) {
        try {
            server.drawCard(this.playerName, this.roomId, card);
        } catch (RemoteException e) {
            //TODO
            System.err.println("Server RMI not working!");
        }
    }

    /**
     * asks stub to make the player leave the game
     */
    @Override
    public void leave() {
        try {
            server.leave(this.playerName, this.roomId);
        } catch (RemoteException e) {
            //TODO
            System.err.println("Server RMI not working!");
        }
    }

    /**
     * @param roomId
     */
    @Override
    public void updateRoomId(String roomId){
       this.roomId = roomId;
       ui.showRoom(roomId);
    }

    public void updateField(int id, int x, int y, boolean front){
        ui.updateField(id, x, y, front);
    }

    /**
     * Starts the game
     */
    @Override
    public void startGame() throws RemoteException {
        ui.startGame();
    }

    /**
     * @param playerName
     */
    @Override
    public void updateCurrentPlayer(String playerName){
        ui.showCurrentPlayer(playerName);
    }

    /**
     * @param cardId
     */
    @Override
    public void showStarter(int cardId){
        ui.showStarter(cardId);
    }

    /**
     * @param availableColors
     */
    @Override
    public void showAvailableColors(List<PlayerColor> availableColors){
        ui.showAvailableColors(availableColors);
    }

    /**
     * @param ready
     */
    @Override
    public void updateReady(String playerName, boolean ready) throws RemoteException {
        ui.updateReady(playerName, ready);
    }

    /**
     * @param objectivesIds
     */
    @Override
    public void showCommonObjectives(List<Integer> objectivesIds) throws RemoteException {
        //TODO
    }

    /**
     * @param drawableCardsIds
     */
    @Override
    public void showTable(Map<TablePosition, Integer> drawableCardsIds) throws RemoteException {
        //TODO
    }

    /**
     * @param cardIds
     */
    @Override
    public void showHand(List<Integer> cardIds) throws RemoteException {
        ui.showHand();
    }

    /**
     * @param playerName
     *
     */
    @Override
    public void showField(String playerName) throws RemoteException {
        ui.showField();
    }

    /**
     * @param possibleObjectivesIds
     */
    @Override
    public void showSecretObjectives(List<Integer> possibleObjectivesIds) throws RemoteException {
        ui.showPossibleObjectives(possibleObjectivesIds);
    }

    /**
     * @param error
     */
    @Override
    public void showError(String error) throws RemoteException {
        ui.showError(error);
    }

    /**
     * @param message
     */
    @Override
    public void serviceMessage(String message) throws RemoteException {
        ui.showServiceMessage(message);
    }
}