package frontend;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import backend.*;
import java.util.List;

import java.awt.event.ActionListener;

public class ScoreCard extends BackgroundPanel {
    private JTable battingTable, bowlerTable, overTable;
    private JLabel scoreLabel, runRateLabel, projectedScoreLabel, currentTeamLabel, inningLabel;

    private int totalRuns = 0, totalWickets = 0, totalOvers = 0, ballsBowled = 0;
    private boolean isFirstInning = true;
    private static final int TOTAL_OVERS_IN_MATCH = 10;
    private static final int MAX_WICKETS = 10;

    private DataManager dataManager;
    private Match currentMatch;
    private Player striker, nonStriker, currentBowler;
    private Team battingTeam, bowlingTeam;

    public ScoreCard(Image backgroundImage) {
        super(backgroundImage);
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        dataManager = new DataManager();

        // Setup Top Panel
        setupTopPanel();

        // Setup Center Panel
        setupCenterPanel();

        // Setup Bottom Panel (Buttons)
        setupButtonPanel();

        // Load existing data or start new match
        loadMatchData();
    }

    private void setupTopPanel() {
        JPanel topPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        topPanel.setOpaque(false);

        inningLabel = createLabel("Inning: 1", Font.BOLD, 20);
        currentTeamLabel = createLabel("Current Team: -", Font.BOLD, 20);
        scoreLabel = createLabel("Score: 0/0 (0.0 Overs)", Font.BOLD, 24);
        runRateLabel = createLabel("Run Rate: 0.00", Font.PLAIN, 18);
        projectedScoreLabel = createLabel("Projected Score: 0", Font.PLAIN, 18);

        topPanel.add(inningLabel);
        topPanel.add(currentTeamLabel);
        topPanel.add(scoreLabel);
        topPanel.add(runRateLabel);
        topPanel.add(projectedScoreLabel);

        add(topPanel, BorderLayout.NORTH);
    }

    private void setupCenterPanel() {
        JPanel centerPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        centerPanel.setOpaque(false);

        battingTable = createScoreTable();
        bowlerTable = createBowlerTable();
        overTable = createOverTable();

        centerPanel.add(createTableScrollPane(battingTable, "Batting Team Stats"));
        centerPanel.add(createTableScrollPane(bowlerTable, "Bowling Team Stats"));
        centerPanel.add(createTableScrollPane(overTable, "Over Summary"));

        add(centerPanel, BorderLayout.CENTER);
    }

    private void setupButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setOpaque(false);

        // Buttons
        JButton startMatchButton = createButton("Start Match", e -> startMatch());
        JButton endMatchButton = createButton("End Match", e -> endMatch());
        JButton addRunButton = createButton("Add Run", e -> addRun(1));
        JButton addWicketButton = createButton("Add Wicket", e -> addWicket());
        JButton nextBallButton = createButton("Next Ball", e -> nextBall());
        JButton addFourButton = createButton("Add 4 Runs", e -> addRun(4));
        JButton addSixButton = createButton("Add 6 Runs", e -> addRun(6));
        JButton noBallButton = createButton("No Ball", e -> addExtraRuns("No Ball", 1));
        JButton extraRunButton = createButton("Extra Run", e -> addExtraRuns("Extra Run", 1));
        JButton switchInningButton = createButton("Switch Inning", e -> switchInning());

        // Add buttons to the panel
        buttonPanel.add(startMatchButton);
        buttonPanel.add(endMatchButton);
        buttonPanel.add(addRunButton);
        buttonPanel.add(addWicketButton);
        buttonPanel.add(nextBallButton);
        buttonPanel.add(addFourButton);
        buttonPanel.add(addSixButton);
        buttonPanel.add(noBallButton);
        buttonPanel.add(extraRunButton);
        buttonPanel.add(switchInningButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadMatchData() {
        try {
            currentMatch = dataManager.getOngoingMatch();
            if (currentMatch != null) {
                setupMatch(currentMatch);
                selectPlayers();
            } else {
                JOptionPane.showMessageDialog(this, "No ongoing match found. Start a new match.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading match data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            currentMatch = null; // Reset currentMatch if an error occurs
        }
    }
    

    private void startMatch() {
        try {
            String tossWinner = JOptionPane.showInputDialog(this, "Who won the toss? (Team 1 / Team 2)");
            if (!"Team 1".equalsIgnoreCase(tossWinner) && !"Team 2".equalsIgnoreCase(tossWinner)) {
                JOptionPane.showMessageDialog(this, "Invalid team! Please choose 'Team 1' or 'Team 2'.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            String choice = JOptionPane.showInputDialog(this, "What did they choose? (Bat / Bowl)");
            if (!"Bat".equalsIgnoreCase(choice) && !"Bowl".equalsIgnoreCase(choice)) {
                JOptionPane.showMessageDialog(this, "Invalid choice! Please choose 'Bat' or 'Bowl'.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            currentMatch = dataManager.startNewMatch(tossWinner, choice);
            if (currentMatch == null) {
                JOptionPane.showMessageDialog(this, "Match with ID 1 is already ongoing or failed to start a new match.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            setupMatch(currentMatch);
            JOptionPane.showMessageDialog(this, "Match started successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            selectPlayers();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error starting match: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void selectPlayers() {
        List<Player> battingPlayers = currentMatch.getTeam1().getPlayers();
        List<Player> bowlingPlayers = currentMatch.getTeam2().getPlayers();
    
        Player selectedStriker = (Player) JOptionPane.showInputDialog(this, "Select Striker", "Player Selection",
                JOptionPane.QUESTION_MESSAGE, null, battingPlayers.toArray(), battingPlayers.get(0));
        Player selectedNonStriker = (Player) JOptionPane.showInputDialog(this, "Select Non-Striker", "Player Selection",
                JOptionPane.QUESTION_MESSAGE, null, battingPlayers.toArray(), battingPlayers.get(1));
        Player selectedBowler = (Player) JOptionPane.showInputDialog(this, "Select Bowler", "Player Selection",
                JOptionPane.QUESTION_MESSAGE, null, bowlingPlayers.toArray(), bowlingPlayers.get(0));
    
        if (selectedStriker != null && selectedNonStriker != null && selectedBowler != null) {
            striker = selectedStriker;
            nonStriker = selectedNonStriker;
            currentBowler = selectedBowler;
    
            updateScoreLabel();
            populateTables();
        } else {
            JOptionPane.showMessageDialog(this, "Player selection incomplete. Please select all players.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void endMatch() {
        try {
            dataManager.endMatch(1);
            JOptionPane.showMessageDialog(this, "Match ended successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error ending match: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void setupMatch(Match match) {
        if (match == null) {
            JOptionPane.showMessageDialog(this, "No match data found. Please start a new match.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
    
        battingTeam = match.getBattingTeam(match.getTossWinner().equals("Team 1") ? 1 : 2);
        bowlingTeam = match.getTeam1().equals(battingTeam) ? match.getTeam2() : match.getTeam1();
    
        if (battingTeam.getPlayers().isEmpty() || bowlingTeam.getPlayers().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Teams do not have enough players to start the match.", "Error", JOptionPane.ERROR_MESSAGE);
            currentMatch = null; // Reset match
            return;
        }
    
        // Assign initial players
        striker = battingTeam.getPlayers().get(0);
        nonStriker = battingTeam.getPlayers().get(1);
        currentBowler = bowlingTeam.getPlayers().get(0);
    
        updateScoreLabel();
        populateTables();
    }
    
    
private void addRun(int runs) {
    if (currentMatch == null) {
        JOptionPane.showMessageDialog(this, "No match in progress!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    totalRuns += runs;
    striker.setRuns(striker.getRuns() + runs);

    // Update database
    dataManager.updatePlayer(striker);
    dataManager.updateMatch(currentMatch);

    if (runs % 2 != 0) {
        switchStrikers();
    }
    updateScoreLabel();
    updatePlayerTables();
}


    private void switchStrikers() {
        Player temp = striker;
        striker = nonStriker;
        nonStriker = temp;
    }

    private void addWicket() {
        totalWickets++;
        JOptionPane.showMessageDialog(this, "Player Out! Please select the next batsman.", "Player Out", JOptionPane.INFORMATION_MESSAGE);

        String nextBatsmanName = JOptionPane.showInputDialog(this, "Enter the next batsman's name:");
        Player nextBatsman = dataManager.getPlayerByName(nextBatsmanName);

        if (nextBatsman == null) {
            JOptionPane.showMessageDialog(this, "Invalid batsman name!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        striker = nextBatsman;
        updateScoreLabel();
    }

    private void nextBall() {
        ballsBowled++;

        if (ballsBowled == 6) {
            ballsBowled = 0;
            totalOvers++;
            JOptionPane.showMessageDialog(this, "Over Completed! Please select the next bowler.", "New Over", JOptionPane.INFORMATION_MESSAGE);

            String nextBowlerName = JOptionPane.showInputDialog(this, "Enter the next bowler's name:");
            Player nextBowler = dataManager.getPlayerByName(nextBowlerName);

            if (nextBowler == null || !nextBowler.isEligibleBowler(TOTAL_OVERS_IN_MATCH / 2)) {
                JOptionPane.showMessageDialog(this, "Invalid or ineligible bowler selected!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            currentBowler = nextBowler;
        }

        updateScoreLabel();
    }

    private void addExtraRuns(String type, int runs) {
        totalRuns += runs;
        if (type.equalsIgnoreCase("No Ball")) {
            striker.setRuns(striker.getRuns() + 1); // Add run to striker for no-ball
            updateScoreLabel();
            JOptionPane.showMessageDialog(this, "No Ball! 1 run added to total score.", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else {
            updateScoreLabel();
            JOptionPane.showMessageDialog(this, "Extra runs added: " + runs, "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void switchInning() {
        isFirstInning = !isFirstInning;
        Team temp = battingTeam;
        battingTeam = bowlingTeam;
        bowlingTeam = temp;

        totalRuns = 0;
        totalWickets = 0;
        totalOvers = 0;
        ballsBowled = 0;

        setupMatch(currentMatch);
        updateScoreLabel();
    }

    private void updateScoreLabel() {
        double overs = totalOvers + (ballsBowled / 6.0);
        scoreLabel.setText(String.format("Score: %d/%d (%.1f Overs)", totalRuns, totalWickets, overs));

        double runRate = (overs > 0) ? totalRuns / overs : 0;
        int projectedScore = (int) (runRate * TOTAL_OVERS_IN_MATCH);

        runRateLabel.setText(String.format("Run Rate: %.2f", runRate));
        projectedScoreLabel.setText(String.format("Projected Score: %d", projectedScore));
    }

    private void populateTables() {
  
        DefaultTableModel battingModel = (DefaultTableModel) battingTable.getModel();
        battingModel.setRowCount(0);
        for (Player player : battingTeam.getPlayers()) {
            battingModel.addRow(new Object[]{
                player.getName(),
                player.getRuns(),
                player.getBallsFaced(),
                player.getFours(),
                player.getSixes(),
                player.calculateStrikeRate()
            });
        }

        DefaultTableModel bowlerModel = (DefaultTableModel) bowlerTable.getModel();
        bowlerModel.setRowCount(0);
        for (Player player : bowlingTeam.getPlayers()) {
            bowlerModel.addRow(new Object[]{
                player.getName(),
                player.getOversBowled(),
                player.getMaidens(),
                player.getRuns(),
                player.getWickets(),
                player.calculateEconomy()
            });
        }

        DefaultTableModel overModel = (DefaultTableModel) overTable.getModel();
    overModel.setRowCount(0);
    for (int i = 0; i < currentMatch.getOvers(); i++) {
        overModel.addRow(new Object[]{
            i + 1,
            currentMatch.getOverRuns().get(i),
            currentMatch.getOverWickets().get(i)
        });
    }

    }

    private void updatePlayerTables() {
        DefaultTableModel battingModel = (DefaultTableModel) battingTable.getModel();
        battingModel.setRowCount(0);
        for (Player player : battingTeam.getPlayers()) {
            battingModel.addRow(new Object[] {
                player.getName(),
                player.getRuns(),
                player.getBallsFaced(),
                player.getFours(),
                player.getSixes(),
                player.calculateStrikeRate()
            });
        }

        DefaultTableModel bowlerModel = (DefaultTableModel) bowlerTable.getModel();
        bowlerModel.setRowCount(0);
        for (Player player : bowlingTeam.getPlayers()) {
            bowlerModel.addRow(new Object[] {
                player.getName(),
                player.getOversBowled(),
                player.getMaidens(),
                player.getRuns(),
                player.getWickets(),
                player.calculateEconomy()
            });
        }
    }

    private JScrollPane createTableScrollPane(JTable table, String title) {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(transparentColor),
            title,
            TitledBorder.CENTER,
            TitledBorder.TOP,
            new Font("Calibri", Font.BOLD, 18),
            foregroundColor));
            scrollPane.setBackground(backgroundColor);
        return scrollPane;
    }


    private JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        styleButton(button);
        return button;
    }

    private JLabel createLabel(String text, int style, int size) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Calibri", style, size));
        label.setForeground(Color.BLACK);
        return label;
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
}
