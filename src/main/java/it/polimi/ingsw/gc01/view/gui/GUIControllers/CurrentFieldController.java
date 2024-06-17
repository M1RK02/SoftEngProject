package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

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
        GridPane gridPane = (GridPane) o[0];
        this.playerName = (String) o[1];
        boolean canGoBack = (boolean) o[2];
        if(!canGoBack){
            goBack.setDisable(true);
            goBack.setVisible(false);
        }
        scrollPane.setContent(gridPane);
        turn.setText("Turn: " + playerName);
    }

    @FXML
    private void goBack(){
        gui.backToPlay();
    }
}
