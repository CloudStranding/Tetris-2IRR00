package com.mycompany.irr00_group_project.gameanimation;

import com.mycompany.irr00_group_project.gamelogic.GameEngine;
import com.mycompany.irr00_group_project.sound.SoundManager;

import javafx.animation.AnimationTimer;

/**
 * GameLoop manages the main game loop using JavaFX AnimationTimer.
 * Ensures smooth 30 FPS rendering and handles game timing.
 */
public class GameLoop {

    private static final long TARGET_FPS = 30;
    private static final long OPTIMAL_TIME = 1_000_000_000 / TARGET_FPS; // nanoseconds per frame

    private final GameEngine gameEngine;
    private final GameTimer gameTimer;
    private AnimationTimer animationTimer;

    private long lastUpdate = 0;
    private boolean isRunning = false;
    private boolean isPaused = false;

    private Runnable renderCallback;

    /**
     * Creates a new GameLoop instance.
     *
     * @param gameEngine the game engine to update
     * @param gameTimer  the timer for automatic block dropping
     */
    public GameLoop(GameEngine gameEngine, GameTimer gameTimer) {
        this.gameEngine = gameEngine;
        this.gameTimer = gameTimer;
        initializeAnimationTimer();
    }

    /**
     * Initializes the JavaFX AnimationTimer for the game loop.
     */
    private void initializeAnimationTimer() {
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= OPTIMAL_TIME) {
                    if (!isPaused) {
                        gameUpdate();
                    }

                    if (renderCallback != null) {
                        renderCallback.run();
                    }

                    lastUpdate = now;
                }
            }
        };
    }

    /**
     * Main game update method called every frame.
     */
    private void gameUpdate() {
        if (gameTimer.shouldDrop()) {
            try {
                gameEngine.update();
                gameTimer.reset();
                System.out.println("Game updated - pieces: " + gameEngine.getGrid().size());
            } catch (Exception e) {
                System.err.println("Error updating game: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Sets a render callback for additional rendering operations.
     *
     * @param callback the render callback
     */
    public void setRenderCallback(Runnable callback) {
        this.renderCallback = callback;
    }

    /**
     * Starts the game loop and game timer.
     */
    public void start() {
        if (!isRunning) {
            isRunning = true;
            isPaused = false;
            lastUpdate = System.nanoTime();
            animationTimer.start();
            gameTimer.start();
            SoundManager.playBackgroundMusic();
        }
    }

    /**
     * Stops the game loop and timer completely.
     */
    public void stop() {
        if (isRunning) {
            isRunning = false;
            animationTimer.stop();
            gameTimer.stop();
            SoundManager.stopBackgroundMusic();
        }
    }

    /**
     * Pauses the game loop.
     */
    public void pause() {
        if (isRunning && !isPaused) {
            isPaused = true;
            gameTimer.pause();
            SoundManager.pauseBackgroundMusic();
        }
    }

    /**
     * Resumes the game loop from pause.
     */
    public void resume() {
        if (isRunning && isPaused) {
            isPaused = false;
            lastUpdate = System.nanoTime(); // Reset to avoid jump
            gameTimer.resume();
            SoundManager.playBackgroundMusic();
        }
    }

    /**
     * Checks if the game loop is running.
     *
     * @return true if running, false otherwise
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Checks if the game loop is paused.
     *
     * @return true if paused, false otherwise
     */
    public boolean isPaused() {
        return isPaused;
    }

    /**
     * Gets the current FPS target.
     *
     * @return the target FPS
     */
    public long getTargetFPS() {
        return TARGET_FPS;
    }
} 