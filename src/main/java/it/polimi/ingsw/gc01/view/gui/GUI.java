package it.polimi.ingsw.gc01.view.gui;

import it.polimi.ingsw.gc01.model.player.*;
import it.polimi.ingsw.gc01.network.NetworkClient;
import it.polimi.ingsw.gc01.network.rmi.RmiClient;
import it.polimi.ingsw.gc01.utils.DefaultValue;
import it.polimi.ingsw.gc01.view.UI;
import it.polimi.ingsw.gc01.view.gui.GUIControllers.*;
import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;

public class GUI extends Application implements UI {
    private String playerName;
    private NetworkClient networkClient;
    private Stage stage;
    private FXMLLoader loader;

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

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ignored) {
        }
    }

    private void switchToScene(SceneEnum sceneName, Object... attribute) {
        try {
            loader = new FXMLLoader(getClass().getResource(sceneName.path()));
            Parent root = loader.load();
            GenericController controller = loader.getController();
            if (controller != null) {
                controller.setGUI(this);
                controller.setAttributes(attribute);
            }
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ignored) {
        }
    }

    public void play() {
        switchToScene(SceneEnum.SETUP);
    }

    public void connect(String nickName, String remoteIP, String personalIP, String connectionType) {
        this.playerName = nickName;
        DefaultValue.ServerIp = remoteIP;
        if (connectionType.equals("RMI")) {
            System.setProperty("java.rmi.server.hostname", personalIP);
            try {
                this.networkClient = new RmiClient(playerName, this);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else if (connectionType.equals("SOCKET")) {
            //TODO
        }

        switchToScene(SceneEnum.MENU);
    }

    public void leave() {
        switchToScene(SceneEnum.INTRO);
    }

    public void goBackFromJoinById() {
        switchToScene(SceneEnum.MENU);
    }

    public void createGame() {
        networkClient.createGame();
    }

    public void chooseSecretObjective(int id) {
        networkClient.chooseSecretObjective(id);
        switchToScene(SceneEnum.WAITING_OTHERS);
    }


    public void joinFirstGame() {
        networkClient.joinFirstGame();
    }

    public void askRoomId() {
        switchToScene(SceneEnum.JOIN_BY_ID);
    }

    public void joinGame(String roomId) {
        networkClient.joinGame(roomId);
    }

    public void setReady() {
        networkClient.switchReady();
        switchToScene(SceneEnum.WAITING_OTHERS);
    }


    public void chooseStarter(int choice, int cardId) {
        if (choice == 1) {
            networkClient.playCard(cardId, new Position(0, 0));
        } else {
            networkClient.flipCard(cardId);
            networkClient.playCard(cardId, new Position(0, 0));
        }
        switchToScene(SceneEnum.WAITING_OTHERS);
    }

    public void chooseColor(String color) {
        networkClient.chooseColor(PlayerColor.valueOf(color));
        switchToScene(SceneEnum.WAITING_OTHERS);
    }

    /**
     * Shows the entered room
     *
     * @param roomId of the room
     */
    @Override
    public void showRoom(String roomId) {
        Platform.runLater(() -> switchToScene(SceneEnum.WAITING_ROOM, roomId));
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
                System.out.println(DefaultValue.ANSI_RED + message + DefaultValue.ANSI_RESET);
                Platform.runLater(() -> switchToScene(SceneEnum.MENU));
                break;
            case "PLAY":
                System.out.println(DefaultValue.ANSI_RED + message + DefaultValue.ANSI_RESET);
                //TODO
                break;
            case "DRAW":
                System.out.println(DefaultValue.ANSI_RED + message + DefaultValue.ANSI_RESET);
                //TODO
                break;
            case "GAME":
                System.out.println(DefaultValue.ANSI_RED + message + DefaultValue.ANSI_RESET);
                networkClient.leave();
                System.exit(0);
        }
    }

    /**
     * Show a service message
     *
     * @param message to show
     */
    @Override
    public void showServiceMessage(String message) {

    }

    /**
     * Show that is the last turn
     */
    @Override
    public void showLastCircle() {

    }

    /**
     * Show the game winners
     *
     * @param winners list of winner names
     */
    @Override
    public void showWinners(List<String> winners) {

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

    }

    /**
     * Shows the field of the indicated player
     *
     * @param playerName of the player
     */
    @Override
    public void showField(String playerName) {

    }

    /**
     * Show the points for each player in the room
     *
     * @param points map of playerName, points
     */
    @Override
    public void showPoints(Map<String, Integer> points) {

    }

    /**
     * Show the hand to the player
     *
     * @param handIds list of card ids in the hand
     */
    @Override
    public void showHand(List<Integer> handIds) {

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

    }

    /**
     * Start the game
     */
    @Override
    public void startGame() {

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

    }

    /**
     * Show the center table
     *
     * @param drawableCardsIds map of drawable cards
     */
    @Override
    public void showTable(Map<Integer, Integer> drawableCardsIds) {

    }

    /**
     * Show the common objectives
     *
     * @param objectiveIds list of objective card ids
     */
    @Override
    public void showCommonObjectives(List<Integer> objectiveIds) {

    }

    /**
     * Go back to menu
     */
    @Override
    public void backToMenu() {

    }
}