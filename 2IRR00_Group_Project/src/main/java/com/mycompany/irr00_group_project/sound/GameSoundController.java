/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.irr00_group_project.sound;

import com.mycompany.irr00_group_project.gamelogic.MovementType;
/**
 *
 * @author steve
 */
public class GameSoundController {
    
    public void onPieceMoved(MovementType type) {
        if (type == MovementType.DOWN) {
            SoundManager.playDrop();
        } else if (type == MovementType.LEFT || type == MovementType.RIGHT) {
            // no music
        }
    }

    public void onPieceRotated() {
        SoundManager.playRotate();
        System.out.println("SoundRotate played");
    }

    public void onGameOver() {
        SoundManager.playGameOver();
    }    
}
