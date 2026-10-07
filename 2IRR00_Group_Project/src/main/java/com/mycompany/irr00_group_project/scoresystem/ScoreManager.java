package com.mycompany.irr00_group_project.scoresystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manages the game's scoring system.
 * Handles score calculation, storage, and retrieval.
 *
 * @author Jayson Leander, Yingyao Feng
 */
public class ScoreManager implements ScoreServices {

    private int score;
    private final Map<String, Integer> scoreHistory = new LinkedHashMap<>();
    private final String historyFile = "score_history.txt";

    /**
     * Creates a new ScoreManager.
     */
    public ScoreManager() {
        this.score = 0;
        System.out.println("ScoreManager initialized");
    }

    /**
     * Resets score to 0.
     */
    @Override
    public void resetScore() {
        this.score = 0;
        System.out.println("Score reset to 0");
    }

    /**
     * Updates the score based on the number of lines cleared.
     * Calculate and update score according to classic Tetris scoring:
     * - No lines cleared (piece placement only): 0 points
     * - Single line clear: 40 points
     * - Double line clear: 100 points
     * - Triple line clear: 300 points
     * - Tetris (4 lines): 1200 points
     *
     * @param linesCleared the number of linesCleaned each time returned by GridManager
     */
    @Override
    public void updateScore(int linesCleared) {
        int pointsAdded = 0;
        switch (linesCleared) {
            case 1 -> pointsAdded = 40;   // Single line clear
            case 2 -> pointsAdded = 100;  // Double line clear
            case 3 -> pointsAdded = 300;  // Triple line clear
            case 4 -> pointsAdded = 1200; // Tetris (4 lines)
            case 0 -> pointsAdded = 0;    // Piece placement only, no points
            default -> pointsAdded = 0;   // Shouldn't happen, but safe fallback
        }
        score += pointsAdded;
        
        if (pointsAdded > 0) {
            System.out.println("Score updated: +" + pointsAdded 
                + " points (" + getLinesDescription(linesCleared) + ") = " + score);
        } else if (linesCleared == 0) {
            System.out.println("Piece placed (no lines cleared) - no points added");
        }
    }
    
    /**
     * Returns a descriptive string for the number of lines cleared.
     *
     * @param linesCleared the number of lines cleared
     * @return a descriptive string
     */
    private String getLinesDescription(int linesCleared) {
        return switch (linesCleared) {
            case 1 -> "Single line clear";
            case 2 -> "Double line clear";
            case 3 -> "Triple line clear";
            case 4 -> "TETRIS!";
            default -> linesCleared + " lines";
        };
    }

    /**
     * Gets the current score.
     *
     * @return the current score
     */
    @Override
    public int getScore() {
        return score;
    }

    /**
     * Stores the final score of a user into the in-memory map and local file.
     * If the username already exists, store all its scores.
     *
     * @param username The username of the player
     */
    public void storeFinalScore(String username) {
        int existingBest = getHistoricalBestScore(username);

        if (score > existingBest) {
            scoreHistory.put(username, score);
            saveToFile(username, score);
            System.out.println("New highest score updated " + username + score);
        } else {
            System.out.println("Score is not higher than existing best");
        }
    }

    /**
     * Saves all scores to the local file for persistent history.
     *
     * @param username the username to save
     * @param score the score to save
     */
    private void saveToFile(String username, int score) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(historyFile, true))) {
            writer.println(username + ":" + score);
            System.out.println("Saved: " + username 
                + " -> " + score); // For test
        } catch (IOException e) {
            System.err.println("Error saving score history: " + e.getMessage()); // For test
        }
    }

    /**
     * Loads the top 15 scores from the history file.
     *
     * @return a map of usernames to their highest scores
     */
    public Map<String, Integer> loadTop15Scores() {
        Map<String, Integer> highestScores = new HashMap<>();

        // read local history file
        try (BufferedReader reader = new BufferedReader(new FileReader(historyFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String username = parts[0].trim();
                    int score = Integer.parseInt(parts[1].trim());

                    // for each username, we only load the highest score into the ScoreBoard
                    highestScores.put(username, 
                            Math.max(highestScores.getOrDefault(username, 0), score));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading score history: " + e.getMessage());
        }

        // select the first 15 scores for ScoreBoard display
        return highestScores.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()))
                .limit(15)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    /**
     * Checks the highest score for the given username.
     *
     * @param targetUsername the username to check
     * @return the highest score for the username
     */
    private int getHistoricalBestScore(String targetUsername) {
        int best = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(historyFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String username = parts[0].trim();
                    int pastScore = Integer.parseInt(parts[1].trim());
                    if (username.equals(targetUsername)) {
                        best = Math.max(best, pastScore);
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error checking historical best: " + e.getMessage());
        }
        return best;
    }
}
