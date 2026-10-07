package com.mycompany.irr00_group_project;

import com.mycompany.irr00_group_project.GUI.ui.DifficultyScreen;
import com.mycompany.irr00_group_project.GUI.ui.GameScreen;
import com.mycompany.irr00_group_project.GUI.ui.InstructionScreen;
import com.mycompany.irr00_group_project.GUI.ui.MainMenuScreen;
import com.mycompany.irr00_group_project.gameAnimation.GameController;
import com.mycompany.irr00_group_project.gameAnimation.GameRenderer;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main Tetris game application using modularized GUI components.
 * Redesigned UI flow: Main Menu → Game Screen → Pause Overlay
 */
public class TetrisGameMain extends Application {
    
    private static final int GRID_WIDTH = 10;
    private static final int GRID_HEIGHT = 20;
    private static final int BLOCK_SIZE = 30;
    
    // Core components
    private EnhancedGameEngine gameEngine;
    private GameController gameController;
    private GameRenderer gameRenderer;
    
    // UI components (modularized)
    private Stage primaryStage;
    private MainMenuScreen mainMenuScreen;
    private GameScreen gameScreen;
    private DifficultyScreen difficultyScreen;
    private InstructionScreen instructionScreen;
    
    // Game state
    private int selectedDifficulty = 1;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
        // Initialize all UI screens
        initializeScreens();
        
        // Configure stage
        primaryStage.setTitle("Tetris - Team Project");
        primaryStage.setResizable(false);
        
        // Start with main menu
        showMainMenu();
        primaryStage.show();
    }
    
    /**
     * Initialize all UI screens and their event handlers.
     */
    private void initializeScreens() {
        // Create screens
        mainMenuScreen = new MainMenuScreen();
        difficultyScreen = new DifficultyScreen();
        instructionScreen = new InstructionScreen();
        
        // Set up main menu event handlers
        mainMenuScreen.getStartGameButton().setOnAction(e -> startGame());
        mainMenuScreen.getDifficultyButton().setOnAction(e -> showDifficultySelection());
        mainMenuScreen.getInstructionButton().setOnAction(e -> showInstructions());
        
        // Set up difficulty screen event handlers
        for (int i = 0; i < difficultyScreen.getDifficultyButtons().length; i++) {
            final int level = i + 1;
            difficultyScreen.getDifficultyButtons()[i].setOnAction(e -> {
                selectedDifficulty = level;
                difficultyScreen.setSelectedDifficulty(level);
                mainMenuScreen.updateDifficulty(level);
                showMainMenu();
            });
        }
        difficultyScreen.getBackButton().setOnAction(e -> showMainMenu());
        
        // Set up instruction screen event handlers
        instructionScreen.getBackButton().setOnAction(e -> showMainMenu());
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
            gameController.togglePause();
            gameScreen.showPauseOverlay();
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
            
            // Handle game over - show overlay instead of using GameRenderer
            if (gameController.isGameOver() && !gameScreen.isGameOverOverlayVisible()) {
                gameScreen.showGameOverOverlay();
            }
        });
    }
    
    /**
     * Start a new game.
     */
    private void startGame() {
        // Ensure difficulty is within valid range (1-3)
        selectedDifficulty = Math.max(1, Math.min(3, selectedDifficulty));
        
        // Initialize fresh game components
        initializeGameComponents();
        
        // Show game screen
        primaryStage.setScene(gameScreen.getScene());
        
        // Set difficulty (multiply by 2 for game engine scaling)
        gameController.setDifficulty(selectedDifficulty * 2);
        
        // Start the game
        gameController.startGame();
        
        // Focus on game for keyboard input
        gameScreen.getScene().getRoot().requestFocus();
        
        System.out.println("Game started with difficulty: " + selectedDifficulty + " (" + 
                          DifficultyScreen.getDifficultyName(selectedDifficulty) + ")");
    }
    
    /**
     * Show main menu.
     */
    private void showMainMenu() {
        mainMenuScreen.updateDifficulty(selectedDifficulty);
        primaryStage.setScene(mainMenuScreen.getScene());
        primaryStage.centerOnScreen();
    }
    
    /**
     * Show difficulty selection screen.
     */
    private void showDifficultySelection() {
        difficultyScreen.setSelectedDifficulty(selectedDifficulty);
        primaryStage.setScene(difficultyScreen.getScene());
    }
    
    /**
     * Show instructions screen.
     */
    private void showInstructions() {
        primaryStage.setScene(instructionScreen.getScene());
    }
    
    /**
     * Main method.
     */
    public static void main(String[] args) {
        launch(args);
    }
} 