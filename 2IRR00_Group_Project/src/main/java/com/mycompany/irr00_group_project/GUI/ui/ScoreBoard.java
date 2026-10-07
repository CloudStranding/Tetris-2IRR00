package com.mycompany.irr00_group_project.GUI.ui;

import com.mycompany.irr00_group_project.scoresystem.ScoreManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScoreBoard {

    private final ScoreManager scoreManager;

    public ScoreBoard(ScoreManager scoreManager) {
        this.scoreManager = scoreManager;
    }

    public void show() {
        Map<String, Integer> topScoresMap = scoreManager.loadTop15Scores();

        List<Map.Entry<String, Integer>> topScores = topScoresMap.entrySet()
                .stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                .limit(15)
                .collect(Collectors.toList());

        VBox mainContainer = new VBox(30); // 间距加大
        mainContainer.setPadding(new Insets(30));
        mainContainer.setAlignment(Pos.TOP_CENTER);
        mainContainer.setStyle(
                "-fx-background-color: rgba(15, 52, 96, 0.95);" +
                        "-fx-background-radius: 20;" +
                        "-fx-border-color: rgba(100, 200, 255, 0.7);" +
                        "-fx-border-width: 3;" +
                        "-fx-border-radius: 20;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 20, 0.8, 0, 10);"
        );

        Label title = new Label("🏆 Top 15 Players");
        title.setStyle(
                "-fx-font-size: 24px;" +
                        "-fx-font-family: 'Arial Black';" +
                        "-fx-text-fill: white;" +
                        "-fx-background-color: linear-gradient(to bottom, #1a5490, #0d2a4a);" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-color: rgb(247, 246, 241);" +
                        "-fx-border-width: 3;" +
                        "-fx-border-radius: 12;" +
                        "-fx-padding: 10 25;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 8, 0.6, 0, 4);"
        );

        VBox scoreList = new VBox(10);
        scoreList.setAlignment(Pos.CENTER_LEFT);
        scoreList.setPadding(new Insets(10, 30, 10, 30));

        if (topScores.isEmpty()) {
            Label noData = new Label("No scores yet.");
            noData.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
            scoreList.getChildren().add(noData);
        } else {
            int rank = 1;
            for (Map.Entry<String, Integer> entry : topScores) {
                Label scoreLine = new Label(String.format("No.%d  %-15s %5d", rank++, entry.getKey(), entry.getValue()));
                scoreLine.setStyle(
                        "-fx-font-size: 16px;" +
                                "-fx-font-family: 'Courier New';" +
                                "-fx-font-weight: bold;" +
                                "-fx-text-fill: white;"
                );
                scoreList.getChildren().add(scoreLine);
            }
        }

        mainContainer.getChildren().addAll(title, scoreList);

        Scene scene = new Scene(mainContainer, 450, 550);
        Stage stage = new Stage();
        stage.setTitle("Scoreboard");
        stage.setScene(scene);
        stage.show();
    }

}
