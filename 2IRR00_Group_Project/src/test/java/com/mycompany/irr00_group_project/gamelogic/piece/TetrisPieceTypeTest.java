package com.mycompany.irr00_group_project.gamelogic.piece;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
