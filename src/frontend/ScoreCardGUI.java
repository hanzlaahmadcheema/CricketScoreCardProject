package frontend;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ScoreCardGUI extends BackgroundPanel {
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

    public ScoreCardGUI(Image backgroundImage) {
        super(backgroundImage);
        setLayout(new BorderLayout(20, 20));  
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 

        JPanel topPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        scoreLabel = new JLabel("Score: 0/0 (0.0 Overs)", SwingConstants.CENTER);
        runRateLabel = new JLabel("Run Rate: 0.00", SwingConstants.CENTER);
        projectedScoreLabel = new JLabel("Projected Score: 0", SwingConstants.CENTER);
        topPanel.setOpaque(false);

        scoreLabel.setFont(new Font("Garamond", Font.BOLD, 24));
        scoreLabel.setForeground(new Color(255, 255, 255));
        runRateLabel.setFont(new Font("Garamond", Font.PLAIN, 18));
        runRateLabel.setForeground(new Color(255, 255, 255));
        projectedScoreLabel.setFont(new Font("Garamond", Font.PLAIN, 18));
        projectedScoreLabel.setForeground(new Color(255, 255, 255));

        topPanel.add(scoreLabel);
        topPanel.add(runRateLabel);
        topPanel.add(projectedScoreLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        centerPanel.setBackground(new Color(255, 255, 255, 50));
        scoreTable = createScoreTable();
        JScrollPane scoreScrollPane = new JScrollPane(scoreTable);
        scoreScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0,0,0,0)), "Batting Team Stats", TitledBorder.CENTER, TitledBorder.TOP, new Font("Garamond", Font.BOLD, 18), Color.WHITE));
        scoreScrollPane.setOpaque(false);
        scoreScrollPane.getViewport().setOpaque(false);
        centerPanel.add(scoreScrollPane);

        bowlerTable = createBowlerTable();
        JScrollPane bowlerScrollPane = new JScrollPane(bowlerTable);
        bowlerScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0,0,0,0)), "Bowling Team Stats", TitledBorder.CENTER, TitledBorder.TOP, new Font("Garamond", Font.BOLD, 18), Color.WHITE));
        bowlerScrollPane.setOpaque(false);
        bowlerScrollPane.getViewport().setOpaque(false);
        centerPanel.add(bowlerScrollPane);

        overTable = createOverTable();
        JScrollPane overScrollPane = new JScrollPane(overTable);
        overScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0,0,0,0)), "Over Summary", TitledBorder.CENTER, TitledBorder.TOP, new Font("Garamond", Font.BOLD, 18), Color.WHITE));
        overScrollPane.setOpaque(false);
        overScrollPane.getViewport().setOpaque(false);
        centerPanel.add(overScrollPane);

        add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setBackground(new Color(255, 255, 255, 50));
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
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Garamond", Font.BOLD, 16));
        button.setBackground(new Color(57, 62, 70));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private JTable createScoreTable() {
        String[] columns = {"Batsman", "Runs", "Balls", "4s", "6s", "Strike Rate"};
        DefaultTableModel model = new DefaultTableModel(columns, 11);
        return createNonEditableTable(model);
    }

    private JTable createBowlerTable() {
        String[] columns = {"Bowler", "Overs", "Maidens", "Runs", "Wickets", "Economy"};
        DefaultTableModel model = new DefaultTableModel(columns, 11);
        return createNonEditableTable(model);
    }

    private JTable createOverTable() {
        String[] columns = {"Over Number", "Runs", "Wickets"};
        DefaultTableModel model = new DefaultTableModel(columns, TOTAL_OVERS_IN_MATCH);
        for (int i = 0; i < TOTAL_OVERS_IN_MATCH; i++) {
            model.setValueAt(i + 1, i, 0);
        }
        return createNonEditableTable(model);
    }

    private JTable createNonEditableTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(new Font("Garamond", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.setBackground(new Color(57, 62, 70));
        table.setShowGrid(true);
        table.setGridColor(Color.BLACK);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(true);
        table.setForeground(Color.WHITE);
        table.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 1));
        table.getTableHeader().setBackground(new Color(34, 40, 49));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setOpaque(false);
        table.setSelectionBackground(new Color(34, 40, 49));
        table.setSelectionForeground(new Color(192, 192, 192));
        return table;
    }

    private void addRun(int runs) {
        totalRuns += runs;
        updateScoreLabel();
    }

    private void addWicket() {
        if (totalWickets < MAX_WICKETS) {
            totalWickets++;
            updateScoreLabel();
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
    }

    private void updateScoreLabel() {
        double overs = totalOvers + (ballsBowled / 6.0);
        scoreLabel.setText(String.format("Score: %d/%d (%.1f Overs)", totalRuns, totalWickets, overs));

        double runRate = (overs > 0) ? totalRuns / overs : 0;
        int projectedScore = (int) (runRate * TOTAL_OVERS_IN_MATCH);

        runRateLabel.setText(String.format("Run Rate: %.2f", runRate));
        projectedScoreLabel.setText(String.format("Projected Score: %d", projectedScore));
        JPanel topPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        topPanel.setBackground(new Color(255, 255, 255, 50));
    }
}
