package it.polimi.ingsw.gc01.model.decks;

import it.polimi.ingsw.gc01.model.cards.GoldenCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GoldenDeckTest {
    GoldenDeck goldenDeck;
    GoldenCard goldenCard;

    @BeforeEach
    void setUp() {
        goldenDeck = new GoldenDeck();;
        assert(!goldenDeck.isEmpty());
    }


    @Test
    void pick() {
        goldenCard = goldenDeck.pick();
        assertNull(goldenDeck.pickById(goldenCard.getId()));
    }

    @Test
    void get() {
        goldenCard = goldenDeck.get();
        assertEquals(goldenCard, goldenDeck.pick());
    }
}