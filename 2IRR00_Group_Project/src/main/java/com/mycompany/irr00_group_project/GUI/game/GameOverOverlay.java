package com.mycompany.irr00_group_project.gui.game;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import static com.mycompany.irr00_group_project.gui.ColorUtil.adjustBrightness;
import static com.mycompany.irr00_group_project.gui.ColorUtil.hexToRgb;

/**
 * Game over overlay for the Tetris game.
 * Contains GAME OVER message and QUIT button.
 */
public class GameOverOverlay extends VBox {

    // Unified warm color scheme - matching other screens
    private static final String SUCCESS_COLOR = "#5C8A58"; // Soft green
    private static final String DANGER_COLOR = "#B85450"; // Soft red
    private static final String WARM_BACKGROUND =
            "linear-gradient(to bottom, #2D1B69, #4A2C6A, #2D1B69)";

    private final Button quitButton;

    /**
     * Creates a new game over overlay.
     */
    public GameOverOverlay() {
        super(30);
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: " + WARM_BACKGROUND + ";"
                + "-fx-background-radius: 15;"
                + "-fx-border-color: rgba(180, 84, 80, 0.4);"
                + "-fx-border-width: 2;"
                + "-fx-border-radius: 15;"
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 18, 0.8, 0, 10);");
        setPrefSize(380, 280);
        setMaxSize(380, 280);

        Label gameOverTitle = new Label("GAME OVER");
        gameOverTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        gameOverTitle.setTextFill(Color.WHITE);
        gameOverTitle.setStyle("-fx-background-color: linear-gradient(to bottom, "
                + DANGER_COLOR + ", " + adjustBrightness(DANGER_COLOR, 0.8) + ");"
                + "-fx-background-radius: 12;"
                + "-fx-border-color: " + adjustBrightness(DANGER_COLOR, 1.2) + ";"
                + "-fx-border-width: 2;"
                + "-fx-border-radius: 12;"
                + "-fx-padding: 15;"
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 8, 0.6, 0, 4);");

        Label messageLabel = new Label("Don't give up! Try again!");
        messageLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 16));
        messageLabel.setTextFill(Color.web("#F0E6F7"));
        messageLabel.setStyle("-fx-padding: 10;");

        quitButton = createGameOverButton();

        getChildren().addAll(gameOverTitle, messageLabel, quitButton);
    }

    /**
     * Creates a button for the game over overlay.
     *
     * @return The created button
     */
    private Button createGameOverButton() {
        Button button = new Button("QUIT");
        button.setPrefWidth(200);
        button.setPrefHeight(55);
        button.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));

        String lighterColor = adjustBrightness(GameOverOverlay.SUCCESS_COLOR, 1.2);
        String darkerColor = adjustBrightness(GameOverOverlay.SUCCESS_COLOR, 0.8);

        button.setStyle(String.format(
                "-fx-background-color: linear-gradient(to bottom, %s, %s);"
                        + "-fx-text-fill: white;"
                        + "-fx-background-radius: 12;"
                        + "-fx-border-radius: 12;"
                        + "-fx-border-color: %s;"
                        + "-fx-border-width: 2;"
                        + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 8, 0.6, 0, 4);",
                lighterColor, darkerColor, lighterColor
        ));

        // Refined hover effects
        button.setOnMouseEntered(e -> {
            button.setStyle(String.format(
                    "-fx-background-color: linear-gradient(to bottom, %s, %s);"
                            + "-fx-text-fill: white;"
                            + "-fx-background-radius: 12;"
                            + "-fx-border-radius: 12;"
                            + "-fx-border-color: %s;"
                            + "-fx-border-width: 2;"
                            + "-fx-effect: dropshadow(gaussian, rgba(%s, 0.6), 12, 0.8, 0, 6);"
                            + "-fx-scale-x: 1.05;"
                            + "-fx-scale-y: 1.05;",
                    adjustBrightness(GameOverOverlay.SUCCESS_COLOR, 1.3),
                    GameOverOverlay.SUCCESS_COLOR,
                    lighterColor,
                    hexToRgb(GameOverOverlay.SUCCESS_COLOR)
            ));
        });

        button.setOnMouseExited(e -> {
            button.setStyle(String.format(
                    "-fx-background-color: linear-gradient(to bottom, %s, %s);"
                            + "-fx-text-fill: white;"
                            + "-fx-background-radius: 12;"
                            + "-fx-border-radius: 12;"
                            + "-fx-border-color: %s;"
                            + "-fx-border-width: 2;"
                            + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 8, 0.6, 0, 4);"
                            + "-fx-scale-x: 1.0;"
                            + "-fx-scale-y: 1.0;",
                    lighterColor, darkerColor, lighterColor
            ));
        });

        return button;
    }

    /**
     * Returns the quit button.
     *
     * @return The quit button
     */
    public Button getQuitButton() {
        return quitButton;
    }
} 