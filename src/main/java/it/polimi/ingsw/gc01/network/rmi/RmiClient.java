package it.polimi.ingsw.gc01.network.rmi;

import it.polimi.ingsw.gc01.model.DefaultValue;
import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.model.room.TablePosition;
import it.polimi.ingsw.gc01.network.VirtualView;
import it.polimi.ingsw.gc01.network.message.Message;
import it.polimi.ingsw.gc01.network.message.ShowAvailableColorMessage;
import it.polimi.ingsw.gc01.network.message.UpdateRoomIdMessage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class RmiClient {

    private VirtualServer server;
    private final String roomId;
    final String playerName;


    public RmiClient(String playerName, String roomId) {
        this.playerName = playerName;
        this.roomId = roomId;
    }

    /**
     * the method connects the client to the server creating his own stub
     *
     */
    public void connect(){
        boolean retry = false;
        int attempt = 1;
        int i;
        do {
            try{

                Registry registry = LocateRegistry.getRegistry(DefaultValue.serverIp, DefaultValue.Default_port_RMI);
                this.server = (VirtualServer) registry.lookup(DefaultValue.Default_servername_RMI);
                System.out.println("Client RMI ready");
                retry = false;

            } catch (Exception e) {
                if (!retry){
                    System.out.println("[ERROR] CONNECTING TO RMI SERVER: \n\tClient RMI exception: " + e + "\n");
                }
                System.out.println("[#" + attempt + "]Waiting to reconnect to RMI Server on port: '" + DefaultValue.Default_port_RMI + "' with name: '" + DefaultValue.Default_servername_RMI + "'");

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

    /**
     * asks stub to create game
     * @param client
     */
    public void createGame(VirtualView client){
        server.createGame(this.playerName, client);

}

    /**
     * asks stub to join game
     * @param client
     */
    public void joinGame(VirtualView client){
        server.joinGame(this.playerName, client, this.roomId);
    }

    /**
     * asks stub to join the first available game
     * @param client
     */
    public void joinFirstGame( VirtualView client){
        server.joinFirstGame(this.playerName, client);
    }

    /**
     * asks stub to choose the color of the player
     * @param color
     */
    public void chooseColor(PlayerColor color){
        server.chooseColor(this.playerName, this.roomId, color);
    }

    /**
     * asks stub to set the player ready
     */
    public void changeReady(){
        server.changeReady(this.playerName,this.roomId);
    }

    /**
     * asks stub to choose the secret objective
     * @param cardId
     */
    public void chooseSecretObjective(int cardId){
        server.chooseSecretObjective(this.playerName, this.roomId, cardId);
    }

    /**
     * asks stub to flip the selected card
     * @param cardId
     */
    public void flipCard(int cardId){
        server.flipCard(this.playerName, this.roomId, cardId);
    }

    /**
     * asks stub to play the card in the position with coordinates x ,y
     * @param cardId
     * @param x
     * @param y
     */
    public void playCard(int cardId, int x, int y){
        server.playCard(this.playerName, this.roomId, cardId, x, y);
    }

    /**
     * asks stub to draw card from a certain Table position
     * @param card
     */
    public void drawCard (TablePosition card){
        server.drawCard(this.playerName, this.roomId, card);
    }

    /**
     * asks stub to make the player leafve the game
     */
    public void leave (){
        server.leave(this.playerName, this.roomId);
        }


    public void updateRoomId(){}

    public void showAvailableColor( ){}

    public void updateReady(){}

    public void showCommonObjective(){}

    public void showTable(){}

    public void showHand(){}

    public void showField(){}

    public void showSecretObjectives(){}

    public void showError(){}

    public void serviceMessage(){}



    }








