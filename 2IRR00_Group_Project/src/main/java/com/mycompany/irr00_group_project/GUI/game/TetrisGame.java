package com.mycompany.irr00_group_project.game;

import javafx.scene.input.KeyCode;

public interface TetrisGame {
    void start();
    void pause();
    void resume();
    void restart();
    void handleKeyPress(KeyCode code);
    void update();
    boolean isRunning();
    int getScore();
    int[][] getNextPiece();
    int[][] getCurrentGrid();
} 