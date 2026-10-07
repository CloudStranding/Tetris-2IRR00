package com.mycompany.irr00_group_project.gameanimation;

import java.util.function.Consumer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * GameRenderer handles additional rendering utilities for the game.
 * Provides helper methods for special effects and UI overlays.
 * 
 * @author Koray
 */
public class GameRenderer {
    
    private final Canvas gameCanvas;
    private final GraphicsContext gc;
    
    // Callback for custom rendering
    private Consumer<GraphicsContext> renderCallback;
    
    /**
     * Creates a new GameRenderer.
     * 
     * @param gameCanvas the canvas to render on
     */
    public GameRenderer(Canvas gameCanvas) {
        this.gameCanvas = gameCanvas;
        this.gc = gameCanvas.getGraphicsContext2D();
    }
    
    /**
     * Clears the entire canvas.
     */
    public void clearCanvas() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
    }
    
    /**
     * Renders the game grid background.
     * 
     * @param gridWidth width of the grid in blocks
     * @param gridHeight height of the grid in blocks
     * @param blockSize size of each block in pixels
     */
    public void renderGridBackground(int gridWidth, int gridHeight, int blockSize) {
        gc.setStroke(Color.DARKGRAY.darker());
        gc.setLineWidth(0.5);
        
        // Draw vertical lines
        for (int x = 0; x <= gridWidth; x++) {
            gc.strokeLine(x * blockSize, 0, x * blockSize, gridHeight * blockSize);
        }
        
        // Draw horizontal lines
        for (int y = 0; y <= gridHeight; y++) {
            gc.strokeLine(0, y * blockSize, gridWidth * blockSize, y * blockSize);
        }
    }
    
    /**
     * Sets a custom render callback for additional rendering.
     * 
     * @param callback the rendering callback
     */
    public void setRenderCallback(Consumer<GraphicsContext> callback) {
        this.renderCallback = callback;
    }
    
    /**
     * Executes the custom render callback if set.
     */
    public void executeRenderCallback() {
        if (renderCallback != null) {
            renderCallback.accept(gc);
        }
    }
    
    /**
     * Renders a game over screen overlay.
     */
    public void renderGameOver() {
        // Semi-transparent overlay
        gc.setFill(Color.rgb(0, 0, 0, 0.7));
        gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        
        // Game over text
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", 36));
        String text = "GAME OVER";
        double textWidth = text.length() * 18; // Approximate width
        gc.fillText(text, 
                    (gameCanvas.getWidth() - textWidth) / 2, 
                    gameCanvas.getHeight() / 2);
        
        // Instructions
        gc.setFont(Font.font("Arial", 14));
        gc.fillText("Press RESTART to play again", 
                    gameCanvas.getWidth() / 2 - 90, 
                    gameCanvas.getHeight() / 2 + 40);
    }
    
    /**
     * Renders a pause screen overlay.
     */
    public void renderPauseScreen() {
        // Semi-transparent overlay
        gc.setFill(Color.rgb(0, 0, 0, 0.5));
        gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        
        // Pause text
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", 24));
        String text = "PAUSED";
        double textWidth = text.length() * 12; // Approximate width
        gc.fillText(text, 
                    (gameCanvas.getWidth() - textWidth) / 2, 
                    gameCanvas.getHeight() / 2);
        
        // Instructions
        gc.setFont(Font.font("Arial", 14));
        gc.fillText("Press PAUSE to resume", 
                    gameCanvas.getWidth() / 2 - 70, 
                    gameCanvas.getHeight() / 2 + 30);
    }
    
    /**
     * Renders a countdown before game start.
     * 
     * @param count the countdown number to display
     */
    public void renderCountdown(int count) {
        gc.setFill(Color.rgb(0, 0, 0, 0.3));
        gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", 72));
        String text = String.valueOf(count);
        gc.fillText(text, 
                    gameCanvas.getWidth() / 2 - 20, 
                    gameCanvas.getHeight() / 2);
    }
    
    /**
     * Renders score update animation.
     * 
     * @param score the score to display
     * @param x x position
     * @param y y position
     */
    public void renderScoreAnimation(int score, double x, double y) {
        gc.setFill(Color.YELLOW);
        gc.setFont(Font.font("Arial", 20));
        gc.fillText("+" + score, x, y);
    }
    
    /**
     * Gets the graphics context for direct rendering access.
     * 
     * @return the graphics context
     */
    public GraphicsContext getGraphicsContext() {
        return gc;
    }
    
    /**
     * Gets the canvas width.
     * 
     * @return canvas width
     */
    public double getCanvasWidth() {
        return gameCanvas.getWidth();
    }
    
    /**
     * Gets the canvas height.
     * 
     * @return canvas height
     */
    public double getCanvasHeight() {
        return gameCanvas.getHeight();
    }
} 