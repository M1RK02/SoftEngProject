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
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;

public class GUI extends Application implements UI {
    private String playerName;
    private NetworkClient networkClient;
    private Stage stage;
    private FXMLLoader loader;
    private ClientModel clientModel;
    private ClientFieldGUI field;
    private Map<String, ClientFieldGUI> otherFields;
    private boolean isStarted = false;

    @Override
    public void start(Stage primaryStage) {
        this.clientModel = new ClientModel();
        this.field = new ClientFieldGUI();
        this.otherFields = new HashMap<>();
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
            controller.setGUI(this);
            controller.setAttributes(attribute);
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
        networkClient.leave();
        switchToScene(SceneEnum.MENU);
    }

    public void goBackFromJoinById() {
        switchToScene(SceneEnum.MENU);
    }

    public void createGame() {
        networkClient.createGame();
    }

    public void chooseSecretObjective(int id) {
        networkClient.chooseSecretObjective(id);
        clientModel.setSecretObjective(id);
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
    }

    public void chooseStarter(int choice, int cardId) {
        if (choice == 1) {
            networkClient.playCard(cardId, new Position(0, 0));
        } else {
            networkClient.flipCard(cardId);
            networkClient.playCard(cardId, new Position(0, 0));
        }
        //switchToScene(SceneEnum.WAITING_OTHERS, "Waiting for others to choose their starter");
    }

    public void chooseColor(String color) {
        networkClient.chooseColor(PlayerColor.valueOf(color));
        //switchToScene(SceneEnum.WAITING_OTHERS, "Waiting for others to choose their color");
    }

    public void switchToIntro(){
        switchToScene(SceneEnum.INTRO);
    }

    /**
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

    private void addLobbyPlayerPanes(List<String> playerNames){
        for (int i = 0; i < playerNames.size(); i++) {
            Pane panePlayerLobby = (Pane) this.stage.getScene().getRoot().lookup("#pane" + i);
            panePlayerLobby.setVisible(true);

            Text nickname = (Text) this.stage.getScene().getRoot().lookup("#nickName" + i);
            nickname.setText(playerNames.get(i));
            clientModel.setNumOfPlayers(clientModel.getNumOfPlayers() + 1);
        }
    }

    private void showLastJoined(String playerName) {
        if (!playerName.equals(this.playerName)) {
            for (int i = 0; i < 4; i++) {
                Pane newPane = (Pane) this.stage.getScene().getRoot().lookup("#pane" + i);

                if (!newPane.isVisible()){
                    Text nickname = (Text) this.stage.getScene().getRoot().lookup("#nickName" + i);
                    nickname.setText(playerName);
                    newPane.setVisible(true);
                    clientModel.setNumOfPlayers(clientModel.getNumOfPlayers() + 1);
                    return;
                }

            }
        }
    }

    private void removePlayerCard(String playerName) {
        for (int i = 0; i < 4; i++) {
            Pane panePlayerLobby = (Pane) this.stage.getScene().getRoot().lookup("#pane" + i);
            Text nickname = (Text) this.stage.getScene().getRoot().lookup("#nickName" + i);
            if (nickname != null && nickname.getText().equals(playerName)) {
                panePlayerLobby.setVisible(false);
                clientModel.setNumOfPlayers(clientModel.getNumOfPlayers() - 1);
            }

        }
    }

    private void changeReady(String playerName, boolean ready) {
        for (int i = 0; i < clientModel.getNumOfPlayers(); i++) {
            Text nickname = (Text) this.stage.getScene().getRoot().lookup("#nickName" + i);
            if (nickname != null && nickname.getText().equals(playerName)) {
                Pane paneReady = (Pane) this.stage.getScene().getRoot().lookup("#ready" + i);
                paneReady.setVisible(ready);
            }
        }
    }

    public void showTablePoints(){
        switchToScene(SceneEnum.TABLE_POINT, clientModel.getPawnPoints());
    }

    public void showObjectives(){
        switchToScene(SceneEnum.OBJECTIVES, clientModel.getCommonObjective1(), clientModel.getCommonObjective2(), clientModel.getSecretObjective());
    }

    public void backToPlay(){
        switchToScene(SceneEnum.PLAY, clientModel, field.generateField());
    }

    public void chooseCardToDraw(int cardId){
        networkClient.drawCard(cardId);
    }

    public void chooseCardToPlay(){
        int card = clientModel.getHandIDs().getFirst();
        networkClient.flipCard(card);
        networkClient.playCard(card, field.getAvailablePositions().get(0));
        field.playCard(card, false, field.getAvailablePositions().get(0));
    }

    public void showDrawables(){
        switchToScene(SceneEnum.DRAW_CARD, clientModel.getDrawableCardsIds(), false);
    }

    public void showOtherFields(){
        switchToScene(SceneEnum.CHOOSE_OTHER_FIELDS, otherFields);
    }

    public void showOtherFields(String playerName){
        switchToScene(SceneEnum.CURRENT_FIELD, otherFields.get(playerName).generateField(), playerName, true);
    }

    /**
     * Shows the entered room
     *
     * @param roomId of the room
     */
    @Override
    public void showRoom(String roomId) {
        Platform.runLater(() -> switchToScene(SceneEnum.WAITING_ROOM, roomId));
        Platform.runLater(this::hidePanesInLobby);
    }

    /**
     * Shows the players in the room
     *
     * @param playerNames the names of the players in the room
     */
    @Override
    public void showPlayers(List<String> playerNames) {
        Platform.runLater(() -> addLobbyPlayerPanes(playerNames));
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
    public void showPoints(Map<String, Integer> points, Map<PlayerColor, Integer> tablePoints) {
        clientModel.setPoints(points);
        clientModel.setPawnPoints(tablePoints);
        //this.switchToScene(SceneEnum.TABLE_POINT, clientModel.getPawnPoints());
    }

    /**
     * Show the hand to the player
     *
     * @param handIds list of card ids in the hand
     */
    @Override
    public void showHand(List<Integer> handIds) {
        clientModel.setHandIDs(handIds);
        Platform.runLater(() -> switchToScene(SceneEnum.PLAY, clientModel, field.generateField()));
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
        isStarted = true;
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
        if(playerName.equals(this.playerName)){
            field.playCard(id, front, position);
            field.setAvailablePositions(availablePositions);
        } else {
            if(!otherFields.containsKey(playerName)){
                otherFields.put(playerName, new ClientFieldGUI());
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

    }
}