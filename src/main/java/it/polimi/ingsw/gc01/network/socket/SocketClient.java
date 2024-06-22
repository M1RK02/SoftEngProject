package it.polimi.ingsw.gc01.network.socket;

import it.polimi.ingsw.gc01.model.player.*;
import it.polimi.ingsw.gc01.network.NetworkClient;
import it.polimi.ingsw.gc01.utils.DefaultValue;
import it.polimi.ingsw.gc01.view.UI;

import java.io.*;
import java.net.Socket;
import java.util.*;

import static it.polimi.ingsw.gc01.network.socket.SocketClientMessage.*;


public class SocketClient implements NetworkClient {
    private final String playerName;
    private final UI ui;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private String roomId;
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
            this.input = inputStream;
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
        SocketServerMessage message = null;
        while ((message = (SocketServerMessage) input.readObject()) != null) {
            switch (message) {
                case UPDATE_ROOM_ID:
                    updateRoomId((String) input.readObject());
                    break;
                case SHOW_PLAYERS:
                    showPlayers((List<String>) input.readObject());
                    break;
                case SHOW_PLAYER_JOINED:
                    showPlayerJoined((String) input.readObject());
                    break;
                case SHOW_PLAYER_LEFT:
                    showPlayerLeft((String) input.readObject());
                    break;
                case SHOW_WAITING_FOR:
                    showWaitingFor((String) input.readObject(), (String) input.readObject());
                    break;
                case START_GAME:
                    startGame();
                    break;
                case UPDATE_CURRENT_PLAYER:
                    updateCurrentPlayer((String) input.readObject());
                    break;
                case SHOW_STARTER:
                    showStarter((int) input.readObject());
                    break;
                case SHOW_AVAILABLE_COLORS:
                    showAvailableColors((List<PlayerColor>) input.readObject());
                    break;
                case UPDATE_READY:
                    updateReady((String) input.readObject(), (boolean) input.readObject());
                    break;
                case SHOW_COMMON_OBJECTIVES:
                    showCommonObjectives((List<Integer>) input.readObject());
                    break;
                case SHOW_TABLE:
                    showTable((Map<Integer, Integer>) input.readObject());
                    break;
                case UPDATE_TABLE:
                    updateTable((Map<Integer, Integer>) input.readObject());
                    break;
                case SHOW_HAND:
                    showHand((List<Integer>) input.readObject());
                    break;
                case UPDATE_HAND:
                    updateHand((List<Integer>) input.readObject());
                    break;
                case SHOW_FIELD:
                    showField((String) input.readObject());
                    break;
                case SHOW_PONTS:
                    showPoints((Map<String, Integer>) input.readObject(), (Map<PlayerColor, Integer>) input.readObject());
                    break;
                case SHOW_SECRET_OBJECTIVES:
                    showSecretObjectives((List<Integer>) input.readObject());
                    break;
                case SHOW_ERROR:
                    showError((String) input.readObject());
                    break;
                case SERVICE_MESSAGE:
                    serviceMessage((String) input.readObject());
                    break;
                case SHOW_LAST_CIRCLE:
                    showLastCircle();
                    break;
                case SHOW_WINNERS:
                    showWinners((List<String>) input.readObject());
                    break;
                case UPDATE_FIELD:
                    updateField((String) input.readObject(), (int) input.readObject(), (boolean) input.readObject(), (Position) input.readObject(), (List<Position>) input.readObject());
                    break;
                case IS_ALIVE:
                    isAlive();
                    break;
                case BACK_TO_MENU:
                    backToMenu();
                    break;
            }
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
     * Create a new game
     */
    @Override
    public void createGame() {
        try {
            output.writeObject(CREATE_GAME);
            output.writeObject(playerName);
            output.flush();
        } catch (IOException e) {
            System.err.println("Server socket not working!");
        }
    }

    /**
     * Join the indicated game
     *
     * @param roomId of the game to join
     */
    @Override
    public void joinGame(String roomId) {
        try {
            output.writeObject(JOIN_GAME);
            output.writeObject(playerName);
            output.writeObject(roomId);
            output.flush();
        } catch (IOException e) {
            System.err.println("Server socket not working!");
        }
    }

    /**
     * Join the first available game
     */
    @Override
    public void joinFirstGame() {
        try {
            output.writeObject(JOIN_FIRST_GAME);
            output.writeObject(playerName);
            output.flush();
        } catch (IOException e) {
            System.err.println("Server socket not working!");
        }
    }

    /**
     * Chose the selected color for the player
     *
     * @param color chosen color
     */
    @Override
    public void chooseColor(PlayerColor color) {
        try {
            output.writeObject(CHOOSE_COLOR);
            output.writeObject(color);
            output.flush();
        } catch (IOException e) {
            System.err.println("Server socket not working!");
        }
    }

    /**
     * Switch the readiness of the player
     */
    @Override
    public void switchReady() {
        try {
            output.writeObject(SWITCH_READY);
            output.flush();
        } catch (IOException e) {
            System.err.println("Server socket not working!");
        }
    }

    /**
     * Choose the secret objective
     *
     * @param cardId of the secret objective
     */
    @Override
    public void chooseSecretObjective(int cardId) {
        try {
            output.writeObject(CHOOSE_SECRET_OBJECTIVE);
            output.writeObject(cardId);
            output.flush();
        } catch (IOException e) {
            System.err.println("Server socket not working!");
        }
    }

    /**
     * Flip the indicated card
     *
     * @param cardId of the card to flip
     */
    @Override
    public void flipCard(int cardId) {
        try {
            output.writeObject(FLIP_CARD);
            output.writeObject(cardId);
            output.flush();
        } catch (IOException e) {
            System.err.println("Server socket not working!");
        }
    }

    /**
     * Play the card in the selected position
     *
     * @param cardId   of the card to play
     * @param position where to play the card
     */
    @Override
    public void playCard(int cardId, Position position) {
        try {
            output.writeObject(PLAY_CARD);
            output.writeObject(cardId);
            output.writeObject(position);
            output.flush();
        } catch (IOException e) {
            System.err.println("Server socket not working!");
        }
    }

    /**
     * Draw the chosen card
     *
     * @param choice index of the chosen card
     */
    @Override
    public void drawCard(int choice) {
        try {
            output.writeObject(DRAW_CARD);
            output.writeObject(choice);
            output.flush();
        } catch (IOException e) {
            System.err.println("Server socket not working!");
        }
    }

    /**
     * Leave the current room
     */
    @Override
    public void leave() {
        try {
            output.writeObject(LEAVE);
            output.flush();
        } catch (IOException e) {
            System.err.println("Server socket not working!");
        }
    }

    /**
     * Update the room id
     *
     * @param roomId of the room
     */
    public void updateRoomId(String roomId) {
        this.roomId = roomId;
        ui.showRoom(roomId);
    }

    /**
     * Show the players in the room
     *
     * @param playerNames the names of the players in the room
     */
    public void showPlayers(List<String> playerNames) {
        ui.showPlayers(playerNames);
    }

    /**
     * Show the player that has just joined
     *
     * @param playerName the names of the player that has just joined
     */
    public void showPlayerJoined(String playerName) {
        ui.showPlayerJoined(playerName);
    }

    /**
     * Show the player that has just left
     *
     * @param playerName the names of the player that has just left
     */
    public void showPlayerLeft(String playerName) {
        ui.showPlayerLeft(playerName);
    }

    /**
     * Show the waiting scene for every client except the one choosing
     *
     * @param playerName
     * @param scene
     */
    public void showWaitingFor(String playerName, String scene) {
        if (!playerName.equals(this.playerName)) {
            ui.showWaitingFor(playerName, scene);
        }
    }

    /**
     * Start the game
     */
    public void startGame() {
        ui.startGame();
    }

    /**
     * Update the current player
     *
     * @param playerName of the new current player
     */
    public void updateCurrentPlayer(String playerName) {
        ui.showCurrentPlayer(playerName);
    }

    /**
     * Show the starter card to the player
     *
     * @param cardId of the starter card
     */
    public void showStarter(int cardId) {
        ui.showStarter(cardId);
    }

    /**
     * Show available colors to the player
     *
     * @param availableColors list of available colors
     */
    public void showAvailableColors(List<PlayerColor> availableColors) {
        ui.showAvailableColors(availableColors);
    }

    /**
     * Update readiness of a player
     *
     * @param playerName of the player to update
     * @param ready      new status of the player, true if ready, false otherwise
     */
    public void updateReady(String playerName, boolean ready) {
        ui.updateReady(playerName, ready);
    }

    /**
     * Show common objectives
     *
     * @param objectivesIds list of common objective ids
     */
    public void showCommonObjectives(List<Integer> objectivesIds) {
        ui.showCommonObjectives(objectivesIds);
    }

    /**
     * Show the center table
     *
     * @param drawableCardsIds map of drawable card ids
     */
    public void showTable(Map<Integer, Integer> drawableCardsIds) {
        ui.showTable(drawableCardsIds);
    }

    /**
     * Update the drawable cards on the table
     *
     * @param drawableCardsIds the map of the ids with the positions in the table
     */
    public void updateTable(Map<Integer, Integer> drawableCardsIds) {
        ui.updateTable(drawableCardsIds);
    }

    /**
     * Show the hand
     *
     * @param handIds list of card ids in the hand
     */
    public void showHand(List<Integer> handIds) {
        ui.showHand(handIds);
    }

    /**
     * Update the hand
     *
     * @param cardIds list of card ids in the hand
     */
    public void updateHand(List<Integer> cardIds) {
        ui.updateHand(cardIds);
    }

    /**
     * Show the field of the indicated player
     *
     * @param playerName to show the field
     */
    public void showField(String playerName) {
        ui.showField(playerName);
    }

    /**
     * Show the points for each player
     *
     * @param points      map of playerName, points
     * @param tablePoints
     */
    public void showPoints(Map<String, Integer> points, Map<PlayerColor, Integer> tablePoints) {
        ui.showPoints(points, tablePoints);
    }

    /**
     * Show the possible secret objectives
     *
     * @param possibleObjectivesIds list of possible objectives ids
     */
    public void showSecretObjectives(List<Integer> possibleObjectivesIds) {
        ui.showPossibleObjectives(possibleObjectivesIds);
    }

    /**
     * Show the error message
     *
     * @param error to show
     */
    public void showError(String error) {
        ui.showError(error);
    }

    /**
     * Show the service message
     *
     * @param message to show
     */
    public void serviceMessage(String message) {
        ui.showServiceMessage(message);
    }

    /**
     * Show the last turn notification
     */
    public void showLastCircle() {
        ui.showLastCircle();
    }

    /**
     * Show the list of winners
     *
     * @param winners list of winners
     */
    public void showWinners(List<String> winners) {
        ui.showWinners(winners);
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
    public void updateField(String playerName, int id, boolean front, Position position, List<Position> availablePositions) {
        ui.updateField(playerName, id, front, position, availablePositions);
    }

    /**
     * Check if the client is alive
     */
    public void isAlive() {
    }

    /**
     * Go back to menu
     */
    public void backToMenu() {
        ui.backToMenu();
    }
}
