package it.polimi.ingsw.gc01.network.rmi;


import it.polimi.ingsw.gc01.controller.MainController;
import it.polimi.ingsw.gc01.model.DefaultValue;
import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.model.player.Position;
import it.polimi.ingsw.gc01.model.room.TablePosition;
import it.polimi.ingsw.gc01.network.VirtualView;
import it.polimi.ingsw.gc01.network.actions.*;

import java.rmi.*;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class RmiServer implements VirtualServer {

    MainController mainController;
    BlockingQueue<Action> actions;

    public  RmiServer () throws RemoteException {
        this.mainController = MainController.getInstance();
        actions = new ArrayBlockingQueue<Action>(100);
        bind();
    }

    /**
     * exports the remote object as a stub to let the clients connect to the RMI server
     * @throws RemoteException
     * @throws AlreadyBoundException
     */
    private void bind() {
        try {
            //esporto l'oggetto remoto cosìpuò ricevere chiamate dai client
            VirtualServer stub = (VirtualServer) UnicastRemoteObject.exportObject(this, DefaultValue.Default_port_RMI);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.createRegistry(1234);
            registry.bind(DefaultValue.Default_servername_RMI, stub);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * a Thread is created to take actions from the Queue and call their execute method that
     * is going to modify the model of the game
     */
    private void executeActions(){
        Thread thread = new Thread ( () -> {
            try{
                while (true) {
                    //Spila le action e le esegue
                    Action action = actions.take();
                    action.execute();
                }
            }catch (InterruptedException e){
                System.err.println("Il thread che esegue le Action è stato interrotto");
                throw new RuntimeException(e);
            }
        });

        thread.start();
    }


    /**
     * Creates a CreateGameAction and put it in the Queue
     * @param playerName The name of the player who is creating a room
     * @param client The client  of the player who is creating the room
     */
    public void createGame (String playerName, VirtualView client) throws RemoteException {
        CreateGameAction createGame = new CreateGameAction(playerName, this.mainController, client);
        try{
            actions.put(createGame);
        } catch (InterruptedException e){
            System.err.println("L'inserimento dell'azione createGame nella coda è stato interrotto.");
            throw new RuntimeException(e);
        }
    }


    /**
     * Creates a joinGameAction and put it in the Queue
     * @param playerName The name of the player who is trying to join a game
     * @param client The client of the player who is trying to join a game
     * @param roomId The id of the game to join
     */
    public void joinGame(String playerName, VirtualView client, String roomId) throws RemoteException {
        JoinGameAction joinGame = new JoinGameAction(playerName, this.mainController, client, roomId);
        try{
            actions.put(joinGame);
        } catch (InterruptedException e){
            System.err.println("L'inserimento dell'azione joinGame nella coda è stato interrotto.");
            throw new RuntimeException(e);
        }
    }





    /**
     * Creates a joinFirstGameAction and put it in the Queue
     * @param playerName The name of the player who is trying to join a game
     * @param client The client of the player who is trying to join a game
     */
    public void joinFirstGame(String playerName, VirtualView client) throws RemoteException {
        JoinFirstGameAction joinFirstGame = new JoinFirstGameAction(playerName, this.mainController, client);
        try{
            actions.put(joinFirstGame);
        } catch (InterruptedException e){
            System.err.println("L'inserimento dell'azione joinFirstGame nella coda è stato interrotto.");
            throw new RuntimeException(e);
        }
    }


    /**
     * Creates a chooseColorAction and put it in the Queue
     * @param playerName The name of the player who is choosing the color
     * @param roomId The id of the room in which is the player who is making the action
     * @param color the color chosen by the player
     */
    public void chooseColor(String playerName, String roomId, PlayerColor color) throws RemoteException {

        ChooseColorAction chooseColor = new ChooseColorAction(playerName, mainController.getRooms().get(roomId), color);
        try{
            actions.put(chooseColor);
        } catch (InterruptedException e){
            System.err.println("L'inserimento dell'azione chooseColor nella coda è stato interrotto.");
            throw new RuntimeException(e);
        }

    }


    /**
     * Creates a SetReadyAction and put it in the Queue
     * @param playerName The name of the player who is switching his readiness state
     * @param roomId The id of the room in which is the player who is making the action
     */
    public void switchReady (String playerName, String roomId) throws RemoteException {
    SwitchReadyAction switchReady = new SwitchReadyAction(playerName, mainController.getRooms().get(roomId));
        try{
            actions.put(switchReady);
        } catch (InterruptedException e){
            System.err.println("L'inserimento dell'azione changeReady nella coda è stato interrotto.");
            throw new RuntimeException(e);
        }
    }


    /**
     * Creates a ChooseSecretObjectiveAction and put it in the Queue
     * @param playerName The name of the player who is choosing the secret objective
     * @param roomId The id of the room in which is the player who is making the action
     * @param cardId The id of the objective card chosen
     */
    public void chooseSecretObjective(String playerName, String roomId, int cardId) throws RemoteException {
    ChooseSecretObjectiveAction chooseSecretObjective = new ChooseSecretObjectiveAction(playerName, mainController.getRooms().get(roomId), cardId);
        try{
            actions.put(chooseSecretObjective);
        } catch (InterruptedException e){
            System.err.println("L'inserimento dell'azione chooseSecretObjective nella coda è stato interrotto.");
            throw new RuntimeException(e);
        }
    }


    /**
     * Creates a FlipCardAction and put it in the Queue
     * @param playerName The name of the player
     * @param roomId The id of the room in which is the player who is making the action
     * @param cardId The id of the card to flip
     */
    public void flipCard(String playerName, String roomId, int cardId) throws RemoteException {
    FlipCardAction flipCard = new FlipCardAction(playerName, mainController.getRooms().get(roomId), cardId);
        try{
            actions.put(flipCard);
        } catch (InterruptedException e){
            System.err.println("L'inserimento dell'azione fliCard nella coda è stato interrotto.");
            throw new RuntimeException(e);
        }
    }


    /**
     * Creates a PlayCardAction and put it in the Queue
     * @param playerName The name of the player
     * @param roomId The id of the room in which is the player who is making the action
     * @param cardId The id of the card to play
     * @param x The x coordinate in the matrix of the player field
     * @param y The y coordinate in the matrix of the player field
     */
    public void playCard(String playerName, String roomId, int cardId, int x, int y) throws RemoteException {
    PlayCardAction playCard = new PlayCardAction(playerName, mainController.getRooms().get(roomId), cardId, new Position(x,y));
        try{
            actions.put(playCard);
        } catch (InterruptedException e){
            System.err.println("L'inserimento dell'azione playCard nella coda è stato interrotto.");
            throw new RuntimeException(e);
        }
    }


    /**
     * Creates a DrawCardAction and put it in the Queue
     * @param playerName The name of the player
     * @param roomId The id of the room in which is the player who is making the action
     * @param card The position of the card to draw in the Drawable cards
     */
    public void drawCard(String playerName, String roomId, TablePosition card) throws RemoteException {
    DrawCardAction drawCard = new DrawCardAction(playerName, mainController.getRooms().get(roomId), card);
        try{
            actions.put(drawCard);
        } catch (InterruptedException e){
            System.err.println("L'inserimento dell'azione drawCard nella coda è stato interrotto.");
            throw new RuntimeException(e);
        }
    }


    /**
     * Creates a LeaveAction and put it in the Queue
     * @param playerName The name of the player
     * @param roomId The id of the room in which is the player who is making the action
     */
    public void leave(String playerName, String roomId) throws RemoteException {
    LeaveAction leave = new LeaveAction(playerName, mainController.getRooms().get(roomId));
        try{
            actions.put(leave);
        } catch (InterruptedException e){
            System.err.println("L'inserimento dell'azione leave nella coda è stato interrotto.");
            throw new RuntimeException(e);
        }
    }
}