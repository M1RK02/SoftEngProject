package it.polimi.ingsw.gc01.network.rmi;

import it.polimi.ingsw.gc01.model.DefaultValue;
import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.model.room.TablePosition;
import it.polimi.ingsw.gc01.network.Client;
import it.polimi.ingsw.gc01.network.VirtualView;

import java.io.IOException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;


public class RmiClient extends UnicastRemoteObject implements VirtualView {

    private VirtualServer server;
    private String roomId;
    final String playerName;


    public RmiClient(String playerName) throws RemoteException {
        this.playerName = playerName;
    }

    /**
     * the method connects the client to the server creating his own stub
     *
     */
    public void connect() {
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
     */
    public void createGame() throws RemoteException {
        try {
            server.createGame(this.playerName, this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * asks stub to join game
     */
    public void joinGame() throws RemoteException {
        try {
            server.joinGame(this.playerName, (VirtualView) this, this.roomId);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * asks stub to join the first available game
     */
    public void joinFirstGame() throws RemoteException {
        try {
            server.joinFirstGame(this.playerName, (VirtualView) this);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * asks stub to choose the color of the player
     * @param color the color wich will be given to the player
     */
    public void chooseColor(PlayerColor color) throws RemoteException {
        try {
            server.chooseColor(this.playerName, this.roomId, color);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * asks stub to set the player ready
     */
    public void switchReady() throws RemoteException {
        try {
            server.switchReady(this.playerName,this.roomId);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * asks stub to choose the secret objective
     * @param cardId the id of the chosen objective card
     */
    public void chooseSecretObjective(int cardId) throws RemoteException {
        try {
            server.chooseSecretObjective(this.playerName, this.roomId, cardId);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * asks stub to flip the selected card
     * @param cardId the id of the card to flip
     */
    public void flipCard(int cardId) throws RemoteException {
        try {
            server.flipCard(this.playerName, this.roomId, cardId);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * asks stub to play the card in the position with coordinates x ,y
     * @param cardId the id of the card to play
     * @param x the x coordinate in the matrix of the player field
     * @param y the y coordinate in the matrix of the player field
     */
    public void playCard(int cardId, int x, int y) throws RemoteException {
        try {
            server.playCard(this.playerName, this.roomId, cardId, x, y);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * asks stub to draw card from a certain Table position
     * @param card the position of the card to draw in the drawableCard positions
     */
    public void drawCard (TablePosition card)throws RemoteException {
        try {
            server.drawCard(this.playerName, this.roomId, card);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * asks stub to make the player leave the game
     */
    public void leave()throws RemoteException {
        try {
            server.leave(this.playerName, this.roomId);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    // TODO scrivere tutti i metodi per aggiornare la view

    /**
     * @param roomId
     */
    @Override
    public void updateRoomId(String roomId) throws RemoteException{
        System.out.println("[#" + this.roomId + "] " + roomId);
    }

    /**
     * @param availableColors
     */
    @Override
    public void showAvailableColors(List<PlayerColor> availableColors) throws RemoteException {

    }

    /**
     * @param ready
     */
    @Override
    public void updateReady(boolean ready) throws RemoteException {

    }

    /**
     * @param objectivesIds
     */
    @Override
    public void showCommonObjectives(List<Integer> objectivesIds) throws RemoteException {

    }

    /**
     * @param drawableCardsIds
     */
    @Override
    public void showTable(Map<TablePosition, Integer> drawableCardsIds) throws RemoteException {

    }

    /**
     * @param cardIds
     */
    @Override
    public void showHand(List<Integer> cardIds) throws RemoteException {

    }

    /**
     * @param playerName
     * @param cardId
     * @param x
     * @param y
     */
    @Override
    public void showField(String playerName, int cardId, int x, int y) throws RemoteException {

    }

    /**
     * @param possibleObjectivesIds
     */
    @Override
    public void showSecretObjectives(List<Integer> possibleObjectivesIds) throws RemoteException {

    }

    /**
     * @param error
     */
    @Override
    public void showError(String error) throws RemoteException {

    }

    /**
     * @param message
     */
    @Override
    public void serviceMessage(String message) throws RemoteException {

    }
}