package com.mycompany.irr00_group_project.GUI.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Pause overlay for the Tetris game.
 * Contains RESUME and RESTART buttons.
 */
public class PauseOverlay extends VBox {
    
    private final Button resumeButton;
    private final Button restartButton;
    
    public PauseOverlay() {
        super(35);
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: rgba(15, 52, 96, 0.95);" +
                "-fx-background-radius: 20;" +
                "-fx-border-color: rgba(100, 200, 255, 0.7);" +
                "-fx-border-width: 3;" +
                "-fx-border-radius: 20;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 20, 0.8, 0, 10);");
        setPrefSize(400, 300);
        setMaxSize(400, 300);
        
        Label pauseTitle = new Label("GAME PAUSED");
        pauseTitle.setFont(Font.font("Arial Black", FontWeight.EXTRA_BOLD, 26));
        pauseTitle.setTextFill(Color.WHITE);
        pauseTitle.setStyle("-fx-background-color: linear-gradient(to bottom, #1a5490, #0d2a4a);" +
                          "-fx-background-radius: 12;" +
                          "-fx-border-color: #FFD700;" +
                          "-fx-border-width: 3;" +
                          "-fx-border-radius: 12;" +
                          "-fx-padding: 15;" +
                          "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 8, 0.6, 0, 4);");
        
        resumeButton = createPauseButton("RESUME", Color.LIMEGREEN);
        restartButton = createPauseButton("RESTART", Color.CRIMSON);
        
        getChildren().addAll(pauseTitle, resumeButton, restartButton);
    }
    
    private Button createPauseButton(String text, Color color) {
        Button button = new Button(text);
        button.setPrefWidth(200);
        button.setPrefHeight(55);
        button.setFont(Font.font("Arial Black", FontWeight.EXTRA_BOLD, 18));
        button.setStyle(String.format(
            "-fx-background-color: linear-gradient(to bottom, %s, %s);" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 15;" +
            "-fx-border-radius: 15;" +
            "-fx-border-color: %s;" +
            "-fx-border-width: 3;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.7), 10, 0.7, 0, 5);",
            toRGBCode(color.brighter()),
            toRGBCode(color.darker()),
            toRGBCode(color.brighter())
        ));
        
        // Enhanced hover effects
        button.setOnMouseEntered(e -> {
            button.setStyle(String.format(
                "-fx-background-color: linear-gradient(to bottom, %s, %s);" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 15;" +
                "-fx-border-radius: 15;" +
                "-fx-border-color: %s;" +
                "-fx-border-width: 3;" +
                "-fx-effect: dropshadow(gaussian, %s, 15, 0.8, 0, 8);" +
                "-fx-scale-x: 1.1;" +
                "-fx-scale-y: 1.1;",
                toRGBCode(color.brighter().brighter()),
                toRGBCode(color),
                toRGBCode(color.brighter()),
                toRGBCode(color)
            ));
        });
        
        button.setOnMouseExited(e -> {
            button.setStyle(String.format(
                "-fx-background-color: linear-gradient(to bottom, %s, %s);" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 15;" +
                "-fx-border-radius: 15;" +
                "-fx-border-color: %s;" +
                "-fx-border-width: 3;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.7), 10, 0.7, 0, 5);" +
                "-fx-scale-x: 1.0;" +
                "-fx-scale-y: 1.0;",
                toRGBCode(color.brighter()),
                toRGBCode(color.darker()),
                toRGBCode(color.brighter())
            ));
        });
        
        return button;
    }
    
    private String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
            (int) (color.getRed() * 255),
            (int) (color.getGreen() * 255),
            (int) (color.getBlue() * 255));
    }
    
    // Getters
    public Button getResumeButton() { return resumeButton; }
    public Button getRestartButton() { return restartButton; }
} 