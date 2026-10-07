package com.mycompany.irr00_group_project.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Username input screen.
 */
public class UsernameScreen {
    private static final String ACCENT_COLOR = "#E6C068";
    private static final String SUCCESS_COLOR = "#5C8A58";
    private static final String DANGER_COLOR = "#B85450";
    private static final String WARM_BACKGROUND = 
        "linear-gradient(to bottom, #3D2B7A, #5A3F7D, #3D2B7A)";
    
    private final VBox usernameRoot;
    private final Scene usernameScene;
    private TextField usernameField;
    private Button startButton;
    private Button backButton;
    private Label errorLabel;
    
    /**
     * Creates username screen.
     */
    public UsernameScreen() {
        usernameRoot = new VBox(40);
        usernameRoot.setAlignment(Pos.CENTER);
        usernameRoot.setPadding(new Insets(60));
        usernameRoot.setStyle("-fx-background-color: " + WARM_BACKGROUND + ";");
        
        createUI();
        
        usernameScene = new Scene(usernameRoot, 850, 750);
    }
    
    /**
     * Creates UI components.
     */
    private void createUI() {
        usernameRoot.getChildren().addAll(
            createTitleLabel(),
            createInstructionLabel(),
            createUsernameField(),
            createErrorLabel(),
            createButtonContainer()
        );
    }

    private Label createTitleLabel() {
        Label titleLabel = new Label("ENTER YOUR NAME");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.EXTRA_BOLD, 36));
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        String titleStyle = "-fx-background-color: linear-gradient(to bottom, #3A2F6B, #2D1B69);"
            + "-fx-background-radius: 15;"
            + "-fx-border-color: " + ACCENT_COLOR + ";"
            + "-fx-border-width: 2;"
            + "-fx-border-radius: 15;"
            + "-fx-padding: 18;"
            + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 8, 0.5, 0, 4);"
            + "-fx-alignment: center;";
        titleLabel.setStyle(titleStyle);
        return titleLabel;
    }

    private Label createInstructionLabel() {
        Label instructionLabel = new Label("Your name will be saved to the leaderboard!");
        instructionLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 16));
        instructionLabel.setTextFill(Color.web("#F0E6F7"));
        instructionLabel.setStyle("-fx-padding: 12;"
            + "-fx-background-color: rgba(0,0,0,0.2);"
            + "-fx-background-radius: 12;");
        return instructionLabel;
    }

    private TextField createUsernameField() {
        usernameField = new TextField();
        usernameField.setPromptText("Enter your username...");
        usernameField.setPrefWidth(400);
        usernameField.setMaxWidth(400);
        usernameField.setPrefHeight(60);
        usernameField.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 18));
        usernameField.setAlignment(Pos.CENTER);
        String fieldStyle = "-fx-background-color: rgba(255,255,255,0.95);"
            + "-fx-background-radius: 12;"
            + "-fx-border-color: " + ACCENT_COLOR + ";"
            + "-fx-border-width: 2;"
            + "-fx-border-radius: 12;"
            + "-fx-padding: 15;"
            + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 6, 0.4, 0, 3);"
            + "-fx-alignment: center;";
        usernameField.setStyle(fieldStyle);
        
        // Event handlers
        usernameField.setOnAction(e -> {
            if (validateUsername()) {
                startButton.fire();
            }
        });
        
        usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (errorLabel.isVisible()) {
                hideError();
            }
        });
        
        return usernameField;
    }

    private Label createErrorLabel() {
        errorLabel = new Label("Username not valid");
        errorLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        errorLabel.setTextFill(Color.web(DANGER_COLOR));
        errorLabel.setStyle("-fx-padding: 8;"
            + "-fx-background-color: rgba(184, 84, 80, 0.2);"
            + "-fx-background-radius: 8;"
            + "-fx-border-color: " + DANGER_COLOR + ";"
            + "-fx-border-width: 1;"
            + "-fx-border-radius: 8;");
        errorLabel.setVisible(false);
        return errorLabel;
    }

    private VBox createButtonContainer() {
        startButton = createStyledButton("START GAME", SUCCESS_COLOR);
        startButton.setPrefWidth(250);
        startButton.setPrefHeight(60);
        
        backButton = createStyledButton("BACK", DANGER_COLOR);
        backButton.setPrefWidth(250);
        backButton.setPrefHeight(60);
        
        VBox buttonContainer = new VBox(20);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().addAll(startButton, backButton);
        return buttonContainer;
    }
    
    private Button createStyledButton(String text, String baseColor) {
        Button button = new Button(text);
        button.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        
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
     * Validates username.
     * 
     * @return true if valid
     */
    public boolean validateUsername() {
        String username = usernameField.getText().trim();
        if (username.isEmpty()) {
            showError();
            return false;
        }
        hideError();
        return true;
    }
    
    /**
     * Shows error message.
     */
    private void showError() {
        errorLabel.setVisible(true);
        String errorStyle = "-fx-background-color: rgba(255,255,255,0.95);"
            + "-fx-background-radius: 12;"
            + "-fx-border-color: " + DANGER_COLOR + ";"
            + "-fx-border-width: 2;"
            + "-fx-border-radius: 12;"
            + "-fx-padding: 15;"
            + "-fx-effect: dropshadow(gaussian, rgba(184, 84, 80, 0.4), 6, 0.4, 0, 3);"
            + "-fx-alignment: center;";
        usernameField.setStyle(errorStyle);
    }
    
    /**
     * Hides error message.
     */
    private void hideError() {
        errorLabel.setVisible(false);
        String normalStyle = "-fx-background-color: rgba(255,255,255,0.95);"
            + "-fx-background-radius: 12;"
            + "-fx-border-color: " + ACCENT_COLOR + ";"
            + "-fx-border-width: 2;"
            + "-fx-border-radius: 12;"
            + "-fx-padding: 15;"
            + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 6, 0.4, 0, 3);"
            + "-fx-alignment: center;";
        usernameField.setStyle(normalStyle);
    }
    
    /**
     * Gets username.
     * 
     * @return username or null
     */
    public String getUsername() {
        String username = usernameField.getText().trim();
        return username.isEmpty() ? null : username;
    }
    
    /**
     * Gets username with fallback.
     * 
     * @return username or "Player"
     */
    public String getUsernameWithFallback() {
        String username = usernameField.getText().trim();
        return username.isEmpty() ? "Player" : username;
    }
    
    /**
     * Clears username field.
     */
    public void clearUsername() {
        usernameField.clear();
    }
    
    /**
     * Focuses username field.
     */
    public void focusUsernameField() {
        usernameField.requestFocus();
    }
    
    // Getters
    public Scene getScene() { 
        return usernameScene; 
    }
    
    public Button getStartButton() { 
        return startButton; 
    }
    
    public Button getBackButton() { 
        return backButton; 
    }
    
    public TextField getUsernameField() { 
        return usernameField; 
    }
}