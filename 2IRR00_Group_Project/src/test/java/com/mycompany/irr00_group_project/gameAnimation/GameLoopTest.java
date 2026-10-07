package com.mycompany.irr00_group_project.gameanimation;

import com.mycompany.irr00_group_project.gameAnimation.GameLoop;
import com.mycompany.irr00_group_project.gameAnimation.GameTimer;
import com.mycompany.irr00_group_project.gamelogic.GameEngine; 
import static org.junit.jupiter.api.Assertions.*;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//@author Yanhang Luo

class GameLoopTest {

    private GameEngineStub engineStub;
    private GameTimerStub timerStub;
    private Canvas canvas;
    private GameLoop loop;

    /** A minimal stubbed-out GameEngine that just tracks update() calls. */
    static class GameEngineStub implements com.mycompany.irr00_group_project.gamelogic.GameEngine {
        int updateCallCount = 0;
        @Override public void update() { updateCallCount++; }
        @Override public java.util.List<?> getGrid() { return java.util.Collections.emptyList(); }
        // All other methods (rotate, move, etc.) are not used by GameLoop; leave unimplemented.
    }

    /** A subclass of GameTimer that overrides Timeline usage to avoid real scheduling. */
    static class GameTimerStub extends GameTimer {
        boolean started = false;
        boolean paused = false;
        boolean stopped = false;

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

    @BeforeAll
    static void initJfxToolkit() throws InterruptedException {
        // Initialize JavaFX toolkit so that Canvas and AnimationTimer exist
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new RuntimeException("Timeout initializing JavaFX");
        }
    }

    @BeforeEach
    void setUp() {
        engineStub = new GameEngineStub();
        timerStub = new GameTimerStub();
        canvas = new Canvas(100, 200);
        loop = new GameLoop(engineStub, timerStub, canvas);
    }

    @Test
    void testInitialState() {
        // By default, not running, not paused, targetFPS = 30
        assertFalse(loop.isRunning());
        assertFalse(loop.isPaused());
        assertEquals(30, loop.getTargetFPS());
    }

    @Test
    void testStartSetsFlagsAndStartsTimer() {
        loop.start();
        assertTrue(loop.isRunning());
        assertFalse(loop.isPaused());
        assertTrue(timerStub.started);

        // Calling start again should not clear flags or call start() twice
        timerStub.started = false;
        loop.start();
        assertFalse(timerStub.started, "start() on GameTimer should not be called again if already running");
    }

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
        assertFalse(timerStub.paused, "pause() on GameTimer should not be called again if already paused");
    }

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
        assertFalse(timerStub.paused, "resume() should clear paused flag on timer");

        // Calling resume again => no change
        timerStub.paused = true;
        loop.resume();
        assertTrue(timerStub.paused, "resume() should not be called when not paused");
    }

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
        assertFalse(timerStub.stopped, "stop() on GameTimer not called again if already stopped");
    }
}
