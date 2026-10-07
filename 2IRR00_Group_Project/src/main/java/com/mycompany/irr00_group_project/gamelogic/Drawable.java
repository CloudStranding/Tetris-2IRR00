package com.mycompany.irr00_group_project.gamelogic;

import javafx.scene.canvas.GraphicsContext;

/**
 * Interface to draw objects on a {@code Canvas}.
 * Uses a {@code GraphicsContext}.
 */
public interface Drawable {

    /**
     * Draws this object on a {@code Canvas}.
     *
     * @param gc retrieved using {@code canvas.getGraphicsContext2D()}
     */
    void draw(GraphicsContext gc);
}
