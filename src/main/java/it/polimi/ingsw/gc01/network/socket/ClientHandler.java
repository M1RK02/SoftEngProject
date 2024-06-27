package it.polimi.ingsw.gc01.network.socket;

import it.polimi.ingsw.gc01.controller.MainController;
import it.polimi.ingsw.gc01.model.ChatMessage;
import it.polimi.ingsw.gc01.model.player.*;
import it.polimi.ingsw.gc01.network.VirtualView;
import it.polimi.ingsw.gc01.network.actions.*;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.BlockingQueue;

import static it.polimi.ingsw.gc01.network.socket.SocketServerMessage.*;

/**
 * Classes which handles Client requests for Socket connection
 */
public class ClientHandler implements VirtualView {
    private final ObjectInputStream input;
    private final ObjectOutputStream output;
    private final MainController mainController;
    private final BlockingQueue<Action> actions;
    private String playerName;
    private String roomId;

    /**
     * Constructs a ClientHandler for a Client
     * @param mainController main controller
     * @param actions actions to be executed by the server
     * @param clientSocket  clientSocket
     * @throws IOException exception
     */
    public ClientHandler(MainController mainController, BlockingQueue<Action> actions, Socket clientSocket) throws IOException {
        this.actions = actions;
        this.mainController = mainController;
        this.input = new ObjectInputStream(clientSocket.getInputStream());
        this.output = new ObjectOutputStream(clientSocket.getOutputStream());
    }

    /**
     * Processes incoming messages from the client and enqueues corresponding actions that will be executed by server
     * @throws IOException IOException if an I/O error occurs while reading from the input stream.
     * @throws ClassNotFoundException  ClassNotFoundException if a class of a serialized object cannot be found.
     */
    public void executeClientMessages() throws IOException, ClassNotFoundException {
        SocketClientMessage message;
        int cardId;
        while ((message = (SocketClientMessage) input.readObject()) != null) {
            switch (message) {
                case CREATE_GAME:
                    this.playerName = (String) input.readObject();
                    Action createGame = new CreateGameAction(playerName, this);
                    try {
                        actions.put(createGame);
                    } catch (InterruptedException e) {
                        System.err.println("L'inserimento dell'azione createGame nella coda è stato interrotto.");
                    }
                    break;
                case JOIN_GAME:
                    this.playerName = (String) input.readObject();
                    this.roomId = (String) input.readObject();
                    JoinGameAction joinGame = new JoinGameAction(playerName, this, roomId);
                    try {
                        actions.put(joinGame);
                    } catch (InterruptedException e) {
                        System.err.println("L'inserimento dell'azione joinGame nella coda è stato interrotto.");
                    }
                    break;
                case JOIN_FIRST_GAME:
                    this.playerName = (String) input.readObject();
                    JoinFirstGameAction joinFirstGame = new JoinFirstGameAction(playerName, this);
                    try {
                        actions.put(joinFirstGame);
                    } catch (InterruptedException e) {
                        System.err.println("L'inserimento dell'azione joinFirstGame nella coda è stato interrotto.");
                    }
                    break;
                case CHOOSE_COLOR:
                    PlayerColor color = (PlayerColor) input.readObject();
                    ChooseColorAction chooseColor = new ChooseColorAction(playerName, mainController.getRooms().get(roomId), color);
                    try {
                        actions.put(chooseColor);
                    } catch (InterruptedException e) {
                        System.err.println("L'inserimento dell'azione chooseColor nella coda è stato interrotto.");
                    }
                    break;
                case SWITCH_READY:
                    SwitchReadyAction switchReady = new SwitchReadyAction(playerName, mainController.getRooms().get(roomId));
                    try {
                        actions.put(switchReady);
                    } catch (InterruptedException e) {
                        System.err.println("L'inserimento dell'azione switchReady nella coda è stato interrotto.");
                    }
                    break;
                case CHOOSE_SECRET_OBJECTIVE:
                    cardId = (Integer) input.readObject();
                    ChooseSecretObjectiveAction chooseSecretObjective = new ChooseSecretObjectiveAction(playerName, mainController.getRooms().get(roomId), cardId);
                    try {
                        actions.put(chooseSecretObjective);
                    } catch (InterruptedException e) {
                        System.err.println("L'inserimento dell'azione chooseSecretObjective nella coda è stato interrotto.");
                    }
                    break;
                case FLIP_CARD:
                    cardId = (Integer) input.readObject();
                    FlipCardAction flipCard = new FlipCardAction(playerName, mainController.getRooms().get(roomId), cardId);
                    try {
                        actions.put(flipCard);
                    } catch (InterruptedException e) {
                        System.err.println("L'inserimento dell'azione fliCard nella coda è stato interrotto.");
                    }
                    break;
                case PLAY_CARD:
                    cardId = (Integer) input.readObject();
                    Position position = (Position) input.readObject();
                    PlayCardAction playCard = new PlayCardAction(playerName, mainController.getRooms().get(roomId), cardId, position);
                    try {
                        actions.put(playCard);
                    } catch (InterruptedException e) {
                        System.err.println("L'inserimento dell'azione playCard nella coda è stato interrotto.");
                    }
                    break;
                case DRAW_CARD:
                    int choice = (Integer) input.readObject();
                    DrawCardAction drawCard = new DrawCardAction(playerName, mainController.getRooms().get(roomId), choice);
                    try {
                        actions.put(drawCard);
                    } catch (InterruptedException e) {
                        System.err.println("L'inserimento dell'azione drawCard nella coda è stato interrotto.");
                    }
                    break;
                case LEAVE:
                    LeaveAction leave = new LeaveAction(playerName, mainController.getRooms().get(roomId));
                    try {
                        actions.put(leave);
                    } catch (InterruptedException e) {
                        System.err.println("L'inserimento dell'azione leave nella coda è stato interrotto.");
                    }
                    break;
                case CHAT_MESSAGE:
                    ChatMessage chatMessage = (ChatMessage) input.readObject();
                    NewChatMessageAction newChatMessage = new NewChatMessageAction(playerName, mainController.getRooms().get(roomId), chatMessage);
                    try {
                        actions.put(newChatMessage);
                    } catch (InterruptedException e) {
                        System.err.println("L'inserimento dell'azione new chat message nella coda è stato interrotto.");
                    }
                    break;
            }

        }
    }

    /**
     * Update the room id
     *
     * @param roomId of the room
     */
    @Override
    public void updateRoomId(String roomId) throws IOException {
        this.roomId = roomId;
        output.writeObject(UPDATE_ROOM_ID);
        output.writeObject(roomId);
        output.flush();
    }

    /**
     * Show the players in the room
     *
     * @param playersAlreadyIn map with key the player name and value the ready status
     */
    @Override
    public void showPlayers(Map<String, Boolean> playersAlreadyIn) throws IOException {
        output.writeObject(SHOW_PLAYERS);
        output.writeObject(playersAlreadyIn);
        output.flush();
    }

    /**
     * Show the player that has just joined
     *
     * @param playerName the names of the player that has just joined
     */
    @Override
    public void showPlayerJoined(String playerName) throws IOException {
        output.writeObject(SHOW_PLAYER_JOINED);
        output.writeObject(playerName);
        output.flush();
    }

    /**
     * Show the player that has just left
     *
     * @param playerName the names of the player that has just left
     */
    @Override
    public void showPlayerLeft(String playerName) throws IOException {
        output.writeObject(SHOW_PLAYER_LEFT);
        output.writeObject(playerName);
        output.flush();
    }

    /**
     * Show the waiting scene for every client except the one choosing
     *
     * @param playerName
     * @param scene
     */
    @Override
    public void showWaitingFor(String playerName, String scene) throws IOException {
        output.writeObject(SHOW_WAITING_FOR);
        output.writeObject(playerName);
        output.writeObject(scene);
        output.flush();
    }

    /**
     * Start the game
     */
    @Override
    public void startGame() throws IOException {
        output.writeObject(START_GAME);
        output.flush();
    }

    /**
     * Update the current player
     *
     * @param playerName of the new current player
     */
    @Override
    public void updateCurrentPlayer(String playerName) throws IOException {
        output.writeObject(UPDATE_CURRENT_PLAYER);
        output.writeObject(playerName);
        output.flush();
    }

    /**
     * Show the starter card to the player
     *
     * @param cardId of the starter card
     */
    @Override
    public void showStarter(int cardId) throws IOException {
        output.writeObject(SHOW_STARTER);
        output.writeObject(cardId);
        output.flush();
    }

    /**
     * Show available colors to the player
     *
     * @param availableColors list of available colors
     */
    @Override
    public void showAvailableColors(List<PlayerColor> availableColors) throws IOException {
        output.writeObject(SHOW_AVAILABLE_COLORS);
        output.writeObject(availableColors);
        output.flush();
    }

    /**
     * Update readiness of a player
     *
     * @param playerName of the player to update
     * @param ready      new status of the player, true if ready, false otherwise
     */
    @Override
    public void updateReady(String playerName, boolean ready) throws IOException {
        output.writeObject(UPDATE_READY);
        output.writeObject(playerName);
        output.writeObject(ready);
        output.flush();
    }

    /**
     * Show common objectives
     *
     * @param objectivesIds list of common objective ids
     */
    @Override
    public void showCommonObjectives(List<Integer> objectivesIds) throws IOException {
        output.writeObject(SHOW_COMMON_OBJECTIVES);
        output.writeObject(objectivesIds);
        output.flush();
    }

    /**
     * Show the center table
     *
     * @param drawableCardsIds map of drawable card ids
     */
    @Override
    public void showTable(Map<Integer, Integer> drawableCardsIds) throws IOException {
        output.writeObject(SHOW_TABLE);
        output.writeObject(drawableCardsIds);
        output.flush();
    }

    /**
     * Update the drawable cards on the table
     *
     * @param drawableCardsIds the map of the ids with the positions in the table
     */
    @Override
    public void updateTable(Map<Integer, Integer> drawableCardsIds) throws IOException {
        output.writeObject(UPDATE_TABLE);
        output.writeObject(drawableCardsIds);
        output.flush();
    }

    /**
     * Show the hand
     *
     * @param cardIds list of card ids in the hand
     */
    @Override
    public void showHand(List<Integer> cardIds) throws IOException {
        output.writeObject(SHOW_HAND);
        output.writeObject(cardIds);
        output.flush();
    }

    /**
     * Update the hand
     *
     * @param cardIds list of card ids in the hand
     */
    @Override
    public void updateHand(List<Integer> cardIds) throws IOException {
        output.writeObject(UPDATE_HAND);
        output.writeObject(cardIds);
        output.flush();
    }

    /**
     * Show the field of the indicated player
     *
     * @param playerName to show the field
     */
    @Override
    public void showField(String playerName) throws IOException {
        output.writeObject(SHOW_FIELD);
        output.writeObject(playerName);
        output.flush();
    }

    /**
     * Show the points for each player
     *
     * @param points     map of playerName, points
     * @param colors    map of color, playerName
     */
    @Override
    public void showPoints(Map<String, Integer> points, Map<PlayerColor, String> colors) throws IOException {
        output.writeObject(SHOW_POINTS);
        output.writeObject(points);
        output.writeObject(colors);
        output.flush();
    }

    /**
     * Show the possible secret objectives
     *
     * @param possibleObjectivesIds list of possible objectives ids
     */
    @Override
    public void showSecretObjectives(List<Integer> possibleObjectivesIds) throws IOException {
        output.writeObject(SHOW_SECRET_OBJECTIVES);
        output.writeObject(possibleObjectivesIds);
        output.flush();
    }

    /**
     * Show the error message
     *
     * @param error to show
     */
    @Override
    public void showError(String error) throws IOException {
        output.writeObject(SHOW_ERROR);
        output.writeObject(error);
        output.flush();
    }

    /**
     * Show the last turn notification
     */
    @Override
    public void showLastCircle() throws IOException {
        output.writeObject(SHOW_LAST_CIRCLE);
        output.flush();
    }

    /**
     * Show the list of winners
     *
     * @param winners list of winners
     */
    @Override
    public void showWinners(List<String> winners) throws IOException {
        output.writeObject(SHOW_WINNERS);
        output.writeObject(winners);
        output.flush();
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
    public void updateField(String playerName, int id, boolean front, Position position, List<Position> availablePositions) throws IOException {
        output.writeObject(UPDATE_FIELD);
        output.writeObject(playerName);
        output.writeObject(id);
        output.writeObject(front);
        output.writeObject(position);
        output.writeObject(availablePositions);
        output.flush();
    }

    @Override
    public void updateChat(ChatMessage newChatMessage) throws IOException {
        output.writeObject(CHAT_MESSAGE);
        output.writeObject(newChatMessage);
        output.flush();
    }

    /**
     * Check if the client is alive
     */
    @Override
    public void isAlive() throws IOException {
        output.writeObject(IS_ALIVE);
        output.flush();
    }

    /**
     * Go back to menu
     */
    @Override
    public void backToMenu() throws IOException {
        output.writeObject(BACK_TO_MENU);
        output.flush();
    }
}