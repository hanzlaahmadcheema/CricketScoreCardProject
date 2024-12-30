package frontend;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class MainWindow extends JFrame {
    // Main container for switching between different screens
    private JPanel mainPanel;
    
    // Map to store registered screens for easy navigation
    private HashMap<String, JPanel> screenMap;

    // Background panel for aesthetic purposes
    BackgroundPanel bp = new BackgroundPanel(null);

    // Predefined color scheme for consistent styling
    Color primaryBackgroundColor = bp.primaryBackgroundColor;
    Color secondaryBackgroundColor = bp.secondaryBackgroundColor;
    Color foregroundColor = bp.foregroundColor;
    Color redColor = bp.redColor;

    public MainWindow() {
        // Set up the main window properties
        setTitle("Cricket Scoreboard");
        setSize(1250, 760);
        setLocationRelativeTo(null); // Center the window on the screen
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the window by default
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Exit the application on close
        setLayout(new BorderLayout());
        setUndecorated(true); // Remove default window decorations

        // Load background image
        Image backgroundImage;
        try {
            backgroundImage = new ImageIcon("src/frontend/images/backgroundImage.png").getImage();
        } catch (Exception e) {
            backgroundImage = null; // Use a default or null background
            System.err.println("Background image not found: " + e.getMessage());
        }

        // Create navigation bar panel
        JPanel navBarPanel = new JPanel();
        navBarPanel.setLayout(new BoxLayout(navBarPanel, BoxLayout.X_AXIS)); // Horizontal layout for buttons
        navBarPanel.setBackground(primaryBackgroundColor);

        // Panel to hold navigation buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(primaryBackgroundColor);

        // Define the screens available for navigation
        String[] screens = {
            "Home", "Team Setup", "Scorecard", 
            "Player Statistics", "Match Summary", 
            "Leaderboard", "Commentary"
        };

        // Create a button for each screen
        for (String screen : screens) {
            JButton navButton = new JButton(screen);
            navButton.setFont(new Font("Calibri", Font.BOLD, 16));
            navButton.setBackground(secondaryBackgroundColor);
            navButton.setForeground(foregroundColor);
            navButton.setFocusPainted(false); // Remove focus border
            navButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

            // Hover effects for buttons
            navButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    navButton.setBackground(primaryBackgroundColor);
                    navButton.setForeground(foregroundColor);
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    navButton.setBackground(secondaryBackgroundColor);
                    navButton.setForeground(foregroundColor);
                }
            });

            // Action to switch screens
            navButton.addActionListener(e -> switchToScreen(screen));
            buttonPanel.add(navButton);
        }

        // Close button for exiting the application
        JButton closeButton = new JButton("X");
        closeButton.setFont(new Font("Calibri", Font.BOLD, 16));
        closeButton.setBackground(secondaryBackgroundColor);
        closeButton.setForeground(redColor);
        closeButton.setFocusPainted(false);
        closeButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        // Hover effects for the close button
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                closeButton.setBackground(redColor);
                closeButton.setForeground(foregroundColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                closeButton.setBackground(secondaryBackgroundColor);
                closeButton.setForeground(redColor);
            }
        });

        // Action to close the application
        closeButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        // Add navigation buttons and close button to the navigation bar
        navBarPanel.add(Box.createHorizontalGlue()); // Add space for centering
        navBarPanel.add(Box.createHorizontalGlue()); // Add space for centering
        navBarPanel.add(buttonPanel);
        navBarPanel.add(Box.createHorizontalGlue()); // Add space for centering
        navBarPanel.add(Box.createHorizontalGlue()); // Add space for centering
        navBarPanel.add(closeButton);
        navBarPanel.add(Box.createHorizontalGlue()); // Add space for centering
        navBarPanel.add(Box.createHorizontalGlue()); // Add space for centering

        add(navBarPanel, BorderLayout.NORTH);

        // Main panel for displaying screens
        mainPanel = new BackgroundPanel(backgroundImage);
        mainPanel.setLayout(new CardLayout()); // Use CardLayout for screen switching
        add(mainPanel, BorderLayout.CENTER);

        // Map to store screens
        screenMap = new HashMap<>();
        
        // Register available screens
        registerScreen("Home", new HomeScreen(this, backgroundImage));
        registerScreen("Team Setup", new TeamSetup(backgroundImage));
        registerScreen("Scorecard", new ScoreCard(backgroundImage));
        registerScreen("Player Statistics", new PlayerStats(backgroundImage));
        registerScreen("Match Summary", new MatchSummary(backgroundImage));
        registerScreen("Leaderboard", new Leaderboard(backgroundImage));
        registerScreen("Commentary", new CommentaryPanel(backgroundImage));

        // Set the default screen
        switchToScreen("Home");
    }

    // Method to register a new screen
    private void registerScreen(String screenName, JPanel screen) {
        screenMap.put(screenName, screen);
        mainPanel.add(screen, screenName);
    }

    // Method to switch between screens
    public void switchToScreen(String screenName) {
        if (screenMap.containsKey(screenName)) {
            CardLayout layout = (CardLayout) mainPanel.getLayout();
            layout.show(mainPanel, screenName);
        } else {
            System.err.println("Screen not found: " + screenName);
        }
    }
}
