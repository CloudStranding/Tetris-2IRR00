package com.mycompany.irr00_group_project.gamelogic.piece;

import com.mycompany.irr00_group_project.gamelogic.Drawable;
import com.mycompany.irr00_group_project.gamelogic.MovementType;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Object that represents a tetris piece.
 * It is represented as a {@code List<Block>}.
 * These blocks contain positions so this object doesn't have to.
 * Even though it doesn't store its position,
 * this object has the responsibility of moving itself.
 * Implements {@code Drawable}, draws itself by drawing the {@code List<Block>}.
 * Object can be deep cloned.
 *
 * @author Jayson Leander, Yingyao Feng
 */
public class TetrisPiece implements Drawable, Cloneable {

    private List<Block> blocks;

    /**
     * Constructor.
     *
     * @param blocks list of blocks
     * @author Jayson Leander, Yingyao Feng
     */
    public TetrisPiece(List<Block> blocks) {
        this.blocks = blocks;
    }

    /**
     * Returns the list of blocks representing the piece.
     *
     * @return list of blocks
     * @author Jayson Leander, Yingyao Feng
     */
    public List<Block> getBlocks() {
        return this.blocks;
    }

    /**
     * Performs a move to this piece.
     * Move is determined by {@code MovementType}.
     *
     * @param move type of move
     * @author Jayson Leander, Yingyao Feng
     */
    public void performMove(MovementType move) {
        switch (move) {
            case ROTATE -> rotate();
            case RIGHT -> moveRight();
            case LEFT -> moveLeft();
            case DOWN -> moveDown();
            default -> {
            }
        }
    }

    private void moveDown() {
        for (Block block : this.blocks) {
            Point oldPos = block.getPos();
            block.setPos(new Point(
                    oldPos.x,
                    oldPos.y + 1
            ));
        }
    }

    private void moveRight() {
        for (Block block : this.blocks) {
            Point oldPos = block.getPos();
            block.setPos(new Point(
                    oldPos.x + 1,
                    oldPos.y
            ));
        }
    }

    private void moveLeft() {
        for (Block block : this.blocks) {
            Point oldPos = block.getPos();
            block.setPos(new Point(
                    oldPos.x - 1,
                    oldPos.y
            ));
        }
    }

    private void rotate() {
        Point origin = blocks.getFirst().getPos();

        for (Block block : this.blocks) {
            Point pos = block.getPos();

            int relX = pos.x - origin.x;
            int relY = pos.y - origin.y;

            int rotatedX = -relY;
            int rotatedY = relX;

            block.setPos(new Point(
                    origin.x + rotatedX,
                    origin.y + rotatedY
            ));
        }
    }

    /**
     * Checks if this piece intersects another tetris piece.
     * It determines this based on the position of the blocks.
     *
     * @param piece piece to check collision with
     * @return true if pieces intersect each other and false if otherwise
     * @author Jayson Leander, Yingyao Feng
     */
    public boolean intersects(TetrisPiece piece) {
        Set<Point> myPositions = this.blocks.stream()
                .map(Block::getPos)
                .collect(Collectors.toSet());

        for (Block b : piece.getBlocks()) {
            if (myPositions.contains(b.getPos())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void draw(GraphicsContext gc) {
        for (Block block : this.blocks) {
            block.draw(gc);
        }
    }

    @Override
    public TetrisPiece clone() {
        try {
            TetrisPiece clone = (TetrisPiece) super.clone();
            clone.blocks = this.getBlocks().stream()
                    .map(Block::clone)
                    .collect(Collectors.toList());
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
