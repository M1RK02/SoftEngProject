package it.polimi.ingsw.gc01.model.player;

import it.polimi.ingsw.gc01.model.cards.PlayableCard;
import it.polimi.ingsw.gc01.model.corners.CornerPosition;
import it.polimi.ingsw.gc01.model.decks.*;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FieldTest {
    private static Player player;
    private static StarterDeck starterDeck;
    private static ResourceDeck resourceDeck;
    private static GoldenDeck goldenDeck;
    private static Field testField;
    private Map<CornerPosition, PlayableCard> adjacentCards;

    @BeforeEach
    void setUp() {
        player = new Player("testPlayer", null);
        adjacentCards = new HashMap<>();
        testField = player.getField();

        starterDeck = new StarterDeck();
        resourceDeck = new ResourceDeck();
        goldenDeck = new GoldenDeck();

        testField.getPositions().put(new Position(0, 0), starterDeck.get());
    }

    @Test
    void getAdjacentCards() {
        testField.getPositions().put(new Position(1, 1), resourceDeck.pickById(2));
        adjacentCards = testField.getAdjacentCards(new Position(2, 0));
        assertEquals(resourceDeck.pickById(2), adjacentCards.get(CornerPosition.TOP_LEFT));

        testField.getPositions().put(new Position(1, -1), resourceDeck.pickById(4));
        adjacentCards = testField.getAdjacentCards(new Position(2, 0));
        assertEquals(resourceDeck.pickById(4), adjacentCards.get(CornerPosition.BOTTOM_LEFT));

        testField.getPositions().put(new Position(2, -2), resourceDeck.pickById(1));
        testField.getPositions().put(new Position(2, 2), resourceDeck.pickById(3));
        testField.getPositions().put(new Position(3, 1), goldenDeck.pickById(47));
        adjacentCards = testField.getAdjacentCards(new Position(2, 0));
        assertEquals(goldenDeck.pickById(47), adjacentCards.get(CornerPosition.TOP_RIGHT));

        testField.getPositions().put(new Position(3, -1), goldenDeck.pickById(71));
        adjacentCards = testField.getAdjacentCards(new Position(2, 0));
        assertEquals(goldenDeck.pickById(71), adjacentCards.get(CornerPosition.BOTTOM_RIGHT));
        assertEquals(goldenDeck.pickById(47), adjacentCards.get(CornerPosition.TOP_RIGHT));
        assertEquals(resourceDeck.pickById(4), adjacentCards.get(CornerPosition.BOTTOM_LEFT));
        assertEquals(resourceDeck.pickById(2), adjacentCards.get(CornerPosition.TOP_LEFT));
    }
}