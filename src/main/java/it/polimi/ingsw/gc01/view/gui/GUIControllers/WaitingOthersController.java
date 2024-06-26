package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Controller that shows a scene while waiting for other players
 */
public class WaitingOthersController extends GenericController {
    @FXML
    private Label message;

    @Override
    public void setAttributes(Object... o) {
        message.setText(String.valueOf(o[0]));
    }
}