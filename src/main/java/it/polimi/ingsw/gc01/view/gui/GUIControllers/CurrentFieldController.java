package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import it.polimi.ingsw.gc01.view.gui.ClientModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

public class CurrentFieldController extends GenericController{
    private String playerName;
    @FXML
    private Label turn;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Button goBack;

    @Override
    public void setAttributes(Object... o) {
        Pane pane = (Pane) o[0];
        this.playerName = (String) o[1];
        boolean canGoBack = (boolean) o[2];
        if(!canGoBack){
            goBack.setDisable(true);
            goBack.setVisible(false);
        }
        scrollPane.setContent(pane);
        turn.setText("Turn: " + playerName);
    }

    @FXML
    private void goBack(){
        ClientModel clientModel = gui.getClientModel();
        if (gui.getPlayerName().equals(clientModel.getCurrentPlayer())){
            gui.backToPlay();
        } else {
            gui.backToOtherFields();
        }
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
}
