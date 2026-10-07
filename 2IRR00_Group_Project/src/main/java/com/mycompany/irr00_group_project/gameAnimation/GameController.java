package com.mycompany.irr00_group_project.gameAnimation;

import com.mycompany.irr00_group_project.EnhancedGameEngine;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * GameController coordinates between the game loop, game engine, and user input.
 * Manages game state transitions and handles input acceleration.
 * 
 * @author Koray
 */
public class GameController {
    
    private final EnhancedGameEngine gameEngine;
    private final GameLoop gameLoop;
    private final GameTimer gameTimer;
    
    private boolean isGameStarted = false;
    
    /**
     * Creates a new GameController.
     * 
     * @param gameEngine the game engine to control
     * @param gameCanvas the canvas for rendering
     */
    public GameController(EnhancedGameEngine gameEngine, Canvas gameCanvas) {
        this.gameEngine = gameEngine;
        this.gameTimer = new GameTimer();
        this.gameLoop = new GameLoop(gameEngine, gameTimer, gameCanvas);
        System.out.println("GameController created");
    }
    
    /**
     * Starts a new game.
     */
    public void startGame() {
        System.out.println("GameController: Starting game...");
        isGameStarted = true;
        gameEngine.start();
        gameLoop.start();
        System.out.println("GameController: Game started successfully");
    }
    
    /**
     * Pauses the game.
     */
    public void pauseGame() {
        if (isGameStarted && !gameEngine.isGameOver()) {
            System.out.println("GameController: Pausing game...");
            gameEngine.pause();
            gameLoop.pause();
        }
    }
    
    /**
     * Resumes the game from pause.
     */
    public void resumeGame() {
        if (isGameStarted && !gameEngine.isGameOver() && gameLoop.isPaused()) {
            System.out.println("GameController: Resuming game...");
            gameLoop.resume();
        }
    }
    
    /**
     * Stops the game completely.
     */
    public void stopGame() {
        System.out.println("GameController: Stopping game...");
        isGameStarted = false;
        gameEngine.stop();
        gameLoop.stop();
    }
    
    /**
     * Restarts the game.
     */
    public void restartGame() {
        System.out.println("GameController: Restarting game...");
        
        // Stop current game loop
        gameLoop.stop();
        
        // Reset game state
        isGameStarted = false;
        
        // Restart the game engine
        gameEngine.restart();
        
        // Start everything again
        isGameStarted = true;
        gameLoop.start();
        
        System.out.println("GameController: Game restarted successfully");
    }
    
    /**
     * Handles key press events with acceleration for down key.
     * 
     * @param event the key event
     */
    public void handleKeyPress(KeyEvent event) {
        if (!isGameStarted || gameEngine.isGameOver() || gameLoop.isPaused()) {
            System.out.println("Key press ignored - game not active");
            return;
        }
        
        // Handle down key acceleration
        if (event.getCode() == KeyCode.DOWN) {
            gameTimer.accelerate();
        }
        
        // Pass event to game engine for movement handling
        gameEngine.handle(event);
    }
    
    /**
     * Handles key release events to decelerate drop speed.
     * 
     * @param event the key event
     */
    public void handleKeyRelease(KeyEvent event) {
        if (event.getCode() == KeyCode.DOWN) {
            gameTimer.decelerate();
        }
    }
    
    /**
     * Sets the game difficulty level.
     * 
     * @param level the difficulty level (1-10)
     */
    public void setDifficulty(int level) {
        System.out.println("Setting difficulty to level: " + level);
        gameTimer.setDifficultyLevel(level);
    }
    
    /**
     * Toggles between pause and resume.
     */
    public void togglePause() {
        if (gameLoop.isPaused()) {
            resumeGame();
        } else {
            pauseGame();
        }
    }
    
    /**
     * Checks if the game is currently running.
     * 
     * @return true if game is running, false otherwise
     */
    public boolean isGameRunning() {
        return isGameStarted && gameEngine.isGameStarted() && !gameEngine.isGameOver() && gameLoop.isRunning();
    }
    
    /**
     * Checks if the game is paused.
     * 
     * @return true if paused, false otherwise
     */
    public boolean isGamePaused() {
        return gameLoop.isPaused();
    }
    
    /**
     * Checks if the game is over.
     * 
     * @return true if game over, false otherwise
     */
    public boolean isGameOver() {
        return gameEngine.isGameOver();
    }
    
    /**
     * Gets the game timer for external access.
     * 
     * @return the game timer
     */
    public GameTimer getGameTimer() {
        return gameTimer;
    }
    
    /**
     * Gets the game loop for external access.
     * 
     * @return the game loop
     */
    public GameLoop getGameLoop() {
        return gameLoop;
    }
} 