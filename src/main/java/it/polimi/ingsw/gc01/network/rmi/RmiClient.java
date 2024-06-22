package it.polimi.ingsw.gc01.network.rmi;

import it.polimi.ingsw.gc01.model.player.*;
import it.polimi.ingsw.gc01.network.*;
import it.polimi.ingsw.gc01.utils.DefaultValue;
import it.polimi.ingsw.gc01.view.UI;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * Implementation of an RMI Client
 */
public class RmiClient extends UnicastRemoteObject implements VirtualView, NetworkClient {
    /**
     * Name of the player
     */
    private final String playerName;
    /**
     * User interface of the player
     */
    private final UI ui;
    /**
     * Reference to the RMI server
     */
    private VirtualServer server;
    /**
     * Id of the room
     */
    private String roomId;

    /**
     * Construct a new RmiClient object and connect it to the server
     *
     * @param playerName    of the player
     * @param userInterface chosen by the player
     * @throws RemoteException
     */
    public RmiClient(String playerName, UI userInterface) throws RemoteException {
        this.playerName = playerName;
        this.ui = userInterface;
        connect();
    }

    /**
     * Connect the client to the server creating his own stub
     */
    private void connect() {
        try {
            Registry registry = LocateRegistry.getRegistry(DefaultValue.ServerIp, DefaultValue.Default_RMI_port);
            this.server = (VirtualServer) registry.lookup(DefaultValue.RMIServerName);
            System.out.println("Client RMI ready");
        } catch (RemoteException | NotBoundException e) {
            System.err.println("Server RMI not working!");
        }
    }

    /**
     * @return the room id
     */
    @Override
    public String getRoomId() {
        return roomId;
    }

    /**
     * Asks stub to create game
     */
    @Override
    public void createGame() {
        try {
            server.createGame(this.playerName, this);
        } catch (RemoteException e) {
            System.err.println("Server RMI not working!");
        }
    }

    /**
     * Asks stub to join game
     */
    @Override
    public void joinGame(String roomId) {
        try {
            server.joinGame(this.playerName, this, roomId);
        } catch (RemoteException e) {
            System.err.println("Server RMI not working!");
        }
    }

    /**
     * Asks stub to join the first available game
     */
    @Override
    public void joinFirstGame() {
        try {
            server.joinFirstGame(this.playerName, this);
        } catch (RemoteException e) {
            System.err.println("Server RMI not working!");
        }
    }

    /**
     * Asks stub to choose the color of the player
     *
     * @param color the color wich will be given to the player
     */
    @Override
    public void chooseColor(PlayerColor color) {
        try {
            server.chooseColor(this.playerName, this.roomId, color);
        } catch (RemoteException e) {
            System.err.println("Server RMI not working!");
        }
    }

    /**
     * Asks stub to set the player ready
     */
    @Override
    public void switchReady() {
        try {
            server.switchReady(this.playerName, this.roomId);
        } catch (RemoteException e) {
            System.err.println("Server RMI not working!");
        }
    }

    /**
     * Asks stub to choose the secret objective
     *
     * @param cardId the id of the chosen objective card
     */
    @Override
    public void chooseSecretObjective(int cardId) {
        try {
            server.chooseSecretObjective(this.playerName, this.roomId, cardId);
        } catch (RemoteException e) {
            System.err.println("Server RMI not working!");
        }
    }

    /**
     * Asks stub to flip the selected card
     *
     * @param cardId the id of the card to flip
     */
    @Override
    public void flipCard(int cardId) {
        try {
            server.flipCard(this.playerName, this.roomId, cardId);
        } catch (RemoteException e) {
            System.err.println("Server RMI not working!");
        }
    }

    /**
     * Asks stub to play the card in the position with coordinates x ,y
     *
     * @param cardId   the id of the card to play
     * @param position the position in the player field
     */
    @Override
    public void playCard(int cardId, Position position) {
        try {
            server.playCard(this.playerName, this.roomId, cardId, position);
        } catch (RemoteException e) {
            System.err.println("Server RMI not working!");
        }
    }

    /**
     * Asks stub to draw card from a certain Table position
     *
     * @param choice the position of the card to draw in the drawableCard positions
     */
    @Override
    public void drawCard(int choice) {
        try {
            server.drawCard(this.playerName, this.roomId, choice);
        } catch (RemoteException e) {
            System.err.println("Server RMI not working!");
        }
    }

    /**
     * Asks stub to make the player leave the game
     */
    @Override
    public void leave() {
        try {
            server.leave(this.playerName, this.roomId);
        } catch (RemoteException e) {
            System.err.println("Server RMI not working!");
        }
    }

    /**
     * Show the roomId to the ui
     *
     * @param roomId of the room
     */
    @Override
    public void updateRoomId(String roomId) {
        this.roomId = roomId;
        ui.showRoom(roomId);
    }

    /**
     * Show the players in the room
     *
     * @param playerNames the names of the players in the room
     */
    @Override
    public void showPlayers(List<String> playerNames) {
        ui.showPlayers(playerNames);
    }

    /**
     * Show the player that has just joined
     *
     * @param playerName the names of the player that has just joined
     */
    @Override
    public void showPlayerJoined(String playerName) {
        ui.showPlayerJoined(playerName);
    }

    /**
     * Show the player that has just left
     *
     * @param playerName the names of the player that has just left
     */
    @Override
    public void showPlayerLeft(String playerName) {
        ui.showPlayerLeft(playerName);
    }

    /**
     * Show the waiting scene for every client except the one choosing
     *
     * @throws RemoteException
     */
    @Override
    public void showWaitingFor(String playerName, String scene) throws RemoteException {
        if (!playerName.equals(this.playerName)) {
            ui.showWaitingFor(playerName, scene);
        }
    }

    /**
     * Update the field for the indicated player
     *
     * @param playerName         of the player to update
     * @param id                 of the newly played card
     * @param front              true if the card is played front, false otherwise
     * @param position           of the played card
     * @param availablePositions list of available positions
     */
    @Override
    public void updateField(String playerName, int id, boolean front, Position position, List<Position> availablePositions) {
        ui.updateField(playerName, id, front, position, availablePositions);
    }

    /**
     * Starts the game
     */
    @Override
    public void startGame() {
        ui.startGame();
    }

    /**
     * Update the current player
     *
     * @param playerName of the new current player
     */
    @Override
    public void updateCurrentPlayer(String playerName) {
        ui.showCurrentPlayer(playerName);
    }

    /**
     * Show the starter card to the player
     *
     * @param cardId of the starter card
     */
    @Override
    public void showStarter(int cardId) {
        ui.showStarter(cardId);
    }

    /**
     * Show available colors to the player
     *
     * @param availableColors list of available colors
     */
    @Override
    public void showAvailableColors(List<PlayerColor> availableColors) {
        ui.showAvailableColors(availableColors);
    }

    /**
     * Update readiness of a player
     *
     * @param playerName of the player to update
     * @param ready      new status of the player, true if ready, false otherwise
     */
    @Override
    public void updateReady(String playerName, boolean ready) {
        ui.updateReady(playerName, ready);
    }

    /**
     * Show common objectives
     *
     * @param objectivesIds list of common objective ids
     */
    @Override
    public void showCommonObjectives(List<Integer> objectivesIds) {
        ui.showCommonObjectives(objectivesIds);
    }

    /**
     * Show the center table
     *
     * @param drawableCardsIds map of drawable card ids
     */
    @Override
    public void showTable(Map<Integer, Integer> drawableCardsIds) {
        ui.showTable(drawableCardsIds);
    }

    /**
     * Update the drawable cards on the table
     *
     * @param drawableCardsIds the map of the ids with the positions in the table
     * @throws RemoteException
     */
    @Override
    public void updateTable(Map<Integer, Integer> drawableCardsIds) throws RemoteException {
        ui.updateTable(drawableCardsIds);
    }

    /**
     * Show the hand
     *
     * @param handIds list of card ids in the hand
     */
    @Override
    public void showHand(List<Integer> handIds) {
        ui.showHand(handIds);
    }

    /**
     * Update the hand
     *
     * @param cardIds list of card ids in the hand
     * @throws RemoteException
     */
    @Override
    public void updateHand(List<Integer> cardIds) throws RemoteException {
        ui.updateHand(cardIds);
    }

    /**
     * Show the field of the indicated player
     *
     * @param playerName to show the field
     */
    @Override
    public void showField(String playerName) {
        ui.showField(playerName);
    }

    /**
     * Show the points for each player
     *
     * @param points map of playerName, points
     */
    @Override
    public void showPoints(Map<String, Integer> points, Map<PlayerColor, Integer> tablePoints) {
        ui.showPoints(points, tablePoints);
    }

    /**
     * Show the possible secret objectives
     *
     * @param possibleObjectivesIds list of possible objectives ids
     */
    @Override
    public void showSecretObjectives(List<Integer> possibleObjectivesIds) {
        ui.showPossibleObjectives(possibleObjectivesIds);
    }

    /**
     * Show the error message
     *
     * @param error to show
     */
    @Override
    public void showError(String error) {
        ui.showError(error);
    }

    /**
     * Show the service message
     *
     * @param message to show
     */
    @Override
    public void serviceMessage(String message) {
        ui.showServiceMessage(message);
    }


    /**
     * Show the last turn notification
     */
    @Override
    public void showLastCircle() {
        ui.showLastCircle();
    }

    /**
     * Show the list of winners
     *
     * @param winners list of winners
     */
    @Override
    public void showWinners(List<String> winners) {
        ui.showWinners(winners);
    }

    /**
     * Check if the client is alive
     */
    @Override
    public void isAlive() {
    }

    /**
     * Send ui back to menu
     */
    @Override
    public void backToMenu() {
        ui.backToMenu();
    }

}