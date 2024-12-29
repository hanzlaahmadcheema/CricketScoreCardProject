package frontend;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PlayerStatsGUI extends BackgroundPanel {
    private JTable playerStatsTable;
    private JTable partnershipTable;
    private DefaultTableModel playerStatsModel;
    private DefaultTableModel partnershipModel;

    public PlayerStatsGUI(Image backgroundImage) {
        super(backgroundImage);
        setLayout(new BorderLayout(20, 20)); 
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 

        JLabel titleLabel = new JLabel("Player Stats", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Garamond", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 102, 204));
        add(titleLabel, BorderLayout.NORTH);

        playerStatsModel = createPlayerStatsModel();
        playerStatsTable = createNonEditableTable(playerStatsModel);
        JScrollPane playerScrollPane = new JScrollPane(playerStatsTable);
        playerScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 102, 204)), "Player Statistics", TitledBorder.LEFT, TitledBorder.TOP, new Font("Garamond", Font.BOLD, 18), new Color(0, 102, 204)));
        add(playerScrollPane, BorderLayout.CENTER);

        JPanel partnershipPanel = new JPanel(new BorderLayout(20, 20));
        JLabel partnershipLabel = new JLabel("Partnership Tracking", SwingConstants.CENTER);
        partnershipLabel.setFont(new Font("Garamond", Font.BOLD, 20));
        partnershipLabel.setForeground(new Color(0, 102, 204));
        partnershipPanel.add(partnershipLabel, BorderLayout.NORTH);

        partnershipModel = createPartnershipModel();
        partnershipTable = createNonEditableTable(partnershipModel);
        JScrollPane partnershipScrollPane = new JScrollPane(partnershipTable);
        partnershipScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(0, 102, 204)), "Partnerships", TitledBorder.LEFT, TitledBorder.TOP, new Font("Garamond", Font.BOLD, 18), new Color(0, 102, 204)));
        partnershipPanel.add(partnershipScrollPane, BorderLayout.CENTER);

        add(partnershipPanel, BorderLayout.SOUTH);
    }

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

    private DefaultTableModel createPartnershipModel() {
        String[] columns = {"Batsman 1", "Batsman 2", "Runs Scored"};
        Object[][] data = {
            {"Player 1", "Player 2", 0},
            {"Player 3", "Player 4", 0}
        };
        return new DefaultTableModel(data, columns);
    }

    private JTable createNonEditableTable(DefaultTableModel model) {
        JTable table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.setFont(new Font("Garamond", Font.PLAIN, 16));
        table.setRowHeight(25);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
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

    public void updatePartnership(String batsman1, String batsman2, int runsScored) {
        for (int i = 0; i < partnershipModel.getRowCount(); i++) {
            if (partnershipModel.getValueAt(i, 0).equals(batsman1) && partnershipModel.getValueAt(i, 1).equals(batsman2)) {
                partnershipModel.setValueAt(runsScored, i, 2);
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Partnership not found: " + batsman1 + " and " + batsman2, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
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
