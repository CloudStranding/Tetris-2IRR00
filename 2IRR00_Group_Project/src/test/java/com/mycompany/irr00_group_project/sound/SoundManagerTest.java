package com.mycompany.irr00_group_project.sound;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mycompany.irr00_group_project.gameanimation.JavaFXTestUtils;

/**
 * Unit tests for sound management.
 * @author Steve
 */

public class SoundManagerTest {
    
    /**
     * Initialize JavaFX toolkit before any tests run.
     * @throws Exception if initialization fails
     */
    @BeforeAll
    static void initJavaFX() throws Exception {
        JavaFXTestUtils.initializeJavaFX();
    }
    
    /**
     * stop all the music before each test.
     */
    
    @BeforeEach
    void resetState() {
        SoundManager.stopBackgroundMusic();
    }

    @Test
    void testPlayBackgroundMusicDoesNotThrow() {
        assertDoesNotThrow(
            SoundManager::playBackgroundMusic, 
            "Should not throw when playing background music"
        );
    }

    @Test
    void testPauseBackgroundMusic() {
        SoundManager.playBackgroundMusic();
        SoundManager.pauseBackgroundMusic();
        assertFalse(
            SoundManager.isMusicPlaying(), 
            "Music should be paused after pauseBackgroundMusic()"
        );
    }

    @Test
    void testStopBackgroundMusic() {
        SoundManager.playBackgroundMusic();
        SoundManager.stopBackgroundMusic();
        assertFalse(
            SoundManager.isMusicPlaying(), 
            "Music should not be playing after stopBackgroundMusic()"
        );
    }

    @Test
    void testSetMusicVolume() {
        assertDoesNotThrow(
            () -> SoundManager.setMusicVolume(0.7), 
            "Setting volume should not throw exception"
        );
    }

    @Test
    void testPlayMoveSound() {
        assertDoesNotThrow(SoundManager::playMove, "playMove should not throw any exception");
    }

    @Test
    void testPlayRotateSound() {
        assertDoesNotThrow(SoundManager::playRotate, "playRotate should not throw any exception");
    }

    @Test
    void testPlayGameOverSound() {
        assertDoesNotThrow(
            SoundManager::playGameOver, 
            "playGameOver should not throw any exception"
        );
    }
}
