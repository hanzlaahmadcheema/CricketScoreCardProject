package frontend;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

import backend.DataManager;

public class HomeScreen extends BackgroundPanel {

    
    public HomeScreen(MainWindow mainWindow, Image backgroundImage) {
        super(backgroundImage); 
        setLayout(new BorderLayout(10, 10)); 
        setBorder(BorderFactory.createEmptyBorder(50, 20, 20, 20)); 

        
        JLabel welcomeLabel = new JLabel();
        String welcomeText = "Welcome to Cricket Score Card System - " + LocalDate.now(); 
        welcomeLabel.setText(welcomeText); 
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER); 
        welcomeLabel.setFont(new Font("Calibri", Font.BOLD, 24));
        welcomeLabel.setForeground(foregroundColor); 
        add(welcomeLabel, BorderLayout.NORTH);

        
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(2, 3, 100, 50)); 
        imagePanel.setBorder(BorderFactory.createEmptyBorder(150, 100, 150, 100)); 
        imagePanel.setBackground(backgroundColor); 

        
        JButton teamSetupButton = createImageButton("Team Setup");
        JButton scorecardButton = createImageButton("Scorecard");
        JButton playerStatsButton = createImageButton("Player Statistics");
        JButton matchSummaryButton = createImageButton("Match Summary");
        JButton leaderboardButton = createImageButton("Leaderboard");
        JButton commentaryButton = createImageButton("Commentary");

        
        teamSetupButton.addActionListener(e -> mainWindow.switchToScreen("Team Setup"));
        scorecardButton.addActionListener(e -> mainWindow.switchToScreen("Scorecard"));
        matchSummaryButton.addActionListener(e -> mainWindow.switchToScreen("Match Summary"));
        playerStatsButton.addActionListener(e -> mainWindow.switchToScreen("Player Statistics"));
        leaderboardButton.addActionListener(e -> mainWindow.switchToScreen("Leaderboard"));
        commentaryButton.addActionListener(e -> mainWindow.switchToScreen("Commentary"));

        
        imagePanel.add(teamSetupButton);
        imagePanel.add(scorecardButton);
        imagePanel.add(playerStatsButton);
        imagePanel.add(matchSummaryButton);
        imagePanel.add(leaderboardButton);
        imagePanel.add(commentaryButton);

        
        add(imagePanel, BorderLayout.CENTER);

        
        JLabel footerLabel = new JLabel("Navigate to any section using the buttons above.", SwingConstants.CENTER);
        footerLabel.setFont(new Font("Calibri", Font.BOLD, 18));
        footerLabel.setForeground(foregroundColor);
        add(footerLabel, BorderLayout.SOUTH);
    }

    
    private JButton createImageButton(String altText) {
        JButton button = new JButton();
        button.setToolTipText("Go to the " + altText + " section."); 
        button.setFocusPainted(false); 
        button.setBackground(secondaryBackgroundColor); 
        button.setBorder(BorderFactory.createLineBorder(foregroundColor, 1)); 
        button.setHorizontalTextPosition(SwingConstants.CENTER); 
        button.setVerticalTextPosition(SwingConstants.BOTTOM); 
        button.setFont(new Font("Calibri", Font.BOLD, 20)); 
        button.setText(altText); 
        button.setForeground(foregroundColor); 

        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverBackgroundColor); 
                button.setForeground(foregroundColor); 
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(secondaryBackgroundColor); 
                button.setForeground(foregroundColor); 
            }
        });
        return button; 
    }
}
