package com.mycompany.irr00_group_project.gamelogic.grid;

import com.mycompany.irr00_group_project.gamelogic.MovementType;
import com.mycompany.irr00_group_project.gamelogic.piece.Block;
import com.mycompany.irr00_group_project.gamelogic.piece.TetrisPiece;
import com.mycompany.irr00_group_project.gamelogic.piece.TetrisPieceFactory;
import com.mycompany.irr00_group_project.gamelogic.piece.TetrisPieceType;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class GridManagerTest {

    private GridManager manager;
    private TetrisPiece piece;
    private TetrisPiece boundary;

    @BeforeEach
    public void setUp() {
        TetrisPieceFactory factory = new TetrisPieceFactory(10, 20, 20);
        boundary = factory.createTetrisPiece(TetrisPieceType.BOUNDARY);

        // Create a regular Tetris piece (T-shape)
        Block b1 = new Block(new Point(2, 1), 20, Color.BLUE);
        Block b2 = new Block(new Point(3, 1), 20, Color.BLUE);
        Block b3 = new Block(new Point(4, 1), 20, Color.BLUE);
        Block b4 = new Block(new Point(3, 2), 20, Color.BLUE);
        List<Block> blocks = new ArrayList<>(List.of(b1, b2, b3, b4));
        piece = new TetrisPiece(blocks);

        manager = new GridManager(boundary, 10, 20);
    }

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

        // Move down repeatedly until hitting the boundary
        for (int i = 0; i < 17; i++) {
            manager.performMove(piece, MovementType.DOWN);
        }

        // The next move should be invalid due to boundary collision
        assertFalse(manager.performMove(piece, MovementType.DOWN), "Piece should not move beyond boundary");
    }

    @Test
    public void testClearFullLines_RemovesFilledRow() {
        manager.addPiece(piece);

        // Add a full row at y = 5
        List<Block> fullRowBlocks = new ArrayList<>();
        for (int x = 0; x < 10; x++) {
            fullRowBlocks.add(new Block(new Point(x, 5), 20, Color.RED));
        }
        TetrisPiece fullRow = new TetrisPiece(fullRowBlocks);
        manager.addPiece(fullRow);

        manager.clearFullLines();

        // After clearing, the total number of blocks should be less than 14 (4 + 10)
        long remainingBlocks = manager.getPieces().stream()
            .flatMap(p -> p.getBlocks().stream())
            .count();

        assertTrue(remainingBlocks < 14, "Cleared full row should reduce block count");
    }

    @Test
    public void testMultipleLinesClear() {
        int width = 5;
        int height = 3;

        TetrisPieceFactory factory = new TetrisPieceFactory(width,height, 5);
        TetrisPiece boundary = factory.createTetrisPiece(TetrisPieceType.BOUNDARY);

        this.manager = new GridManager(boundary,width,height);

        int[][] grid = {
            {0,0,0,0,0},
            {1,1,1,1,2},
            {1,1,1,2,2}
        };
        this.manager.setPieces(fromGrid(grid));

        int[][] afterGrid = {
                {0,0,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0}
        };

        int amount = this.manager.clearFullLines();

        for (int i = 0; i < grid.length; i++) {
            assertArrayEquals(afterGrid[i], this.manager.getGridArray()[i]);
        }
        assertEquals(2, amount);
    }

    @Test
    public void testRemainderLinesClear() {
        int width = 5;
        int height = 3;

        TetrisPieceFactory factory = new TetrisPieceFactory(width,height, 5);
        TetrisPiece boundary = factory.createTetrisPiece(TetrisPieceType.BOUNDARY);

        this.manager = new GridManager(boundary,width,height);

        int[][] grid = {
                {0,1,0,0,0},
                {1,1,1,1,2},
                {1,0,1,2,2}
        };
        this.manager.setPieces(fromGrid(grid));

        int[][] afterGrid = {
                {0,0,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0}
        };

        for (int i = 0; i < grid.length; i++) {
            assertArrayEquals(afterGrid[i], this.manager.getGridArray()[i]);
        }
        assertEquals(2, this.manager.clearFullLines());

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
