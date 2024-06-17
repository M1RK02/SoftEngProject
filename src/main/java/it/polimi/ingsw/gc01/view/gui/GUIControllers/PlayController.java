package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import it.polimi.ingsw.gc01.view.gui.ClientModel;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;


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
        GridPane gridPane = (GridPane) o[1];
        String currentPlayer = clientModel.getCurrentPlayer();
        turn.setText("Turn: " + currentPlayer);
        points.setText("Points: " + clientModel.getPoints().get(currentPlayer));
        scrollPane.setContent(gridPane);

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
    private void showTablePoints(){
        gui.showTablePoints();
    }

    @FXML
    private void showObjectives(){
        gui.showObjectives();
    }

    @FXML
    private void playRandom(){
        gui.chooseCardToPlay();
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
