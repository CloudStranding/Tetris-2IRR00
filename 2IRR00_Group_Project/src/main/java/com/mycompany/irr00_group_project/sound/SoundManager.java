/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses
 * /license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates
 * /Classes/Class.java to edit this template
 */

package com.mycompany.irr00_group_project.sound;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Utility class for managing all game sounds and background music.
 * Provides methods to play sound effects (move, rotate, game over) and background music.
 * Uses JavaFX AudioClip for sound effects and MediaPlayer for background music.
 * Designed to be used statically across the game.
 *
 * @author Wenjie Li
 */

public class SoundManager {
    private static MediaPlayer backgroundMusicPlayer;
    private static boolean musicPlaying = false;
    
    // Sound effect clips
    private static AudioClip moveSound;
    private static AudioClip rotateSound;
    private static AudioClip gameOverSound;
    
    static {
        loadSounds();
    }
    
    /**
     * Loads all sound files at startup.
     */
    private static void loadSounds() {
        try {
            // Load sound effects using AudioClip for quick playback
            var moveResource = SoundManager.class.getResource("/sounds/move.wav");
            if (moveResource != null) {
                moveSound = new AudioClip(moveResource.toExternalForm());
            } else {
                System.err.println("Warning: move.wav not found in /sounds/");
            }
            
            var rotateResource = SoundManager.class.getResource("/sounds/rotate.wav");
            if (rotateResource != null) {
                rotateSound = new AudioClip(rotateResource.toExternalForm());
            } else {
                System.err.println("Warning: rotate.wav not found in /sounds/");
            }
            
            var gameOverResource = SoundManager.class.getResource("/sounds/gameover.wav");
            if (gameOverResource != null) {
                gameOverSound = new AudioClip(gameOverResource.toExternalForm());
            } else {
                System.err.println("Warning: gameover.wav not found in /sounds/");
            }
            
            // Load background music using MediaPlayer for looping
            var musicResource = SoundManager.class.getResource("/sounds/tetrismusic.wav");
            if (musicResource != null) {
                Media backgroundMusic = new Media(musicResource.toExternalForm());
                backgroundMusicPlayer = new MediaPlayer(backgroundMusic);
                backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop indefinitely
                backgroundMusicPlayer.setVolume(0.3); // Lower volume for background music
            } else {
                System.err.println("Warning: tetrismusic.wav not found in /sounds/");
            }
            
        } catch (Exception e) {
            System.err.println("Error loading sound files: " + e.getMessage());
        }
    }
    
    /**
     * Plays the background music.
     */
    public static void playBackgroundMusic() {
        try {
            if (backgroundMusicPlayer != null && !musicPlaying) {
                backgroundMusicPlayer.play();
                musicPlaying = true;
                System.out.println("Playing background music");
            }
        } catch (Exception e) {
            System.err.println("Error playing background music: " + e.getMessage());
        }
    }
    
    /**
     * Pauses the background music.
     */
    public static void pauseBackgroundMusic() {
        try {
            if (backgroundMusicPlayer != null && musicPlaying) {
                backgroundMusicPlayer.pause();
                musicPlaying = false;
                System.out.println("Pausing background music");
            }
        } catch (Exception e) {
            System.err.println("Error pausing background music: " + e.getMessage());
        }
    }

    /**
     * Stops the background music.
     */
    public static void stopBackgroundMusic() {
        try {
            if (backgroundMusicPlayer != null) {
                backgroundMusicPlayer.stop();
                musicPlaying = false;
                System.out.println("Stopping background music");
            }
        } catch (Exception e) {
            System.err.println("Error stopping background music: " + e.getMessage());
        }
    }
    
    /**
     * Plays the move sound effect.
     */
    public static void playMove() {
        try {
            if (moveSound != null) {
                moveSound.play(0.5); // Play at 50% volume
                System.out.println("Playing move sound");
            } else {
                System.out.println("Move sound not available");
            }
        } catch (Exception e) {
            System.err.println("Error playing move sound: " + e.getMessage());
        }
    }

    /**
     * Plays the rotate sound effect.
     */
    public static void playRotate() {
        try {
            if (rotateSound != null) {
                rotateSound.play(0.5); // Play at 50% volume
                System.out.println("Playing rotate sound");
            } else {
                System.out.println("Rotate sound not available");
            }
        } catch (Exception e) {
            System.err.println("Error playing rotate sound: " + e.getMessage());
        }
    }

    /**
     * Plays the game over sound effect.
     */
    public static void playGameOver() {
        try {
            if (gameOverSound != null) {
                gameOverSound.play(0.7); // Play at 70% volume
                System.out.println("Playing game over sound");
            } else {
                System.out.println("Game over sound not available");
            }
        } catch (Exception e) {
            System.err.println("Error playing game over sound: " + e.getMessage());
        }
    }
    
    /**
     * Checks if music is currently playing.
     *
     * @return true if music is playing, false otherwise
     */
    public static boolean isMusicPlaying() {
        return musicPlaying;
    }
    
    /**
     * Sets volume for background music.
     *
     * @param volume volume level between 0.0 and 1.0
     */
    public static void setMusicVolume(double volume) {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.setVolume(Math.max(0.0, Math.min(1.0, volume)));
        }
    }
} 