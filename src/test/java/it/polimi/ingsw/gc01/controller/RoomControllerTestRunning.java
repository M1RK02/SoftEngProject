package it.polimi.ingsw.gc01.controller;

import it.polimi.ingsw.gc01.model.player.Player;
import it.polimi.ingsw.gc01.model.player.Position;
import it.polimi.ingsw.gc01.model.room.TablePosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class RoomControllerTestRunning {
    public static RoomController testRoomController;
    Player p1 = new Player("Player1", null);
    Player p2 = new Player("Player2", null);
    Player p3 = new Player("Player3", null);
    Player p4 = new Player("Player4", null);
    Player p5 = new Player("Player5", null);
    Player p6 = new Player("Player6", null);
    Player p7 = new Player("Player7", null);
    Player p8 = new Player("Player8", null);
    Player p9 = new Player("Player9", null);
    Player p10 = new Player("Player10", null);
    Player p11 = new Player("Player11", null);
    Player p12 = new Player("Player12", null);
    Player p13 = new Player("Player13", null);
    Player p14 = new Player("Player14", null);
    Player p15 = new Player("Player15", null);
    Player p16 = new Player("Player16", null);
    Player p17 = new Player("Player17", null);
    Player p18 = new Player("Player18", null);
    Player p19 = new Player("Player19", null);

    @BeforeEach
    void setUp() {
        testRoomController = new RoomController();

        testRoomController.addPlayer(p1.getName());
        testRoomController.addPlayer(p2.getName());
        testRoomController.addPlayer(p3.getName());
        testRoomController.addPlayer(p4.getName());

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
            //Choose random objective card
            testRoomController.chooseSecretObjective(player, player.getPossibleObjectives().get(x));
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
    void playAndDraw(){
        while (testRoomController.getState().equals(GameState.RUNNING)){
            Player currentPlayer = testRoomController.getRoom().getCurrentPlayer();
            assertEquals(3, currentPlayer.getHand().size());
            Random random = new Random();
            int x = random.nextInt(3);
            currentPlayer.playCard(currentPlayer.getHand().get(x), getRandomAvaliablePosition());
            assertEquals(2, currentPlayer.getHand().size());
            testRoomController.drawCard(currentPlayer, getRandomDrawable());
            assertEquals(3, currentPlayer.getHand().size());
            testRoomController.changeStateIfTwenty();
        }
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
        //TODO
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
        testRoomController.getRoom().getCurrentPlayer().addPoints(2);
        testRoomController.nextPlayer();
        testRoomController.getRoom().getCurrentPlayer().addPoints(9);
        testRoomController.nextPlayer();
        testRoomController.getRoom().getCurrentPlayer().addPoints(23);
        testRoomController.nextPlayer();
        testRoomController.getRoom().getCurrentPlayer().addPoints(23);
        testRoomController.changeStateIfTwenty();
        testRoomController.endGame();
        assertEquals(p3.getName(), testRoomController.getRoom().getWinners().get(0).getName());
        assertEquals(p4.getName(), testRoomController.getRoom().getWinners().get(1).getName());
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

    private TablePosition getRandomDrawable(){
        Set<TablePosition> availablePosition = Arrays.stream(TablePosition.values()).collect(Collectors.toSet());
        int size = availablePosition.size();
        int item = new Random().nextInt(size);
        int i = 0;
        for(TablePosition p : availablePosition)
        {
            if (i == item)
                return p;
            i++;
        }
        return null;
    }
}