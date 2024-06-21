package it.polimi.ingsw.gc01.network.socket;

import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.model.player.Position;
import it.polimi.ingsw.gc01.network.NetworkClient;
import it.polimi.ingsw.gc01.network.VirtualView;
import it.polimi.ingsw.gc01.utils.DefaultValue;
import it.polimi.ingsw.gc01.view.UI;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import static it.polimi.ingsw.gc01.network.socket.SocketMessage.*;


public class SocketClient implements NetworkClient, VirtualView {
    private ObjectInputStream input;

    private ObjectOutputStream output;

    private String roomId;

    private String playerName;

    private UI ui;

    private Socket serverSocket;

    public SocketClient(String playerName, UI ui) {
        this.ui = ui;
        this.playerName = playerName;
        connect();
        run();
    }

    private void connect() {
        try {
            serverSocket = new Socket(DefaultValue.ServerIp, DefaultValue.Default_Socket_port);
            ObjectOutputStream outputStream = new ObjectOutputStream(serverSocket.getOutputStream());
            outputStream.flush();
            ObjectInputStream inputStream = new ObjectInputStream(serverSocket.getInputStream());
            this.output = outputStream;
            this. input = inputStream;
            System.out.println("Socket client connected");
        } catch (IOException e) {
            System.out.println("Socket server not working");
        }
    }

    public void run() {
        new Thread(() -> {
            try {
                executeServerMessage();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void executeServerMessage() throws IOException, ClassNotFoundException {
        SocketMessage message = null;
        while ((message = (SocketMessage) input.readObject()) != null) {
            switch (message) {
                case UPDATEROOMIDMESSAGE:
                    updateRoomId((String) input.readObject());
                    break;
            }
        }
    }

    /**
     * @return the room id
     */
    @Override
    public String getRoomId() {
        return "";
    }

    /**
     * Create a new game
     */
    @Override
    public void createGame() {
        try {
            output.writeObject(CREATEGAME);
            output.writeObject(playerName);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Join the indicated game
     *
     * @param roomId of the game to join
     */
    @Override
    public void joinGame(String roomId) {

    }

    /**
     * Join the first available game
     */
    @Override
    public void joinFirstGame() {

    }

    /**
     * Chose the selected color for the player
     *
     * @param color chosen color
     */
    @Override
    public void chooseColor(PlayerColor color) {

    }

    /**
     * Switch the readiness of the player
     */
    @Override
    public void switchReady() {

    }

    /**
     * Choose the secret objective
     *
     * @param cardId of the secret objective
     */
    @Override
    public void chooseSecretObjective(int cardId) {

    }

    /**
     * Flip the indicated card
     *
     * @param cardId of the card to flip
     */
    @Override
    public void flipCard(int cardId) {

    }

    /**
     * Play the card in the selected position
     *
     * @param cardId   of the card to play
     * @param position where to play the card
     */
    @Override
    public void playCard(int cardId, Position position) {

    }

    /**
     * Draw the chosen card
     *
     * @param choice index of the chosen card
     */
    @Override
    public void drawCard(int choice) {

    }

    /**
     * Leave the current room
     */
    @Override
    public void leave() {

    }

    /**
     * Update the room id
     *
     * @param roomId of the room
     * @throws RemoteException
     */
    @Override
    public void updateRoomId(String roomId) throws RemoteException {
        this.roomId = roomId;
        ui.showRoom(roomId);
    }

    /**
     * Show the players in the room
     *
     * @param playerNames the names of the players in the room
     * @throws RemoteException
     */
    @Override
    public void showPlayers(List<String> playerNames) throws RemoteException {

    }

    /**
     * Show the player that has just joined
     *
     * @param playerName the names of the player that has just joined
     * @throws RemoteException
     */
    @Override
    public void showPlayerJoined(String playerName) throws RemoteException {

    }

    /**
     * Show the player that has just left
     *
     * @param playerName the names of the player that has just left
     * @throws RemoteException
     */
    @Override
    public void showPlayerLeft(String playerName) throws RemoteException {

    }

    /**
     * Show the waiting scene for every client except the one choosing
     *
     * @param playerName
     * @param scene
     * @throws RemoteException
     */
    @Override
    public void showWaitingFor(String playerName, String scene) throws RemoteException {

    }

    /**
     * Start the game
     *
     * @throws RemoteException
     */
    @Override
    public void startGame() throws RemoteException {

    }

    /**
     * Update the current player
     *
     * @param playerName of the new current player
     * @throws RemoteException
     */
    @Override
    public void updateCurrentPlayer(String playerName) throws RemoteException {

    }

    /**
     * Show the starter card to the player
     *
     * @param cardId of the starter card
     * @throws RemoteException
     */
    @Override
    public void showStarter(int cardId) throws RemoteException {

    }

    /**
     * Show available colors to the player
     *
     * @param availableColors list of available colors
     * @throws RemoteException
     */
    @Override
    public void showAvailableColors(List<PlayerColor> availableColors) throws RemoteException {

    }

    /**
     * Update readiness of a player
     *
     * @param playerName of the player to update
     * @param ready      new status of the player, true if ready, false otherwise
     * @throws RemoteException
     */
    @Override
    public void updateReady(String playerName, boolean ready) throws RemoteException {

    }

    /**
     * Show common objectives
     *
     * @param objectivesIds list of common objective ids
     * @throws RemoteException
     */
    @Override
    public void showCommonObjectives(List<Integer> objectivesIds) throws RemoteException {

    }

    /**
     * Show the center table
     *
     * @param drawableCardsIds map of drawable card ids
     * @throws RemoteException
     */
    @Override
    public void showTable(Map<Integer, Integer> drawableCardsIds) throws RemoteException {

    }

    /**
     * Update the drawable cards on the table
     *
     * @param drawableCardsIds the map of the ids with the positions in the table
     * @throws RemoteException
     */
    @Override
    public void updateTable(Map<Integer, Integer> drawableCardsIds) throws RemoteException {

    }

    /**
     * Show the hand
     *
     * @param cardIds list of card ids in the hand
     * @throws RemoteException
     */
    @Override
    public void showHand(List<Integer> cardIds) throws RemoteException {

    }

    /**
     * Show the field of the indicated player
     *
     * @param playerName to show the field
     * @throws RemoteException
     */
    @Override
    public void showField(String playerName) throws RemoteException {

    }

    /**
     * Show the points for each player
     *
     * @param points      map of playerName, points
     * @param tablePoints
     * @throws RemoteException
     */
    @Override
    public void showPoints(Map<String, Integer> points, Map<PlayerColor, Integer> tablePoints) throws RemoteException {

    }

    /**
     * Show the possible secret objectives
     *
     * @param possibleObjectivesIds list of possible objectives ids
     * @throws RemoteException
     */
    @Override
    public void showSecretObjectives(List<Integer> possibleObjectivesIds) throws RemoteException {

    }

    /**
     * Show the error message
     *
     * @param error to show
     * @throws RemoteException
     */
    @Override
    public void showError(String error) throws RemoteException {

    }

    /**
     * Show the service message
     *
     * @param message to show
     * @throws RemoteException
     */
    @Override
    public void serviceMessage(String message) throws RemoteException {

    }

    /**
     * Show the last turn notification
     *
     * @throws RemoteException
     */
    @Override
    public void showLastCircle() throws RemoteException {

    }

    /**
     * Show the list of winners
     *
     * @param winners list of winners
     * @throws RemoteException
     */
    @Override
    public void showWinners(List<String> winners) throws RemoteException {

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
    public void updateField(String playerName, int id, boolean front, Position position, List<Position> availablePositions) throws RemoteException {

    }

    /**
     * Check if the client is alive
     *
     * @throws RemoteException
     */
    @Override
    public void isAlive() throws RemoteException {

    }

    /**
     * Go back to menu
     *
     * @throws RemoteException
     */
    @Override
    public void backToMenu() throws RemoteException {

    }
}
