package it.polimi.ingsw.gc01.network.actions;

import it.polimi.ingsw.gc01.controller.RoomController;
import it.polimi.ingsw.gc01.model.player.Player;
import it.polimi.ingsw.gc01.model.room.TablePosition;

public class DrawCardAction extends RoomAction{
    private TablePosition position;

    public DrawCardAction(String playerName, RoomController room, TablePosition position){
        super(playerName, room);
        this.position = position;
    }

    @Override
    public void execute(){
        RoomController controller = getRoomController();
        Player playerDrawing = controller.getRoom().getPlayerByName(getPlayerName());

        controller.drawCard(playerDrawing, position);

    }
}
