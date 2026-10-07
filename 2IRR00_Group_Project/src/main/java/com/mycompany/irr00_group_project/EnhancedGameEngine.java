package com.mycompany.irr00_group_project;

import java.util.List;

import com.mycompany.irr00_group_project.gamelogic.Drawable;
import com.mycompany.irr00_group_project.gamelogic.Game;
import com.mycompany.irr00_group_project.gamelogic.GameEngine;
import com.mycompany.irr00_group_project.scoresystem.ScoreManager;

import javafx.scene.input.KeyEvent;

/**
 * Enhanced GameEngine that integrates score tracking with the game logic.
 * This wrapper allows the score system to work with the existing game logic
 * without modifying the original code.
 */
public class EnhancedGameEngine implements GameEngine {
    
    private final Game game;
    private final ScoreManager scoreManager;
    private int previousScore;
    private int previousPieceCount;
    private String currentUsername;
    
    public EnhancedGameEngine(int gridWidth, int gridHeight, int blockSize) {
        this.game = new Game(gridWidth, gridHeight, blockSize);
        this.scoreManager = new ScoreManager();
        this.previousScore = 0;
        this.previousPieceCount = 0;
        System.out.println("EnhancedGameEngine created with grid " + gridWidth + "x" + gridHeight);
    }
    
    @Override
    public void start() {
        System.out.println("Starting enhanced game engine...");
        game.start();
        scoreManager.resetScore();
        previousScore = 0;
        previousPieceCount = 0;
        System.out.println("Game started. Initial pieces: " + game.getGrid().size());
    }
    
    @Override
    public void stop() {
        System.out.println("Stopping enhanced game engine...");
        game.stop();
    }
    
    @Override
    public void pause() {
        System.out.println("Pausing enhanced game engine...");
        game.pause();
    }
    
    /**
     * Restarts the game completely.
     */
    public void restart() {
        System.out.println("Restarting enhanced game engine...");
        game.restart();
        scoreManager.resetScore();
        previousScore = 0;
        previousPieceCount = 0;
        System.out.println("Game restarted. Pieces: " + game.getGrid().size());
    }
    
    @Override
    public void update() {
        if (!game.isGameStarted() || game.isGameOver()) {
            return;
        }
        
        // Store previous state for debugging
        int piecesBefore = game.getGrid().size();
        
        // Perform the game update
        game.update();
        
        // Get accurate line clearing information from the game
        int linesCleared = game.getAndResetLinesCleared();
        if (linesCleared > 0) {
            System.out.println("Lines cleared: " + linesCleared + " (accurate count from GridManager)");
            scoreManager.updateScore(linesCleared);
            System.out.println("Score updated to: " + scoreManager.getScore());
        }
        
        // Track piece placement for debugging
        int piecesAfter = game.getGrid().size();
        if (piecesAfter > piecesBefore) {
            System.out.println("Piece placed. Current pieces: " + piecesAfter + " (no points for placement)");
        }
        
        previousPieceCount = piecesAfter;
    }
    
    @Override
    public Drawable getNextPiece() {
        return game.getNextPiece();
    }
    
    @Override
    public List<? extends Drawable> getGrid() {
        return game.getGrid();
    }
    
    @Override
    public void handle(KeyEvent event) {
        game.handle(event);
    }
    
    /**
     * Gets the current grid as a 2D array.
     */
    public int[][] getCurrentGrid() {
        return game.getCurrentGrid();
    }
    
    /**
     * Gets the current score.
     */
    public int getScore() {
        return scoreManager.getScore();
    }
    
    /**
     * Gets the score manager for external access.
     */
    public ScoreManager getScoreManager() {
        return scoreManager;
    }
    
    /**
     * Checks if the game is over.
     */
    public boolean isGameOver() {
        return game.isGameOver();
    }
    
    /**
     * Checks if the game is started.
     */
    public boolean isGameStarted() {
        return game.isGameStarted();
    }

    /**
     * Sets the username for the current game.
     *
     * @param username username
     */
    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

    /**
     * Get the username for the current game.
     *
     * @return username
     */
    public String getCurrentUsername() {
        return currentUsername;
    }
}