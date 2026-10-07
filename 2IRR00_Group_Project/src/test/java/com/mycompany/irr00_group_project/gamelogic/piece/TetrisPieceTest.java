package com.mycompany.irr00_group_project.gamelogic.piece;

import com.mycompany.irr00_group_project.gamelogic.MovementType;
import org.junit.jupiter.api.Test;
import java.awt.*;
import java.util.List;
import javafx.scene.paint.Color;

import static org.junit.jupiter.api.Assertions.*;

public class TetrisPieceTest {

    @Test
    public void testMoveLeft() {
        Block b1 = new Block(new Point(2, 3), 20, Color.RED);
        TetrisPiece piece = new TetrisPiece(List.of(b1));
        piece.performMove(MovementType.LEFT);
        assertEquals(new Point(1, 3), b1.getPos());
    }

    @Test
    public void testMoveRight() {
        Block b1 = new Block(new Point(2, 3), 20, Color.RED);
        TetrisPiece piece = new TetrisPiece(List.of(b1));
        piece.performMove(MovementType.RIGHT);
        assertEquals(new Point(3, 3), b1.getPos());
    }

    @Test
    public void testMoveDown() {
        Block b1 = new Block(new Point(2, 3), 20, Color.RED);
        TetrisPiece piece = new TetrisPiece(List.of(b1));
        piece.performMove(MovementType.DOWN);
        assertEquals(new Point(2, 4), b1.getPos());
    }

    @Test
    public void testCloneIndependence() {
        Block b1 = new Block(new Point(1, 1), 20, Color.RED);
        TetrisPiece piece = new TetrisPiece(List.of(b1));
        TetrisPiece copy = piece.clone();

        assertNotSame(piece, copy);
        assertNotSame(piece.getBlocks(), copy.getBlocks());
        assertEquals(piece.getBlocks().get(0).getPos(), copy.getBlocks().get(0).getPos());
        assertNotSame(piece.getBlocks().get(0), copy.getBlocks().get(0));
    }

    @Test
    public void testIntersectionDetectionOfOneBlockPiece() {
        Block b1 = new Block(new Point(2, 2), 20, Color.BLUE);
        Block b2 = new Block(new Point(2, 2), 20, Color.GREEN);
        TetrisPiece p1 = new TetrisPiece(List.of(b1));
        TetrisPiece p2 = new TetrisPiece(List.of(b2));
        assertTrue(p1.intersects(p2));
    }
    
    @Test
    public void testIntersectionDetectionOfThreeBlocksPiece(){
        Block b1 = new Block(new Point(2, 2), 20, Color.BLUE);
        Block b2 = new Block(new Point(2, 3), 20, Color.BLUE);
        Block b3 = new Block(new Point(2, 3), 20, Color.GREEN);
        Block b4 = new Block(new Point(2, 4), 20, Color.GREEN);
        TetrisPiece p1 = new TetrisPiece(List.of(b1, b2));
        TetrisPiece p2 = new TetrisPiece(List.of(b3, b4));
        assertTrue(p1.intersects(p2));
    }

    @Test
    public void testIntersectionFalse() {
        Block b1 = new Block(new Point(2, 2), 20, Color.BLUE);
        Block b2 = new Block(new Point(3, 3), 20, Color.GREEN);
        TetrisPiece p1 = new TetrisPiece(List.of(b1));
        TetrisPiece p2 = new TetrisPiece(List.of(b2));
        assertFalse(p1.intersects(p2));
    }
}
