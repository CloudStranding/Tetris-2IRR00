package com.mycompany.irr00_group_project.gamelogic.grid;

import java.awt.Point;
import java.util.List;

import com.mycompany.irr00_group_project.gamelogic.MovementType;
import com.mycompany.irr00_group_project.gamelogic.piece.Block;
import com.mycompany.irr00_group_project.gamelogic.piece.TetrisPiece;

/**
 * Object that will detect collisions with other tetris pieces.
 * Will do so based on a {@code List<TetrisPiece>} representing a grid.
 * The class assumes the pieces list is a reference.
 * This means that if this list gets added to somewhere else,
 * It will be represented in this object too.
 * Therefore, there is no such method to add a piece to the list in this object.
 * Only to change the reference itself.
 * This object should only be used by {@code GridManager}.
 *
 * @author Jayson Leander, Yingyao Feng
 */
public class GridCollisionDetector {

    private List<TetrisPiece> pieces;
    private TetrisPiece boundary;

    /**
     * Constructor.
     *
     * @param pieces reference to a list with tetris pieces
     * @param boundary piece representing the boundary of the grid
     * @author Jayson Leander, Yingyao Feng
     */
    public GridCollisionDetector(List<TetrisPiece> pieces, TetrisPiece boundary) {
        this.pieces = pieces;
        this.boundary = boundary;
    }

    /**
     * Checks if a move is valid.
     * Will apply the move of passed type to the passed piece.
     * It will then check if there are no collisions on the grid.
     * Grid is supplied as reference in constructor or getter.
     *
     * @param piece tetris piece to check move for
     * @param move movement type
     * @return true if the move doesn't cause collisions and false if otherwise
     * @author Jayson Leander, Yingyao Feng
     */
    public boolean isMoveValid(TetrisPiece piece, MovementType move) {
        validatePieceOnBoard(piece, pieces);
        TetrisPiece clone = piece.clone();

        clone.performMove(move);

        return !(intersectsPieces(clone, pieces, piece) || clone.intersects(boundary));
    }

    /**
     * Checks if piece can be added to the grid.
     * For proper Tetris gameplay, only the visible part of the piece (y >= 0) 
     * should be checked for conflicts with existing pieces.
     *
     * @param piece tetris piece to check with
     * @return true if adding doesn't cause a collision and false if otherwise
     * @author Jayson Leander, Yingyao Feng
     */
    public boolean canBeAdded(TetrisPiece piece) {
        // Check if the VISIBLE part of the new piece conflicts with existing pieces
        boolean visiblePartConflicts = hasVisiblePartConflict(piece);
        
        // Check if any part of the piece goes outside the side/bottom boundaries
        boolean intersectsWithBoundaries = intersectsWithSideOrBottomBoundary(piece);
        
        boolean canAdd = !visiblePartConflicts && !intersectsWithBoundaries;
        
        // Only log when there's a conflict to reduce noise
        if (!canAdd) {
            System.out.println("canBeAdded: CONFLICT - visibleConflict=" + visiblePartConflicts + 
                              ", boundaryConflict=" + intersectsWithBoundaries);
        }
        
        return canAdd;
    }
    
    /**
     * Checks if the visible part of the new piece (blocks with y >= 0) 
     * conflicts with any existing pieces.
     */
    private boolean hasVisiblePartConflict(TetrisPiece newPiece) {
        for (Block newBlock : newPiece.getBlocks()) {
            // Only check blocks that are in the visible area (y >= 0)
            if (newBlock.getPos().y >= 0) {
                // Check against all existing pieces
                for (TetrisPiece existingPiece : pieces) {
                    for (Block existingBlock : existingPiece.getBlocks()) {
                        if (newBlock.getPos().equals(existingBlock.getPos())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * Checks if the piece intersects with side or bottom boundaries.
     * Top boundary checking is excluded to allow pieces to spawn above the visible area.
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

    private void validatePieceOnBoard(TetrisPiece piece, List<TetrisPiece> pieces)
            throws IllegalArgumentException {
        if (!pieces.contains(piece)) {
            throw new IllegalArgumentException(
                    "Given piece is not in the grid"
            );
        }
    }
}
