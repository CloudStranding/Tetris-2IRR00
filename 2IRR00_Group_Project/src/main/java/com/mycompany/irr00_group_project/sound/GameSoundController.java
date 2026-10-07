package com.mycompany.irr00_group_project.sound;

/**
 * Controller class for triggering game sound effects.
 * Provides methods to play sound effects in response to game events:
 * piece movement, rotation, and game over.
 * 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt 
 * to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to 
 * edit this template
 *
 * @author Wenjie Li
 */
public class GameSoundController {
    
    /**
     * Plays the sound effect for piece movement.
     */
    public void onPieceMoved() {
        SoundManager.playMove();
        System.out.println("SoundMove played");
    }
    
    /**
     * Plays the sound effect for piece rotation.
     */
    public void onPieceRotated() {
        SoundManager.playRotate();
        System.out.println("SoundRotate played");
    }

    /**
     * Plays the sound effect for game over.
     */
    public void onGameOver() {
        SoundManager.playGameOver();
        System.out.println("SoundGameOver played");
    }    
} 