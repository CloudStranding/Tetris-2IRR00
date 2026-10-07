package com.mycompany.irr00_group_project.gui.game;

import com.mycompany.irr00_group_project.EnhancedGameEngine;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * Game area component for Tetris game.
 */
public class GameArea extends StackPane {
    private static final int GRID_WIDTH = 10;
    private static final int GRID_HEIGHT = 20;
    private static final int BLOCK_SIZE = 30;

    private final Canvas gameCanvas;
    private final EnhancedGameEngine gameEngine;

    /**
     * Creates a new GameArea.
     *
     * @param gameEngine the game engine instance
     */
    public GameArea(EnhancedGameEngine gameEngine) {
        this.gameEngine = gameEngine;
        gameCanvas = new Canvas(GRID_WIDTH * BLOCK_SIZE, GRID_HEIGHT * BLOCK_SIZE);
        initializeCanvas();
        getChildren().add(gameCanvas);

        String style = "-fx-background-color: rgba(0,0,0,0.3);"
                + "-fx-background-radius: 15;"
                + "-fx-border-color: rgba(230, 192, 104, 0.4);"
                + "-fx-border-width: 2;"
                + "-fx-border-radius: 15;"
                + "-fx-padding: 15;";
        setStyle(style);
    }

    private void initializeCanvas() {
        renderGame();
    }

    /**
     * Renders the game state.
     */
    public void renderGame() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();

        // Clear canvas
        gc.setFill(Color.rgb(20, 20, 20));
        gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        // Draw grid lines
        gc.setStroke(Color.rgb(40, 40, 40));
        gc.setLineWidth(1);
        for (int x = 0; x <= GRID_WIDTH; x++) {
            gc.strokeLine(x * BLOCK_SIZE, 0, x * BLOCK_SIZE, GRID_HEIGHT * BLOCK_SIZE);
        }
        for (int y = 0; y <= GRID_HEIGHT; y++) {
            gc.strokeLine(0, y * BLOCK_SIZE, GRID_WIDTH * BLOCK_SIZE, y * BLOCK_SIZE);
        }

        // Draw game pieces
        if (gameEngine != null) {
            try {
                gameEngine.getGrid().forEach(drawable -> {
                    if (drawable != null) {
                        drawable.draw(gc);
                    }
                });
            } catch (Exception e) {
                System.err.println("Error drawing game pieces: " + e.getMessage());
            }
        }

        // Draw inner border
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1);
        gc.strokeRect(1, 1, gameCanvas.getWidth() - 2, gameCanvas.getHeight() - 2);
    }
}