package com.mycompany.irr00_group_project.gamelogic.piece;

import java.awt.Point;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import javafx.scene.paint.Color;

// @author: Steve

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
        // Check color consistency
        Color firstColor = blocks.get(0).getColor();
        for (Block block : blocks) {
            assertEquals(firstColor, block.getColor(), "All blocks should have the same color");
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
        // Check color consistency
        Color firstColor = blocks.get(0).getColor();
        for (Block block : blocks) {
            assertEquals(firstColor, block.getColor(), "All blocks should have the same color");
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
        
        // Check color consistency
        Color firstColor = blocks.get(0).getColor();
        for (Block block : blocks) {
            assertEquals(firstColor, block.getColor(), "All boundary blocks should have the same color");
        }
    }

    @Test
    void testAllPieceTypesNotNull() {
        for (TetrisPieceType type : TetrisPieceType.values()) {
            assertNotNull(factory.createTetrisPiece(type), "Piece should not be null for type: " + type);
        }
    }

    @Test
    void testRandomPieceGeneration() {
        Set<TetrisPieceType> generatedTypes = new HashSet<>();

        for (int i = 0; i < 100; i++) {
            TetrisPieceType type = TetrisPieceType.randomPieceType();
            TetrisPiece piece = factory.createTetrisPiece(type);

            assertNotNull(piece, "Random piece should not be null");
            List<Block> blocks = piece.getBlocks();
            assertFalse(blocks.isEmpty(), "Random piece should have blocks");

            generatedTypes.add(type);

            for (Block block : blocks) {
                Point pos = block.getPos();
                assertTrue(pos.x >= 0 && pos.x < 10, "Block should be within grid width");
                assertTrue(pos.y >= 0, "Block should be above or at grid top");
            }

            Color firstColor = blocks.get(0).getColor();
            for (Block block : blocks) {
                assertEquals(firstColor, block.getColor(), "All blocks should have the same color");
            }
        }

        // Verify that we generated multiple distinct piece types
        assertTrue(generatedTypes.size() >= 5, "Should generate at least 5 distinct piece types");
    }

    @Test
    void testPieceColors() {
        // Test that each piece type has a consistent color
        for (TetrisPieceType type : TetrisPieceType.values()) {
            if (type == TetrisPieceType.BOUNDARY) continue; // Skip boundary
            
            TetrisPiece piece = factory.createTetrisPiece(type);
            List<Block> blocks = piece.getBlocks();
            Color firstColor = blocks.get(0).getColor();
            
            for (Block block : blocks) {
                assertEquals(firstColor, block.getColor(), 
                    "All blocks in " + type + " piece should have the same color");
            }
        }
    }
}
