package com.mycompany.irr00_group_project.gamelogic.grid;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mycompany.irr00_group_project.gamelogic.MovementType;
import com.mycompany.irr00_group_project.gamelogic.piece.Block;
import com.mycompany.irr00_group_project.gamelogic.piece.TetrisPiece;

/**
 * Will manage a tetris grid.
 * A grid is represented as a {@code List<TetrisPiece>} containing tetris pieces.
 * This works as the tetris pieces contain blocks that store position.
 * Contains methods to move pieces around on the grid.
 * Movement is first checked with {@code GridCollisionDetector}.
 * Also has functionality to detect and clear full lines on the grid.
 * In addition to the {@code List<TetrisPiece>} the grid is also represented as a {@code int[][]}.
 * This is an option for the GUI to draw.
 *
 * @author Jayson Leander, Yingyao Feng
 */
public class GridManager {

    private final List<TetrisPiece> pieces;
    private final TetrisPiece boundary;
    private final GridCollisionDetector collisionDetector;
    private final int gridWidth;
    private final int gridHeight;
    private final int[][] grid;

    /**
     * Constructor.
     *
     * @param boundary tetris piece representing boundary of the grid
     * @param gridWidth width of the grid
     * @param gridHeight height of the grid
     * @author Jayson Leander, Yingyao Feng
     */
    public GridManager(TetrisPiece boundary, int gridWidth, int gridHeight) {
        this.boundary = boundary;
        this.pieces = new ArrayList<>();
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.grid = new int[gridHeight][gridWidth];
        this.collisionDetector = new GridCollisionDetector(this.pieces, this.boundary);
    }

    /**
     * Adds a tetris piece to the grid.
     * Will only do so if it doesn't cause a collision.
     *
     * @param piece piece to add
     * @return true if piece has been added and false otherwise
     * @author Jayson Leander, Yingyao Feng
     */
    public boolean addPiece(TetrisPiece piece) {
        if (collisionDetector.canBeAdded(piece)) {
            addPieceToGrid(piece);
            return this.pieces.add(piece);
        }

        return false;
    }

    private void addPieceToGrid(TetrisPiece piece) {
        for (Block block : piece.getBlocks()) {
            Point pos = block.getPos();
            if (pos.x >= 0 && pos.x < gridWidth && pos.y >= 0 && pos.y < gridHeight) {
                grid[pos.y][pos.x] = 1;
            }
        }
    }

    private void removePieceFromGrid(TetrisPiece piece) {
        for (Block block : piece.getBlocks()) {
            Point pos = block.getPos();
            if (pos.x >= 0 && pos.x < gridWidth && pos.y >= 0 && pos.y < gridHeight) {
                grid[pos.y][pos.x] = 0;
            }
        }
    }

    private void fullGridRebuild() {
        for (int i = 0; i < gridHeight; i++) {
            Arrays.fill(grid[i], 0);
        }

        for (TetrisPiece piece : pieces) {
            addPieceToGrid(piece);
        }
    }

    /**
     * Return the grid as a 2d array
     * Any index with a 1 has a block.
     *
     * @return 2d array as grid
     * @author Jayson Leander, Yinyao Feng
     */
    public int[][] getGridArray() {
        return grid;
    }

    /**
     * Performs a move to a tetris piece on the grid.
     * Will only do so if it doesn't cause a collision.
     *
     * @param piece piece to move
     * @param move movement type
     * @return true if piece has been moved and false otherwise
     * @author Jayson Leander, Yingyao Feng
     */
    public boolean performMove(TetrisPiece piece, MovementType move) {
        if (collisionDetector.isMoveValid(piece, move)) {
            removePieceFromGrid(piece);
            piece.performMove(move);
            addPieceToGrid(piece);
            return true;
        }

        return false;
    }

    /**
     * Clears full lines on the grid.
     * Moves pieces down when necessary if line gets cleared.
     *
     * @return the number of lines cleared
     * @author Jayson Leander, Yingyao Feng
     */
    public int clearFullLines() {
        Map<Integer, List<Block>> blocksByRow = new HashMap<>();

        for (TetrisPiece piece : pieces) {
            for (Block block : piece.getBlocks()) {
                int y = block.getPos().y;
                blocksByRow.computeIfAbsent(y, k -> new ArrayList<>()).add(block);
            }
        }

        List<Integer> fullRows = blocksByRow.entrySet().stream()
                .filter(entry -> entry.getValue().size() == gridWidth)
                .map(Map.Entry::getKey)
                .sorted() // TODO maybe change for performance
                .toList();

        if (fullRows.isEmpty()) {
            return 0; // No lines cleared
        }

        int linesCleared = fullRows.size();
        System.out.println("Clearing " + linesCleared + " full lines: " + fullRows);

        for (TetrisPiece piece : pieces) {
            piece.getBlocks().removeIf(block -> fullRows.contains(block.getPos().y));
        }

        for (int clearedY : fullRows) {
            for (TetrisPiece piece : pieces) {
                for (Block block : piece.getBlocks()) {
                    if (block.getPos().y < clearedY) {
                        Point oldPos = block.getPos();

                        block.setPos(new Point(oldPos.x, oldPos.y + 1));
                    }
                }
            }
        }

        fullGridRebuild();
        
        System.out.println("Successfully cleared " + linesCleared + " lines");
        return linesCleared;
    }

    /**
     * Getter for list of pieces representing the grid.
     *
     * @return list with tetris pieces
     * @author Jayson Leander, Yingyao Feng
     */
    public List<TetrisPiece> getPieces() {
        return this.pieces;
    }
}
