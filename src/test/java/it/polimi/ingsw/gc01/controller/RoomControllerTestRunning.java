package it.polimi.ingsw.gc01.controller;

import it.polimi.ingsw.gc01.model.player.Player;
import it.polimi.ingsw.gc01.model.player.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RoomControllerTestRunning {

    public static RoomController testRoomController;
    public static final List<Player> players = new ArrayList<>();

    @BeforeEach
    void setUp() {
        testRoomController = new RoomController();
        players.add(new Player("Player1"));
        players.add(new Player("Player2"));
        players.add(new Player("Player3"));
        players.add(new Player("Player4"));
        testRoomController.addPlayer(players.get(0).getName());
        testRoomController.addPlayer(players.get(1).getName());
        testRoomController.addPlayer(players.get(2).getName());
        testRoomController.addPlayer(players.get(3).getName());
        testRoomController.prepareGame();
        for (Player player : testRoomController.getRoom().getPlayers()) {
            Random generatore = new Random();
            int y = generatore.nextInt(2);
            //Choose random side of starterCard and plays it
            if (y == 1) {
                (player.getHand().get(0)).setFront(true);
            }
            player.playCard(player.getHand().get(0), new Position(0,0));
        }
        testRoomController.distributeCards();
        for (Player player : testRoomController.getRoom().getPlayers()) {
            Random generatore = new Random();
            int x = generatore.nextInt(2);
            //Choose random side of starterCard and plays it
            player.setSecretObjective(player.getPossibleObjectives().get(x));
        }
        testRoomController.startGame();
    }

    @Test
    void playCard() {
        //TODO
        /*
        Player currentPlayer = testRoomController.getRoom().getCurrentPlayer();
        for (int i = 0; i < 33; i++){
            if (currentPlayer.getHand().get(0) instanceof GoldenCard){
                if (((GoldenCard) currentPlayer.getHand().get(0)).checkRequirements(currentPlayer)){
                    if (!currentPlayer.getHand().get(0).isFront()){
                        testRoomController.flipCard(currentPlayer.getHand().get(0));
                    }
                    testRoomController.playCard(currentPlayer, currentPlayer.getHand().get(0), getRandomAvaliablePosition());
                    assertEquals(2, currentPlayer.getHand().size());
                    assertEquals(2 + i, currentPlayer.getField().getPositions().size());
                }
                else{
                    if (currentPlayer.getHand().get(0).isFront()){
                        testRoomController.flipCard(currentPlayer.getHand().get(0));
                    }
                    testRoomController.playCard(currentPlayer, currentPlayer.getHand().get(0), getRandomAvaliablePosition());
                    assertEquals(2, currentPlayer.getHand().size());
                    assertEquals(2 + i, currentPlayer.getField().getPositions().size());
                }
            }
            else{
                testRoomController.playCard(currentPlayer, currentPlayer.getHand().get(0), getRandomAvaliablePosition());
                assertEquals(2, currentPlayer.getHand().size());
                assertEquals(2 + i, currentPlayer.getField().getPositions().size());
            }

            int choice = new Random().nextInt(2);
            if (choice == 1){
                testRoomController.drawCard(currentPlayer, TablePosition.RESOURCEDECK);
            }
            else{
                testRoomController.drawCard(currentPlayer, TablePosition.GOLDENDECK);
            }
        }

         */

    }

    @Test
    void nextPlayer() {
        for (Player player : testRoomController.getRoom().getPlayers()) {
            assertEquals(player.getName(), testRoomController.getRoom().getCurrentPlayer().getName());;
            testRoomController.nextPlayer();
        }
    }

    @Test
    void flipCard() {
        assertFalse(testRoomController.getRoom().getCurrentPlayer().getHand().get(0).isFront());
        testRoomController.flipCard(testRoomController.getRoom().getCurrentPlayer().getHand().get(0));
        assertTrue(testRoomController.getRoom().getCurrentPlayer().getHand().get(0).isFront());
    }

    @Test
    void drawCard() {

    }

    @Test
    void changeStateIfTwenty() {
        testRoomController.getRoom().getCurrentPlayer().addPoints(19);
        testRoomController.changeStateIfTwenty();
        assertEquals(GameState.RUNNING, testRoomController.getState());
        testRoomController.getRoom().getCurrentPlayer().addPoints(1);
        testRoomController.changeStateIfTwenty();
        assertEquals(GameState.LAST_CIRCLE, testRoomController.getState());

    }

    @Test
    void endGame() {
        testRoomController.getRoom().getCurrentPlayer().addPoints(22);
        testRoomController.nextPlayer();
        testRoomController.getRoom().getCurrentPlayer().addPoints(19);
        testRoomController.nextPlayer();
        testRoomController.getRoom().getCurrentPlayer().addPoints(23);
        testRoomController.nextPlayer();
        testRoomController.getRoom().getCurrentPlayer().addPoints(23);
        testRoomController.changeStateIfTwenty();
        testRoomController.endGame();
        assertEquals(players.get(2).getName(), testRoomController.getRoom().getWinners().get(0).getName());
        assertEquals(players.get(3).getName(), testRoomController.getRoom().getWinners().get(1).getName());
    }

    /**
     *
     * @return a random position between the set of AvailablePosition from each player field
     */
    private Position getRandomAvaliablePosition(){
        Set<Position> availablePosition = testRoomController.getRoom().getCurrentPlayer().getField().getAvailablePositions();
        int size = availablePosition.size();
        int item = new Random().nextInt(size);
        int i = 0;
        for(Position p : availablePosition)
        {
            if (i == item)
                return p;
            i++;
        }
        return null;
    }
}