package frontend;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

// Home screen class extending BackgroundPanel to reuse background functionality
public class HomeScreen extends BackgroundPanel {

    // Constructor to initialize the Home Screen with main window reference and background image
    public HomeScreen(MainWindow mainWindow, Image backgroundImage) {
        super(backgroundImage); // Call parent class constructor to set the background image
        setLayout(new BorderLayout(10, 10)); // Use BorderLayout with spacing between components
        setBorder(BorderFactory.createEmptyBorder(50, 20, 20, 20)); // Add padding around the panel

        // Add welcome label at the top
        JLabel welcomeLabel = new JLabel();
        String welcomeText = "Welcome to Cricket Score Card System - " + LocalDate.now(); // Dynamic date
        welcomeLabel.setText(welcomeText); // Set the welcome text  
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center align the text
        welcomeLabel.setFont(new Font("Calibri", Font.BOLD, 24));
        welcomeLabel.setForeground(foregroundColor); // Use predefined foreground color
        add(welcomeLabel, BorderLayout.NORTH);

        // Create a grid layout for shortcut buttons in the center
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(2, 3, 100, 50)); // Grid: 2 rows, 3 columns, with spacing
        imagePanel.setBorder(BorderFactory.createEmptyBorder(150, 100, 150, 100)); // Add padding inside the grid
        imagePanel.setBackground(backgroundColor); // Set semi-transparent background

        // Create buttons for each major section
        JButton teamSetupButton = createImageButton("Team Setup");
        JButton scorecardButton = createImageButton("Scorecard");
        JButton playerStatsButton = createImageButton("Player Statistics");
        JButton matchSummaryButton = createImageButton("Match Summary");
        JButton leaderboardButton = createImageButton("Leaderboard");
        JButton commentaryButton = createImageButton("Commentary");

        // Add navigation functionality to each button
        teamSetupButton.addActionListener(e -> mainWindow.switchToScreen("Team Setup"));
        scorecardButton.addActionListener(e -> mainWindow.switchToScreen("Scorecard"));
        matchSummaryButton.addActionListener(e -> mainWindow.switchToScreen("Match Summary"));
        playerStatsButton.addActionListener(e -> mainWindow.switchToScreen("Player Statistics"));
        leaderboardButton.addActionListener(e -> mainWindow.switchToScreen("Leaderboard"));
        commentaryButton.addActionListener(e -> mainWindow.switchToScreen("Commentary"));

        // Add buttons to the panel
        imagePanel.add(teamSetupButton);
        imagePanel.add(scorecardButton);
        imagePanel.add(playerStatsButton);
        imagePanel.add(matchSummaryButton);
        imagePanel.add(leaderboardButton);
        imagePanel.add(commentaryButton);

        // Add the grid panel to the center of the screen
        add(imagePanel, BorderLayout.CENTER);

        // Add footer label at the bottom
        JLabel footerLabel = new JLabel("Navigate to any section using the buttons above.", SwingConstants.CENTER);
        footerLabel.setFont(new Font("Calibri", Font.BOLD, 18));
        footerLabel.setForeground(foregroundColor);
        add(footerLabel, BorderLayout.SOUTH);
    }

    // Utility method to create styled buttons with hover effects and tooltips
    private JButton createImageButton(String altText) {
        JButton button = new JButton();
        button.setToolTipText("Go to the " + altText + " section."); // Tooltip displayed on hover
        button.setFocusPainted(false); // Remove focus border
        button.setBackground(secondaryBackgroundColor); // Set default background color
        button.setBorder(BorderFactory.createLineBorder(foregroundColor, 1)); // Add a border with the foreground color
        button.setHorizontalTextPosition(SwingConstants.CENTER); // Center text horizontally
        button.setVerticalTextPosition(SwingConstants.BOTTOM); // Position text below the image
        button.setFont(new Font("Calibri", Font.BOLD, 20)); // Set font style and size
        button.setText(altText); // Display button text
        button.setForeground(foregroundColor); // Set default text color

        // Add hover effect using MouseListener
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverBackgroundColor); // Change background on hover
                button.setForeground(foregroundColor); // Maintain text color
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(secondaryBackgroundColor); // Revert to default background
                button.setForeground(foregroundColor); // Maintain text color
            }
        });
        return button; // Return the styled button
    }
}
