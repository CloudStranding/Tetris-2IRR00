package com.mycompany.irr00_group_project.gui.game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Game information panel.
 */
public class GameInfoPanel extends VBox {
    private final Label scoreLabel;
    private final Canvas nextPieceCanvas;

    /**
     * Creates game info panel.
     */
    public GameInfoPanel() {
        setPadding(new Insets(20));
        setAlignment(Pos.TOP_CENTER);
        String style = "-fx-background-color: rgba(61, 43, 122, 0.7);"
                + "-fx-border-color: rgba(230, 192, 104, 0.3);"
                + "-fx-border-width: 2;"
                + "-fx-background-radius: 12;"
                + "-fx-border-radius: 12;";
        setStyle(style);
        setPrefWidth(160);

        // Score display
        Label scoreTitleLabel = new Label("SCORE");
        scoreTitleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        scoreTitleLabel.setTextFill(Color.WHITE);
        String scoreStyle = "-fx-background-color: linear-gradient(to bottom, #3A2F6B, #2D1B69);"
                + "-fx-background-radius: 10;"
                + "-fx-border-color: #E6C068;"
                + "-fx-border-width: 2;"
                + "-fx-border-radius: 10;"
                + "-fx-padding: 8;"
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 4, 0.4, 0, 2);";
        scoreTitleLabel.setStyle(scoreStyle);

        scoreLabel = new Label("0");
        scoreLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 26));
        scoreLabel.setTextFill(Color.web("#E6C068"));
        scoreLabel.setStyle("-fx-background-color: rgba(0,0,0,0.2);"
                + "-fx-background-radius: 10;"
                + "-fx-padding: 10;");

        // Next piece display
        Label nextPieceLabel = new Label("NEXT");
        nextPieceLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        nextPieceLabel.setTextFill(Color.WHITE);
        nextPieceLabel.setStyle(scoreStyle);

        nextPieceCanvas = new Canvas(4 * 30, 4 * 30);
        nextPieceCanvas.setStyle("-fx-background-color: rgba(0,0,0,0.3);"
                + "-fx-background-radius: 10;"
                + "-fx-border-color: rgba(255,255,255,0.2);"
                + "-fx-border-width: 1;"
                + "-fx-border-radius: 10;");

        getChildren().addAll(scoreTitleLabel, scoreLabel,
                new Label(""), nextPieceLabel, nextPieceCanvas);
    }

    /**
     * Gets score label.
     *
     * @return score label
     */
    public Label getScoreLabel() {
        return scoreLabel;
    }

    /**
     * Gets next piece canvas.
     *
     * @return next piece canvas
     */
    public Canvas getNextPieceCanvas() {
        return nextPieceCanvas;
    }
}