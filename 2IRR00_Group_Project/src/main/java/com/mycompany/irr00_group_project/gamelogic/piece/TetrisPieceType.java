package com.mycompany.irr00_group_project.gamelogic.piece;

import java.util.Random;

/**
 * Enumeration of all possible tetris pieces types.
 * Provides method to get a random piece type.
 *
 * @author Jayson Leander, Yingyao Feng
 */
public enum TetrisPieceType {
    /** I-shaped piece. */
    I,
    
    /** L-shaped piece. */
    L,
    
    /** J-shaped piece. */
    J,
    
    /** T-shaped piece. */
    T,
    
    /** S-shaped piece. */
    S,
    
    /** Z-shaped piece. */
    Z,
    
    /** O-shaped piece. */
    O,
    
    /** Boundary piece. */
    BOUNDARY;

    private static final Random RANDOM = new Random();

    /**
     * Returns a random tetris piece type.
     * Excludes BOUNDARY type.
     *
     * @return random tetris piece type
     * @author Jayson Leander, Yingyao Feng
     */
    public static TetrisPieceType randomPieceType() {
        TetrisPieceType[] playablePieces = {I, L, J, T, S, Z, O};
        return playablePieces[RANDOM.nextInt(playablePieces.length)];
    }
}
