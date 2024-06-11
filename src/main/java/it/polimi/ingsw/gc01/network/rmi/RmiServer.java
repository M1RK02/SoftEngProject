package it.polimi.ingsw.gc01.network.rmi;

import it.polimi.ingsw.gc01.controller.MainController;
import it.polimi.ingsw.gc01.model.player.*;
import it.polimi.ingsw.gc01.network.VirtualView;
import it.polimi.ingsw.gc01.network.rmi.actions.*;
import it.polimi.ingsw.gc01.utils.DefaultValue;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.*;

public class RmiServer implements VirtualServer {
    private final MainController mainController;
    private final BlockingQueue<Action> actions;

    public RmiServer() {
        this.mainController = MainController.getInstance();
        actions = new ArrayBlockingQueue<Action>(100);
        bind();
        executeActions();
    }

    /**
     * Exports the remote object as a stub to let the clients connect to the RMI server
     */
    private void bind() {
        try {
            VirtualServer stub = (VirtualServer) UnicastRemoteObject.exportObject(this, DefaultValue.RMIPort);
            Registry registry = LocateRegistry.createRegistry(DefaultValue.RMIPort);
            registry.bind(DefaultValue.RMIServerName, stub);
            System.out.println("Server RMI ready");
        } catch (RemoteException | AlreadyBoundException e) {
            System.out.println("Server RMI not working!");
        }
    }

    /**
     * A thread is created to take actions from the Queue and call their execute method that
     * is going to modify the model of the game
     */
    private void executeActions() {
        new Thread(() -> {
            try {
                while (true) {
                    //Spila le action e le esegue
                    Action action = actions.take();
                    action.execute();
                }
            } catch (InterruptedException e) {
                System.err.println("Il thread che esegue le Action è stato interrotto");
            }
        }).start();
    }

    /**
     * Creates a CreateGameAction and put it in the Queue
     *
     * @param playerName The name of the player who is creating a room
     * @param client     The client  of the player who is creating the room
     */
    @Override
    public void createGame(String playerName, VirtualView client) {
        CreateGameAction createGame = new CreateGameAction(playerName, client);
        try {
            actions.put(createGame);
        } catch (InterruptedException e) {
            System.err.println("L'inserimento dell'azione createGame nella coda è stato interrotto.");
        }
    }

    /**
     * Creates a joinGameAction and put it in the Queue
     *
     * @param playerName The name of the player who is trying to join a game
     * @param client     The client of the player who is trying to join a game
     * @param roomId     The id of the game to join
     */
    @Override
    public void joinGame(String playerName, VirtualView client, String roomId) {
        JoinGameAction joinGame = new JoinGameAction(playerName, client, roomId);
        try {
            actions.put(joinGame);
        } catch (InterruptedException e) {
            System.err.println("L'inserimento dell'azione joinGame nella coda è stato interrotto.");
        }
    }

    /**
     * Creates a joinFirstGameAction and put it in the Queue
     *
     * @param playerName The name of the player who is trying to join a game
     * @param client     The client of the player who is trying to join a game
     */
    @Override
    public void joinFirstGame(String playerName, VirtualView client) {
        JoinFirstGameAction joinFirstGame = new JoinFirstGameAction(playerName, client);
        try {
            actions.put(joinFirstGame);
        } catch (InterruptedException e) {
            System.err.println("L'inserimento dell'azione joinFirstGame nella coda è stato interrotto.");
        }
    }

    /**
     * Creates a chooseColorAction and put it in the Queue
     *
     * @param playerName The name of the player who is choosing the color
     * @param roomId     The id of the room in which is the player who is making the action
     * @param color      the color chosen by the player
     */
    @Override
    public void chooseColor(String playerName, String roomId, PlayerColor color) {

        ChooseColorAction chooseColor = new ChooseColorAction(playerName, mainController.getRooms().get(roomId), color);
        try {
            actions.put(chooseColor);
        } catch (InterruptedException e) {
            System.err.println("L'inserimento dell'azione chooseColor nella coda è stato interrotto.");
        }

    }

    /**
     * Creates a SetReadyAction and put it in the Queue
     *
     * @param playerName The name of the player who is switching his readiness state
     * @param roomId     The id of the room in which is the player who is making the action
     */
    @Override
    public void switchReady(String playerName, String roomId) {
        SwitchReadyAction switchReady = new SwitchReadyAction(playerName, mainController.getRooms().get(roomId));
        try {
            actions.put(switchReady);
        } catch (InterruptedException e) {
            System.err.println("L'inserimento dell'azione changeReady nella coda è stato interrotto.");
        }
    }

    /**
     * Creates a ChooseSecretObjectiveAction and put it in the Queue
     *
     * @param playerName The name of the player who is choosing the secret objective
     * @param roomId     The id of the room in which is the player who is making the action
     * @param cardId     The id of the objective card chosen
     */
    @Override
    public void chooseSecretObjective(String playerName, String roomId, int cardId) {
        ChooseSecretObjectiveAction chooseSecretObjective = new ChooseSecretObjectiveAction(playerName, mainController.getRooms().get(roomId), cardId);
        try {
            actions.put(chooseSecretObjective);
        } catch (InterruptedException e) {
            System.err.println("L'inserimento dell'azione chooseSecretObjective nella coda è stato interrotto.");
        }
    }

    /**
     * Creates a FlipCardAction and put it in the Queue
     *
     * @param playerName The name of the player
     * @param roomId     The id of the room in which is the player who is making the action
     * @param cardId     The id of the card to flip
     */
    @Override
    public void flipCard(String playerName, String roomId, int cardId) {
        FlipCardAction flipCard = new FlipCardAction(playerName, mainController.getRooms().get(roomId), cardId);
        try {
            actions.put(flipCard);
        } catch (InterruptedException e) {
            System.err.println("L'inserimento dell'azione fliCard nella coda è stato interrotto.");
        }
    }

    /**
     * Creates a PlayCardAction and put it in the Queue
     *
     * @param playerName The name of the player
     * @param roomId     The id of the room in which is the player who is making the action
     * @param cardId     The id of the card to play
     * @param position   The position in the player field
     */
    @Override
    public void playCard(String playerName, String roomId, int cardId, Position position) {
        PlayCardAction playCard = new PlayCardAction(playerName, mainController.getRooms().get(roomId), cardId, position);
        try {
            actions.put(playCard);
        } catch (InterruptedException e) {
            System.err.println("L'inserimento dell'azione playCard nella coda è stato interrotto.");
        }
    }

    /**
     * Creates a DrawCardAction and put it in the Queue
     *
     * @param playerName The name of the player
     * @param roomId     The id of the room in which is the player who is making the action
     * @param choice     The position of the card to draw in the Drawable cards
     */
    @Override
    public void drawCard(String playerName, String roomId, int choice) {
        DrawCardAction drawCard = new DrawCardAction(playerName, mainController.getRooms().get(roomId), choice);
        try {
            actions.put(drawCard);
        } catch (InterruptedException e) {
            System.err.println("L'inserimento dell'azione drawCard nella coda è stato interrotto.");
        }
    }

    /**
     * Creates a LeaveAction and put it in the Queue
     *
     * @param playerName The name of the player
     * @param roomId     The id of the room in which is the player who is making the action
     */
    @Override
    public void leave(String playerName, String roomId) {
        LeaveAction leave = new LeaveAction(playerName, mainController.getRooms().get(roomId));
        try {
            actions.put(leave);
        } catch (InterruptedException e) {
            System.err.println("L'inserimento dell'azione leave nella coda è stato interrotto.");
        }
    }
}