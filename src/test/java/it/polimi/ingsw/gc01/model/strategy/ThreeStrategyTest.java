package it.polimi.ingsw.gc01.model.strategy;

import it.polimi.ingsw.gc01.model.Item;
import it.polimi.ingsw.gc01.model.player.Player;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ThreeStrategyTest {
    private static Player player;
    private static ThreeStrategy threeStrategy;

    @BeforeEach
    void setUp() {
        player = new Player("lazzaro", null);
        threeStrategy = new ThreeStrategy();

        player.addResource(Item.INKWELL);
        player.addResource(Item.INKWELL);
        player.addResource(Item.INKWELL);
        player.addResource(Item.INKWELL);
        player.addResource(Item.INKWELL);
        player.addResource(Item.INKWELL);
        player.addResource(Item.INKWELL);
        player.addResource(Item.INKWELL);
        player.addResource(Item.INKWELL);
        player.addResource(Item.INKWELL);
        player.addResource(Item.INKWELL);


        player.addResource(Item.QUILL);
        player.addResource(Item.QUILL);


        player.addResource(Item.MANUSCRIPT);
        player.addResource(Item.MANUSCRIPT);
        player.addResource(Item.MANUSCRIPT);
        player.addResource(Item.MANUSCRIPT);
    }

    @Test
    void check() {
        int threePoints;

        threePoints = threeStrategy.check(player);

        assertEquals(6, threePoints);
    }
}