package frontend;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import backend.DataManager;
import backend.Player;

public class PlayerStats extends BackgroundPanel {

    private JTable playerStatsTable;
    private DefaultTableModel playerStatsModel;
    private DataManager dataManager;

    public PlayerStats(Image backgroundImage) {
        super(backgroundImage);
        dataManager = new DataManager(); // Initialize DataManager
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title Label
        JLabel titleLabel = new JLabel("Player Stats", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Calibri", Font.BOLD, 24));
        titleLabel.setForeground(foregroundColor);
        add(titleLabel, BorderLayout.NORTH);

        // Player Stats Table
        playerStatsModel = createPlayerStatsModel();
        playerStatsTable = createNonEditableTable(playerStatsModel);
        JScrollPane scrollPane = new JScrollPane(playerStatsTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(transparentColor));

        add(scrollPane, BorderLayout.CENTER);
         // Refresh Button
    JButton refreshButton = new JButton("Refresh Stats");
    refreshButton.setFont(new Font("Calibri", Font.BOLD, 16));
    refreshButton.setBackground(primaryBackgroundColor);
    refreshButton.setForeground(foregroundColor);
    refreshButton.setFocusPainted(false);

    // Add action listener to refresh button
    refreshButton.addActionListener(e -> refreshPlayerStats());
    add(refreshButton, BorderLayout.SOUTH); // Add button at the bottom
    }

    // Dynamically populate the player stats model
private DefaultTableModel createPlayerStatsModel() {
    String[] columns = {"Player Name", "Runs Scored", "Balls", "4s", "6s", "Runs Conceded", "Wickets", "Overs"};
    List<Player> filteredPlayers = dataManager.getFilteredPlayers(); // Fetch all players
    
    // Populate table with filtered players
    Object[][] data = new Object[filteredPlayers.size()][columns.length];
    for (int i = 0; i < filteredPlayers.size(); i++) {
        Player player = filteredPlayers.get(i);
        data[i][0] = player.getName();
        data[i][1] = player.getRunsScored();    // Runs Scored
        data[i][2] = player.getBallsFaced();    // Balls
        data[i][3] = player.getFours();         // 4s
        data[i][4] = player.getSixes();         // 6s
        data[i][5] = player.getRunsConceded();  // Runs Conceded
        data[i][6] = player.getWickets();       // Wickets
        data[i][7] = player.getOversBowled();   // Overs
    }

    return new DefaultTableModel(data, columns);
}

    // Create a non-editable table
    private JTable createNonEditableTable(DefaultTableModel model) {
        JTable table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
    
        // Ensure table header is properly linked
        table.getTableHeader().setFont(new Font("Calibri", Font.BOLD, 16));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setBackground(primaryBackgroundColor);
        table.getTableHeader().setForeground(foregroundColor);
    
        // Other table settings
        table.setFont(new Font("Calibri", Font.PLAIN, 16));
        table.setRowHeight(25);
        table.setShowGrid(true);
        table.setGridColor(blackColor);
        table.setBackground(secondaryBackgroundColor);
        table.setForeground(foregroundColor);
    
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    
        return table;
    }
    

    // Update stats of a specific player
    public void updatePlayerStats(String playerName, int runs, int balls, int fours, int sixes, int wickets, double overs) {
        for (int i = 0; i < playerStatsModel.getRowCount(); i++) {
            if (playerStatsModel.getValueAt(i, 0).equals(playerName)) {
                playerStatsModel.setValueAt(runs, i, 1);
                playerStatsModel.setValueAt(balls, i, 2);
                playerStatsModel.setValueAt(fours, i, 3);
                playerStatsModel.setValueAt(sixes, i, 4);
                playerStatsModel.setValueAt(wickets, i, 5);
                playerStatsModel.setValueAt(overs, i, 6);
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Player not found: " + playerName, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Update all player stats
    public void refreshPlayerStats() {
        List<Player> players = dataManager.getFilteredPlayers(); // Fetch all players from the database
        
        // Ensure the table model size matches the number of players
        if (players.size() != playerStatsModel.getRowCount()) {
            // Update table model with the new size if necessary
            playerStatsModel.setRowCount(players.size());
        }
    
        // Update each player's stats in the table
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            playerStatsModel.setValueAt(player.getName(), i, 0);
            playerStatsModel.setValueAt(player.getRunsScored(), i, 1);
            playerStatsModel.setValueAt(player.getBallsFaced(), i, 2);
            playerStatsModel.setValueAt(player.getFours(), i, 3);
            playerStatsModel.setValueAt(player.getSixes(), i, 4);
            playerStatsModel.setValueAt(player.getRunsConceded(), i, 5);
            playerStatsModel.setValueAt(player.getWickets(), i, 6);
            playerStatsModel.setValueAt(player.getOversBowled(), i, 7);
        }
    }
    

    // Reset all stats to 0
    public void resetStats() {
        for (int i = 0; i < playerStatsModel.getRowCount(); i++) {
            for (int j = 1; j < playerStatsModel.getColumnCount(); j++) {
                playerStatsModel.setValueAt(0, i, j);
            }
        }
    }
}
