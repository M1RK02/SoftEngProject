package it.polimi.ingsw.gc01.network.actions;

import it.polimi.ingsw.gc01.controller.RoomController;
import it.polimi.ingsw.gc01.model.player.PlayerColor;

/**
 * Action to choose the color
 */
public class ChooseColorAction extends RoomAction {
    /**
     * Color chosen by the player
     */
    private final PlayerColor color;

    /**
     * Create a new ChooseColorAction
     *
     * @param playerName of the player who is creating the action
     * @param room       where the action will be executed
     * @param color      chosen by the player
     */
    public ChooseColorAction(String playerName, RoomController room, PlayerColor color) {
        super(playerName, room);
        this.color = color;
    }

    /**
     * Execute the action
     */
    @Override
    public void execute() {
        RoomController controller = getRoomController();
        controller.chooseColor(getPlayerName(), color);
    }
}