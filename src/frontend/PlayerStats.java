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
        dataManager = new DataManager(); 
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        
        JLabel titleLabel = new JLabel("Player Stats", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Calibri", Font.BOLD, 24));
        titleLabel.setForeground(foregroundColor);
        add(titleLabel, BorderLayout.NORTH);

        
        playerStatsModel = createPlayerStatsModel();
        playerStatsTable = createNonEditableTable(playerStatsModel);
        JScrollPane scrollPane = new JScrollPane(playerStatsTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(transparentColor));

        add(scrollPane, BorderLayout.CENTER);
         
    JButton refreshButton = new JButton("Refresh Stats");
    refreshButton.setFont(new Font("Calibri", Font.BOLD, 16));
    refreshButton.setBackground(primaryBackgroundColor);
    refreshButton.setForeground(foregroundColor);
    refreshButton.setFocusPainted(false);

    
    refreshButton.addActionListener(e -> refreshPlayerStats());
    add(refreshButton, BorderLayout.SOUTH); 
    }

    
private DefaultTableModel createPlayerStatsModel() {
    String[] columns = {"Player Name", "Runs Scored", "Balls", "4s", "6s", "Runs Conceded", "Wickets", "Overs"};
    List<Player> filteredPlayers = dataManager.getFilteredPlayers(); 
    
    
    Object[][] data = new Object[filteredPlayers.size()][columns.length];
    for (int i = 0; i < filteredPlayers.size(); i++) {
        Player player = filteredPlayers.get(i);
        data[i][0] = player.getName();
        data[i][1] = player.getRunsScored();    
        data[i][2] = player.getBallsFaced();    
        data[i][3] = player.getFours();         
        data[i][4] = player.getSixes();         
        data[i][5] = player.getRunsConceded();  
        data[i][6] = player.getWickets();       
        data[i][7] = player.getOversBowled();   
    }

    return new DefaultTableModel(data, columns);
}

    
    private JTable createNonEditableTable(DefaultTableModel model) {
        JTable table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
    
        
        table.getTableHeader().setFont(new Font("Calibri", Font.BOLD, 16));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setBackground(primaryBackgroundColor);
        table.getTableHeader().setForeground(foregroundColor);
    
        
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

    
    public void refreshPlayerStats() {
        List<Player> players = dataManager.getFilteredPlayers(); 
        
        
        if (players.size() != playerStatsModel.getRowCount()) {
            
            playerStatsModel.setRowCount(players.size());
        }
    
        
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
    

    
    public void resetStats() {
        for (int i = 0; i < playerStatsModel.getRowCount(); i++) {
            for (int j = 1; j < playerStatsModel.getColumnCount(); j++) {
                playerStatsModel.setValueAt(0, i, j);
            }
        }
    }
}
