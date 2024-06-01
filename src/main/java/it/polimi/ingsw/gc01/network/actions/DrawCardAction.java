package it.polimi.ingsw.gc01.network.actions;

import it.polimi.ingsw.gc01.controller.RoomController;
import it.polimi.ingsw.gc01.model.room.TablePosition;

public class DrawCardAction extends RoomAction {
    private final TablePosition position;

    public DrawCardAction(String playerName, RoomController room, int choice) {
        super(playerName, room);
        switch (choice){
            case 1 -> this.position = TablePosition.RESOURCEDECK;
            case 2 -> this.position = TablePosition.RESOURCELEFT;
            case 3 -> this.position = TablePosition.RESOURCERIGHT;
            case 4 -> this.position = TablePosition.GOLDENDECK;
            case 5 -> this.position = TablePosition.GOLDENLEFT;
            case 6 -> this.position = TablePosition.GOLDENRIGHT;
            default -> this.position = null;
        }
    }

    @Override
    public void execute() {
        RoomController controller = getRoomController();
        controller.drawCard(getPlayerName(), position);
    }
}