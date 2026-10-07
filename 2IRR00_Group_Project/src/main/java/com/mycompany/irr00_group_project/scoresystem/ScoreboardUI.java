package com.mycompany.irr00_group_project.scoresystem;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Map;

//ToDo this class need to be added and tested in GUI part

/**
 * This class creates a new window that shows all stored player scores in descending order.
 */
public class ScoreboardUI {
    private final ScoreManager scoreManager;

    public ScoreboardUI(ScoreManager scoreManager) {
        this.scoreManager = scoreManager;
    }

    public void show() {
        Stage scoreboardStage = new Stage();
        scoreboardStage.setTitle("Scoreboard");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));

        Map<String, Integer> sortedScores = scoreManager.getSortedScores();
        for (Map.Entry<String, Integer> entry : sortedScores.entrySet()) {
            Label label = new Label(entry.getKey() + ": " + entry.getValue());
            label.setFont(Font.font("Arial", 16));
            label.setTextFill(Color.DARKBLUE);
            layout.getChildren().add(label);
        }

        Scene scene = new Scene(layout, 250, 400);
        scoreboardStage.setScene(scene);
        scoreboardStage.show();
    }
}
