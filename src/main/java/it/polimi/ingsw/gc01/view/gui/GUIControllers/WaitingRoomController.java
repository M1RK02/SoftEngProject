package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class WaitingRoomController extends GenericController {
    @FXML
    private Text roomId;

    @Override
    public void setAttributes(Object... o) {
        roomId.setText((String) o[0]);
    }

    @FXML
    private void handleReadyButton() {
        gui.setReady();
    }

    @FXML
    private void handleLeaveButton() {
        gui.leave();
    }
}