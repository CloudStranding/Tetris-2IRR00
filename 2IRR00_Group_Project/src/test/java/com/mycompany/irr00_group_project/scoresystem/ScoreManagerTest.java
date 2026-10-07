package com.mycompany.irr00_group_project.scoresystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreManagerTest {

    private final String historyFile = "score_history.txt";

    @BeforeEach
    void setUp() {
        // delete history file before each test
        File file = new File(historyFile);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * Test when history has more than 15 entries, only keep the first 15 with the highest scores.
     */
    @Test
    void testTop15ScoresOnly() {
        ScoreManager manager = new ScoreManager();

        for (int i = 1; i <= 20; i++) {
            manager.resetScore();
            for (int j = 0; j < i; j++) {
                manager.updateScore(1); // 每次加 40 分
            }
            manager.storeFinalScore("Player" + i);
        }

        Map<String, Integer> topScores = manager.loadTop15Scores();
        assertEquals(15, topScores.size());

        // Check scores are descending order
        List<Integer> scores = new ArrayList<>(topScores.values());
        for (int i = 0; i < scores.size() - 1; i++) {
            assertTrue(scores.get(i) >= scores.get(i + 1));
        }
    }

    /**
     * Test when history file is empty.
     */
    @Test
    void testEmptyHistoryReturnsEmptyMap() {
        ScoreManager manager = new ScoreManager();
        Map<String, Integer> topScores = manager.loadTop15Scores();
        assertTrue(topScores.isEmpty());
    }

}
