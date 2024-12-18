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
        welcomeLabel.setForeground(new Color(0, 173, 181));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(1, 6, 15, 15)); 
        imagePanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JButton teamSetupButton = createImageButton("team_setup.png", "Team Setup");
        JButton scorecardButton = createImageButton("scorecard.png", "Scorecard");
        JButton playerStatsButton = createImageButton("player_stats.png", "Player Statistics");
        JButton matchSummaryButton = createImageButton("match_summary.png", "Match Summary");
        JButton leaderboardButton = createImageButton("leaderboard.png", "Leaderboard");
        JButton commentaryButton = createImageButton("commentary.png", "Commentary");

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

        JLabel footerLabel = new JLabel("Navigate to any section using the images above.", SwingConstants.CENTER);
        footerLabel.setFont(new Font("Monospaced", Font.ITALIC, 14));
        footerLabel.setForeground(Color.GRAY);
        add(footerLabel, BorderLayout.SOUTH);
    }

    private JButton createImageButton(String imagePath, String altText) {
        ImageIcon icon = new ImageIcon(imagePath);
        JButton button = new JButton(icon);
        button.setToolTipText(altText);
        button.setFocusPainted(false);
        button.setBackground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setFont(new Font("Monospaced", Font.BOLD, 16));
        button.setText(altText);
        button.setForeground(new Color(0, 173, 181)); 
        return button;
    }
}
