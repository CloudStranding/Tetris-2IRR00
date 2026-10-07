package com.mycompany.irr00_group_project.GUI.ui;

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
    
    public DifficultyScreen() {
        difficultyButtons = new Button[3];
        
        VBox difficultyBox = new VBox(25);
        difficultyBox.setAlignment(Pos.CENTER);
        difficultyBox.setPadding(new Insets(40));
        difficultyBox.setStyle("-fx-background-color: rgba(15, 52, 96, 0.95);" +
                              "-fx-background-radius: 20;" +
                              "-fx-border-color: rgba(100, 200, 255, 0.5);" +
                              "-fx-border-width: 2;" +
                              "-fx-border-radius: 20;");
        
        Label title = new Label("Select Difficulty");
        title.setFont(Font.font("Arial Black", FontWeight.EXTRA_BOLD, 28));
        title.setTextFill(Color.WHITE);
        title.setStyle("-fx-background-color: linear-gradient(to bottom, #1a5490, #0d2a4a);" +
                      "-fx-background-radius: 15;" +
                      "-fx-border-color: #FFD700;" +
                      "-fx-border-width: 3;" +
                      "-fx-border-radius: 15;" +
                      "-fx-padding: 15;" +
                      "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 10, 0.6, 0, 5);");
        
        VBox buttonContainer = new VBox(20);
        buttonContainer.setAlignment(Pos.CENTER);
        
        for (int i = 0; i < 3; i++) {
            final int level = i + 1;
            Button levelButton = createDifficultyButton(DIFFICULTY_NAMES[i], DIFFICULTY_DESCRIPTIONS[i], level);
            
            updateButtonStyle(levelButton, level);
            difficultyButtons[i] = levelButton;
            buttonContainer.getChildren().add(levelButton);
        }
        
        backButton = createMenuButton("BACK", Color.GRAY);
        
        difficultyBox.getChildren().addAll(title, buttonContainer, backButton);
        
        StackPane difficultyRoot = new StackPane();
        difficultyRoot.setStyle("-fx-background-color: linear-gradient(to bottom, #0f3460, #16537e, #0f3460);");
        difficultyRoot.getChildren().add(difficultyBox);
        
        difficultyScene = new Scene(difficultyRoot, 850, 750);
    }
    
    private Button createDifficultyButton(String name, String description, int level) {
        VBox buttonContent = new VBox(5);
        buttonContent.setAlignment(Pos.CENTER);
        
        Label nameLabel = new Label(name);
        nameLabel.setFont(Font.font("Arial Black", FontWeight.EXTRA_BOLD, 18));
        nameLabel.setTextFill(Color.WHITE);
        
        Label descLabel = new Label(description);
        descLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        descLabel.setTextFill(Color.LIGHTGRAY);
        descLabel.setWrapText(true);
        descLabel.setMaxWidth(300);
        
        Button button = new Button();
        button.setGraphic(buttonContent);
        button.setPrefWidth(350);
        button.setPrefHeight(80);
        
        buttonContent.getChildren().addAll(nameLabel, descLabel);
        
        return button;
    }
    
    private void updateButtonStyle(Button button, int level) {
        if (level == selectedDifficulty) {
            button.setStyle("-fx-background-color: linear-gradient(to bottom, #FFD700, #DAA520);" +
                          "-fx-background-radius: 15;" +
                          "-fx-border-color: #FFD700;" +
                          "-fx-border-width: 3;" +
                          "-fx-border-radius: 15;" +
                          "-fx-effect: dropshadow(gaussian, rgba(255,215,0,0.8), 15, 0.8, 0, 0);");
        } else {
            // Different colors for different difficulty levels
            String[] colors = {
                "#4CAF50, #45a049", // Green for Entry Level
                "#FF9800, #F57C00", // Orange for Challenge
                "#F44336, #D32F2F"  // Red for Blitz
            };
            
            button.setStyle(String.format(
                "-fx-background-color: linear-gradient(to bottom, %s);" +
                "-fx-background-radius: 15;" +
                "-fx-border-color: rgba(255,255,255,0.3);" +
                "-fx-border-width: 2;" +
                "-fx-border-radius: 15;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 8, 0.6, 0, 4);",
                colors[level - 1]
            ));
        }
        
        // Add hover effects
        button.setOnMouseEntered(e -> {
            if (level != selectedDifficulty) {
                button.setStyle(button.getStyle() + "-fx-scale-x: 1.05; -fx-scale-y: 1.05;");
            }
        });
        
        button.setOnMouseExited(e -> {
            if (level != selectedDifficulty) {
                button.setStyle(button.getStyle().replace("-fx-scale-x: 1.05; -fx-scale-y: 1.05;", ""));
            }
        });
    }
    
    private Button createMenuButton(String text, Color color) {
        Button button = new Button(text);
        button.setPrefWidth(150);
        button.setPrefHeight(50);
        button.setFont(Font.font("Arial Black", FontWeight.EXTRA_BOLD, 14));
        button.setStyle(String.format(
            "-fx-background-color: linear-gradient(to bottom, %s, %s);" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 15;" +
            "-fx-border-radius: 15;" +
            "-fx-border-color: %s;" +
            "-fx-border-width: 2;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 8, 0.6, 0, 4);",
            toRGBCode(color.brighter()),
            toRGBCode(color.darker()),
            toRGBCode(color.brighter())
        ));
        
        return button;
    }
    
    private String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
            (int) (color.getRed() * 255),
            (int) (color.getGreen() * 255),
            (int) (color.getBlue() * 255));
    }
    
    public void setSelectedDifficulty(int difficulty) {
        this.selectedDifficulty = difficulty;
        // Update all button styles
        for (int i = 0; i < difficultyButtons.length; i++) {
            updateButtonStyle(difficultyButtons[i], i + 1);
        }
    }
    
    public static String getDifficultyName(int difficulty) {
        if (difficulty >= 1 && difficulty <= 3) {
            return DIFFICULTY_NAMES[difficulty - 1];
        }
        return DIFFICULTY_NAMES[0];
    }
    
    // Getters
    public Scene getScene() { return difficultyScene; }
    public Button[] getDifficultyButtons() { return difficultyButtons; }
    public Button getBackButton() { return backButton; }
    public int getSelectedDifficulty() { return selectedDifficulty; }
} 