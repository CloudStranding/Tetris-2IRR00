package com.mycompany.irr00_group_project.gui;

/**
 * Utility class for colors.
 * Contains methods that help with colors.
 */
public final class ColorUtil {

    /**
     * Converts a hex color to RGB format.
     *
     * @param hexColor The hex color to convert
     * @return The RGB color as a string
     */
    public static String hexToRgb(String hexColor) {
        int rgb = Integer.parseInt(hexColor.substring(1), 16);
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        return r + "," + g + "," + b;
    }

    /**
     * Adjusts the brightness of a hex color.
     *
     * @param hexColor The hex color to adjust
     * @param factor   The brightness factor
     * @return The adjusted hex color
     */
    public static String adjustBrightness(String hexColor, double factor) {
        // Convert hex to RGB
        int rgb = Integer.parseInt(hexColor.substring(1), 16);
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;

        // Adjust brightness
        r = Math.min(255, (int) (r * factor));
        g = Math.min(255, (int) (g * factor));
        b = Math.min(255, (int) (b * factor));

        return String.format("#%02X%02X%02X", r, g, b);
    }
}
