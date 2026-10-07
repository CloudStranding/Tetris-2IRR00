package com.mycompany.irr00_group_project.gameAnimation;

import com.mycompany.irr00_group_project.EnhancedGameEngine;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Example integration class showing how to use the gameAnimation module
 * with the existing GUI and game logic components.
 * 
 * @author Koray
 */
public class TetrisGameIntegration extends Application {
    
    private static final int GRID_WIDTH = 10;
    private static final int GRID_HEIGHT = 20;
    private static final int BLOCK_SIZE = 30;
    
    private GameController gameController;
    private EnhancedGameEngine gameEngine;
    private Canvas gameCanvas;
    private Label scoreLabel;
    private Label statusLabel;
    
    @Override
    public void start(Stage primaryStage) {
        // Create the enhanced game engine
        gameEngine = new EnhancedGameEngine(GRID_WIDTH, GRID_HEIGHT, BLOCK_SIZE);
        
        // Create the game canvas
        gameCanvas = new Canvas(GRID_WIDTH * BLOCK_SIZE, GRID_HEIGHT * BLOCK_SIZE);
        gameCanvas.setStyle("-fx-background-color: black;");
        
        // Create the game controller
        gameController = new GameController(gameEngine, gameCanvas);
        
        // Set up the UI
        BorderPane root = setupUI();
        
        // Create and configure the scene
        Scene scene = new Scene(root, 600, 700);
        scene.setOnKeyPressed(event -> gameController.handleKeyPress(event));
        scene.setOnKeyReleased(event -> gameController.handleKeyRelease(event));
        
        // Set up the stage
        primaryStage.setTitle("Tetris - Game Animation Integration");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
        // Focus on scene root for keyboard input
        scene.getRoot().requestFocus();
    }
    
    /**
     * Sets up the user interface.
     * 
     * @return the configured root pane
     */
    private BorderPane setupUI() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: #2b2b2b;");
        
        // Center: Game Canvas
        root.setCenter(gameCanvas);
        
        // Top: Status bar
        HBox topBar = createTopBar();
        root.setTop(topBar);
        
        // Bottom: Control buttons
        HBox controlBar = createControlBar();
        root.setBottom(controlBar);
        
        return root;
    }
    
    /**
     * Creates the top status bar.
     * 
     * @return the configured top bar
     */
    private HBox createTopBar() {
        HBox topBar = new HBox(20);
        topBar.setAlignment(Pos.CENTER);
        topBar.setPadding(new Insets(10));
        topBar.setStyle("-fx-background-color: #1a1a1a;");
        
        scoreLabel = new Label("Score: 0");
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setFont(Font.font("Arial", 18));
        
        statusLabel = new Label("Press Start to Play");
        statusLabel.setTextFill(Color.LIGHTGREEN);
        statusLabel.setFont(Font.font("Arial", 16));
        
        topBar.getChildren().addAll(scoreLabel, statusLabel);
        
        return topBar;
    }
    
    /**
     * Creates the control button bar.
     * 
     * @return the configured control bar
     */
    private HBox createControlBar() {
        HBox controlBar = new HBox(10);
        controlBar.setAlignment(Pos.CENTER);
        controlBar.setPadding(new Insets(10));
        
        Button startButton = createStyledButton("Start", "#27ae60");
        Button pauseButton = createStyledButton("Pause", "#f39c12");
        Button restartButton = createStyledButton("Restart", "#e74c3c");
        
        // Button actions
        startButton.setOnAction(e -> {
            gameController.startGame();
            statusLabel.setText("Playing");
            updateScoreDisplay();
        });
        
        pauseButton.setOnAction(e -> {
            gameController.togglePause();
            statusLabel.setText(gameController.isGamePaused() ? "Paused" : "Playing");
        });
        
        restartButton.setOnAction(e -> {
            gameController.restartGame();
            statusLabel.setText("Playing");
            updateScoreDisplay();
        });
        
        // Difficulty buttons
        VBox difficultyBox = new VBox(5);
        difficultyBox.setAlignment(Pos.CENTER);
        Label diffLabel = new Label("Difficulty:");
        diffLabel.setTextFill(Color.WHITE);
        
        HBox diffButtons = new HBox(5);
        for (int i = 1; i <= 5; i++) {
            final int level = i;
            Button diffButton = createStyledButton(String.valueOf(i), "#3498db");
            diffButton.setPrefWidth(30);
            diffButton.setOnAction(e -> {
                gameController.setDifficulty(level);
            });
            diffButtons.getChildren().add(diffButton);
        }
        
        difficultyBox.getChildren().addAll(diffLabel, diffButtons);
        
        controlBar.getChildren().addAll(startButton, pauseButton, restartButton, difficultyBox);
        
        return controlBar;
    }
    
    /**
     * Updates the score display.
     */
    private void updateScoreDisplay() {
        if (gameEngine != null) {
            scoreLabel.setText("Score: " + gameEngine.getScore());
        }
    }
    
    /**
     * Creates a styled button.
     * 
     * @param text the button text
     * @param color the button color
     * @return the styled button
     */
    private Button createStyledButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle(
            "-fx-background-color: " + color + ";" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 14px;" +
            "-fx-padding: 8 16 8 16;" +
            "-fx-cursor: hand;"
        );
        
        // Hover effect
        button.setOnMouseEntered(e -> 
            button.setStyle(button.getStyle() + "-fx-opacity: 0.8;")
        );
        button.setOnMouseExited(e -> 
            button.setStyle(button.getStyle().replace("-fx-opacity: 0.8;", ""))
        );
        
        return button;
    }
    
    /**
     * Main method to launch the application.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
} 