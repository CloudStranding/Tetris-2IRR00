package com.mycompany.irr00_group_project.gamelogic;

import com.mycompany.irr00_group_project.gamelogic.grid.GridManager;
import com.mycompany.irr00_group_project.gamelogic.piece.TetrisPiece;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game(10, 20, 30); 
    }

    @Test
    void testGameInitialization() {
        assertNotNull(game, "Game instance should not be null");
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
    }

}
