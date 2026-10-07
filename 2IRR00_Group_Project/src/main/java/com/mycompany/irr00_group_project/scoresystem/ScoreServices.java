package com.mycompany.irr00_group_project.scoresystem;

/**
 * Interface for score-related services in the Tetris game.
 * Defines methods for managing and updating the game score.
 *
 */
public interface ScoreServices {
    /**
     * Initializes and resets score to 0.
     */
    void resetScore();

    /**
     * Updates the score based on the number of lines cleared.
     *
     * @param linesCleaned the number of lines cleared, returned by GridManager
     */
    void updateScore(int linesCleaned);

    /**
     * Gets the current score.
     *
     * @return the current score
     */
    int getScore();
}
