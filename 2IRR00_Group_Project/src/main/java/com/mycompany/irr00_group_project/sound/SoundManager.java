/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.irr00_group_project.sound;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;


/**
 * Utility class for managing all game sounds and background music.
 * Provides methods to play sound effects (move, rotate, game over) and background music.
 * Uses AudioClip for short effects and MediaPlayer for looping background music.
 * Designed to be used statically across the game.
 *
 * @author Wenjie Li
 */

public class SoundManager {
    private static final AudioClip moveSound = loadSound("move.wav");
    private static final AudioClip rotateSound = loadSound("rotate.wav");
    private static final AudioClip gameOverSound = loadSound("gameover.wav");

    private static MediaPlayer backgroundMusicPlayer;
    
    public static void playBackgroundMusic() {
        try {
            if (backgroundMusicPlayer == null) {
                URL url = SoundManager.class.getResource("/sounds/tetrismusic.wav");
                Media media = new Media(url.toExternalForm());
                backgroundMusicPlayer = new MediaPlayer(media);
                backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                backgroundMusicPlayer.play();
            } else {
                backgroundMusicPlayer.play();
            }
        } catch (Exception e) {
            System.err.println("Error loading background music: " + e.getMessage());
        }
    }
    
    public static void pauseBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.pause();
        }
    }

    public static void stopBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
            backgroundMusicPlayer = null;
        }
    }
    
    private static AudioClip loadSound(String fileName) {
        try {
            URL url = SoundManager.class.getResource("/sounds/" + fileName);
            System.out.println("Loaded URL: " + url);  
            return new AudioClip(url.toExternalForm());
        } catch (Exception e) {
            System.err.println("Error loading sound: " + fileName);
            return null;
        }
    }
    
    public static void playMove() {
        if (moveSound != null) moveSound.play();
    }

    public static void playRotate() {
        if (rotateSound != null) rotateSound.play();
    }

    public static void playGameOver() {
        if (gameOverSound != null) gameOverSound.play();
    }    
}
