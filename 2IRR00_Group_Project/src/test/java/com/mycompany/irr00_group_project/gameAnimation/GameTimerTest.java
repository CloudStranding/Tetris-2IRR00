package com.mycompany.irr00_group_project.gameanimation;

import com.mycompany.irr00_group_project.gameAnimation.GameTimer;
import static org.junit.jupiter.api.Assertions.*;

import javafx.animation.Timeline;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class GameTimerTest {

    private GameTimer timer;

    @BeforeEach
    void setUp() {
        // Start with the default constructor (1000 ms drop interval)
        timer = new GameTimer();
    }

    @Test
    void testDefaultInterval() {
        // Default drop interval should be 1000 ms
        assertEquals(1000.0, timer.getCurrentInterval(), 0.01);
        assertFalse(timer.shouldDrop());
        assertFalse(timer.isAccelerated());
    }

    @Test
    void testAccelerateAndDecelerate() {
        double original = timer.getCurrentInterval(); // 1000
        assertFalse(timer.isAccelerated());

        // Accelerate should halve the interval
        timer.accelerate();
        assertTrue(timer.isAccelerated());
        assertEquals(original * 0.5, timer.getCurrentInterval(), 0.01);

        // Calling accelerate again should have no further effect
        timer.accelerate();
        assertEquals(original * 0.5, timer.getCurrentInterval(), 0.01);

        // Decelerate should restore interval
        timer.decelerate();
        assertFalse(timer.isAccelerated());
        assertEquals(original, timer.getCurrentInterval(), 0.01);

        // Calling decelerate again should not change it further
        timer.decelerate();
        assertEquals(original, timer.getCurrentInterval(), 0.01);
    }

    @Test
    void testUpdateIntervalLowerBound() {
        // If we try to update below MIN_DROP_INTERVAL (100 ms), it should clamp
        timer.updateInterval(50);
        assertEquals(100.0, timer.getCurrentInterval(), 0.01);
    }

    @Test
    void testSetDifficultyLevels() {
        // Level 1 => DEFAULT_DROP_INTERVAL / (1 + 0 * 0.1) = 1000
        timer.setDifficultyLevel(1);
        assertEquals(1000.0, timer.getCurrentInterval(), 0.01);

        // Level 5 => newInterval = 1000 / (1 + 4*0.1) = 1000 / 1.4 ≈ 714.2857
        timer.setDifficultyLevel(5);
        assertEquals(1000.0 / 1.4, timer.getCurrentInterval(), 0.1);

        // Level 10 => newInterval = 1000 / (1 + 9*0.1) = 1000 / 1.9 ≈ 526.3158
        timer.setDifficultyLevel(10);
        assertEquals(1000.0 / 1.9, timer.getCurrentInterval(), 0.1);

        // Out­-of­-bounds levels clamp to [1..10]
        timer.setDifficultyLevel(0);
        assertEquals(1000.0, timer.getCurrentInterval(), 0.01);

        timer.setDifficultyLevel(11);
        // Above 10, it uses level=10 internally
        assertEquals(1000.0 / 1.9, timer.getCurrentInterval(), 0.1);
    }
}