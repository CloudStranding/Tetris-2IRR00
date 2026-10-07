package com.mycompany.irr00_group_project.gamelogic;


import com.mycompany.irr00_group_project.gamelogic.grid.GridManager;
import com.mycompany.irr00_group_project.gamelogic.piece.TetrisPiece;
import com.mycompany.irr00_group_project.gamelogic.piece.TetrisPieceFactory;
import com.mycompany.irr00_group_project.gamelogic.piece.TetrisPieceType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Arrays;
import java.util.List;

/**
 * Objects that represents a {@code GameEngine} for tetris.
 * Has a current piece that represents the piece that will be moving per update.
 *
 * @author Jayson Leander, Yingyao Feng
 */
public class Game implements GameEngine {

    private final GridManager gridManager;
    private TetrisPiece currentPiece;
    private final TetrisPieceFactory factory;

    /**
     * Constructor.
     *
     * @param gridWidth width of the grid
     * @param gridHeight height of the grid
     * @param blockSize size of individual blocks which form a tetris piece
     * @author Jayson Leander, Yingyao Feng
     */
    public Game(int gridWidth, int gridHeight, int blockSize) {
        this.factory = new TetrisPieceFactory(gridWidth, gridHeight, blockSize);
        this.gridManager = new GridManager(this.factory.createTetrisPiece(TetrisPieceType.BOUNDARY),
                gridWidth,
                gridHeight);
    }

    @Override
    public void update() {
        if (this.gridManager.performMove(this.currentPiece, MovementType.DOWN)) {
            return;
        }

        this.gridManager.clearFullLines();

        TetrisPiece nextPiece = getNextPiece();
        if (this.gridManager.addPiece(nextPiece)) {
            this.currentPiece = nextPiece;
        } else {
            gameOver();
        }
    }

    @Override
    public void restart() {
        stop();
        start();
    }

    @Override
    public boolean isRunning() {
        return true;
    }

    @Override
    public TetrisPiece getNextPiece() {
        return this.factory.createTetrisPiece(TetrisPieceType.randomPieceType());
    }

    @Override
    public List<? extends Drawable> getGrid() {
        return this.gridManager.getPieces();
    }

    @Override
    public int[][] getCurrentGrid() {
        return this.gridManager.getGridArray();
    }

    @Override
    public void stop() {
        //TODO timeline stuff
    }

    @Override
    public void pause() {
        //TODO timeline stuff and possibly persistence
    }

    @Override
    public void resume() {
        //TODO timeline stuff
    }

    @Override
    public void start() {
        this.currentPiece = getNextPiece();
        this.gridManager.addPiece(this.currentPiece);
    }

    private void gameOver() {
        stop();
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case KeyCode.W -> this.gridManager.performMove(this.currentPiece,
                    MovementType.ROTATE);
            case KeyCode.S -> this.gridManager.performMove(this.currentPiece,
                    MovementType.DOWN);
            case KeyCode.A -> this.gridManager.performMove(this.currentPiece,
                    MovementType.LEFT);
            case KeyCode.D -> this.gridManager.performMove(this.currentPiece,
                    MovementType.RIGHT);
            default -> {
            }
        }
    }
}
