package frontend;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

import backend.DataManager;

public class MainWindow extends JFrame {
    private JPanel mainPanel;
    private DataManager dataManager;

    private HashMap<String, JPanel> screenMap;

    BackgroundPanel bp = new BackgroundPanel(null);

    Color primaryBackgroundColor = bp.primaryBackgroundColor;
    Color secondaryBackgroundColor = bp.secondaryBackgroundColor;
    Color foregroundColor = bp.foregroundColor;
    Color redColor = bp.redColor;

    
    public MainWindow() {
        
        dataManager = new DataManager();

        setTitle("Cricket Scoreboard");
        setSize(1250, 760);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setUndecorated(true);

        Image backgroundImage;
        try {
            backgroundImage = new ImageIcon("resources/images/backgroundImage.png").getImage();
        } catch (Exception e) {
            backgroundImage = null; 
            System.err.println("Background image not found: " + e.getMessage());
        }

        JPanel navBarPanel = new JPanel();
        navBarPanel.setLayout(new BoxLayout(navBarPanel, BoxLayout.X_AXIS));
        navBarPanel.setBackground(primaryBackgroundColor);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(primaryBackgroundColor);

        String[] screens = {
            "Home", "Team Setup", "Scorecard", 
            "Player Statistics", "Match Summary", 
            "Leaderboard", "Commentary"
        };

        for (String screen : screens) {
            JButton navButton = new JButton(screen);
            navButton.setFont(new Font("Calibri", Font.BOLD, 16));
            navButton.setBackground(secondaryBackgroundColor);
            navButton.setForeground(foregroundColor);
            navButton.setFocusPainted(false); 
            navButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

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

            navButton.addActionListener(e -> switchToScreen(screen));
            buttonPanel.add(navButton);
        }

        JButton closeButton = new JButton("X");
        closeButton.setFont(new Font("Calibri", Font.BOLD, 16));
        closeButton.setBackground(secondaryBackgroundColor);
        closeButton.setForeground(redColor);
        closeButton.setFocusPainted(false);
        closeButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

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

        closeButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        navBarPanel.add(Box.createHorizontalGlue());
        navBarPanel.add(Box.createHorizontalGlue());
        navBarPanel.add(buttonPanel);
        navBarPanel.add(Box.createHorizontalGlue());
        navBarPanel.add(Box.createHorizontalGlue());
        navBarPanel.add(closeButton);
        navBarPanel.add(Box.createHorizontalGlue());
        navBarPanel.add(Box.createHorizontalGlue());

        add(navBarPanel, BorderLayout.NORTH);

        mainPanel = new BackgroundPanel(backgroundImage);
        mainPanel.setLayout(new CardLayout());
        add(mainPanel, BorderLayout.CENTER);

        screenMap = new HashMap<>();
        
        registerScreen("Home", new HomeScreen(this, backgroundImage));
        registerScreen("Team Setup", new TeamSetup(backgroundImage));
        registerScreen("Scorecard", new ScoreCard(backgroundImage));
        registerScreen("Player Statistics", new PlayerStats(backgroundImage));
        registerScreen("Match Summary", new MatchSummary(backgroundImage));
        registerScreen("Leaderboard", new Leaderboard(backgroundImage));
        registerScreen("Commentary", new CommentaryPanel(backgroundImage));

        switchToScreen("Home");
    }

    private void registerScreen(String screenName, JPanel screen) {
        screenMap.put(screenName, screen);
        mainPanel.add(screen, screenName);
    }

    public void switchToScreen(String screenName) {
        if (screenMap.containsKey(screenName)) {
            CardLayout layout = (CardLayout) mainPanel.getLayout();
            layout.show(mainPanel, screenName);
        } else {
            System.err.println("Screen not found: " + screenName);
        }
    }
}