package it.polimi.ingsw.gc01.view.gui;

import it.polimi.ingsw.gc01.model.player.Position;
import javafx.geometry.*;
import javafx.scene.image.*;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Stream.concat;

public class ClientFieldGUI {

   private final GUI gui;
   private final List<Integer> cards;
   private final Map<Integer, Boolean> side;
   private final Map<Integer, Position> field;
   private List<Position> availablePositions;

   private double cardWidth = 993.0/5;
   private double cardHeight = 662.0/5;
   private double horizontalGap = 225.0/5;
   private double verticalGap = 270.0/5;

   public ClientFieldGUI(GUI gui){
       this.cards= new ArrayList<>();
       this.side = new HashMap<>();
       this.field = new HashMap<>();
       this.availablePositions= new ArrayList<>();
       this.gui= gui;

   }

   public List<Position> getAvailablePositions() {
      return availablePositions;
   }

   public void playCard( int id, boolean front, Position position){
      cards.add(id);
      side.put(id, front);
      field.put(id, position);
   }

   public void setAvailablePositions(List<Position> availablePositions){
      this.availablePositions=availablePositions;
   }

   public GridPane generateField() {
      GridPane grid = new GridPane();
      grid.setHgap(horizontalGap);
      grid.setVgap(verticalGap);
      grid.setPadding(new Insets(verticalGap, horizontalGap, verticalGap, horizontalGap));
      int maxY = concat(field.values().stream(), availablePositions.stream()).mapToInt(Position::getY).max().orElse(40);
      int minX = concat(field.values().stream(), availablePositions.stream()).mapToInt(Position::getX).min().orElse(-40);

      for (Position position : availablePositions) {
         ImageView available = new ImageView(new Image(getClass().getResourceAsStream("/images/cards/available.png")));

         available.setOnDragOver(event -> {
            if (event.getDragboard().hasImage()) {
               event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
         });

         available.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasImage() && db.hasString()) {
               String id = db.getString();
               boolean side = true;
               int cardId=-1;
               if(id.contains("Back")){
                  side = false;
                  cardId = Integer.parseInt(id.substring(4));
               } else {
                  cardId= Integer.parseInt(id.substring(5));
               }
               gui.chooseCardToPlay(cardId, side, position);
               success = true;
            }
            event.setDropCompleted(success);
            event.consume();
         });


         available.setFitWidth(cardWidth);
         available.setFitHeight(cardHeight);
         GridPane.setMargin(available, new Insets(-verticalGap+10, -horizontalGap+10, -verticalGap+10, -horizontalGap+10));
         GridPane.setHalignment(available, HPos.CENTER);
         GridPane.setValignment(available, VPos.CENTER);
         grid.add(available, position.getX()-minX, maxY-position.getY());
      }

      for (Integer card : cards) {
         String imagePath;
         if(side.get(card)){
            imagePath = "/images/cards/Front" + card + ".png";
         }else {
            imagePath = "/images/cards/Back" + card + ".png";
         }
         ImageView cardPlace = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
         cardPlace.setId(String.valueOf(card));
         cardPlace.setFitWidth(cardWidth);
         cardPlace.setFitHeight(cardHeight);
         GridPane.setMargin(cardPlace, new Insets(-verticalGap, -horizontalGap, -verticalGap, -horizontalGap));
         GridPane.setHalignment(cardPlace, HPos.CENTER);
         GridPane.setValignment(cardPlace, VPos.CENTER);
         grid.add(cardPlace, field.get(card).getX()-minX, maxY-field.get(card).getY());
      }

      return grid;
   }
}
