package com.mycompany.irr00_group_project.GUI.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Displays the username input screen with buttons to start or go back.
 * Has a method to check the current username in the input.
 *
 * @author Jayson Leander
 */
public class UsernameScreen {

    private Button backButton;
    private Button startButton;
    private final Scene scene;
    private TextField userInput;

    /**
     * Constructor.
     *
     * @author Jayson Leander
     */
    public UsernameScreen() {
        this.backButton = createMenuButton("Back", Color.RED);
        this.startButton = createMenuButton("Start", Color.GREEN);

        this.userInput = new TextField();
        this.userInput.setStyle("-fx-pref-height: 40px;");
        this.userInput.setFont(Font.font("Arial Black", FontWeight.EXTRA_BOLD, 28));
        this.userInput.setAlignment(Pos.BASELINE_CENTER);

        Label usernameLabel = new Label("Enter your username");
        usernameLabel.setFont(Font.font("Arial Black", FontWeight.EXTRA_BOLD, 28));
        usernameLabel.setTextFill(Color.WHITE);
        usernameLabel.setStyle("-fx-background-color: linear-gradient(to bottom, #1a5490, #0d2a4a);" +
                "-fx-background-radius: 15;" +
                "-fx-border-color: #FFD700;" +
                "-fx-border-width: 3;" +
                "-fx-border-radius: 15;" +
                "-fx-padding: 15;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 10, 0.6, 0, 5);");

        VBox usernameBox = new VBox(25);
        usernameBox.setAlignment(Pos.CENTER);
        usernameBox.setPadding(new Insets(40));
        usernameBox.getChildren().addAll(usernameLabel, this.userInput, this.startButton, this.backButton);

        StackPane usernameRoot = new StackPane();
        usernameRoot.setStyle("-fx-background-color: linear-gradient(to bottom, #0f3460, #16537e, #0f3460);");
        usernameRoot.getChildren().add(usernameBox);
        this.scene = new Scene(usernameRoot, 850, 750);
    }

    private Button createMenuButton(String text, Color color) {
        Button button = new Button(text);
        button.setPrefWidth(250);
        button.setPrefHeight(60);
        button.setFont(Font.font("Arial Black", FontWeight.EXTRA_BOLD, 18));
        button.setStyle(String.format(
                "-fx-background-color: linear-gradient(to bottom, %s, %s);" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 15;" +
                        "-fx-border-radius: 15;" +
                        "-fx-border-color: %s;" +
                        "-fx-border-width: 3;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 8, 0.6, 0, 4);",
                toRGBCode(color.brighter()),
                toRGBCode(color.darker()),
                toRGBCode(color.brighter())
        ));

        // Enhanced hover effects with scaling and glow
        button.setOnMouseEntered(e -> {
            button.setStyle(String.format(
                    "-fx-background-color: linear-gradient(to bottom, %s, %s);" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 15;" +
                            "-fx-border-radius: 15;" +
                            "-fx-border-color: %s;" +
                            "-fx-border-width: 3;" +
                            "-fx-effect: dropshadow(gaussian, %s, 12, 0.8, 0, 6);" +
                            "-fx-scale-x: 1.08;" +
                            "-fx-scale-y: 1.08;",
                    toRGBCode(color.brighter().brighter()),
                    toRGBCode(color),
                    toRGBCode(color.brighter()),
                    toRGBCode(color)
            ));
        });

        button.setOnMouseExited(e -> {
            button.setStyle(String.format(
                    "-fx-background-color: linear-gradient(to bottom, %s, %s);" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 15;" +
                            "-fx-border-radius: 15;" +
                            "-fx-border-color: %s;" +
                            "-fx-border-width: 3;" +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 8, 0.6, 0, 4);" +
                            "-fx-scale-x: 1.0;" +
                            "-fx-scale-y: 1.0;",
                    toRGBCode(color.brighter()),
                    toRGBCode(color.darker()),
                    toRGBCode(color.brighter())
            ));
        });

        return button;
    }

    private String toRGBCode(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    /**
     * Returns the back button used to navigate to the previous screen.
     *
     * @return the back button
     * @author Jayson Leander
     */
    public Button getBackButton() {
        return backButton;
    }

    /**
     * Returns the start button used to proceed after entering a username.
     *
     * @return the start button
     * @author Jayson Leander
     */
    public Button getStartButton() {
        return startButton;
    }

    /**
     * Returns the scene containing the entire username screen layout.
     *
     * @return the scene for this screen
     * @author Jayson Leander
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Checks if the entered username is non-empty.
     *
     * @return true if the username is valid, false otherwise
     * @author Jayson Leander
     */
    public boolean isValidUsername() {
        return !this.userInput.getText().isEmpty();
    }

    /**
     * Returns the text field where the user inputs their username.
     *
     * @return the username input text field
     * @author Jayson Leander
     */
    public TextField getUserInput() {
        return this.userInput;
    }
}
