package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import it.polimi.ingsw.gc01.view.gui.ClientModel;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.util.List;

/**
 * Controls the action of playing a card on the player's field
 */
public class PlayController extends GenericController {
    @FXML
    private Label turn;
    @FXML
    private Label points;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ImageView handLeft;
    @FXML
    private ImageView handCenter;
    @FXML
    private ImageView handRight;
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
        ClientModel clientModel = gui.getClientModel();
        String currentPlayer = clientModel.getCurrentPlayer();
        ObservableList<TextFlow> messages = clientModel.getMessages();
        List<String> otherPlayers = clientModel.getOtherPlayers();
        messagesView.setItems(messages);
        messages.addListener((ListChangeListener<TextFlow>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    Platform.runLater(() -> messagesView.scrollTo(messages.size() - 1));
                }
            }
        });
        turn.setText("Turn: " + currentPlayer);
        points.setText("Points: " + clientModel.getPoints().get(currentPlayer));
        scrollPane.setContent(pane);

        Image left = new Image(getClass().getResourceAsStream("/images/cards/Front" + clientModel.getHandIDs().get(0) + ".png"));
        Image center = new Image(getClass().getResourceAsStream("/images/cards/Front" + clientModel.getHandIDs().get(1) + ".png"));
        Image right = new Image(getClass().getResourceAsStream("/images/cards/Front" + clientModel.getHandIDs().get(2) + ".png"));

        handLeft.setImage(left);
        handCenter.setImage(center);
        handRight.setImage(right);

        handLeft.setId("Front" + clientModel.getHandIDs().get(0));
        handCenter.setId("Front" + clientModel.getHandIDs().get(1));
        handRight.setId("Front" + clientModel.getHandIDs().get(2));

        playersChoice.getItems().clear();
        playersChoice.getItems().add(new MenuItem("ALL"));
        for (String player : otherPlayers) {
            playersChoice.getItems().add(new MenuItem(player));
        }
        //Quando si seleziona un giocatore, il menu viene aggiornato con il nome del giocatore selezionato
        playersChoice.getItems().forEach(item -> item.setOnAction(event -> playersChoice.setText(item.getText())));

    }

    @FXML
    private void dragDetected(Event event) {
        ImageView imageView = (ImageView) event.getSource();
        Dragboard db = imageView.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        content.putString(imageView.getId());
        db.setContent(content);
        db.setDragView(imageView.getImage());
        event.consume();
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
    private void flipCard(Event e) {
        ImageView card = (ImageView) e.getSource();
        String cardID = card.getId();
        if (cardID.contains("Front")) {
            cardID = "Back" + cardID.substring(5);
        } else {
            cardID = "Front" + cardID.substring(4);
        }
        Image flipped = new Image(getClass().getResourceAsStream("/images/cards/" + cardID + ".png"));
        card.setImage(flipped);
        card.setId(cardID);
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
