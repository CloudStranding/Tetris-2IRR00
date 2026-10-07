package com.mycompany.irr00_group_project.GUI.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Main menu screen for the Tetris game.
 * Contains START GAME, DIFFICULTY, and INSTRUCTION buttons.
 */
public class MainMenuScreen {
    
    private final VBox menuRoot;
    private final Scene menuScene;
    private final Button startGameButton;
    private final Button difficultyButton;
    private final Button instructionButton;
    private final Label difficultyLabel;
    
    private int selectedDifficulty = 1;
    
    public MainMenuScreen() {
        menuRoot = new VBox(50);
        menuRoot.setAlignment(Pos.CENTER);
        menuRoot.setPadding(new Insets(80));
        menuRoot.setStyle("-fx-background-color: linear-gradient(to bottom,rgb(116, 144, 177),rgb(46, 118, 170),rgb(53, 104, 167));");
        
        // Create title with enhanced styling
        Label titleLabel = new Label("TETRIS");
        titleLabel.setFont(Font.font("Trebuchet MS", FontWeight.EXTRA_BOLD, 85));
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setStyle("-fx-background-color: linear-gradient(to bottom, #1a5490,rgb(44, 80, 119));" +
                          "-fx-background-radius: 20;" +
                          "-fx-border-color:rgb(19, 19, 17);" +
                          "-fx-border-width: 4;" +
                          "-fx-border-radius: 20;" +
                          "-fx-padding: 25;" +
                          "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 12, 0.6, 0, 6);");
        
        // Create menu buttons with enhanced styling
        startGameButton = createMenuButton("START GAME", Color.LIMEGREEN);
        difficultyButton = createMenuButton("DIFFICULTY", Color.ORANGE);
        instructionButton = createMenuButton("INSTRUCTION", Color.DEEPSKYBLUE);
        
        // Create difficulty indicator with better styling
        difficultyLabel = new Label("Current Difficulty: " + getDifficultyName(selectedDifficulty));
        difficultyLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        difficultyLabel.setTextFill(Color.LIGHTCYAN);
        difficultyLabel.setStyle("-fx-background-color: rgba(0,0,0,0.3);" +
                               "-fx-background-radius: 10;" +
                               "-fx-padding: 10;");
        
        // Layout components with better spacing
        VBox buttonContainer = new VBox(25);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().addAll(startGameButton, difficultyButton, instructionButton);
        
        menuRoot.getChildren().addAll(titleLabel, buttonContainer, difficultyLabel);
        
        menuScene = new Scene(menuRoot, 850, 750);
    }
    
    private Button createMenuButton(String text, Color color) {
        Button button = new Button(text);
        button.setPrefWidth(250);
        button.setPrefHeight(60);
        button.setFont(Font.font("Arial Black", FontWeight.EXTRA_BOLD, 18));
        button.setStyle(String.format(
            "-fx-background-color: linear-gradient(to bottom, %s, %s);" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 15;" +
            "-fx-border-radius: 15;" +
            "-fx-border-color: %s;" +
            "-fx-border-width: 3;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 8, 0.6, 0, 4);",
            toRGBCode(color.brighter()),
            toRGBCode(color.darker()),
            toRGBCode(color.brighter())
        ));
        
        // Enhanced hover effects with scaling and glow
        button.setOnMouseEntered(e -> {
            button.setStyle(String.format(
                "-fx-background-color: linear-gradient(to bottom, %s, %s);" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 15;" +
                "-fx-border-radius: 15;" +
                "-fx-border-color: %s;" +
                "-fx-border-width: 3;" +
                "-fx-effect: dropshadow(gaussian, %s, 12, 0.8, 0, 6);" +
                "-fx-scale-x: 1.08;" +
                "-fx-scale-y: 1.08;",
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
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 8, 0.6, 0, 4);" +
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
    
    private String getDifficultyName(int difficulty) {
        return DifficultyScreen.getDifficultyName(difficulty);
    }
    
    public void updateDifficulty(int difficulty) {
        this.selectedDifficulty = difficulty;
        difficultyLabel.setText("Current Difficulty: " + getDifficultyName(difficulty));
    }
    
    // Getters
    public Scene getScene() { return menuScene; }
    public Button getStartGameButton() { return startGameButton; }
    public Button getDifficultyButton() { return difficultyButton; }
    public Button getInstructionButton() { return instructionButton; }
    public int getSelectedDifficulty() { return selectedDifficulty; }
} 