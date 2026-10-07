package com.mycompany.irr00_group_project.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Pause overlay for the Tetris game.
 * Contains RESUME, RESTART, and SCOREBOARD buttons.
 */
public class PauseOverlay extends VBox {
    
    // Unified warm color scheme - matching other screens
    private static final String ACCENT_COLOR = "#E6C068"; // Soft gold
    private static final String SUCCESS_COLOR = "#5C8A58"; // Soft green
    private static final String DANGER_COLOR = "#B85450"; // Soft red
    private static final String INFO_COLOR = "#4A7BA7"; // Soft blue
    private static final String WARM_BACKGROUND = 
            "linear-gradient(to bottom, #2D1B69, #4A2C6A, #2D1B69)";
    
    private final Button resumeButton;
    private final Button restartButton;
    private final Button scoreboardButton;
    
    /**
     * Creates a new PauseOverlay with all necessary UI components.
     * Initializes the overlay with a title and three buttons: Resume, Restart, and Scoreboard.
     */
    public PauseOverlay() {
        super(30);
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: " + WARM_BACKGROUND + ";"
            + "-fx-background-radius: 15;"
            + "-fx-border-color: rgba(230, 192, 104, 0.4);"
            + "-fx-border-width: 2;"
            + "-fx-border-radius: 15;"
            + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 15, 0.7, 0, 8);");
        // Increased size as requested
        setPrefSize(420, 380);
        setMaxSize(420, 380);
        
        Label pauseTitle = new Label("GAME PAUSED");
        pauseTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        pauseTitle.setTextFill(Color.WHITE);
        pauseTitle.setStyle("-fx-background-color: rgba(58, 47, 107, 0.7);"
            + "-fx-background-radius: 12;"
            + "-fx-border-color: " + ACCENT_COLOR + ";"
            + "-fx-border-width: 2;"
            + "-fx-border-radius: 12;"
            + "-fx-padding: 15;"
            + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 6, 0.5, 0, 3);");
        
        resumeButton = createPauseButton("RESUME", SUCCESS_COLOR);
        restartButton = createPauseButton("RESTART", DANGER_COLOR);
        scoreboardButton = createPauseButton("SCOREBOARD", INFO_COLOR);
        
        getChildren().addAll(pauseTitle, resumeButton, restartButton, scoreboardButton);
    }
    
    /**
     * Creates a button for the pause overlay.
     * @param text The button text
     * @param baseColor The base color for the button
     * @return The created button
     */
    private Button createPauseButton(String text, String baseColor) {
        Button button = new Button(text);
        button.setPrefWidth(220);
        button.setPrefHeight(55);
        button.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        
        String lighterColor = adjustBrightness(baseColor, 1.2);
        String darkerColor = adjustBrightness(baseColor, 0.8);
        
        button.setStyle(String.format(
            "-fx-background-color: linear-gradient(to bottom, %s, %s);"
            + "-fx-text-fill: white;"
            + "-fx-background-radius: 12;"
            + "-fx-border-radius: 12;"
            + "-fx-border-color: %s;"
            + "-fx-border-width: 2;"
            + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 6, 0.5, 0, 3);",
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
                + "-fx-effect: dropshadow(gaussian, rgba(%s, 0.5), 10, 0.7, 0, 5);"
                + "-fx-scale-x: 1.05;"
                + "-fx-scale-y: 1.05;",
                adjustBrightness(baseColor, 1.3),
                baseColor,
                lighterColor,
                hexToRgb(baseColor)
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
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 6, 0.5, 0, 3);"
                + "-fx-scale-x: 1.0;"
                + "-fx-scale-y: 1.0;",
                lighterColor, darkerColor, lighterColor
            ));
        });
        
        return button;
    }
    
    /**
     * Adjusts the brightness of a hex color.
     * @param hexColor The hex color to adjust
     * @param factor The brightness factor
     * @return The adjusted hex color
     */
    private String adjustBrightness(String hexColor, double factor) {
        // Convert hex to RGB
        int rgb = Integer.parseInt(hexColor.substring(1), 16);
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        
        // Adjust brightness
        r = Math.min(255, (int) (r * factor));
        g = Math.min(255, (int) (g * factor));
        b = Math.min(255, (int) (b * factor));
        
        return String.format("#%02X%02X%02X", r, g, b);
    }
    
    /**
     * Converts a hex color to RGB format.
     * @param hexColor The hex color to convert
     * @return The RGB color as a string
     */
    private String hexToRgb(String hexColor) {
        int rgb = Integer.parseInt(hexColor.substring(1), 16);
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        return r + ", " + g + ", " + b;
    }
    
    /**
     * Gets the resume button.
     * @return The resume button
     */
    public Button getResumeButton() { 
        return resumeButton; 
    }
    
    /**
     * Gets the restart button.
     * @return The restart button
     */
    public Button getRestartButton() { 
        return restartButton; 
    }
    
    /**
     * Gets the scoreboard button.
     * @return The scoreboard button
     */
    public Button getScoreboardButton() { 
        return scoreboardButton; 
    }
} 