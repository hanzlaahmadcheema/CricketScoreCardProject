package frontend;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;


public class MatchSummary extends BackgroundPanel {
    
    private JLabel bestBatsmanLabel;
    private JLabel bestBowlerLabel;

    
    private DefaultListModel<String> partnershipListModel;
    private DefaultListModel<String> highlightReelListModel;

    
    public MatchSummary(Image backgroundImage) {
        super(backgroundImage); 
        setLayout(new BorderLayout(20, 20)); 
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 

        
        JLabel titleLabel = new JLabel("Match Summary", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Calibri", Font.BOLD, 30));
        titleLabel.setForeground(foregroundColor);
        add(titleLabel, BorderLayout.NORTH); 

        
        JPanel summaryPanel = new JPanel();
        summaryPanel.setLayout(new GridLayout(3, 1, 20, 20)); 
        summaryPanel.setOpaque(false);

        
        JPanel totalScoresPanel = new JPanel(new GridLayout(1, 4, 10, 20));
        totalScoresPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(transparentColor), 
            "Total Scores", 
            TitledBorder.CENTER, 
            TitledBorder.TOP, 
            new Font("Calibri", Font.BOLD, 24), 
            foregroundColor
        ));
        totalScoresPanel.setBackground(backgroundColor);

        JLabel team1ScoreLabel1 = new JLabel("Team 1:", SwingConstants.RIGHT);
        team1ScoreLabel1.setFont(new Font("Calibri", Font.BOLD, 18));
        team1ScoreLabel1.setForeground(foregroundColor);

        JLabel team1ScoreLabel2 = new JLabel("200/8 (20 overs)"); 
        team1ScoreLabel2.setFont(new Font("Calibri", Font.PLAIN, 18));
        team1ScoreLabel2.setForeground(foregroundColor);

        JLabel team2ScoreLabel1 = new JLabel("Team 2:", SwingConstants.RIGHT);
        team2ScoreLabel1.setFont(new Font("Calibri", Font.BOLD, 18));
        team2ScoreLabel1.setForeground(foregroundColor);

        JLabel team2ScoreLabel2 = new JLabel("180/9 (20 overs)"); 
        team2ScoreLabel2.setFont(new Font("Calibri", Font.PLAIN, 18));
        team2ScoreLabel2.setForeground(foregroundColor);

        
        totalScoresPanel.add(team1ScoreLabel1);
        totalScoresPanel.add(team1ScoreLabel2);
        totalScoresPanel.add(team2ScoreLabel1);
        totalScoresPanel.add(team2ScoreLabel2);

        
        JPanel topPerformersPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        topPerformersPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(transparentColor), 
            "Top Performers", 
            TitledBorder.CENTER, 
            TitledBorder.TOP, 
            new Font("Calibri", Font.BOLD, 24), 
            foregroundColor
        ));
        topPerformersPanel.setBackground(backgroundColor);

        JLabel bestBatsmanLabel1 = new JLabel("Best Batsman:", SwingConstants.RIGHT);
        bestBatsmanLabel1.setFont(new Font("Calibri", Font.BOLD, 18));
        bestBatsmanLabel1.setForeground(foregroundColor);

        JLabel bestBatsmanLabel2 = new JLabel("Player 1 - 80 runs (50 balls)"); 
        bestBatsmanLabel2.setFont(new Font("Calibri", Font.PLAIN, 18));
        bestBatsmanLabel2.setForeground(foregroundColor);

        JLabel bestBowlerLabel1 = new JLabel("Best Bowler:", SwingConstants.RIGHT);
        bestBowlerLabel1.setFont(new Font("Calibri", Font.BOLD, 18));
        bestBowlerLabel1.setForeground(foregroundColor);

        JLabel bestBowlerLabel2 = new JLabel("Player 3 - 4 wickets (4 overs)"); 
        bestBowlerLabel2.setFont(new Font("Calibri", Font.PLAIN, 18));
        bestBowlerLabel2.setForeground(foregroundColor);

        
        topPerformersPanel.add(bestBatsmanLabel1);
        topPerformersPanel.add(bestBatsmanLabel2);
        topPerformersPanel.add(bestBowlerLabel1);
        topPerformersPanel.add(bestBowlerLabel2);

        
        JPanel partnershipsPanel = new JPanel(new BorderLayout(20, 20));
        partnershipsPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(transparentColor), 
            "Key Partnerships", 
            TitledBorder.CENTER, 
            TitledBorder.TOP, 
            new Font("Calibri", Font.BOLD, 24), 
            foregroundColor
        ));
        partnershipsPanel.setBackground(backgroundColor);

        partnershipListModel = new DefaultListModel<>();
        JList<String> partnershipList = new JList<>(partnershipListModel);
        partnershipList.setFont(new Font("Calibri", Font.PLAIN, 24));
        partnershipList.setOpaque(false);

        
        partnershipListModel.addElement("Player 1 & Player 2: 120 runs"); 
        partnershipListModel.addElement("Player 3 & Player 4: 50 runs"); 
        partnershipListModel.addElement("Player 5 & Player 6: 30 runs");

        partnershipList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setOpaque(false);
                label.setFont(new Font("Calibri", Font.BOLD, 18));
                label.setForeground(foregroundColor);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                return label;
            }
        });

        JScrollPane scrollPane = new JScrollPane(partnershipList);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);

        partnershipsPanel.add(scrollPane, BorderLayout.CENTER);

        
        summaryPanel.add(totalScoresPanel);
        summaryPanel.add(topPerformersPanel);
        summaryPanel.add(partnershipsPanel);

        add(summaryPanel, BorderLayout.CENTER); 
    }

    
    public void updateTotalScores(String team1Score, String team2Score) {
        JLabel team1ScoreLabel2 = (JLabel) ((JPanel) ((JPanel) getComponent(1)).getComponent(0)).getComponent(1);
        JLabel team2ScoreLabel2 = (JLabel) ((JPanel) ((JPanel) getComponent(1)).getComponent(0)).getComponent(3);
        team1ScoreLabel2.setText(team1Score);
        team2ScoreLabel2.setText(team2Score);
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
