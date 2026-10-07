package com.mycompany.irr00_group_project.gameanimation;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mycompany.irr00_group_project.gamelogic.Drawable;
import com.mycompany.irr00_group_project.gamelogic.GameEngine;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;

class GameLoopTest {

    private GameEngineStub engineStub;
    private GameTimerStub timerStub;
    private Canvas canvas;
    private GameLoop loop;

    /** A minimal stubbed-out GameEngine that just tracks update() calls. */
    static class GameEngineStub implements GameEngine {
        int updateCallCount = 0;

        @Override
        public void update() {
            updateCallCount++;
        }

        @Override
        public List<? extends Drawable> getGrid() {
            return Collections.emptyList();
        }

        @Override
        public int[][] getCurrentGrid() {
            return new int[0][0];
        }

        @Override
        public Drawable getNextPiece() {
            return null;
        }

        @Override
        public void start() {
        }

        @Override
        public void stop() {
        }

        @Override
        public void pause() {
        }

        @Override
        public void handle(KeyEvent event) {
        }
    }

    /** A subclass of GameTimer that overrides Timeline usage to avoid real scheduling. */
    static class GameTimerStub extends GameTimer {
        boolean started = false;
        boolean paused = false;
        boolean stopped = false;

        /**
         * Creates a new GameTimerStub with a dummy interval and immediately stops its Timeline.
         */
        GameTimerStub() {
            // Call super with a dummy interval, but immediately stop its Timeline:
            super(500);
            super.stop();
        }

        @Override
        public void start() {
            started = true;
        }

        @Override
        public void stop() {
            stopped = true;
        }

        @Override
        public void pause() {
            paused = true;
        }

        @Override
        public void resume() {
            paused = false;
        }

        @Override
        public boolean shouldDrop() {
            // Pretend it's always time to drop once after start
            return started && !paused;
        }

        @Override
        public void reset() {
            // no-op
        }
    }

    /**
     * Initializes the JavaFX toolkit for testing.
     * @throws InterruptedException if the initialization times out
     */
    @BeforeAll
    static void initJfxToolkit() throws InterruptedException {
        // Initialize JavaFX toolkit so that Canvas and AnimationTimer exist
        JavaFXTestUtils.initializeJavaFX();
    }

    /**
     * Sets up a new GameLoop with stub engine and timer before each test.
     */
    @BeforeEach
    void setUp() {
        engineStub = new GameEngineStub();
        timerStub = new GameTimerStub();
        canvas = new Canvas(100, 200);
        loop = new GameLoop(engineStub, timerStub, canvas);
    }

    /**
     * Verifies initial state: not running, not paused, default target FPS.
     */
    @Test
    void testInitialState() {
        // By default, not running, not paused, targetFPS = 30
        assertFalse(loop.isRunning());
        assertFalse(loop.isPaused());
        assertEquals(30, loop.getTargetFPS());
    }

    /**
     * Tests that start() sets running flag, unpauses, and starts timer only once.
     */
    @Test
    void testStartSetsFlagsAndStartsTimer() {
        loop.start();
        assertTrue(loop.isRunning());
        assertFalse(loop.isPaused());
        assertTrue(timerStub.started);

        // Calling start again should not clear flags or call start() twice
        timerStub.started = false;
        loop.start();
        assertFalse(timerStub.started,
                "start() on GameTimer should not be called again if already running");
    }

    /**
     * Tests that pause() only takes effect when running and only once when paused.
     */
    @Test
    void testPauseOnlyWhenRunning() {
        // Calling pause before start => no effect
        loop.pause();
        assertFalse(loop.isPaused());
        assertFalse(timerStub.paused);

        // Now start, then pause
        loop.start();
        loop.pause();
        assertTrue(loop.isPaused());
        assertTrue(timerStub.paused);

        // Calling pause again => no additional change
        timerStub.paused = false;
        loop.pause();
        assertFalse(timerStub.paused,
                "pause() on GameTimer should not be called again if already paused");
    }

    /**
     * Tests that resume() only takes effect when paused and only once.
     */
    @Test
    void testResumeOnlyWhenPaused() {
        // Without start => resume is no-op
        loop.resume();
        assertFalse(loop.isPaused());

        // Start & pause, then resume
        loop.start();
        loop.pause();
        assertTrue(loop.isPaused());

        loop.resume();
        assertFalse(loop.isPaused());
        assertFalse(timerStub.paused,
                "resume() should clear paused flag on timer");

        // Calling resume again => no change
        timerStub.paused = true;
        loop.resume();
        assertTrue(timerStub.paused,
                "resume() should not be called when not paused");
    }

    /**
     * Verifies that stop() halts loop and timer only once.
     */
    @Test
    void testStopStopsLoopAndTimer() {
        loop.start();
        assertTrue(loop.isRunning());

        loop.stop();
        assertFalse(loop.isRunning());
        assertTrue(timerStub.stopped);

        // Calling stop again => no change
        timerStub.stopped = false;
        loop.stop();
        assertFalse(timerStub.stopped,
                "stop() on GameTimer not called again if already stopped");
    }
}
