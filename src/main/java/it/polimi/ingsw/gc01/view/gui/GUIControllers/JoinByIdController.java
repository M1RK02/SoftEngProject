package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class JoinByIdController extends GenericController {
    @FXML
    private Button nextButton;

    @FXML
    private TextField roomID;

    @FXML
    private void handleNextButtonAction() {
        String roomId;
        roomId = roomID.getText();

        gui.joinGame(roomId);
    }

    @FXML
    private void handleGoBackButton() {
        gui.goBackFromJoinById();
    }


}
