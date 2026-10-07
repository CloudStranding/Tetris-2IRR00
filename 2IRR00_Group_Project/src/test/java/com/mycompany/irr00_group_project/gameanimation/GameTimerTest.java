package com.mycompany.irr00_group_project.gameanimation;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for GameTimer.
 */
class GameTimerTest {

    private GameTimer timer;

    /**
     * Initializes the JavaFX toolkit for testing.
     *
     * @throws InterruptedException if the initialization times out
     */
    @BeforeAll
    static void initToolkit() throws InterruptedException {
        JavaFXTestUtils.initializeJavaFX();
    }

    /**
     * Sets up a new GameTimer instance before each test.
     */
    @BeforeEach
    void setup() {
        timer = new GameTimer(100); // 100ms interval for quick testing
    }

    /**
     * Verifies that a new timer is not ready to drop immediately after creation.
     */
    @Test
    void testInitialState() {
        assertFalse(timer.shouldDrop(),
                "Timer should not be ready to drop initially");
    }

    /**
     * Verifies start, stop, and reset behavior of the timer.
     */
    @Test
    void testStartStop() {
        timer.start();
        // After a short delay, it might be ready to drop
        timer.stop();
        // After stop, reset the timer
        timer.reset();
        assertFalse(timer.shouldDrop(),
                "After reset, should not be ready to drop");
    }

    /**
     * Verifies pause and resume flow of the timer without asserting timing specifics.
     */
    @Test
    void testPauseResume() {
        timer.start();
        timer.pause();
        timer.resume();
        // Basic flow test - specific timing behavior may vary
    }
}
