package it.polimi.ingsw.gc01.network.rmi;

import it.polimi.ingsw.gc01.model.player.Player;
import it.polimi.ingsw.gc01.view.UI;
import it.polimi.ingsw.gc01.model.DefaultValue;
import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.model.room.TablePosition;
import it.polimi.ingsw.gc01.network.*;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class RmiClient extends UnicastRemoteObject implements VirtualView, NetworkClient {
    private final String playerName;
    private VirtualServer server;
    private String roomId;
    private final UI ui;

    public RmiClient(String playerName, UI userInterface) throws RemoteException {
        this.playerName = playerName;
        this.ui = userInterface;
        connect();
    }

    /**
     * the method connects the client to the server creating his own stub
     */
    private void connect() {
        try {
            Registry registry = LocateRegistry.getRegistry(DefaultValue.ServerIp, DefaultValue.RMIPort);
            this.server = (VirtualServer) registry.lookup(DefaultValue.RMIServerName);
            System.out.println("Client RMI ready");
        } catch (RemoteException | NotBoundException e) {
            //TODO
            System.err.println("Server RMI not working!");
        }
    }

    public String getRoomId(){
        return roomId;
    }

    /**
     * asks stub to create game
     */
    @Override
    public void createGame() {
        try {
            server.createGame(this.playerName, this);
        } catch (RemoteException e) {
            //TODO
            System.err.println("Server RMI not working!");
        }
    }

    /**
     * asks stub to join game
     */
    @Override
    public void joinGame(String roomId) {
        try {
            server.joinGame(this.playerName, this, roomId);
        } catch (RemoteException e) {
            //TODO
            System.err.println("Server RMI not working!");
        }
    }

    /**
     * asks stub to join the first available game
     */
    @Override
    public void joinFirstGame() {
        try {
            server.joinFirstGame(this.playerName, this);
        } catch (RemoteException e) {
            //TODO
            System.err.println("Server RMI not working!");
        }
    }

    /**
     * asks stub to choose the color of the player
     *
     * @param color the color wich will be given to the player
     */
    @Override
    public void chooseColor(PlayerColor color) {
        try {
            server.chooseColor(this.playerName, this.roomId, color);
        } catch (RemoteException e) {
            //TODO
            System.err.println("Server RMI not working!");
        }
    }

    /**
     * asks stub to set the player ready
     */
    @Override
    public void switchReady() {
        try {
            server.switchReady(this.playerName, this.roomId);
        } catch (RemoteException e) {
            //TODO
            System.err.println("Server RMI not working!");
        }
    }

    /**
     * asks stub to choose the secret objective
     *
     * @param cardId the id of the chosen objective card
     */
    @Override
    public void chooseSecretObjective(int cardId) {
        try {
            server.chooseSecretObjective(this.playerName, this.roomId, cardId);
        } catch (RemoteException e) {
            //TODO
            System.err.println("Server RMI not working!");
        }
    }

    /**
     * asks stub to flip the selected card
     *
     * @param cardId the id of the card to flip
     */
    @Override
    public void flipCard(int cardId) {
        try {
            server.flipCard(this.playerName, this.roomId, cardId);
        } catch (RemoteException e) {
            //TODO
            System.err.println("Server RMI not working!");
        }
    }

    /**
     * asks stub to play the card in the position with coordinates x ,y
     *
     * @param cardId the id of the card to play
     * @param x      the x coordinate in the matrix of the player field
     * @param y      the y coordinate in the matrix of the player field
     */
    @Override
    public void playCard(int cardId, int x, int y) {
        try {
            server.playCard(this.playerName, this.roomId, cardId, x, y);
        } catch (RemoteException e) {
            //TODO
            System.err.println("Server RMI not working!");
        }
    }

    /**
     * asks stub to draw card from a certain Table position
     *
     * @param card the position of the card to draw in the drawableCard positions
     */
    @Override
    public void drawCard(TablePosition card) {
        try {
            server.drawCard(this.playerName, this.roomId, card);
        } catch (RemoteException e) {
            //TODO
            System.err.println("Server RMI not working!");
        }
    }

    /**
     * asks stub to make the player leave the game
     */
    @Override
    public void leave() {
        try {
            server.leave(this.playerName, this.roomId);
        } catch (RemoteException e) {
            //TODO
            System.err.println("Server RMI not working!");
        }
    }

    /**
     * @param roomId
     */
    @Override
    public void updateRoomId(String roomId) throws RemoteException {
       this.roomId = roomId;
       ui.showRoom(roomId);
    }

    @Override
    public void showStarter(int cardId){
        String input = "";
        Scanner scanner = new Scanner(System.in);
        if (cardId == 81){
            System.out.println("""
                    ╔═══╦═══════════════╦═══╗       ╔═══╦═══════════════╦═══╗
                    ║   ║               ║ P ║       ║ F ║               ║ P ║
                    ╠═══╝     ╔═══╗     ╚═══║       ║═══╝     ╔═══╗     ╚═══║
                    ║         ║ I ║         ║       ║         ║   ║         ║
                    ╠═══╗     ╚═══╝     ╔═══╣       ╠═══╗     ╚═══╝     ╔═══║
                    ║ I ║               ║   ║       ║ I ║               ║ A ║
                    ╚═══╩═══════════════╩═══╝       ╚═══╩═══════════════╩═══╝
                    """);
        }
        if (cardId == 82){
            System.out.println("""
                    ╔═══╦═══════════════╦═══╗       ╔═══╦═══════════════╦═══╗
                    ║ A ║               ║   ║       ║ P ║               ║ A ║
                    ╠═══╝     ╔═══╗     ╚═══║       ║═══╝     ╔═══╗     ╚═══║
                    ║         ║ F ║         ║       ║         ║   ║         ║
                    ╠═══╗     ╚═══╝     ╔═══╣       ╠═══╗     ╚═══╝     ╔═══║
                    ║   ║               ║ F ║       ║ F ║               ║ I ║
                    ╚═══╩═══════════════╩═══╝       ╚═══╩═══════════════╩═══╝
                    """);
        }
        if (cardId == 83){
            System.out.println("""
                    ╔═══╦═══════════════╦═══╗       ╔═══╦═══════════════╦═══╗
                    ║   ║               ║   ║       ║ I ║               ║ A ║
                    ╠═══╝     ╔═══╗     ╚═══║       ║═══╝     ╔═══╗     ╚═══║
                    ║         ║P F║         ║       ║         ║   ║         ║
                    ╠═══╗     ╚═══╝     ╔═══╣       ╠═══╗     ╚═══╝     ╔═══║
                    ║   ║               ║   ║       ║ F ║               ║ P ║
                    ╚═══╩═══════════════╩═══╝       ╚═══╩═══════════════╩═══╝
                    """);
        }
        if (cardId == 84){
            System.out.println("""
                    ╔═══╦═══════════════╦═══╗       ╔═══╦═══════════════╦═══╗
                    ║   ║               ║   ║       ║ P ║               ║ I ║
                    ╠═══╝     ╔═══╗     ╚═══║       ║═══╝     ╔═══╗     ╚═══║
                    ║         ║A I║         ║       ║         ║   ║         ║
                    ╠═══╗     ╚═══╝     ╔═══╣       ╠═══╗     ╚═══╝     ╔═══║
                    ║   ║               ║   ║       ║ A ║               ║ F ║
                    ╚═══╩═══════════════╩═══╝       ╚═══╩═══════════════╩═══╝
                    """);
        }
        if (cardId == 85){
            System.out.println("""
                    ╔═══╦═══════════════╦═══╗       ╔═══╦═══════════════╦═══╗
                    ║   ║               ║   ║       ║ I ║               ║ F ║
                    ╠═══╝     ╔═══╗     ╚═══║       ║═══╝     ╔═══╗     ╚═══║
                    ║         ║AIP║         ║       ║         ║   ║         ║
                    ╠═══╗     ╚═══╝     ╔═══╣       ╠═══╗     ╚═══╝     ╔═══║
                    ║▓▓▓║               ║▓▓▓║       ║ P ║               ║ A ║
                    ╚═══╩═══════════════╩═══╝       ╚═══╩═══════════════╩═══╝
                    """);
        }
        if (cardId == 86){
            System.out.println("""
                    ╔═══╦═══════════════╦═══╗       ╔═══╦═══════════════╦═══╗
                    ║   ║               ║   ║       ║ F ║               ║ A ║
                    ╠═══╝     ╔═══╗     ╚═══║       ║═══╝     ╔═══╗     ╚═══║
                    ║         ║PAF║         ║       ║         ║   ║         ║
                    ╠═══╗     ╚═══╝     ╔═══╣       ╠═══╗     ╚═══╝     ╔═══║
                    ║▓▓▓║               ║▓▓▓║       ║ P ║               ║ I ║
                    ╚═══╩═══════════════╩═══╝       ╚═══╩═══════════════╩═══╝
                    """);
        }
        System.out.println("Choose (1) front or (2) back of the radix card:");
        while (input.isEmpty()){
            input = scanner.nextLine();
            if (input.isEmpty()){
                System.out.println(DefaultValue.ANSI_RED + "Wrong choice" + DefaultValue.ANSI_RESET);
            }
        }
    }

    /**
     * @param availableColors
     */
    @Override
    public void showAvailableColors(List<PlayerColor> availableColors) throws RemoteException {
        String input = "";
        int choice = 0;
        Scanner scanner = new Scanner(System.in);
        while (input.isEmpty()){
            System.out.println("Select your color:\n");
            if (availableColors.contains(PlayerColor.RED)) System.out.println(DefaultValue.ANSI_RED +"(1) RED" + DefaultValue.ANSI_RESET);
            if (availableColors.contains(PlayerColor.BLUE)) System.out.println(DefaultValue.ANSI_BLUE +"(2) BLUE" + DefaultValue.ANSI_RESET);
            if (availableColors.contains(PlayerColor.GREEN)) System.out.println(DefaultValue.ANSI_GREEN +"(3) GREEN" + DefaultValue.ANSI_RESET);
            if (availableColors.contains(PlayerColor.YELLOW)) System.out.println(DefaultValue.ANSI_YELLOW +"(4) YELLOW" + DefaultValue.ANSI_RESET);

            input = scanner.nextLine();
            try{
                choice = Integer.parseInt(input);
                if (choice != 1 && choice != 2 && choice != 3 && choice != 4){
                    input = "";
                    System.out.println(DefaultValue.ANSI_RED + "Wrong choice" + DefaultValue.ANSI_RESET);
                }
                if (choice == 1 && !availableColors.contains(PlayerColor.RED)){
                    input = "";
                    System.out.println("Color RED already taken");
                }
                if (choice == 2 && !availableColors.contains(PlayerColor.BLUE)){
                    input = "";
                    System.out.println("Color BLUE already taken");
                }
                if (choice == 3 && !availableColors.contains(PlayerColor.GREEN)){
                    input = "";
                    System.out.println("Color GREEN already taken");
                }
                if (choice == 4 && !availableColors.contains(PlayerColor.YELLOW)){
                    input = "";
                    System.out.println("Color YELLOW already taken");
                }
            } catch (NumberFormatException e){
                input = "";
            }
            if (input.isEmpty()){
                System.out.println(DefaultValue.ANSI_RED + "Wrong choice" + DefaultValue.ANSI_RESET);
            }
        }

        switch (choice){
            case 1 -> server.chooseColor(playerName, roomId, PlayerColor.RED);
            case 2 -> server.chooseColor(playerName, roomId, PlayerColor.BLUE);
            case 3 -> server.chooseColor(playerName, roomId, PlayerColor.GREEN);
            case 4 -> server.chooseColor(playerName, roomId, PlayerColor.YELLOW);
        }


    }

    /**
     * @param ready
     */
    @Override
    public void updateReady(boolean ready) throws RemoteException {
        //TODO
    }

    /**
     * @param objectivesIds
     */
    @Override
    public void showCommonObjectives(List<Integer> objectivesIds) throws RemoteException {
        //TODO
    }

    /**
     * @param drawableCardsIds
     */
    @Override
    public void showTable(Map<TablePosition, Integer> drawableCardsIds) throws RemoteException {
        //TODO
    }

    /**
     * @param cardIds
     */
    @Override
    public void showHand(List<Integer> cardIds) throws RemoteException {
        //TODO
    }

    /**
     * @param playerName
     * @param cardId
     * @param x
     * @param y
     */
    @Override
    public void showField(String playerName, int cardId, int x, int y) throws RemoteException {
        //TODO
    }

    /**
     * @param possibleObjectivesIds
     */
    @Override
    public void showSecretObjectives(List<Integer> possibleObjectivesIds) throws RemoteException {
        //TODO
    }

    /**
     * @param error
     */
    @Override
    public void showError(String error) throws RemoteException {
        ui.showError(error);
    }

    /**
     * @param message
     */
    @Override
    public void serviceMessage(String message) throws RemoteException {
        ui.showServiceMessage(message);
    }
}