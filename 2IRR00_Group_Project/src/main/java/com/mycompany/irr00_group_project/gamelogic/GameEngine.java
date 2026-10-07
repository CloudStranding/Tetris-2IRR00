package com.mycompany.irr00_group_project.gamelogic;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import java.util.List;

/**
 * An interface that represents a game engine for tetris.
 * Extends {@code EventHandler<KeyEvent>} as to also be able to handle input.
 *
 * @author Jayson Leander, Yingyao Feng
 */
public interface GameEngine extends EventHandler<KeyEvent> {

    /**
     * Start engine.
     *
     * @author Jayson Leander, Yingyao Feng
     */
    void start();

    /**
     * Stop engine.
     *
     * @author Jayson Leander, Yingyao Feng
     */
    void stop();

    /**
     * Pause engine.
     *
     * @author Jayson Leander, Yingyao Feng
     */
    void pause();

    /**
     * Resume engine.
     *
     * @author Jayson Leander, Yingyao Feng
     */
    void resume();

    /**
     * Update engine.
     *
     * @author Jayson Leander, Yingyao Feng
     */
    void update();

    /**
     * Restart the engine.
     *
     * @author Jayson Leander, Yinyao Feng
     */
    void restart();

    /**
     * Returns the state of the game engine.
     *
     * @return true if engine is running and false if otherwise
     */
    boolean isRunning();

    /**
     * Returns the next piece that will spawn in the game.
     * Represented as a {@code Drawable} for ease of use in GUIs.
     *
     * @return the next piece that will spawn in the game
     * @author Jayson Leander, Yingyao Feng
     */
    Drawable getNextPiece();

    /**
     * Returns the grid represented as a list of drawables.
     * These drawables have a position for ease of use in GUIs.
     *
     * @return list of drawables
     * @author Jayson Leander, Yingyao Feng
     */
    List<? extends Drawable> getGrid();

    /**
     * Return the grid represented as a 2d array
     * Every index containing 1 has a block
     *
     * @return 2d array with integers
     * @author Jayson Leander, Yingyao Feng
     */
    int[][] getCurrentGrid();
}
