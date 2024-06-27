package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import it.polimi.ingsw.gc01.view.gui.ClientFieldGUI;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.Map;

/**
 * Controller to choose the player's field to view
 */
public class ChooseOtherFieldsController extends GenericController {

    @FXML
    private Button player1;
    @FXML
    private Button player2;
    @FXML
    private Button player3;
    @FXML
    private Button player4;

    @Override
    public void setAttributes(Object... o) {
        Map<String, ClientFieldGUI> fields = (Map<String, ClientFieldGUI>) o[0];
        player4.setText(gui.getPlayerName());

        player1.setVisible(false);
        player1.setDisable(true);
        player2.setVisible(false);
        player2.setDisable(true);
        player3.setVisible(false);
        player3.setDisable(true);

        for (String playerName : fields.keySet()) {
            if (!player1.isVisible()) {
                player1.setVisible(true);
                player1.setDisable(false);
                player1.setText(playerName);
                continue;
            }
            if (!player2.isVisible()) {
                player2.setVisible(true);
                player2.setDisable(false);
                player2.setText(playerName);
                continue;
            }
            if (!player3.isVisible()) {
                player3.setVisible(true);
                player3.setDisable(false);
                player3.setText(playerName);
            }
        }
    }

    @FXML
    private void chooseField(Event e) {
        Button button = (Button) e.getSource();
        String playerName = button.getText();
        if (playerName.equals(gui.getPlayerName()) && playerName.equals(gui.getClientModel().getCurrentPlayer())) {
            gui.backToPlay();
        } else {
            gui.showOtherFields(playerName);
        }
    }

    @FXML
    private void goBack() {
        if (gui.getPlayerName().equals(gui.getClientModel().getCurrentPlayer())) {
            gui.backToPlay();
        } else {
            gui.backToOtherFields();
        }
    }
}