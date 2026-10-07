package com.mycompany.irr00_group_project.gamelogic.piece;

import java.awt.Point;

import com.mycompany.irr00_group_project.gamelogic.Drawable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Object that represents a block.
 * A block has a position represented as a {@code Point}.
 * Can be drawn using a {@code GraphicContext} from a {@code Canvas}.
 * To this end it implements {@code Drawable} and contains a size and color.
 * Object can be deep cloned.
 *
 * @author Jayson Leander, Yingyao Feng
 */
public class Block implements Drawable, Cloneable {

    private Point pos;
    private final int size;
    private final Color color;

    /**
     * Constructor.
     *
     * @param pos position
     * @param blockSize size of the block, used for drawing
     * @param color color of the block, used for drawing
     * @author Jayson Leander, Yingyao Feng
     */
    public Block(Point pos, int blockSize, Color color) {
        this.pos = pos;
        this.size = blockSize;
        this.color = color;
    }

    @Override
    public void draw(GraphicsContext gc) {
        double x = this.pos.getX() * this.size;
        double y = this.pos.getY() * this.size;
        
        // Don't draw boundary blocks (they have BLACK color and negative positions)
        // Don't draw blocks that are completely outside the visible area
        if (this.color == Color.BLACK || this.pos.getY() < 0 || x < 0) {
            return;
        }
        
        // Fill the block with its color
        gc.setFill(this.color);
        gc.fillRect(x, y, this.size, this.size);
        
        // Add a darker border for better visibility
        gc.setStroke(this.color.darker());
        gc.setLineWidth(2);
        gc.strokeRect(x, y, this.size, this.size);
        
        // Add a lighter inner border for 3D effect
        gc.setStroke(this.color.brighter());
        gc.setLineWidth(1);
        gc.strokeRect(x + 1, y + 1, this.size - 2, this.size - 2);
    }

    /**
     * Sets the position of the block.
     *
     * @param pos position to set block to
     * @author Jayson Leander, Yingyao Feng
     */
    public void setPos(Point pos) {
        this.pos = pos;
    }

    /**
     * Returns the position of the block as a {@code Point}.
     *
     * @return position of the block
     * @author Jayson Leander, Yingyao Feng
     */
    public Point getPos() {
        return pos;
    }

    /**
     * Returns the color of the block.
     *
     * @return color of the block
     */
    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return this.pos.toString();
    }

    @Override
    public Block clone() {
        try {
            Block clone = (Block) super.clone();
            clone.pos = (Point) this.pos.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
