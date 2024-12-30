package frontend;

import javax.swing.*;
import java.awt.*;

// A custom JPanel to support a background image and a predefined color scheme
public class BackgroundPanel extends JPanel {
    // The background image to be rendered
    private Image backgroundImage;

    // Predefined color palette for consistent styling across the application
    Color backgroundColor = new Color(0, 0, 0, 50); // Semi-transparent black
    Color primaryBackgroundColor = new Color(34, 40, 49); // Dark primary background
    Color secondaryBackgroundColor = new Color(57, 62, 70); // Slightly lighter secondary background
    Color foregroundColor = new Color(238, 238, 238); // Light foreground color for text and icons
    Color transparentColor = new Color(0, 0, 0, 0); // Fully transparent color
    Color selectionForegroundColor = new Color(192, 192, 192); // Gray for selected items
    Color blackColor = new Color(0, 0, 0); // Pure black
    Color hoverBackgroundColor = new Color(40, 46, 55); // Background color for hover effects
    Color redColor = new Color(255, 0, 0); // Bright red for warnings or alerts

    // Constructor to initialize the background image
    public BackgroundPanel(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    // Override paintComponent to render the background image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Call the parent class's paint method

        // Draw the background image scaled to the panel size, if available
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
