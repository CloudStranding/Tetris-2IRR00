package com.mycompany.irr00_group_project.scoresystem;

public class ScoreManager implements ScoreServices {

    private int score;

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
     * @param linesCleared the number of linesCleaned each time returned by GridManager
     * Calculate and update score according to classic Tetris scoring:
     * - No lines cleared (piece placement only): 0 points
     * - Single line clear: 40 points
     * - Double line clear: 100 points
     * - Triple line clear: 300 points
     * - Tetris (4 lines): 1200 points
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
            System.out.println("Score updated: +" + pointsAdded + " points (" + 
                             getLinesDescription(linesCleared) + ") = " + score);
        } else if (linesCleared == 0) {
            System.out.println("Piece placed (no lines cleared) - no points added");
        }
    }
    
    /**
     * Returns a descriptive string for the number of lines cleared.
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
     * @return the current score
     */
    @Override
    public int getScore() {
        return score;
    }
}
