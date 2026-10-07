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
 * Instruction screen for the Tetris game.
 */
public class InstructionScreen {
    
    private final Scene instructionScene;
    private final Button backButton;
    
    public InstructionScreen() {
        VBox instructionBox = new VBox(25);
        instructionBox.setAlignment(Pos.CENTER);
        instructionBox.setPadding(new Insets(40));
        instructionBox.setStyle("-fx-background-color: rgba(15, 52, 96, 0.95);" +
                              "-fx-background-radius: 20;" +
                              "-fx-border-color: rgba(100, 200, 255, 0.5);" +
                              "-fx-border-width: 2;" +
                              "-fx-border-radius: 20;");
        
        Label title = new Label("How to Play");
        title.setFont(Font.font("Arial Black", FontWeight.EXTRA_BOLD, 32));
        title.setTextFill(Color.WHITE);
        title.setStyle("-fx-background-color: linear-gradient(to bottom, #1a5490,rgb(54, 102, 152));" +
                      "-fx-background-radius: 15;" +
                      "-fx-border-color:rgb(240, 240, 238);" +
                      "-fx-border-width: 3;" +
                      "-fx-border-radius: 15;" +
                      "-fx-padding: 18;" +
                      "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 10, 0.6, 0, 5);");
        
        VBox instructions = new VBox(15);
        instructions.setAlignment(Pos.CENTER_LEFT);
        instructions.setPadding(new Insets(20));
        instructions.setStyle("-fx-background-color: rgba(0,0,0,0.3);" +
                            "-fx-background-radius: 15;" +
                            "-fx-border-color: rgba(255,255,255,0.2);" +
                            "-fx-border-width: 1;" +
                            "-fx-border-radius: 15;");
        
        String[] instructionTexts = {
            "← → : Move piece left/right",
            "↑ : Rotate piece",
            "↓ : Soft drop (faster falling)",
            "",
            "Scoring System:",
            "• Single line: 40 points",
            "• Double line: 100 points", 
            "• Triple line: 300 points",
            "• Tetris (4 lines): 1200 points",
            "",
            "💡 Complete horizontal lines to score points!",
            "💡 Game ends when pieces reach the top."
        };
        
        for (String text : instructionTexts) {
            Label instructionLabel = new Label(text);
            
            if (text.equals("Scoring System:")) {
                instructionLabel.setFont(Font.font("Arial Black", FontWeight.EXTRA_BOLD, 18));
                instructionLabel.setTextFill(Color.LIGHTGREEN);
                instructionLabel.setStyle("-fx-effect: dropshadow(gaussian, rgba(27, 223, 138, 0.5), 5, 0.5, 0, 0);");
            } else if (text.startsWith("•")) {
                instructionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
                instructionLabel.setTextFill(Color.YELLOW);
            } else if (text.startsWith("💡") || text.startsWith("⚠️")) {
                instructionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
                instructionLabel.setTextFill(Color.ORANGE);
            } else if (!text.isEmpty()) {
                instructionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
                instructionLabel.setTextFill(Color.LIGHTCYAN);
            } else {
                instructionLabel.setFont(Font.font("Arial", 8));
                instructionLabel.setTextFill(Color.TRANSPARENT);
            }
            
            instructions.getChildren().add(instructionLabel);
        }
        
        backButton = createMenuButton("BACK", Color.STEELBLUE);
        
        instructionBox.getChildren().addAll(title, instructions, backButton);
        
        StackPane instructionRoot = new StackPane();
        instructionRoot.setStyle("-fx-background-color: linear-gradient(to bottom, #0f3460, #16537e, #0f3460);");
        instructionRoot.getChildren().add(instructionBox);
        
        instructionScene = new Scene(instructionRoot, 850, 750);
    }
    
    private Button createMenuButton(String text, Color color) {
        Button button = new Button(text);
        button.setPrefWidth(150);
        button.setPrefHeight(50);
        button.setFont(Font.font("Arial Black", FontWeight.EXTRA_BOLD, 16));
        button.setStyle(String.format(
            "-fx-background-color: linear-gradient(to bottom, %s, %s);" +
            "-fx-text-fill: white;" +
            "-fx-background-radius: 15;" +
            "-fx-border-radius: 15;" +
            "-fx-border-color: %s;" +
            "-fx-border-width: 3;" +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 10, 0.7, 0, 5);",
            toRGBCode(color.brighter()),
            toRGBCode(color.darker()),
            toRGBCode(color.brighter())
        ));
        
        // Hover effect
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
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 10, 0.7, 0, 5);" +
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
    public Scene getScene() { return instructionScene; }
    public Button getBackButton() { return backButton; }
} 