package it.polimi.ingsw.gc01.network.rmi.actions;

import it.polimi.ingsw.gc01.controller.RoomController;
import it.polimi.ingsw.gc01.model.room.TablePosition;

/**
 * Action to draw a card
 */
public class DrawCardAction extends RoomAction {
    /**
     * Position where to draw the card
     */
    private final TablePosition position;

    /**
     * Create a new DrawCardAction
     * @param playerName of the player who is creating the action
     * @param room where the action will be executed
     * @param choice position chosen by the player
     */
    public DrawCardAction(String playerName, RoomController room, int choice) {
        super(playerName, room);
        switch (choice) {
            case 1 -> this.position = TablePosition.RESOURCEDECK;
            case 2 -> this.position = TablePosition.RESOURCELEFT;
            case 3 -> this.position = TablePosition.RESOURCERIGHT;
            case 4 -> this.position = TablePosition.GOLDENDECK;
            case 5 -> this.position = TablePosition.GOLDENLEFT;
            case 6 -> this.position = TablePosition.GOLDENRIGHT;
            default -> this.position = null;
        }
    }

    /**
     * Execute the action
     */
    @Override
    public void execute() {
        RoomController controller = getRoomController();
        controller.drawCard(getPlayerName(), position);
    }
}