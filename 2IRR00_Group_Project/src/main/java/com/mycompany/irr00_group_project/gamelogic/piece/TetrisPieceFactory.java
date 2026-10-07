package com.mycompany.irr00_group_project.gamelogic.piece;

import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Factory to create tetris pieces based on {@code TetrisPieceType}.
 * Needs {@code gridWidth, gridHeight, blockSize} to calculate some pieces positions.
 * When a piece is created it will have a position in the middle of the grid.
 * Also assigns a color and size to the pieces.
 *
 * @author Jayson Leander, Yingyao Feng
 */
public class TetrisPieceFactory {

    private final int gridWidth;
    private final int gridHeight;
    private final int blockSize;
    private final List<Block> blocks;

    /**
     * Constructor.
     *
     * @param gridWidth width of grid
     * @param gridHeight height of grid
     * @param blockSize size of individual blocks that form a piece
     * @author Jayson Leander, Yingyao Feng
     */
    public TetrisPieceFactory(int gridWidth, int gridHeight, int blockSize) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.blockSize = blockSize;
        this.blocks = new ArrayList<>();
    }

    /**
     * Creates a tetris piece based on passed type.
     * Piece will have a position in the middle of the grid.
     *
     * @param type type of piece to create
     * @return a tetris piece, with a position in the middle of the grid, based on passed type
     * @author Jayson Leander, Yingyao Feng
     */
    public TetrisPiece createTetrisPiece(TetrisPieceType type) {
        this.blocks.clear();

        return switch (type) {
            case I -> createIPiece(Color.CYAN);
            case Z -> createZPiece(Color.RED);
            case L -> createLPiece(Color.ORANGE);
            case J -> createJPiece(Color.BLUE);
            case O -> createOPiece(Color.YELLOW);
            case S -> createSPiece(Color.GREEN);
            case T -> createTPiece(Color.PURPLE);
            case BOUNDARY -> createBoundaryPiece(Color.BLACK);
        };
    }

    private TetrisPiece createBoundaryPiece(Color color) {
        for (int y = 0; y < this.gridHeight; y++) {
            blocks.add(new Block(new Point(-1, y), this.blockSize, color));
            blocks.add(new Block(new Point(this.gridWidth, y), this.blockSize, color));
        }
        for (int x = -1; x <= this.gridWidth; x++) {
            blocks.add(new Block(new Point(x, this.gridHeight), this.blockSize, color));
        }
        return new TetrisPiece(new ArrayList<>(this.blocks));
    }

    private TetrisPiece createIPiece(Color color) {
        int startX = (gridWidth) / 2;
        for (int i = 0; i < 4; i++) {
            blocks.add(new Block(new Point(startX, i), this.blockSize, color));
        }
        return new TetrisPiece(new ArrayList<>(this.blocks));
    }

    private TetrisPiece createZPiece(Color color) {
        blocks.add(new Block(new Point(4, 0), this.blockSize, color));
        blocks.add(new Block(new Point(5, 0), this.blockSize, color));
        blocks.add(new Block(new Point(5, 1), this.blockSize, color));
        blocks.add(new Block(new Point(6, 1), this.blockSize, color));
        return new TetrisPiece(new ArrayList<>(this.blocks));
    }

    private TetrisPiece createOPiece(Color color) {
        blocks.add(new Block(new Point(4, 0), this.blockSize, color));
        blocks.add(new Block(new Point(5, 0), this.blockSize, color));
        blocks.add(new Block(new Point(4, 1), this.blockSize, color));
        blocks.add(new Block(new Point(5, 1), this.blockSize, color));
        return new TetrisPiece(new ArrayList<>(this.blocks));
    }

    private TetrisPiece createSPiece(Color color) {
        blocks.add(new Block(new Point(5, 0), this.blockSize, color));
        blocks.add(new Block(new Point(6, 0), this.blockSize, color));
        blocks.add(new Block(new Point(4, 1), this.blockSize, color));
        blocks.add(new Block(new Point(5, 1), this.blockSize, color));
        return new TetrisPiece(new ArrayList<>(this.blocks));
    }

    private TetrisPiece createLPiece(Color color) {
        blocks.add(new Block(new Point(4, 0), this.blockSize, color));
        blocks.add(new Block(new Point(4, 1), this.blockSize, color));
        blocks.add(new Block(new Point(4, 2), this.blockSize, color));
        blocks.add(new Block(new Point(5, 2), this.blockSize, color));
        return new TetrisPiece(new ArrayList<>(this.blocks));
    }

    private TetrisPiece createJPiece(Color color) {
        blocks.add(new Block(new Point(5, 0), this.blockSize, color));
        blocks.add(new Block(new Point(5, 1), this.blockSize, color));
        blocks.add(new Block(new Point(5, 2), this.blockSize, color));
        blocks.add(new Block(new Point(4, 2), this.blockSize, color));
        return new TetrisPiece(new ArrayList<>(this.blocks));
    }

    private TetrisPiece createTPiece(Color color) {
        blocks.add(new Block(new Point(4, 0), this.blockSize, color));
        blocks.add(new Block(new Point(5, 0), this.blockSize, color));
        blocks.add(new Block(new Point(6, 0), this.blockSize, color));
        blocks.add(new Block(new Point(5, 1), this.blockSize, color));
        return new TetrisPiece(new ArrayList<>(this.blocks));
    }
}
