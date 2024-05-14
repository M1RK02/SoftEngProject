package it.polimi.ingsw.gc01.network.socket;

import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.model.room.TablePosition;
import it.polimi.ingsw.gc01.network.Client;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;



public class SocketClient implements Client {

    private ObjectInputStream input;

    private ObjectOutputStream output;

    private String roomId;

    private String playerName;



    public void connect() {

    }

    public void createGame(){

    }

    public void joinGame(){

    }

    public void joinFirstGame(){

    }

    public void chooseColor(PlayerColor color){

    }

    public void switchReady(){

    }

    public void chooseSecretObjective(int cardId){

    }

    public void flipCard(int card){

    }

    public void playCard (int cardId, int x, int y){

    }

    public void drawCard (TablePosition card){

    }

    public void leave(){

    }

    public void update(){
        //TODO
    }
}
