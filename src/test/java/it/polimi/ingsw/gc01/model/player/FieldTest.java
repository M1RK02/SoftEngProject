package it.polimi.ingsw.gc01.model.player;

import it.polimi.ingsw.gc01.model.cards.PlayableCard;
import it.polimi.ingsw.gc01.model.corners.CornerPosition;
import it.polimi.ingsw.gc01.model.decks.GoldenDeck;
import it.polimi.ingsw.gc01.model.decks.ResourceDeck;
import it.polimi.ingsw.gc01.model.decks.StarterDeck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FieldTest {
    private static Player player;
    private static StarterDeck starterDeck;
    private static ResourceDeck resourceDeck;
    private static GoldenDeck goldenDeck;
    private static Field testField;
    private Map<CornerPosition, PlayableCard> adjacentCards;
    @BeforeEach
    void setUp() {
        player = new Player("testPlayer", PlayerColor.BLUE);
        adjacentCards = new HashMap<>();
        testField = player.getField();

        starterDeck = new StarterDeck();
        resourceDeck = new ResourceDeck();
        goldenDeck = new GoldenDeck();

        testField.put(new Position(0,0), starterDeck.get(), player);
    }

    @Test
    void getAdjacentCards() {
        testField.put(new Position(1,1), resourceDeck.pickById(2), player);
        adjacentCards = testField.getAdjacentCards(new Position(2,0));
        assertEquals(resourceDeck.pickById(2), adjacentCards.get(CornerPosition.TOP_LEFT));

        testField.put(new Position(1,-1), resourceDeck.pickById(4), player);
        adjacentCards = testField.getAdjacentCards(new Position(2,0));
        assertEquals(resourceDeck.pickById(4), adjacentCards.get(CornerPosition.BOTTOM_LEFT));

        testField.put(new Position(2,-2), resourceDeck.pickById(1), player);
        testField.put(new Position(2,2), resourceDeck.pickById(3), player);
        testField.put(new Position(3,1), goldenDeck.pickById(47), player);
        adjacentCards = testField.getAdjacentCards(new Position(2,0));
        assertEquals(goldenDeck.pickById(47), adjacentCards.get(CornerPosition.TOP_RIGHT));

        testField.put(new Position(3,-1), goldenDeck.pickById(71), player);
        adjacentCards = testField.getAdjacentCards(new Position(2,0));
        assertEquals(goldenDeck.pickById(71), adjacentCards.get(CornerPosition.BOTTOM_RIGHT));
        assertEquals(goldenDeck.pickById(47), adjacentCards.get(CornerPosition.TOP_RIGHT));
        assertEquals(resourceDeck.pickById(4), adjacentCards.get(CornerPosition.BOTTOM_LEFT));
        assertEquals(resourceDeck.pickById(2), adjacentCards.get(CornerPosition.TOP_LEFT));
    }
}