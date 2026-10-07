package com.mycompany.irr00_group_project.gameanimation;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mycompany.irr00_group_project.gameAnimation.GameRenderer;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;


  //@author Yanhang Luo
  
class GameRendererTest {

    private Canvas canvas;
    private GameRenderer renderer;

    @BeforeAll
    static void initJfxToolkit() throws InterruptedException {
        // Initialize JavaFX so Canvas and GraphicsContext are usable
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new RuntimeException("Timeout initializing JavaFX");
        }
    }

    @BeforeEach
    void setUp() {
        canvas = new Canvas(300, 500);
        renderer = new GameRenderer(canvas);
    }

    @Test
    void testClearCanvasDoesNotThrow() {
        assertDoesNotThrow(() -> renderer.clearCanvas());
    }

    @Test
    void testRenderGridBackgroundDoesNotThrow() {
        // e.g. gridWidth = 10, gridHeight = 20, blockSize = 30
        assertDoesNotThrow(() -> renderer.renderGridBackground(10, 20, 30));
    }

    @Test
    void testRenderGameOverDoesNotThrow() {
        assertDoesNotThrow(() -> renderer.renderGameOver());
    }

    @Test
    void testRenderPauseScreenDoesNotThrow() {
        assertDoesNotThrow(() -> renderer.renderPauseScreen());
    }

    @Test
    void testRenderCountdownDoesNotThrow() {
        for (int i = 3; i >= 0; i--) {
            int count = i;
            assertDoesNotThrow(() -> renderer.renderCountdown(count));
        }
    }

    @Test
    void testRenderScoreAnimationDoesNotThrow() {
        assertDoesNotThrow(() -> renderer.renderScoreAnimation(150, 100.0, 200.0));
    }

    @Test
    void testGetCanvasWidthHeight() {
        assertEquals(300.0, renderer.getCanvasWidth(), 0.01);
        assertEquals(500.0, renderer.getCanvasHeight(), 0.01);
    }

    @Test
    void testRenderCallbackExecution() {
        final boolean[] wasCalled = { false };
        renderer.setRenderCallback(gc -> wasCalled[0] = true);
        renderer.executeRenderCallback();
        assertTrue(wasCalled[0], "executeRenderCallback() should invoke the provided Consumer<GraphicsContext>");
    }
}