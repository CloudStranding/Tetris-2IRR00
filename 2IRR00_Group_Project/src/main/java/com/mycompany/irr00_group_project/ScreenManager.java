package com.mycompany.irr00_group_project;

import com.mycompany.irr00_group_project.gui.menu.DifficultyScreen;
import com.mycompany.irr00_group_project.gui.game.GameScreen;
import com.mycompany.irr00_group_project.gui.menu.InstructionScreen;
import com.mycompany.irr00_group_project.gui.menu.MainMenuScreen;
import com.mycompany.irr00_group_project.gui.menu.ScoreBoard;
import com.mycompany.irr00_group_project.gui.menu.UsernameScreen;
import com.mycompany.irr00_group_project.gamelogic.ScoreManager;

import javafx.stage.Stage;

/**
 * Screen manager to handle UI screen creation and navigation.
 * Reduces coupling in TetrisGameMain by centralizing screen management.
 */
public class ScreenManager {

    // UI components (modularized)
    private MainMenuScreen mainMenuScreen;
    private DifficultyScreen difficultyScreen;
    private InstructionScreen instructionScreen;
    private UsernameScreen usernameScreen;
    private ScoreBoard scoreBoard;

    private final Stage primaryStage;

    /**
     * Creates a new ScreenManager.
     *
     * @param primaryStage the primary stage for the application
     */
    public ScreenManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initializeScreens();
    }

    /**
     * Initialize all UI screens.
     */
    private void initializeScreens() {
        // Create screens
        mainMenuScreen = new MainMenuScreen();
        difficultyScreen = new DifficultyScreen();
        instructionScreen = new InstructionScreen();
        usernameScreen = new UsernameScreen();
        scoreBoard = new ScoreBoard();
    }

    /**
     * Sets up event handlers for all screens.
     *
     * @param navigationHandler the navigation handler for screen transitions
     */
    public void setupEventHandlers(NavigationHandler navigationHandler) {
        // Set up main menu event handlers
        mainMenuScreen.getStartGameButton().setOnAction(e ->
                navigationHandler.showUsernameScreen());
        mainMenuScreen.getDifficultyButton().setOnAction(e ->
                navigationHandler.showDifficultySelection());
        mainMenuScreen.getInstructionButton().setOnAction(e ->
                navigationHandler.showInstructions());
        mainMenuScreen.getScoreboardButton().setOnAction(e ->
                navigationHandler.showScoreboardDialog());

        // Set up difficulty screen event handlers
        for (int i = 0; i < difficultyScreen.getDifficultyButtons().length; i++) {
            final int level = i + 1;
            difficultyScreen.getDifficultyButtons()[i].setOnAction(e -> {
                navigationHandler.selectDifficulty(level);
            });
        }
        difficultyScreen.getBackButton().setOnAction(e ->
                navigationHandler.showMainMenu());

        // Set up instruction screen event handlers
        instructionScreen.getBackButton().setOnAction(e ->
                navigationHandler.showMainMenu());

        // Set up username screen event handlers
        usernameScreen.getStartButton().setOnAction(e -> {
            if (usernameScreen.validateUsername()) {
                String username = usernameScreen.getUsername();
                navigationHandler.startGameWithUsername(username);
            }
        });
        usernameScreen.getBackButton().setOnAction(e ->
                navigationHandler.showMainMenu());

        // Set up scoreboard event handlers
        scoreBoard.getCloseButton().setOnAction(e -> scoreBoard.hide());
    }

    /**
     * Shows the main menu screen.
     *
     * @param selectedDifficulty the currently selected difficulty
     */
    public void showMainMenu(int selectedDifficulty) {
        mainMenuScreen.updateDifficulty(selectedDifficulty);
        primaryStage.setScene(mainMenuScreen.getScene());
        primaryStage.centerOnScreen();
    }

    /**
     * Shows the difficulty selection screen.
     *
     * @param selectedDifficulty the currently selected difficulty
     */
    public void showDifficultySelection(int selectedDifficulty) {
        difficultyScreen.setSelectedDifficulty(selectedDifficulty);
        primaryStage.setScene(difficultyScreen.getScene());
    }

    /**
     * Shows the instructions screen.
     */
    public void showInstructions() {
        primaryStage.setScene(instructionScreen.getScene());
    }

    /**
     * Shows the username input screen.
     */
    public void showUsernameScreen() {
        usernameScreen.clearUsername();
        usernameScreen.focusUsernameField();
        primaryStage.setScene(usernameScreen.getScene());
    }

    /**
     * Shows the game screen.
     *
     * @param gameScreen the game screen to display
     */
    public void showGameScreen(GameScreen gameScreen) {
        primaryStage.setScene(gameScreen.getScene());
    }

    /**
     * Shows the scoreboard dialog.
     *
     * @param scoreManager the score manager to display scores from
     */
    public void showScoreboardDialog(ScoreManager scoreManager) {
        scoreBoard.updateScoreboard(scoreManager);
        scoreBoard.show();
    }

    /**
     * Gets the main menu screen.
     *
     * @return the main menu screen
     */
    public MainMenuScreen getMainMenuScreen() {
        return mainMenuScreen;
    }

    /**
     * Gets the difficulty screen.
     *
     * @return the difficulty screen
     */
    public DifficultyScreen getDifficultyScreen() {
        return difficultyScreen;
    }

    /**
     * Interface for handling navigation between screens.
     */
    public interface NavigationHandler {

        void showMainMenu();

        void showDifficultySelection();

        void showInstructions();

        void showUsernameScreen();

        void showScoreboardDialog();

        void selectDifficulty(int level);

        void startGameWithUsername(String username);
    }
} 