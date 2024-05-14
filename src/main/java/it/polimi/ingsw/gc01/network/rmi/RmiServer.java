package it.polimi.ingsw.gc01.network.rmi;


import it.polimi.ingsw.gc01.controller.MainController;
import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.model.room.TablePosition;
import it.polimi.ingsw.gc01.network.VirtualView;
import it.polimi.ingsw.gc01.network.actions.Action;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class RmiServer implements VirtualServer {

    MainController mainController;
    BlockingQueue<Action> actions;



    public  RmiServer(MainController mainController) {
        this.mainController = mainController;
        actions = new ArrayBlockingQueue<Action>(20);
    }


    public void bind(){}

    public void executeActions(){}

    public void createGame(String playerName, VirtualView client){}

    public void joinGame(String playerName, VirtualView client, String roomId){}

    public void joinFirstGame(String playerName, VirtualView client){}

    public void chooseColor(String playerName, String roomId, PlayerColor color){}

    public void changeReady(String playerName, String roomId){}

    public void chooseSecretObjective(String playerName, String roomId, int cardId){}

    public void flipCard(String playerName, String roomId, int cardId){}

    public void playCard(String playerName, String roomId, int cardId, int x, int y){}

    public void drawCard(String playerName, String roomId, TablePosition card){}

    public void leave(String playerName, String roomId){}



}
