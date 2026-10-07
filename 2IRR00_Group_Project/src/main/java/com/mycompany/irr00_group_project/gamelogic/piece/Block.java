package com.mycompany.irr00_group_project.gamelogic.piece;

import java.awt.Point;

import com.mycompany.irr00_group_project.gamelogic.Drawable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Represents a {@code Drawable} tetris block.
 * Has a position and can draw itself on the canvas at that position.
 * Can be cloned.
 */
public class Block implements Drawable, Cloneable {

    private Point pos;
    private final int blockSize;
    private final Color color;

    /**
     * Creates a new Block.
     *
     * @param pos       position of the block
     * @param blockSize size of the block
     * @param color     color of the block
     */
    public Block(Point pos, int blockSize, Color color) {
        this.pos = pos;
        this.blockSize = blockSize;
        this.color = color;
    }

    /**
     * Gets the position of the block.
     *
     * @return position
     */
    public Point getPos() {
        return this.pos;
    }

    /**
     * Sets the position of the block.
     *
     * @param pos new position
     */
    public void setPos(Point pos) {
        this.pos = pos;
    }

    /**
     * Gets the color of the block.
     *
     * @return color of the block
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Draws the block on the given graphics context.
     * Draws a filled rectangle with a black border at the block's position.
     *
     * @param gc The graphics context to draw on
     */
    @Override
    public void draw(GraphicsContext gc) {
        // Calculate position in pixels
        double x = pos.x * blockSize;
        double y = pos.y * blockSize;

        // Set color
        gc.setFill(color);

        // Draw the block
        gc.fillRect(x, y, blockSize, blockSize);

        // Draw border
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);
        gc.strokeRect(x, y, blockSize, blockSize);
    }

    /**
     * Creates a deep copy of this block.
     * Creates a new Point object to avoid sharing references.
     *
     * @return A new Block with the same position, size, and color
     */
    @Override
    public Block clone() {
        try {
            Block clone = (Block) super.clone();
            // Create new Point object to avoid sharing references
            clone.pos = new Point(this.pos.x, this.pos.y);
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
