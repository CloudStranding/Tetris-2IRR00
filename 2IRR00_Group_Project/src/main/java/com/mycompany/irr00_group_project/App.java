package com.mycompany.irr00_group_project;

import com.mycompany.irr00_group_project.game.TetrisGame;
import com.mycompany.irr00_group_project.ui.GameBoard;
import com.mycompany.irr00_group_project.ui.TitleScreen;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX Tetris App with styled sidebar, transitions, and persistent controls
 */
public class App extends Application {
    private static final int GRID_WIDTH = 10;
    private static final int GRID_HEIGHT = 20;
    private static final int CELL_SIZE = 30;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tetris JavaFX");

        // Create title screen
        TitleScreen titleScreen = new TitleScreen(
            GRID_WIDTH * CELL_SIZE + 200,
            GRID_HEIGHT * CELL_SIZE + 50
        );

        // Create game board with your friend's game implementation
        TetrisGame game = createGame(); // Your friend will implement this
        GameBoard gameBoard = new GameBoard(game);

        // Set up scene transition
        titleScreen.getStartButton().setOnAction(e -> {
            titleScreen.getFadeOut().play();
            titleScreen.getFadeOut().setOnFinished(ev -> {
                primaryStage.setScene(gameBoard.getScene());
                gameBoard.start();
            });
        });

        primaryStage.setScene(titleScreen.getScene());
        primaryStage.show();
    }

    private TetrisGame createGame() {
        // This is where your friend will provide their game implementation
        // For now, return null to indicate it needs to be implemented
        return null;
    }

    public static void main(String[] args) {
        launch();
    }
}
