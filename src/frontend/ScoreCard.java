package frontend;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

import backend.DataManager;
import backend.Match;

public class ScoreCard extends BackgroundPanel {

    private JTable scoreTable;
    private JTable bowlerTable;
    private JTable overTable;
    private JLabel scoreLabel;
    private JLabel runRateLabel;
    private JLabel projectedScoreLabel;

    private int totalRuns = 0;
    private int totalWickets = 0;
    private int totalOvers = 0;
    private int ballsBowled = 0;

    private static final int TOTAL_OVERS_IN_MATCH = 10;
    private static final int MAX_WICKETS = 10;

    private DataManager dataManager;
    private Match currentMatch;

    public ScoreCard(Image backgroundImage) {
        super(backgroundImage);
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        dataManager = new DataManager();

        JPanel topPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        topPanel.setOpaque(false);

        scoreLabel = new JLabel("Score: 0/0 (0.0 Overs)", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Calibri", Font.BOLD, 24));
        scoreLabel.setForeground(foregroundColor);

        runRateLabel = new JLabel("Run Rate: 0.00", SwingConstants.CENTER);
        runRateLabel.setFont(new Font("Calibri", Font.PLAIN, 18));
        runRateLabel.setForeground(foregroundColor);

        projectedScoreLabel = new JLabel("Projected Score: 0", SwingConstants.CENTER);
        projectedScoreLabel.setFont(new Font("Calibri", Font.PLAIN, 18));
        projectedScoreLabel.setForeground(foregroundColor);

        topPanel.add(scoreLabel);
        topPanel.add(runRateLabel);
        topPanel.add(projectedScoreLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        centerPanel.setOpaque(false);

        scoreTable = createScoreTable();
        JScrollPane scoreScrollPane = new JScrollPane(scoreTable);
        scoreScrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(transparentColor),
                "Batting Team Stats",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Calibri", Font.BOLD, 18),
                foregroundColor));
        scoreScrollPane.setBackground(backgroundColor);
        scoreScrollPane.getViewport().setOpaque(false);
        centerPanel.add(scoreScrollPane);

        bowlerTable = createBowlerTable();
        JScrollPane bowlerScrollPane = new JScrollPane(bowlerTable);
        bowlerScrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(transparentColor),
                "Bowling Team Stats",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Calibri", Font.BOLD, 18),
                foregroundColor));
        bowlerScrollPane.setBackground(backgroundColor);
        bowlerScrollPane.getViewport().setOpaque(false);
        centerPanel.add(bowlerScrollPane);

        overTable = createOverTable();
        JScrollPane overScrollPane = new JScrollPane(overTable);
        overScrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(transparentColor),
                "Over Summary",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Calibri", Font.BOLD, 18),
                foregroundColor));
        overScrollPane.setBackground(backgroundColor);
        overScrollPane.getViewport().setOpaque(false);
        centerPanel.add(overScrollPane);

        add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(backgroundColor);

        JButton addRunButton = new JButton("Add Run");
        JButton addWicketButton = new JButton("Add Wicket");
        JButton nextBallButton = new JButton("Next Ball");

        styleButton(addRunButton);
        styleButton(addWicketButton);
        styleButton(nextBallButton);

        addRunButton.addActionListener(e -> addRun(1));
        addWicketButton.addActionListener(e -> addWicket());
        nextBallButton.addActionListener(e -> nextBall());

        buttonPanel.add(addRunButton);
        buttonPanel.add(addWicketButton);
        buttonPanel.add(nextBallButton);

        add(buttonPanel, BorderLayout.SOUTH);

        loadMatchData();
    }

    private void loadMatchData() {
        try {
            currentMatch = dataManager.getOngoingMatch(); // Fetch ongoing match
            if (currentMatch != null) {
                totalRuns = currentMatch.getRuns();
                totalWickets = currentMatch.getWickets();
                totalOvers = currentMatch.getOvers();
                ballsBowled = currentMatch.getBallsBowled();
                updateScoreLabel(); // Update GUI with current match stats
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading match data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    private void styleButton(JButton button) {
        button.setFont(new Font("Calibri", Font.BOLD, 16));
        button.setBackground(secondaryBackgroundColor);
        button.setForeground(foregroundColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private JTable createScoreTable() {
        String[] columns = { "Batsman", "Runs", "Balls", "4s", "6s", "Strike Rate" };
        DefaultTableModel model = new DefaultTableModel(columns, 11);
        return createNonEditableTable(model);
    }

    private JTable createBowlerTable() {
        String[] columns = { "Bowler", "Overs", "Maidens", "Runs", "Wickets", "Economy" };
        DefaultTableModel model = new DefaultTableModel(columns, 11);
        return createNonEditableTable(model);
    }

    private JTable createOverTable() {
        String[] columns = { "Over Number", "Runs", "Wickets" };
        DefaultTableModel model = new DefaultTableModel(columns, TOTAL_OVERS_IN_MATCH);
        for (int i = 0; i < TOTAL_OVERS_IN_MATCH; i++) {
            model.setValueAt(i + 1, i, 0);
        }
        return createNonEditableTable(model);
    }

    private JTable createNonEditableTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(new Font("Calibri", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setBackground(secondaryBackgroundColor);
        table.setShowGrid(true);
        table.setGridColor(blackColor);
        table.setForeground(foregroundColor);
        table.getTableHeader().setBackground(primaryBackgroundColor);
        table.getTableHeader().setForeground(foregroundColor);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        return table;
    }

    private void addRun(int runs) {
        totalRuns += runs;
        updateScoreLabel();
        saveMatchData();
    }
    
    private void addWicket() {
        if (totalWickets < MAX_WICKETS) {
            totalWickets++;
            updateScoreLabel();
            saveMatchData();
        } else {
            JOptionPane.showMessageDialog(this, "All wickets are down!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void nextBall() {
        ballsBowled++;
        if (ballsBowled == 6) {
            ballsBowled = 0;
            totalOvers++;
            if (totalOvers >= TOTAL_OVERS_IN_MATCH) {
                JOptionPane.showMessageDialog(this, "End of the innings!", "Match Over", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        updateScoreLabel();
        saveMatchData();
    }


    private void updateScoreLabel() {
        double overs = totalOvers + (ballsBowled / 6.0);
        scoreLabel.setText(String.format("Score: %d/%d (%.1f Overs)", totalRuns, totalWickets, overs));

        double runRate = (overs > 0) ? totalRuns / overs : 0;
        int projectedScore = (int) (runRate * TOTAL_OVERS_IN_MATCH);

        runRateLabel.setText(String.format("Run Rate: %.2f", runRate));
        projectedScoreLabel.setText(String.format("Projected Score: %d", projectedScore));
    }

    private void saveMatchData() {
        try {
            if (currentMatch == null) {
                currentMatch = new Match(0, totalRuns, totalWickets, totalOvers, ballsBowled, "Ongoing");
                dataManager.addMatch(currentMatch);
            } else {
                currentMatch.setRuns(totalRuns);
                currentMatch.setWickets(totalWickets);
                currentMatch.setOvers(totalOvers);
                currentMatch.setBallsBowled(ballsBowled);
                dataManager.updateMatch(currentMatch);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving match data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}