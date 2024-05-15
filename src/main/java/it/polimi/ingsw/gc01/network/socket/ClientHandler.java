package it.polimi.ingsw.gc01.network.socket;

import it.polimi.ingsw.gc01.controller.MainController;
import it.polimi.ingsw.gc01.network.VirtualView;
import it.polimi.ingsw.gc01.network.message.Message;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ClientHandler implements VirtualView {
    //TODO
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private MainController mainController;

    public ClientHandler(){
        super(); //Da rimuovere
    }

    public void executeMessage(){

    }

    @Override
    public void updateRoomId(Message message){

    }

    @Override
    public void showAvailableColors(Message message){

    }

    @Override
    public void updateReady(Message message) {

    }

    @Override
    public void showCommonObjective(Message message) {

    }

    @Override
    public void showTable(Message message) {

    }

    @Override
    public void showHand(Message message) {

    }

    @Override
    public void showField(Message message) {

    }

    @Override
    public void showSecretObjectives(Message message) {

    }

    @Override
    public void showError(Message message) {

    }

    @Override
    public void serviceMessage(Message message) {

    }

}
