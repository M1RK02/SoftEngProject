package it.polimi.ingsw.gc01.network.socket;

import it.polimi.ingsw.gc01.controller.MainController;
import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.model.player.Position;
import it.polimi.ingsw.gc01.network.VirtualView;
import it.polimi.ingsw.gc01.network.rmi.actions.*;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

import static it.polimi.ingsw.gc01.network.socket.SocketServerMessage.*;
import static it.polimi.ingsw.gc01.network.socket.SocketClientMessage.*;

public class ClientHandler implements VirtualView {
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private MainController mainController;
    private BlockingQueue<Action> actions;
    private String playerName;
    private String roomId;

    public ClientHandler(MainController mainController,BlockingQueue<Action> actions, Socket clientSocket) throws IOException {
       this.actions = actions;
       this.mainController = mainController;
       this.input = new ObjectInputStream(clientSocket.getInputStream());
       this.output = new ObjectOutputStream(clientSocket.getOutputStream());
    }

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
                        System.err.println("L'inserimento dell'azione joinGame nella coda è stato interrotto.");
                    }
                    break;
                case JOIN_GAME:
                    this.roomId = (String) input.readObject();
                    JoinGameAction joinGame = new JoinGameAction(playerName, this, roomId);
                    try {
                        actions.put(joinGame);
                    } catch (InterruptedException e) {
                        System.err.println("L'inserimento dell'azione joinGame nella coda è stato interrotto.");
                    }
                    break;
                case JOIN_FIRST_GAME:
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
                        System.err.println("L'inserimento dell'azione changeReady nella coda è stato interrotto.");
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
        this.roomId = roomId;
        try {
            output.writeObject(UPDATE_ROOM_ID);
            output.writeObject(roomId);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Show the players in the room
     *
     * @param playerNames the names of the players in the room
     * @throws RemoteException
     */
    @Override
    public void showPlayers(List<String> playerNames) throws RemoteException {
        try {
            output.writeObject(SHOW_PLAYERS);
            output.writeObject(playerNames);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Show the player that has just joined
     *
     * @param playerName the names of the player that has just joined
     * @throws RemoteException
     */
    @Override
    public void showPlayerJoined(String playerName) throws RemoteException {
        try {
            output.writeObject(SHOW_PLAYER_JOINED);
            output.writeObject(playerName);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Show the player that has just left
     *
     * @param playerName the names of the player that has just left
     * @throws RemoteException
     */
    @Override
    public void showPlayerLeft(String playerName) throws RemoteException {
        try {
            output.writeObject(SHOW_PLAYER_LEFT);
            output.writeObject(playerName);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        try {
            output.writeObject(SHOW_WAITING_FOR);
            output.writeObject(playerName);
            output.writeObject(scene);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Start the game
     *
     * @throws RemoteException
     */
    @Override
    public void startGame() throws RemoteException {
        try {
            output.writeObject(START_GAME);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Update the current player
     *
     * @param playerName of the new current player
     * @throws RemoteException
     */
    @Override
    public void updateCurrentPlayer(String playerName) throws RemoteException {
        try {
            output.writeObject(UPDATE_CURRENT_PLAYER);
            output.writeObject(playerName);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Show the starter card to the player
     *
     * @param cardId of the starter card
     * @throws RemoteException
     */
    @Override
    public void showStarter(int cardId) throws RemoteException {
        try {
            output.writeObject(SHOW_STARTER);
            output.writeObject(cardId);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Show available colors to the player
     *
     * @param availableColors list of available colors
     * @throws RemoteException
     */
    @Override
    public void showAvailableColors(List<PlayerColor> availableColors) throws RemoteException {
        try {
            output.writeObject(SHOW_AVAILABLE_COLORS);
            output.writeObject(availableColors);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        try {
            output.writeObject(UPDATE_READY);
            output.writeObject(playerName);
            output.writeObject(ready);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Show common objectives
     *
     * @param objectivesIds list of common objective ids
     * @throws RemoteException
     */
    @Override
    public void showCommonObjectives(List<Integer> objectivesIds) throws RemoteException {
        try {
            output.writeObject(SHOW_COMMON_OBJECTIVES);
            output.writeObject(objectivesIds);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Show the center table
     *
     * @param drawableCardsIds map of drawable card ids
     * @throws RemoteException
     */
    @Override
    public void showTable(Map<Integer, Integer> drawableCardsIds) throws RemoteException {
        try {
            output.writeObject(SHOW_TABLE);
            output.writeObject(drawableCardsIds);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Update the drawable cards on the table
     *
     * @param drawableCardsIds the map of the ids with the positions in the table
     * @throws RemoteException
     */
    @Override
    public void updateTable(Map<Integer, Integer> drawableCardsIds) throws RemoteException {
        try {
            output.writeObject(UPDATE_TABLE);
            output.writeObject(drawableCardsIds);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Show the hand
     *
     * @param cardIds list of card ids in the hand
     * @throws RemoteException
     */
    @Override
    public void showHand(List<Integer> cardIds) throws RemoteException {
        try {
            output.writeObject(SHOW_HAND);
            output.writeObject(cardIds);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Update the hand
     *
     * @param cardIds list of card ids in the hand
     * @throws RemoteException
     */
    @Override
    public void updateHand(List<Integer> cardIds) throws RemoteException {
        try {
            output.writeObject(UPDATE_HAND);
            output.writeObject(cardIds);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Show the field of the indicated player
     *
     * @param playerName to show the field
     * @throws RemoteException
     */
    @Override
    public void showField(String playerName) throws RemoteException {
        try {
            output.writeObject(SHOW_FIELD);
            output.writeObject(playerName);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        try {
            output.writeObject(SHOW_PONTS);
            output.writeObject(points);
            output.writeObject(tablePoints);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Show the possible secret objectives
     *
     * @param possibleObjectivesIds list of possible objectives ids
     * @throws RemoteException
     */
    @Override
    public void showSecretObjectives(List<Integer> possibleObjectivesIds) throws RemoteException {
        try {
            output.writeObject(SHOW_SECRET_OBJECTIVES);
            output.writeObject(possibleObjectivesIds);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Show the error message
     *
     * @param error to show
     * @throws RemoteException
     */
    @Override
    public void showError(String error) throws RemoteException {
        try {
            output.writeObject(SHOW_ERROR);
            output.writeObject(error);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Show the service message
     *
     * @param message to show
     * @throws RemoteException
     */
    @Override
    public void serviceMessage(String message) throws RemoteException {
        try {
            output.writeObject(SERVICE_MESSAGE);
            output.writeObject(message);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Show the last turn notification
     *
     * @throws RemoteException
     */
    @Override
    public void showLastCircle() throws RemoteException {
        try {
            output.writeObject(SHOW_LAST_CIRCLE);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Show the list of winners
     *
     * @param winners list of winners
     * @throws RemoteException
     */
    @Override
    public void showWinners(List<String> winners) throws RemoteException {
        try {
            output.writeObject(SHOW_WINNERS);
            output.writeObject(winners);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
    public void updateField(String playerName, int id, boolean front, Position position, List<Position> availablePositions) throws RemoteException {
        try {
            output.writeObject(UPDATE_FIELD);
            output.writeObject(playerName);
            output.writeObject(id);
            output.writeObject(front);
            output.writeObject(position);
            output.writeObject(availablePositions);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Check if the client is alive
     *
     * @throws RemoteException
     */
    @Override
    public void isAlive() throws RemoteException {
        try {
            output.writeObject(IS_ALIVE);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Go back to menu
     *
     * @throws RemoteException
     */
    @Override
    public void backToMenu() throws RemoteException {
        try {
            output.writeObject(BACK_TO_MENU);
            output.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}