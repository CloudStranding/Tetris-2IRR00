package com.mycompany.irr00_group_project.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Game controls panel.
 */
public class GameControls extends VBox {
    private final Button pauseButton;

    /**
     * Creates game controls.
     */
    public GameControls() {
        setPadding(new Insets(20));
        setAlignment(Pos.TOP_CENTER);
        String style = "-fx-background-color: rgba(61, 43, 122, 0.7);"
            + "-fx-border-color: rgba(230, 192, 104, 0.3);"
            + "-fx-border-width: 2;"
            + "-fx-background-radius: 12;"
            + "-fx-border-radius: 12;";
        setStyle(style);
        setPrefWidth(180);
        
        pauseButton = createPauseButton();

        // Instructions
        Label instructionsLabel = new Label("CONTROLS");
        instructionsLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        instructionsLabel.setTextFill(Color.WHITE);
        String labelStyle = "-fx-background-color: linear-gradient(to bottom, #3A2F6B, #2D1B69);"
            + "-fx-background-radius: 10;"
            + "-fx-border-color: #E6C068;"
            + "-fx-border-width: 2;"
            + "-fx-border-radius: 10;"
            + "-fx-padding: 8;"
            + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 4, 0.4, 0, 2);";
        instructionsLabel.setStyle(labelStyle);
        
        VBox keyContainer = new VBox(8);
        keyContainer.setAlignment(Pos.CENTER);
        keyContainer.setStyle("-fx-background-color: rgba(0,0,0,0.2);"
            + "-fx-background-radius: 10;"
            + "-fx-padding: 15;");
        
        GridPane keyGrid = new GridPane();
        keyGrid.setHgap(10);
        keyGrid.setVgap(8);
        keyGrid.setAlignment(Pos.CENTER);
        
        keyGrid.add(createKeyLabel("←→"), 0, 0);
        keyGrid.add(createKeyLabel("Move"), 1, 0);
        keyGrid.add(createKeyLabel("↑"), 0, 1);
        keyGrid.add(createKeyLabel("Rotate"), 1, 1);
        keyGrid.add(createKeyLabel("↓"), 0, 2);
        keyGrid.add(createKeyLabel("Drop"), 1, 2);
        keyGrid.add(createKeyLabel("P"), 0, 3);
        keyGrid.add(createKeyLabel("Pause"), 1, 3);
        
        keyContainer.getChildren().add(keyGrid);
        
        getChildren().addAll(pauseButton, new Label(""), instructionsLabel, keyContainer);
    }

    private Button createPauseButton() {
        Button button = new Button("PAUSE");
        button.setPrefWidth(130);
        button.setPrefHeight(50);
        button.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        
        String baseColor = "#C17A3A";
        String lighterColor = adjustBrightness(baseColor, 1.2);
        String darkerColor = adjustBrightness(baseColor, 0.8);
        
        String buttonStyle = "-fx-background-color: linear-gradient(to bottom, " 
            + lighterColor + ", " + darkerColor + ");"
            + "-fx-text-fill: white;"
            + "-fx-background-radius: 12;"
            + "-fx-border-radius: 12;"
            + "-fx-border-color: " + lighterColor + ";"
            + "-fx-border-width: 2;"
            + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 6, 0.4, 0, 3);";
        button.setStyle(buttonStyle);
        button.setFocusTraversable(false);
        
        button.setOnMouseEntered(e -> {
            String hoverStyle = "-fx-background-color: linear-gradient(to bottom, " 
                + adjustBrightness(baseColor, 1.3) + ", " + baseColor + ");"
                + "-fx-text-fill: white;"
                + "-fx-background-radius: 12;"
                + "-fx-border-radius: 12;"
                + "-fx-border-color: " + lighterColor + ";"
                + "-fx-border-width: 2;"
                + "-fx-effect: dropshadow(gaussian, rgba(" 
                + hexToRgb(baseColor) + ", 0.4), 8, 0.6, 0, 4);"
                + "-fx-scale-x: 1.05;"
                + "-fx-scale-y: 1.05;";
            button.setStyle(hoverStyle);
        });
        
        button.setOnMouseExited(e -> {
            button.setStyle(buttonStyle);
        });
        
        return button;
    }

    private Label createKeyLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 12));
        label.setTextFill(Color.web("#F0E6F7"));
        return label;
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

    /**
     * Gets the pause button.
     * 
     * @return pause button
     */
    public Button getPauseButton() {
        return pauseButton;
    }
}