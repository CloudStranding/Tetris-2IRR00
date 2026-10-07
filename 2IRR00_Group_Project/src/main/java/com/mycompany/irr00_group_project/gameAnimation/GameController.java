package com.mycompany.irr00_group_project.gameanimation;

import com.mycompany.irr00_group_project.EnhancedGameEngine;

/**
 * GameController coordinates between the game loop, game engine, and user input.
 * Manages game state transitions and handles input acceleration.
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
     */
    public GameController(EnhancedGameEngine gameEngine) {
        this.gameEngine = gameEngine;
        this.gameTimer = new GameTimer();
        this.gameLoop = new GameLoop(gameEngine, gameTimer);
    }

    /**
     * Starts a new game.
     */
    public void startGame() {
        isGameStarted = true;
        gameEngine.start();
        gameLoop.start();
    }

    /**
     * Pauses the game.
     */
    public void pauseGame() {
        if (isGameStarted && !gameEngine.isGameOver()) {
            gameEngine.pause();
            gameLoop.pause();
        }
    }

    /**
     * Resumes the game from pause.
     */
    public void resumeGame() {
        if (isGameStarted && !gameEngine.isGameOver() && gameLoop.isPaused()) {
            gameLoop.resume();
        }
    }

    /**
     * Stops the game completely.
     */
    public void stopGame() {
        isGameStarted = false;
        gameEngine.stop();
        gameLoop.stop();
    }

    /**
     * Sets the game difficulty level.
     *
     * @param level the difficulty level (1-10)
     */
    public void setDifficulty(int level) {
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
        return isGameStarted && gameEngine.isGameStarted()
                && !gameEngine.isGameOver() && gameLoop.isRunning();
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
     * Gets the game loop for external access.
     *
     * @return the game loop
     */
    public GameLoop getGameLoop() {
        return gameLoop;
    }
} 