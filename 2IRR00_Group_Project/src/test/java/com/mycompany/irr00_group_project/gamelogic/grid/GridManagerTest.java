package com.mycompany.irr00_group_project.gamelogic.grid;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mycompany.irr00_group_project.gamelogic.MovementType;
import com.mycompany.irr00_group_project.gamelogic.piece.Block;
import com.mycompany.irr00_group_project.gamelogic.piece.TetrisPiece;
import com.mycompany.irr00_group_project.gamelogic.piece.TetrisPieceFactory;
import com.mycompany.irr00_group_project.gamelogic.piece.TetrisPieceType;

import javafx.scene.paint.Color;

import static org.junit.jupiter.api.Assertions.*;

// @author: Steve

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

        int linesCleared = manager.clearFullLines();
        assertEquals(1, linesCleared, "One line should be cleared");

        // After clearing, the total number of blocks should be less than 14 (4 + 10)
        long remainingBlocks = manager.getPieces().stream()
                .flatMap(p -> p.getBlocks().stream())
                .count();

        assertTrue(remainingBlocks < 14, "Cleared full row should reduce block count");
    }

    @Test
    public void testGetGridArray() {
        manager.addPiece(piece);
        int[][] grid = manager.getGridArray();

        assertNotNull(grid, "Grid array should not be null");
        assertEquals(20, grid.length, "Grid should have correct height");
        assertEquals(10, grid[0].length, "Grid should have correct width");

        // Check if piece blocks are properly represented in grid
        for (Block block : piece.getBlocks()) {
            Point pos = block.getPos();
            assertEquals(1, grid[pos.y][pos.x], "Grid should contain piece at correct position");
        }
    }

    @Test
    public void testAddPiece_InvalidPosition() {
        // Create a piece that would be outside the grid
        Block b1 = new Block(new Point(-1, 0), 20, Color.RED);
        TetrisPiece invalidPiece = new TetrisPiece(List.of(b1));

        assertFalse(manager.addPiece(invalidPiece), "Piece outside grid should not be added");
    }

    @Test
    public void testAddPiece_Collision() {
        // Add first piece
        manager.addPiece(piece);

        // Create a piece that would collide with the first piece
        Block b1 = new Block(new Point(2, 1), 20, Color.RED);
        TetrisPiece collidingPiece = new TetrisPiece(List.of(b1));

        assertFalse(manager.addPiece(collidingPiece), "Colliding piece should not be added");
    }

    @Test
    public void testMultipleLinesClear() {
        setup(5, 3, new int[][]{
                {0, 0, 0, 0, 0},
                {1, 1, 1, 1, 2},
                {1, 1, 1, 2, 2}
        });

        assertEquals(2, manager.clearFullLines());
        assertGridEquals(new int[][]{
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}
        });
    }

    @Test
    public void testRemainderLinesClear() {
        setup(5, 3, new int[][]{
                {0, 1, 0, 0, 0},
                {1, 1, 1, 1, 2},
                {1, 0, 1, 2, 2}
        });

        assertEquals(2, manager.clearFullLines());
        assertGridEquals(new int[][]{
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}
        });
    }

    @Test
    public void testNoLineCleared() {
        int[][] grid = {
                {0, 0, 1, 0, 0},
                {1, 1, 0, 0, 1},
                {0, 0, 1, 1, 0}
        };
        setup(5, 3, grid);

        assertEquals(0, manager.clearFullLines());
        assertGridEquals(grid);
    }

    @Test
    public void testClearBottomRowOnly() {
        setup(5, 3, new int[][]{
                {0, 0, 0, 0, 0},
                {0, 1, 1, 2, 0},
                {1, 1, 1, 1, 1}
        });

        assertEquals(1, manager.clearFullLines());
        assertGridEquals(new int[][]{
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0}
        });
    }

    @Test
    public void testTopRowClearsAndDrops() {
        setup(5, 3, new int[][]{
                {1, 1, 1, 1, 1},
                {0, 1, 0, 1, 0},
                {0, 0, 0, 0, 0}
        });

        assertEquals(1, manager.clearFullLines());
        assertGridEquals(new int[][]{
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 1, 0, 1, 0}
        });
    }

    @Test
    public void testMultipleGapsAndPartialClears() {
        setup(5, 4, new int[][]{
                {1, 1, 1, 1, 1},
                {1, 0, 1, 0, 1},
                {2, 2, 2, 2, 2},
                {0, 1, 0, 1, 1}
        });

        assertEquals(3, manager.clearFullLines());
        assertGridEquals(new int[][]{
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1}
        });
    }

    @Test
    public void testScatterClears() {
        setup(5, 5, new int[][]{
                {1, 1, 0, 1, 0},
                {1, 0, 1, 1, 1},
                {1, 1, 1, 0, 1},
                {1, 1, 1, 1, 1},
                {0, 0, 1, 0, 0}
        });

        assertEquals(1, manager.clearFullLines());
        assertGridEquals(new int[][]{
                {0, 0, 0, 0, 0},
                {1, 1, 0, 1, 0},
                {1, 0, 1, 1, 1},
                {1, 1, 1, 0, 1},
                {0, 0, 1, 0, 0}
        });
    }

    @Test
    public void testFloatingPieceFallsFullyAfterClear() {
        setup(5, 4, new int[][]{
                {0, 0, 0, 0, 0},
                {3, 3, 3, 0, 0},
                {1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0}
        });

        assertEquals(1, manager.clearFullLines());
        assertGridEquals(new int[][]{
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {1, 1, 1, 0, 0}
        });
    }

    @Test
    public void testTwoFloatingPiecesFallIndependently() {
        setup(5, 5, new int[][]{
                {1, 0, 0, 0, 2},
                {1, 0, 0, 0, 2},
                {1, 0, 0, 0, 2},
                {0, 0, 0, 0, 0},
                {3, 3, 3, 3, 3}
        });

        assertEquals(1, manager.clearFullLines());
        assertGridEquals(new int[][]{
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {1, 0, 0, 0, 1},
                {1, 0, 0, 0, 1},
                {1, 0, 0, 0, 1}
        });
    }

    @Test
    public void testAllLinesClearedFully() {
        setup(5, 3, new int[][]{
                {4, 4, 4, 4, 4},
                {3, 3, 3, 3, 3},
                {2, 2, 2, 2, 2}
        });

        assertEquals(3, manager.clearFullLines());
        assertGridEquals(new int[][]{
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}
        });
    }

    @Test
    public void testFloatingBlockStackFallsCorrectly() {
        setup(5, 5, new int[][]{
                {0, 0, 0, 0, 0},
                {0, 4, 0, 0, 0},
                {0, 4, 0, 0, 0},
                {0, 4, 0, 0, 0},
                {1, 1, 1, 1, 1}
        });

        assertEquals(1, manager.clearFullLines());
        assertGridEquals(new int[][]{
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0},
                {0, 1, 0, 0, 0},
                {0, 1, 0, 0, 0}
        });
    }

    @Test
    public void testTopRowFloatingAfterMiddleClearFalls() {
        setup(5, 4, new int[][]{
                {0, 5, 0, 0, 5},
                {0, 5, 0, 0, 5},
                {6, 6, 6, 6, 6},
                {0, 0, 0, 0, 0}
        });

        assertEquals(1, manager.clearFullLines());
        assertGridEquals(new int[][]{
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 1, 0, 0, 1},
                {0, 1, 0, 0, 1}
        });
    }


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
