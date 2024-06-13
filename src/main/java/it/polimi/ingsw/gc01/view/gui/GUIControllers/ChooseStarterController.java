package it.polimi.ingsw.gc01.view.gui.GUIControllers;


import it.polimi.ingsw.gc01.view.gui.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;



public class ChooseStarterController extends GenericController {

    private int id;

    @FXML
    private ImageView starterCardFront;
    @FXML
    private ImageView starterCardBack;

    @FXML
    private void setFront(){
        gui.chooseStarter(1, id);
    }

    @FXML
    private void setBack(){
        gui.chooseStarter(0, id);
    }

    public void setId(int id) {
        this.id = id;
        Image front = new Image(getClass().getResourceAsStream("/cardfront/"+id+".png"));
        Image back = new Image(getClass().getResourceAsStream("/cardback/"+id+".png"));
        starterCardFront.setImage(front);
        starterCardBack.setImage(back);
    }
}
