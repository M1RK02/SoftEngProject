package it.polimi.ingsw.gc01.network;

import it.polimi.ingsw.gc01.model.ChatMessage;
import it.polimi.ingsw.gc01.model.cards.*;
import it.polimi.ingsw.gc01.model.player.*;
import it.polimi.ingsw.gc01.model.room.TablePosition;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class that manages the notifications to the clients
 */
public class ObserverManager {
    /**
     * Reference to the clients
     */
    private final Map<String, VirtualView> observers;

    /**
     * Construct a new ObserverManager object
     */
    public ObserverManager() {
        observers = new HashMap<>();
        new Thread(this::heartBeat).start();
    }

    /**
     * Add a player as an observer
     *
     * @param playerName of the player
     * @param client     of the player
     */
    public void addObserver(String playerName, VirtualView client) {
        synchronized (observers) {
            observers.put(playerName, client);
        }
    }

    /**
     * Remove a player as an observer
     *
     * @param playerName of the player
     */
    public void removeObserver(String playerName) {
        synchronized (observers) {
            observers.remove(playerName);
        }
    }

    /**
     * Update the room id to the indicated client
     *
     * @param playerName of the player
     * @param roomId     of the room
     */
    public void updateRoomId(String playerName, String roomId) {
        synchronized (observers) {
            VirtualView client = observers.get(playerName);
            try {
                client.updateRoomId(roomId);
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * Displays the list of players to a specific client.
     *
     * @param playerName       the name of the player to whom the list of players will be shown
     * @param playersAlreadyIn a map containing the names of players already in the game and their ready status
     */
    public void showPlayers(String playerName, Map<String, Boolean> playersAlreadyIn) {
        synchronized (observers) {
            VirtualView client = observers.get(playerName);
            try {
                client.showPlayers(playersAlreadyIn);
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * Displays the list of joined players to a specific client.
     *
     * @param playerName the name of the player to whom the list of Joined players will be shown
     */
    public void showPlayerJoined(String playerName) {
        synchronized (observers) {
            for (VirtualView client : observers.values()) {
                try {
                    client.showPlayerJoined(playerName);
                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     * Notifies all clients that a player has left the game.
     *
     * @param playerName the name of the player who has left the game
     */
    public void showPlayerLeft(String playerName) {
        synchronized (observers) {
            for (VirtualView client : observers.values()) {
                try {
                    client.showPlayerLeft(playerName);
                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     * Start the game to every client
     */
    public void startGame() {
        synchronized (observers) {
            for (VirtualView client : observers.values()) {
                try {
                    client.startGame();
                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     * Update the current player to every client
     *
     * @param playerName new current player
     */
    public void updateCurrentPlayer(String playerName) {
        synchronized (observers) {
            for (VirtualView client : observers.values()) {
                try {
                    client.updateCurrentPlayer(playerName);
                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     * Show the starter card to the indicated client
     *
     * @param playerName of the player
     * @param card       starter card to show
     */
    public void showStarter(String playerName, StarterCard card) {
        synchronized (observers) {
            VirtualView client = observers.get(playerName);
            try {
                client.showStarter(card.getId());
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * Show to every client except the playing one to wait
     *
     * @param playerName of the player
     * @param scene      the scene for which the player is waiting
     */
    public void showWaitingFor(String playerName, String scene) {
        synchronized (observers) {
            for (VirtualView client : observers.values()) {
                try {
                    client.showWaitingFor(playerName, scene);
                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     * Show the list of available colors to the indicated client
     *
     * @param playerName of the player
     * @param colors     list of available colors
     */
    public void showAvailableColor(String playerName, List<PlayerColor> colors) {
        synchronized (observers) {
            VirtualView client = observers.get(playerName);
            try {
                client.showAvailableColors(colors);
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * Update the readiness of the indicated player to every client
     *
     * @param playerName to update the status
     * @param ready      new status, true if ready, false otherwise
     */
    public void updateReady(String playerName, boolean ready) {
        synchronized (observers) {
            for (VirtualView client : observers.values()) {
                try {
                    client.updateReady(playerName, ready);
                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     * Update the field of the indicated player to every client
     *
     * @param playerName         of the player to update
     * @param id                 of the newly played card
     * @param front              true if the card is played front, false otherwise
     * @param position           of the played card
     * @param availablePositions list of available positions
     */
    public void updateField(String playerName, int id, boolean front, Position position, List<Position> availablePositions) {
        synchronized (observers) {
            for (VirtualView client : observers.values()) {
                try {
                    client.updateField(playerName, id, front, position, availablePositions);
                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     * Show the center table to the indicated client
     *
     * @param playerName    of the client
     * @param drawableCards map of drawable cards
     */
    public void showTable(String playerName, Map<TablePosition, ResourceCard> drawableCards) {
        Map<Integer, Integer> drawableIds = new HashMap<>();
        for (TablePosition position : drawableCards.keySet()) {
            switch (position) {
                case RESOURCEDECK -> drawableIds.put(1, drawableCards.get(TablePosition.RESOURCEDECK).getId());
                case RESOURCELEFT -> drawableIds.put(2, drawableCards.get(TablePosition.RESOURCELEFT).getId());
                case RESOURCERIGHT -> drawableIds.put(3, drawableCards.get(TablePosition.RESOURCERIGHT).getId());
                case GOLDENDECK -> drawableIds.put(4, drawableCards.get(TablePosition.GOLDENDECK).getId());
                case GOLDENLEFT -> drawableIds.put(5, drawableCards.get(TablePosition.GOLDENLEFT).getId());
                case GOLDENRIGHT -> drawableIds.put(6, drawableCards.get(TablePosition.GOLDENRIGHT).getId());
            }
        }
        synchronized (observers) {
            VirtualView client = observers.get(playerName);
            try {
                client.showTable(drawableIds);
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * Updates the table with drawable cards for all clients.
     *
     * @param drawableCards a map containing positions of drawable cards and their corresponding {@link ResourceCard} objects
     *                      *                     as {@code TablePosition} keys and {@code ResourceCard} values
     */
    public void updateTable(Map<TablePosition, ResourceCard> drawableCards) {
        Map<Integer, Integer> drawableIds = new HashMap<>();
        for (TablePosition position : drawableCards.keySet()) {
            switch (position) {
                case RESOURCEDECK -> drawableIds.put(1, drawableCards.get(TablePosition.RESOURCEDECK).getId());
                case RESOURCELEFT -> drawableIds.put(2, drawableCards.get(TablePosition.RESOURCELEFT).getId());
                case RESOURCERIGHT -> drawableIds.put(3, drawableCards.get(TablePosition.RESOURCERIGHT).getId());
                case GOLDENDECK -> drawableIds.put(4, drawableCards.get(TablePosition.GOLDENDECK).getId());
                case GOLDENLEFT -> drawableIds.put(5, drawableCards.get(TablePosition.GOLDENLEFT).getId());
                case GOLDENRIGHT -> drawableIds.put(6, drawableCards.get(TablePosition.GOLDENRIGHT).getId());
            }
        }
        synchronized (observers) {
            for (VirtualView client : observers.values()) {
                try {
                    client.updateTable(drawableIds);
                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     * Show the common objectives to every client
     *
     * @param commonObjectives list of common objectives
     */
    public void showCommonObjectives(List<ObjectiveCard> commonObjectives) {
        synchronized (observers) {
            List<Integer> commonObjectivesIds = commonObjectives.stream().map(ObjectiveCard::getId).collect(Collectors.toList());
            for (VirtualView client : observers.values()) {
                try {
                    client.showCommonObjectives(commonObjectivesIds);
                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     * Show the hand to the indicated client
     *
     * @param playerName of the client
     * @param hand       list of cards in the hand
     */
    public void showHand(String playerName, List<PlayableCard> hand) {
        List<Integer> handIds = hand.stream().map(PlayableCard::getId).collect(Collectors.toList());
        synchronized (observers) {
            VirtualView client = observers.get(playerName);
            try {
                client.showHand(handIds);
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * Update the hand to the indicated client
     *
     * @param playerName of the client
     * @param hand       list of cards in the hand
     */
    public void updateHand(String playerName, List<PlayableCard> hand) {
        List<Integer> handIds = hand.stream().map(PlayableCard::getId).collect(Collectors.toList());
        synchronized (observers) {
            VirtualView client = observers.get(playerName);
            try {
                client.updateHand(handIds);
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * Show the field of the indicated player to every client
     *
     * @param playerName to show the field of
     */
    public void showField(String playerName) {
        synchronized (observers) {
            for (VirtualView client : observers.values()) {
                try {
                    client.showField(playerName);
                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     * Notifies all registered observers to display the points and corresponding colors for each player.
     *
     * @param points A map where keys are player names and values are their respective points.
     * @param colors A map where keys are PlayerColor enum values and values are strings representing color names.
     */
    public void showPoints(Map<String, Integer> points, Map<PlayerColor, String> colors) {
        synchronized (observers) {
            for (VirtualView client : observers.values()) {
                try {
                    client.showPoints(points, colors);
                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     * Send every client back to menu
     */
    public void backToMenu() {
        synchronized (observers) {
            for (VirtualView client : observers.values()) {
                try {
                    client.backToMenu();
                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     * Show secret objectives to the indicated client
     *
     * @param playerName         of the client
     * @param possibleObjectives list of possible objectives
     */
    public void showSecretObjectives(String playerName, List<ObjectiveCard> possibleObjectives) {
        List<Integer> possibleObjectivesIds = possibleObjectives.stream().map(ObjectiveCard::getId).collect(Collectors.toList());
        synchronized (observers) {
            VirtualView client = observers.get(playerName);
            try {
                client.showSecretObjectives(possibleObjectivesIds);
            } catch (IOException ignored) {
            }
        }

    }

    /**
     * Show the error to the indicated client
     *
     * @param playerName of the client
     * @param error      to show
     */
    public void showError(String playerName, String error) {
        synchronized (observers) {
            VirtualView client = observers.get(playerName);
            try {
                client.showError(error);
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * Show the game error to every client
     *
     * @param error to show
     */
    public void showGameError(String error) {
        synchronized (observers) {
            for (VirtualView client : observers.values()) {
                try {
                    client.showError(error);
                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     * Show last turn to every client
     */
    public void showLastCircle() {
        synchronized (observers) {
            for (VirtualView client : observers.values()) {
                try {
                    client.showLastCircle();
                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     * Show winners to every client
     *
     * @param winners list of winners
     */
    public void showWinners(List<String> winners) {
        synchronized (observers) {
            for (VirtualView client : observers.values()) {
                try {
                    client.showWinners(winners);
                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     * Method to check for disconnections and crashes (running every tot seconds)
     */
    private void heartBeat() {
        while (true) {
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException ignored) {
            }
            String dead = "";
            synchronized (observers) {
                for (String playerName : observers.keySet()) {
                    try {
                        observers.get(playerName).isAlive();
                    } catch (IOException e) {
                        observers.remove(playerName);
                        dead = playerName;
                        break;
                    }
                }
            }
            if (!dead.isEmpty()) {
                showGameError("GAME " + dead + " has left the game");
            }
        }
    }

    /**
     * Updates the chat with a new chat message.
     *
     * @param newChatMessage chat Message to be added to the ClientChat
     */
    public void updateChat(ChatMessage newChatMessage) {
        if (newChatMessage.getRecipient().equals("ALL")) {
            synchronized (observers) {
                for (VirtualView client : observers.values()) {
                    try {
                        client.updateChat(newChatMessage);

                    } catch (IOException ignored) {
                    }
                }
            }
        } else {
            try {
                observers.get(newChatMessage.getRecipient()).updateChat(newChatMessage);
                observers.get(newChatMessage.getSender()).updateChat(newChatMessage);
            } catch (IOException ignored) {
            }
        }
    }
}