package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import it.polimi.ingsw.gc01.view.gui.ClientModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;


public class PlayController extends GenericController{
    @FXML
    private Label turn;

    @FXML
    private Label points;

    @Override
    public void setAttributes(Object... o) {
        ClientModel clientModel = (ClientModel) o[0];
        String currentPlayer = clientModel.getCurrentPlayer();
        turn.setText("Turn: " + currentPlayer);
        points.setText("Points: " + clientModel.getPoints().get(currentPlayer));
    }
}
