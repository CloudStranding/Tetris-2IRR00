package com.mycompany.irr00_group_project.gamelogic.piece;

import java.awt.Point;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.mycompany.irr00_group_project.gamelogic.Drawable;
import com.mycompany.irr00_group_project.gamelogic.MovementType;

import javafx.scene.canvas.GraphicsContext;

/**
 * Represents a tetris piece.
 * It is represented as a {@code List<Block>}.
 * These blocks contain positions so this object doesn't have to.
 * Even though it doesn't store its position,
 * this object has the responsibility of moving itself.
 * Implements {@code Drawable}, draws itself by drawing the {@code List<Block>}.
 * Object can be deep cloned.
 */
public class TetrisPiece implements Drawable, Cloneable {

    private List<Block> blocks;
    private final TetrisPieceType type;

    /**
     * Creates a new TetrisPiece.
     * Type of piece will be {@code null}.
     *
     * @param blocks list of blocks
     */
    public TetrisPiece(List<Block> blocks) {
        this(blocks, null);
    }

    /**
     * Creates a new TetrisPiece.
     *
     * @param blocks list of blocks
     * @param type   piece type
     */
    public TetrisPiece(List<Block> blocks, TetrisPieceType type) {
        this.blocks = blocks;
        this.type = type;
    }

    /**
     * Gets the list of blocks representing the piece.
     *
     * @return list of blocks
     */
    public List<Block> getBlocks() {
        return this.blocks;
    }

    /**
     * Performs a move to this piece.
     * Move is determined by {@code MovementType}.
     * Rotations do not apply to pieces with {@code TetrisPieceType.O}.
     * All moves do not apply to {@code TetrisPieceType.BOUNDARY}.
     *
     * @param move type of move
     */
    public void performMove(MovementType move) {
        if (TetrisPieceType.BOUNDARY.equals(this.type)) {
            return;
        }

        switch (move) {
            case ROTATE -> rotate();
            case RIGHT -> moveRight();
            case LEFT -> moveLeft();
            case DOWN -> moveDown();
            default -> {
            }
        }
    }

    /**
     * Moves the piece down by one unit.
     * Updates the position of each block in the piece.
     */
    private void moveDown() {
        for (Block block : this.blocks) {
            Point oldPos = block.getPos();
            block.setPos(new Point(
                    oldPos.x,
                    oldPos.y + 1
            ));
        }
    }

    /**
     * Moves the piece right by one unit.
     * Updates the position of each block in the piece.
     */
    private void moveRight() {
        for (Block block : this.blocks) {
            Point oldPos = block.getPos();
            block.setPos(new Point(
                    oldPos.x + 1,
                    oldPos.y
            ));
        }
    }

    /**
     * Moves the piece left by one unit.
     * Updates the position of each block in the piece.
     */
    private void moveLeft() {
        for (Block block : this.blocks) {
            Point oldPos = block.getPos();
            block.setPos(new Point(
                    oldPos.x - 1,
                    oldPos.y
            ));
        }
    }

    /**
     * Rotates the piece 90 degrees clockwise around its first block.
     * Does nothing for O pieces.
     * Updates the position of each block in the piece.
     */
    private void rotate() {
        if (TetrisPieceType.O.equals(this.type)) {
            return;
        }

        Point origin = blocks.getFirst().getPos();

        for (Block block : this.blocks) {
            Point pos = block.getPos();

            int relX = pos.x - origin.x;
            int relY = pos.y - origin.y;

            int rotatedX = -relY;

            block.setPos(new Point(
                    origin.x + rotatedX,
                    origin.y + relX
            ));
        }
    }

    /**
     * Checks if this piece intersects another tetris piece.
     * It determines this based on the position of the blocks.
     *
     * @param piece piece to check collision with
     * @return true if pieces intersect each other and false if otherwise
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

    /**
     * Draws the piece on the given graphics context.
     *
     * @param gc The graphics context to draw on
     */
    @Override
    public void draw(GraphicsContext gc) {
        for (Block block : this.blocks) {
            block.draw(gc);
        }
    }

    /**
     * Creates a deep copy of this piece.
     *
     * @return A new TetrisPiece with the same blocks and type
     */
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
