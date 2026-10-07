package com.mycompany.irr00_group_project.gamelogic.piece;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

// @author: Steve   

public class TetrisPieceTypeTest {

    @Test
    void testRandomPieceTypeIsNotNull() {
        for (int i = 0; i < 100; i++) {
            assertNotNull(TetrisPieceType.randomPieceType());
        }
    }

    @Test
    void testRandomPieceTypeIsValid() {
        for (int i = 0; i < 100; i++) {
            TetrisPieceType piece = TetrisPieceType.randomPieceType();
            assertTrue(piece instanceof TetrisPieceType); 
        }
    }

    @Test
    void testRandomPieceTypeExcludesBoundary() {
        for (int i = 0; i < 100; i++) {
            TetrisPieceType piece = TetrisPieceType.randomPieceType();
            assertNotEquals(TetrisPieceType.BOUNDARY, piece);
        }
    }
}
