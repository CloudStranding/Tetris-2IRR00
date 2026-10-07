package com.mycompany.irr00_group_project.GUI.ui;

import com.mycompany.irr00_group_project.game.TetrisGame;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class GameBoard {
    private static final int GRID_WIDTH = 10;
    private static final int GRID_HEIGHT = 20;
    private static final int CELL_SIZE = 30;

    private final GridPane grid;
    private final Scene gameScene;
    private final Timeline updateTimeline;
    private final FadeTransition fadeIn;
    private final Label scoreLabel;
    private final Pane nextPreview;
    private final TetrisGame game;

    public GameBoard(TetrisGame game) {
        this.game = game;
        
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: #222;");

        // Initialize UI components
        scoreLabel = new Label("Score: 0");
        scoreLabel.setFont(Font.font(18));
        scoreLabel.setTextFill(Color.WHITE);

        nextPreview = new Pane();
        nextPreview.setPrefSize(CELL_SIZE * 4, CELL_SIZE * 4);
        nextPreview.setStyle("-fx-background-color: #333; -fx-border-color: #888; -fx-border-width: 2;");

        // Game grid
        grid = createGrid();
        root.setCenter(grid);

        // Sidebar
        VBox sidebar = createSidebar();
        root.setRight(sidebar);

        // Game scene
        gameScene = new Scene(root, GRID_WIDTH * CELL_SIZE + 200, GRID_HEIGHT * CELL_SIZE + 50);
        gameScene.setOnKeyPressed(this::handleKey);

        // Update timeline
        updateTimeline = new Timeline(new KeyFrame(Duration.millis(500), e -> update()));
        updateTimeline.setCycleCount(Timeline.INDEFINITE);

        // Fade transition
        fadeIn = new FadeTransition(Duration.millis(500), root);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
    }

    private GridPane createGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setStyle("-fx-background-color: #000; -fx-border-color: #555; -fx-border-width: 4;");
        
        for (int y = 0; y < GRID_HEIGHT; y++) {
            for (int x = 0; x < GRID_WIDTH; x++) {
                Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
                cell.setFill(Color.LIGHTGRAY);
                cell.setStroke(Color.GRAY);
                grid.add(cell, x, y);
            }
        }
        return grid;
    }

    private VBox createSidebar() {
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(10));
        sidebar.setAlignment(Pos.TOP_CENTER);

        Label nextLabel = new Label("Next:");
        nextLabel.setFont(Font.font(16));
        nextLabel.setTextFill(Color.WHITE);

        Label controlsLabel = new Label("Controls");
        controlsLabel.setFont(Font.font(16));
        controlsLabel.setTextFill(Color.WHITE);

        Button pauseBtn = createControlButton("Pause", "#f39c12");
        Button restartBtn = createControlButton("Restart", "#e74c3c");

        pauseBtn.setOnAction(e -> togglePause());
        restartBtn.setOnAction(e -> restartGame());

        HBox controlButtons = new HBox(10, pauseBtn, restartBtn);
        controlButtons.setAlignment(Pos.CENTER);
        sidebar.getChildren().addAll(scoreLabel, nextLabel, nextPreview, controlsLabel, controlButtons);
        return sidebar;
    }

    private Button createControlButton(String text, String color) {
        Button button = new Button(text);
        button.setFont(Font.font(14));
        button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white;");
        return button;
    }

    private void handleKey(KeyEvent event) {
        if (!game.isRunning()) return;
        game.handleKeyPress(event.getCode());
    }

    private void update() {
        if (!game.isRunning()) return;
        game.update();
        updateUI();
    }

    private void updateUI() {
        // Update score
        scoreLabel.setText("Score: " + game.getScore());

        // Update grid
        int[][] currentGrid = game.getCurrentGrid();
        for (int y = 0; y < GRID_HEIGHT; y++) {
            for (int x = 0; x < GRID_WIDTH; x++) {
                Rectangle cell = (Rectangle) grid.getChildren().get(y * GRID_WIDTH + x);
                cell.setFill(getColorForValue(currentGrid[y][x]));
            }
        }

        // Update next piece preview
        updateNextPiecePreview();
    }

    private void updateNextPiecePreview() {
        nextPreview.getChildren().clear();
        int[][] nextPiece = game.getNextPiece();
        if (nextPiece != null) {
            for (int y = 0; y < nextPiece.length; y++) {
                for (int x = 0; x < nextPiece[y].length; x++) {
                    if (nextPiece[y][x] != 0) {
                        Rectangle cell = new Rectangle(CELL_SIZE, CELL_SIZE);
                        cell.setFill(getColorForValue(nextPiece[y][x]));
                        cell.setStroke(Color.GRAY);
                        cell.setX(x * CELL_SIZE);
                        cell.setY(y * CELL_SIZE);
                        nextPreview.getChildren().add(cell);
                    }
                }
            }
        }
    }

    private Color getColorForValue(int value) {
        // This can be customized based on your color scheme
        switch (value) {
            case 0: return Color.LIGHTGRAY;
            case 1: return Color.CYAN;
            case 2: return Color.BLUE;
            case 3: return Color.ORANGE;
            case 4: return Color.YELLOW;
            case 5: return Color.GREEN;
            case 6: return Color.PURPLE;
            case 7: return Color.RED;
            default: return Color.LIGHTGRAY;
        }
    }

    private void togglePause() {
        if (!game.isRunning()) {
            game.resume();
            updateTimeline.play();
        } else {
            game.pause();
            updateTimeline.pause();
        }
    }

    private void restartGame() {
        game.restart();
        updateUI();
    }

    public Scene getScene() {
        return gameScene;
    }

    public void start() {
        game.start();
        updateTimeline.play();
        fadeIn.play();
    }

    public void stop() {
        updateTimeline.stop();
    }
} 