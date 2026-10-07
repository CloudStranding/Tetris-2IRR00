package com.mycompany.irr00_group_project.scoresystem;

//ToDo int linesCleaned need to be added into the GridManager part

public interface ScoreServices {
    /**
     * Initialize and reset score to 0
     */
    void resetScore();

    /**
     * @param linesCleaned the number of linesCleaned each time returned by GridManager
     * Calculate and update score
     */
    void updateScore(int linesCleaned);

    /**
     * @return the current score
     */
    int getScore();


}
