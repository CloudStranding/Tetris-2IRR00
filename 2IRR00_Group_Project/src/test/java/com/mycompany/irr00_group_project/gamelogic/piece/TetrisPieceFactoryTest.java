package com.mycompany.irr00_group_project.gamelogic.piece;

import org.junit.jupiter.api.Test;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TetrisPieceFactoryTest {

    private final TetrisPieceFactory factory = new TetrisPieceFactory(10, 20, 4);

    @Test
    void testCreateIPiece() {
        TetrisPiece piece = factory.createTetrisPiece(TetrisPieceType.I);
        assertNotNull(piece);
        List<Block> blocks = piece.getBlocks();
        assertEquals(4, blocks.size());
        for (int i = 0; i < 4; i++) {
            assertEquals(i, blocks.get(i).getPos().y);
        }
    }

    @Test
    void testCreateOPiece() {
        TetrisPiece piece = factory.createTetrisPiece(TetrisPieceType.O);
        assertNotNull(piece);
        List<Block> blocks = piece.getBlocks();
        assertEquals(4, blocks.size());
        List<Point> expected = List.of(new Point(4, 0), new Point(5, 0), new Point(4, 1), new Point(5, 1));
        for (Block b : blocks) {
            assertTrue(expected.contains(b.getPos()));
        }
    }

    @Test
    void testCreateBoundaryPiece() {
        TetrisPiece piece = factory.createTetrisPiece(TetrisPieceType.BOUNDARY);
        List<Block> blocks = piece.getBlocks();
        assertEquals(52, blocks.size()); 

        boolean hasLeft = blocks.stream().anyMatch(b -> b.getPos().x == -1);
        boolean hasBottom = blocks.stream().anyMatch(b -> b.getPos().y == 20);
        assertTrue(hasLeft && hasBottom);
    }

    @Test
    void testAllPieceTypesNotNull() {
        for (TetrisPieceType type : TetrisPieceType.values()) {
            assertNotNull(factory.createTetrisPiece(type), "Piece should not be null for type: " + type);
        }
    }
}
