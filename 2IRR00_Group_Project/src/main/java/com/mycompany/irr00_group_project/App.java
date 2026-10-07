package com.mycompany.irr00_group_project;

import javafx.application.Application;

/**
 * Main application entry point.
 * This class is used to launch the Tetris game.
 * 
 */
public class App {
    public static void main(String[] args) {
        // Configure JavaFX modules
        System.setProperty("javafx.verbose", "false");
        
        // Add required JavaFX modules
        System.setProperty("javafx.controls", "true");
        System.setProperty("javafx.graphics", "true");
        
        // Launch the main application
        Application.launch(TetrisGameMain.class, args);
    }
}
