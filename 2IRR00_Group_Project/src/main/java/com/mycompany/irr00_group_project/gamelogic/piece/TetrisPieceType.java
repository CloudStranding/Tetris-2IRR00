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

    private static final List<TetrisPieceType> VALUES = List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new SecureRandom();

    /**
     * Returns a random piece type.
     *
     * @return a random {@code TetrisPieceType}
     * @author Jayson Leander, Yingyao Feng
     */
    public static TetrisPieceType randomPieceType()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
