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
        if (roomId.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Room ID is empty");
            alert.setContentText("Please insert a room ID");
            alert.showAndWait();
            return;
        }
        if (roomId.length() != 5){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Room ID is not valid");
            alert.setContentText("Please insert a valid room ID");
            alert.showAndWait();
            return;

        }

        gui.joinGame(roomId.toUpperCase());
    }

    @FXML
    private void handleGoBackButton() {
        gui.goBackFromJoinById();
    }


}
