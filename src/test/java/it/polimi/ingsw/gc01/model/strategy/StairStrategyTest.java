package it.polimi.ingsw.gc01.model.strategy;

import it.polimi.ingsw.gc01.model.cards.CardColor;
import it.polimi.ingsw.gc01.model.cards.ResourceCard;
import it.polimi.ingsw.gc01.model.cards.StarterCard;
import it.polimi.ingsw.gc01.model.decks.GoldenDeck;
import it.polimi.ingsw.gc01.model.decks.ObjectiveDeck;
import it.polimi.ingsw.gc01.model.decks.ResourceDeck;
import it.polimi.ingsw.gc01.model.decks.StarterDeck;
import it.polimi.ingsw.gc01.model.player.Field;
import it.polimi.ingsw.gc01.model.player.Player;
import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.model.player.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StairStrategyTest {
    private static Player player;
    private static StarterDeck starterDeck;
    private static ResourceDeck resourceDeck;
    private static GoldenDeck goldenDeck;
    private static ObjectiveDeck objectiveDeck;
    private static StairStrategy stairStrategyTest;


    @BeforeEach
    void setUp() {
        player = new Player("Prend", PlayerColor.BLUE);
        Field field = player.getField();
        starterDeck = new StarterDeck();
        resourceDeck = new ResourceDeck();
        stairStrategyTest = new StairStrategy(CardColor.GREEN);
        field.put(new Position(0,0), starterDeck.pickById(81));
        field.put(new Position(1,-1), resourceDeck.pickById(11));
        field.put(new Position(2,-2), resourceDeck.pickById(12));
        field.put(new Position(3,-3), resourceDeck.pickById(13));
        field.put(new Position(4,-4), resourceDeck.pickById(14));
        field.put(new Position(5,-5), resourceDeck.pickById(15));
        field.put(new Position(6,-6), resourceDeck.pickById(16));
    }

    @Test
    void check(){
        int points;
        points = stairStrategyTest.check(player);
        System.out.println(points);
    }
}