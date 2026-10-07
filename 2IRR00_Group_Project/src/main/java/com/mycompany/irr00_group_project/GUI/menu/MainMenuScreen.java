package com.mycompany.irr00_group_project.gui.menu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Objects;

import static com.mycompany.irr00_group_project.gui.ColorUtil.adjustBrightness;
import static com.mycompany.irr00_group_project.gui.ColorUtil.hexToRgb;


/**
 * Main menu screen for the Tetris game.
 * Contains START GAME, DIFFICULTY, INSTRUCTION, and SCOREBOARD buttons.
 */
public class MainMenuScreen {

    // Unified warm color scheme - warmer, more elegant colors
    private static final String ACCENT_COLOR = "#E6C068"; // Soft gold
    private static final String SUCCESS_COLOR = "#5C8A58"; // Soft green
    private static final String WARNING_COLOR = "#C17A3A"; // Soft orange  
    private static final String INFO_COLOR = "#4A7BA7"; // Soft blue
    private static final String SPECIAL_COLOR = "#8B7BB8"; // Soft purple

    // Warm background gradient - lighter and warmer
    private static final String WARM_BACKGROUND =
            "linear-gradient(to bottom, #3D2B7A, #5A3F7D, #3D2B7A)";

    private final Scene menuScene;
    private final Button startGameButton;
    private final Button difficultyButton;
    private final Button instructionButton;
    private final Button scoreboardButton;
    private final Label difficultyLabel;

    private int selectedDifficulty = 1;

    /**
     * Creates a new MainMenuScreen with all necessary UI components.
     */
    public MainMenuScreen() {
        VBox menuRoot = new VBox(30);
        menuRoot.setAlignment(Pos.CENTER);
        menuRoot.setPadding(new Insets(60));
        menuRoot.setStyle("-fx-background-color: " + WARM_BACKGROUND + ";");

        // Create title with refined styling and modern font
        Label titleLabel = new Label("TETRIS");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.EXTRA_BOLD, 85));
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setStyle("-fx-background-color: linear-gradient(to bottom, #3A2F6B, #2D1B69);"
                + "-fx-background-radius: 15;"
                + "-fx-border-color: " + ACCENT_COLOR + ";"
                + "-fx-border-width: 2;"
                + "-fx-border-radius: 15;"
                + "-fx-padding: 25;"
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 8, 0.5, 0, 4);");


        // Create menu buttons with unified styling
        startGameButton = createMenuButton("START GAME", SUCCESS_COLOR);
        difficultyButton = createMenuButton("DIFFICULTY", WARNING_COLOR);
        instructionButton = createMenuButton("INSTRUCTION", INFO_COLOR);
        scoreboardButton = createMenuButton("SCOREBOARD", SPECIAL_COLOR);

        // Create difficulty indicator with refined styling
        difficultyLabel = new Label("Current Difficulty: " + getDifficultyName(selectedDifficulty));
        difficultyLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 16));
        difficultyLabel.setTextFill(Color.web("#F0E6F7"));
        difficultyLabel.setStyle("-fx-background-color: rgba(0,0,0,0.2);"
                + "-fx-background-radius: 12;"
                + "-fx-padding: 12;");

        // Layout components with refined spacing
        VBox buttonContainer = new VBox(20);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().addAll(
                startGameButton, difficultyButton, instructionButton, scoreboardButton);
        // Create image gallery
        HBox imageGallery = createImageGallery();
        menuRoot.getChildren().addAll(titleLabel, imageGallery, buttonContainer, difficultyLabel);

        menuScene = new Scene(menuRoot, 850, 750);
    }

    /**
     * Creates a button with the specified text and color.
     *
     * @param text      The button text
     * @param baseColor The base color for the button
     * @return The created button
     */
    private Button createMenuButton(String text, String baseColor) {
        Button button = new Button(text);
        button.setPrefWidth(250);
        button.setPrefHeight(60);
        button.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));

        // Calculate lighter and darker shades
        String lighterColor = adjustBrightness(baseColor, 1.2);
        String darkerColor = adjustBrightness(baseColor, 0.8);

        button.setStyle(String.format(
                "-fx-background-color: linear-gradient(to bottom, %s, %s);"
                        + "-fx-text-fill: white;"
                        + "-fx-background-radius: 12;"
                        + "-fx-border-radius: 12;"
                        + "-fx-border-color: %s;"
                        + "-fx-border-width: 2;"
                        + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 6, 0.4, 0, 3);",
                lighterColor, darkerColor, lighterColor
        ));

        // Refined hover effects
        button.setOnMouseEntered(e -> {
            button.setStyle(String.format(
                    "-fx-background-color: linear-gradient(to bottom, %s, %s);"
                            + "-fx-text-fill: white;"
                            + "-fx-background-radius: 12;"
                            + "-fx-border-radius: 12;"
                            + "-fx-border-color: %s;"
                            + "-fx-border-width: 2;"
                            + "-fx-effect: dropshadow(gaussian, %s, 8, 0.6, 0, 4);"
                            + "-fx-scale-x: 1.05;"
                            + "-fx-scale-y: 1.05;",
                    adjustBrightness(baseColor, 1.3),
                    baseColor,
                    lighterColor,
                    "rgba(" + hexToRgb(baseColor) + ", 0.4)"
            ));
        });

        button.setOnMouseExited(e -> {
            button.setStyle(String.format(
                    "-fx-background-color: linear-gradient(to bottom, %s, %s);"
                            + "-fx-text-fill: white;"
                            + "-fx-background-radius: 12;"
                            + "-fx-border-radius: 12;"
                            + "-fx-border-color: %s;"
                            + "-fx-border-width: 2;"
                            + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 6, 0.4, 0, 3);"
                            + "-fx-scale-x: 1.0;"
                            + "-fx-scale-y: 1.0;",
                    lighterColor, darkerColor, lighterColor
            ));
        });

        return button;
    }

    /**
     * Gets the name of the difficulty level.
     *
     * @param difficulty The difficulty level
     * @return The name of the difficulty level
     */
    private String getDifficultyName(int difficulty) {
        return DifficultyScreen.getDifficultyName(difficulty);
    }

    /**
     * Updates the difficulty level.
     *
     * @param difficulty The new difficulty level
     */
    public void updateDifficulty(int difficulty) {
        this.selectedDifficulty = difficulty;
        difficultyLabel.setText("Current Difficulty: " + getDifficultyName(difficulty));
    }

    /**
     * Creates an image gallery with Tetris piece previews.
     *
     * @return The created image gallery
     */
    private HBox createImageGallery() {
        HBox imageContainer = new HBox(15);
        imageContainer.setAlignment(Pos.CENTER);
        imageContainer.setPadding(new Insets(20, 0, 20, 0));

        // Load and create image views for the four images
        try {
            String[] imagePaths = {
                    "/images/image1.png",
                    "/images/image2.png",
                    "/images/image3.png",
                    "/images/image4.png"
            };

            for (String imagePath : imagePaths) {
                // Load image from resources
                Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));

                if (image.isError()) {
                    System.err.println("Failed to load image: " + imagePath);
                    continue;
                }

                // Create ImageView with appropriate sizing
                ImageView imageView = getImageView(image);

                imageContainer.getChildren().add(imageView);
            }

        } catch (Exception e) {
            System.err.println("Error loading images: " + e.getMessage());
            e.printStackTrace();

            // Fallback: Add a simple label if images can't be loaded
            Label fallbackLabel = new Label("🎮 Tetris Images 🎮");
            fallbackLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
            fallbackLabel.setTextFill(Color.web("#F0E6F7"));
            fallbackLabel.setStyle("-fx-background-color: rgba(0,0,0,0.2);"
                    + "-fx-background-radius: 12;"
                    + "-fx-padding: 12;");
            imageContainer.getChildren().add(fallbackLabel);
        }

        return imageContainer;
    }

    private ImageView getImageView(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(120);  // Set width to fit nicely in the layout
        imageView.setFitHeight(80);  // Adjust height proportionally
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        // Add elegant styling with border and shadow
        imageView.setStyle("-fx-background-color: rgba(255,255,255,0.1);"
                + "-fx-background-radius: 12;"
                + "-fx-border-color: " + ACCENT_COLOR + ";"
                + "-fx-border-width: 2;"
                + "-fx-border-radius: 12;"
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0.4, 0, 3);");

        // Add hover effect
        imageView.setOnMouseEntered(e -> {
            imageView.setStyle("-fx-background-color: rgba(255,255,255,0.2);"
                    + "-fx-background-radius: 12;"
                    + "-fx-border-color: " + adjustBrightness(ACCENT_COLOR, 1.2) + ";"
                    + "-fx-border-width: 2;"
                    + "-fx-border-radius: 12;"
                    + "-fx-effect: dropshadow(gaussian, "
                    + "rgba(230, 192, 104, 0.4), 12, 0.6, 0, 5);"
                    + "-fx-scale-x: 1.05;"
                    + "-fx-scale-y: 1.05;");
        });

        imageView.setOnMouseExited(e -> {
            imageView.setStyle("-fx-background-color: rgba(255,255,255,0.1);"
                    + "-fx-background-radius: 12;"
                    + "-fx-border-color: " + ACCENT_COLOR + ";"
                    + "-fx-border-width: 2;"
                    + "-fx-border-radius: 12;"
                    + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 8, 0.4, 0, 3);"
                    + "-fx-scale-x: 1.0;"
                    + "-fx-scale-y: 1.0;");
        });
        return imageView;
    }

    /**
     * Gets the scene of the main menu.
     *
     * @return The main menu scene
     */
    public Scene getScene() {
        return menuScene;
    }

    /**
     * Gets the start game button.
     *
     * @return The start game button
     */
    public Button getStartGameButton() {
        return startGameButton;
    }

    /**
     * Gets the difficulty button.
     *
     * @return The difficulty button
     */
    public Button getDifficultyButton() {
        return difficultyButton;
    }

    /**
     * Gets the instruction button.
     *
     * @return The instruction button
     */
    public Button getInstructionButton() {
        return instructionButton;
    }

    /**
     * Gets the scoreboard button.
     *
     * @return The scoreboard button
     */
    public Button getScoreboardButton() {
        return scoreboardButton;
    }
} 