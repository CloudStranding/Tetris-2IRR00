package com.mycompany.irr00_group_project.gameanimation;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mycompany.irr00_group_project.EnhancedGameEngine;
import com.mycompany.irr00_group_project.gameAnimation.GameController;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;

/**
 * Unit tests for GameController.
 * @author Yanhang Luo
 */
class GameControllerTest {

    static class StubEngine extends EnhancedGameEngine {
        private boolean started = false;
        private boolean over    = false;

        public StubEngine() {
            super(1,1,1);
        }

        @Override
        public void start() {
            started = true;
        }

        @Override
        public void stop() {
            started = false;
        }

        @Override
        public void pause() {
            // no-op
        }

        @Override
        public boolean isGameStarted() {
            return started;
        }

        @Override
        public boolean isGameOver() {
            return over;
        }

        // Expose a way to set gameOver
        public void setGameOver(boolean o) {
            over = o;
        }
    }

    private GameController controller;
    private StubEngine    engine;

    @BeforeAll
    static void initToolkit() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new RuntimeException("JavaFX init timeout");
        }
    }

    @BeforeEach
    void setup() {
        engine = new StubEngine();
        controller = new GameController(engine, new Canvas(100, 200));
    }

    @Test
    void testStartPauseResumeStopRestartFlow() {
        // Initially nothing is running
        assertFalse(controller.isGameRunning());

        // Start
        controller.startGame();
        assertTrue(engine.isGameStarted(),    "Engine should have been started");
        assertTrue(controller.isGameRunning(), "Controller reports running");
        assertFalse(controller.isGamePaused());
        assertFalse(controller.isGameOver());

        // Pause
        controller.pauseGame();
        assertTrue(controller.isGamePaused(),  "Controller reports paused");

        // Resume
        controller.resumeGame();
        assertFalse(controller.isGamePaused(), "Controller reports not paused");

        // Simulate game over
        engine.setGameOver(true);
        assertTrue(controller.isGameOver(),    "Controller reports game over");
        // Further pause/resume should not flip running
        controller.pauseGame();
        assertFalse(controller.isGameRunning(), "After game over, not running");

        // Restart
        engine.setGameOver(false);
        controller.restartGame();
        assertTrue(controller.isGameRunning(),  "After restart, running again");
        assertFalse(controller.isGameOver());
    }

    @Test
    void testStopCompletely() {
        controller.startGame();
        assertTrue(controller.isGameRunning());

        controller.stopGame();
        assertFalse(engine.isGameStarted(),   "Engine stopped");
        assertFalse(controller.isGameRunning(), "Controller not running");
    }
}
