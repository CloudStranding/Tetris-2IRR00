package com.mycompany.irr00_group_project.gamelogic.piece;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;

/**
 * Factory for creating tetris pieces of different types.
 * Creates pieces with blocks positioned according to the standard Tetris piece patterns.
 * Also creates boundary pieces for grid boundaries.
 */
public class TetrisPieceFactory {

    private final int gridWidth;
    private final int gridHeight;
    private final int blockSize;

    /**
     * Creates a new TetrisPieceFactory.
     *
     * @param gridWidth  width of the grid
     * @param gridHeight height of the grid
     * @param blockSize  size of individual blocks
     */
    public TetrisPieceFactory(int gridWidth, int gridHeight, int blockSize) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.blockSize = blockSize;
    }

    /**
     * Creates a tetris piece of the specified type.
     *
     * @param type the type of piece to create
     * @return a new tetris piece
     */
    public TetrisPiece createTetrisPiece(TetrisPieceType type) {
        return switch (type) {
            case I -> createIPiece();
            case L -> createLPiece();
            case J -> createJPiece();
            case T -> createTPiece();
            case S -> createSPiece();
            case Z -> createZPiece();
            case O -> createOPiece();
            case BOUNDARY -> createBoundaryPiece();
        };
    }

    /**
     * Creates an I-shaped piece.
     *
     * @return a new I-shaped piece
     */
    private TetrisPiece createIPiece() {
        List<Block> blocks = new ArrayList<>();
        int startX = gridWidth / 2 - 2;
        int startY = 0; // Changed from -1 to 0 to make piece fully visible

        for (int i = 0; i < 4; i++) {
            blocks.add(new Block(new Point(startX + i, startY), blockSize, Color.CYAN));
        }

        return new TetrisPiece(blocks, TetrisPieceType.I);
    }

    /**
     * Creates an L-shaped piece.
     *
     * @return a new L-shaped piece
     */
    private TetrisPiece createLPiece() {
        List<Block> blocks = new ArrayList<>();
        int startX = gridWidth / 2 - 1;
        int startY = 0; // Changed to 0 so all blocks are fully visible

        // L shape: three blocks horizontally, one block up and to the right
        blocks.add(new Block(new Point(startX, startY + 1), blockSize, Color.ORANGE));     // Y = 1
        blocks.add(new Block(new Point(startX + 1, startY + 1), blockSize, Color.ORANGE)); // Y = 1
        blocks.add(new Block(new Point(startX + 2, startY + 1), blockSize, Color.ORANGE)); // Y = 1
        blocks.add(new Block(new Point(startX + 2, startY), blockSize, Color.ORANGE));     // Y = 0

        return new TetrisPiece(blocks, TetrisPieceType.L);
    }

    /**
     * Creates a J-shaped piece.
     *
     * @return a new J-shaped piece
     */
    private TetrisPiece createJPiece() {
        List<Block> blocks = new ArrayList<>();
        int startX = gridWidth / 2 - 1;
        int startY = 0; // Changed to 0 so all blocks are fully visible

        // J shape: three blocks horizontally, one block up and to the left
        blocks.add(new Block(new Point(startX, startY + 1), blockSize, Color.BLUE));     // Y = 1
        blocks.add(new Block(new Point(startX + 1, startY + 1), blockSize, Color.BLUE)); // Y = 1
        blocks.add(new Block(new Point(startX + 2, startY + 1), blockSize, Color.BLUE)); // Y = 1
        blocks.add(new Block(new Point(startX, startY), blockSize, Color.BLUE));         // Y = 0

        return new TetrisPiece(blocks, TetrisPieceType.J);
    }

    /**
     * Creates a T-shaped piece.
     *
     * @return a new T-shaped piece
     */
    private TetrisPiece createTPiece() {
        List<Block> blocks = new ArrayList<>();
        int startX = gridWidth / 2 - 1;
        int startY = 0; // Changed to 0 so all blocks are fully visible

        // T shape: three blocks horizontally, one block up and in the middle
        blocks.add(new Block(new Point(startX, startY + 1), blockSize, Color.PURPLE));     // Y = 1
        blocks.add(new Block(new Point(startX + 1, startY + 1), blockSize, Color.PURPLE)); // Y = 1
        blocks.add(new Block(new Point(startX + 2, startY + 1), blockSize, Color.PURPLE)); // Y = 1
        blocks.add(new Block(new Point(startX + 1, startY), blockSize, Color.PURPLE));     // Y = 0

        return new TetrisPiece(blocks, TetrisPieceType.T);
    }

    /**
     * Creates an S-shaped piece.
     *
     * @return a new S-shaped piece
     */
    private TetrisPiece createSPiece() {
        List<Block> blocks = new ArrayList<>();
        int startX = gridWidth / 2 - 1;
        int startY = 0; // Changed to 0 so all blocks are fully visible

        // S shape: zigzag going up and to the right
        blocks.add(new Block(new Point(startX, startY + 1), blockSize, Color.GREEN));     // Y = 1
        blocks.add(new Block(new Point(startX + 1, startY + 1), blockSize, Color.GREEN)); // Y = 1
        blocks.add(new Block(new Point(startX + 1, startY), blockSize, Color.GREEN));     // Y = 0
        blocks.add(new Block(new Point(startX + 2, startY), blockSize, Color.GREEN));     // Y = 0

        return new TetrisPiece(blocks, TetrisPieceType.S);
    }

    /**
     * Creates a Z-shaped piece.
     *
     * @return a new Z-shaped piece
     */
    private TetrisPiece createZPiece() {
        List<Block> blocks = new ArrayList<>();
        int startX = gridWidth / 2 - 1;
        int startY = 0; // Changed to 0 so all blocks are fully visible

        // Z shape: zigzag going up and to the left
        blocks.add(new Block(new Point(startX, startY), blockSize, Color.RED));         // Y = 0
        blocks.add(new Block(new Point(startX + 1, startY), blockSize, Color.RED));     // Y = 0
        blocks.add(new Block(new Point(startX + 1, startY + 1), blockSize, Color.RED)); // Y = 1
        blocks.add(new Block(new Point(startX + 2, startY + 1), blockSize, Color.RED)); // Y = 1

        return new TetrisPiece(blocks, TetrisPieceType.Z);
    }

    /**
     * Creates an O-shaped piece.
     *
     * @return a new O-shaped piece
     */
    private TetrisPiece createOPiece() {
        List<Block> blocks = new ArrayList<>();
        int startX = gridWidth / 2 - 1;
        int startY = 0; // Changed to 0 so all blocks are fully visible

        // O shape: 2x2 square
        blocks.add(new Block(new Point(startX, startY), blockSize, Color.YELLOW));         // Y = 0
        blocks.add(new Block(new Point(startX + 1, startY), blockSize, Color.YELLOW));     // Y = 0
        blocks.add(new Block(new Point(startX, startY + 1), blockSize, Color.YELLOW));     // Y = 1
        blocks.add(new Block(new Point(startX + 1, startY + 1), blockSize, Color.YELLOW)); // Y = 1

        return new TetrisPiece(blocks, TetrisPieceType.O);
    }

    /**
     * Creates a boundary piece.
     *
     * @return a new boundary piece
     */
    private TetrisPiece createBoundaryPiece() {
        List<Block> blocks = new ArrayList<>();

        // Left wall
        for (int y = -4; y < gridHeight + 4; y++) {
            blocks.add(new Block(new Point(-1, y), blockSize, Color.BLACK));
        }

        // Right wall
        for (int y = -4; y < gridHeight + 4; y++) {
            blocks.add(new Block(new Point(gridWidth, y), blockSize, Color.BLACK));
        }

        // Bottom wall
        for (int x = -1; x <= gridWidth; x++) {
            blocks.add(new Block(new Point(x, gridHeight), blockSize, Color.BLACK));
        }

        return new TetrisPiece(blocks, TetrisPieceType.BOUNDARY);
    }
}
