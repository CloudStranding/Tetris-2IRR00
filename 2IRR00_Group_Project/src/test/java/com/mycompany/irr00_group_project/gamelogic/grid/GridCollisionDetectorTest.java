package com.mycompany.irr00_group_project.gamelogic.grid;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mycompany.irr00_group_project.gamelogic.MovementType;
import com.mycompany.irr00_group_project.gamelogic.piece.Block;
import com.mycompany.irr00_group_project.gamelogic.piece.TetrisPiece;
import com.mycompany.irr00_group_project.gamelogic.piece.TetrisPieceFactory;
import com.mycompany.irr00_group_project.gamelogic.piece.TetrisPieceType;

import javafx.scene.paint.Color;

/**
 * Test class for GridCollisionDetector.
 * @author Steve
 */
public class GridCollisionDetectorTest {

    private GridCollisionDetector detector;
    private List<TetrisPiece> pieces;
    private TetrisPiece boundary;
    /**
     * Sets up test environment before each test.
     */
    
    @BeforeEach
    public void setUp() {
        pieces = new ArrayList<>();
        TetrisPieceFactory factory = new TetrisPieceFactory(10, 20, 20);
        boundary = factory.createTetrisPiece(TetrisPieceType.BOUNDARY);
        detector = new GridCollisionDetector(pieces, boundary);
    }
    
    /**
     * Tests if a piece can be added to an empty grid.
     */
    @Test
    public void testCanBeAdded_EmptyGrid() {
        Block b1 = new Block(new Point(5, 5), 20, Color.BLUE);
        TetrisPiece piece = new TetrisPiece(List.of(b1));
        assertTrue(detector.canBeAdded(piece), "Piece should be addable to empty grid");
    }

    @Test
    public void testCanBeAdded_CollisionWithExistingPiece() {
        Block b1 = new Block(new Point(5, 5), 20, Color.BLUE);
        TetrisPiece existingPiece = new TetrisPiece(List.of(b1));
        pieces.add(existingPiece);

        Block b2 = new Block(new Point(5, 5), 20, Color.RED);
        TetrisPiece newPiece = new TetrisPiece(List.of(b2));

        assertFalse(detector.canBeAdded(newPiece), 
            "Piece should not be addable due to collision");
    }

    @Test
    public void testCanBeAdded_CollisionWithBoundary() {
        Block b1 = new Block(new Point(-1, 5), 20, Color.BLUE);
        TetrisPiece piece = new TetrisPiece(List.of(b1));
        assertFalse(detector.canBeAdded(piece), 
            "Piece should not be addable due to boundary collision");
    }

    @Test
    public void testIsMoveValid_ValidMove() {
        Block b1 = new Block(new Point(5, 5), 20, Color.BLUE);
        TetrisPiece piece = new TetrisPiece(List.of(b1));
        pieces.add(piece);

        assertTrue(detector.isMoveValid(piece, MovementType.DOWN), 
            "Move down should be valid");
        assertTrue(detector.isMoveValid(piece, MovementType.LEFT), 
            "Move left should be valid");
        assertTrue(detector.isMoveValid(piece, MovementType.RIGHT), 
            "Move right should be valid");
    }

    @Test
    public void testIsMoveValid_InvalidMove_HitsBoundary() {
        Block b1 = new Block(new Point(5, 19), 20, Color.BLUE);
        TetrisPiece piece = new TetrisPiece(List.of(b1));
        pieces.add(piece);

        assertFalse(detector.isMoveValid(piece, MovementType.DOWN), 
            "Move down should be invalid due to boundary");
    }

    @Test
    public void testIsMoveValid_InvalidMove_HitsOtherPiece() {
        Block b1 = new Block(new Point(5, 5), 20, Color.BLUE);
        TetrisPiece piece1 = new TetrisPiece(List.of(b1));
        pieces.add(piece1);

        Block b2 = new Block(new Point(5, 4), 20, Color.RED);
        TetrisPiece piece2 = new TetrisPiece(List.of(b2));
        pieces.add(piece2);

        assertFalse(detector.isMoveValid(piece2, MovementType.DOWN), 
            "Move down should be invalid due to collision");
    }
}