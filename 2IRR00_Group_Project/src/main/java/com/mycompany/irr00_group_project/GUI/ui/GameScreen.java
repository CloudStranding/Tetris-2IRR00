package com.mycompany.irr00_group_project.GUI.ui;

import com.mycompany.irr00_group_project.EnhancedGameEngine;
import com.mycompany.irr00_group_project.gamelogic.piece.Block;
import com.mycompany.irr00_group_project.gamelogic.piece.TetrisPiece;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Game screen for the Tetris game.
 * Contains the game grid, score display, and pause functionality.
 */
public class GameScreen {
    
    private static final int GRID_WIDTH = 10;
    private static final int GRID_HEIGHT = 20;
    private static final int BLOCK_SIZE = 30;
    
    private final StackPane gameRoot;
    private final Scene gameScene;
    private final Canvas gameCanvas;
    private final Label scoreLabel;
    private final Label statusLabel;
    private final Canvas nextPieceCanvas;
    private final Button pauseButton;
    private final Label debugLabel;
    private final PauseOverlay pauseOverlay;
    private final GameOverOverlay gameOverOverlay;
    
    private EnhancedGameEngine gameEngine;
    private int selectedDifficulty;
    
    public GameScreen(EnhancedGameEngine gameEngine, int difficulty) {
        this.gameEngine = gameEngine;
        this.selectedDifficulty = difficulty;
        
        gameRoot = new StackPane();
        
        // Create game canvas
        gameCanvas = new Canvas(GRID_WIDTH * BLOCK_SIZE, GRID_HEIGHT * BLOCK_SIZE);
        initializeCanvas();
        
        // Create UI components
        scoreLabel = new Label("0");
        statusLabel = new Label("Playing");
        nextPieceCanvas = new Canvas(4 * BLOCK_SIZE, 4 * BLOCK_SIZE);
        pauseButton = createPauseButton();
        debugLabel = new Label("Debug: Ready");
        
        // Create overlays
        pauseOverlay = new PauseOverlay();
        gameOverOverlay = new GameOverOverlay();
        
        // Create main game UI
        BorderPane gameUI = createGameUI();
        
        // Add to stack pane (overlays on top)
        gameRoot.getChildren().addAll(gameUI, pauseOverlay, gameOverOverlay);
        pauseOverlay.setVisible(false);
        gameOverOverlay.setVisible(false);
        
        gameScene = new Scene(gameRoot, 850, 750);
        setupKeyboardControls();
    }
    
    private void initializeCanvas() {
        GraphicsContext gc = gameCanvas.getGraphicsContext2D();
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
    }
    
    private BorderPane createGameUI() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #0f3460, #16537e, #0f3460);");
        
        // Center: Game area with enhanced styling
        VBox gameArea = new VBox(10);
        gameArea.setAlignment(Pos.CENTER);
        gameArea.setStyle("-fx-background-color: rgba(0,0,0,0.3);" +
                         "-fx-background-radius: 20;" +
                         "-fx-border-color: rgba(100, 200, 255, 0.5);" +
                         "-fx-border-width: 2;" +
                         "-fx-border-radius: 20;" +
                         "-fx-padding: 15;");
        gameArea.getChildren().add(gameCanvas);
        root.setCenter(gameArea);
        
        // Left: Info panel
        VBox leftPanel = createInfoPanel();
        root.setLeft(leftPanel);
        
        // Right: Control panel
        VBox rightPanel = createControlPanel();
        root.setRight(rightPanel);
        
        // Top: Title and status
        HBox topBar = createTopBar();
        root.setTop(topBar);
        
        return root;
    }
    
    private HBox createTopBar() {
        HBox topBar = new HBox(40);
        topBar.setAlignment(Pos.CENTER);
        topBar.setPadding(new Insets(15));
        topBar.setStyle("-fx-background-color: rgba(15, 52, 96, 0.8);" +
                      "-fx-background-radius: 15;" +
                      "-fx-border-color: rgba(100, 200, 255, 0.5);" +
                      "-fx-border-width: 2;" +
                      "-fx-border-radius: 15;");
        
        Label titleLabel = new Label("TETRIS");
        titleLabel.setFont(Font.font("Arial Black", FontWeight.EXTRA_BOLD, 32));
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setStyle("-fx-background-color: linear-gradient(to bottom, #1a5490, #0d2a4a);" +
                          "-fx-background-radius: 12;" +
                          "-fx-border-color: #FFD700;" +
                          "-fx-border-width: 2;" +
                          "-fx-border-radius: 12;" +
                          "-fx-padding: 12;" +
                          "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 8, 0.6, 0, 4);");
        
        statusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        statusLabel.setTextFill(Color.LIGHTGREEN);
        
        topBar.getChildren().addAll(titleLabel, statusLabel);
        
        return topBar;
    }
    
    private VBox createInfoPanel() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(20));
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setStyle("-fx-background-color: rgba(15, 52, 96, 0.8);" +
                      "-fx-border-color: rgba(100, 200, 255, 0.5);" +
                      "-fx-border-width: 2;" +
                      "-fx-background-radius: 15;" +
                      "-fx-border-radius: 15;");
        panel.setPrefWidth(160);
        
        // Score display
        Label scoreTitleLabel = new Label("SCORE");
        scoreTitleLabel.setFont(Font.font("Arial Black", FontWeight.EXTRA_BOLD, 18));
        scoreTitleLabel.setTextFill(Color.WHITE);
        scoreTitleLabel.setStyle("-fx-background-color: linear-gradient(to bottom, #1a5490, #0d2a4a);" +
                               "-fx-background-radius: 10;" +
                               "-fx-border-color: #FFD700;" +
                               "-fx-border-width: 2;" +
                               "-fx-border-radius: 10;" +
                               "-fx-padding: 8;" +
                               "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 5, 0.5, 0, 3);");
        
        scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        scoreLabel.setTextFill(Color.YELLOW);
        scoreLabel.setStyle("-fx-background-color: rgba(0,0,0,0.3);" +
                          "-fx-background-radius: 10;" +
                          "-fx-padding: 10;");
        
        // Next piece display
        Label nextPieceLabel = new Label("NEXT");
        nextPieceLabel.setFont(Font.font("Arial Black", FontWeight.EXTRA_BOLD, 18));
        nextPieceLabel.setTextFill(Color.WHITE);
        nextPieceLabel.setStyle("-fx-background-color: linear-gradient(to bottom, #1a5490, #0d2a4a);" +
                              "-fx-background-radius: 10;" +
                              "-fx-border-color: #FFD700;" +
                              "-fx-border-width: 2;" +
                              "-fx-border-radius: 10;" +
                              "-fx-padding: 8;" +
                              "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 5, 0.5, 0, 3);");
        
        nextPieceCanvas.setStyle("-fx-background-color: rgba(0,0,0,0.5);" +
                               "-fx-background-radius: 10;" +
                               "-fx-border-color: rgba(255,255,255,0.3);" +
                               "-fx-border-width: 1;" +
                               "-fx-border-radius: 10;");
        
        panel.getChildren().addAll(scoreTitleLabel, scoreLabel, 
                                  new Label(""), // Spacer
                                  nextPieceLabel, nextPieceCanvas);
        
        return panel;
    }
    
    private VBox createControlPanel() {
        VBox panel = new VBox(25);
        panel.setPadding(new Insets(20));
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setStyle("-fx-background-color: rgba(15, 52, 96, 0.8);" +
                      "-fx-border-color: rgba(100, 200, 255, 0.5);" +
                      "-fx-border-width: 2;" +
                      "-fx-background-radius: 15;" +
                      "-fx-border-radius: 15;");
        panel.setPrefWidth(180);
        
        // Instructions
        Label instructionsLabel = new Label("CONTROLS");
        instructionsLabel.setFont(Font.font("Arial Black", FontWeight.EXTRA_BOLD, 14));
        instructionsLabel.setTextFill(Color.WHITE);
        instructionsLabel.setStyle("-fx-background-color: linear-gradient(to bottom, #1a5490, #0d2a4a);" +
                                 "-fx-background-radius: 10;" +
                                 "-fx-border-color: #FFD700;" +
                                 "-fx-border-width: 2;" +
                                 "-fx-border-radius: 10;" +
                                 "-fx-padding: 8;" +
                                 "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 5, 0.5, 0, 3);");
        
        VBox keyContainer = new VBox(8);
        keyContainer.setAlignment(Pos.CENTER);
        keyContainer.setStyle("-fx-background-color: rgba(0,0,0,0.3);" +
                            "-fx-background-radius: 10;" +
                            "-fx-padding: 15;");
        
        GridPane keyGrid = new GridPane();
        keyGrid.setHgap(10);
        keyGrid.setVgap(8);
        keyGrid.setAlignment(Pos.CENTER);
        keyGrid.add(createKeyLabel("←→"), 0, 0);
        keyGrid.add(createKeyLabel("Move"), 1, 0);
        keyGrid.add(createKeyLabel("↑"), 0, 1);
        keyGrid.add(createKeyLabel("Rotate"), 1, 1);
        keyGrid.add(createKeyLabel("↓"), 0, 2);
        keyGrid.add(createKeyLabel("Drop"), 1, 2);
        
        keyContainer.getChildren().add(keyGrid);
        
        panel.getChildren().addAll(
            pauseButton,
            new Label(""), // Spacer
            instructionsLabel, 
            keyContainer
        );
        
        return panel;
    }
    
    private Button createPauseButton() {
        Button button = new Button("PAUSE");
        button.setPrefWidth(130);
        button.setPrefHeight(50);
        button.setFont(Font.font("Arial Black", FontWeight.EXTRA_BOLD, 14));
        button.setStyle("-fx-background-color: linear-gradient(to bottom, #FF6B35, #D84315);" +
                      "-fx-text-fill: white;" +
                      "-fx-background-radius: 15;" +
                      "-fx-border-radius: 15;" +
                      "-fx-border-color: #FF8A65;" +
                      "-fx-border-width: 3;" +
                      "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 8, 0.6, 0, 4);");
        button.setFocusTraversable(false);
        
        // Enhanced hover effect
        button.setOnMouseEntered(e -> {
            button.setStyle("-fx-background-color: linear-gradient(to bottom, #FF8A65, #FF6B35);" +
                          "-fx-text-fill: white;" +
                          "-fx-background-radius: 15;" +
                          "-fx-border-radius: 15;" +
                          "-fx-border-color: #FF8A65;" +
                          "-fx-border-width: 3;" +
                          "-fx-effect: dropshadow(gaussian, rgba(255,107,53,0.8), 12, 0.8, 0, 6);" +
                          "-fx-scale-x: 1.05;" +
                          "-fx-scale-y: 1.05;");
        });
        
        button.setOnMouseExited(e -> {
            button.setStyle("-fx-background-color: linear-gradient(to bottom, #FF6B35, #D84315);" +
                          "-fx-text-fill: white;" +
                          "-fx-background-radius: 15;" +
                          "-fx-border-radius: 15;" +
                          "-fx-border-color: #FF8A65;" +
                          "-fx-border-width: 3;" +
                          "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 8, 0.6, 0, 4);" +
                          "-fx-scale-x: 1.0;" +
                          "-fx-scale-y: 1.0;");
        });
        
        return button;
    }
    
    private Label createKeyLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        label.setTextFill(Color.LIGHTCYAN);
        return label;
    }
    
    private void setupKeyboardControls() {
        gameScene.setOnKeyPressed(event -> {
            if (gameEngine != null && !pauseOverlay.isVisible() && !gameOverOverlay.isVisible()) {
                gameEngine.handle(event);
            }
        });
    }
    
    public void showPauseOverlay() {
        pauseOverlay.setVisible(true);
        pauseButton.setDisable(true); // Disable pause button when overlay is shown
        updateDebugInfo(); // Update status display immediately
    }
    
    public void hidePauseOverlay() {
        pauseOverlay.setVisible(false);
        pauseButton.setDisable(false); // Re-enable pause button
        updateDebugInfo(); // Update status display immediately
    }
    
    public void showGameOverOverlay() {
        gameOverOverlay.setVisible(true);
        pauseButton.setDisable(true); // Disable pause button when game is over
    }
    
    public void hideGameOverOverlay() {
        gameOverOverlay.setVisible(false);
        pauseButton.setDisable(false); // Re-enable pause button
    }
    
    public boolean isGameOverOverlayVisible() {
        return gameOverOverlay.isVisible();
    }
    
    public void updateUI() {
        if (gameEngine != null) {
            scoreLabel.setText(String.valueOf(gameEngine.getScore()));
            updateNextPiecePreview();
            updateDebugInfo();
        }
    }
    
    private void updateDebugInfo() {
        try {
            int pieceCount = gameEngine.getGrid().size();
            boolean gameOver = gameEngine.isGameOver();
            
            if (gameOver) {
                statusLabel.setText("GAME OVER");
                statusLabel.setTextFill(Color.RED);
            } else if (pauseOverlay.isVisible()) {
                statusLabel.setText("PAUSED");
                statusLabel.setTextFill(Color.ORANGE);
            } else {
                statusLabel.setText("PLAYING");
                statusLabel.setTextFill(Color.LIGHTGREEN);
            }
        } catch (Exception e) {
            // Remove debug label to clean up the interface
            // debugLabel.setText("Debug: Error - " + e.getMessage());
        }
    }
    
    private void updateNextPiecePreview() {
        GraphicsContext gc = nextPieceCanvas.getGraphicsContext2D();
        
        // Clear canvas
        gc.setFill(Color.rgb(20, 20, 20));
        gc.fillRect(0, 0, nextPieceCanvas.getWidth(), nextPieceCanvas.getHeight());
        
        try {
            var nextPieceDrawable = gameEngine.getNextPiece();
            if (nextPieceDrawable instanceof TetrisPiece) {
                TetrisPiece nextPiece = (TetrisPiece) nextPieceDrawable;
                
                // Calculate center position for preview
                double canvasWidth = nextPieceCanvas.getWidth();
                double canvasHeight = nextPieceCanvas.getHeight();
                double previewBlockSize = BLOCK_SIZE * 0.8;
                
                // Find bounds
                int minX = Integer.MAX_VALUE, maxX = Integer.MIN_VALUE;
                int minY = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE;
                
                for (Block block : nextPiece.getBlocks()) {
                    int x = block.getPos().x;
                    int y = block.getPos().y;
                    minX = Math.min(minX, x);
                    maxX = Math.max(maxX, x);
                    minY = Math.min(minY, y);
                    maxY = Math.max(maxY, y);
                }
                
                // Calculate piece dimensions and offset
                int pieceWidth = maxX - minX + 1;
                int pieceHeight = maxY - minY + 1;
                double offsetX = (canvasWidth - pieceWidth * previewBlockSize) / 2;
                double offsetY = (canvasHeight - pieceHeight * previewBlockSize) / 2;
                
                // Draw each block
                for (Block block : nextPiece.getBlocks()) {
                    double x = offsetX + (block.getPos().x - minX) * previewBlockSize;
                    double y = offsetY + (block.getPos().y - minY) * previewBlockSize;
                    
                    // Use the actual color of the block
                    Color blockColor = block.getColor();
                    
                    // Fill the block
                    gc.setFill(blockColor);
                    gc.fillRect(x, y, previewBlockSize, previewBlockSize);
                    
                    // Add border for better visibility
                    gc.setStroke(blockColor.brighter());
                    gc.setLineWidth(1);
                    gc.strokeRect(x, y, previewBlockSize, previewBlockSize);
                    
                    // Add inner border for 3D effect
                    gc.setStroke(blockColor.darker());
                    gc.setLineWidth(1);
                    gc.strokeRect(x + 1, y + 1, previewBlockSize - 2, previewBlockSize - 2);
                }
            }
        } catch (Exception e) {
            // Fallback placeholder
            gc.setFill(Color.GRAY);
            gc.fillRect(20, 20, 20, 20);
        }
    }
    
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
        try {
            gameEngine.getGrid().forEach(drawable -> {
                if (drawable != null) {
                    drawable.draw(gc);
                }
            });
        } catch (Exception e) {
            System.err.println("Error drawing game pieces: " + e.getMessage());
        }
        
        // Draw inner border to avoid blocking game content
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1);
        gc.strokeRect(1, 1, gameCanvas.getWidth() - 2, gameCanvas.getHeight() - 2);
    }
    
    // Getters
    public Scene getScene() { return gameScene; }
    public Button getPauseButton() { return pauseButton; }
    public PauseOverlay getPauseOverlay() { return pauseOverlay; }
    public GameOverOverlay getGameOverOverlay() { return gameOverOverlay; }
    public Canvas getGameCanvas() { return gameCanvas; }
} 