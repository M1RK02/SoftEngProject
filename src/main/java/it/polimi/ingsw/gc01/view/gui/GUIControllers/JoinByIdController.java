package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import it.polimi.ingsw.gc01.view.gui.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

public class JoinByIdController extends GenericController{
    @FXML
    private Button nextButton;

    @FXML
    private TextField roomID;

    @FXML
    private void handleNextButtonAction() {
        String roomId = roomID.getText();
        gui.joinGame(roomId);
    }

}
