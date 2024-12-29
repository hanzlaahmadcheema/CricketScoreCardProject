package frontend;

import javax.swing.*;
import java.awt.*;

public class HomeScreen extends BackgroundPanel {

    public HomeScreen(MainWindow mainWindow, Image backgroundImage) {
        super(backgroundImage);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(100, 20, 20, 20));

        JLabel welcomeLabel = new JLabel("Cricket Score Card System", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Garamond", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(255, 255, 255));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(2, 3, 50, 50)); 
        imagePanel.setBorder(BorderFactory.createEmptyBorder(100, 100, 150, 100));
        imagePanel.setOpaque(false);


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
        footerLabel.setFont(new Font("Garamond", Font.BOLD, 18));
        footerLabel.setForeground(Color.WHITE);
        add(footerLabel, BorderLayout.SOUTH);
    }

    private JButton createImageButton(String altText) {
        JButton button = new JButton();
        button.setToolTipText(altText);
        button.setFocusPainted(false);
        button.setBackground(new Color(57, 62, 70));
        button.setBorder(BorderFactory.createLineBorder(new Color(196, 192, 192), 1));
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setFont(new Font("Garamond", Font.BOLD, 20));
        button.setText(altText);
        button.setForeground(new Color(255, 255, 255)); 
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(40, 46, 55));
                button.setForeground(new Color(255, 255, 255));
                
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(57, 62, 70));
                button.setForeground(new Color(255, 255, 255));
            }
        });
        return button;
    }
}
