package it.polimi.ingsw.gc01.view.gui;

import it.polimi.ingsw.gc01.model.player.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientFieldGUI {

   private final List<Integer> cards;
   private final Map<Integer, Boolean> side;
   private final Map<Integer, Position> field;
   private List<Position> availablePositions;

   public ClientFieldGUI(){
       this.cards= new ArrayList<>();
       this.side = new HashMap<>();
       this.field = new HashMap<>();
       this.availablePositions= new ArrayList<>();

   }

   public void playCard( int id, boolean front, Position position){
      cards.add(id);
      side.put(id, front);
      field.put(id, position);
   }

   public void setAvailablePositions(List<Position> availablePositions){
      this.availablePositions=availablePositions;
   }

   public List<Integer> getCards(){
      return this.cards;
   }

   public Map<Integer, Boolean> getSide(){
      return this.side;
   }

   public Map<Integer, Position> getField(){
      return this.field;
   }


}
