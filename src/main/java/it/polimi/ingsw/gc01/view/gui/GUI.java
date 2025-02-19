package it.polimi.ingsw.gc01.view.gui;

import it.polimi.ingsw.gc01.model.ChatMessage;
import it.polimi.ingsw.gc01.model.player.*;
import it.polimi.ingsw.gc01.network.NetworkClient;
import it.polimi.ingsw.gc01.network.rmi.RmiClient;
import it.polimi.ingsw.gc01.network.socket.SocketClient;
import it.polimi.ingsw.gc01.utils.DefaultValue;
import it.polimi.ingsw.gc01.view.UI;
import it.polimi.ingsw.gc01.view.gui.GUIControllers.*;
import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;

/**
 * GUI class implements the UI interface for graphical user interface interactions in CodexNaturalis game.
 * Extends Application to initialize JavaFX application.
 */
public class GUI extends Application implements UI {
    private String playerName;
    private NetworkClient networkClient;
    private Stage stage;
    private FXMLLoader loader;
    private ClientModel clientModel;
    private ClientFieldGUI field;
    private Map<String, ClientFieldGUI> otherFields;

    /**
     * JavaFX start method, initializes GUI and displays the intro scene.
     *
     * @param primaryStage The primary stage for this application, onto which the application scene can be set.
     */
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        try {
            loader = new FXMLLoader(getClass().getResource(SceneEnum.INTRO.path()));
            Parent root = loader.load();
            IntroController controller = loader.getController();
            controller.setGUI(this);

            Image icon = new Image(getClass().getResource("/images/CodexNaturalis.png").toExternalForm());
            stage.getIcons().add(icon);
            stage.setTitle("CodexNaturalis");
            stage.setOnCloseRequest(event -> {
                System.exit(0);
            });
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ignored) {
        }
    }

    /**
     * Switches the scene to the specified scene name with optional attributes.
     *
     * @param sceneName The SceneEnum value representing the scene to switch to.
     * @param attribute Optional attributes to pass to the scene controller.
     */
    private void switchToScene(SceneEnum sceneName, Object... attribute) {
        try {
            loader = new FXMLLoader(getClass().getResource(sceneName.path()));
            Parent root = loader.load();
            GenericController controller = loader.getController();
            controller.setGUI(this);
            controller.setAttributes(attribute);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ignored) {
        }
    }

    /**
     * Retrieves the player's name.
     *
     * @return The player's name.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Retrieves the client model.
     *
     * @return The client model.
     */
    public ClientModel getClientModel() {
        return clientModel;
    }

    /**
     * Starts the game setup process by switching to the setup scene.
     */
    public void play() {
        switchToScene(SceneEnum.SETUP);
    }

    /**
     * Connects the client to the server with the specified player name, remote IP, personal IP, and connection type.
     *
     * @param playerName     The player's name.
     * @param remoteIP       The remote IP address.
     * @param personalIP     The personal IP address.
     * @param connectionType The type of connection (RMI or SOCKET).
     */
    public void connect(String playerName, String remoteIP, String personalIP, String connectionType) {
        this.playerName = playerName;
        DefaultValue.ServerIp = remoteIP;
        if (connectionType.equals("RMI")) {
            System.setProperty("java.rmi.server.hostname", personalIP);
            try {
                networkClient = new RmiClient(playerName, this);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else if (connectionType.equals("SOCKET")) {
            networkClient = new SocketClient(playerName, this);
        }

        switchToScene(SceneEnum.MENU);
    }

    /**
     * Leaves the current game and switches back to the menu scene.
     */
    public void leave() {
        networkClient.leave();
        switchToScene(SceneEnum.MENU);
    }

    /**
     * Goes back from the join by ID scene to the menu scene.
     */
    public void goBackFromJoinById() {
        switchToScene(SceneEnum.MENU);
    }

    /**
     * Instructs the network client to create a new game.
     */
    public void createGame() {
        networkClient.createGame();
    }

    /**
     * Chooses the secret objective with the specified ID.
     *
     * @param id The ID of the secret objective.
     */
    public void chooseSecretObjective(int id) {
        networkClient.chooseSecretObjective(id);
        clientModel.setSecretObjective(id);
    }

    /**
     * Joins the first available game.
     */
    public void joinFirstGame() {
        networkClient.joinFirstGame();
    }

    /**
     * Switches to the join by ID scene.
     */
    public void askRoomId() {
        switchToScene(SceneEnum.JOIN_BY_ID);
    }

    /**
     * Joins the game with the specified room ID.
     *
     * @param roomId The ID of the room to join.
     */
    public void joinGame(String roomId) {
        networkClient.joinGame(roomId);
    }

    /**
     * Sets the player's readiness status.
     */
    public void setReady() {
        networkClient.switchReady();
    }

    /**
     * Chooses the starter card with the specified choice and card ID.
     *
     * @param choice The choice of starter card.
     * @param cardId The ID of the starter card.
     */
    public void chooseStarter(int choice, int cardId) {
        if (choice == 1) {
            networkClient.playCard(cardId, new Position(0, 0));
        } else {
            networkClient.flipCard(cardId);
            networkClient.playCard(cardId, new Position(0, 0));
        }
    }

    /**
     * Chooses the player's color.
     *
     * @param color The chosen color.
     */
    public void chooseColor(String color) {
        networkClient.chooseColor(PlayerColor.valueOf(color));
    }

    /**
     * Switches to the intro scene.
     */
    public void switchToIntro() {
        switchToScene(SceneEnum.INTRO);
    }

    /**
     * Switches to the menu scene.
     */
    public void switchToMenu() {
        switchToScene(SceneEnum.MENU);
    }

    /**
     * Hides the panes in the lobby.
     * This method is used to hide the panes in the lobby.
     */
    private void hidePanesInLobby() {
        for (int i = 0; i < 4; i++) {
            Pane panePlayerLobby = (Pane) this.stage.getScene().getRoot().lookup("#pane" + i);
            panePlayerLobby.setVisible(false);

            Pane paneReady = (Pane) this.stage.getScene().getRoot().lookup("#ready" + i);
            paneReady.setVisible(false);
        }
    }

    /**
     * Adds lobby player panes for players already in the lobby.
     *
     * @param playersAlreadyIn Map with key as player name and value as ready status.
     */
    private void addLobbyPlayerPanes(Map<String, Boolean> playersAlreadyIn) {
        List<String> playerNames = playersAlreadyIn.keySet().stream().toList();
        for (int i = 0; i < playerNames.size(); i++) {
            Pane panePlayerLobby = (Pane) this.stage.getScene().getRoot().lookup("#pane" + i);
            panePlayerLobby.setVisible(true);

            Text nickname = (Text) this.stage.getScene().getRoot().lookup("#nickName" + i);
            nickname.setText(playerNames.get(i));
            clientModel.setNumOfPlayers(clientModel.getNumOfPlayers() + 1);

            if (playersAlreadyIn.get(playerNames.get(i))) {
                Pane paneReady = (Pane) this.stage.getScene().getRoot().lookup("#ready" + i);
                paneReady.setVisible(true);
            }
        }
    }

    /**
     * Shows the last player who joined the lobby.
     *
     * @param playerName The name of the player who just joined.
     */
    private void showLastJoined(String playerName) {
        if (!playerName.equals(this.playerName)) {
            for (int i = 0; i < 4; i++) {
                Pane newPane = (Pane) this.stage.getScene().getRoot().lookup("#pane" + i);

                if (!newPane.isVisible()) {
                    Text nickname = (Text) this.stage.getScene().getRoot().lookup("#nickName" + i);
                    nickname.setText(playerName);
                    newPane.setVisible(true);
                    clientModel.setNumOfPlayers(clientModel.getNumOfPlayers() + 1);
                    return;
                }

            }
        }
    }

    /**
     * Removes the player's card from the lobby.
     *
     * @param playerName The name of the player who left.
     */
    private void removePlayerCard(String playerName) {
        for (int i = 0; i < 4; i++) {
            Pane panePlayerLobby = (Pane) this.stage.getScene().getRoot().lookup("#pane" + i);
            Pane readyPane = (Pane) this.stage.getScene().getRoot().lookup("#ready" + i);
            Text nickname = (Text) this.stage.getScene().getRoot().lookup("#nickName" + i);
            if (nickname != null && nickname.getText().equals(playerName)) {
                readyPane.setVisible(false);
                panePlayerLobby.setVisible(false);
                clientModel.setNumOfPlayers(clientModel.getNumOfPlayers() - 1);
            }

        }
    }

    /**
     * Changes the readiness status of a player in the lobby.
     *
     * @param playerName The name of the player whose readiness status is to be changed.
     * @param ready      The new readiness status (true if ready, false otherwise).
     */
    private void changeReady(String playerName, boolean ready) {
        for (int i = 0; i < 4; i++) {
            Text nickname = (Text) this.stage.getScene().getRoot().lookup("#nickName" + i);
            if (nickname != null && nickname.getText().equals(playerName)) {
                Pane paneReady = (Pane) this.stage.getScene().getRoot().lookup("#ready" + i);
                paneReady.setVisible(ready);
            }
        }
    }

    /**
     * Switches the scene to the table points scene.
     */
    public void showTablePoints() {
        switchToScene(SceneEnum.TABLE_POINT);
    }

    /**
     * Switches the scene to show the objectives with specified common and secret objectives.
     */
    public void showObjectives() {
        switchToScene(SceneEnum.OBJECTIVES, clientModel.getCommonObjective1(), clientModel.getCommonObjective2(), clientModel.getSecretObjective());
    }

    /**
     * Switches the scene that allows the player to play a card
     */
    public void backToPlay() {
        switchToScene(SceneEnum.PLAY, field.generateField());
    }

    /**
     * Switches the scene back to the other fields scene.
     */
    public void backToOtherFields() {
        String currentPlayer = clientModel.getCurrentPlayer();
        switchToScene(SceneEnum.CURRENT_FIELD, otherFields.get(currentPlayer).generateField(), currentPlayer, false);
    }

    /**
     * Chooses a card to draw from the network client.
     *
     * @param cardId The ID of the card to draw.
     */
    public void chooseCardToDraw(int cardId) {
        networkClient.drawCard(cardId);
    }

    /**
     * Chooses a card to play from the network client.
     *
     * @param cardId   The ID of the card to play.
     * @param front    True if the card should be played on the front side, false if flipped.
     * @param position The position to play the card.
     */
    public void chooseCardToPlay(int cardId, boolean front, Position position) {
        if (!front) {
            networkClient.flipCard(cardId);
        }
        networkClient.playCard(cardId, position);
    }

    /**
     * Switches the scene to show the drawable cards.
     */
    public void showDrawables() {
        switchToScene(SceneEnum.DRAW_CARD, clientModel.getDrawableCardsIds(), false);
    }

    /**
     * Switches the scene to show the other fields.
     */
    public void showOtherFields() {
        switchToScene(SceneEnum.CHOOSE_OTHER_FIELDS, otherFields, field);
    }

    /**
     * Switches the scene to show the current field of the specified player.
     * If the player is the current player, shows their own field; otherwise, shows the other player's field.
     *
     * @param playerName The name of the player whose field to show.
     */
    public void showOtherFields(String playerName) {
        if (this.playerName.equals(playerName)) {
            switchToScene(SceneEnum.CURRENT_FIELD, field.generateField(), this.playerName, true);
        } else {
            switchToScene(SceneEnum.CURRENT_FIELD, otherFields.get(playerName).generateField(), playerName, true);
        }
    }

    /**
     * Shows the entered room
     *
     * @param roomId of the room
     */
    @Override
    public void showRoom(String roomId) {
        this.clientModel = new ClientModel();
        this.field = new ClientFieldGUI(this);
        this.otherFields = new HashMap<>();
        Platform.runLater(() -> switchToScene(SceneEnum.WAITING_ROOM, roomId));
        Platform.runLater(this::hidePanesInLobby);
    }

    /**
     * Shows the players in the room
     *
     * @param playersAlreadyIn map with key the player name and value the ready status
     */
    @Override
    public void showPlayers(Map<String, Boolean> playersAlreadyIn) {
        Platform.runLater(() -> addLobbyPlayerPanes(playersAlreadyIn));
    }

    /**
     * Shows the players that has just joined
     *
     * @param playerName the names of the players that has just joined
     */
    @Override
    public void showPlayerJoined(String playerName) {
        Platform.runLater(() -> showLastJoined(playerName));
    }

    /**
     * Shows the players that has just left
     *
     * @param playerName the names of the players that has just left
     */
    @Override
    public void showPlayerLeft(String playerName) {
        Platform.runLater(() -> removePlayerCard(playerName));
    }

    /**
     * Shows the waiting scene for every client except the one choosing
     *
     * @param playerName of the player choosing
     * @param scene      the name of the scene waiting for
     */
    @Override
    public void showWaitingFor(String playerName, String scene) {
        switch (scene) {
            case "STARTER_SELECTION":
                Platform.runLater(() -> switchToScene(SceneEnum.WAITING_OTHERS, "Waiting for [" + playerName + "] to choose his starter!"));
                break;
            case "COLOR_SELECTION":
                Platform.runLater(() -> switchToScene(SceneEnum.WAITING_OTHERS, "Waiting for [" + playerName + "] to choose his color!"));
                break;
            case "OBJECTIVE_SELECTION":
                Platform.runLater(() -> switchToScene(SceneEnum.WAITING_OTHERS, "Waiting for [" + playerName + "] to choose his objective!"));
                break;
            default:
                break;
        }
    }


    /**
     * Shows an error
     *
     * @param error to show
     */
    @Override
    public void showError(String error) {
        String type = error.substring(0, error.indexOf(" "));
        String message = error.substring(error.indexOf(" ") + 1);
        switch (type) {
            case "MAIN":
                Platform.runLater(() -> switchToScene(SceneEnum.MENU));
                Platform.runLater(() -> showAlert("Main Error", "Main Error", message));
                break;
            case "PLAY":
                Platform.runLater(() -> showAlert("Play Error", "Play Error", message));
                break;
            case "DRAW":
                Platform.runLater(() -> showAlert("Draw Error", "Draw Error", message));
                break;
            case "GAME":
                networkClient.leave();
                Platform.runLater(() -> switchToScene(SceneEnum.MENU));
                Platform.runLater(() -> showAlert("Game Error", "Game Error", message));
        }
    }

    /**
     * Show that is the last turn
     */
    @Override
    public void showLastCircle() {
        Platform.runLater(() -> showAlert("Last Turn", "Last Turn", "This is the last turn of the game!"));
    }

    /**
     * Displays an alert dialog with the specified title, header, and content.
     *
     * @param title   The title of the alert dialog.
     * @param header  The header text of the alert dialog.
     * @param content The content text of the alert dialog.
     */
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Show the game winners
     *
     * @param winners list of winner names
     */
    @Override
    public void showWinners(List<String> winners) {
        Platform.runLater(() -> switchToScene(SceneEnum.WINNER, winners));
    }

    /**
     * Show the starter card
     *
     * @param cardId of the starter card
     */
    @Override
    public void showStarter(int cardId) {
        Platform.runLater(() -> switchToScene(SceneEnum.CHOOSE_STARTER, cardId));
    }

    /**
     * Show the list of available colors
     *
     * @param availableColors in the room
     */
    @Override
    public void showAvailableColors(List<PlayerColor> availableColors) {
        Platform.runLater(() -> switchToScene(SceneEnum.CHOOSE_COLOR, availableColors));
    }

    /**
     * Show the current player
     *
     * @param playerName of the current player
     */
    @Override
    public void showCurrentPlayer(String playerName) {
        clientModel.setCurrentPlayer(playerName);
    }

    /**
     * Shows the field of the indicated player
     *
     * @param playerName of the player
     */
    @Override
    public void showField(String playerName) {
        if (!playerName.equals(this.playerName)) {
            Platform.runLater(() -> switchToScene(SceneEnum.CURRENT_FIELD, otherFields.get(playerName).generateField(), playerName, false));
        }
    }

    /**
     * Show the points for each player in the room
     *
     * @param points map of playerName, points
     */
    @Override
    public void showPoints(Map<String, Integer> points, Map<PlayerColor, String> colors) {
        clientModel.setPoints(points);
        clientModel.setColors(colors);
    }

    /**
     * Show the hand to the player
     *
     * @param handIds list of card ids in the hand
     */
    @Override
    public void showHand(List<Integer> handIds) {
        clientModel.setHandIDs(handIds);
        Platform.runLater(() -> switchToScene(SceneEnum.PLAY, field.generateField()));
    }

    /**
     * Update the hand of the player
     *
     * @param handIds list of card ids in the hand
     */
    @Override
    public void updateHand(List<Integer> handIds) {
        clientModel.setHandIDs(handIds);
    }

    /**
     * Show possible secret objective
     *
     * @param possibleObjectiveIds list of objective card ids
     */
    @Override
    public void showPossibleObjectives(List<Integer> possibleObjectiveIds) {
        Platform.runLater(() -> switchToScene(SceneEnum.CHOOSE_OBJECTIVE, possibleObjectiveIds));
    }

    /**
     * Update the readiness of a player
     *
     * @param playerName of the player to update
     * @param ready      new readiness status
     */
    @Override
    public void updateReady(String playerName, boolean ready) {
        Platform.runLater(() -> changeReady(playerName, ready));
    }

    /**
     * Start the game
     */
    @Override
    public void startGame() {
        this.clientModel = new ClientModel();
        this.field = new ClientFieldGUI(this);
        this.otherFields = new HashMap<>();
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
        if (playerName.equals(this.playerName)) {
            field.playCard(id, front, position);
            field.setAvailablePositions(availablePositions);
        } else {
            if (!otherFields.containsKey(playerName)) {
                otherFields.put(playerName, new ClientFieldGUI(this));
                clientModel.addOtherPlayers(playerName);
            }
            otherFields.get(playerName).playCard(id, front, position);
        }
    }

    /**
     * Show the center table
     *
     * @param drawableCardsIds map of drawable cards
     */
    @Override
    public void showTable(Map<Integer, Integer> drawableCardsIds) {
        Platform.runLater(() -> switchToScene(SceneEnum.DRAW_CARD, drawableCardsIds, true));
    }

    /**
     * Update the drawable cards on the table
     *
     * @param drawableCardsIds the map of the ids with the positions in the table
     */
    @Override
    public void updateTable(Map<Integer, Integer> drawableCardsIds) {
        clientModel.setDrawableCardsIds(drawableCardsIds);
    }

    /**
     * Show the common objectives
     *
     * @param objectiveIds list of objective card ids
     */
    @Override
    public void showCommonObjectives(List<Integer> objectiveIds) {
        clientModel.setCommonObjectives(objectiveIds.get(0), objectiveIds.get(1));
    }

    /**
     * Go back to menu
     */
    @Override
    public void backToMenu() {
        networkClient.leave();
    }

    /**
     * Updates the chat display with a new message.
     *
     * @param newChatMessage The ChatMessage object containing the sender, recipient, and content of the message.
     */
    @Override
    public void updateChat(ChatMessage newChatMessage) {
        StringBuilder prefix = new StringBuilder();

        if (newChatMessage.getSender().equals(playerName)) {
            prefix.append("[YOU");
        } else {
            prefix.append("[").append(newChatMessage.getSender());
        }

        if (newChatMessage.getRecipient().equals("ALL")) {
            prefix.append("] ");
        } else {
            if (newChatMessage.getRecipient().equals(playerName))
                prefix.append(" to YOU] ");
            else
                prefix.append(" to ").append(newChatMessage.getRecipient()).append("] ");
        }
        Text prefixText = new Text(prefix.toString());
        if (newChatMessage.getRecipient().equals("ALL")) {
            prefixText.setFill(Color.ORANGE);
        } else {
            prefixText.setFill(Color.CYAN);
        }
        Text contentText = new Text(": " + newChatMessage.getContent());
        contentText.setFill(Color.WHITE);
        TextFlow textFlow = new TextFlow(prefixText, contentText);
        textFlow.setMaxWidth(280);

        Platform.runLater(() -> clientModel.getMessages().add(textFlow));
    }

    /**
     * Sends a new chat message to the network client.
     *
     * @param content   The content of the chat message.
     * @param recipient The recipient of the chat message ("ALL" for all players or a specific player's name).
     */
    public void newChatMessage(String content, String recipient) {
        ChatMessage newMessage = new ChatMessage(playerName, content, recipient);
        networkClient.newChatMessage(newMessage);
    }
}