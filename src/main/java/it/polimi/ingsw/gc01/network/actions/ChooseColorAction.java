package it.polimi.ingsw.gc01.network.actions;

import it.polimi.ingsw.gc01.controller.RoomController;
import it.polimi.ingsw.gc01.model.player.Player;
import it.polimi.ingsw.gc01.model.player.PlayerColor;

public class ChooseColorAction extends RoomAction{
    private PlayerColor color;

    public ChooseColorAction(String playerName, RoomController room, PlayerColor color){
        super(playerName, room);
        this.color = color;
    }

    @Override
    public void execute(){
        RoomController controller = getRoomController();
        Player playerToSetColor = controller.getRoom().getPlayerByName(getPlayerName());
        controller.chooseColor(playerToSetColor, color);
    }
}
