package com.mycompany.irr00_group_project.scoresystem;

import java.util.HashMap;
import java.util.Map;
import java.util.LinkedHashMap;

public class ScoreManager implements ScoreServices {

    private int score;
    private Map<String, Integer> playerScores;

    /**
     * Initialization, creates playerScores to store all player's score.
     */
    public ScoreManager() {
        this.score = 0;
        this.playerScores = new HashMap<>();
    }

    /**
     * Resets score to 0.
     */
    @Override
    public void resetScore() {
        this.score = 0;
    }

    /**
     * @param linesCleared the number of linesCleaned each time returned by GridManager
     * Calculate and update score
     */
    @Override
    public void updateScore(int linesCleared) {
        switch (linesCleared) {
            case 1 -> score += 100;
            case 2 -> score += 300;
            case 3 -> score += 500;
            case 4 -> score += 800;
            default -> score += 0;
        }
    }

    /**
     * @return the current score
     */
    @Override
    public int getScore() {
        return score;
    }

    /**
     * Stores username together with this round's score.
     * @param username given by user input, everytime the game is restarted, user will be asked to input username
     */
    public void storeScore(String username) {
        playerScores.put(username, score);
    }

    /**
     * @return the HashMap playerScores.
     */
    public Map<String, Integer> getStoredScores() {
        return playerScores;
    }

    /**
     * @return a new LinkedHashMap of player scores sorted in descending order.
     */
    public Map<String, Integer> getSortedScores() {
        return playerScores.entrySet()
                .stream()
                .sorted((a, b) -> b.getValue().compareTo(a.getValue()))
                .collect(
                        java.util.stream.Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (e1, e2) -> e1,
                                LinkedHashMap::new
                        )
                );
    }
}
