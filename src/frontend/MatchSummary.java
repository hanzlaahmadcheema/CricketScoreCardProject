package frontend;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import backend.DataManager;
import backend.Player;

public class MatchSummary extends BackgroundPanel {

    private JLabel bestBatsmanLabel;
    private JLabel bestBowlerLabel;
    private JLabel team1ScoreLabel;
    private JLabel team2ScoreLabel;
    private DefaultListModel<String> highlightReelListModel;
    private DataManager dataManager;

    public MatchSummary(Image backgroundImage) {
        super(backgroundImage);
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        dataManager = new DataManager();

        JLabel titleLabel = new JLabel("Match Summary", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Calibri", Font.BOLD, 30));
        titleLabel.setForeground(foregroundColor);
        add(titleLabel, BorderLayout.NORTH);

        JPanel summaryPanel = new JPanel();
        summaryPanel.setLayout(new GridLayout(3, 1, 20, 20));
        summaryPanel.setOpaque(false);

        JPanel totalScoresPanel = new JPanel(new GridLayout(1, 2, 10, 20));
        totalScoresPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(transparentColor),
                "Total Scores",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Calibri", Font.BOLD, 30),
                foregroundColor
        ));
        totalScoresPanel.setBackground(backgroundColor);

        team1ScoreLabel = new JLabel(dataManager.getTeamName(1)+": 0/0 (0.0)", SwingConstants.CENTER);
        team1ScoreLabel.setFont(new Font("Calibri", Font.BOLD, 24));
        team1ScoreLabel.setForeground(foregroundColor);
        totalScoresPanel.add(team1ScoreLabel);

        team2ScoreLabel = new JLabel(dataManager.getTeamName(2)+": 0/0 (0.0)", SwingConstants.CENTER);
        team2ScoreLabel.setFont(new Font("Calibri", Font.BOLD, 24));
        team2ScoreLabel.setForeground(foregroundColor);
        totalScoresPanel.add(team2ScoreLabel);

        summaryPanel.add(totalScoresPanel);

        JPanel topPerformersPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        topPerformersPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(transparentColor),
                "Top Performers",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Calibri", Font.BOLD, 30),
                foregroundColor
        ));
        topPerformersPanel.setBackground(backgroundColor);

        JLabel bestBatsmanLabel1 = new JLabel("Best Batsman:", SwingConstants.RIGHT);
        bestBatsmanLabel1.setFont(new Font("Calibri", Font.BOLD, 26));
        bestBatsmanLabel1.setForeground(foregroundColor);

        bestBatsmanLabel = new JLabel("No data available");
        bestBatsmanLabel.setFont(new Font("Calibri", Font.BOLD, 24));
        bestBatsmanLabel.setForeground(foregroundColor);

        JLabel bestBowlerLabel1 = new JLabel("Best Bowler:", SwingConstants.RIGHT);
        bestBowlerLabel1.setFont(new Font("Calibri", Font.BOLD, 26));
        bestBowlerLabel1.setForeground(foregroundColor);

        bestBowlerLabel = new JLabel("No data available");
        bestBowlerLabel.setFont(new Font("Calibri", Font.BOLD, 24));
        bestBowlerLabel.setForeground(foregroundColor);

        topPerformersPanel.add(bestBatsmanLabel1);
        topPerformersPanel.add(bestBatsmanLabel);
        topPerformersPanel.add(bestBowlerLabel1);
        topPerformersPanel.add(bestBowlerLabel);

        summaryPanel.add(topPerformersPanel);

        add(summaryPanel, BorderLayout.CENTER);

        
        JButton refreshButton = new JButton("↻");
        refreshButton.setFont(new Font("Calibri", Font.BOLD, 20));
        refreshButton.setForeground(foregroundColor);
        refreshButton.setBackground(primaryBackgroundColor);
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTotalScores();
                updateTopPerformers();
            }
        });
        add(refreshButton, BorderLayout.SOUTH);
    }

    public void updateTotalScores() {
        String team1Score = dataManager.getTeamScore(1); 
        String team2Score = dataManager.getTeamScore(2); 

        team1ScoreLabel.setText(dataManager.getTeamName(1)+": " + team1Score);
        team2ScoreLabel.setText(dataManager.getTeamName(2)+": " + team2Score);
        this.revalidate();
        this.repaint();
    }

    public void updateTopPerformers() {
        List<Player> topBatsmen = dataManager.getTopPerformers("batsman", 1);
        List<Player> topBowlers = dataManager.getTopPerformers("bowler", 1);

        if (!topBatsmen.isEmpty()) {
            Player topBatsman = topBatsmen.get(0);
            bestBatsmanLabel.setText(topBatsman.getName() + " - " + topBatsman.getRunsScored() + " runs (" + topBatsman.getBallsFaced() + " balls)");
        } else {
            bestBatsmanLabel.setText("No data available");
        }

        if (!topBowlers.isEmpty()) {
            Player topBowler = topBowlers.get(0);
            bestBowlerLabel.setText(topBowler.getName() + " - " + topBowler.getWickets() + " wickets (" + topBowler.getOversBowled() + " overs)");
        } else {
            bestBowlerLabel.setText("No data available");
        }
        this.revalidate();
        this.repaint();
    }

    public void updateHighlightReel(String[] highlights) {
        highlightReelListModel.clear();
        for (String highlight : highlights) {
            highlightReelListModel.addElement(highlight);
        }
    }
}