/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.irr00_group_project.sound;

/**
 * Controller class for triggering game sound effects.
 * Provides methods to play sound effects in response to game events:
 * piece movement, rotation, and game over.
 *
 * @author Wenjie Li
 */
public class GameSoundController {
    

    public void onPieceMoved() {
        SoundManager.playMove();
        System.out.println("SoundRotate played");
    }
    
    public void onPieceRotated() {
        SoundManager.playRotate();
        System.out.println("SoundRotate played");
    }

    public void onGameOver() {
        SoundManager.playGameOver();
        System.out.println("SoundGameOver played");
    }    
}
