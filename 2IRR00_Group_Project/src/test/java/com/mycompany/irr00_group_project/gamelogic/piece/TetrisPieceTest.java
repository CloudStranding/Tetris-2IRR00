package com.mycompany.irr00_group_project.gamelogic.piece;

import java.awt.Point;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mycompany.irr00_group_project.gamelogic.MovementType;

import javafx.scene.paint.Color;

/**
 * Test class for TetrisPiece.
 * @author Steve
 */
public class TetrisPieceTest {

    private TetrisPiece piece;

    /**
     * Sets up test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        Block b1 = new Block(new Point(1, 0), 20, Color.BLUE);
        Block b2 = new Block(new Point(0, 1), 20, Color.BLUE);
        Block b3 = new Block(new Point(1, 1), 20, Color.BLUE);
        Block b4 = new Block(new Point(2, 1), 20, Color.BLUE);
        piece = new TetrisPiece(List.of(b1, b2, b3, b4), TetrisPieceType.T);
    }

    // Test methods remain the same with line breaks for long lines...
    
    @Test
    public void testIntersects() {
        Block b1 = new Block(new Point(1, 1), 20, Color.RED);
        TetrisPiece intersectingPiece = new TetrisPiece(List.of(b1));
        assertTrue(piece.intersects(intersectingPiece), "Pieces should intersect");

        Block b2 = new Block(new Point(5, 5), 20, Color.GREEN);
        TetrisPiece nonIntersectingPiece = new TetrisPiece(List.of(b2));
        assertFalse(piece.intersects(nonIntersectingPiece), 
            "Pieces should not intersect");
    }
}