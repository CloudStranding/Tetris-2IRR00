package com.mycompany.irr00_group_project.gamelogic.grid;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
 * Test class for GridManager.
 * @author Steve
 */
public class GridManagerTest {

    private GridManager manager;
    private TetrisPiece piece;
    private TetrisPiece boundary;

    /**
     * Sets up test environment before each test.
     */
    
    @BeforeEach
    public void setUp() {
        TetrisPieceFactory factory = new TetrisPieceFactory(10, 20, 20);
        boundary = factory.createTetrisPiece(TetrisPieceType.BOUNDARY);

        Block b1 = new Block(new Point(2, 1), 20, Color.BLUE);
        Block b2 = new Block(new Point(3, 1), 20, Color.BLUE);
        Block b3 = new Block(new Point(4, 1), 20, Color.BLUE);
        Block b4 = new Block(new Point(3, 2), 20, Color.BLUE);
        List<Block> blocks = new ArrayList<>(List.of(b1, b2, b3, b4));
        piece = new TetrisPiece(blocks);

        manager = new GridManager(boundary, 10, 20);
    }
    
    /**
     * Tests successful piece addition to the grid.
     */
    
    @Test
    public void testAddPiece_Success() {
        assertTrue(manager.addPiece(piece), "Piece should be added successfully");
        assertEquals(1, manager.getPieces().size(), "Manager should contain one piece");
    }

    @Test
    public void testPerformMove_ValidMove() {
        manager.addPiece(piece);
        assertTrue(manager.performMove(piece, MovementType.DOWN), "Move DOWN should be valid");
    }

    @Test
    public void testPerformMove_InvalidMove_HitsBoundary() {
        manager.addPiece(piece);

        for (int i = 0; i < 17; i++) {
            manager.performMove(piece, MovementType.DOWN);
        }

        assertFalse(manager.performMove(piece, MovementType.DOWN), 
            "Piece should not move beyond boundary");
    }

    // Other test methods remain the same with proper line breaks...
    
    private void setup(int width, int height, int[][] grid) {
        TetrisPieceFactory factory = new TetrisPieceFactory(width, height, 5);
        TetrisPiece boundary = factory.createTetrisPiece(TetrisPieceType.BOUNDARY);
        manager = new GridManager(boundary, width, height);
        manager.setPieces(fromGrid(grid));
    }

    private void assertGridEquals(int[][] expected) {
        for (int i = 0; i < expected.length; i++) {
            assertArrayEquals(expected[i], manager.getGridArray()[i], "Row " + i + " mismatch");
        }
    }

    private static List<TetrisPiece> fromGrid(int[][] grid) {
        Map<Integer, List<Block>> pieceBlocks = new HashMap<>();

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                int val = grid[y][x];
                if (val != 0) {
                    pieceBlocks
                        .computeIfAbsent(val, k -> new ArrayList<>())
                        .add(new Block(new Point(x, y), 1, Color.BLACK));
                }
            }
        }

        return pieceBlocks.values().stream()
            .map(TetrisPiece::new)
            .collect(Collectors.toList());
    }
}