package it.polimi.ingsw.gc01.view.gui.GUIControllers;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.*;


public class ChooseStarterController extends GenericController {

    private int id;
    int choice = -1;

    @FXML
    private ImageView starterCardFront;
    @FXML
    private ImageView starterCardBack;

    @FXML
    private void setFront() {
        choice = 1;
    }

    @FXML
    private void setBack() {
        choice = 0;
    }

    @FXML
    private void next() {
        if (choice == -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please select a starter card");
            alert.showAndWait();
            return;
        }
        gui.chooseStarter(choice, id);
    }

    @Override
    public void setAttributes(Object... object) {
        this.id = (Integer) object[0];
        Image front = new Image(getClass().getResourceAsStream("/images/cards/Front" + id + ".png"));
        Image back = new Image(getClass().getResourceAsStream("/images/cards/Back" + id + ".png"));
        starterCardFront.setImage(front);
        starterCardBack.setImage(back);
    }
}
