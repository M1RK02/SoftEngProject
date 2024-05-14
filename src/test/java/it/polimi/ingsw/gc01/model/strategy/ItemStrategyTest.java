package it.polimi.ingsw.gc01.model.strategy;

import it.polimi.ingsw.gc01.model.Item;
import it.polimi.ingsw.gc01.model.player.Player;
import it.polimi.ingsw.gc01.model.player.PlayerColor;
import it.polimi.ingsw.gc01.model.player.PlayerResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemStrategyTest {
    private static Player player;
    private static ItemStrategy quillStrategy, inkwellStrategy, manuscriptStrategy;
    @BeforeEach
    void setUp() {

        player = new Player("lazzaro", null);
        quillStrategy = new ItemStrategy(Item.QUILL);
        inkwellStrategy = new ItemStrategy(Item.INKWELL);
        manuscriptStrategy = new ItemStrategy(Item.MANUSCRIPT);

        //4
        player.addResource(Item.QUILL);
        player.addResource(Item.QUILL);
        player.addResource(Item.QUILL);
        player.addResource(Item.QUILL);

        //3
        player.addResource(Item.INKWELL);
        player.addResource(Item.INKWELL);
        player.addResource(Item.INKWELL);

        //1
        player.addResource(Item.MANUSCRIPT);

    }

    @Test
    void check() {
        int quill, inkwell, manuscript;

        quill = quillStrategy.check(player);
        inkwell = inkwellStrategy.check(player);
        manuscript = manuscriptStrategy.check(player);

        assertEquals(4, quill);
        assertEquals(2, inkwell);
        assertEquals(0, manuscript);

    }
}