package com.mycompany.irr00_group_project.gameanimation;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mycompany.irr00_group_project.gameAnimation.TetrisGameIntegration;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//@author Yanhang Luo

class TetrisGameIntegrationTest {

    private static TetrisGameIntegration app;
    private static Stage stage;
    private static BorderPane root;

    @BeforeAll
    static void initAndStartApp() throws Exception {
        // Initialize JavaFX toolkit
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new RuntimeException("Failed to initialize JavaFX");
        }

        // Now launch TetrisGameIntegration on the JavaFX thread
        CountDownLatch startLatch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                app = new TetrisGameIntegration();
                stage = new Stage();
                app.start(stage);
                root = (BorderPane) stage.getScene().getRoot();
            } catch (Exception e) {
                fail("Exception in start(): " + e.getMessage());
            } finally {
                startLatch.countDown();
            }
        });
        if (!startLatch.await(5, TimeUnit.SECONDS)) {
            throw new RuntimeException("Timeout starting TetrisGameIntegration");
        }
    }

    @Test
    void testUIStructureExists() {
        // Root should be a BorderPane
        assertNotNull(root);
        assertTrue(root instanceof BorderPane);

        // Top region should be an HBox containing two Labels (score + status)
        Node topNode = root.getTop();
        assertTrue(topNode instanceof HBox, "Top region should be an HBox");
        HBox topBar = (HBox) topNode;
        assertEquals(2, topBar.getChildren().size(), "Top bar should have exactly two children");
        assertTrue(topBar.getChildren().get(0) instanceof Label, "First child on top should be a Label");
        assertTrue(topBar.getChildren().get(1) instanceof Label, "Second child on top should be a Label");

        // Bottom region should be an HBox containing at least Start, Pause, Restart, and a VBox for difficulty
        Node bottomNode = root.getBottom();
        assertTrue(bottomNode instanceof HBox, "Bottom region should be an HBox");
        HBox bottomBar = (HBox) bottomNode;
        assertTrue(bottomBar.getChildren().size() >= 4, "Bottom bar should have at least 4 children");

        // First three should be Buttons: “Start”, “Pause”, “Restart”
        assertTrue(bottomBar.getChildren().get(0) instanceof Button);
        assertEquals("Start", ((Button) bottomBar.getChildren().get(0)).getText());

        assertTrue(bottomBar.getChildren().get(1) instanceof Button);
        assertEquals("Pause", ((Button) bottomBar.getChildren().get(1)).getText());

        assertTrue(bottomBar.getChildren().get(2) instanceof Button);
        assertEquals("Restart", ((Button) bottomBar.getChildren().get(2)).getText());

        // Fourth child should be a VBox with difficulty label + row of 5 buttons
        assertTrue(bottomBar.getChildren().get(3) instanceof VBox);
        VBox diffVBox = (VBox) bottomBar.getChildren().get(3);
        // Expect two children: a Label and an HBox
        assertEquals(2, diffVBox.getChildren().size());
        assertTrue(diffVBox.getChildren().get(0) instanceof Label);
        assertEquals("Difficulty:", ((Label) diffVBox.getChildren().get(0)).getText());
        assertTrue(diffVBox.getChildren().get(1) instanceof HBox);
        HBox diffHBox = (HBox) diffVBox.getChildren().get(1);
        // Should have exactly 5 buttons labeled “1” through “5”
        assertEquals(5, diffHBox.getChildren().size());
        for (int i = 0; i < 5; i++) {
            Node child = diffHBox.getChildren().get(i);
            assertTrue(child instanceof Button);
            assertEquals(String.valueOf(i + 1), ((Button) child).getText());
        }
    }

    @Test
    void testStartPauseRestartFlow() throws Exception {
        // Grab references to the UI components:
        HBox topBar = (HBox) root.getTop();
        Label scoreLabel = (Label) topBar.getChildren().get(0);
        Label statusLabel = (Label) topBar.getChildren().get(1);

        HBox bottomBar = (HBox) root.getBottom();
        Button startButton = (Button) bottomBar.getChildren().get(0);
        Button pauseButton = (Button) bottomBar.getChildren().get(1);
        Button restartButton = (Button) bottomBar.getChildren().get(2);

        // Initially, statusLabel should read “Press Start to Play”
        assertEquals("Press Start to Play", statusLabel.getText());

        // Click “Start” on the JavaFX thread:
        CountDownLatch latch1 = new CountDownLatch(1);
        Platform.runLater(() -> {
            startButton.fire();
            latch1.countDown();
        });
        assertTrue(latch1.await(2, TimeUnit.SECONDS));
        assertEquals("Playing", statusLabel.getText());
        assertTrue(scoreLabel.getText().startsWith("Score:"), "Score label should update to “Score: 0” or similar.");

        // Click “Pause”
        CountDownLatch latch2 = new CountDownLatch(1);
        Platform.runLater(() -> {
            pauseButton.fire();
            latch2.countDown();
        });
        assertTrue(latch2.await(2, TimeUnit.SECONDS));
        assertEquals("Paused", statusLabel.getText());

        // Click “Pause” again (should resume)
        CountDownLatch latch3 = new CountDownLatch(1);
        Platform.runLater(() -> {
            pauseButton.fire();
            latch3.countDown();
        });
        assertTrue(latch3.await(2, TimeUnit.SECONDS));
        assertEquals("Playing", statusLabel.getText());

        // Click “Restart”
        CountDownLatch latch4 = new CountDownLatch(1);
        Platform.runLater(() -> {
            restartButton.fire();
            latch4.countDown();
        });
        assertTrue(latch4.await(2, TimeUnit.SECONDS));
        assertEquals("Playing", statusLabel.getText(), "After restart, status should return to Playing");
    }
}