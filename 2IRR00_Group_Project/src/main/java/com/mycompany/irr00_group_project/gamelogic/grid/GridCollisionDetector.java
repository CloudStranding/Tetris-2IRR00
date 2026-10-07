package com.mycompany.irr00_group_project.gamelogic.grid;

import java.awt.Point;
import java.util.List;

import com.mycompany.irr00_group_project.gamelogic.MovementType;
import com.mycompany.irr00_group_project.gamelogic.piece.Block;
import com.mycompany.irr00_group_project.gamelogic.piece.TetrisPiece;

/**
 * Detects collisions with other tetris pieces.
 * Will do so based on a {@code List<TetrisPiece>} representing a grid.
 * The class assumes the pieces list is a reference.
 * This means that if this list gets added to somewhere else,
 * It will be represented in this object too.
 * Therefore, there is no such method to add a piece to the list in this object.
 * Only to change the reference itself.
 * This object should only be used by {@code GridManager}.
 */
public class GridCollisionDetector {

    private final List<TetrisPiece> pieces;
    private final TetrisPiece boundary;

    /**
     * Creates a new GridCollisionDetector.
     *
     * @param pieces   reference to a list with tetris pieces
     * @param boundary piece representing the boundary of the grid
     */
    public GridCollisionDetector(List<TetrisPiece> pieces, TetrisPiece boundary) {
        this.pieces = pieces;
        this.boundary = boundary;
    }

    /**
     * Checks if piece can be added to the grid.
     * For proper Tetris gameplay, we need to check if the piece can safely
     * exist at its spawn position AND be able to move down into the visible area.
     *
     * @param piece tetris piece to check with
     * @return true if adding doesn't cause a collision and false if otherwise
     */
    public boolean canBeAdded(TetrisPiece piece) {
        // Check if any part of the piece conflicts with existing pieces at spawn position
        boolean spawnConflicts = hasAnyPartConflict(piece);

        // Check if any part of the piece goes outside the side/bottom boundaries
        boolean intersectsWithBoundaries = intersectsWithSideOrBottomBoundary(piece);

        // Additional game over check: if piece spawns above visible area,
        // check if it can move down into visible area without conflicts
        boolean canMoveToVisibleArea = canPieceMoveToVisibleArea(piece);

        boolean canAdd = !spawnConflicts && !intersectsWithBoundaries && canMoveToVisibleArea;

        // Log when there's a conflict (game over scenario)
        if (!canAdd) {
            System.out.println("canBeAdded: GAME OVER DETECTED - spawnConflict=" + spawnConflicts
                    + ", boundaryConflict=" + intersectsWithBoundaries
                    + ", canMoveToVisible=" + canMoveToVisibleArea);
        }

        return canAdd;
    }

    /**
     * Checks if any part of the new piece conflicts with existing pieces.
     *
     * @param newPiece the piece to check
     * @return true if there is a conflict, false otherwise
     */
    private boolean hasAnyPartConflict(TetrisPiece newPiece) {
        for (Block newBlock : newPiece.getBlocks()) {
            // Check against all existing pieces
            for (TetrisPiece existingPiece : pieces) {
                for (Block existingBlock : existingPiece.getBlocks()) {
                    if (newBlock.getPos().equals(existingBlock.getPos())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Checks if the piece can exist safely in its spawn position and in the visible area.
     * Since pieces now spawn mostly in visible area, this mainly checks if there's space.
     *
     * @param piece the piece to check
     * @return true if the piece can move to visible area, false otherwise
     */
    private boolean canPieceMoveToVisibleArea(TetrisPiece piece) {
        // Since pieces now spawn at or near the visible area (Y >= 0), 
        // we mainly need to check if the spawn position is clear

        // Check if any blocks are above the visible area (Y < 0)
        boolean hasBlocksAboveVisible = piece.getBlocks().stream()
                .anyMatch(block -> block.getPos().y < 0);

        if (!hasBlocksAboveVisible) {
            // All blocks are in visible area, piece is fine
            return true;
        }

        // Some blocks are above visible area, check if piece can settle
        // The key test: can the piece exist without overlapping existing pieces?
        // This is already checked by hasAnyPartConflict, so we can be more lenient here

        // Check if the piece will be able to drop down naturally
        TetrisPiece testPiece = piece.clone();

        // Try a few moves down to see if the piece can settle into visible area
        for (int moves = 0; moves < 3; moves++) {
            // Reduced iterations since spawn is closer to visible area
            // Check if the piece can move down
            if (!isMoveValidForTestPiece(testPiece)) {
                // Cannot move down - check if at least part is in visible area
                return testPiece.getBlocks().stream()
                        .anyMatch(block -> block.getPos().y >= 0);
            }

            // Move the test piece down
            testPiece.performMove(MovementType.DOWN);

            // Check if most of the piece is now in visible area
            long visibleBlocks = testPiece.getBlocks().stream()
                    .filter(block -> block.getPos().y >= 0)
                    .count();
            if (visibleBlocks >= testPiece.getBlocks().size() / 2) {
                return true; // At least half the piece is visible, good enough
            }
        }

        // Default to allowing the piece - strict checking is done by hasAnyPartConflict
        return true;
    }

    /**
     * Helper method to check if a move is valid for a test piece (not on the actual grid).
     *
     * @param testPiece the piece to test
     * @return true if the move is valid, false otherwise
     */
    private boolean isMoveValidForTestPiece(TetrisPiece testPiece) {
        TetrisPiece clone = testPiece.clone();
        clone.performMove(MovementType.DOWN);

        // Check against existing pieces on the grid
        for (TetrisPiece existingPiece : pieces) {
            if (clone.intersects(existingPiece)) {
                return false;
            }
        }

        // Check against boundary
        return !clone.intersects(boundary);
    }

    /**
     * Checks if the piece intersects with side or bottom boundaries.
     * Top boundary checking is excluded to allow pieces to spawn above the visible area.
     *
     * @param piece the piece to check
     * @return true if the piece intersects with side or bottom boundaries, false otherwise
     */
    private boolean intersectsWithSideOrBottomBoundary(TetrisPiece piece) {
        for (Block pieceBlock : piece.getBlocks()) {
            Point piecePos = pieceBlock.getPos();

            for (Block boundaryBlock : boundary.getBlocks()) {
                Point boundaryPos = boundaryBlock.getPos();

                // Only check side and bottom boundaries:
                // Side boundaries: x < 0 or x >= gridWidth
                // Bottom boundaries: y >= gridHeight
                // Skip top boundaries (y < 0) to allow spawning above visible area
                if (boundaryPos.y >= 0 && piecePos.equals(boundaryPos)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a move is valid for a piece.
     *
     * @param piece The piece to check the move for
     * @param move  The type of move to check
     * @return true if the move is valid, false otherwise
     */
    public boolean isMoveValid(TetrisPiece piece, MovementType move) {
        validatePieceOnBoard(piece, pieces);
        TetrisPiece clone = piece.clone();

        clone.performMove(move);

        return !(intersectsPieces(clone, pieces, piece) || clone.intersects(boundary));
    }

    /**
     * Checks if a piece intersects with any other pieces in the list.
     *
     * @param piece  The piece to check
     * @param pieces The list of pieces to check against
     * @param ignore The piece to ignore in the check
     * @return true if there is an intersection, false otherwise
     */
    private boolean intersectsPieces(TetrisPiece piece,
                                     List<TetrisPiece> pieces,
                                     TetrisPiece ignore) {
        for (TetrisPiece piece1 : pieces) {
            if (piece1 != ignore && piece1.intersects(piece)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Validates that a piece is properly placed on the board.
     *
     * @param piece  The piece to validate
     * @param pieces The list of pieces on the board
     * @throws IllegalArgumentException if the piece is not properly placed
     */
    private void validatePieceOnBoard(TetrisPiece piece, List<TetrisPiece> pieces)
            throws IllegalArgumentException {
        if (!pieces.contains(piece)) {
            throw new IllegalArgumentException(
                    "Given piece is not in the grid"
            );
        }
    }
}
