package it.polimi.ingsw.gc01.network.socket;

import it.polimi.ingsw.gc01.controller.*;
import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.model.player.Position;
import it.polimi.ingsw.gc01.network.VirtualView;
import it.polimi.ingsw.gc01.network.rmi.actions.Action;
import it.polimi.ingsw.gc01.network.rmi.actions.CreateGameAction;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class ClientHandler implements VirtualView {
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private BlockingQueue<Action> actions;
    private String roomId;

    public ClientHandler(BlockingQueue<Action> actions, Socket clientSocket) throws IOException {
       this.actions = actions;
       this.input = new ObjectInputStream(clientSocket.getInputStream());
       this.output = new ObjectOutputStream(clientSocket.getOutputStream());
    }

    public void executeMessages() {
        SocketMessage message = null;
        while (true) {
            try {
                if (((message = (SocketMessage) input.readObject()) != null)) {
                    switch (message) {
                        case CREATEGAME:
                            Action createGame = new CreateGameAction("piana", this);
                            actions.put(createGame);
                    }
                };
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    /**
     * Update the room id
     *
     * @param roomId of the room
     * @throws RemoteException
     */
    @Override
    public void updateRoomId(String roomId) throws RemoteException {

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

    //TODO METODI DELLA VIRTUALVIEW CHE MANDANO IL MESSAGGIO AL CLIENT
}