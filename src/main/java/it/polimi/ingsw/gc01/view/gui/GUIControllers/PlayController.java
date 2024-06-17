package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import it.polimi.ingsw.gc01.view.gui.ClientModel;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;


public class PlayController extends GenericController{
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

    @Override
    public void setAttributes(Object... o) {

        ClientModel clientModel = (ClientModel) o[0];
        Pane pane = (Pane) o[1];
        String currentPlayer = clientModel.getCurrentPlayer();
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
    }

    @FXML
    private void dragDetected(Event event){
        ImageView imageView = (ImageView) event.getSource();
        Dragboard db = imageView.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        content.putImage(imageView.getImage());
        content.putString(imageView.getId());// Carry the ID
        db.setContent(content);

        // Set the drag view to show the image under the mouse cursor
        db.setDragView(imageView.getImage());

        event.consume();
    }

    @FXML
    private void showTablePoints(){
        gui.showTablePoints();
    }

    @FXML
    private void showObjectives(){
        gui.showObjectives();
    }

    @FXML
    private void showDrawables(){
        gui.showDrawables();
    }

    @FXML
    private void showOtherFields(){
        gui.showOtherFields();
    }



    @FXML
    private void flipCard(Event e){
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

}
