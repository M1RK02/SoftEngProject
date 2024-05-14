package it.polimi.ingsw.gc01.network.actions;

import it.polimi.ingsw.gc01.controller.RoomController;
import it.polimi.ingsw.gc01.controller.exceptions.CardNotValidException;
import it.polimi.ingsw.gc01.controller.exceptions.NameNotValidException;
import it.polimi.ingsw.gc01.model.cards.ObjectiveCard;
import it.polimi.ingsw.gc01.model.player.Player;

public class ChooseSecretObjectiveAction extends RoomAction{
    private final int cardId;

    public ChooseSecretObjectiveAction(String playerName, RoomController room, int cardId){
        super(playerName, room);
        this.cardId = cardId;
    }

    @Override
    public void execute() throws NameNotValidException, CardNotValidException{
        RoomController controller = getRoomController();
        Player playerChoosing = controller.getRoom().getPlayerByName(getPlayerName());
        boolean choose = false;

        if (playerChoosing == null){
            throw new NameNotValidException();
        }

        for (ObjectiveCard obj : playerChoosing.getPossibleObjective()){
            if (obj.getId() == cardId){
                controller.chooseSecretObjective(playerChoosing, obj);
                choose = true;
            }
        }

        if (!choose){
            throw new CardNotValidException();
        }
    }
}
