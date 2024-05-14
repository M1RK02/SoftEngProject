package it.polimi.ingsw.gc01.network.actions;

import it.polimi.ingsw.gc01.controller.MainController;
import it.polimi.ingsw.gc01.network.VirtualView;

public class JoinFirstGameAction extends MainAction{
    public JoinFirstGameAction(String playerName, MainController mainController, VirtualView client){
        super(playerName, mainController, client);
    }

    @Override
    public void execute(){
        MainController controller = getMainController();

        try {
            controller.joinFirstGame(getPlayerName());
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
