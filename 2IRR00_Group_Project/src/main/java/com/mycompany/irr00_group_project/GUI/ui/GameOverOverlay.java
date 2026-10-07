package com.mycompany.irr00_group_project.GUI.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Game over overlay for the Tetris game.
 * Contains GAME OVER message and RESTART button.
 */
public class GameOverOverlay extends VBox {
    
    private final Button restartButton;
    
    public GameOverOverlay() {
        super(40);
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: rgba(15, 52, 96, 0.95);" +
                "-fx-background-radius: 20;" +
                "-fx-border-color: rgba(255, 69, 0, 0.8);" +
                "-fx-border-width: 4;" +
                "-fx-border-radius: 20;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.9), 25, 0.9, 0, 15);");
        setPrefSize(450, 320);
        setMaxSize(450, 320);
        
        Label gameOverTitle = new Label("GAME OVER");
        gameOverTitle.setFont(Font.font("Arial Black", FontWeight.EXTRA_BOLD, 32));
        gameOverTitle.setTextFill(Color.WHITE);
        gameOverTitle.setStyle("-fx-background-color: linear-gradient(to bottom, #8B0000, #B22222);" +
                             "-fx-background-radius: 15;" +
                             "-fx-border-color: #FF4500;" +
                             "-fx-border-width: 3;" +
                             "-fx-border-radius: 15;" +
                             "-fx-padding: 18;" +
                             "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.9), 12, 0.8, 0, 6);");
        
        Label messageLabel = new Label("Don't give up! Try again!");
        messageLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        messageLabel.setTextFill(Color.LIGHTCYAN);
        messageLabel.setStyle("-fx-padding: 10;");
        
        restartButton = createGameOverButton("RESTART", Color.LIMEGREEN);
        
        getChildren().addAll(gameOverTitle, messageLabel, restartButton);
    }
    
    private Button createGameOverButton(String text, Color color) {
        Button button = new Button(text);
        button.setPrefWidth(220);
        button.setPrefHeight(65);
        button.setFont(Font.font("Arial Black", FontWeight.EXTRA_BOLD, 20));
        button.setStyle(String.format(
            "-fx-background-color: linear-gradient(to bottom, %s, %s);" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 15;" +
            "-fx-border-radius: 15;" +
            "-fx-border-color: %s;" +
            "-fx-border-width: 3;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.7), 12, 0.8, 0, 6);",
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
                "-fx-effect: dropshadow(gaussian, %s, 18, 0.9, 0, 10);" +
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
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.7), 12, 0.8, 0, 6);" +
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
    
    // Getter
    public Button getRestartButton() { return restartButton; }
} 