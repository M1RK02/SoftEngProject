package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.*;

/**
 * Allows the player to choose his nickname, the server IP and the type of connection he wants to use
 */
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
     * Check if the input is a valid IP address
     *
     * @param input string to check
     * @return true if is a valid IP, otherwise false
     */
    private static boolean isValidIP(String input) {
        List<String> parsed;
        parsed = Arrays.stream(input.split("\\.")).toList();
        if (parsed.size() != 4) {
            return false;
        }
        for (String part : parsed) {
            try {
                Integer.parseInt(part);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    /**
     * Mette i due toggle button nello stesso gruppo per rende cliccabile solo uno dei due
     */
    @FXML
    public void initialize() {
        rmiToggleButton.setToggleGroup(connectionGroup);
        socketToggleButton.setToggleGroup(connectionGroup);
        // Aggiungi listener per cambiare lo stile quando viene selezionato
        connectionGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (oldToggle != null) {
                ((ToggleButton) oldToggle).getStyleClass().remove("toggle-button-pressed");
            }
            if (newToggle != null) {
                ((ToggleButton) newToggle).getStyleClass().add("toggle-button-pressed");
            }
        });
    }

    @FXML
    private void handleNextButtonAction() {
        // Ottieni i valori dai campi di testo
        String nickname = nicknameField.getText();
        String remoteIP = remoteIPField.getText();
        String personalIP = personalIPField.getText();

        if (nickname.isEmpty()) {
            // Mostra un messaggio di errore se uno dei campi è vuoto
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Name can't be empty");
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
            return;
        }

        if (!remoteIP.isEmpty() && !isValidIP(remoteIP)) {
            // Mostra un messaggio di errore se l'IP remoto non è valido
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid remote IP");
            alert.setContentText("Please insert a valid IP address");
            alert.showAndWait();
            return;
        }

        if (!personalIP.isEmpty() && !isValidIP(personalIP)) {
            // Mostra un messaggio di errore se l'IP personale non è valido
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid personal IP");
            alert.setContentText("Please insert a valid IP address");
            alert.showAndWait();
            return;
        }

        // Ottieni la scelta della connessione
        ToggleButton selectedButton = (ToggleButton) connectionGroup.getSelectedToggle();
        if (selectedButton == null) {
            // Mostra un messaggio di errore se non è stata selezionata una connessione
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Connection type not selected");
            alert.setContentText("Please select a connection type");
            alert.showAndWait();
            return;
        }
        String connectionType = selectedButton.getText();
        gui.connect(nickname, remoteIP, personalIP, connectionType);
    }
}