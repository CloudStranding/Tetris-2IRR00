package com.mycompany.irr00_group_project.gamelogic;

import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 * An interface that represents a game engine for tetris.
 * Extends {@code EventHandler<KeyEvent>} as to also be able to handle input.
 */
public interface GameEngine extends EventHandler<KeyEvent> {

    /**
     * Starts the engine.
     */
    void start();

    /**
     * Stops the engine.
     */
    void stop();

    /**
     * Pauses the engine.
     */
    void pause();

    /**
     * Updates the engine.
     */
    void update();

    /**
     * Gets the next piece that will spawn in the game.
     * Represented as a {@code Drawable} for ease of use in GUIs.
     *
     * @return the next piece that will spawn in the game
     */
    Drawable getNextPiece();

    /**
     * Gets the grid represented as a 2d array.
     * Every index containing 1 has a block.
     *
     * @return 2d array with integers
     */
    int[][] getCurrentGrid();

    /**
     * Gets the grid represented as a list of drawables.
     * These drawables have a position for ease of use in GUIs.
     *
     * @return list of drawables
     */
    List<? extends Drawable> getGrid();
}
