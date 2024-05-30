package it.polimi.ingsw.gc01.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldUtilTest {
    private FieldUtil field;

    @BeforeEach
    void setUp() {
        field = new FieldUtil();
    }

    @Test
    void printUsedField() {
        field.playCard(82, 0, 0, false);
        field.playCard(19, -1, 1, false);
        field.playCard(3, 1, 1, true);
        field.playCard(70, 0, 2, true);
        field.playCard(5, -2, 2, true);
        field.playCard(6, 2, 2, false);
        field.playCard(50, 1, -1, true);
        field.playCard(30, 0, -2, true);
        field.playCard(42, -1, -1, true);

        field.printUsedField();
    }
}