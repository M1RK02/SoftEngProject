package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Intro {
    @FXML
    private Button button;

    @FXML
    private Label messageLabel;

    @FXML
    private void handleButtonClick(ActionEvent event) {
        System.out.println("Hai cliccato il bottone!");
        messageLabel.setText("Hai cliccato il bottone!");
    }
}