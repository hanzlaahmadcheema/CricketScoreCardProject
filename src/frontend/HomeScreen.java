package frontend;

import javax.swing.*;
import java.awt.*;

public class HomeScreen extends BackgroundPanel {

    public HomeScreen(MainWindow mainWindow, Image backgroundImage) {
        super(backgroundImage);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel welcomeLabel = new JLabel("Welcome to the Cricket Score Card System", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Monospaced", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(255, 255, 255));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(2, 3, 15, 15)); 
        imagePanel.setBorder(BorderFactory.createEmptyBorder(100, 10, 100, 10));
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
        footerLabel.setFont(new Font("Monospaced", Font.ITALIC, 14));
        footerLabel.setForeground(Color.WHITE);
        footerLabel.setOpaque(true);
        footerLabel.setBackground(new Color(255, 255, 255));
        add(footerLabel, BorderLayout.SOUTH);
    }

    private JButton createImageButton(String altText) {
        JButton button = new JButton();
        button.setToolTipText(altText);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 1));
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setFont(new Font("Monospaced", Font.BOLD, 20));
        button.setText(altText);
        button.setForeground(new Color(255, 255, 255)); 
        return button;
    }
}
