package com.mycompany.irr00_group_project.gamelogic.piece;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for TetrisPieceFactory.
 * @author Steve
 */
public class TetrisPieceFactoryTest {

    private TetrisPieceFactory factory;
    private static final int GRID_WIDTH = 10;
    private static final int GRID_HEIGHT = 20;
    private static final int BLOCK_SIZE = 30;

    @BeforeEach
    public void setUp() {
        factory = new TetrisPieceFactory(GRID_WIDTH, GRID_HEIGHT, BLOCK_SIZE);
    }

    // Test methods remain the same...
    
    @Test
    public void testPiecePositioning() {
        TetrisPiece piece = factory.createTetrisPiece(TetrisPieceType.T);
        
        piece.getBlocks().forEach(block -> {
            assertTrue(block.getPos().x >= -2 && block.getPos().x < GRID_WIDTH + 2, 
                "Block x position should be reasonable");
            assertTrue(block.getPos().y >= -5 && block.getPos().y < GRID_HEIGHT, 
                "Block y position should be reasonable");
        });
    }
}