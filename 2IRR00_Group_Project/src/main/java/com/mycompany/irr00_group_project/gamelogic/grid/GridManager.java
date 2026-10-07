package com.mycompany.irr00_group_project.gamelogic.grid;

import java.awt.Point;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    private List<TetrisPiece> pieces;
    private final TetrisPiece boundary;
    private final GridCollisionDetector collisionDetector;
    private final int gridWidth;
    private final int gridHeight;
    private final int[][] grid;

    /**
     * Constructor.
     *
     * @param boundary   tetris piece representing boundary of the grid
     * @param gridWidth  width of the grid
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
     * @param move  movement type
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
     * Clearing lines is done using the <a href="https://harddrop.com/wiki/Line_clear">Sticky</a> method.
     *
     * @return the number of lines cleared
     * @author Jayson Leander, Yingyao Feng
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

    private void moveGroupDown(List<Block> group, List<Block> all) {
        List<Block> others = all.stream()
                .filter(b -> !group.contains(b))
                .toList();

        TetrisPiece otherPiece = new TetrisPiece(others);
        TetrisPiece groupPiece = new TetrisPiece(group);
        TetrisPiece cloneGroupPiece = groupPiece.clone();

        //TODO maybe use grid collision detector for this

        while (true) {
            cloneGroupPiece.performMove(MovementType.DOWN);

            if (otherPiece.intersects(cloneGroupPiece) || this.boundary.intersects(cloneGroupPiece)) {
                break;
            }

            groupPiece.performMove(MovementType.DOWN);
        }
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

    /**
     * Sets the pieces in the grid.
     *
     * @param pieces pieces to set the grid to
     * @author Jayson Leander, Yingyao Feng
     */
    protected void setPieces(List<TetrisPiece> pieces) {
        this.pieces = pieces;
        fullGridRebuild();
    }
}
