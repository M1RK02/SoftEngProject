package it.polimi.ingsw.gc01.view.gui.GUIControllers;

import it.polimi.ingsw.gc01.view.gui.ClientModel;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.text.Text;

import java.util.Map;

public class DrawCardController extends GenericController {

    @FXML
    private ImageView resourceDeck;
    @FXML
    private ImageView resourceLeft;
    @FXML
    private ImageView resourceRight;
    @FXML
    private ImageView goldenDeck;
    @FXML
    private ImageView goldenLeft;
    @FXML
    private ImageView goldenRight;
    @FXML
    private Text title;
    @FXML
    private Button goBack;

    @FXML
    private void drawCard(Event event) {
        ImageView img = (ImageView) event.getSource();
        gui.chooseCardToDraw(Integer.parseInt(img.getId()));
    }

    @FXML
    private void goBack() {
        ClientModel clientModel = gui.getClientModel();
        if (gui.getPlayerName().equals(clientModel.getCurrentPlayer())) {
            gui.backToPlay();
        } else {
            gui.backToOtherFields();
        }
    }


    @Override
    public void setAttributes(Object... o) {
        Map<Integer, Integer> drawableCardsIds = (Map<Integer, Integer>) o[0];
        boolean canDraw = (boolean) o[1];

        if (!canDraw) {
            resourceDeck.setDisable(true);
            resourceLeft.setDisable(true);
            resourceRight.setDisable(true);
            goldenDeck.setDisable(true);
            goldenLeft.setDisable(true);
            goldenRight.setDisable(true);
            title.setVisible(false);
        } else {
            goBack.setDisable(true);
            goBack.setVisible(false);
            title.setVisible(true);
        }

        if (drawableCardsIds.get(1) != null) {
            Image resourceDeckImage = new Image(getClass().getResourceAsStream("/images/cards/Back" + drawableCardsIds.get(1) + ".png"));
            resourceDeck.setImage(resourceDeckImage);
        } else {
            resourceDeck.setImage(null);
            resourceDeck.setOpacity(0);
            resourceDeck.setDisable(true);
        }

        if (drawableCardsIds.get(2) != null) {
            Image resourceLeftImage = new Image(getClass().getResourceAsStream("/images/cards/Front" + drawableCardsIds.get(2) + ".png"));
            resourceLeft.setImage(resourceLeftImage);
        } else {
            resourceLeft.setImage(null);
            resourceLeft.setOpacity(0);
            resourceLeft.setDisable(true);
        }

        if (drawableCardsIds.get(3) != null) {
            Image resourceRightImage = new Image(getClass().getResourceAsStream("/images/cards/Front" + drawableCardsIds.get(3) + ".png"));
            resourceRight.setImage(resourceRightImage);
        } else {
            resourceRight.setImage(null);
            resourceRight.setOpacity(0);
            resourceRight.setDisable(true);
        }

        if (drawableCardsIds.get(4) != null) {
            Image goldenDeckImage = new Image(getClass().getResourceAsStream("/images/cards/Back" + drawableCardsIds.get(4) + ".png"));
            goldenDeck.setImage(goldenDeckImage);
        } else {
            goldenDeck.setImage(null);
            goldenDeck.setOpacity(0);
            goldenDeck.setDisable(true);
        }

        if (drawableCardsIds.get(5) != null) {
            Image goldenLeftImage = new Image(getClass().getResourceAsStream("/images/cards/Front" + drawableCardsIds.get(5) + ".png"));
            goldenLeft.setImage(goldenLeftImage);
        } else {
            goldenLeft.setImage(null);
            goldenLeft.setOpacity(0);
            goldenLeft.setDisable(true);
        }

        if (drawableCardsIds.get(6) != null) {
            Image goldenRightImage = new Image(getClass().getResourceAsStream("/images/cards/Front" + drawableCardsIds.get(6) + ".png"));
            goldenRight.setImage(goldenRightImage);
        } else {
            goldenRight.setImage(null);
            goldenRight.setOpacity(0);
            goldenRight.setDisable(true);
        }
    }
}
