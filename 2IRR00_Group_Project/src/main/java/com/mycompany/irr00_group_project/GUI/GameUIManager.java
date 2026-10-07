package com.mycompany.irr00_group_project.gui;

import com.mycompany.irr00_group_project.EnhancedGameEngine;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Manages game UI components.
 */
public class GameUIManager {
    private final BorderPane root = new BorderPane();
    private final GameArea gameArea;
    private final GameInfoPanel infoPanel;
    private final GameControls controls;
    private final HBox topBar;
    private final Label statusLabel;

    /**
     * Creates UI manager.
     * 
     * @param gameEngine game engine instance
     */
    public GameUIManager(EnhancedGameEngine gameEngine) {
        root.setPadding(new Insets(10));
        root.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #3D2B7A, #5A3F7D, #3D2B7A);");
        
        gameArea = new GameArea(gameEngine);
        infoPanel = new GameInfoPanel();
        controls = new GameControls();
        topBar = createTopBar();
        statusLabel = (Label) topBar.getChildren().get(1);
        
        root.setTop(topBar);
        root.setCenter(gameArea);
        root.setLeft(infoPanel);
        root.setRight(controls);
    }

    private HBox createTopBar() {
        HBox topBar = new HBox(40);
        topBar.setAlignment(Pos.CENTER);
        topBar.setPadding(new Insets(15));
        String topBarStyle = "-fx-background-color: rgba(61, 43, 122, 0.7);"
            + "-fx-background-radius: 12;"
            + "-fx-border-color: rgba(230, 192, 104, 0.3);"
            + "-fx-border-width: 2;"
            + "-fx-border-radius: 12;";
        topBar.setStyle(topBarStyle);
        
        Label titleLabel = new Label("TETRIS");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
        titleLabel.setTextFill(Color.WHITE);
        String titleStyle = "-fx-background-color: linear-gradient(to bottom, #3A2F6B, #2D1B69);"
            + "-fx-background-radius: 12;"
            + "-fx-border-color: #E6C068;"
            + "-fx-border-width: 2;"
            + "-fx-border-radius: 12;"
            + "-fx-padding: 12;"
            + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 6, 0.4, 0, 3);";
        titleLabel.setStyle(titleStyle);
        
        Label statusLabel = new Label("Playing");
        statusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        statusLabel.setTextFill(Color.web("#98D982"));
        
        topBar.getChildren().addAll(titleLabel, statusLabel);
        return topBar;
    }

    public BorderPane getRoot() {
        return root;
    }

    public GameArea getGameArea() {
        return gameArea;
    }

    public GameInfoPanel getInfoPanel() {
        return infoPanel;
    }

    public GameControls getControls() {
        return controls;
    }

    public Label getStatusLabel() {
        return statusLabel;
    }
}