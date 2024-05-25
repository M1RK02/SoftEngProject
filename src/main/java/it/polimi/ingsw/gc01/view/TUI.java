package it.polimi.ingsw.gc01.view;

import it.polimi.ingsw.gc01.model.DefaultValue;
import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.network.NetworkClient;
import it.polimi.ingsw.gc01.network.rmi.RmiClient;

import java.util.*;

public class TUI implements UI {
    private NetworkClient networkClient;

    public TUI(){
        start();
    }

    public void start() {
        String playerName = askPlayerName();
        askServerIP();
        askPlayerIP();
        createRMIClient(playerName);
        new Thread(this::askModalityToEnterGame).start();
    }

    private String askPlayerName(){
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (input.isEmpty()){
            System.out.println("Type in the nickname that you want to use to play CODEX NATURALIS (lazzar edition)");
            input = scanner.nextLine();
            if (input.isEmpty()){
                System.out.println(DefaultValue.ANSI_RED + "Name can't be empty" + DefaultValue.ANSI_RESET);
            }
        }
        return input;
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
            networkClient = new RmiClient(playerName, this);
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
                (4) Exit
                """);
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        int choice = 0;
        try {
            choice = Integer.parseInt(input);
        } catch (Exception ignored){}
        switch (choice){
            case (1):
                networkClient.createGame();
                break;
            case (2):
                networkClient.joinFirstGame();
                break;
            case (3):
                System.out.println("Type the roomId of the Game you want to join...");
                String roomId = scanner.nextLine();
                while (!checkRoomId(roomId)){
                    System.out.println(DefaultValue.ANSI_RED + "RoomId not valid" + DefaultValue.ANSI_RESET);
                    roomId = scanner.nextLine();
                }
                networkClient.joinGame(roomId.toUpperCase());
                break;
            case (4):
                System.out.println("Exiting");
                System.exit(1);
            default:
                System.out.println(DefaultValue.ANSI_RED + "Invalid choice" + DefaultValue.ANSI_RESET);
                askModalityToEnterGame();
        }
    }

    private void askReady(){
        String ready = "";
        System.out.println(DefaultValue.ANSI_YELLOW + """
                        (y) to ready up
                        (l) to leave
                        """ + DefaultValue.ANSI_RESET);
        Scanner scanner = new Scanner(System.in);
        while (true){
            ready = scanner.nextLine();
            if (ready.equals("l")){
                networkClient.leave();
                new Thread(this::askModalityToEnterGame).start();
                return;
            }
            if (ready.equals("y")){
                networkClient.switchReady();
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
        new Thread(this::askReady).start();
    }

    @Override
    public void showCurrentPlayer(String playerName) {
        System.out.println(DefaultValue.ANSI_WHITE + "-> Current player is: " + playerName + " !" + DefaultValue.ANSI_RESET);
    }

    @Override
    public void showError(String error) {
        String type = error.substring(0, error.indexOf(" "));
        String message = error.substring(error.indexOf(" ")+1);
        switch(type) {
            case "MAIN":
                System.out.println(DefaultValue.ANSI_RED + message + DefaultValue.ANSI_RESET);
                new Thread(this::askModalityToEnterGame).start();
        }
    }

    @Override
    public void showServiceMessage(String message) {
        System.out.println(message);
    }

    public void showStarter(int cardId){
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
        new Thread(() -> chooseStarter(cardId)).start();
    }

    private void chooseStarter(int cardId){
        String input = "";
        Scanner scanner = new Scanner(System.in);
        while (input.isEmpty()){
            input = scanner.nextLine();
            if (input.isEmpty()){
                System.out.println(DefaultValue.ANSI_RED + "Wrong choice" + DefaultValue.ANSI_RESET);
            }
        }
        int choice = Integer.parseInt(input);
        if (choice == 1){
            networkClient.playCard(cardId, 0, 0);
        } else {
            networkClient.flipCard(cardId);
            networkClient.playCard(cardId, 0, 0);
        }
    }

    /**
     * @param availableColors
     */
    @Override
    public void showAvailableColors(List<PlayerColor> availableColors){
        System.out.println("Select your color:\n");
        if (availableColors.contains(PlayerColor.RED)) System.out.println(DefaultValue.ANSI_RED +"(1) RED" + DefaultValue.ANSI_RESET);
        if (availableColors.contains(PlayerColor.BLUE)) System.out.println(DefaultValue.ANSI_BLUE +"(2) BLUE" + DefaultValue.ANSI_RESET);
        if (availableColors.contains(PlayerColor.GREEN)) System.out.println(DefaultValue.ANSI_GREEN +"(3) GREEN" + DefaultValue.ANSI_RESET);
        if (availableColors.contains(PlayerColor.YELLOW)) System.out.println(DefaultValue.ANSI_YELLOW +"(4) YELLOW" + DefaultValue.ANSI_RESET);
        new Thread(() -> chooseColor(availableColors)).start();
    }

    private void chooseColor(List<PlayerColor> availableColors){
        String input = "";
        int choice = 0;
        Scanner scanner = new Scanner(System.in);
        while (input.isEmpty()) {

            input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input);
                if (choice != 1 && choice != 2 && choice != 3 && choice != 4) {
                    input = "";
                    System.out.println(DefaultValue.ANSI_RED + "Wrong choice" + DefaultValue.ANSI_RESET);
                }
                if (choice == 1 && !availableColors.contains(PlayerColor.RED)) {
                    input = "";
                    System.out.println("Color RED already taken");
                }
                if (choice == 2 && !availableColors.contains(PlayerColor.BLUE)) {
                    input = "";
                    System.out.println("Color BLUE already taken");
                }
                if (choice == 3 && !availableColors.contains(PlayerColor.GREEN)) {
                    input = "";
                    System.out.println("Color GREEN already taken");
                }
                if (choice == 4 && !availableColors.contains(PlayerColor.YELLOW)) {
                    input = "";
                    System.out.println("Color YELLOW already taken");
                }
            } catch (NumberFormatException e) {
                input = "";
            }
            if (input.isEmpty()) {
                System.out.println(DefaultValue.ANSI_RED + "Wrong choice" + DefaultValue.ANSI_RESET);
            }
        }

        switch (choice){
            case 1 -> networkClient.chooseColor(PlayerColor.RED);
            case 2 -> networkClient.chooseColor(PlayerColor.BLUE);
            case 3 -> networkClient.chooseColor(PlayerColor.GREEN);
            case 4 -> networkClient.chooseColor(PlayerColor.YELLOW);
        }
    }
}