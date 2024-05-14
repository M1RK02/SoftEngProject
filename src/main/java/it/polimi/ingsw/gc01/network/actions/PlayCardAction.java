package it.polimi.ingsw.gc01.network.actions;

import it.polimi.ingsw.gc01.controller.RoomController;
import it.polimi.ingsw.gc01.model.player.Position;

public class PlayCardAction extends RoomAction{
    private int cardId;
    private Position position;
    public PlayCardAction(String playerName, RoomController room, int cardId, Position position){
        super(playerName, room);
        this.cardId = cardId;
        this.position = position;
    }


    @Override
    public void execute(){

    }


}
