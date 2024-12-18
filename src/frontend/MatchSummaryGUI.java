package frontend;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;

public class MatchSummaryGUI extends BackgroundPanel {
    private JLabel team1ScoreLabel;
    private JLabel team2ScoreLabel;
    private JLabel bestBatsmanLabel;
    private JLabel bestBowlerLabel;
    private DefaultListModel<String> partnershipListModel;
    private DefaultListModel<String> highlightReelListModel;

    public MatchSummaryGUI(Image backgroundImage) {
        super(backgroundImage);
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Match Summary", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 204));
        add(titleLabel, BorderLayout.NORTH);

        JPanel summaryPanel = new JPanel();
        summaryPanel.setLayout(new GridLayout(3, 1, 20, 20));

        JPanel totalScoresPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        totalScoresPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 102, 204)), "Total Scores", TitledBorder.LEFT, TitledBorder.TOP, new Font("Monospaced", Font.BOLD, 18), new Color(0, 102, 204)));
        totalScoresPanel.add(new JLabel("Team 1:", SwingConstants.RIGHT));
        team1ScoreLabel = new JLabel("200/8 (20 overs)");
        team1ScoreLabel.setFont(new Font("Monospaced", Font.PLAIN, 16));
        totalScoresPanel.add(team1ScoreLabel);
        totalScoresPanel.add(new JLabel("Team 2:", SwingConstants.RIGHT));
        team2ScoreLabel = new JLabel("180/9 (20 overs)");
        team2ScoreLabel.setFont(new Font("Monospaced", Font.PLAIN, 16));
        totalScoresPanel.add(team2ScoreLabel);

        JPanel topPerformersPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        topPerformersPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 102, 204)), "Top Performers", TitledBorder.LEFT, TitledBorder.TOP, new Font("Monospaced", Font.BOLD, 18), new Color(0, 102, 204)));
        topPerformersPanel.add(new JLabel("Best Batsman:", SwingConstants.RIGHT));
        bestBatsmanLabel = new JLabel("Player 1 - 80 runs (50 balls)");
        bestBatsmanLabel.setFont(new Font("Monospaced", Font.PLAIN, 16));
        topPerformersPanel.add(bestBatsmanLabel);
        topPerformersPanel.add(new JLabel("Best Bowler:", SwingConstants.RIGHT));
        bestBowlerLabel = new JLabel("Player 3 - 4 wickets (4 overs)");
        bestBowlerLabel.setFont(new Font("Monospaced", Font.PLAIN, 16));
        topPerformersPanel.add(bestBowlerLabel);

        JPanel partnershipsPanel = new JPanel(new BorderLayout(20, 20));
        partnershipsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 102, 204)), "Key Partnerships", TitledBorder.LEFT, TitledBorder.TOP, new Font("Monospaced", Font.BOLD, 18), new Color(0, 102, 204)));
        partnershipListModel = new DefaultListModel<>();
        JList<String> partnershipList = new JList<>(partnershipListModel);
        partnershipList.setFont(new Font("Monospaced", Font.PLAIN, 14));
        partnershipListModel.addElement("Player 1 & Player 2: 120 runs"); 
        partnershipListModel.addElement("Player 3 & Player 4: 50 runs"); 
        partnershipsPanel.add(new JScrollPane(partnershipList), BorderLayout.CENTER);

        summaryPanel.add(totalScoresPanel);
        summaryPanel.add(topPerformersPanel);
        summaryPanel.add(partnershipsPanel);

        add(summaryPanel, BorderLayout.CENTER);

        JPanel highlightReelPanel = new JPanel(new BorderLayout(20, 20));
        highlightReelPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 102, 204)), "Highlight Reel", TitledBorder.LEFT, TitledBorder.TOP, new Font("Monospaced", Font.BOLD, 18), new Color(0, 102, 204)));
        highlightReelListModel = new DefaultListModel<>();
        JList<String> highlightReelList = new JList<>(highlightReelListModel);
        highlightReelList.setFont(new Font("Monospaced", Font.PLAIN, 14));
        highlightReelListModel.addElement("1. Player 1 hit the highest score (80 runs).");
        highlightReelListModel.addElement("2. Player 3 took 4 wickets.");
        highlightReelListModel.addElement("3. Player 2 hit 5 sixes.");
        highlightReelListModel.addElement("4. Team 1 defended 200 runs successfully.");
        highlightReelPanel.add(new JScrollPane(highlightReelList), BorderLayout.CENTER);

        add(highlightReelPanel, BorderLayout.SOUTH);
    }

    public void updateTotalScores(String team1Score, String team2Score) {
        team1ScoreLabel.setText(team1Score);
        team2ScoreLabel.setText(team2Score);
    }

    public void updateTopPerformers(String bestBatsman, String bestBowler) {
        bestBatsmanLabel.setText(bestBatsman);
        bestBowlerLabel.setText(bestBowler);
    }

    public void updatePartnerships(String[] partnerships) {
        partnershipListModel.clear();
        for (String partnership : partnerships) {
            partnershipListModel.addElement(partnership);
        }
    }

    public void updateHighlightReel(String[] highlights) {
        highlightReelListModel.clear();
        for (String highlight : highlights) {
            highlightReelListModel.addElement(highlight);
        }
    }
}
