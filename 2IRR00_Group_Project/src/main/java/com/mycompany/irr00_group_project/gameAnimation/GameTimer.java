package com.mycompany.irr00_group_project.gameanimation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * GameTimer manages the timing for automatic block dropping in Tetris.
 * Handles regular intervals and speed adjustments.
 */
public class GameTimer {

    private static final double DEFAULT_DROP_INTERVAL = 1000; // milliseconds
    private static final double MIN_DROP_INTERVAL = 100; // minimum drop speed

    private Timeline dropTimeline;
    private double currentInterval;
    private boolean shouldDrop;
    private boolean isPaused;

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
     * Updates the drop interval with a new value.
     *
     * @param newInterval the new interval in milliseconds
     */
    public void updateInterval(double newInterval) {
        currentInterval = Math.max(newInterval, MIN_DROP_INTERVAL);

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

    public boolean shouldDrop() {
        return shouldDrop;
    }
}