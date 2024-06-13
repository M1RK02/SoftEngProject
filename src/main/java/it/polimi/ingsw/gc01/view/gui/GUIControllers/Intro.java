package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import it.polimi.ingsw.gc01.view.gui.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Intro {
    @FXML
    private Button button;

    @FXML
    private Label messageLabel;


    private GUI gui;
    @FXML
    private void SwitchToSetUp(ActionEvent event) {
        gui.switchToSetUp();
    }

    public void setGUI(GUI gui){
        this.gui=gui;
    }
}