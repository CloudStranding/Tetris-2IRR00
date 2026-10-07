package com.mycompany.irr00_group_project.gamelogic;

import java.util.List;

import com.mycompany.irr00_group_project.gamelogic.grid.GridManager;
import com.mycompany.irr00_group_project.gamelogic.piece.TetrisPiece;
import com.mycompany.irr00_group_project.gamelogic.piece.TetrisPieceFactory;
import com.mycompany.irr00_group_project.gamelogic.piece.TetrisPieceType;
import com.mycompany.irr00_group_project.sound.GameSoundController;

import javafx.scene.input.KeyEvent;

/**
 * Represents a {@code GameEngine} for tetris.
 * Has a current piece that represents the piece that will be moving per update.
 */
public class Game implements GameEngine {

    private final GridManager gridManager;
    private TetrisPiece currentPiece;
    private TetrisPiece nextPiece;
    private final TetrisPieceFactory factory;
    private boolean gameOver = false;
    private boolean gameStarted = false;
    private int lastLinesCleared = 0; // Track lines cleared in last update
    private final GameSoundController gameSoundController;

    /**
     * Creates a new Game.
     *
     * @param gridWidth  width of the grid
     * @param gridHeight height of the grid
     * @param blockSize  size of individual blocks which form a tetris piece
     */
    public Game(int gridWidth, int gridHeight, int blockSize) {
        this.factory = new TetrisPieceFactory(gridWidth, gridHeight, blockSize);
        this.gridManager = new GridManager(this.factory.createTetrisPiece(TetrisPieceType.BOUNDARY),
                gridWidth,
                gridHeight);
        this.gameSoundController = new GameSoundController();
        System.out.println("Game created with grid " + gridWidth
                + "x" + gridHeight);
    }

    /**
     * Updates the game state.
     * Moves the current piece down if possible,
     * or spawns a new piece if the current one has landed.
     */
    @Override
    public void update() {
        if (!gameStarted || gameOver) {
            System.out.println("Game update skipped - gameStarted: " + gameStarted
                    + ", gameOver: " + gameOver);
            return;
        }

        if (currentPiece == null) {
            System.out.println("No current piece, spawning new one");
            spawnNewPiece();
            return;
        }

        // Try to move current piece down
        boolean moved = this.gridManager.performMove(this.currentPiece, MovementType.DOWN);

        if (moved) {
            // Piece moved down successfully
            System.out.println("Piece moved down successfully");
        } else {
            // Piece cannot move down anymore - it has landed
            System.out.println("Piece landed, fixing in place");

            // The piece is already in the grid, so we just need to:
            // 1. Clear any full lines and track how many were cleared
            // 2. Spawn a new piece

            lastLinesCleared = this.gridManager.clearFullLines();
            spawnNewPiece();
        }
    }

    /**
     * Spawns a new piece at the top of the grid.
     */
    private void spawnNewPiece() {
        // Use the pre-generated next piece, or create a new one
        if (nextPiece == null) {
            nextPiece = this.factory.createTetrisPiece(TetrisPieceType.randomPieceType());
        }

        TetrisPiece newPiece = nextPiece;
        nextPiece = this.factory.createTetrisPiece(TetrisPieceType.randomPieceType());

        // Try to add the new piece to the grid
        if (this.gridManager.addPiece(newPiece)) {
            this.currentPiece = newPiece;
            System.out.println("New piece spawned successfully");
        } else {
            // Cannot add piece - game over
            System.out.println("Cannot place new piece - GAME OVER");
            gameOver();
        }
    }

    /**
     * Gets the next piece that will be spawned.
     *
     * @return The next piece
     */
    @Override
    public Drawable getNextPiece() {
        if (nextPiece == null) {
            nextPiece = this.factory.createTetrisPiece(TetrisPieceType.randomPieceType());
        }

        return nextPiece;
    }

    /**
     * Gets the list of pieces on the grid.
     *
     * @return The list of pieces
     */
    @Override
    public List<? extends Drawable> getGrid() {
        return this.gridManager.getPieces();
    }

    /**
     * Gets the current grid as a 2D array.
     *
     * @return The grid array
     */
    @Override
    public int[][] getCurrentGrid() {
        return this.gridManager.getGridArray();
    }

    /**
     * Stops the game.
     */
    @Override
    public void stop() {
        System.out.println("Game stopped");
        gameStarted = false;
    }

    /**
     * Pauses the game.
     */
    @Override
    public void pause() {
        System.out.println("Game paused");
        // Game loop handles pausing
    }

    /**
     * Starts the game.
     * Clears the grid and spawns the first piece.
     */
    @Override
    public void start() {
        System.out.println("Game starting...");
        gameOver = false;
        gameStarted = true;

        // Clear the grid
        this.gridManager.getPieces().clear();

        // Create first piece
        this.currentPiece = null;
        this.nextPiece = null;
        spawnNewPiece();

        System.out.println("Game started with " + this.gridManager.getPieces().size()
                + " pieces");
    }

    /**
     * Handles game over state.
     */
    private void gameOver() {
        System.out.println("GAME OVER!");
        gameOver = true;
        gameStarted = false;

        // Play gameover sound
        gameSoundController.onGameOver();
    }

    /**
     * Handles keyboard input for the game.
     *
     * @param keyEvent The key event to handle
     */
    @Override
    public void handle(KeyEvent keyEvent) {
        if (!gameStarted || gameOver || currentPiece == null) {
            System.out.println("Key event ignored - game not active or no current piece");
            return;
        }

        System.out.println("Handling key: " + keyEvent.getCode());

        switch (keyEvent.getCode()) {
            case UP -> handleMove(MovementType.ROTATE);
            case DOWN -> handleMove(MovementType.DOWN);
            case LEFT -> handleMove(MovementType.LEFT);
            case RIGHT -> handleMove(MovementType.RIGHT);
            default -> {
                // Ignore other keys
            }
        }
    }

    private void handleMove(MovementType type) {
        if (this.gridManager.performMove(this.currentPiece, type)) {
            if (type == MovementType.ROTATE) {
                gameSoundController.onPieceRotated();
            } else {
                gameSoundController.onPieceMoved();
            }
        }
    }

    /**
     * Checks if the game is over.
     *
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Checks if the game has started.
     *
     * @return true if the game has started, false otherwise
     */
    public boolean isGameStarted() {
        return gameStarted;
    }

    /**
     * Gets the number of lines cleared in the last update and resets the counter.
     *
     * @return The number of lines cleared
     */
    public int getAndResetLinesCleared() {
        int lines = lastLinesCleared;
        lastLinesCleared = 0; // Reset after reading
        return lines;
    }
}
