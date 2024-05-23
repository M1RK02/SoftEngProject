package it.polimi.ingsw.gc01.view;

import it.polimi.ingsw.gc01.model.DefaultValue;
import it.polimi.ingsw.gc01.network.NetworkClient;
import it.polimi.ingsw.gc01.network.rmi.RmiClient;

import java.rmi.RemoteException;
import java.util.Locale;
import java.util.Scanner;

public class TUI implements UI {
    private NetworkClient client;

    public TUI(){
        String playerName = askPlayerName();
        askAndSetIP();
        askAndSetPort();
        createRMIClient(playerName);
        askModalityToEnterGame();
    }

    private String askPlayerName(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type in the nickname that you want to use to play CODEX NATURALIS (lazzar edidtion)");
        return scanner.nextLine();
    }

    private void askAndSetIP(){
        System.out.println("\nType in the Server IP or leave emtpy for localhost:");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.isEmpty()){
            DefaultValue.ServerIp = "localhost";
        } else {
            DefaultValue.ServerIp = input;
        }
    }

    private void askAndSetPort(){
        System.out.println("On which port are you going to connect your computer?" );
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.isEmpty()){
            DefaultValue.RMIPort = 1234;
        } else {
            DefaultValue.RMIPort = Integer.parseInt(input);
        }
    }

    private void createRMIClient(String playerName) {
        try{
            client = new RmiClient(playerName, this);;
        } catch (Exception e){
            System.out.println(DefaultValue.ANSI_RED + "Cannot instance RmiClient" + DefaultValue.ANSI_RESET);
        }
    }

    private void askModalityToEnterGame() {
        System.out.println("""
                Would you rather:
                (1) Create a new Game
                (2) Join the first Available Game
                (3) Join a Game by ID
                """);
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        int choice = Integer.parseInt(input);
        switch (choice){
            case (1):
                client.createGame();
                break;
            case (2):
                client.joinFirstGame();
                break;
            case (3):
                System.out.println("Type the roomId of the Game you want to join...");
                String roomId = scanner.nextLine();
                while (!checkRoomId(roomId)){
                    System.out.println(DefaultValue.ANSI_RED + "RoomId not valid" + DefaultValue.ANSI_RESET);
                    roomId = scanner.nextLine();
                }
                client.joinGame(roomId.toUpperCase());
                break;
        }
    }

    private void askReady(){
        String ready = "";
        System.out.println("""
                        (y) to ready up
                        (l) to leave
                        """);
        Scanner scanner = new Scanner(System.in);
        while (true){
            ready = scanner.nextLine();
            if (ready.equals("l")){
                client.leave();
                System.out.println("Leaved game");
                return;
            }
            if (ready.equals("y")){
                client.switchReady();
                System.out.println("Ready");
                return;
            }
        }
    }

    private boolean checkRoomId(String roomId){
        return roomId.length() == 5;
    }

    @Override
    public void showRoom(String roomId) {
        System.out.println(DefaultValue.ANSI_BLUE + "[Joined room with id: "+ roomId + "]" + DefaultValue.ANSI_RESET);
        new Thread(this::askReady).start();
    }

    @Override
    public void showError(String error) {
        String type = error.substring(0, error.indexOf(" "));
        String message = error.substring(error.indexOf(" ")+1);
        switch(type) {
            case "MAIN":
                System.out.println(DefaultValue.ANSI_RED + message + DefaultValue.ANSI_RESET);
                askModalityToEnterGame();
        }
    }

    @Override
    public void showServiceMessage(String message) {
        System.out.println(message);
    }
}