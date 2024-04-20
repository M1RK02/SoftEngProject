package it.polimi.ingsw.gc01.model.cards;

import it.polimi.ingsw.gc01.model.Item;
import it.polimi.ingsw.gc01.model.Resource;
import it.polimi.ingsw.gc01.model.decks.GoldenDeck;
import it.polimi.ingsw.gc01.model.decks.ResourceDeck;
import it.polimi.ingsw.gc01.model.decks.StarterDeck;
import it.polimi.ingsw.gc01.model.player.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GoldenCardTest {
    private static Player player;
    private static GoldenDeck goldenDeck;
    private static ResourceDeck resourceDeck;
    private static StarterDeck starterDeck;
    private static Field field;



    @BeforeEach
    void setUp() {
        player = new Player("testPlayer", PlayerColor.BLUE);
        starterDeck = new StarterDeck();
        resourceDeck = new ResourceDeck();
        goldenDeck = new GoldenDeck();
        field = player.getField();

        //FUNGI: 3, ANIMAL: 1, INSECT: 0, PLANT: 0
        player.addResource(Resource.FUNGI);
        player.addResource(Resource.FUNGI);
        player.addResource(Resource.FUNGI);
        player.addResource(Resource.ANIMAL);

    }

    @Test
    void checkRequirements() {
        assert(goldenDeck.pickById(41).checkRequirements(player));
        assert(!goldenDeck.pickById(42).checkRequirements(player));
        assert(!goldenDeck.pickById(43).checkRequirements(player));
        assert(goldenDeck.pickById(44).checkRequirements(player));
        assert(!goldenDeck.pickById(45).checkRequirements(player));
        assert(!goldenDeck.pickById(46).checkRequirements(player));
        assert(goldenDeck.pickById(47).checkRequirements(player));
        assert(!goldenDeck.pickById(50).checkRequirements(player));

        //FUNGI: 5, ANIMAL: 1, INSECT: 1, PLANT: 1
        player.addResource(Resource.FUNGI);
        player.addResource(Resource.FUNGI);
        player.addResource(Resource.PLANT);
        player.addResource(Resource.INSECT);

        assert(goldenDeck.pickById(42).checkRequirements(player));
        assert(goldenDeck.pickById(43).checkRequirements(player));
        assert(goldenDeck.pickById(50).checkRequirements(player));

    }

    @Test
    void calculatePoints() {
        //scoreCondition.equals(Item.QUILL)
        field.put(new Position(-1,-1), resourceDeck.pickById(5), player);
        field.put(new Position(-1,1), resourceDeck.pickById(15), player);
        field.put(new Position(-2, -2), goldenDeck.pickById(51), player);
        assertEquals(3, goldenDeck.pickById(51).calculatePoints(player, new Position(-2,-2)));

        //scoreCondition.equals(Item.INKWELL)
        field.put(new Position(0,-2), resourceDeck.pickById(6), player);
        field.put(new Position(0,2), goldenDeck.pickById(42), player);
        assertEquals(2, goldenDeck.pickById(42).calculatePoints(player, new Position(0,2)));

        //scoreCondition.equals(Item.MANUSCRIPT)
        field.put(new Position(-3,-3), resourceDeck.pickById(7), player);
        field.put(new Position(-4,-2), goldenDeck.pickById(62), player);
        assertEquals(2, goldenDeck.pickById(62).calculatePoints(player, new Position(-4,-2)));

        //scoreCondition.equals(ConditionType.EMPTY)
        field.put(new Position(-5,-3), goldenDeck.pickById(79), player);
        assertEquals(3, goldenDeck.pickById(79).calculatePoints(player, new Position(-5,-3)));

        //scoreCondition.equals(ConditionType.CORNER)
        field.put(new Position(0,0), starterDeck.get(), player);
        field.put(new Position(1,1), resourceDeck.pickById(2), player);
        assertEquals(2, goldenDeck.pickById(44).calculatePoints(player, new Position(2,0)));

        field.put(new Position(1,-1), resourceDeck.pickById(4), player);
        assertEquals(4, goldenDeck.pickById(44).calculatePoints(player, new Position(2,0)));

        field.put(new Position(2,-2), resourceDeck.pickById(1), player);
        field.put(new Position(2,2), resourceDeck.pickById(3), player);
        field.put(new Position(3,1), resourceDeck.pickById(5), player);
        assertEquals(6, goldenDeck.pickById(44).calculatePoints(player, new Position(2,0)));

        field.put(new Position(3,-1), resourceDeck.pickById(6), player);
        assertEquals(8, goldenDeck.pickById(44).calculatePoints(player, new Position(2,0)));


    }
}