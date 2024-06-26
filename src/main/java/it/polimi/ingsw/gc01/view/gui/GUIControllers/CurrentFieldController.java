package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import it.polimi.ingsw.gc01.view.gui.ClientModel;
import it.polimi.ingsw.gc01.view.gui.GUI;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.util.List;
/**
 * Controller that manages the view when is not your turn
 */
public class CurrentFieldController extends GenericController {
    private String playerName;
    @FXML
    private Label turn;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Button goBack;
    @FXML
    private Pane chat;
    @FXML
    private ListView<TextFlow> messagesView;
    @FXML
    private MenuButton playersChoice;
    @FXML
    private TextField messageField;

    @Override
    public void setAttributes(Object... o) {
        Pane pane = (Pane) o[0];
        this.playerName = (String) o[1];
        boolean canGoBack = (boolean) o[2];

        if (!canGoBack) {
            goBack.setDisable(true);
            goBack.setVisible(false);
        }
        scrollPane.setContent(pane);
        turn.setText("Turn: " + playerName);

        List<String> otherPlayers = gui.getClientModel().getOtherPlayers();
        ObservableList<TextFlow> messages = gui.getClientModel().getMessages();
        messagesView.setItems(messages);
        messages.addListener((ListChangeListener<TextFlow>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    Platform.runLater(() -> messagesView.scrollTo(messages.size()-1));
                }
            }
        });

        playersChoice.getItems().clear();
        playersChoice.getItems().add(new MenuItem("ALL"));
        for (String player : otherPlayers) {
            playersChoice.getItems().add(new MenuItem(player));
        }
        //Quando si seleziona un giocatore, il menu viene aggiornato con il nome del giocatore selezionato
        playersChoice.getItems().forEach(item -> item.setOnAction(event -> playersChoice.setText(item.getText())));
    }

    @FXML
    private void goBack() {
        ClientModel clientModel = gui.getClientModel();
        if (gui.getPlayerName().equals(clientModel.getCurrentPlayer())) {
            gui.backToPlay();
        } else {
            gui.backToOtherFields();
        }
    }

    @FXML
    private void showTablePoints() {
        gui.showTablePoints();
    }

    @FXML
    private void showObjectives() {
        gui.showObjectives();
    }

    @FXML
    private void showDrawables() {
        gui.showDrawables();
    }

    @FXML
    private void showOtherFields() {
        gui.showOtherFields();
    }

    @FXML
    private void showChat() {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(0.5));
        translateTransition.setNode(chat);
        translateTransition.setFromX(-320);
        translateTransition.setToX(0);
        translateTransition.play();
    }

    @FXML
    private void hideChat() {
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.seconds(0.5));
        translateTransition.setNode(chat);
        translateTransition.setFromX(0);
        translateTransition.setToX(-320);
        translateTransition.play();
    }

    @FXML
    private void sendMessage() {
        String recipient = playersChoice.getText();
        String newMessage = messageField.getText();
        if (!newMessage.isEmpty()) {
            gui.newChatMessage(newMessage, recipient);
        }
        messageField.setText("");
    }
}
