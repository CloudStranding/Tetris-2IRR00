package com.mycompany.irr00_group_project.gui;

import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

/**
 * Represents the title screen of the game.
 * Displays the game title and animations.
 *
 * @author Jayson Leander, Yingyao Feng
 */
public class TitleScreen {
    private final VBox titleLayout;
    private final Scene titleScene;
    private final FadeTransition fadeOut;
    private final FadeTransition fadeIn;
    private final Button startBtn;

    /**
     * Creates the title screen with animations and styling.
     */
    public TitleScreen(int width, int height) {
        titleLayout = new VBox(20);
        titleLayout.setAlignment(Pos.CENTER);
        titleLayout.setPadding(new Insets(20));
        titleLayout.setStyle("-fx-background-color: linear-gradient(to bottom, #1e5799, #7db9e8);"
                          + "-fx-background-image: url('background.jpg');"
                          + "-fx-background-size: cover;");

        Label title = new Label("TETRIS");
        title.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 72));
        title.setTextFill(Color.WHITE);

        startBtn = createStartButton();
        titleLayout.getChildren().addAll(title, startBtn);
        titleScene = new Scene(titleLayout, width, height);

        // Fade transitions
        fadeOut = new FadeTransition(Duration.millis(500), titleLayout);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeIn = new FadeTransition(Duration.millis(500));
    }

    private Button createStartButton() {
        Button button = new Button("Start Game");
        button.setFont(Font.font(20));
        button.setStyle(
                        "-fx-background-radius: 10; "
                                + "-fx-background-color: #2ecc71; -fx-text-fill: white;"
                      + "-fx-padding: 10 20 10 20; -fx-cursor: hand;"
                      + "-fx-effect: dropshadow(one-pass-box, rgba(0,0,0,0.4), 5, 0, 2, 2);");
        
        button.setOnMouseEntered(e -> button.setStyle(
                        "-fx-background-radius: 10; "
                                + "-fx-background-color: #27ae60; -fx-text-fill: white;"
                      + "-fx-padding: 10 20 10 20; -fx-cursor: hand;"
                      + "-fx-effect: dropshadow(one-pass-box, rgba(0,0,0,0.4), 5, 0, 2, 2);"));
        
        button.setOnMouseExited(e -> button.setStyle(
                        "-fx-background-radius: 10;"
                                + " -fx-background-color: #2ecc71; -fx-text-fill: white;"
                      + "-fx-padding: 10 20 10 20; -fx-cursor: hand;"
                      + "-fx-effect: dropshadow(one-pass-box, rgba(0,0,0,0.4), 5, 0, 2, 2);"));
        
        return button;
    }

    public Scene getScene() {
        return titleScene;
    }

    public Button getStartButton() {
        return startBtn;
    }

    public FadeTransition getFadeOut() {
        return fadeOut;
    }

    public FadeTransition getFadeIn() {
        return fadeIn;
    }
} 