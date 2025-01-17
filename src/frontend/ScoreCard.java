package frontend;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import backend.*;

import java.util.ArrayList;
import java.util.Collections;
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
    private Team team1, team2;
    private int target;

    public ScoreCard(Image backgroundImage) {
        super(backgroundImage);
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        dataManager = new DataManager();

        
        setupTopPanel();

        
        setupCenterPanel();

        
        setupButtonPanel();

        
        loadMatchData();
    }

    private void setupTopPanel() {
        JPanel topPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        topPanel.setOpaque(false);

        inningLabel = createLabel("Inning: 1", Font.BOLD, 20);
        currentTeamLabel = createLabel("Current Team: -", Font.BOLD, 20);
        scoreLabel = createLabel("Score: 0/0 (0.0 Overs)", Font.BOLD, 24);
        runRateLabel = createLabel("Run Rate: 0.00", Font.BOLD, 18);
        projectedScoreLabel = createLabel("Projected Score: 0", Font.BOLD, 18);

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
        

        add(centerPanel, BorderLayout.CENTER);
    }

    private void setupButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setOpaque(false);

        
        JButton startMatchButton = createButton("Start Match", e -> startMatch());
        JButton endMatchButton = createButton("End Match", e -> endMatch());
        JButton add1RunButton = createButton("1", e -> addRun(1));
        JButton add2RunButton = createButton("2", e -> addRun(2));
        JButton add3RunButton = createButton("3", e -> addRun(3));
        JButton add4RunButton = createButton("4", e -> add4Runs());
        JButton add5RunButton = createButton("5", e -> addRun(5));
        JButton add6RunButton = createButton("6", e -> add6Runs());
        JButton addextraRunButton = createButton("Extra", e -> extraRun());
        JButton addWicketButton = createButton("Wicket", e -> addWicket());
        JButton nextBallButton = createButton("Next Ball", e -> nextBallUpdate());
        JButton switchStrikerButton = createButton("Switch Striker", e -> switchStriker());
        JButton switchInningButton = createButton("Switch Inning", e -> switchInning());
        JButton resetMatchButton = createButton("Reset", e -> resetMatchData());

        
        buttonPanel.add(startMatchButton);
        buttonPanel.add(add1RunButton);
        buttonPanel.add(add2RunButton);
        buttonPanel.add(add3RunButton);
        buttonPanel.add(add4RunButton);
        buttonPanel.add(add5RunButton);
        buttonPanel.add(add6RunButton);
        buttonPanel.add(addextraRunButton);
        buttonPanel.add(addWicketButton);
        buttonPanel.add(nextBallButton);
        
        
        buttonPanel.add(resetMatchButton);
        buttonPanel.add(endMatchButton);

        add(buttonPanel, BorderLayout.SOUTH);
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
        label.setForeground(Color.WHITE);
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
        String[] columns = { "Bowler", "Overs", "Runs", "Wickets", "Economy" };
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

    private void loadMatchData() {
        try {
            currentMatch = dataManager.getOngoingMatch();
            if (currentMatch != null) {
                setupMatch(currentMatch);
                updateScoreLabel(); 
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading match data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            currentMatch = null;
        }
    }
        private void startMatch() {
            if (currentMatch != null) {
                JOptionPane.showMessageDialog(this, "A match is already ongoing. Please end the current match before starting a new one.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            dataManager.resetPlayerData();
            try {
                String tossWinner = JOptionPane.showInputDialog(this, "Which team won the toss?");
                if (tossWinner == null || tossWinner.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Invalid toss winner!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                String choice = JOptionPane.showInputDialog(this, "What did they choose? (Bat / Bowl)");
                if (!"Bat".equalsIgnoreCase(choice) && !"Bowl".equalsIgnoreCase(choice)) {
                    JOptionPane.showMessageDialog(this, "Invalid choice! Please choose 'Bat' or 'Bowl'.", "Error", JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(this, "Error starting match: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        private void selectPlayers() {
        List<Player> battingPlayers = currentMatch.getBattingTeam().getPlayers();
        List<Player> bowlingPlayers = currentMatch.getBowlingTeam().getPlayers();
    
        Player selectedStriker = (Player) JOptionPane.showInputDialog(this, "Select Striker", "Player Selection",
                JOptionPane.QUESTION_MESSAGE, null, battingPlayers.toArray(), battingPlayers.get(0));
        Player selectedNonStriker = (Player) JOptionPane.showInputDialog(this, "Select Non-Striker", "Player Selection",
                JOptionPane.QUESTION_MESSAGE, null, battingPlayers.toArray(), battingPlayers.get(1));
        Collections.reverse(bowlingPlayers);
        Player selectedBowler = (Player) JOptionPane.showInputDialog(this, "Select Bowler", "Player Selection",
            JOptionPane.QUESTION_MESSAGE, null, bowlingPlayers.toArray(), bowlingPlayers.get(0));
        Collections.reverse(bowlingPlayers);
    
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
    
private void selectNewBowler() {
    List<Player> bowlingPlayers = currentMatch.getBowlingTeam().getPlayers();
    List<Player> eligibleBowlers = new ArrayList<>();

    for (Player player : bowlingPlayers) {
        if (player.getOversBowled() < 3 && player != currentBowler) {
            eligibleBowlers.add(player);
        }
    }

    if (eligibleBowlers.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No eligible bowlers left. All bowlers have completed their 3 overs.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    
    Collections.reverse(eligibleBowlers);

    Player selectedBowler = null;
    while (selectedBowler == null) {
        selectedBowler = (Player) JOptionPane.showInputDialog(this, "Select New Bowler", "Player Selection",
                JOptionPane.QUESTION_MESSAGE, null, eligibleBowlers.toArray(), eligibleBowlers.get(0));

        if (selectedBowler == null) {
            JOptionPane.showMessageDialog(this, "Bowler selection incomplete. Please select a bowler.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    currentBowler = selectedBowler;
    JOptionPane.showMessageDialog(this, "New bowler selected: " + currentBowler.getName(), "Info", JOptionPane.INFORMATION_MESSAGE);
}

private void endMatch() {
    if (currentMatch == null) {
        JOptionPane.showMessageDialog(this, "No ongoing match to end.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    int confirmation = JOptionPane.showConfirmDialog(this, "Are you sure you want to end the match?", "Confirm End Match", JOptionPane.YES_NO_OPTION);
    if (confirmation == JOptionPane.YES_OPTION) {
        currentMatch.setStatus(Match.MatchStatus.Completed);
        dataManager.saveMatchData(currentMatch);

        String winningTeam;
        if (currentMatch.getTotalRuns() > currentMatch.getBowlingTeam().getTotalRuns()) {
            winningTeam = currentMatch.getBattingTeam().getName();
        } else if (currentMatch.getTotalRuns() < currentMatch.getBowlingTeam().getTotalRuns()) {
            winningTeam = currentMatch.getBowlingTeam().getName();
        } else {
            winningTeam = "Match Tied";
        }

        JOptionPane.showMessageDialog(this, "Match ended successfully! Winning team: " + winningTeam, "Success", JOptionPane.INFORMATION_MESSAGE);
        resetUI();
        refreshData();
    }
}
    
    private void resetUI() {
        currentMatch = null;
        striker = null;
        nonStriker = null;
        currentBowler = null;
        battingTeam = null;
        bowlingTeam = null;
    
        inningLabel = createLabel("Inning: 1", Font.BOLD, 20);
        currentTeamLabel = createLabel("Current Team: -", Font.BOLD, 20);
        scoreLabel.setText("Score: 0/0 (0.0 Overs)");
        runRateLabel.setText("Run Rate: 0.0");
        projectedScoreLabel.setText("Projected Score: 0");
    
        DefaultTableModel battingModel = (DefaultTableModel) battingTable.getModel();
        battingModel.setRowCount(0);
    
        DefaultTableModel bowlerModel = (DefaultTableModel) bowlerTable.getModel();
        bowlerModel.setRowCount(0);
    
        DefaultTableModel overModel = (DefaultTableModel) overTable.getModel();
        overModel.setRowCount(0);
    }
    
    private void setupMatch(Match match) {
        if (match == null) {
            JOptionPane.showMessageDialog(this, "No match data found. Please start a new match.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
    
        Team team1 = match.getTeam1();
        Team team2 = match.getTeam2();
        String tossWinner = match.getTossWinner();
        String choice = match.getChoice();
    
        if (choice.equalsIgnoreCase("Bat")) {
            battingTeam = tossWinner.equals(team1.getName()) ? team1 : team2;
            bowlingTeam = tossWinner.equals(team1.getName()) ? team2 : team1;
        } else {
            bowlingTeam = tossWinner.equals(team1.getName()) ? team1 : team2;
            battingTeam = tossWinner.equals(team1.getName()) ? team2 : team1;
        }
    
        match.setBattingTeam(battingTeam);
        match.setBowlingTeam(bowlingTeam);
    
        updateScoreLabel();
        populateTables();
    }
    
    private void resetMatchData() {
        currentMatch.setTotalRuns(0);
        currentMatch.setTotalWickets(0);
        currentMatch.setBallsBowled(0);
        currentMatch.setTotalOvers(0);
        striker = currentMatch.getBattingTeam().getPlayers().get(0);
        nonStriker = currentMatch.getBattingTeam().getPlayers().get(1);
        currentBowler = currentMatch.getBowlingTeam().getPlayers().get(0);
        dataManager.resetPlayerData();
        refreshData();
        populateTables();
        dataManager.clearPlayerStats();
    }
    
    private void addRun(int runs) {
        if (currentMatch==null) {
            JOptionPane.showMessageDialog(this, "Match is not ongoing. Please start a new match.", "Info", JOptionPane.INFORMATION_MESSAGE);
            int confirmation = JOptionPane.showConfirmDialog(this, "Do you want to start a new match?", "Confirm Start Match", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
            startMatch();
            }
            return;
        }

        if (!isFirstInning && currentMatch.getTotalRuns() + runs >= target) {
            // Batting team wins
            currentMatch.setTotalRuns(currentMatch.getTotalRuns() + runs);
            updateScoreLabel();
            JOptionPane.showMessageDialog(this, "Match ended! Winner: " + currentMatch.getBattingTeam().getName(), "Success", JOptionPane.INFORMATION_MESSAGE);
            endMatch(); // Automatically end the match
            return;
        }

        if (currentMatch.getTotalOvers() >= TOTAL_OVERS_IN_MATCH || currentMatch.getTotalWickets() >= MAX_WICKETS) {
            JOptionPane.showMessageDialog(this, "Inning is already completed.", "Info", JOptionPane.INFORMATION_MESSAGE);
            if (isFirstInning) {
                int confirmation = JOptionPane.showConfirmDialog(this, "Do you want to switch the inning?", "Confirm Switch Inning", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                switchInning();
                }
            }
            return;
        }
        
        striker.setRunsScored(striker.getRunsScored() + runs);
        currentMatch.setTotalRuns(currentMatch.getTotalRuns() + runs);
        currentBowler.setRunsConceded(currentBowler.getRunsConceded() + runs);
        nextBallUpdate();

        if (runs == 1 || runs == 3) {
            switchStriker();
        }
    }

    private void extraRun() {
        if (currentMatch==null) {
            JOptionPane.showMessageDialog(this, "Match is not ongoing. Please start a new match.", "Info", JOptionPane.INFORMATION_MESSAGE);
            int confirmation = JOptionPane.showConfirmDialog(this, "Do you want to start a new match?", "Confirm Start Match", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
            startMatch();
            }
            return;
        }
        if (currentMatch.getTotalOvers() >= TOTAL_OVERS_IN_MATCH || currentMatch.getTotalWickets() >= MAX_WICKETS) {
            JOptionPane.showMessageDialog(this, "Inning is already completed.", "Info", JOptionPane.INFORMATION_MESSAGE);
            if (isFirstInning) {
                int confirmation = JOptionPane.showConfirmDialog(this, "Do you want to switch the inning?", "Confirm Switch Inning", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                switchInning();
                }
            }
        }
        
        currentMatch.setTotalRuns(currentMatch.getTotalRuns() + 1);
        currentBowler.setRunsConceded(currentBowler.getRunsConceded() + 1);

        updateScoreLabel();
        populateTables();
        dataManager.updatePlayerBowlingStats(currentBowler);
        dataManager.saveMatchData(currentMatch);
    }
    
    private void addWicket() {
        if (currentMatch==null) {
            JOptionPane.showMessageDialog(this, "Match is not ongoing. Please start a new match.", "Info", JOptionPane.INFORMATION_MESSAGE);
            int confirmation = JOptionPane.showConfirmDialog(this, "Do you want to start a new match?", "Confirm Start Match", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
            startMatch();
            }
            return;
        }
        if (currentMatch.getTotalOvers() >= TOTAL_OVERS_IN_MATCH || currentMatch.getTotalWickets() >= MAX_WICKETS) {
            JOptionPane.showMessageDialog(this, "Inning is already completed.", "Info", JOptionPane.INFORMATION_MESSAGE);
            if (isFirstInning) {
                int confirmation = JOptionPane.showConfirmDialog(this, "Do you want to switch the inning?", "Confirm Switch Inning", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                switchInning();
                return;
                }
            }
        }
        currentMatch.setTotalWickets(currentMatch.getTotalWickets() + 1);
        currentBowler.setWickets(currentBowler.getWickets() + 1);
        nextBallUpdate();
    
        if (currentMatch.getTotalWickets() >= MAX_WICKETS) {
            JOptionPane.showMessageDialog(this, "All wickets are down. Inning is completed.", "Info", JOptionPane.INFORMATION_MESSAGE);
            if (isFirstInning) {
                int confirmation = JOptionPane.showConfirmDialog(this, "Do you want to switch the inning?", "Confirm Switch Inning", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                switchInning();
                return;
                }
            }
        }
        
        List<Player> battingPlayers = currentMatch.getBattingTeam().getPlayers();
        List<Player> availableBatsmen = new ArrayList<>();
        for (Player player : battingPlayers) {
            if (!player.getIsOut() && player != striker && player != nonStriker) {
                availableBatsmen.add(player);
            }
        }
    
        if (availableBatsmen.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No available batsmen left.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        Player newBatsman = null;
        while (newBatsman == null) {
            newBatsman = (Player) JOptionPane.showInputDialog(this, "Select New Batsman", "Player Selection",
                    JOptionPane.QUESTION_MESSAGE, null, availableBatsmen.toArray(), availableBatsmen.get(0));
    
            if (newBatsman == null) {
                JOptionPane.showMessageDialog(this, "Batsman selection incomplete. Please select a batsman.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    
        
        striker.setIsOut(true);
        striker = newBatsman;
        JOptionPane.showMessageDialog(this, "New batsman selected: " + striker.getName(), "Info", JOptionPane.INFORMATION_MESSAGE);
    
        updateScoreLabel();
        populateTables();
        dataManager.savePlayerData(striker);
        dataManager.updatePlayerBowlingStats(currentBowler);
        dataManager.saveMatchData(currentMatch);
    }
    

    private void nextBallUpdate() {
        if (currentMatch==null) {
            JOptionPane.showMessageDialog(this, "Match is not ongoing. Please start a new match.", "Info", JOptionPane.INFORMATION_MESSAGE);
            int confirmation = JOptionPane.showConfirmDialog(this, "Do you want to start a new match?", "Confirm Start Match", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
            startMatch();
            }
            return;
        }
        if (currentMatch.getTotalOvers() >= TOTAL_OVERS_IN_MATCH || currentMatch.getTotalWickets() == MAX_WICKETS) {
            JOptionPane.showMessageDialog(this, "Inning is already completed.", "Info", JOptionPane.INFORMATION_MESSAGE);
            if (isFirstInning) {
                int confirmation = JOptionPane.showConfirmDialog(this, "Do you want to switch the inning?", "Confirm Switch Inning", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                switchInning();
                return;
                }
            }
        }
        currentMatch.setBallsBowled(currentMatch.getBallsBowled() + 1);
        currentBowler.setBallsBowled(currentBowler.getBallsBowled() + 1);
        striker.setBallsFaced(striker.getBallsFaced() + 1);
    
        
        int completedOvers = currentMatch.getBallsBowled() / 6;
        int remainingBalls = currentMatch.getBallsBowled() % 6;
        currentMatch.setTotalOvers(completedOvers + remainingBalls / 10.0);
    
        
        if (remainingBalls == 0) {
            currentBowler.setOversBowled(currentBowler.getOversBowled() + 1);
            if (currentBowler.getOversBowled() >= 3) {
                JOptionPane.showMessageDialog(this, currentBowler.getName() + " has completed their 3 overs.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
            switchStriker(); 
            selectNewBowler();
        }
    
        updateScoreLabel();
        populateTables();
        dataManager.savePlayerData(striker);
        dataManager.updatePlayerBowlingStats(currentBowler);
        dataManager.saveMatchData(currentMatch);
    }

        private void add4Runs() {
        if (currentMatch.getTotalOvers() >= TOTAL_OVERS_IN_MATCH || currentMatch.getTotalWickets() == MAX_WICKETS) {
            JOptionPane.showMessageDialog(this, "Inning is already completed.", "Info", JOptionPane.INFORMATION_MESSAGE);
            if (isFirstInning) {
                int confirmation = JOptionPane.showConfirmDialog(this, "Do you want to switch the inning?", "Confirm Switch Inning", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                switchInning();
                }
            }
        }
        striker.setFours(striker.getFours() + 1); 
        dataManager.updatePlayerFours(striker); 
        addRun(4);
        }

        private void add6Runs() {
        if (currentMatch.getTotalOvers() >= TOTAL_OVERS_IN_MATCH || currentMatch.getTotalWickets() == MAX_WICKETS) {
            JOptionPane.showMessageDialog(this, "Inning is already completed.", "Info", JOptionPane.INFORMATION_MESSAGE);
            if (isFirstInning) {
            int confirmation = JOptionPane.showConfirmDialog(this, "Do you want to switch the inning?", "Confirm Switch Inning", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
            switchInning();
            }
        }
            return;
        }
        striker.setSixes(striker.getSixes() + 1);
        dataManager.updatePlayerSixes(striker);
        addRun(6);
    }

    private void switchStriker() {
        Player temp = striker;
        striker = nonStriker;
        nonStriker = temp;
    }
    
    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + currentMatch.getTotalRuns() + "/" + currentMatch.getTotalWickets() + " (" + currentMatch.getTotalOvers() + " Overs)");
        runRateLabel.setText("Run Rate: " + calculateRunRate());
    
        if (isFirstInning) {
            projectedScoreLabel.setText("Projected Score: " + calculateProjectedScore());
        } else {
            if (currentMatch.getTotalRuns() >= target) {
                projectedScoreLabel.setText("Winner: " + currentMatch.getBattingTeam().getName());
            } else {
                projectedScoreLabel.setText("Target: " + target);
            }
        }
    
        inningLabel.setText("Inning: " + (isFirstInning ? "1" : "2"));
        currentTeamLabel.setText("Current Team: " + (isFirstInning ? currentMatch.getBattingTeam().getName() : currentMatch.getBowlingTeam().getName()));
    }
    

        private double calculateRunRate() {
        return currentMatch.getTotalOvers() == 0 ? 0.0 : Math.round((currentMatch.getTotalRuns() / currentMatch.getTotalOvers()) * 100.0) / 100.0;
        }
        
        private int calculateProjectedScore() {
        return (int) (calculateRunRate() * TOTAL_OVERS_IN_MATCH);
    }
    
    private void populateTables() {
        updateBattingTable();
        updateBowlingTable();
        updateOverTable();
    }
    
    public void refreshData() {
        if (currentMatch == null) {
            JOptionPane.showMessageDialog(this, "No ongoing match to refresh.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        updateScoreLabel();
        populateTables();
    }

    private void updateBattingTable() {
        DefaultTableModel battingModel = (DefaultTableModel) battingTable.getModel();
        battingModel.setRowCount(0);
        for (Player player : currentMatch.getBattingTeam().getPlayers()) {
            battingModel.addRow(new Object[]{
                player.getName(),
                player.getRunsScored(),
                player.getBallsFaced(),
                player.getFours(),
                player.getSixes(),
                player.calculateStrikeRate()
            });
        }
    }
    
    private void updateBowlingTable() {
        DefaultTableModel bowlerModel = (DefaultTableModel) bowlerTable.getModel();
        bowlerModel.setRowCount(0);
        for (Player player : currentMatch.getBowlingTeam().getPlayers()) {
            int completedOvers = (int) player.getBallsBowled() / 6;
            int remainingBalls = (int) player.getBallsBowled() % 6;
            double fractionalOvers = completedOvers + remainingBalls / 10.0;
            bowlerModel.addRow(new Object[]{
                player.getName(),
                fractionalOvers,
                player.getRunsConceded(),
                player.getWickets(),
                player.calculateEconomy()
            }); 
        }
    }
    
    private void updateOverTable() {
        DefaultTableModel overModel = (DefaultTableModel) overTable.getModel();
        overModel.setRowCount(0);
        for (int i = 0; i < currentMatch.getTotalOvers(); i++) {
            if (i < currentMatch.getOverRuns().size() && i < currentMatch.getOverWickets().size()) {
                overModel.addRow(new Object[]{
                    i + 1,
                    currentMatch.getOverRuns().get(i),
                    currentMatch.getOverWickets().get(i)
                });
            }
        }
    }
    
    private void switchInning() {
        if (isFirstInning) {
            // Switching to the second inning
            isFirstInning = false;
            currentMatch.switchInning();
            target = currentMatch.getTotalRuns() + 1; // Set the target
            resetMatchData();
            updateScoreLabel();
            populateTables();
            dataManager.saveMatchData(currentMatch);
            JOptionPane.showMessageDialog(this, "First inning completed. Target for second team: " + target, "Info", JOptionPane.INFORMATION_MESSAGE);
            refreshData();
        } else {
            // End the match and determine the winner
            String winningTeam;
            if (currentMatch.getTotalRuns() >= target) {
                winningTeam = currentMatch.getBattingTeam().getName();
            } else if (currentMatch.getTotalRuns() < target - 1) {
                winningTeam = currentMatch.getBowlingTeam().getName();
            } else {
                winningTeam = "Match Tied";
            }
    
            JOptionPane.showMessageDialog(this, "Match ended successfully! Winning team: " + winningTeam, "Success", JOptionPane.INFORMATION_MESSAGE);
            currentMatch.setStatus(Match.MatchStatus.Completed);
            dataManager.saveMatchData(currentMatch);
            resetUI(); // Reset the UI after the match ends
        }
    }
    

}


