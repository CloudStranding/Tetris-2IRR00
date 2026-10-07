package com.mycompany.irr00_group_project.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Difficulty selection screen for the Tetris game.
 * Features 3 difficulty levels: Entry Level, Challenge, Blitz
 */
public class DifficultyScreen {
    
    // Unified warm color scheme - soft, elegant colors
    private static final String ACCENT_COLOR = "#E6C068"; // Soft gold
    private static final String ENTRY_COLOR = "#5C8A58"; // Soft green - easy
    private static final String CHALLENGE_COLOR = "#C17A3A"; // Soft orange - medium  
    private static final String BLITZ_COLOR = "#B85450"; // Soft red - hard
    private static final String NEUTRAL_COLOR = "#7A8B9A"; // Soft gray
    private static final String WARM_BACKGROUND = 
        "linear-gradient(to bottom, #3D2B7A, #5A3F7D, #3D2B7A)";
    
    private final Scene difficultyScene;
    private final Button[] difficultyButtons;
    private final Button backButton;
    private int selectedDifficulty = 1;
    
    private static final String[] DIFFICULTY_NAMES = {"Entry Level", "Challenge", "Blitz"};
    private static final String[] DIFFICULTY_DESCRIPTIONS = {
        "Perfect for beginners - slower pace, more time to think",
        "Standard difficulty - balanced challenge for most players", 
        "Fast-paced action - only for the brave!"
    };
    
    /**
     * Creates a new difficulty screen.
     */
    public DifficultyScreen() {
        difficultyButtons = new Button[3];
        
        VBox difficultyBox = new VBox(25);
        difficultyBox.setAlignment(Pos.CENTER);
        difficultyBox.setPadding(new Insets(40));
        difficultyBox.setStyle("-fx-background-color: rgba(45, 27, 105, 0.85);"
            + "-fx-background-radius: 15;"
            + "-fx-border-color: rgba(230, 192, 104, 0.4);"
            + "-fx-border-width: 2;"
            + "-fx-border-radius: 15;");
        
        Label title = new Label("SELECT DIFFICULTY");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 36));
        title.setTextFill(Color.WHITE);
        title.setStyle("-fx-background-color: linear-gradient(to bottom, #3A2F6B, #2D1B69);"
            + "-fx-background-radius: 15;"
            + "-fx-border-color: " + ACCENT_COLOR + ";"
            + "-fx-border-width: 2;"
            + "-fx-border-radius: 15;"
            + "-fx-padding: 20;"
            + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 8, 0.5, 0, 4);");
        
        VBox buttonContainer = new VBox(20);
        buttonContainer.setAlignment(Pos.CENTER);
        
        String[] colors = {ENTRY_COLOR, CHALLENGE_COLOR, BLITZ_COLOR};
        
        for (int i = 0; i < 3; i++) {
            final int level = i + 1;
            Button levelButton = createDifficultyButton(
                DIFFICULTY_NAMES[i],
                DIFFICULTY_DESCRIPTIONS[i],
                level,
                colors[i]
            );
            updateButtonStyle(levelButton, level, colors[i]);
            difficultyButtons[i] = levelButton;
            buttonContainer.getChildren().add(levelButton);
        }
        
        backButton = createBackButton();
        
        difficultyBox.getChildren().addAll(title, buttonContainer, backButton);
        
        StackPane difficultyRoot = new StackPane();
        difficultyRoot.setStyle("-fx-background-color: " + WARM_BACKGROUND + ";");
        difficultyRoot.getChildren().add(difficultyBox);
        
        difficultyScene = new Scene(difficultyRoot, 850, 750);
    }
    
    /**
     * Creates a button for selecting a difficulty level.
     * @param name The name of the difficulty level
     * @param description The description of the difficulty level
     * @param level The difficulty level number
     * @param baseColor The base color for the button
     * @return The created button
     */
    private Button createDifficultyButton(String name, String description, 
            int level, String baseColor) {
        VBox buttonContent = new VBox(5);
        buttonContent.setAlignment(Pos.CENTER);
        
        Label nameLabel = new Label(name);
        nameLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        nameLabel.setTextFill(Color.WHITE);
        
        Label descLabel = new Label(description);
        descLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        descLabel.setTextFill(Color.web("#F0E6F7"));
        descLabel.setWrapText(true);
        descLabel.setMaxWidth(300);
        
        Button button = new Button();
        button.setGraphic(buttonContent);
        button.setPrefWidth(350);
        button.setPrefHeight(80);
        
        buttonContent.getChildren().addAll(nameLabel, descLabel);
        
        return button;
    }
    
    /**
     * Updates the style of a difficulty button based on its selection state.
     * @param button The button to update
     * @param level The difficulty level of the button
     * @param baseColor The base color for the button
     */
    private void updateButtonStyle(Button button, int level, String baseColor) {
        if (level == selectedDifficulty) {
            // Selected state with accent color
            button.setStyle("-fx-background-color: linear-gradient(to bottom, " 
                + ACCENT_COLOR + ", " + adjustBrightness(ACCENT_COLOR, 0.8) + ");"
                + "-fx-background-radius: 12;"
                + "-fx-border-color: " + ACCENT_COLOR + ";"
                + "-fx-border-width: 2;"
                + "-fx-border-radius: 12;"
                + "-fx-effect: dropshadow(gaussian, rgba(230, 192, 104, 0.6), 10, 0.7, 0, 0);");
        } else {
            // Normal state with difficulty-specific color
            String lighterColor = adjustBrightness(baseColor, 1.2);
            String darkerColor = adjustBrightness(baseColor, 0.8);
            
            button.setStyle(String.format(
                "-fx-background-color: linear-gradient(to bottom, %s, %s);"
                + "-fx-background-radius: 12;"
                + "-fx-border-color: rgba(255,255,255,0.2);"
                + "-fx-border-width: 2;"
                + "-fx-border-radius: 12;"
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 6, 0.4, 0, 3);",
                lighterColor, darkerColor
            ));
        }
        
        // Add refined hover effects
        button.setOnMouseEntered(e -> {
            if (level != selectedDifficulty) {
                String brighterColor = adjustBrightness(baseColor, 1.3);
                button.setStyle(button.getStyle() 
                    + "-fx-scale-x: 1.03; -fx-scale-y: 1.03;"
                    + "-fx-effect: dropshadow(gaussian, rgba(" 
                    + hexToRgb(baseColor) + ", 0.4), 8, 0.6, 0, 4);");
            }
        });
        
        button.setOnMouseExited(e -> {
            if (level != selectedDifficulty) {
                updateButtonStyle(button, level, baseColor); // Reset to normal state
            }
        });
    }
    
    /**
     * Creates the back button for returning to the main menu.
     * @return The created back button
     */
    private Button createBackButton() {
        Button button = new Button("BACK");
        button.setPrefWidth(150);
        button.setPrefHeight(50);
        button.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        
        String lighterColor = adjustBrightness(NEUTRAL_COLOR, 1.2);
        String darkerColor = adjustBrightness(NEUTRAL_COLOR, 0.8);
        
        button.setStyle(String.format(
            "-fx-background-color: linear-gradient(to bottom, %s, %s);"
            + "-fx-text-fill: white;"
            + "-fx-background-radius: 12;"
            + "-fx-border-radius: 12;"
            + "-fx-border-color: %s;"
            + "-fx-border-width: 2;"
            + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 6, 0.4, 0, 3);",
            lighterColor, darkerColor, lighterColor
        ));
        
        // Add hover effects
        button.setOnMouseEntered(e -> {
            button.setStyle(String.format(
                "-fx-background-color: linear-gradient(to bottom, %s, %s);"
                + "-fx-text-fill: white;"
                + "-fx-background-radius: 12;"
                + "-fx-border-radius: 12;"
                + "-fx-border-color: %s;"
                + "-fx-border-width: 2;"
                + "-fx-effect: dropshadow(gaussian, rgba(" + hexToRgb(NEUTRAL_COLOR)  
                        + ", 0.4), 8, 0.6, 0, 4);"
                + "-fx-scale-x: 1.05;"
                + "-fx-scale-y: 1.05;",
                adjustBrightness(NEUTRAL_COLOR, 1.3), NEUTRAL_COLOR, lighterColor
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
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 6, 0.4, 0, 3);"
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
        return r + "," + g + "," + b;
    }
    
    /**
     * Sets the selected difficulty level.
     * @param difficulty The difficulty level to select
     */
    public void setSelectedDifficulty(int difficulty) {
        this.selectedDifficulty = difficulty;
        // Update all button styles with their respective colors
        String[] colors = {ENTRY_COLOR, CHALLENGE_COLOR, BLITZ_COLOR};
        for (int i = 0; i < difficultyButtons.length; i++) {
            updateButtonStyle(difficultyButtons[i], i + 1, colors[i]);
        }
    }
    
    /**
     * Gets the name of a difficulty level.
     * @param difficulty The difficulty level
     * @return The name of the difficulty level
     */
    public static String getDifficultyName(int difficulty) {
        if (difficulty >= 1 && difficulty <= 3) {
            return DIFFICULTY_NAMES[difficulty - 1];
        }
        return DIFFICULTY_NAMES[0];
    }
    
    /**
     * Gets the difficulty selection scene.
     * @return The difficulty selection scene
     */
    public Scene getScene() { 
        return difficultyScene; 
    }

    /**
     * Gets the array of difficulty selection buttons.
     * @return The difficulty selection buttons
     */
    public Button[] getDifficultyButtons() { 
        return difficultyButtons; 
    }

    /**
     * Gets the currently selected difficulty level.
     * @return The selected difficulty level
     */
    public int getSelectedDifficulty() { 
        return selectedDifficulty; 
    }

    /**
     * Gets the back button.
     * @return The back button
     */
    public Button getBackButton() { 
        return backButton; 
    }
} 