package com.mycompany.irr00_group_project.gamelogic;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mycompany.irr00_group_project.gamelogic.piece.TetrisPiece;

/**
 * Test class for Game logic.
 * @author Steve
 */
public class GameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game(10, 20, 30); 
    }

    @Test
    void testGameInitialization() {
        assertNotNull(game, "Game instance should not be null");
        assertFalse(game.isGameStarted(), "Game should not be started initially");
        assertFalse(game.isGameOver(), "Game should not be over initially");
    }

    @Test
    void testGetNextPiece() {
        TetrisPiece piece = game.getNextPiece();
        assertNotNull(piece, "getNextPiece should return a non-null TetrisPiece");
    }

    @Test
    void testStartAddsPieceToGrid() {
        game.start();
        List<? extends Drawable> grid = game.getGrid();
        assertFalse(grid.isEmpty(), "Grid should not be empty after start()");
        assertTrue(game.isGameStarted(), "Game should be started after start()");
    }

    @Test
    void testRestart() {
        game.start();
        assertTrue(game.isGameStarted(), "Game should be started");
        
        game.restart();
        assertTrue(game.isGameStarted(), "Game should be started after restart");
        assertFalse(game.isGameOver(), "Game should not be over after restart");
        
        List<? extends Drawable> grid = game.getGrid();
        assertFalse(grid.isEmpty(), "Grid should have new piece after restart");
    }

    @Test
    void testStop() {
        game.start();
        assertTrue(game.isGameStarted(), "Game should be started");
        
        game.stop();
        assertFalse(game.isGameStarted(), "Game should not be started after stop");
    }

    @Test
    void testPause() {
        game.start();
        assertTrue(game.isGameStarted(), "Game should be started");
        
        game.pause();
        assertTrue(game.isGameStarted(), "Game should still be started after pause");
    }

    @Test
    void testGetCurrentGrid() {
        game.start();
        int[][] grid = game.getCurrentGrid();
        assertNotNull(grid, "getCurrentGrid should return non-null array");
        assertEquals(20, grid.length, "Grid should have correct height");
        assertEquals(10, grid[0].length, "Grid should have correct width");
    }

    @Test
    void testGetAndResetLinesCleared() {
        game.start();
        int linesCleared = game.getAndResetLinesCleared();
        assertEquals(0, linesCleared, "No lines should be cleared initially");
    }
}