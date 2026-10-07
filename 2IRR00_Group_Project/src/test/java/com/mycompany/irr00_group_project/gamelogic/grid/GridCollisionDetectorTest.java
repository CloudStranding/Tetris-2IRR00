package com.mycompany.irr00_group_project.gamelogic.grid;

import java.awt.Point;
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

// @author: Steve

public class GridCollisionDetectorTest {

    private GridCollisionDetector detector;
    private TetrisPiece piece;
    private TetrisPiece boundaryPiece;

    @BeforeEach
    void setUp() {
        
        TetrisPieceFactory factory = new TetrisPieceFactory(10, 20, 20);
        boundaryPiece = factory.createTetrisPiece(TetrisPieceType.BOUNDARY);

        // create a piece which is not near the boundary
        Block b1 = new Block(new Point(2, 1), 20, javafx.scene.paint.Color.BLUE);
        Block b2 = new Block(new Point(3, 1), 20, javafx.scene.paint.Color.BLUE);
        Block b3 = new Block(new Point(4, 1), 20, javafx.scene.paint.Color.BLUE);
        Block b4 = new Block(new Point(3, 2), 20, javafx.scene.paint.Color.BLUE);
        piece = new TetrisPiece(List.of(b1, b2, b3, b4));

        detector = new GridCollisionDetector(List.of(piece), boundaryPiece);
    }

    @Test
    void testCanBeAdded_pieceDoesNotIntersect() {
        // far away with existing piecee
        Block b1 = new Block(new Point(7, 5), 20, javafx.scene.paint.Color.RED);
        Block b2 = new Block(new Point(8, 5), 20, javafx.scene.paint.Color.RED);
        Block b3 = new Block(new Point(9, 5), 20, javafx.scene.paint.Color.RED);
        Block b4 = new Block(new Point(8, 6), 20, javafx.scene.paint.Color.RED);
        TetrisPiece newPiece = new TetrisPiece(List.of(b1, b2, b3, b4));

        assertTrue(detector.canBeAdded(newPiece));
    }

    @Test
    void testCanBeAdded_pieceDoesIntersect() {
        // intersect with existing piece
        Block b1 = new Block(new Point(3, 1), 20, javafx.scene.paint.Color.RED); 
        Block b2 = new Block(new Point(4, 1), 20, javafx.scene.paint.Color.RED);
        Block b3 = new Block(new Point(5, 1), 20, javafx.scene.paint.Color.RED);
        Block b4 = new Block(new Point(4, 2), 20, javafx.scene.paint.Color.RED);
        TetrisPiece overlappingPiece = new TetrisPiece(List.of(b1, b2, b3, b4));

        assertFalse(detector.canBeAdded(overlappingPiece));
    }

    @Test
    void testIsMoveValid_noCollision() {
        //piece is at (2,1)(3,1)(4,1)(3,2)，move right won't hit any other pieces or boundary
        assertTrue(detector.isMoveValid(piece, MovementType.RIGHT));
    }

    @Test
    void testIsMoveValid_withCollision() {
        // add piece
        Block b1 = new Block(new Point(5, 1), 20, javafx.scene.paint.Color.RED);
        Block b2 = new Block(new Point(6, 1), 20, javafx.scene.paint.Color.RED);
        Block b3 = new Block(new Point(7, 1), 20, javafx.scene.paint.Color.RED);
        Block b4 = new Block(new Point(6, 2), 20, javafx.scene.paint.Color.RED);
        TetrisPiece blockingPiece = new TetrisPiece(List.of(b1, b2, b3, b4));

        // add blockingPiece to detector
        detector = new GridCollisionDetector(List.of(piece, blockingPiece), boundaryPiece);

        // move piece to right will hit blockingPiece
        assertFalse(detector.isMoveValid(piece, MovementType.RIGHT));
    }
}
