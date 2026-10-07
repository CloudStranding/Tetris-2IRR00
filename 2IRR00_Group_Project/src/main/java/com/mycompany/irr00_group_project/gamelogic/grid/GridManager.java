package com.mycompany.irr00_group_project.gamelogic.grid;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.IntStream;

import com.mycompany.irr00_group_project.gamelogic.MovementType;
import com.mycompany.irr00_group_project.gamelogic.piece.Block;
import com.mycompany.irr00_group_project.gamelogic.piece.TetrisPiece;

/**
 * Manages a Tetris grid.
 * A grid is represented as a {@code List<TetrisPiece>} containing tetris pieces.
 * This works as the tetris pieces contain blocks that store position.
 * Contains methods to move pieces around on the grid.
 * Movement is first checked with {@code GridCollisionDetector}.
 * Also has functionality to detect and clear full lines on the grid.
 * In addition to the {@code List<TetrisPiece>} the grid is also represented as a {@code int[][]}.
 * This is an option for the GUI to draw.
 */
public class GridManager {

    private List<TetrisPiece> pieces;
    private final TetrisPiece boundary;
    private final GridCollisionDetector collisionDetector;
    private final int gridWidth;
    private final int gridHeight;
    private final int[][] grid;

    /**
     * Creates a new GridManager.
     *
     * @param boundary   tetris piece representing boundary of the grid
     * @param gridWidth  width of the grid
     * @param gridHeight height of the grid
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
     */
    public boolean addPiece(TetrisPiece piece) {
        if (collisionDetector.canBeAdded(piece)) {
            addPieceToGrid(piece);
            return this.pieces.add(piece);
        }

        return false;
    }

    /**
     * Adds a piece to the grid array representation.
     *
     * @param piece the piece to add
     */
    private void addPieceToGrid(TetrisPiece piece) {
        for (Block block : piece.getBlocks()) {
            Point pos = block.getPos();
            if (pos.x >= 0 && pos.x < gridWidth && pos.y >= 0 && pos.y < gridHeight) {
                grid[pos.y][pos.x] = 1;
            }
        }
    }

    /**
     * Removes a piece from the grid array representation.
     *
     * @param piece the piece to remove
     */
    private void removePieceFromGrid(TetrisPiece piece) {
        for (Block block : piece.getBlocks()) {
            Point pos = block.getPos();
            if (pos.x >= 0 && pos.x < gridWidth && pos.y >= 0 && pos.y < gridHeight) {
                grid[pos.y][pos.x] = 0;
            }
        }
    }

    /**
     * Rebuilds the entire grid array from the pieces list.
     */
    private void fullGridRebuild() {
        for (int i = 0; i < gridHeight; i++) {
            Arrays.fill(grid[i], 0);
        }

        for (TetrisPiece piece : pieces) {
            addPieceToGrid(piece);
        }
    }

    /**
     * Returns the grid as a 2d array.
     * Any index with a 1 has a block.
     *
     * @return 2d array as grid
     */
    public int[][] getGridArray() {
        return grid;
    }

    /**
     * Performs a move to a tetris piece on the grid.
     * Will only do so if it doesn't cause a collision.
     *
     * @param piece piece to move
     * @param move  movement type
     * @return true if piece has been moved and false otherwise
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
     * Clearing lines is done using the <a href="https://harddrop.com/wiki/Line_clear">Sticky</a> method.
     *
     * @return the number of lines cleared
     */
    public int clearFullLines() {
        int linesCleared = 0;

        while (true) {
            Map<Integer, List<Block>> blocksByRow = new HashMap<>();

            for (TetrisPiece piece : pieces) {
                for (Block block : piece.getBlocks()) {
                    int y = block.getPos().y;
                    blocksByRow.computeIfAbsent(y, k -> new ArrayList<>()).add(block);
                }
            }

            List<Block> rowToClear = IntStream.range(0, this.gridHeight)
                    .mapToObj(blocksByRow::get)
                    .filter(row -> row != null && row.size() == this.gridWidth)
                    .findFirst()
                    .orElse(null);

            if (rowToClear == null) {
                break;
            }

            for (TetrisPiece piece : pieces) {
                piece.getBlocks().removeIf(rowToClear::contains);
            }

            linesCleared++;

            List<Block> remaining = pieces.stream()
                    .flatMap(p -> p.getBlocks().stream())
                    .toList();

            List<Block> visited = new ArrayList<>();
            List<List<Block>> floatingGroups = new ArrayList<>();

            for (Block block : remaining) {
                if (!visited.contains(block)) {
                    List<Block> group = new ArrayList<>();
                    floodFill(block, remaining, group);
                    visited.addAll(group);
                    floatingGroups.add(group);
                }
            }

            for (List<Block> group : floatingGroups) {
                moveGroupDown(group, remaining);
            }
        }

        fullGridRebuild();
        System.out.println("Successfully cleared " + linesCleared + " lines");
        return linesCleared;
    }

    /**
     * Performs a flood fill algorithm to find connected blocks.
     *
     * @param start The starting block
     * @param all   The list of all blocks
     * @param group The list to store the connected blocks
     */
    private void floodFill(Block start, List<Block> all, List<Block> group) {
        Queue<Block> queue = new ArrayDeque<>();
        queue.add(start);
        group.add(start);

        while (!queue.isEmpty()) {
            Block curr = queue.poll();
            Point pos = curr.getPos();

            for (Point offset : List.of(
                    new Point(0, -1),
                    new Point(0, 1),
                    new Point(-1, 0),
                    new Point(1, 0))) {

                Point neighborPos = new Point(pos.x + offset.x, pos.y + offset.y);
                for (Block b : all) {
                    if (!group.contains(b) && b.getPos().equals(neighborPos)) {
                        group.add(b);
                        queue.add(b);
                    }
                }
            }
        }
    }

    /**
     * Moves a group of blocks down until they can't move further.
     *
     * @param group The group of blocks to move
     * @param all   The list of all blocks
     */
    private void moveGroupDown(List<Block> group, List<Block> all) {
        List<Block> others = all.stream()
                .filter(b -> !group.contains(b))
                .toList();

        TetrisPiece otherPiece = new TetrisPiece(others);
        TetrisPiece groupPiece = new TetrisPiece(group);
        TetrisPiece cloneGroupPiece = groupPiece.clone();

        while (true) {
            cloneGroupPiece.performMove(MovementType.DOWN);

            if (otherPiece.intersects(cloneGroupPiece)
                    || this.boundary.intersects(cloneGroupPiece)) {
                break;
            }

            groupPiece.performMove(MovementType.DOWN);
        }
    }

    /**
     * Gets the list of pieces on the grid.
     *
     * @return The list of pieces
     */
    public List<TetrisPiece> getPieces() {
        return this.pieces;
    }

    /**
     * Sets the list of pieces on the grid.
     *
     * @param pieces The new list of pieces
     */
    protected void setPieces(List<TetrisPiece> pieces) {
        this.pieces = pieces;
        fullGridRebuild();
    }
}
