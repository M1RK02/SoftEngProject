package it.polimi.ingsw.gc01.network.socket;

import it.polimi.ingsw.gc01.model.DefaultValue;
import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.model.room.TablePosition;
import it.polimi.ingsw.gc01.network.Client;
import it.polimi.ingsw.gc01.network.message.CreateGameMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class SocketClient implements Client {
    //TODO
    private ObjectInputStream input;

    private ObjectOutputStream output;

    private String roomId;

    private String playerName;



    public void connect() {
        boolean retry = false;
        int attempt = 1;
        int i;
        Socket clientSoc;

        do {
            try {
                clientSoc = new Socket(DefaultValue.serverIp, DefaultValue.Default_port_Socket);
                output = new ObjectOutputStream(clientSoc.getOutputStream());
                input = new ObjectInputStream(clientSoc.getInputStream());
                retry = false;
            } catch (IOException e) {
                if (!retry) {
                    System.out.println("[ERROR] CONNECTING TO SOCKET SERVER: \n\tClient RMI exception: " + e + "\n");
                }
                System.out.println("[#" + attempt + "]Waiting to reconnect to Socket Server on port: '" + DefaultValue.Default_port_Socket + "' with ip: '" + DefaultValue.serverIp + "'");

                i = 0;
                while (i < DefaultValue.seconds_between_reconnection) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.out.println(".");
                    i++;
                }
                System.out.println("\n");

                if (attempt >= DefaultValue.num_of_attempt_to_connect_toServer_before_giveup) {
                    System.out.println("Give up!");
                    try {
                        System.in.read();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.exit(-1);
                }
                retry = true;
                attempt++;
            }
        } while (retry);
    }

    public void createGame(){
        //TODO
        try {
            output.writeObject(new CreateGameMessage(this.playerName));
            output.flush();
            output.reset();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
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
