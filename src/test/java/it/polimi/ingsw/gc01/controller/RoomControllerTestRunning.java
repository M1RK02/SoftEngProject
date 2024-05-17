package it.polimi.ingsw.gc01.controller;

import it.polimi.ingsw.gc01.controller.exceptions.GoldenRequirementsException;
import it.polimi.ingsw.gc01.controller.exceptions.NotPlayerTurnException;
import it.polimi.ingsw.gc01.model.cards.GoldenCard;
import it.polimi.ingsw.gc01.model.cards.PlayableCard;
import it.polimi.ingsw.gc01.model.cards.ResourceCard;
import it.polimi.ingsw.gc01.model.player.Player;
import it.polimi.ingsw.gc01.model.player.Position;
import it.polimi.ingsw.gc01.model.room.Room;
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
    void playAndDraw(){
        Room room = testRoomController.getRoom();
        while (testRoomController.getState().equals(GameState.RUNNING)){
            Player currentPlayer = testRoomController.getRoom().getCurrentPlayer();
            assertEquals(3, currentPlayer.getHand().size());
            Random random = new Random();
            int x = random.nextInt(3);
            if (currentPlayer.getHand().get(x) instanceof GoldenCard){
                if (!((GoldenCard) currentPlayer.getHand().get(x)).checkRequirements(currentPlayer)){
                    assertThrows(GoldenRequirementsException.class, () -> testRoomController.playCard(currentPlayer, currentPlayer.getHand().get(x), getRandomAvaliablePosition()));
                    if (currentPlayer.getHand().get(x).isFront()){
                        testRoomController.flipCard(currentPlayer.getHand().get(x));
                    }
                }
            }
            testRoomController.playCard(currentPlayer, currentPlayer.getHand().get(x), getRandomAvaliablePosition());
            assertEquals(2, currentPlayer.getHand().size());

            testRoomController.drawCard(currentPlayer, getRandomDrawable());
            assertEquals(3, currentPlayer.getHand().size());
            if (room.getResourceDeck().isEmpty() && room.getGoldenDeck().isEmpty() && currentPlayer.equals(room.getPlayers().get(room.getPlayers().size() - 1))){
                assertEquals(GameState.LAST_CIRCLE, testRoomController.getState());
            }
        }

        while (testRoomController.getState().equals(GameState.LAST_CIRCLE) ){
            Player currentPlayer = testRoomController.getRoom().getCurrentPlayer();
            if (currentPlayer.getHand().get(0) instanceof GoldenCard){
                if (!((GoldenCard) currentPlayer.getHand().get(0)).checkRequirements(currentPlayer)){
                    if (currentPlayer.getHand().get(0).isFront()){
                        testRoomController.flipCard(currentPlayer.getHand().get(0));
                    }
                }
            }
            assertThrows(NotPlayerTurnException.class, () -> testRoomController.playCard(testRoomController.getRoom().getNextPlayer(), currentPlayer.getHand().get(0), getRandomAvaliablePosition()));
            testRoomController.playCard(currentPlayer, currentPlayer.getHand().get(0), getRandomAvaliablePosition());
            if (currentPlayer.equals(room.getPlayers().get(room.getPlayers().size() - 1))){
                assertEquals(GameState.ENDED, testRoomController.getState());
            }
            room.setCurrentPlayer(room.getNextPlayer());
        }
    }

    @Test
    void exceptionsTest(){

    }

    @Test
    void leave(){
        testRoomController.leave(p4);
        assertEquals(3, testRoomController.getNumOfPlayers());
        testRoomController.leave(p3);
        assertEquals(2, testRoomController.getNumOfPlayers());
        testRoomController.leave(p2);
        assertEquals(1, testRoomController.getNumOfPlayers());
        testRoomController.leave(p1);
        assertEquals(0, testRoomController.getNumOfPlayers());
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
        assertTrue(testRoomController.getRoom().getCurrentPlayer().getHand().get(0).isFront());
        testRoomController.flipCard(testRoomController.getRoom().getCurrentPlayer().getHand().get(0));
        assertFalse(testRoomController.getRoom().getCurrentPlayer().getHand().get(0).isFront());
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
        boolean deckEmpty = false;


        Map<TablePosition, ResourceCard> drawableCards = testRoomController.getRoom().getDrawableCards();
        for (TablePosition pos : TablePosition.values()){
            if (drawableCards.get(pos) == null){
                deckEmpty = true;
            }
        }

        if (deckEmpty){
            for (TablePosition pos : TablePosition.values()){
                if (drawableCards.get(pos) != null){
                    return pos;
                }
            }
        }

        for(TablePosition p : availablePosition)
        {
            if (i == item){
                if (drawableCards.get(p) == null){
                    return null;
                }
                else {
                    return p;
                }
            }
            i++;
        }
        return null;
    }
}