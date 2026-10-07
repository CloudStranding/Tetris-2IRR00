package com.mycompany.irr00_group_project.gamelogic.piece;

import java.util.Random;

/**
 * Enumeration of all possible tetris pieces types.
 * Provides method to get a random piece type.
 */
public enum TetrisPieceType {
    I,

    L,

    J,

    T,

    S,

    Z,

    O,

    BOUNDARY;

    private static final Random RANDOM = new Random();
    private static final TetrisPieceType[] PLAYABLE_PIECES = {I, L, J, T, S, Z, O};

    /**
     * Returns a random tetris piece type.
     * Excludes BOUNDARY type.
     *
     * @return random tetris piece type
     */
    public static TetrisPieceType randomPieceType() {
        return PLAYABLE_PIECES[RANDOM.nextInt(PLAYABLE_PIECES.length)];
    }
}
