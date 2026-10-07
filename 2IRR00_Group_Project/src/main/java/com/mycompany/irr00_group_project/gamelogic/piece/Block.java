package com.mycompany.irr00_group_project.gamelogic.piece;

import com.mycompany.irr00_group_project.gamelogic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;

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
        gc.setFill(this.color);
        gc.fillRect(this.pos.getX() * this.size,
                this.pos.getY() * this.size,
                this.size,
                this.size);
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
