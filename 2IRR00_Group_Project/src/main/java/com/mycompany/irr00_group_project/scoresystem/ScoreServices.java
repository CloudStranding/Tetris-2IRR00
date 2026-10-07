package com.mycompany.irr00_group_project.scoresystem;

import java.util.Map;


//ToDo int linesCleaned need to be added into the GridManager part
//ToDo String username should be offered by Userinput part
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

    /**
     * Store the current score associated with the given username.
     * This should be called once after the game ends.
     * @param username the player's username
     */
    void storeScore(String username);

    /**
     * @return map of all stored scores with usernames
     */
    Map<String, Integer> getStoredScores();

    /**
     * @return sorted map of scores in descending order
     */
    Map<String, Integer> getSortedScores();

}
