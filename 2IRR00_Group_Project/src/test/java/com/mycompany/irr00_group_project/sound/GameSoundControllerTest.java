package com.mycompany.irr00_group_project.sound;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mycompany.irr00_group_project.gameanimation.JavaFXTestUtils;

/**
 * Unit tests for sound controller.
 * @author Steve
 */

public class GameSoundControllerTest {

    private GameSoundController controller;
    
    /**
     * Initialize JavaFX toolkit before any tests run.
     * @throws Exception if initialization fails
     */
    @BeforeAll
    static void initJavaFX() throws Exception {
        JavaFXTestUtils.initializeJavaFX();
    }
    
    /**
     * Sets up a new controller before each test.
     */
    
    @BeforeEach
    void setup() {
        controller = new GameSoundController();
    }

    @Test
    void testOnPieceMoved() {
        assertDoesNotThrow(() -> controller.onPieceMoved(), "onPieceMoved should not throw");
    }

    @Test
    void testOnPieceRotated() {
        assertDoesNotThrow(() -> controller.onPieceRotated(), "onPieceRotated should not throw");
    }

    @Test
    void testOnGameOver() {
        assertDoesNotThrow(() -> controller.onGameOver(), "onGameOver should not throw");
    }
}
