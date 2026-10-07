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
 * Instruction screen for the Tetris game.
 * Displays game controls and scoring information.
 */
public class InstructionScreen {
    private static final String ACCENT_COLOR = "#E6C068";
    private static final String SUCCESS_COLOR = "#98D982";
    private static final String WARNING_COLOR = "#F48982";
    private static final String INFO_COLOR = "#4A7BA7";
    private static final String WARM_BACKGROUND = 
        "linear-gradient(to bottom, #3D2B7A, #5A3F7D, #3D2B7A)";
    
    private final Scene instructionScene;
    private final Button backButton;
    
    /**
     * Constructs the instruction screen with game controls and information.
     */
    public InstructionScreen() {
        backButton = createBackButton();
        VBox instructionBox = createInstructionBox();
        instructionScene = createScene(instructionBox);
    }
    
    /**
     * Creates the main container for the instruction screen.
     * 
     * @return the created VBox container
     */
    private VBox createInstructionBox() {
        VBox instructionBox = new VBox(25);
        instructionBox.setAlignment(Pos.CENTER);
        instructionBox.setPadding(new Insets(40));
        instructionBox.setStyle("-fx-background-color: rgba(45, 27, 105, 0.85);"
            + "-fx-background-radius: 15;"
            + "-fx-border-color: rgba(230, 192, 104, 0.4);"
            + "-fx-border-width: 2;"
            + "-fx-border-radius: 15;");
        
        instructionBox.getChildren().addAll(
            createTitleLabel(),
            createInstructionsContent(),
            backButton
        );
        
        return instructionBox;
    }
    
    private Label createTitleLabel() {
        Label title = new Label("HOW TO PLAY");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 36));
        title.setTextFill(Color.WHITE);
        title.setStyle(
            "-fx-background-color: linear-gradient(to bottom, #3A2F6B, #2D1B69);"
            + "-fx-background-radius: 15;"
            + "-fx-border-color: " + ACCENT_COLOR + ";"
            + "-fx-border-width: 2;"
            + "-fx-border-radius: 15;"
            + "-fx-padding: 20;"
            + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 8, 0.5, 0, 4);"
        );
        return title;
    }
    
    private VBox createInstructionsContent() {
        VBox instructions = new VBox(12);
        instructions.setAlignment(Pos.CENTER_LEFT);
        instructions.setPadding(new Insets(20));
        instructions.setStyle(
            "-fx-background-color: rgba(0,0,0,0.2);"
            + "-fx-background-radius: 12;"
            + "-fx-border-color: rgba(255,255,255,0.15);"
            + "-fx-border-width: 1;"
            + "-fx-border-radius: 12;"
        );
        
        String[] instructionTexts = {
            "← → : Move piece left/right",
            "↑ : Rotate piece",
            "↓ : Soft drop (faster falling)",
            "P : Pause the game",
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
            instructions.getChildren().add(createInstructionLabel(text));
        }
        
        return instructions;
    }
    
    private Label createInstructionLabel(String text) {
        Label instructionLabel = new Label(text);
        
        if (text.equals("Scoring System:")) {
            instructionLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
            instructionLabel.setTextFill(Color.web(SUCCESS_COLOR));
            instructionLabel.setStyle(
                "-fx-effect: dropshadow(gaussian, rgba(152, 217, 130, 0.3), "
                + "4, 0.4, 0, 0);"
            );
        } else if (text.startsWith("•")) {
            instructionLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 16));
            instructionLabel.setTextFill(Color.web(ACCENT_COLOR));
        } else if (text.startsWith("💡")) {
            instructionLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 16));
            instructionLabel.setTextFill(Color.web("#E8A87C"));
        } else if (!text.isEmpty()) {
            instructionLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 16));
            instructionLabel.setTextFill(Color.web("#F0E6F7"));
        } else {
            instructionLabel.setFont(Font.font("Segoe UI", 8));
            instructionLabel.setTextFill(Color.TRANSPARENT);
        }
        
        return instructionLabel;
    }
    
    private Button createBackButton() {
        Button button = new Button("BACK");
        button.setPrefWidth(150);
        button.setPrefHeight(50);
        button.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        
        String lighterColor = adjustBrightness(INFO_COLOR, 1.2);
        String darkerColor = adjustBrightness(INFO_COLOR, 0.8);
        
        button.setStyle(createButtonStyle(lighterColor, darkerColor, lighterColor, false));
        setupButtonHoverEffects(button, lighterColor, darkerColor);
        
        return button;
    }
    
    private Scene createScene(VBox instructionBox) {
        StackPane instructionRoot = new StackPane();
        instructionRoot.setStyle("-fx-background-color: " + WARM_BACKGROUND + ";");
        instructionRoot.getChildren().add(instructionBox);
        return new Scene(instructionRoot, 850, 750);
    }
    
    private String createButtonStyle(String bgColor1, String bgColor2, 
                                     String borderColor, boolean isHover) {
        String effect = isHover 
            ? "-fx-effect: dropshadow(gaussian, rgba(" 
              + hexToRgb(INFO_COLOR) + ", 0.4), 8, 0.6, 0, 4);"
            : "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 6, 0.4, 0, 3);";
        
        String scale = isHover ? "-fx-scale-x: 1.05; -fx-scale-y: 1.05;" : "";
        
        return String.format(
            "-fx-background-color: linear-gradient(to bottom, %s, %s);"
            + "-fx-text-fill: white;"
            + "-fx-background-radius: 12;"
            + "-fx-border-radius: 12;"
            + "-fx-border-color: %s;"
            + "-fx-border-width: 2;"
            + "%s"  // effect
            + "%s", // scale
            bgColor1, bgColor2, borderColor, effect, scale
        );
    }
    
    private void setupButtonHoverEffects(Button button, 
                                        String lighterColor, String darkerColor) {
        button.setOnMouseEntered(e -> {
            button.setStyle(createButtonStyle(
                adjustBrightness(INFO_COLOR, 1.3), 
                INFO_COLOR, 
                lighterColor, 
                true
            ));
        });
        
        button.setOnMouseExited(e -> {
            button.setStyle(createButtonStyle(
                lighterColor, 
                darkerColor, 
                lighterColor, 
                false
            ));
        });
    }
    
    private String adjustBrightness(String hexColor, double factor) {
        int rgb = Integer.parseInt(hexColor.substring(1), 16);
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        
        r = Math.min(255, (int) (r * factor));
        g = Math.min(255, (int) (g * factor));
        b = Math.min(255, (int) (b * factor));
        
        return String.format("#%02X%02X%02X", r, g, b);
    }
    
    private String hexToRgb(String hexColor) {
        int rgb = Integer.parseInt(hexColor.substring(1), 16);
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        return r + "," + g + "," + b;
    }
    
    public Scene getScene() { 
        return instructionScene; 
    }

    public Button getBackButton() { 
        return backButton; 
    }
}