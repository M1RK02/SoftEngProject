package it.polimi.ingsw.gc01.model.player;

import it.polimi.ingsw.gc01.model.cards.ObjectiveCard;
import it.polimi.ingsw.gc01.model.cards.PlayableCard;
import it.polimi.ingsw.gc01.model.decks.GoldenDeck;
import it.polimi.ingsw.gc01.model.decks.ObjectiveDeck;
import it.polimi.ingsw.gc01.model.decks.ResourceDeck;
import it.polimi.ingsw.gc01.model.decks.StarterDeck;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    static private Player player;
    static private GoldenDeck goldenDeck;
    static private StarterDeck starterDeck;
    static private ResourceDeck resourceDeck;

    @BeforeAll
    static void setUp() {
        goldenDeck = new GoldenDeck();
        starterDeck = new StarterDeck();
        resourceDeck = new ResourceDeck();
        player = new Player("testPlayer", PlayerColor.BLUE);
        player.addCard(starterDeck.pick());
    }

    @Test
    void playCard() {
        Random generatore = new Random();
        int p, c, n;

        player.playCard(player.getHand().get(0), new Position(0,0));
        player.getHand().add(resourceDeck.pick());
        player.getHand().add(resourceDeck.pick());
        player.getHand().add(goldenDeck.pick());

        for (int i = 0; i < 10; i++) {
            p = generatore.nextInt(player.getField().getAvailablePositions().size());
            c = generatore.nextInt(3);
            Position[] availablePositionsArray = new Position[player.getField().getAvailablePositions().size()];
            player.playCard(player.getHand().get(c), player.getField().getAvailablePositions().toArray(availablePositionsArray)[p]);
            n = generatore.nextInt(2);
            if (n == 1) {
                player.getHand().add(resourceDeck.pick());
            }else {
                player.getHand().add(goldenDeck.pick());
            }
        }
    }
}