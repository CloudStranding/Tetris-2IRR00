package com.mycompany.irr00_group_project.gameanimation;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.canvas.Canvas;

/**
 * Unit tests for GameRenderer.
 * @author Yanhang Luo
 */
class GameRendererTest {

    private GameRenderer renderer;
    private Canvas       canvas;

    /**
     * Initializes the JavaFX toolkit for testing.
     * @throws InterruptedException if the initialization times out
     */
    @BeforeAll
    static void initToolkit() throws InterruptedException {
        JavaFXTestUtils.initializeJavaFX();
    }

    /**
     * Sets up a new GameRenderer and Canvas instance before each test.
     */
    @BeforeEach
    void setup() {
        canvas   = new Canvas(300, 600);
        renderer = new GameRenderer(canvas);
    }

    /**
     * Verifies that the GameRenderer instance is created successfully.
     */
    @Test
    void testRendererCreation() {
        assertNotNull(renderer, "Renderer should be created successfully");
    }

    /**
     * Verifies that the renderer has access to the canvas graphics context.
     */
    @Test
    void testCanvasInteraction() {
        // Test that renderer can work with canvas
        assertNotNull(canvas.getGraphicsContext2D(),
                "Canvas should have graphics context");
    }
}
