package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;

/**
 * Controller that shows the final scene when one or more players win
 */
public class WinnerController extends GenericController {

    @FXML
    private Label titleLabel;
    @FXML
    private Label winner1;
    @FXML
    private Label winner2;
    @FXML
    private Label winner3;
    @FXML
    private Label winner4;

    @FXML
    public void backToMenu() {
        gui.switchToMenu();
    }

    @Override
    public void setAttributes(Object... o) {
        List<String> winners = (List<String>) o[0];
        if (winners.size() == 1) {
            titleLabel.setText("The winner is: ");
            winner1.setText(winners.get(0));
            winner2.setVisible(false);
            winner3.setVisible(false);
            winner4.setVisible(false);
        } else {
            titleLabel.setText("The winners are:");
            for (String winner : winners) {
                if (winner1.getText().isEmpty()) {
                    winner1.setText(winner);
                } else if (winner2.getText().isEmpty()) {
                    winner2.setText(winner);
                } else if (winner3.getText().isEmpty()) {
                    winner3.setText(winner);
                } else {
                    winner4.setText(winner);
                }
            }
        }


    }
}
