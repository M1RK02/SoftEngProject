package it.polimi.ingsw.gc01.view.tui;

import it.polimi.ingsw.gc01.model.player.*;
import it.polimi.ingsw.gc01.network.NetworkClient;
import it.polimi.ingsw.gc01.network.rmi.RmiClient;
import it.polimi.ingsw.gc01.utils.DefaultValue;
import it.polimi.ingsw.gc01.view.UI;

import java.util.*;

/**
 * Class to manage the TUI of the game
 */
public class TUI implements UI {
    /**
     * Deck of cards
     */
    private final ClientDeck clientDeck;
    /**
     * Name of the player
     */
    private final String playerName;
    /**
     * Field of the player
     */
    private ClientField field;
    /**
     * Map of other players fields
     */
    private Map<String, ClientField> otherFields;
    /**
     * Chosen network interface
     */
    private NetworkClient networkClient;
    /**
     * Ids of the cards in the hand
     */
    private List<Integer> handIds;

    /**
     * Construct a new TUI object and starts it
     */
    public TUI() {
        clientDeck = new ClientDeck();
        playerName = askPlayerName();
        start();
    }

    /**
     * Check if the input is a valid IP address
     *
     * @param input string to check
     * @return true if is a valid IP, otherwise false
     */
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

    /**
     * Starts the tui asking server and player IPs
     */
    public void start() {
        askServerIP();
        askPlayerIP();
        switch (askConnection()) {
            case 1 -> createRMIClient(playerName);
            case 2 -> createSocketClient(playerName);
        }
        new Thread(this::askModalityToEnterGame).start();
    }

    /**
     * Ask the player name
     *
     * @return the player name
     */
    private String askPlayerName() {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (input.isEmpty()) {
            System.out.println("Type in the nickname that you want to use to play CODEX NATURALIS (lazzar edition)");
            input = scanner.nextLine();
            if (input.isEmpty()) {
                System.out.println(DefaultValue.ANSI_RED + "Name can't be empty" + DefaultValue.ANSI_RESET);
            }
        }
        return input;
    }

    /**
     * Ask the server ip
     */
    private void askServerIP() {
        String input;
        do {
            System.out.println("Type in the Remote IP or leave empty for localhost:");
            input = new Scanner(System.in).nextLine();
            if (!input.isEmpty() && !isValidIP(input)) {
                System.out.println(DefaultValue.ANSI_RED + "Not valid ip" + DefaultValue.ANSI_RESET);
            }
        } while (!input.isEmpty() && !isValidIP(input));
        if (!input.isEmpty())
            DefaultValue.ServerIp = input;
    }

    /**
     * Ask the player ip
     */
    private void askPlayerIP() {
        String input;
        do {
            System.out.println("Type in your IP or leave empty for localhost:");
            input = new Scanner(System.in).nextLine();
            if (!input.isEmpty() && !isValidIP(input)) {
                System.out.println(DefaultValue.ANSI_RED + "Not valid ip" + DefaultValue.ANSI_RESET);
            }
        } while (!input.isEmpty() && !isValidIP(input));
        if (!input.isEmpty())
            System.setProperty("java.rmi.server.hostname", input);
    }

    /**
     * Ask the type of connection
     *
     * @return 1 for RMI, 2 for Socket
     */
    private int askConnection() {
        System.out.println("Select connection type:\n(1) RMI\n(2) SOCKET");
        do {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            try {
                int choice = Integer.parseInt(input);
                if (choice == 1 || choice == 2) {
                    return choice;
                }
                System.out.println("Wrong choice");
            } catch (NumberFormatException e) {
                System.out.println("Wrong format");
            }
        } while (true);
    }

    /**
     * Create the RMI network client
     *
     * @param playerName
     */
    private void createRMIClient(String playerName) {
        try {
            networkClient = new RmiClient(playerName, this);
        } catch (Exception e) {
            System.out.println(DefaultValue.ANSI_RED + "Cannot instance RmiClient" + DefaultValue.ANSI_RESET);
        }
    }

    /**
     * Create the Socket network client
     *
     * @param playerName
     */
    private void createSocketClient(String playerName) {
        System.out.println(DefaultValue.ANSI_YELLOW + "Socket is currently work in progress..." + DefaultValue.ANSI_RESET);
        System.out.println("Defaulting to RMI...\n");
        createRMIClient(playerName);
    }

    /**
     * Ask the modality to enter a game
     */
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
        } catch (Exception ignored) {
        }
        switch (choice) {
            case (1):
                networkClient.createGame();
                break;
            case (2):
                networkClient.joinFirstGame();
                break;
            case (3):
                System.out.println("Type the roomId of the Game you want to join...");
                String roomId = scanner.nextLine();
                while (!checkRoomId(roomId)) {
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

    /**
     * Ask if the player is ready
     */
    private void askReady() {
        String input = "";
        System.out.println(DefaultValue.ANSI_YELLOW + """
                (y) to ready up
                (l) to leave
                """ + DefaultValue.ANSI_RESET);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            input = scanner.nextLine();
            if (input.equals("l")) {
                networkClient.leave();
                System.out.println(DefaultValue.ANSI_GREEN + "\n\n-> Going back to menu\n" + DefaultValue.ANSI_RESET);
                new Thread(this::askModalityToEnterGame).start();
                return;
            }
            if (input.equals("y")) {
                networkClient.switchReady();
                return;
            }
        }
    }

    /**
     * Check if the room id is valid
     *
     * @param roomId to check
     * @return true if is valid, false otherwise
     */
    private boolean checkRoomId(String roomId) {
        return roomId.length() == 5;
    }

    /**
     * Print the joined room
     *
     * @param roomId of the room
     */
    @Override
    public void showRoom(String roomId) {
        System.out.println(DefaultValue.ANSI_BLUE + "\n\n[Joined room with id: " + roomId + "]" + DefaultValue.ANSI_RESET);
        new Thread(this::askReady).start();
    }

    /**
     * Print the players in the room
     *
     * @param playerNames the list of the names of the players in the room
     */
    @Override
    public void showPlayers(List<String> playerNames) {
        StringBuilder message = new StringBuilder(DefaultValue.ANSI_BLUE + "[Current players in the room: ");
        for (String names : playerNames) {
            message.append("- ").append(names).append(" ");
        }
        message.append("]\n" + DefaultValue.ANSI_RESET);
        System.out.println(message);
    }

    /**
     * Shows the players that has just joined
     *
     * @param playerName the names of the players that has just joined
     */
    @Override
    public void showPlayerJoined(String playerName) {
        System.out.println(DefaultValue.ANSI_GREEN + "-> " + playerName + " joined!" + DefaultValue.ANSI_RESET);
    }

    /**
     * Shows the players that has just left
     *
     * @param playerName the names of the players that has just left
     */
    @Override
    public void showPlayerLeft(String playerName) {
        System.out.println(DefaultValue.ANSI_RED + "-> " + playerName + " left the room!" + DefaultValue.ANSI_RESET);
    }

    /**
     * Shows the waiting scene for every client except the one choosing
     *
     * @param playerName of the player choosing
     * @param scene      the name of the scene waiting for
     */
    @Override
    public void showWaitingFor(String playerName, String scene) {

    }

    /**
     * Print the notification for the game start
     */
    @Override
    public void startGame() {
        field = new ClientField();
        otherFields = new HashMap<>();
        System.out.println(DefaultValue.ANSI_PURPLE + "Game is starting!" + DefaultValue.ANSI_RESET);
    }

    /**
     * Print the current player
     *
     * @param playerName of the current player
     */
    @Override
    public void showCurrentPlayer(String playerName) {
        if (playerName.equals(this.playerName)) {
            System.out.println(DefaultValue.ANSI_WHITE + "-> It's your turn!" + DefaultValue.ANSI_RESET);
        } else {
            System.out.println(DefaultValue.ANSI_WHITE + "-> Current player is: " + playerName + " !" + DefaultValue.ANSI_RESET);
        }
    }

    /**
     * Print the field for the indicated player
     *
     * @param playerName of the player
     */
    @Override
    public void showField(String playerName) {
        if (playerName.equals(this.playerName)) {
            System.out.println(DefaultValue.ANSI_YELLOW + "-> Your Field:\n" + DefaultValue.ANSI_RESET);
            field.printUsedField();
        } else {
            System.out.println(DefaultValue.ANSI_YELLOW + "-> " + playerName + "'s Field:\n" + DefaultValue.ANSI_RESET);
            otherFields.get(playerName).printUsedField();
        }
    }

    /**
     * Print the points for every player
     *
     * @param points map of playerName, points
     */
    @Override
    public void showPoints(Map<String, Integer> points) {
        System.out.println(DefaultValue.ANSI_BLUE + "-> Points:" + DefaultValue.ANSI_RESET);
        for (String playerName : points.keySet()) {
            System.out.println(DefaultValue.ANSI_BLUE + playerName + ": " + points.get(playerName) + " points" + DefaultValue.ANSI_RESET);
        }
    }

    /**
     * Print and manage the received error
     *
     * @param error to show
     */
    @Override
    public void showError(String error) {
        String type = error.substring(0, error.indexOf(" "));
        String message = error.substring(error.indexOf(" ") + 1);
        switch (type) {
            case "MAIN":
                System.out.println(DefaultValue.ANSI_RED + message + DefaultValue.ANSI_RESET);
                new Thread(this::askModalityToEnterGame).start();
                break;
            case "PLAY":
                System.out.println(DefaultValue.ANSI_RED + message + DefaultValue.ANSI_RESET);
                new Thread(this::chooseCardToPlay).start();
                break;
            case "DRAW":
                System.out.println(DefaultValue.ANSI_RED + message + DefaultValue.ANSI_RESET);
                new Thread(this::chooseCardToDraw).start();
                break;
            case "GAME":
                System.out.println(DefaultValue.ANSI_RED + message + DefaultValue.ANSI_RESET);
                networkClient.leave();
                System.exit(0);
        }
    }

    /**
     * Print the service message
     *
     * @param message to show
     */
    @Override
    public void showServiceMessage(String message) {
        System.out.println(message);
    }

    /**
     * Print the notification for the last turn
     */
    @Override
    public void showLastCircle() {
        System.out.println(DefaultValue.ANSI_PURPLE + "-> Last circle!");
    }

    /**
     * Print the back and the front of the starter card
     *
     * @param cardId of the starter card
     */
    @Override
    public void showStarter(int cardId) {
        String[] cardFront = clientDeck.generateCardById(cardId, true);
        String[] cardBack = clientDeck.generateCardById(cardId, false);
        for (int i = 0; i < cardFront.length; i++) {
            System.out.print(DefaultValue.ANSI_YELLOW + cardFront[i] + "\t\t");
            System.out.println(cardBack[i] + DefaultValue.ANSI_RESET);
        }
        System.out.println("Choose (1) front or (2) back of the radix card:");
        new Thread(() -> chooseStarter(cardId)).start();
    }

    /**
     * Propose the choice to play the front or the back of the starter card
     *
     * @param cardId of the extracted starter card
     */
    private void chooseStarter(int cardId) {
        String input = "";
        Scanner scanner = new Scanner(System.in);
        while (input.isEmpty() || (!input.equals("1") && !input.equals("2"))) {
            input = scanner.nextLine();
            if (input.isEmpty() || (!input.equals("1") && !input.equals("2"))) {
                System.out.println(DefaultValue.ANSI_RED + "Wrong choice" + DefaultValue.ANSI_RESET);
            }
        }
        int choice = Integer.parseInt(input);
        if (choice == 1) {
            networkClient.playCard(cardId, new Position(0, 0));
        } else {
            networkClient.flipCard(cardId);
            networkClient.playCard(cardId, new Position(0, 0));
        }
    }

    /**
     * Print the list of available colors
     *
     * @param availableColors in the room
     */
    @Override
    public void showAvailableColors(List<PlayerColor> availableColors) {
        System.out.println("Select your color:\n");
        if (availableColors.contains(PlayerColor.RED))
            System.out.println(DefaultValue.ANSI_RED + "(1) RED" + DefaultValue.ANSI_RESET);
        if (availableColors.contains(PlayerColor.BLUE))
            System.out.println(DefaultValue.ANSI_BLUE + "(2) BLUE" + DefaultValue.ANSI_RESET);
        if (availableColors.contains(PlayerColor.GREEN))
            System.out.println(DefaultValue.ANSI_GREEN + "(3) GREEN" + DefaultValue.ANSI_RESET);
        if (availableColors.contains(PlayerColor.YELLOW))
            System.out.println(DefaultValue.ANSI_YELLOW + "(4) YELLOW" + DefaultValue.ANSI_RESET);
        new Thread(() -> chooseColor(availableColors)).start();
    }

    /**
     * Propose to choose from one of the available color
     *
     * @param availableColors list of currently available colors
     */
    private void chooseColor(List<PlayerColor> availableColors) {
        String input = "";
        int choice = 0;
        Scanner scanner = new Scanner(System.in);
        while (input.isEmpty()) {
            input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input);
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
            if (input.isEmpty() || (choice != 1 && choice != 2 && choice != 3 && choice != 4)) {
                System.out.println(DefaultValue.ANSI_RED + "Wrong choices" + DefaultValue.ANSI_RESET);
                input = "";
            }
        }

        switch (choice) {
            case 1 -> networkClient.chooseColor(PlayerColor.RED);
            case 2 -> networkClient.chooseColor(PlayerColor.BLUE);
            case 3 -> networkClient.chooseColor(PlayerColor.GREEN);
            case 4 -> networkClient.chooseColor(PlayerColor.YELLOW);
        }
    }

    /**
     * Update the field for the indicated player
     *
     * @param playerName         of the player to update
     * @param id                 of the newly played card
     * @param front              true if the card is played front, false otherwise
     * @param position           of the played card
     * @param availablePositions list of available positions
     */
    @Override
    public void updateField(String playerName, int id, boolean front, Position position, List<Position> availablePositions) {
        if (playerName.equals(this.playerName)) {
            field.playCard(id, front, position);
            field.setAvailablePositions(availablePositions);
        } else {
            if (!otherFields.containsKey(playerName)) {
                otherFields.put(playerName, new ClientField());
            }
            otherFields.get(playerName).playCard(id, front, position);
        }
    }

    /**
     * Print the readiness of a player
     *
     * @param playerName of the player to update
     * @param ready      new readiness status
     */
    @Override
    public void updateReady(String playerName, boolean ready) {
        if (ready) {
            System.out.println(DefaultValue.ANSI_YELLOW + "-> " + playerName + " is ready!" + DefaultValue.ANSI_RESET);
        } else {
            System.out.println(DefaultValue.ANSI_YELLOW + "-> " + playerName + " is not ready!" + DefaultValue.ANSI_RESET);
        }
    }

    /**
     * Print the common objectives
     *
     * @param objectiveIds list of objective card ids
     */
    @Override
    public void showCommonObjectives(List<Integer> objectiveIds) {
        System.out.println("Showing common objectives!");
        String[] obj1 = clientDeck.generateCardById(objectiveIds.get(0), true);
        String[] obj2 = clientDeck.generateCardById(objectiveIds.get(1), true);
        for (int i = 0; i < obj1.length; i++) {
            System.out.print(obj1[i] + "\t\t");
            System.out.println(obj2[i]);
        }
    }

    /**
     * Print the possible secret objectives
     *
     * @param possibleObjectiveIds list of objective card ids
     */
    @Override
    public void showPossibleObjectives(List<Integer> possibleObjectiveIds) {
        System.out.println("Choose (1) for the left objective card or (2) for the right objective card:");
        String[] obj1 = clientDeck.generateCardById(possibleObjectiveIds.get(0), true);
        String[] obj2 = clientDeck.generateCardById(possibleObjectiveIds.get(1), true);
        for (int i = 0; i < obj1.length; i++) {
            System.out.print(obj1[i] + "\t\t");
            System.out.println(obj2[i]);
        }
        new Thread(() -> chooseSecret(possibleObjectiveIds.get(0), possibleObjectiveIds.get(1))).start();
    }

    /**
     * Propose the choice of the secret objective
     *
     * @param obj1 id of the first objective
     * @param obj2 id og the second objective
     */
    private void chooseSecret(int obj1, int obj2) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (input.isEmpty() || (!input.equals("1") && !input.equals("2"))) {
            System.out.println(DefaultValue.ANSI_RED + "Wrong choice" + DefaultValue.ANSI_RESET);
            input = scanner.nextLine();
        }
        int choice = Integer.parseInt(input);
        if (choice == 1) {
            networkClient.chooseSecretObjective(obj1);
        } else {
            networkClient.chooseSecretObjective(obj2);
        }
    }

    /**
     * Print the player hand
     *
     * @param handIds list of card ids in the hand
     */
    @Override
    public void showHand(List<Integer> handIds) {
        this.handIds = handIds;
        String[] card1Front = clientDeck.generateCardById(handIds.get(0), true);
        String[] card2Front = clientDeck.generateCardById(handIds.get(1), true);
        String[] card3Front = clientDeck.generateCardById(handIds.get(2), true);

        String[] card1Back = clientDeck.generateCardById(handIds.get(0), false);
        String[] card2Back = clientDeck.generateCardById(handIds.get(1), false);
        String[] card3Back = clientDeck.generateCardById(handIds.get(2), false);
        System.out.println("\n\n-> Your hand:");
        //Carte front
        for (int i = 0; i < card1Front.length; i++) {
            System.out.print(card1Front[i] + "\t\t");
            System.out.print(card2Front[i] + "\t\t");
            System.out.print(card3Front[i]);
            if (i == 3) System.out.print("\t\t Front");
            System.out.print("\n");
        }
        //Carte back
        for (int i = 0; i < card1Front.length; i++) {
            System.out.print(card1Back[i] + "\t\t");
            System.out.print(card2Back[i] + "\t\t");
            System.out.print(card3Back[i]);
            if (i == 3) System.out.print("\t\t Back");
            System.out.print("\n");
        }
        System.out.println("\t\t   (1) \t\t\t\t\t\t\t   (2) \t\t\t\t\t\t\t   (3)");
        new Thread(this::chooseCardToPlay).start();
    }

    /**
     * Propose the choice of the card to play
     */
    private void chooseCardToPlay() {
        System.out.println(DefaultValue.ANSI_YELLOW + "Choose which card you want to play: " + DefaultValue.ANSI_RESET);
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (input.isEmpty() || (!input.equals("1") && !input.equals("2") && !input.equals("3"))) {
            System.out.println(DefaultValue.ANSI_RED + "Wrong choice" + DefaultValue.ANSI_RESET);
            input = scanner.nextLine();
        }
        int cardSelected = Integer.parseInt(input) - 1;

        System.out.println("Choose (1) Front or (2) back: ");
        input = scanner.nextLine();
        while (input.isEmpty() || (!input.equals("1") && !input.equals("2"))) {
            System.out.println(DefaultValue.ANSI_RED + "Wrong choice" + DefaultValue.ANSI_RESET);
            input = scanner.nextLine();
        }
        int front = Integer.parseInt(input);

        int index = -1;
        while (index == -1) {
            System.out.println("Choose the position where the card will be played: ");
            input = "";
            while (input.isEmpty()) {
                input = scanner.nextLine();
                try {
                    index = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    index = -1;
                    System.out.println(DefaultValue.ANSI_RED + "Wrong choice" + DefaultValue.ANSI_RESET);
                }
            }

            if (index < 0 || index >= field.getAvailablePositions().size()) {
                index = -1;
                System.out.println(DefaultValue.ANSI_RED + "Position not available" + DefaultValue.ANSI_RESET);
            }
        }

        if (front == 2)
            networkClient.flipCard(handIds.get(cardSelected));
        networkClient.playCard(handIds.get(cardSelected), field.getAvailablePositions().get(index));
    }

    /**
     * Print the center table
     *
     * @param drawableCardsIds map of drawable cards
     */
    @Override
    public void showTable(Map<Integer, Integer> drawableCardsIds) {
        String[] resourceDeck = clientDeck.generateCardById(drawableCardsIds.get(1), false);
        String[] resourceLeft = clientDeck.generateCardById(drawableCardsIds.get(2), true);
        String[] resourceRight = clientDeck.generateCardById(drawableCardsIds.get(3), true);

        String[] goldenDeck = clientDeck.generateCardById(drawableCardsIds.get(4), false);
        String[] goldenLeft = clientDeck.generateCardById(drawableCardsIds.get(5), true);
        String[] goldenRight = clientDeck.generateCardById(drawableCardsIds.get(6), true);

        System.out.println("\n\n-> Drawable Cards: ");
        //Carte Resource
        System.out.println("\n\t\t   (1) \t\t\t\t\t\t\t   (2) \t\t\t\t\t\t\t   (3)");
        for (int i = 0; i < resourceDeck.length; i++) {
            System.out.print(resourceDeck[i] + "\t\t");
            System.out.print(resourceLeft[i] + "\t\t");
            System.out.print(resourceRight[i]);
            if (i == 3) System.out.print("\t\t Resource Cards");
            System.out.print("\n");
        }
        //Carte back
        for (int i = 0; i < goldenDeck.length; i++) {
            System.out.print(goldenDeck[i] + "\t\t");
            System.out.print(goldenLeft[i] + "\t\t");
            System.out.print(goldenRight[i]);
            if (i == 3) System.out.print("\t\t Golden cards");
            System.out.print("\n");
        }
        System.out.println("\t\t   (4) \t\t\t\t\t\t\t   (5) \t\t\t\t\t\t\t   (6)");
        new Thread(this::chooseCardToDraw).start();
    }

    /**
     * Propose the choice of the card to draw
     */
    private void chooseCardToDraw() {
        System.out.println(DefaultValue.ANSI_YELLOW + "Choose which card you want to draw: " + DefaultValue.ANSI_RESET);
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (input.isEmpty() || (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4") && !input.equals("5") && !input.equals("6"))) {
            System.out.println(DefaultValue.ANSI_RED + "Wrong choice" + DefaultValue.ANSI_RESET);
            input = scanner.nextLine();
        }
        int cardSelected = Integer.parseInt(input);

        networkClient.drawCard(cardSelected);
    }

    /**
     * Prints the game winners
     *
     * @param winners list of winner names
     */
    @Override
    public void showWinners(List<String> winners) {
        if (winners.size() == 1) {
            System.out.println(DefaultValue.ANSI_BLUE + "-> The winner is " + winners.getFirst() + "!" + DefaultValue.ANSI_RESET);
        } else {
            System.out.println(DefaultValue.ANSI_BLUE + "-> The winners are:");
            for (String winner : winners) {
                System.out.println("-> " + winner);
            }
            System.out.println(DefaultValue.ANSI_RESET);
        }
    }

    /**
     * Go back to menu and ask modality to enter game
     */
    @Override
    public void backToMenu() {
        networkClient.leave();
        System.out.println(DefaultValue.ANSI_GREEN + "\n\n-> Going back to menu\n" + DefaultValue.ANSI_RESET);
        new Thread(this::askModalityToEnterGame).start();
    }
}