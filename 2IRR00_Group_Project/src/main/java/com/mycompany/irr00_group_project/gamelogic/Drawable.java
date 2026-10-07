package com.mycompany.irr00_group_project.gamelogic;

import javafx.scene.canvas.GraphicsContext;

//TODO could be removed as now array grid is used
/**
 * Interface to draw objects on a {@code Canvas}.
 * Uses a {@code GraphicsContext}.
 *
 * @author Jayson Leander, Yinyao Feng
 */
public interface Drawable {

    /**
     * Draws this object on a {@code Canvas}.
     *
     * @param gc retrieved using {@code canvas.getGraphicsContext2D()}
     * @author Jayson Leander, Yinyao Feng
     */
    void draw(GraphicsContext gc);
}
