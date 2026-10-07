package com.mycompany.irr00_group_project.gui;

import com.mycompany.irr00_group_project.EnhancedGameEngine;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

/**
 * Handles keyboard input.
 */
public class InputHandler {
    /**
     * Sets up keyboard controls.
     *
     * @param scene       scene to attach handlers to
     * @param gameEngine  game engine instance
     * @param pauseAction pause callback
     */
    public static void setup(Scene scene, EnhancedGameEngine gameEngine, Runnable pauseAction) {
        scene.setOnKeyPressed(event -> {
            if (gameEngine != null) {
                if (event.getCode() == KeyCode.P) {
                    pauseAction.run();
                    return;
                }
                gameEngine.handle(event);
            }
        });
    }
}