package com.mycompany.irr00_group_project.gamelogic.piece;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;

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
        System.out.println("TetrisPieceFactory created with blockSize: " + blockSize);
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
        // Create a new blocks list for each piece - this fixes the bug!
        List<Block> blocks = new ArrayList<>();

        TetrisPiece piece = switch (type) {
            case I -> createIPiece(Color.CYAN, blocks);
            case Z -> createZPiece(Color.RED, blocks);
            case L -> createLPiece(Color.ORANGE, blocks);
            case J -> createJPiece(Color.BLUE, blocks);
            case O -> createOPiece(Color.YELLOW, blocks);
            case S -> createSPiece(Color.GREEN, blocks);
            case T -> createTPiece(Color.PURPLE, blocks);
            case BOUNDARY -> createBoundaryPiece(Color.BLACK, blocks);
        };
        
        // Basic validation - only log errors
        if (type != TetrisPieceType.BOUNDARY && piece.getBlocks().size() != 4) {
            System.err.println("ERROR: " + type + " piece has " + piece.getBlocks().size() + 
                             " blocks instead of 4!");
        }
        
        return piece;
    }

    private TetrisPiece createBoundaryPiece(Color color, List<Block> blocks) {
        for (int y = 0; y < this.gridHeight; y++) {
            blocks.add(new Block(new Point(-1, y), this.blockSize, color));
            blocks.add(new Block(new Point(this.gridWidth, y), this.blockSize, color));
        }
        for (int x = -1; x <= this.gridWidth; x++) {
            blocks.add(new Block(new Point(x, this.gridHeight), this.blockSize, color));
        }
        
        return new TetrisPiece(blocks, TetrisPieceType.BOUNDARY);
    }

    private TetrisPiece createIPiece(Color color, List<Block> blocks) {
        // I piece: 4 blocks in a row, all at y=0 for full visibility
        int startX = Math.max(0, (gridWidth - 4) / 2);
        
        for (int i = 0; i < 4; i++) {
            Point pos = new Point(startX, i);
            blocks.add(new Block(pos, this.blockSize, color));
        }
        return new TetrisPiece(blocks, TetrisPieceType.I);
    }

    private TetrisPiece createZPiece(Color color, List<Block> blocks) {
        // Z piece: start from y=0 to show full shape immediately
        int centerX = Math.max(1, Math.min(gridWidth - 2, gridWidth / 2));
        
        blocks.add(new Block(new Point(centerX - 1, 0), this.blockSize, color));
        blocks.add(new Block(new Point(centerX, 0), this.blockSize, color));
        blocks.add(new Block(new Point(centerX, 1), this.blockSize, color));
        blocks.add(new Block(new Point(centerX + 1, 1), this.blockSize, color));
        return new TetrisPiece(blocks, TetrisPieceType.Z);
    }

    private TetrisPiece createOPiece(Color color, List<Block> blocks) {
        // O piece: 2x2 square, start from y=0
        int centerX = Math.max(1, Math.min(gridWidth - 2, gridWidth / 2));
        
        blocks.add(new Block(new Point(centerX - 1, 0), this.blockSize, color));
        blocks.add(new Block(new Point(centerX, 0), this.blockSize, color));
        blocks.add(new Block(new Point(centerX - 1, 1), this.blockSize, color));
        blocks.add(new Block(new Point(centerX, 1), this.blockSize, color));
        return new TetrisPiece(blocks, TetrisPieceType.O);
    }

    private TetrisPiece createSPiece(Color color, List<Block> blocks) {
        // S piece: start from y=0
        int centerX = Math.max(1, Math.min(gridWidth - 2, gridWidth / 2));
        
        blocks.add(new Block(new Point(centerX, 0), this.blockSize, color));
        blocks.add(new Block(new Point(centerX + 1, 0), this.blockSize, color));
        blocks.add(new Block(new Point(centerX - 1, 1), this.blockSize, color));
        blocks.add(new Block(new Point(centerX, 1), this.blockSize, color));
        return new TetrisPiece(blocks, TetrisPieceType.S);
    }

    private TetrisPiece createLPiece(Color color, List<Block> blocks) {
        // L piece: start from y=0 to show full shape
        int centerX = Math.max(1, Math.min(gridWidth - 2, gridWidth / 2));
        
        blocks.add(new Block(new Point(centerX, 0), this.blockSize, color));
        blocks.add(new Block(new Point(centerX, 1), this.blockSize, color));
        blocks.add(new Block(new Point(centerX, 2), this.blockSize, color));
        blocks.add(new Block(new Point(centerX + 1, 2), this.blockSize, color));
        return new TetrisPiece(blocks, TetrisPieceType.L);
    }

    private TetrisPiece createJPiece(Color color, List<Block> blocks) {
        // J piece: start from y=0 to show full shape
        int centerX = Math.max(1, Math.min(gridWidth - 2, gridWidth / 2));
        
        blocks.add(new Block(new Point(centerX, 0), this.blockSize, color));
        blocks.add(new Block(new Point(centerX, 1), this.blockSize, color));
        blocks.add(new Block(new Point(centerX, 2), this.blockSize, color));
        blocks.add(new Block(new Point(centerX - 1, 2), this.blockSize, color));
        return new TetrisPiece(blocks, TetrisPieceType.J);
    }

    private TetrisPiece createTPiece(Color color, List<Block> blocks) {
        // T piece: start from y=0 for full visibility
        int centerX = Math.max(1, Math.min(gridWidth - 2, gridWidth / 2));
        
        blocks.add(new Block(new Point(centerX - 1, 0), this.blockSize, color));
        blocks.add(new Block(new Point(centerX, 0), this.blockSize, color));
        blocks.add(new Block(new Point(centerX + 1, 0), this.blockSize, color));
        blocks.add(new Block(new Point(centerX, 1), this.blockSize, color));
        return new TetrisPiece(blocks, TetrisPieceType.T);
    }
}
