package it.polimi.ingsw.gc01.view.gui.GUIControllers;


import javafx.fxml.FXML;
import javafx.scene.image.*;


public class ChooseStarterController extends GenericController {

    private int id;

    @FXML
    private ImageView starterCardFront;
    @FXML
    private ImageView starterCardBack;

    @FXML
    private void setFront() {
        gui.chooseStarter(1, id);
    }

    @FXML
    private void setBack() {
        gui.chooseStarter(0, id);
    }

    @Override
    public void setAttribute(Object object) {
        this.id = (Integer) object;
        Image front = new Image(getClass().getResourceAsStream("/images/cards/Front" + id + ".png"));
        Image back = new Image(getClass().getResourceAsStream("/images/cards/Back" + id + ".png"));
        starterCardFront.setImage(front);
        starterCardBack.setImage(back);
    }
}
