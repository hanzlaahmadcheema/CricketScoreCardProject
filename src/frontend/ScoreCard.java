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

        
        JButton startMatchButton = createButton("Start Match", e -> startMatch());
        JButton endMatchButton = createButton("End Match", e -> dataManager.endMatch(1));
        JButton addRunButton = createButton("Add Run", e -> addRun(1));
        JButton addWicketButton = createButton("Add Wicket", e -> addWicket());
        JButton nextBallButton = createButton("Next Ball", e -> nextBall());
        JButton addFourButton = createButton("Add 4 Runs", e -> addRun(4));
        JButton addSixButton = createButton("Add 6 Runs", e -> addRun(6));
        JButton noBallButton = createButton("No Ball", e -> addExtraRun());
        JButton extraRunButton = createButton("Extra Run", e -> addExtraRun());
        JButton switchInningButton = createButton("Switch Inning", e -> switchInning());

        
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
    
    private void setupMatch(Match match) {
        if (match == null) {
            JOptionPane.showMessageDialog(this, "No match data found. Please start a new match.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
    
        if (match.getChoice().equalsIgnoreCase("Bat")) {
            battingTeam = match.getTossWinner().equals(match.getTeam1().getName()) ? match.getTeam1() : match.getTeam2();
            bowlingTeam = match.getTossWinner().equals(match.getTeam1().getName()) ? match.getTeam2() : match.getTeam1();
        } else {
            bowlingTeam = match.getTossWinner().equals(match.getTeam1().getName()) ? match.getTeam1() : match.getTeam2();
            battingTeam = match.getTossWinner().equals(match.getTeam1().getName()) ? match.getTeam2() : match.getTeam1();
        }
    
        match.setBattingTeam(battingTeam);
        match.setBowlingTeam(bowlingTeam);
    
        updateScoreLabel();
        populateTables();
    }
    private void updateScoreLabel() {
        scoreLabel.setText("Score: " + currentMatch.getTotalRuns() + "/" + currentMatch.getTotalWickets() + " (" + currentMatch.getTotalOvers() + " Overs)");
    }

    private void addRun(int runs) {
        striker.setRuns(striker.getRuns() + runs);
        currentMatch.setTotalRuns(currentMatch.getTotalRuns() + runs);
        updateScoreLabel();
        populateTables();
        dataManager.savePlayerData(striker);
        dataManager.saveMatchData(currentMatch);
    }

    private void addWicket() {
        currentMatch.setTotalWickets(currentMatch.getTotalWickets() + 1);
        updateScoreLabel();
        populateTables();
        dataManager.saveMatchData(currentMatch);
    }

    private void nextBall() {
        striker.setBallsFaced(striker.getBallsFaced() + 1);
        currentMatch.setTotalOvers(currentMatch.getTotalOvers() + 0.1);
        if (currentMatch.getTotalOvers() % 1 == 0.6) {
            currentMatch.setTotalOvers(currentMatch.getTotalOvers() + 0.4);
        }
        updateScoreLabel();
        populateTables();
        dataManager.savePlayerData(striker);
        dataManager.saveMatchData(currentMatch);
    }

    private void addExtraRun() {
        currentMatch.setTotalRuns(currentMatch.getTotalRuns() + 1);
        updateScoreLabel();
        populateTables();
        dataManager.saveMatchData(currentMatch);
    }

    private void switchInning() {
        currentMatch.switchInning();
        updateScoreLabel();
        populateTables();
        selectPlayers();
        dataManager.saveMatchData(currentMatch);
    }

    private void populateTables() {
  
        DefaultTableModel battingModel = (DefaultTableModel) battingTable.getModel();
        battingModel.setRowCount(0);
        for (Player player : currentMatch.getBattingTeam().getPlayers()) {
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
        for (Player player : currentMatch.getBowlingTeam().getPlayers()) {
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
    for (int i = 0; i < currentMatch.getTotalOvers(); i++) {
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
