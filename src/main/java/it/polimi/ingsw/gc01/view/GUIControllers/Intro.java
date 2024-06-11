package it.polimi.ingsw.gc01.view.GUIControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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