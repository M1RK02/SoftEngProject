package it.polimi.ingsw.gc01.view.gui;

import it.polimi.ingsw.gc01.model.player.Position;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;

import java.util.*;

import static java.util.stream.Stream.concat;

public class ClientFieldGUI {

    private final GUI gui;
    private final List<Integer> cards;
    private final Map<Integer, Boolean> side;
    private final Map<Integer, Position> field;
    private final int scaleFactor = 5;
    private final double cardWidth = 993.0 / scaleFactor;
    private final double cardHeight = 662.0 / scaleFactor;
    private final double horizontalGap = 225.0 / scaleFactor;
    private final double verticalGap = 270.0 / scaleFactor;
    private List<Position> availablePositions;
    private int maxY, minX;

    public ClientFieldGUI(GUI gui) {
        this.cards = new ArrayList<>();
        this.side = new HashMap<>();
        this.field = new HashMap<>();
        this.availablePositions = new ArrayList<>();
        this.gui = gui;

    }

    public List<Position> getAvailablePositions() {
        return availablePositions;
    }

    public void setAvailablePositions(List<Position> availablePositions) {
        this.availablePositions = availablePositions;
    }

    public void playCard(int id, boolean front, Position position) {
        cards.add(id);
        side.put(id, front);
        field.put(id, position);
    }

    public Pane generateField() {
        Pane fieldPane = new Pane();
        maxY = concat(field.values().stream(), availablePositions.stream()).mapToInt(Position::getY).max().orElse(40);
        minX = concat(field.values().stream(), availablePositions.stream()).mapToInt(Position::getX).min().orElse(-40);

        for (Position position : availablePositions) {
            ImageView available = new ImageView(new Image(getClass().getResourceAsStream("/images/cards/available.png")));

            available.setOnDragOver(event -> {
                if (event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                event.consume();
            });

            available.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    String id = db.getString();
                    boolean side;
                    int cardId;
                    if (id.contains("Back")) {
                        side = false;
                        cardId = Integer.parseInt(id.substring(4));
                    } else {
                        side = true;
                        cardId = Integer.parseInt(id.substring(5));
                    }
                    gui.chooseCardToPlay(cardId, side, position);
                    success = true;
                }
                event.setDropCompleted(success);
                event.consume();
            });

            available.setFitWidth(cardWidth);
            available.setFitHeight(cardHeight);
            available.setLayoutX(realX(position.getX()));
            available.setLayoutY(realY(position.getY()));
            fieldPane.getChildren().add(available);
        }

        for (Integer card : cards) {
            String imagePath;
            if (side.get(card)) {
                imagePath = "/images/cards/Front" + card + ".png";
            } else {
                imagePath = "/images/cards/Back" + card + ".png";
            }
            ImageView cardPlace = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
            cardPlace.setFitWidth(cardWidth);
            cardPlace.setFitHeight(cardHeight);
            cardPlace.setLayoutX(realX(field.get(card).getX()));
            cardPlace.setLayoutY(realY(field.get(card).getY()));
            fieldPane.getChildren().add(cardPlace);
        }

        return fieldPane;
    }

    private double realX(int x) {
        return (x - minX) * (cardWidth - horizontalGap);
    }

    private double realY(int y) {
        return (maxY - y) * (cardHeight - verticalGap);
    }
}