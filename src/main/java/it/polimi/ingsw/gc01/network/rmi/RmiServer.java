package it.polimi.ingsw.gc01.network.rmi;


import it.polimi.ingsw.gc01.controller.MainController;
import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.model.player.Position;
import it.polimi.ingsw.gc01.model.room.TablePosition;
import it.polimi.ingsw.gc01.network.VirtualView;
import it.polimi.ingsw.gc01.network.actions.*;

import java.rmi.AlreadyBoundException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class RmiServer implements VirtualServer {

    MainController mainController;
    BlockingQueue<Action> actions;

    public  RmiServer (MainController mainController) {
        this.mainController = mainController;
        actions = new ArrayBlockingQueue<Action>(100);
    }

    /**
     * exports the remote object as a stub to let the clients connect to the RMI server
     * @throws RemoteException
     * @throws AlreadyBoundException
     */
    public void bind() throws RemoteException, AlreadyBoundException {
        try {
            //esporto l'oggetto remoto cosìpuò ricevere chiamate dai client
            VirtualServer stub = (VirtualServer) UnicastRemoteObject.exportObject(this, 1234);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("ServerLazzaro", stub);

        }catch (Exception e){
            System.err.println("Server exception");
            e.printStackTrace();

        }
    }


    /**
     * a Thread is created to take actions from the Queue and call their execute method that
     * is going to modify the model of the game
     */
    public void executeActions(){
        Thread thread = new Thread ( () -> {
            try{
                while (true) {
                    //spila le action e le segue
                    Action action = actions.take();
                    action.execute();
                }
            }catch (InterruptedException e){
                System.err.println("Il thread che esegue le Action è stato interrotto");
                e.printStackTrace();
            }
        });

        thread.start();
    }


    /**
     * Creates a CreateGameAction and put it in the Queue
     * @param playerName
     * @param client
     */
    public void createGame (String playerName, VirtualView client){
        CreateGameAction createGame = new CreateGameAction(playerName, this.mainController, client);
        try{
            actions.put(createGame);
        } catch (InterruptedException e){
            System.err.println("L'inserimento dell'azione createGame nella coda è stato interrotto.");
            e.printStackTrace();
        }
    }


    /**
     * Creates a joinGameAction and put it in the Queue
     * @param playerName
     * @param client
     * @param roomId
     */
    public void joinGame(String playerName, VirtualView client, String roomId){
        JoinGameAction joinGame = new JoinGameAction(playerName, this.mainController, client, roomId);
        try{
            actions.put(joinGame);
        } catch (InterruptedException e){
            System.err.println("L'inserimento dell'azione joinGame nella coda è stato interrotto.");
            e.printStackTrace();
        }
    }





    /**
     * Creates a joinFirstGameAction and put it in the Queue
     * @param playerName
     * @param client
     */
    public void joinFirstGame(String playerName, VirtualView client){
        JoinFirstGameAction joinFirstGame = new JoinFirstGameAction(playerName, this.mainController, client);
        try{
            actions.put(joinFirstGame);
        } catch (InterruptedException e){
            System.err.println("L'inserimento dell'azione joinFirstGame nella coda è stato interrotto.");
            e.printStackTrace();
        }
    }


    /**
     * Creates a chooseColorAction and put it in the Queue
     * @param playerName
     * @param roomId
     * @param color
     */
    public void chooseColor(String playerName, String roomId, PlayerColor color){

        ChooseColorAction chooseColor = new ChooseColorAction(playerName, mainController.getRooms().get(roomId), color);
        try{
            actions.put(chooseColor);
        } catch (InterruptedException e){
            System.err.println("L'inserimento dell'azione chooseColor nella coda è stato interrotto.");
            e.printStackTrace();
        }

    }


    /**
     * Creates a SetReadyAction and put it in the Queue
     * @param playerName
     * @param roomId
     */
    public void switchReady (String playerName, String roomId){
    SwitchReadyAction switchReady = new SwitchReadyAction(playerName, mainController.getRooms().get(roomId));
        try{
            actions.put(switchReady);
        } catch (InterruptedException e){
            System.err.println("L'inserimento dell'azione changeReady nella coda è stato interrotto.");
            e.printStackTrace();
        }
    }


    /**
     * Creates a ChooseSecretObjectiveAction and put it in the Queue
     * @param playerName
     * @param roomId
     * @param cardId
     */
    public void chooseSecretObjective(String playerName, String roomId, int cardId){
    ChooseSecretObjectiveAction chooseSecretObjective = new ChooseSecretObjectiveAction(playerName, mainController.getRooms().get(roomId), cardId);
        try{
            actions.put(chooseSecretObjective);
        } catch (InterruptedException e){
            System.err.println("L'inserimento dell'azione chooseSecretObjective nella coda è stato interrotto.");
            e.printStackTrace();
        }
    }


    /**
     * Creates a FlipCardAction and put it in the Queue
     * @param playerName
     * @param roomId
     * @param cardId
     */
    public void flipCard(String playerName, String roomId, int cardId){
    FlipCardAction flipCard = new FlipCardAction(playerName, mainController.getRooms().get(roomId), cardId);
        try{
            actions.put(flipCard);
        } catch (InterruptedException e){
            System.err.println("L'inserimento dell'azione fliCard nella coda è stato interrotto.");
            e.printStackTrace();
        }
    }


    /**
     * Creates a PlayCardAction and put it in the Queue
     * @param playerName
     * @param roomId
     * @param cardId
     * @param x
     * @param y
     */
    public void playCard(String playerName, String roomId, int cardId, int x, int y){
    PlayCardAction playCard = new PlayCardAction(playerName, mainController.getRooms().get(roomId), cardId, new Position(x,y));
        try{
            actions.put(playCard);
        } catch (InterruptedException e){
            System.err.println("L'inserimento dell'azione playCard nella coda è stato interrotto.");
            e.printStackTrace();
        }
    }


    /**
     * Creates a DrawCardAction and put it in the Queue
     * @param playerName
     * @param roomId
     * @param card
     */
    public void drawCard(String playerName, String roomId, TablePosition card){
    DrawCardAction drawCard = new DrawCardAction(playerName, mainController.getRooms().get(roomId), card);
        try{
            actions.put(drawCard);
        } catch (InterruptedException e){
            System.err.println("L'inserimento dell'azione drawCard nella coda è stato interrotto.");
            e.printStackTrace();
        }
    }


    /**
     * Creates a LeaveAction and put it in the Queue
     * @param playerName
     * @param roomId
     */
    public void leave(String playerName, String roomId){
    LeaveAction leave = new LeaveAction(playerName, mainController.getRooms().get(roomId));
        try{
            actions.put(leave);
        } catch (InterruptedException e){
            System.err.println("L'inserimento dell'azione leave nella coda è stato interrotto.");
            e.printStackTrace();
        }
    }
}