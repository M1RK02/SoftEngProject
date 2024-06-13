package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import it.polimi.ingsw.gc01.view.gui.GUI;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class WaitingRoomController extends GenericController {
    @FXML
    private Text roomId;

    public void setRoomId(String roomId) {
        this.roomId.setText(roomId);
    }

    @FXML
    private void handleReadyButton() {
        gui.setReady();
    }

    @FXML
    private void handleLeaveButton() {
        gui.leaveGame();
    }
}