package it.polimi.ingsw.gc01.view;

import it.polimi.ingsw.gc01.model.DefaultValue;
import it.polimi.ingsw.gc01.network.NetworkClient;
import it.polimi.ingsw.gc01.network.rmi.RmiClient;

import java.util.*;

public class TUI implements UI {
    private NetworkClient client;

    public TUI(){
        new Thread(this).start();
    }

    private String askPlayerName(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type in the nickname that you want to use to play CODEX NATURALIS (lazzar edition)");
        return scanner.nextLine();
    }

    private void askServerIP(){
        String input;
        do {
            System.out.println("Type in the Remote IP or leave empty for localhost:");
            input = new Scanner(System.in).nextLine();
            if (!input.isEmpty() && !isValidIP(input)){
                System.out.println(DefaultValue.ANSI_RED + "Not valid ip" + DefaultValue.ANSI_RESET);
            }
        } while(!input.isEmpty() && !isValidIP(input));
        if (!input.isEmpty())
            DefaultValue.ServerIp = input;
    }

    private void askPlayerIP(){
        String input;
        do {
            System.out.println("Type in your IP or leave empty for localhost:");
            input = new Scanner(System.in).nextLine();
            if (!input.isEmpty() && !isValidIP(input)){
                System.out.println(DefaultValue.ANSI_RED + "Not valid ip" + DefaultValue.ANSI_RESET);
            }
        } while(!input.isEmpty() && !isValidIP(input));
        if (!input.isEmpty())
            System.setProperty("java.rmi.server.hostname", input);
    }

    private static boolean isValidIP(String input) {
        List<String> parsed;
        parsed = Arrays.stream(input.split("\\.")).toList();
        if (parsed.size() != 4) {
            return false;
        }
        for (String part : parsed) {
            try {
                Integer.parseInt(part);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    private void createRMIClient(String playerName) {
        try{
            client = new RmiClient(playerName, this);;
        } catch (Exception e){
            System.out.println(DefaultValue.ANSI_RED + "Cannot instance RmiClient" + DefaultValue.ANSI_RESET);
        }
    }

    private boolean askModalityToEnterGame() {
        System.out.println("""
                Would you rather:
                (1) Create a new Game
                (2) Join the first Available Game
                (3) Join a Game by ID
                """);
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        int choice = 0;
        try {
            choice = Integer.parseInt(input);
        } catch (Exception ignored){}
        switch (choice){
            case (1):
                client.createGame();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return client.getRoomId() != null;
            case (2):
                client.joinFirstGame();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return client.getRoomId() != null;
            case (3):
                System.out.println("Type the roomId of the Game you want to join...");
                String roomId = scanner.nextLine();
                while (!checkRoomId(roomId)){
                    System.out.println(DefaultValue.ANSI_RED + "RoomId not valid" + DefaultValue.ANSI_RESET);
                    roomId = scanner.nextLine();
                }
                client.joinGame(roomId.toUpperCase());
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return client.getRoomId() != null;
            default: return false;
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
                System.exit(1);
                return;
            }
            if (ready.equals("y")){
                client.switchReady();
                return;
            }
        }
    }

    private boolean checkRoomId(String roomId){
        return roomId.length() == 5;
    }

    @Override
    public void showRoom(String roomId) {
        System.out.println(DefaultValue.ANSI_BLUE + "\n\n[Joined room with id: "+ roomId + "]" + DefaultValue.ANSI_RESET);
    }

    @Override
    public void showError(String error) {
        String type = error.substring(0, error.indexOf(" "));
        String message = error.substring(error.indexOf(" ")+1);
        switch(type) {
            case "MAIN":
                System.out.println(DefaultValue.ANSI_RED + message + DefaultValue.ANSI_RESET);
        }
    }

    @Override
    public void showServiceMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void run() {
        String playerName = askPlayerName();
        boolean status = false;

        askServerIP();
        askPlayerIP();
        createRMIClient(playerName);
        while (!status){
            status = askModalityToEnterGame();
        }
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        new Thread(this::askReady).start();
        while (!Thread.interrupted()){


        }
    }
}