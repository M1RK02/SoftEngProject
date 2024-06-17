package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.List;

public class WinnerController extends GenericController{

    @FXML
    private VBox winnersList;


    @FXML
    public void playNewGame(){
        gui.switchToMenu();
    }

    @Override
    public void setAttributes(Object... o) {
        List<String> winners = (List<String>) o[0];
        winnersList.getChildren().clear(); // Pulisce la lista corrente

        for (String winner : winners) {
            Label label = new Label(winner);
            label.setFont(new Font("PingFang SC Regular", 36));
            winnersList.getChildren().add(label);
        }
    }
}
