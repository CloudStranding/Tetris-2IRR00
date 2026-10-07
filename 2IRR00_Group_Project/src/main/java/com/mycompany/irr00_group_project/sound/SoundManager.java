/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.irr00_group_project.sound;

import javafx.scene.media.AudioClip;
import java.net.URL;


/**
 *
 * @author steve
 */
public class SoundManager {
    private static final AudioClip dropSound = loadSound("drop.wav");
    private static final AudioClip rotateSound = loadSound("rotate.wav");
    private static final AudioClip gameOverSound = loadSound("gameover.wav");

    private static AudioClip loadSound(String fileName) {
    try {
        URL url = SoundManager.class.getResource("/sounds/" + fileName);
        System.out.println("Loaded URL: " + url);  // 加上这一行看看是否为 null
        return new AudioClip(url.toExternalForm());
    } catch (Exception e) {
        System.err.println("Error loading sound: " + fileName);
        return null;
    }
}
    
    public static void playDrop() {
        if (dropSound != null) dropSound.play();
    }

    public static void playRotate() {
        if (rotateSound != null) rotateSound.play();
    }

    public static void playGameOver() {
        if (gameOverSound != null) gameOverSound.play();
    }
    
    
}
