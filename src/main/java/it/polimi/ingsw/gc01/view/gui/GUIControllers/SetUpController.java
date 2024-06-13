package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import it.polimi.ingsw.gc01.view.gui.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.util.Map;

public class SetUpController {

    @FXML
    private TextField nicknameField;

    @FXML
    private TextField remoteIPField;

    @FXML
    private TextField personalIPField;

    @FXML
    private ToggleButton rmiToggleButton;

    @FXML
    private ToggleButton socketToggleButton;

    @FXML
    private Button nextButton;

    @FXML
    private ToggleGroup connectionGroup;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private GUI gui;

    /**
     *Mette i due toggle button nello stesso gruppo per rende cliccabile solo uno dei due
     */
    @FXML
    public void initialize() {
        rmiToggleButton.setToggleGroup(connectionGroup);
        socketToggleButton.setToggleGroup(connectionGroup);
    }

    @FXML
    private void handleNextButtonAction(ActionEvent event) {
        // Ottieni i valori dai campi di testo
        String nickname = nicknameField.getText();
        String remoteIP = remoteIPField.getText();
        String personalIP = personalIPField.getText();

        // Ottieni la scelta della connessione
        ToggleButton selectedButton = (ToggleButton) connectionGroup.getSelectedToggle();
        String connectionType = selectedButton != null ? selectedButton.getText() : "None";

        gui.switchToMenu(nickname,remoteIP, personalIP,connectionType );

    }
    public void setGUI(GUI gui){
        this.gui=gui;
    }
}
