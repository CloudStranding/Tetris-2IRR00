package com.mycompany.irr00_group_project.gamelogic.piece;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

/**
 * Enums representing the different types of tetris pieces.
 * Has a method to get a random piece type.
 *
 * @author Jayson Leander, Yingyao Feng
 */
public enum TetrisPieceType {
    O, S, L, J, T, I, Z, BOUNDARY;

    // Only include game pieces, not BOUNDARY
    private static final List<TetrisPieceType> GAME_PIECES = List.of(O, S, L, J, T, I, Z);
    private static final int GAME_PIECES_SIZE = GAME_PIECES.size();
    private static final Random RANDOM = new SecureRandom();

    /**
     * Returns a random game piece type (excludes BOUNDARY).
     *
     * @return a random {@code TetrisPieceType} for gameplay
     * @author Jayson Leander, Yingyao Feng
     */
    public static TetrisPieceType randomPieceType()  {
        return GAME_PIECES.get(RANDOM.nextInt(GAME_PIECES_SIZE));
    }
}
