package com.mycompany.irr00_group_project.gui;

import com.mycompany.irr00_group_project.EnhancedGameEngine;
import com.mycompany.irr00_group_project.gamelogic.piece.Block;
import com.mycompany.irr00_group_project.gamelogic.piece.TetrisPiece;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * Game screen component.
 */
public class GameScreen {
    private static final String WARM_BACKGROUND = 
        "linear-gradient(to bottom, #3D2B7A, #5A3F7D, #3D2B7A)";
    
    private final StackPane gameRoot;
    private final Scene gameScene;
    private final PauseOverlay pauseOverlay;
    private final GameOverOverlay gameOverOverlay;
    private final GameUIManager uiManager;
    
    private EnhancedGameEngine gameEngine;
    
    /**
     * Creates game screen.
     * 
     * @param gameEngine game engine instance
     * @param difficulty game difficulty level
     */
    public GameScreen(EnhancedGameEngine gameEngine, int difficulty) {
        this.gameEngine = gameEngine;
        
        gameRoot = new StackPane();
        uiManager = new GameUIManager(gameEngine);
        
        pauseOverlay = new PauseOverlay();
        gameOverOverlay = new GameOverOverlay();
        
        gameRoot.getChildren().addAll(uiManager.getRoot(), pauseOverlay, gameOverOverlay);
        pauseOverlay.setVisible(false);
        gameOverOverlay.setVisible(false);
        
        gameScene = new Scene(gameRoot, 850, 750);
        setupKeyboardControls();
    }

    /**
     * Sets up keyboard controls.
     */
    private void setupKeyboardControls() {
        InputHandler.setup(gameScene, gameEngine, () -> {
            if (pauseOverlay.isVisible()) {
                hidePauseOverlay();
            } else {
                showPauseOverlay();
            }
        });
    }
    
    /**
     * Shows pause overlay.
     */
    public void showPauseOverlay() {
        pauseOverlay.setVisible(true);
        getPauseButton().setDisable(true);
        updateDebugInfo();
    }
    
    /**
     * Hides pause overlay.
     */
    public void hidePauseOverlay() {
        pauseOverlay.setVisible(false);
        getPauseButton().setDisable(false);
        updateDebugInfo();
    }
    
    /**
     * Shows game over overlay.
     */
    public void showGameOverOverlay() {
        gameOverOverlay.setVisible(true);
        getPauseButton().setDisable(true);
    }
    
    /**
     * Hides game over overlay.
     */
    public void hideGameOverOverlay() {
        gameOverOverlay.setVisible(false);
        getPauseButton().setDisable(false);
    }
    
    /**
     * Checks if game over overlay is visible.
     * 
     * @return true if visible
     */
    public boolean isGameOverOverlayVisible() {
        return gameOverOverlay.isVisible();
    }
    
    /**
     * Updates UI elements.
     */
    public void updateUI() {
        if (gameEngine != null) {
            uiManager.getInfoPanel().getScoreLabel().setText(
                String.valueOf(gameEngine.getScore()));
            updateNextPiecePreview();
            updateDebugInfo();
        }
    }
    
    /**
     * Updates debug info display.
     */
    private void updateDebugInfo() {
        try {
            boolean gameOver = gameEngine.isGameOver();
            
            if (gameOver) {
                uiManager.getStatusLabel().setText("GAME OVER");
                uiManager.getStatusLabel().setTextFill(Color.web("#F48982"));
            } else if (pauseOverlay.isVisible()) {
                uiManager.getStatusLabel().setText("PAUSED");
                uiManager.getStatusLabel().setTextFill(Color.web("#E6C068"));
            } else {
                uiManager.getStatusLabel().setText("PLAYING");
                uiManager.getStatusLabel().setTextFill(Color.web("#98D982"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Updates next piece preview.
     */
    private void updateNextPiecePreview() {
        Canvas nextPieceCanvas = uiManager.getInfoPanel().getNextPieceCanvas();
        GraphicsContext gc = nextPieceCanvas.getGraphicsContext2D();
        
        gc.setFill(Color.rgb(20, 20, 20));
        gc.fillRect(0, 0, nextPieceCanvas.getWidth(), nextPieceCanvas.getHeight());
        
        try {
            var nextPieceDrawable = gameEngine.getNextPiece();
            if (nextPieceDrawable instanceof TetrisPiece) {
                drawNextPiecePreview((TetrisPiece) nextPieceDrawable, nextPieceCanvas, gc);
            }
        } catch (Exception e) {
            gc.setFill(Color.GRAY);
            gc.fillRect(20, 20, 20, 20);
        }
    }
    
    private void drawNextPiecePreview(TetrisPiece nextPiece, Canvas canvas, GraphicsContext gc) {
        double canvasWidth = canvas.getWidth();
        double canvasHeight = canvas.getHeight();
        double previewBlockSize = 30 * 0.8;
        
        int[] bounds = calculatePieceBounds(nextPiece);
        int minX = bounds[0];
        int maxX = bounds[1];
        int minY = bounds[2];
        int maxY = bounds[3];
        
        int pieceWidth = maxX - minX + 1;
        int pieceHeight = maxY - minY + 1;
        double offsetX = (canvasWidth - pieceWidth * previewBlockSize) / 2;
        double offsetY = (canvasHeight - pieceHeight * previewBlockSize) / 2;
        
        for (Block block : nextPiece.getBlocks()) {
            drawBlockPreview(block, minX, minY, offsetX, offsetY, previewBlockSize, gc);
        }
    }
    
    private int[] calculatePieceBounds(TetrisPiece piece) {
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        
        for (Block block : piece.getBlocks()) {
            int x = block.getPos().x;
            int y = block.getPos().y;
            minX = Math.min(minX, x);
            maxX = Math.max(maxX, x);
            minY = Math.min(minY, y);
            maxY = Math.max(maxY, y);
        }
        
        return new int[]{minX, maxX, minY, maxY};
    }
    
    private void drawBlockPreview(Block block, int minX, int minY, 
                                 double offsetX, double offsetY, 
                                 double previewBlockSize, GraphicsContext gc) {
        double x = offsetX + (block.getPos().x - minX) * previewBlockSize;
        double y = offsetY + (block.getPos().y - minY) * previewBlockSize;
        
        Color blockColor = block.getColor();
        gc.setFill(blockColor);
        gc.fillRect(x, y, previewBlockSize, previewBlockSize);
        
        gc.setStroke(blockColor.brighter());
        gc.setLineWidth(1);
        gc.strokeRect(x, y, previewBlockSize, previewBlockSize);
        
        gc.setStroke(blockColor.darker());
        gc.setLineWidth(1);
        gc.strokeRect(x + 1, y + 1, previewBlockSize - 2, previewBlockSize - 2);
    }
    
    /**
     * Renders the game.
     */
    public void renderGame() {
        uiManager.getGameArea().renderGame();
    }
    
    // Getters
    public Scene getScene() { 
        return gameScene; 
    }

    public Button getPauseButton() { 
        return uiManager.getControls().getPauseButton(); 
    }

    public PauseOverlay getPauseOverlay() { 
        return pauseOverlay; 
    }

    public GameOverOverlay getGameOverOverlay() { 
        return gameOverOverlay; 
    }

    public Canvas getGameCanvas() { 
        return uiManager.getGameArea().getGameCanvas(); 
    }
}