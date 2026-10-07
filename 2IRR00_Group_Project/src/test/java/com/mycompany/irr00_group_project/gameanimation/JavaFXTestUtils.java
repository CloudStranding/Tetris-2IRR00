package com.mycompany.irr00_group_project.gameanimation;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javafx.application.Platform;

/**
 * Utility class for initializing JavaFX platform in tests.
 * Ensures that the platform is initialized only once across all test classes.
 */
public class JavaFXTestUtils {

    private static final AtomicBoolean INITIALIZED = new AtomicBoolean(false);

    /**
     * Initializes the JavaFX platform if it hasn't been initialized yet.
     * This method is thread-safe and can be called multiple times safely.
     *
     * @throws InterruptedException if the initialization times out
     */
    public static void initializeJavaFX() throws InterruptedException {
        if (INITIALIZED.compareAndSet(false, true)) {
            // First time initialization
            CountDownLatch latch = new CountDownLatch(1);
            try {
                Platform.startup(latch::countDown);
                if (!latch.await(5, TimeUnit.SECONDS)) {
                    INITIALIZED.set(false); // Reset on failure
                    throw new RuntimeException("Timeout initializing JavaFX");
                }
            } catch (IllegalStateException e) {
                // Platform already initialized by someone else
                // This can happen in rare race conditions
                if (e.getMessage() != null
                        && e.getMessage().contains("Toolkit already initialized")) {
                    // It's already initialized, which is what we wanted
                    return;
                } else {
                    INITIALIZED.set(false); // Reset on other failures
                    throw e;
                }
            }
        }
        // If we get here, JavaFX is initialized (either by us or previously)
    }
} 