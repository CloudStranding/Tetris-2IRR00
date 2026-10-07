package com.mycompany.irr00_group_project.gameanimation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * GameTimer manages the timing for automatic block dropping in Tetris.
 * Handles regular intervals and speed adjustments.
 * 
 * @author Koray
 */
public class GameTimer {
    
    private static final double DEFAULT_DROP_INTERVAL = 1000; // milliseconds
    private static final double MIN_DROP_INTERVAL = 100; // minimum drop speed
    private static final double SPEED_MULTIPLIER = 0.5; // for down key acceleration
    
    private Timeline dropTimeline;
    private double currentInterval;
    private boolean shouldDrop;
    private boolean isPaused;
    private boolean isAccelerated;
    
    /**
     * Creates a new GameTimer with default drop interval.
     */
    public GameTimer() {
        this(DEFAULT_DROP_INTERVAL);
    }
    
    /**
     * Creates a new GameTimer with specified drop interval.
     * 
     * @param dropInterval the interval in milliseconds between drops
     */
    public GameTimer(double dropInterval) {
        this.currentInterval = dropInterval;
        this.shouldDrop = false;
        this.isPaused = false;
        this.isAccelerated = false;
        initializeTimeline();
    }
    
    /**
     * Initializes the Timeline for drop timing.
     */
    private void initializeTimeline() {
        dropTimeline = new Timeline(
            new KeyFrame(Duration.millis(currentInterval), e -> triggerDrop())
        );
        dropTimeline.setCycleCount(Timeline.INDEFINITE);
    }
    
    /**
     * Triggers a drop event.
     */
    private void triggerDrop() {
        if (!isPaused) {
            shouldDrop = true;
        }
    }
    
    /**
     * Checks if a drop should occur.
     * 
     * @return true if drop should happen, false otherwise
     */
    public boolean shouldDrop() {
        return shouldDrop;
    }
    
    /**
     * Resets the drop flag after processing.
     */
    public void reset() {
        shouldDrop = false;
    }
    
    /**
     * Starts the drop timer.
     */
    public void start() {
        dropTimeline.play();
    }
    
    /**
     * Stops the drop timer completely.
     */
    public void stop() {
        dropTimeline.stop();
        shouldDrop = false;
    }
    
    /**
     * Pauses the drop timer.
     */
    public void pause() {
        isPaused = true;
        dropTimeline.pause();
    }
    
    /**
     * Resumes the drop timer from pause.
     */
    public void resume() {
        isPaused = false;
        dropTimeline.play();
    }
    
    /**
     * Accelerates the drop speed (for down key press).
     */
    public void accelerate() {
        if (!isAccelerated) {
            isAccelerated = true;
            updateInterval(currentInterval * SPEED_MULTIPLIER);
        }
    }
    
    /**
     * Returns to normal drop speed.
     */
    public void decelerate() {
        if (isAccelerated) {
            isAccelerated = false;
            updateInterval(currentInterval / SPEED_MULTIPLIER);
        }
    }
    
    /**
     * Updates the drop interval with a new value.
     * 
     * @param newInterval the new interval in milliseconds
     */
    public void updateInterval(double newInterval) {
        double safeInterval = Math.max(newInterval, MIN_DROP_INTERVAL);
        currentInterval = safeInterval;
        
        boolean wasPlaying = dropTimeline.getStatus() == Timeline.Status.RUNNING;
        dropTimeline.stop();
        
        dropTimeline = new Timeline(
            new KeyFrame(Duration.millis(currentInterval), e -> triggerDrop())
        );
        dropTimeline.setCycleCount(Timeline.INDEFINITE);
        
        if (wasPlaying) {
            dropTimeline.play();
        }
    }
    
    /**
     * Sets the difficulty level which affects drop speed.
     * 
     * @param level the difficulty level (1-10)
     */
    public void setDifficultyLevel(int level) {
        level = Math.max(1, Math.min(level, 10));
        double newInterval = DEFAULT_DROP_INTERVAL / (1 + (level - 1) * 0.1);
        updateInterval(newInterval);
    }
    
    /**
     * Gets the current drop interval.
     * 
     * @return the current interval in milliseconds
     */
    public double getCurrentInterval() {
        return currentInterval;
    }
    
    /**
     * Checks if the timer is currently accelerated.
     * 
     * @return true if accelerated, false otherwise
     */
    public boolean isAccelerated() {
        return isAccelerated;
    }
} 