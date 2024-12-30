package frontend;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// PlayerStats class to display and manage individual player statistics and partnerships
public class PlayerStats extends BackgroundPanel {
    // Tables and models for player stats and partnership tracking
    private JTable playerStatsTable;
    private JTable partnershipTable;
    private DefaultTableModel playerStatsModel;
    private DefaultTableModel partnershipModel;

    // Constructor to initialize the PlayerStats UI
    public PlayerStats(Image backgroundImage) {
        super(backgroundImage); // Call parent constructor to set background image
        setLayout(new BorderLayout(20, 20));  // Layout with spacing between components
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around the panel

        // Title Label for Player Stats
        JLabel titleLabel = new JLabel("Player Stats", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Calibri", Font.BOLD, 24));
        titleLabel.setForeground(foregroundColor);
        add(titleLabel, BorderLayout.NORTH);

        // Player Stats Table
        playerStatsModel = createPlayerStatsModel();
        playerStatsTable = createNonEditableTable(playerStatsModel);
        JScrollPane playerScrollPane = new JScrollPane(playerStatsTable);
        playerScrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); // Padding for scroll pane
        playerScrollPane.setOpaque(false);
        add(playerScrollPane, BorderLayout.CENTER);

        // Partnership Panel and Table
        JPanel partnershipPanel = new JPanel(new BorderLayout(20, 20));
        partnershipPanel.setBackground(backgroundColor);

        JLabel partnershipLabel = new JLabel("Partnership Tracking", SwingConstants.CENTER);
        partnershipLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        partnershipLabel.setForeground(foregroundColor);
        partnershipPanel.add(partnershipLabel, BorderLayout.NORTH);

        partnershipModel = createPartnershipModel();
        partnershipTable = createNonEditableTable(partnershipModel);
        JScrollPane partnershipScrollPane = new JScrollPane(partnershipTable);
        partnershipScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        partnershipScrollPane.setOpaque(false);
        partnershipScrollPane.getViewport().setOpaque(false);
        partnershipPanel.add(partnershipScrollPane, BorderLayout.CENTER);

        add(partnershipPanel, BorderLayout.SOUTH); // Add partnership panel to the bottom
    }

    // Method to create the model for the Player Stats table
    private DefaultTableModel createPlayerStatsModel() {
        String[] columns = {"Player Name", "Runs", "Balls", "4s", "6s", "Wickets", "Overs"};
        Object[][] data = {
            {"Player 1", 0, 0, 0, 0, 0, 0},
            {"Player 2", 0, 0, 0, 0, 0, 0},
            {"Player 3", 0, 0, 0, 0, 0, 0},
            {"Player 4", 0, 0, 0, 0, 0, 0}
        };
        return new DefaultTableModel(data, columns);
    }

    // Method to create the model for the Partnership Tracking table
    private DefaultTableModel createPartnershipModel() {
        String[] columns = {"Batsman 1", "Batsman 2", "Runs Scored"};
        Object[][] data = {
            {"Player 1", "Player 2", 0},
            {"Player 3", "Player 4", 0}
        };
        return new DefaultTableModel(data, columns);
    }

    // Method to create a non-editable table with consistent styling
    private JTable createNonEditableTable(DefaultTableModel model) {
        JTable table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Prevent editing of cells
            }
        };

        // Styling for the table
        table.setFont(new Font("Calibri", Font.PLAIN, 16));
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

    // Method to update individual player statistics
    public void updatePlayerStats(String playerName, int runs, int balls, int fours, int sixes, int wickets, double overs) {
        for (int i = 0; i < playerStatsModel.getRowCount(); i++) {
            if (playerStatsModel.getValueAt(i, 0).equals(playerName)) {
                // Update stats for the matching player
                playerStatsModel.setValueAt(runs, i, 1);
                playerStatsModel.setValueAt(balls, i, 2);
                playerStatsModel.setValueAt(fours, i, 3);
                playerStatsModel.setValueAt(sixes, i, 4);
                playerStatsModel.setValueAt(wickets, i, 5);
                playerStatsModel.setValueAt(overs, i, 6);
                return;
            }
        }
        // Show error if player is not found
        JOptionPane.showMessageDialog(this, "Player not found: " + playerName, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Method to update partnership statistics
    public void updatePartnership(String batsman1, String batsman2, int runsScored) {
        for (int i = 0; i < partnershipModel.getRowCount(); i++) {
            if (partnershipModel.getValueAt(i, 0).equals(batsman1) && partnershipModel.getValueAt(i, 1).equals(batsman2)) {
                // Update runs for the matching partnership
                partnershipModel.setValueAt(runsScored, i, 2);
                return;
            }
        }
        // Show error if partnership is not found
        JOptionPane.showMessageDialog(this, "Partnership not found: " + batsman1 + " and " + batsman2, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    // Method to reset all statistics
    public void resetStats() {
        for (int i = 0; i < playerStatsModel.getRowCount(); i++) {
            for (int j = 1; j < playerStatsModel.getColumnCount(); j++) {
                playerStatsModel.setValueAt(0, i, j);
            }
        }
        for (int i = 0; i < partnershipModel.getRowCount(); i++) {
            partnershipModel.setValueAt(0, i, 2);
        }
    }
}
