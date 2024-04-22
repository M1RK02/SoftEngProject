package it.polimi.ingsw.gc01.model.room;

import java.util.*;

import it.polimi.ingsw.gc01.model.cards.GoldenCard;
import it.polimi.ingsw.gc01.model.cards.PlayableCard;
import it.polimi.ingsw.gc01.model.cards.ResourceCard;
import it.polimi.ingsw.gc01.model.decks.GoldenDeck;
import it.polimi.ingsw.gc01.model.decks.ResourceDeck;
import it.polimi.ingsw.gc01.model.decks.StarterDeck;
import it.polimi.ingsw.gc01.model.player.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {
    private static Room testRoom;
    private static List<Player> testPlayers;

    @BeforeAll
     static void beforeAll() {
        testPlayers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            testPlayers.add(new Player("Player" + i, PlayerColor.values()[i]));
        }
        testRoom = new Room(testPlayers);
    }

    @Test
    void getRoomId() {
        String roomId = testRoom.getRoomId();
        assertNotNull(roomId);
        System.out.println("The generated room id is: " + roomId);
    }

    @Test
    void getNextPlayer() {
        testRoom.setCurrentPlayer(testPlayers.get(0));
        assertEquals(testRoom.getNextPlayer(), testPlayers.get(1));
    }

    @Test
    void getNextPlayerIfLast() {
        testRoom.setCurrentPlayer(testPlayers.get(3));
        assertEquals(testRoom.getNextPlayer(), testPlayers.get(0));
    }

    @Test
    void getDrawableCards(){
        ResourceCard resourceCard = testRoom.getResourceDeck().get();
        GoldenCard goldenCard = testRoom.getGoldenDeck().get();
        List<PlayableCard> visibleCards = testRoom.getVisibleCards();
        List<PlayableCard> drawableCards = testRoom.getDrawableCards();
        assertFalse(drawableCards.isEmpty());
        assertEquals(6, drawableCards.size());
        assertTrue(drawableCards.containsAll(visibleCards));
        assertTrue(drawableCards.contains(resourceCard));
        assertTrue(drawableCards.contains(goldenCard));
    }

    @Test
    void getWinner(){
        testPlayers.get(0).addPoints(1);
        testPlayers.get(1).addPoints(4);
        testPlayers.get(2).addPoints(2);
        testPlayers.get(3).addPoints(3);

        assertEquals(testPlayers.get(1), testRoom.getWinner());
    }
}