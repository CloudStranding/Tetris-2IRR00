package com.mycompany.irr00_group_project;

import com.mycompany.irr00_group_project.gameanimation.GameController;
import com.mycompany.irr00_group_project.gameanimation.GameRenderer;
import com.mycompany.irr00_group_project.gui.DifficultyScreen;
import com.mycompany.irr00_group_project.gui.GameScreen;
import com.mycompany.irr00_group_project.scoresystem.ScoreManager;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main Tetris game application using modularized GUI components.
 * Redesigned UI flow: Main Menu → Game Screen → Pause Overlay.
 *
 */
public class TetrisGameMain extends Application implements ScreenManager.NavigationHandler {
    
    private static final int GRID_WIDTH = 10;
    private static final int GRID_HEIGHT = 20;
    private static final int BLOCK_SIZE = 30;
    
    // Core components
    private EnhancedGameEngine gameEngine;
    private GameController gameController;
    private GameRenderer gameRenderer;
    
    // Screen management
    private ScreenManager screenManager;
    private GameScreen gameScreen;
    
    // Game state
    private int selectedDifficulty = 1;
    private String currentUsername = "Player";
    
    @Override
    public void start(Stage primaryStage) {
        // Initialize screen manager
        screenManager = new ScreenManager(primaryStage);
        screenManager.setupEventHandlers(this);
        
        // Configure stage
        primaryStage.setTitle("Tetris - Team Project");
        primaryStage.setResizable(false);
        
        // Start with main menu
        showMainMenu();
        primaryStage.show();
    }
    
    /**
     * Initialize game components. Called fresh each time we start a game.
     */
    private void initializeGameComponents() {
        // Create new game engine instance to ensure fresh start
        gameEngine = new EnhancedGameEngine(GRID_WIDTH, GRID_HEIGHT, BLOCK_SIZE);
        
        // Create new game screen with fresh components
        gameScreen = new GameScreen(gameEngine, selectedDifficulty);
        
        // Create game controller with new engine and canvas
        gameController = new GameController(gameEngine, gameScreen.getGameCanvas());
        
        // Create game renderer
        gameRenderer = new GameRenderer(gameScreen.getGameCanvas());
        
        // Set up game screen event handlers
        gameScreen.getPauseButton().setOnAction(e -> {
            pauseGame();
        });
        
        // Set up pause overlay event handlers
        gameScreen.getPauseOverlay().getResumeButton().setOnAction(e -> {
            gameController.togglePause(); // Resume the game
            gameScreen.hidePauseOverlay();
            gameScreen.getScene().getRoot().requestFocus(); // Restore keyboard focus
        });
        
        gameScreen.getPauseOverlay().getRestartButton().setOnAction(e -> {
            gameController.stopGame();
            gameScreen.hidePauseOverlay();
            showMainMenu();
        });
        
        gameScreen.getPauseOverlay().getScoreboardButton().setOnAction(e -> {
            showScoreboardDialog();
        });
        
        // Set up game over overlay event handlers
        gameScreen.getGameOverOverlay().getRestartButton().setOnAction(e -> {
            gameController.stopGame();
            gameScreen.hideGameOverOverlay();
            showMainMenu();
        });
        
        // Set up render callback for game updates
        gameController.getGameLoop().setRenderCallback(() -> {
            gameScreen.renderGame();
            gameScreen.updateUI();
            
            // Handle game over - show overlay and save score
            if (gameController.isGameOver() && !gameScreen.isGameOverOverlayVisible()) {
                // Save the final score when game ends
                saveGameScore();
                gameScreen.showGameOverOverlay();
            }
        });
    }

    private void pauseGame() {
        gameController.togglePause();
        gameScreen.showPauseOverlay();
    }
    
    /**
     * Start a new game.
     */
    private void startGame() {
        // Ensure difficulty is within valid range (1-3)
        selectedDifficulty = Math.max(1, Math.min(3, selectedDifficulty));
        
        // Initialize fresh game components
        initializeGameComponents();
        
        // Set username for scoring
        gameEngine.setCurrentUsername(currentUsername);
        
        // Show game screen
        screenManager.showGameScreen(gameScreen);
        
        // Set difficulty (multiply by 2 for game engine scaling)
        gameController.setDifficulty(selectedDifficulty * 2);
        
        // Start the game
        gameController.startGame();
        
        // Focus on game for keyboard input
        gameScreen.getScene().getRoot().requestFocus();
        
        System.out.println("Game started with difficulty: " + selectedDifficulty 
            + " (" + DifficultyScreen.getDifficultyName(selectedDifficulty) + ")");
    }
    
    /**
     * Save the current game score.
     */
    private void saveGameScore() {
        if (gameEngine != null) {
            // Save the final score
            gameEngine.setCurrentUsername(currentUsername);
            gameEngine.getScoreManager().storeFinalScore(currentUsername);
            System.out.println("Game Over! Final score saved: " + currentUsername 
                + " - " + gameEngine.getScore());
        }
    }
    
    // NavigationHandler implementation
    @Override
    public void showMainMenu() {
        screenManager.showMainMenu(selectedDifficulty);
    }
    
    @Override
    public void showDifficultySelection() {
        screenManager.showDifficultySelection(selectedDifficulty);
    }
    
    @Override
    public void showInstructions() {
        screenManager.showInstructions();
    }
    
    @Override
    public void showUsernameScreen() {
        screenManager.showUsernameScreen();
    }
    
    @Override
    public void showScoreboardDialog() {
        if (gameEngine != null) {
            screenManager.showScoreboardDialog(gameEngine.getScoreManager());
        } else {
            // Show empty scoreboard when no game is running
            ScoreManager tempScoreManager = new ScoreManager();
            screenManager.showScoreboardDialog(tempScoreManager);
        }
    }
    
    @Override
    public void selectDifficulty(int level) {
        selectedDifficulty = level;
        screenManager.getDifficultyScreen().setSelectedDifficulty(level);
        screenManager.getMainMenuScreen().updateDifficulty(level);
        showMainMenu();
    }
    
    @Override
    public void startGameWithUsername(String username) {
        // Store username for scoreboard
        currentUsername = username;
        
        // Start the game
        startGame();
    }
} 