package com.mycompany.irr00_group_project.gui;

import java.util.Map;

import com.mycompany.irr00_group_project.scoresystem.ScoreManager;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * ScoreBoard dialog for the Tetris game.
 * Displays the top 15 scores from all players as a modal dialog.
 * 
 * @author Raye, Jayson
 */
public class ScoreBoard {
    
    // Color constants
    private static final String ACCENT_COLOR = "#E6C068"; // Soft gold
    private static final String SUCCESS_COLOR = "#5C8A58"; // Soft green
    private static final String WARNING_COLOR = "#C17A3A"; // Soft orange
    private static final String INFO_COLOR = "#4A7BA7"; // Soft blue
    private static final String SPECIAL_COLOR = "#8B7BB8"; // Soft purple
    
    // Design constants
    private static final String BORDER_RADIUS = "12";
    
    // Warm background gradient - lighter and warmer
    private static final String WARM_BACKGROUND =
            "linear-gradient(to bottom, #3D2B7A, #5A3F7D, #3D2B7A)";
    
    private final Stage scoreboardStage;
    private final VBox scoreRoot;
    private final VBox scoreList;
    private final Button closeButton;
    private final Label titleLabel;
    
    private ScoreManager scoreManager;
    
    /**
     * Creates a new ScoreBoard with all necessary UI components.
     * Initializes the scoreboard window with a title, scrollable score list, and close button.
     */
    public ScoreBoard() {
        scoreboardStage = new Stage();
        scoreboardStage.initStyle(StageStyle.UNDECORATED);
        scoreboardStage.initModality(Modality.APPLICATION_MODAL);
        scoreboardStage.setTitle("Scoreboard");
        
        scoreRoot = new VBox(25);
        scoreRoot.setAlignment(Pos.CENTER);
        scoreRoot.setPadding(new Insets(25));
        // Elegant container styling with refined border
        scoreRoot.setStyle("-fx-background-color: linear-gradient(to bottom, #4A4A6B, #3D3D5A);"
            + "-fx-border-color: " + ACCENT_COLOR + ";"
            + "-fx-border-width: 3;"
            + "-fx-border-radius: " + BORDER_RADIUS + ";"
            + "-fx-background-radius: " + BORDER_RADIUS + ";"
            + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 15, 0.5, 0, 5);");
        
        scoreList = new VBox(8);
        scoreList.setAlignment(Pos.CENTER);
        
        titleLabel = new Label("🏆 TOP 15 PLAYERS");
        closeButton = createStyledButton();
        
        createUI();
        
        Scene scene = new Scene(scoreRoot, 500, 580);
        scene.setFill(Color.TRANSPARENT);
        scoreboardStage.setScene(scene);
    }
    
    /**
     * Creates the user interface components for the scoreboard.
     */
    private void createUI() {
        // Enhanced title with elegant gradient background
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setStyle("-fx-background-color:"
                + " linear-gradient(to bottom, rgba(230, 192, 104, 0.3), rgba(230, 192, 104, 0.1));"
            + "-fx-border-color: " + ACCENT_COLOR + ";"
            + "-fx-border-width: 2;"
            + "-fx-border-radius: " + BORDER_RADIUS + ";"
            + "-fx-background-radius: " + BORDER_RADIUS + ";"
            + "-fx-padding: 12;"
            + "-fx-effect: dropshadow(gaussian, rgba(230, 192, 104, 0.3), 8, 0.4, 0, 2);");
        
        // Elegant scroll pane styling
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(scoreList);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setPrefHeight(350);
        scrollPane.setStyle("-fx-background-color: transparent;"
            + "-fx-background: rgba(0,0,0,0.2);"
            + "-fx-border-color: rgba(230, 192, 104, 0.3);"
            + "-fx-border-width: 1;"
            + "-fx-border-radius: " + BORDER_RADIUS + ";"
            + "-fx-background-radius: " + BORDER_RADIUS + ";");
        
        // Enhanced close button
        closeButton.setPrefWidth(120);
        closeButton.setPrefHeight(45);
        closeButton.setOnAction(e -> hide());
        
        scoreRoot.getChildren().addAll(
            titleLabel,
            scrollPane,
            closeButton
        );
    }
    
    /**
     * Shows the scoreboard dialog.
     */
    public void show() {
        scoreboardStage.show();
        scoreboardStage.centerOnScreen();
    }
    
    /**
     * Hides the scoreboard dialog.
     */
    public void hide() {
        scoreboardStage.hide();
    }
    
    /**
     * Updates the scoreboard with current scores.
     * 
     * @param scoreManager the score manager to get scores from
     */
    public void updateScoreboard(ScoreManager scoreManager) {
        this.scoreManager = scoreManager;
        
        // Clear existing scores
        scoreList.getChildren().clear();
        
        // Load and display top scores
        Map<String, Integer> topScores = scoreManager.loadTop15Scores();
        
        if (topScores.isEmpty()) {
            Label noScoresLabel = new Label("No scores yet! Be the first to play!");
            noScoresLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 16));
            noScoresLabel.setTextFill(Color.web("#F0E6F7"));
            noScoresLabel.setStyle("-fx-padding: 40;"
                + "-fx-background-color: rgba(255,255,255,0.08);"
                + "-fx-background-radius: 10;"
                + "-fx-border-color: rgba(230, 192, 104, 0.2);"
                + "-fx-border-width: 1;"
                + "-fx-border-radius: 10;");
            scoreList.getChildren().add(noScoresLabel);
        } else {
            int rank = 1;
            for (Map.Entry<String, Integer> entry : topScores.entrySet()) {
                String playerName = entry.getKey();
                int score = entry.getValue();
                
                HBox scoreRow = createScoreRow(rank, playerName, score);
                scoreList.getChildren().add(scoreRow);
                rank++;
            }
        }
    }
    
    /**
     * Creates a row in the score list for a player's score.
     * @param rank The player's rank
     * @param playerName The player's name
     * @param score The player's score
     * @return The created score row
     */
    private HBox createScoreRow(int rank, String playerName, int score) {
        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(8, 15, 8, 15));
        
        // Enhanced row styling with subtle border
        String rowColor = getRankRowColor(rank);
        row.setStyle("-fx-background-color: linear-gradient(to right, " 
            + rowColor + ", rgba(255,255,255,0.05));"
            + "-fx-border-color: rgba(230, 192, 104, 0.2);"
            + "-fx-border-width: 1;"
            + "-fx-border-radius: 8;"
            + "-fx-background-radius: 8;");
        
        // Clean rank display
        String rankText = switch (rank) {
            case 1 -> "🥇 #1";
            case 2 -> "🥈 #2";
            case 3 -> "🥉 #3";
            default -> "#" + rank;
        };
        
        Label rankLabel = new Label(rankText);
        rankLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        rankLabel.setTextFill(getRankColor(rank));
        rankLabel.setPrefWidth(70);
        
        // Player name with modern font
        Label nameLabel = new Label(playerName);
        nameLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 15));
        nameLabel.setTextFill(Color.WHITE);
        nameLabel.setPrefWidth(220);
        
        // Score with clean styling
        Label scoreLabel = new Label(String.valueOf(score) + " pts");
        scoreLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        scoreLabel.setTextFill(Color.web(SUCCESS_COLOR));
        scoreLabel.setPrefWidth(100);
        scoreLabel.setAlignment(Pos.CENTER_RIGHT);
        
        row.getChildren().addAll(rankLabel, nameLabel, scoreLabel);
        return row;
    }
    
    /**
     * Gets the background color for a rank row.
     * @param rank The rank to get the color for
     * @return The color as a hex string
     */
    private String getRankRowColor(int rank) {
        if (rank == 1) {
            return "rgba(230, 192, 104, 0.3)"; // Gold
        } else if (rank == 2) {
            return "rgba(192, 192, 192, 0.2)"; // Silver
        } else if (rank == 3) {
            return "rgba(205, 127, 50, 0.2)"; // Bronze
        } else {
            return "rgba(255, 255, 255, 0.1)"; // Default
        }
    }
    
    /**
     * Gets the text color for a rank.
     * @param rank The rank to get the color for
     * @return The color for the rank
     */
    private Color getRankColor(int rank) {
        return switch (rank) {
            case 1 -> Color.web(ACCENT_COLOR); // Soft gold
            case 2 -> Color.web("#B8B8B8"); // Soft silver
            case 3 -> Color.web("#C17A3A"); // Soft bronze
            default -> Color.web("#F0E6F7"); // Soft light purple
        };
    }
    
    /**
     * Creates a styled button for the scoreboard.
     * @return The created button
     */
    private Button createStyledButton() {
        Button button = new Button("CLOSE");
        button.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        
        String lighterColor = adjustBrightness(WARNING_COLOR, 1.2);
        String darkerColor = adjustBrightness(WARNING_COLOR, 0.8);
        
        button.setStyle("-fx-background-color: linear-gradient(to bottom, " 
            + lighterColor + ", " + darkerColor + ");"
            + "-fx-text-fill: white;"
            + "-fx-background-radius: 10;"
            + "-fx-border-radius: 10;"
            + "-fx-border-color: " + adjustBrightness(WARNING_COLOR, 1.3) + ";"
            + "-fx-border-width: 2;"
            + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 6, 0.4, 0, 3);");
        button.setFocusTraversable(false);
        
        button.setOnMouseEntered(e -> {
            button.setStyle("-fx-background-color: linear-gradient(to bottom, " 
                + adjustBrightness(WARNING_COLOR, 1.4) + ", " + WARNING_COLOR + ");"
                + "-fx-text-fill: white;"
                + "-fx-background-radius: 10;"
                + "-fx-border-radius: 10;"
                + "-fx-border-color: " + adjustBrightness(WARNING_COLOR, 1.3) + ";"
                + "-fx-border-width: 2;"
                + "-fx-effect: dropshadow(gaussian, rgba("
                    + hexToRgb(WARNING_COLOR) + ", 0.4), 8, 0.6, 0, 4);"
                + "-fx-scale-x: 1.03;"
                + "-fx-scale-y: 1.03;");
        });
        
        button.setOnMouseExited(e -> {
            button.setStyle("-fx-background-color: linear-gradient(to bottom, " 
                + lighterColor + ", " + darkerColor + ");"
                + "-fx-text-fill: white;"
                + "-fx-background-radius: 10;"
                + "-fx-border-radius: 10;"
                + "-fx-border-color: " + adjustBrightness(WARNING_COLOR, 1.3) + ";"
                + "-fx-border-width: 2;"
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 6, 0.4, 0, 3);"
                + "-fx-scale-x: 1.0;"
                + "-fx-scale-y: 1.0;");
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
     * Gets the scoreboard stage.
     * @return The scoreboard stage
     */
    public Stage getStage() { 
        return scoreboardStage; 
    }
    
    /**
     * Gets the close button.
     * @return The close button
     */
    public Button getCloseButton() { 
        return closeButton; 
    }
} 