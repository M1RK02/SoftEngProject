package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class SetUpController extends GenericController {

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
    private ToggleGroup connectionGroup;

    /**
     *Mette i due toggle button nello stesso gruppo per rende cliccabile solo uno dei due
     */
    @FXML
    public void initialize() {
        rmiToggleButton.setToggleGroup(connectionGroup);
        socketToggleButton.setToggleGroup(connectionGroup);
    }

    @FXML
    private void handleNextButtonAction() {
        // Ottieni i valori dai campi di testo
        String nickname = nicknameField.getText();
        String remoteIP = remoteIPField.getText();
        String personalIP = personalIPField.getText();

        // Ottieni la scelta della connessione
        ToggleButton selectedButton = (ToggleButton) connectionGroup.getSelectedToggle();
        String connectionType = selectedButton != null ? selectedButton.getText() : "None";

        gui.connect(nickname,remoteIP, personalIP,connectionType);
    }
}